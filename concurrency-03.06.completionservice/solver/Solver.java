package solver;

import java.util.*;
import java.util.concurrent.*;
import task.*;

public final class Solver {
	/*
	 * the intended solution: - use a completion service - retrieve results - if
	 * a task fails to produce a result then cancel all remaining tasks
	 */
	public static <R> void solve(Executor e, Collection<? extends Callable<R>> tasks, User<R> user)
			throws InterruptedException {

		CompletionService<R> ecs = new ExecutorCompletionService<R>(e);
		int numberOfTasks = tasks.size();
		List<Future<R>> futures = new ArrayList<Future<R>>(numberOfTasks);
		try {
			submitAllTasks(tasks, ecs, futures);
			takeAllTasks(user, ecs, numberOfTasks);
		} finally {
			cancelRemainingTasks(futures);
		}

	}

	private static <R> void takeAllTasks(User<R> user, CompletionService<R> ecs, int numberOfTasks)
			throws InterruptedException {
		for (int i = 0; i < numberOfTasks; ++i) {
			try {
				R result = ecs.take().get();
				if (result == null) {
					break;
				}
				user.use(result);
			} catch (ExecutionException ignore) {
				break;
			}
		}
	}

	private static <R> void cancelRemainingTasks(List<Future<R>> futures) {
		for (Future<R> future : futures) {
			future.cancel(true);
		}
	}

	private static <R> void submitAllTasks(Collection<? extends Callable<R>> tasks, CompletionService<R> ecs,
			List<Future<R>> futures) {
		for (Callable<R> callable : tasks) {
			futures.add(ecs.submit(callable));
		}
	}
}
