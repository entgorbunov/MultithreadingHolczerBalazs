import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * It implements the BlockingQueue interface
 * 
 * 	- unbounded concurrent queue
 * 	- it uses the same ordering rules as the java.util.PriorityQueue class -> have to implement the COmparable interface
 * 			The comparable interface will determine what will the order in the queue
 * 
 * 			The priority can be the same compare() == 0 case
 * 
 *  - no null items !!!
 * 
 *
 */

class FirstPriorityBlockingQueueWorker implements Runnable {

	private BlockingQueue<String> blockingQueue;
	
	public FirstPriorityBlockingQueueWorker(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			blockingQueue.put("B");
			blockingQueue.put("H");
			blockingQueue.put("F");
            Thread.sleep(1000);
            blockingQueue.put("A");
            Thread.sleep(1000);
            blockingQueue.put("E");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }	
	}
}

class SecondPriorityBlockingQueueWorker implements Runnable {

	private BlockingQueue<String> blockingQueue;
	
	public SecondPriorityBlockingQueueWorker(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
            System.out.println(blockingQueue.take());
            Thread.sleep(1000);
            System.out.println(blockingQueue.take());
            Thread.sleep(1000);
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

public class PriorityBlockingQueues {

	public static void main(String[] args) {
		
		BlockingQueue<String> queue = new PriorityBlockingQueue<>();

		FirstPriorityBlockingQueueWorker firstWorker = new FirstPriorityBlockingQueueWorker(queue);
		SecondPriorityBlockingQueueWorker secondWorker = new SecondPriorityBlockingQueueWorker(queue);

        new Thread(firstWorker).start();
        new Thread(secondWorker).start();
		
	}
}
