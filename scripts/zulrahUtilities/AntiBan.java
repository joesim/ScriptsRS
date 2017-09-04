package scripts.zulrahUtilities;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api.util.abc.ABCProperties;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;

/**
 * The AntiBan class provides an easy way to implement Anti-ban Compliance 2.0 into any script.
 *
 * @author Starfox
 * @version 2.0 (1/18/2016 11:47 PM UTC -5:00)
 */
public final class AntiBan {

    /**
     * The object that stores the seeds.
     */
    private static final ABCUtil abc;

    /**
     * The bool flag that determines whether or not debug information is printed.
     */
    private static boolean print_debug;

    /**
     * The amount of resources you have won.
     */
    private static int resources_won;

    /**
     * The amount of resources you have lost.
     */
    private static int resources_lost;

    /**
     * The % run energy to activate run at.
     */
    public static int run_at;

    /**
     * The % hp to eat food at.
     */
    public static int eat_at;

    /**
     * The bool that determines whether or not we should be hovering.
     */
    public static boolean should_hover;

    /**
     * The bool that determines whether or not we should be opening the menu.
     */
    public static boolean should_open_menu;

    /**
     * The time stamp at which we were last under attack.
     */
    public static long last_under_attack_time;

    /**
     * Static initialization block.
     * By default, the use of general anti-ban compliance is set to be true.
     */
    static {
        abc = new ABCUtil();
        print_debug = false;
        resources_won = 0;
        resources_lost = 0;
        run_at = abc.generateRunActivation();
        eat_at = abc.generateEatAtHP();
        should_hover = abc.shouldHover();
        should_open_menu = abc.shouldOpenMenu() && abc.shouldHover();
        last_under_attack_time = 0;
        General.useAntiBanCompliance(true);
    }

    /**
     * Prevent instantiation of this class.
     */
    private AntiBan() {
    }

    /**
     * Destroys the current instance of ABCUtil and stops all anti-ban threads.
     * Call this at the end of your script.
     */
    public static void destroy() {
        abc.close();
    }

    /**
     * Gets the ABCUtil object.
     *
     * @return The ABCUtil object.
     */
    public static ABCUtil getABCUtil() {
        return abc;
    }

    /**
     * Gets the properties for ABCUtil.
     *
     * @return The properties.
     */
    public static ABCProperties getProperties() {
        return getABCUtil().getProperties();
    }

    /**
     * Gets the waiting time for the next action we want to perform.
     *
     * @return The waiting time.
     */
    public static int getWaitingTime() {
        return getProperties().getWaitingTime();
    }

    /**
     * Gets the reaction time that we should sleep for before performing our next action.
     * Examples:
     * <ul>
     * <li>Reacting to when our character stops fishing. The response time will be used before we move on to the next fishing spot, or before we walk to the bank.</li>
     * <li>Reacting to when our character stops mining. The response time will be used before we move on to the next rock, or before we walk to the bank.</li>
     * <li>Reacting to when our character kills our target NPC. The response time will be used before we attack our next target, or before we walk to the bank.</li>
     * </ul>
     *
     * @return The reaction time.
     */
    public static int getReactionTime() {
        resetShouldHover();
        resetShouldOpenMenu();

        ABCProperties properties = getProperties();

        properties.setWaitingTime(getWaitingTime());
        properties.setHovering(should_hover);
        properties.setMenuOpen(should_open_menu);
        properties.setUnderAttack(Combat.isUnderAttack() || (Timing.currentTimeMillis() - last_under_attack_time < 2000));
        properties.setWaitingFixed(false);

        return getABCUtil().generateReactionTime();
    }

    /**
     * Sets the print_debug bool to be equal to the specified bool.
     * By calling this method and providing a true value, other methods in this class will start printing debug information into the system print stream
     * when they are executed.
     *
     * @param print The bool to set.
     */
    public static void setPrintDebug(boolean print) {
        print_debug = print;
    }

    /**
     * Gets the amount of resources won.
     *
     * @return The amount of resources won.
     */
    public static int getResourcesWon() {
        return resources_won;
    }

    /**
     * Gets the amount of resources lost.
     *
     * @return The amount of recourses lost.
     */
    public static int getResourcesLost() {
        return resources_lost;
    }

    /**
     * Sets the amount of resources won to the specified amount.
     *
     * @param amount The amount to set.
     */
    public static void setResourcesWon(int amount) {
        resources_won = amount;
    }

    /**
     * Sets the amount of resources lost to the specified amount.
     *
     * @param amount The amount to set.
     */
    public static void setResourcesLost(int amount) {
        resources_lost = amount;
    }

    /**
     * Increments the amount of resources won by 1.
     */
    public static void incrementResourcesWon() {
        resources_won++;
    }

    /**
     * Increments the amount of resources lost by 1.
     */
    public static void incrementResourcesLost() {
        resources_lost++;
    }

    /**
     * Sets the last_under_attack_time to be equal to the specified time stamp.
     *
     * @param time_stamp The time stamp.
     */
    public static void setLastUnderAttackTime(long time_stamp) {
        last_under_attack_time = time_stamp;
    }

    /**
     * Sleeps for the reaction time generated by ABCUtil.
     * Note that this method uses a special sleeping method from ABCUtil that allows the ABC2 background thread to interrupt the sleep when needed.
     */
    public static void sleepReactionTime() {
        final int reaction_time = getReactionTime();
        if (print_debug) {
            debug("Reaction time: " + reaction_time + "ms.");
        }
        try {
            getABCUtil().sleep(reaction_time);
        } catch (InterruptedException e) {
            debug("Background thread interrupted sleep");
        }
    }

    /**
     * Generates the trackers for ABCUtil.
     * Call this only after successfully completing an action that has a dynamic wait time for the next action.
     *
     * @param estimated_wait The estimated wait time (in milliseconds) before the next action occurs.
     */
    public static void generateTrackers(int estimated_wait) {
        final ABCProperties properties = getProperties();

        properties.setWaitingTime(estimated_wait);
        properties.setUnderAttack(false);
        properties.setWaitingFixed(false);

        getABCUtil().generateTrackers();
    }

    /**
     * Resets the should_hover bool to match the ABCUtil value.
     * This method should be called after successfully clicking an entity.
     */
    public static void resetShouldHover() {
        should_hover = getABCUtil().shouldHover();
    }

    /**
     * Resets the should_open_menu bool to match the ABCUtil value.
     * This method should be called after successfully clicking an entity.
     */
    public static void resetShouldOpenMenu() {
        should_open_menu = getABCUtil().shouldOpenMenu() && getABCUtil().shouldHover();
    }

    /**
     * Randomly moves the camera. Happens only if the time tracker for camera movement is ready.
     *
     * @return True if the action was performed, false otherwise.
     */
    public static boolean moveCamera() {
        if (getABCUtil().shouldRotateCamera()) {
            if (print_debug) {
                debug("Rotated camera");
            }
            getABCUtil().rotateCamera();
            return true;
        }
        return false;
    }

    /**
     * Checks the exp of the skill being trained. Happens only if the time tracker for checking exp is ready.
     *
     * @return True if the exp was checked, false otherwise.
     */
    public static boolean checkXp() {
        if (getABCUtil().shouldCheckXP()) {
            if (print_debug) {
                debug("Checked xp");
            }
            getABCUtil().checkXP();
            return true;
        }
        return false;
    }

    /**
     * Picks up the mouse. Happens only if the time tracker for picking up the mouse is ready.
     *
     * @return True if the mouse was picked up, false otherwise.
     */
    public static boolean pickUpMouse() {
        if (getABCUtil().shouldPickupMouse()) {
            if (print_debug) {
                debug("Picked up mouse");
            }
            getABCUtil().pickupMouse();
            return true;
        }
        return false;
    }

    /**
     * Navigates the mouse off game window and mimics de-focusing the window. Happens only if the time tracker for leaving the game is ready.
     *
     * @return True if the mouse left the game window, false otherwise.
     */
    public static boolean leaveGame() {
        if (getABCUtil().shouldLeaveGame()) {
            if (print_debug) {
                debug("Left game window");
            }
            getABCUtil().leaveGame();
            return true;
        }
        return false;
    }

    /**
     * Examines an entity near your player. Happens only if the time tracker for examining an entity is ready.
     *
     * @return True if an entity was examined, false otherwise.
     */
    public static boolean examineEntity() {
        if (getABCUtil().shouldExamineEntity()) {
            if (print_debug) {
                debug("Examined entity");
            }
            getABCUtil().examineEntity();
            return true;
        }
        return false;
    }

    /**
     * Right clicks the mouse. Happens only if the time tracker for right clicking the mouse is ready.
     *
     * @return True if a random spot was right clicked, false otherwise.
     */
    public static boolean rightClick() {
        if (getABCUtil().shouldRightClick()) {
            if (print_debug) {
                debug("Right clicked");
            }
            getABCUtil().rightClick();
            return true;
        }
        return false;
    }

    /**
     * Moves the mouse. Happens only if the time tracker for moving the mouse is ready.
     *
     * @return True if the mouse was moved to a random point, false otherwise.
     */
    public static boolean mouseMovement() {
        if (getABCUtil().shouldMoveMouse()) {
            if (print_debug) {
                debug("Mouse moved");
            }
            getABCUtil().moveMouse();
            return true;
        }
        return false;
    }

    /**
     * Opens up a game tab. Happens only if the time tracker for tab checking is ready.
     *
     * @return True if the combat tab was checked, false otherwise.
     */
    public static boolean checkTabs() {
        if (getABCUtil().shouldCheckTabs()) {
            if (print_debug) {
                debug("Tab checked");
            }
            getABCUtil().checkTabs();
        }
        return false;
    }

    /**
     * Checks all of the actions that are performed with the time tracker; if any are ready, they will be performed.
     */
    public static void timedActions() {
        moveCamera();
        checkXp();
        pickUpMouse();
        leaveGame();
        examineEntity();
        rightClick();
        mouseMovement();
        checkTabs();
    }

    /**
     * Gets the next target that should be interacted with from the specified list of targets.
     *
     * @param targets The targets to choose from.
     * @param <T>     The generic type.
     * @return The target to interact with.
     */
    public static <T extends Positionable> T selectNextTarget(T[] targets) {
        //noinspection unchecked
        return (T) getABCUtil().selectNextTarget(targets);
    }

    /**
     * Activates run.
     * No action is taken if run is already enabled or the current run energy is less than the value returned by ABCUtil.
     *
     * @return True if run was enabled, false otherwise.
     */
    public static boolean activateRun() {
        if (Game.getRunEnergy() >= run_at && !Game.isRunOn()) {
            Mouse07.fixSelected();
            if (Options.setRunOn(true)) {
                if (print_debug) {
                    debug("Turned run on at " + run_at + "%");
                }
                run_at = getABCUtil().generateRunActivation();
                return true;
            }
        }
        return false;
    }

    /**
     * Eats/drinks an item in your inventory with the specified name if your current HP percent is less than or equal to the value generated by ABCUtil.
     * Note that if there is any delay/lag that is longer than 3000 milliseconds between the time the food/drink was clicked and when your players HP is
     * changed the tracker will not be reset and you will have to reset it manually.
     *
     * @param option The option to click the food/drink with (this is normally "Eat" or "Drink").
     *               Input an empty string to have the method attempt to find the correct option automatically. Note that this is not guaranteed to execute properly if an
     *               empty string is inputted.
     * @param name   The name of the food or drink.
     * @return True if the food/drink was successfully eaten/drank, false otherwise.
     * @see #eat(java.lang.String, org.tribot.api2007.types.RSItem)
     */
    public static boolean eat(String option, final String name) {
        return eat(option, Inventory07.getItem(name));
    }

    /**
     * Eats/drinks an item in your inventory with the specified ID if your current HP percent is less than or equal to the value generated by ABCUtil.
     * Note that if there is any delay/lag that is longer than 3000 milliseconds between the time the food/drink was clicked and when your players HP is
     * changed the tracker will not be reset and you will have to reset it manually.
     *
     * @param option The option to click the food/drink with (this is normally "Eat" or "Drink").
     *               Input an empty string to have the method attempt to find the correct option automatically. Note that this is not guaranteed to execute
     *               properly if an empty string is inputted.
     * @param id     The ID of the food or drink.
     * @return True if the food/drink was successfully eaten/drank, false otherwise.
     * @see #eat(java.lang.String, org.tribot.api2007.types.RSItem)
     */
    public static boolean eat(String option, final int id) {
        return eat(option, Inventory07.getItem(id));
    }

    /**
     * Eats/drinks the item specified if your current HP percent is less than or equal to the value generated by ABCUtil.
     * Note that if there is any delay/lag that is longer than 3000 milliseconds between the time the food/drink was clicked and when your players HP is
     * changed the tracker will not be reset and you will have to reset it manually.
     *
     * @param option The option to click the food/drink with (this is normally "Eat" or "Drink").
     *               Input an empty string to have the method attempt to find the correct option automatically. Note that this is not guaranteed to execute
     *               properly if an empty string is inputted.
     * @param item   The item to eat or drink.
     * @return True if the food/drink was successfully eaten/drank, false otherwise.
     */
    public static boolean eat(String option, RSItem item) {
        if (option == null || item == null) {
            return false;
        }

        final int current_hp = Player07.getHPPercent();

        if (current_hp <= eat_at) {
            Mouse07.fixSelected();
            if (option.isEmpty()) {
                String[] actions = Items07.getActions(item);
                for (String action : actions) {
                    if (action.contains("Eat") || action.contains("Drink")) {
                        option = action;
                        break;
                    }
                }
            }
            if (!option.isEmpty() && Clicking.click(option, item)) {
                if (print_debug) {
                    debug("Ate food at " + eat_at + "%");
                }
                if (Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        sleep();
                        return Player07.getHPPercent() > eat_at;
                    }
                }, 3000)) {
                    eat_at = getABCUtil().generateEatAtHP();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Walks to the next anticipated resource.
     * Note that you must calculate which resource is the next anticipated resource yourself.
     *
     * @param anticipated The next anticipated resource.
     * @return True if the player moved to the resource; false otherwise.
     */
    public static boolean goToAnticipated(Positionable anticipated) {
        if (anticipated != null) {
            sleepReactionTime();
            return Walking07.straightWalk(anticipated.getPosition());
        }
        return false;
    }

    /**
     * Checks to see if the player should switch resources.
     * Note that this method will only return correctly if you have been tracking the resources you have won and lost.
     * Note also that you must create the check time in your script and reset it accordingly.
     * e.g. to check if you should switch resources, you should check the following condition:
     * <code>Timing.currentTimeMillis() >= check_time && AntiBan.shouldSwitchResources()</code>
     *
     * @param player_count The amount of players gathering resources near you.
     * @return True if your player should switch resources, false otherwise.
     */
    public static boolean shouldSwitchResources(int player_count) {
        double win_percent = ((double) (resources_won + resources_lost) / (double) resources_won);
        return win_percent < 50.0 && getABCUtil().shouldSwitchResources(player_count);
    }

    /**
     * Sleeps the current thread for the item interaction delay time.
     * This method should be called directly after interacting with an item in your players inventory.
     */
    public static void sleep() {
        Client.sleep(25, 75);
    }

    /**
     * Sleeps the current thread for the item interaction delay time multiplied by the specified number of iterations. This method can be used to sleep between
     * certain actions that do not have a designated method already assigned to them such as casting spells or clicking interfaces.
     * <p/>
     * This method does not guarantee a static sleep time each iteration.
     *
     * @param iterations How many times to sleep the item interaction delay time.
     * @see #sleep()
     */
    public static void sleep(int iterations) {
        for (int i = 0; i < iterations; i++) {
            sleep();
        }
    }

    /**
     * Hovers the next available object if applicable.
     * <p/>
     * Note that you <i>must</i> reset the tracker yourself after the current object interaction is finished.
     *
     * @param currentlyInteracting The object you are currently interacting with.
     */
    public static void hoverNextObject(final RSObject currentlyInteracting) {
        if (currentlyInteracting == null || !should_hover) {
            return;
        }

        String name = "";

        //<editor-fold defaultstate="collapsed" desc="Get name">
        RSObjectDefinition definition = currentlyInteracting.getDefinition();
        if (definition != null) {
            String definitionName = definition.getName();
            if (definitionName != null) {
                name = definitionName;
            }
        }
        if (name.isEmpty()) {
            return;
        }
        //</editor-fold>

        final String objectName = name;
        final RSObject next = Objects07.getObject(new Filter<RSObject>() {
            //<editor-fold defaultstate="collapsed" desc="Filter">
            @Override
            public boolean accept(RSObject o) {
                if (o == null) {
                    return false;
                }
                final RSObjectDefinition def = o.getDefinition();
                if (def != null) {
                    final String name = def.getName();
                    if (name != null) {
                        return name.equals(objectName) && !o.getPosition().equals(currentlyInteracting.getPosition()) && Objects07.isValid(o, true);
                    }
                }
                return false;
            }
            //</editor-fold>
        }, 15);
        if (next != null) {
            if (!Entities.isHovering(next.getModel()) && Clicking.hover(next)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(25);
                        return Entities.isHovering(next.getModel());
                    }
                }, 500);
            }
        }
    }

    /**
     * Hovers the next available NPC if applicable.
     * <p/>
     * Note that you <i>must</i> reset the tracker yourself after the current NPC interaction is finished.
     */
    public static void hoverNextNPC() {
        if (!should_hover) {
            return;
        }

        final RSCharacter interacting = Player07.getInteractingCharacter();

        if (interacting != null) {
            final String name = interacting.getName();
            if (name != null) {
                final RSNPC next = NPCs07.getNPC(new Filter<RSNPC>() {
                    //<editor-fold defaultstate="collapsed" desc="NPC filter">
                    @Override
                    public boolean accept(RSNPC npc) {
                        if (npc == null) {
                            return false;
                        }
                        RSNPCDefinition def = npc.getDefinition();
                        if (def == null) {
                            return false;
                        }
                        String def_name = def.getName();
                        if (def_name == null || !def_name.equals(name)) {
                            return false;
                        }
                        return npc.isOnScreen() && npc.isClickable() && !npc.getPosition().equals(interacting.getPosition());
                    }
                    //</editor-fold>
                });
                if (next != null) {
                    if (!Entities.isHovering(next.getModel()) && Clicking.hover(next)) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                General.sleep(25);
                                return Entities.isHovering(next.getModel());
                            }
                        }, 500);
                    }
                }
            }
        }
    }

    /**
     * Sends the specified message to the system print stream with the [ABC2] tag.
     *
     * @param message The message to print.
     */
    private static void debug(Object message) {
        System.out.println("[ABC2] " + message);
    }
}
