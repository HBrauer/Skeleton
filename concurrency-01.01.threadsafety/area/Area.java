package area;

import java.awt.Point; 
import java.lang.String;

/*
   An Area specifies an rectangular area in a coordinate space that 
   is described by the rectangle's top-left point (x, y) in the coordinate 
   space, its width, and its height.
*/ 
public interface Area {
	int     getHeight();
	int     getWidth();
    Point   getLocation();
    String  getName();
    void    resize(int factor);
    void    translate(Point p);
    void    rename(String s);
}