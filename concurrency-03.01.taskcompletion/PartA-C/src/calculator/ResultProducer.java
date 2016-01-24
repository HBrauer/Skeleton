package calculator;

import java.util.List;

public class ResultProducer {
	private final int SIZE = 10000;
	public List<Integer> produceResult() {
		return PrimeNumberCalculator.calculate(SIZE);
	}
    ... to be done ...
}
