package projetoIndividual;
import java.util.Random;

public class RunTest {

	private final static int NQUEUES = 10;
	private final static int NELEMS = 100;  // keep it even
	
	public static void main(String[] args) throws IllegalQueueRequest {
		Random rnd = new Random();
		rnd.setSeed(237);
		
		QueueSystem<Integer> q = new ArrayQueueSystem<>(NQUEUES);
		System.out.printf("Current queue: %d (empty? %b)\n", q.current(), q.isEmpty());
		q.create(); 
		q.activate(NQUEUES);
				
		//////////////////////
		
		for(int i=0; i<NELEMS; i++) {
			q.focus(rnd.nextInt(NQUEUES+1));	
			q.enqueue(rnd.nextInt(100));
		}
		System.out.print(q);
		
		int sum=0;
		for(int i=0; i<q.howManyQueues(); i++) {
			if (q.isActivated(i))
				q.focus(i);
			sum += q.front();	
		}
		System.out.printf("Sum of fronts from all %d queues: %d\n", q.howManyActiveQueues(), sum);
		
		for(int i=0; i<NELEMS/2; i++) {
			q.focusMax();
			q.dequeue();
			if (q.isEmpty())
				q.deactivate(q.current());
		}

		System.out.print(q);
		
		for(int i=0; i<NELEMS/2-1; i++) {
			q.focusMax();
			q.dequeue();
			if (q.isEmpty()) {
				System.out.printf("queue %d deactivated\n", q.current());
				q.deactivate(q.current());
			}
		}
		q.focusMin();
		System.out.printf("There is only %d queue activated with index %d", q.howManyActiveQueues(), q.current());
	}
}
