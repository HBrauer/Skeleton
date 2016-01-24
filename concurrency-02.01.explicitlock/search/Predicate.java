package search;

public interface Predicate<T> {
	boolean evaluate(T arg);
}
