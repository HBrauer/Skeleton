package search;

import java.util.ArrayList;
import java.util.Collection;

public final class CollectionProvider {

	private static final int size = 10;

	public static Collection<ElementType> makeCollection() {
		Collection<ElementType> col = new ArrayList<ElementType>(size);
		for (int i = 0; i < size; i++) {
			col.add(new ElementType(i,new StringBuilder()));
		}
		return col;
	}
}
