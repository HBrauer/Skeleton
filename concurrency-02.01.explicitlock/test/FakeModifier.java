package test;

import java.util.Collection;
import search.ElementType;

/*
 * Note, the modifier does not change the part of the StringBuilder element
 * that is used for finding the element (namely the first character).
 */
public final class FakeModifier {
	private static char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static void modify(ElementType element) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		for (int j = 0; j < 3; j++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			element.getMutablePart().append(letters[j]);
		}
	}
}
