import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 *
 * Latch --> multiple threads can wait for each other
 * <p>
 * A CyclicBarrier is used in situations where you want to create a group of
 * tasks to perform work in parallel + wait until they are all finished before
 * moving on to the next step -> something like join() -> something like
 * CountDownLatch
 * <p>
 * CountDownLatch: one-shot event CyclicBarrier: it can be reused over and over
 * again
 * <p>
 * + cyclicBarrier has a barrier action: a runnable, that will run automatically
 * when the count reaches 0 !!
 * <p>
 * new CyclicBarrier(N) -> N threads will wait for each other
 * <p>
 * WE CAN NOT REUSE LATCHES BUT WE CAN REUSE CyclicBarriers --> reset() !!!
 * 
 */

class CyclicBarrierWorker implements Runnable {

	private int id;
	private Random random;
	private CyclicBarrier cyclicBarrier;

	public CyclicBarrierWorker(int id, CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
		this.random = new Random();
		this.id = id;
	}

	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			doWork(i + 1);
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	private void doWork(int round) {
		System.out.println("Thread with ID " + id + " starts the task, round " + round + "...");
		try {
			Thread.sleep(random.nextInt(3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread with ID " + id + " finished round " + round + "...");
	}

	public String toString() { return ""+this.id; };
}

public class CyclicBarriers {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
			private int round = 1;

			@Override
			public void run() {
				System.out.println("Round " + round + " completed. We are able to use the trained neural network...");
				round++;
			}
		});

        IntStream.range(0, 5).mapToObj(i -> new CyclicBarrierWorker(i + 1, barrier))
				.forEach(executorService::execute);

        executorService.shutdown();
	}
}

