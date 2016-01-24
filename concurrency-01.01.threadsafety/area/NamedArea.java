package area;

import java.awt.Point;
import java.lang.String;

/*
   A NamedArea specifies an rectangular area in a coordinate space that 
   is described by the rectangle's top-left point (x, y) in the coordinate 
   space, its width, its height, and its name.
*/ 

public class NamedArea implements Area {
	private String name;
	private Point  topleft;
	private int    width;
	private int    height;


    public NamedArea(String s, Point p, int w, int h) {
      name = s;
	  topleft = p;
      width = w;
	  height = h;
    }
    public int getHeight() {
      return height;
    }
	public int getWidth() {
	  return width;
	}
    public  Point getLocation() {
      return (Point)topleft.clone();
    }
    public String getName() {
      return name;
    }
    public  void resize(int factor) {
	  width  *= factor;
	  height *= factor;
    }
    public  void translate(Point p) {
      topleft.x += p.x;
	  topleft.y += p.y;
    }	
    public void rename(String s) {
      name = s;
    }
}