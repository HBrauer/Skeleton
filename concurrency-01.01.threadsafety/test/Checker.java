package test;

import java.awt.Point;
import java.util.concurrent.ConcurrentSkipListSet;

public final class Checker {
	public static boolean checkLocation(Point p,Point translation) {
		if (p.x/translation.x != p.y/translation.y) 	return false;
		return true;
	}
	public static boolean checkWidthHeight(int w, int h,int newWidth, int newHeighth) {
		if (newWidth/w != newHeighth/h) 	return false;
		return true;
	}
	public static boolean checkName(ConcurrentSkipListSet<String> names_written, String name_read) {
		return names_written.contains(name_read);
	}
}
