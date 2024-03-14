import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * With the help of Exchanger -> two threads can exchange objects
 *
 * exchange() -> exchanging objects is done via one of the two exchange()
 * methods
 *
 * 	For example: genetic algorithms, training neural networks
 *
 */


class Worker implements Runnable {

	private int counter;
	private Exchanger<Integer> exchanger;
	private boolean isIncrementing;
	private int exchangeLimit;

	public Worker(Exchanger<Integer> exchanger, boolean isIncrementing, int startCounter, int exchangeLimit) {
		this.exchanger = exchanger;
		this.isIncrementing = isIncrementing;
		this.counter = startCounter;
		this.exchangeLimit = exchangeLimit;
	}

	@Override
	public void run() {
		int exchanges = 0;
		while (exchanges < exchangeLimit) {
			if (isIncrementing) {
				counter++;
				System.out.println(Thread.currentThread().getName() + " incremented the counter: " + counter);
			} else {
				counter--;
				System.out.println(Thread.currentThread().getName() + " decremented the counter: " + counter);
			}
			try {
				System.out.println(Thread.currentThread().getName() + " before exchange: " + counter);
				counter = exchanger.exchange(counter);
				System.out.println(Thread.currentThread().getName() + " after exchange: " + counter);
				exchanges++;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			try {
				// Small delay to ensure the console output is not jumbled
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}

public class Exchangers {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Exchanger<Integer> exchanger = new Exchanger<>();

		Worker worker1 = new Worker(exchanger, true, 0, 3);
		Worker worker2 = new Worker(exchanger, false, 0, 3);

		executor.execute(worker1);
		executor.execute(worker2);

		executor.shutdown();
		try {
			if (executor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				System.out.println("Все поставленные задачи были выполнены");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
