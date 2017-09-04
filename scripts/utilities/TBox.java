package scripts.utilities;

import java.awt.Point;

public class TBox {
	public int x1;
	public int x2;
	public int y1;
	public int y2;
	
	public TBox(java.awt.Rectangle rec){
		x1 = (int) rec.getMinX();
		y1 = (int) rec.getMinY();
		x2 = (int) rec.getMaxX();
		y2 = (int) rec.getMaxY();
	}
	
	public TBox(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public boolean isPointInBox(Point p){
		if (p.x>=x1 && p.y>=y1 && p.x<=x2 && p.y <= y2){
			return true;
		}
		return false;
	}
	
	public TBox(java.awt.Point point, int size){
		this.x1 = point.x - size/2;
		this.x2 = point.x + size/2;
		this.y1 = point.y - size/2;
		this.y2 = point.y + size/2;
	}
	
	public TBox(java.awt.Point point, int sizeX, int sizeY){
		this.x1 = point.x - sizeX/2;
		this.x2 = point.x + sizeX/2;
		this.y1 = point.y - sizeY/2;
		this.y2 = point.y + sizeY/2;
	}

	public int sizeY() {
		return (y2-y1);
	}

	public int sizeX() {
		return (x2-x1);
	}
	
	public java.awt.Point middlePoint(){
		java.awt.Point point = new Point();
		point.x = x1 + sizeX()/2;
		point.y = y1 + sizeY()/2;
		return point;
	}
}
