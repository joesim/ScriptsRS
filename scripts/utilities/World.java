package scripts.utilities;

import java.awt.Rectangle;

public class World {
	boolean member;
	int world = 0;
	Rectangle rec = null;
	boolean functional;

	public World(int world, Rectangle r, boolean m, boolean functional) {
		this.world = world;
		this.rec = r;
		this.member = m;
		this.functional = functional;
	}

	public void setRec(Rectangle rectangle) {
		rec = rectangle;
		
	}
}
