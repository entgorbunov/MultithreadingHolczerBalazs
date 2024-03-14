import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class FirstConcurrentMapsWorker implements Runnable {

	private ConcurrentMap<String, Integer> map;
	
	public FirstConcurrentMapsWorker(ConcurrentMap<String, Integer> map) {
		this.map = map;
	}

	@Override
	public void run() {
		try {
			map.put("B",1);
			map.put("H",2);
			map.put("F",3);
            Thread.sleep(1000);
            map.put("A",4);
            Thread.sleep(1000);
            map.put("E",5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }	
	}
}

class SecondConcurrentMapsWorker implements Runnable {

	private ConcurrentMap<String, Integer> map;
	
	public SecondConcurrentMapsWorker(ConcurrentMap<String, Integer> map) {
		this.map = map;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
            System.out.println(map.get("A"));
            Thread.sleep(1000);
            System.out.println(map.get("E"));
            Thread.sleep(1000);
            System.out.println(map.get("C"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

public class ConcurrentMaps {

	public static void main(String[] args) {
		
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

		FirstConcurrentMapsWorker firstWorker = new FirstConcurrentMapsWorker(map);
		SecondConcurrentMapsWorker secondWorker = new SecondConcurrentMapsWorker(map);

        new Thread(firstWorker).start();
        new Thread(secondWorker).start();
		
	}
}
