import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * 	BlockingQueue -> an interface that represents a queue that is thread safe
 * 		Put items or take items from it ...
 * 
 * 		For example: one thread putting items into the queue and another thread taking items from it
 * 			at the same time !!!
 * 				We can do it with producer-consumer pattern !!!
 * 
 * 		put() putting items to the queue
 * 		take() taking items from the queue
 * 
 */

class FirstBlockingQueueWorker implements Runnable {

	private BlockingQueue<String> blockingQueue;
	
	public FirstBlockingQueueWorker(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			blockingQueue.put("B");
            Thread.sleep(1000);
            blockingQueue.put("A");
            Thread.sleep(1000);
            blockingQueue.put("C");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }	
	}
}

class SecondBlockingQueueWorker implements Runnable {

	private BlockingQueue<String> blockingQueue;
	
	public SecondBlockingQueueWorker(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

public class BlockingQueues {

	public static void main(String[] args) {
		
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

		FirstBlockingQueueWorker firstBlockingQueueWorker = new FirstBlockingQueueWorker(queue);
		SecondBlockingQueueWorker secondBlockingQueueWorker = new SecondBlockingQueueWorker(queue);

        new Thread(firstBlockingQueueWorker).start();
        new Thread(secondBlockingQueueWorker).start();
		
	}
}
