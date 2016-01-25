package org.test.forkjoinpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ForkJoinPoolTest {

	private static class Muliplier extends RecursiveTask<List<Integer>> {

		private static final long serialVersionUID = -2466166254731843190L;
		private List<Integer> data;

		public Muliplier(List<Integer> data) {
			this.data = data;
		}

		@Override
		protected List<Integer> compute() {
			List<Integer> result;
			if (data.size() > 1) {
				int split = data.size() / 2;
				Muliplier muliplier1 = new Muliplier(data.subList(0, split));
				Muliplier muliplier2 = new Muliplier(data.subList(split, data.size()));
				muliplier2.fork();
				result = Stream.concat(muliplier1.compute().stream(), muliplier2.join().stream()).collect(Collectors.toList());
				
			} else if (data.size() == 1) {
				result = Collections.singletonList(data.get(0));
			} else {
				result = Collections.emptyList();
			}
			return result;
		}

	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		List<Integer> data = forkJoinPool.invoke(new Muliplier(createData()));
		data.stream().forEach(System.out::println);
		
	}
	
	private static List<Integer> createData(){
		return IntStream.range(0, 15).boxed().collect(Collectors.toList());
	}

}
