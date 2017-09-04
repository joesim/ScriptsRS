package scripts.others;

import java.awt.Rectangle;

public enum Screen {

	ALL_SCREEN (0,0,760,500),
	MAIN_SCREEN (0, 0, 515, 335),
	MINIMAP (587,30,706,136),
	INVENTORY (528,173,760,490),
	CHAT_BOX (0,340,518,500),
	VARROCK_TABS(192,205,312,320),
	HOUSE_TABS(361,38,492,148),
	KNIFE(540,200,610,260),
	LONGBOW(279,382,370,434),
	STRING_BOW(242,376,415,451);
	
	private int x1,x2,y1,y2;
	
	Screen(int x1,int y1,int x2,int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x1,y1,(x2-x1),(y2-y1));
	}
	
}
