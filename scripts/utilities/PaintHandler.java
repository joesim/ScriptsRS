package scripts.utilities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.LinkedList;

import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api2007.Game;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSTile;

/**
 * Utility class for doing a couple different things with paint like drawing
 * models and tiles.
 *
 * Instantiate this in your main script class and implement Painting. In your
 * draw(Graphic g) method, simply pass the graphics object into this class's
 * methods casting it to Graphics2D
 * 
 * @author Sea Shepard
 *
 */
public class PaintHandler {

	/**
	 * Draws a model onto the screen
	 * 
	 * @param model
	 *            The model we want to draw
	 * @param g
	 *            Our grapics render
	 * @param fill
	 *            Whether or not we should fill the model
	 */
	public void drawModel(RSModel model, Graphics2D g, boolean fill) {
		if (model.getAllVisiblePoints().length != 0) {
			if (fill) {
				// fill triangles
				for (Polygon p : model.getTriangles()) {
					g.fillPolygon(p);
				}
			} else {
				// draw triangles
				for (Polygon p : model.getTriangles()) {
					g.drawPolygon(p);
				}
			}
		}
	}

	/**
	 * Draws a tile onto the screen
	 * 
	 * @param tile
	 *            The tile you want to draw
	 * @param g
	 *            The graphics render we are using
	 * @param color
	 *            The color we want to use as filler
	 * @param fill
	 *            Whether or not we are filling the tile
	 */
	public void drawTile(RSTile tile, Graphics2D g, boolean fill) {
		if (tile.isOnScreen()) {
			if (fill) {
				g.fillPolygon(Projection.getTileBoundsPoly(tile, 0));
			} else {
				g.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
			}
		}
	}

	

}