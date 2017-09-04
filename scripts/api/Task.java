package scripts.api;

/**
 * @author Encoded
 */
public interface Task {

	String action();
	
    int priority();

    boolean validate();

    void execute();

}