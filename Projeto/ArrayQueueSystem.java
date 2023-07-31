package projetoIndividual;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A queue system is a sequence of FIFO queues.
 * 
 * Queues are identified by a non-negative index (the first queue has index 0)  
 * 
 * Queues inside a queue system can be active or not. 
 * 
 * Queue commands (enqueue, dequeue, front, isEmpty) are only valid on active queues.
 * 
 * All deactivated queues must be empty.
 * 
 * It is possible to create new queues. A new queue must be added at the end of 
 * sequence of existing queues. A new queue is inactive.
 * 
 * When a queue command is executed, those commands are done on the current queue.
 * 
 * There is always one queue denoted the current queue. 
 * 
 * The current queue must always be an active queue. 
 *  
 * @author Alexandre Rodrigues 54472
 *
 * @param <E> The type of elements saved in the queue system
 */
public class ArrayQueueSystem<E> implements QueueSystem<E> {

	private Queue<E>[] queues;
	private int nQueues;       // how many queues do we have? 
	private int currentQueue;	//which one is the active queue?
	private Set<Integer> activeQueues;	//set of queues that are active
	
	/**
	 * Constructor
	 * @param howManyQueues the initial number of active queues
	 */
	@SuppressWarnings("unchecked")
	public ArrayQueueSystem(int howManyQueues) {
		
		// we start with some extra space to account for new queues 
		queues = (Queue<E>[])Array.newInstance(Queue.class, howManyQueues * 2);

		activeQueues = new HashSet<Integer>();
		
		for (int i = 0; i < howManyQueues; i++) {
			queues[i] = new ArrayQueue<E>();	//creates new array queue
			activeQueues.add(i);
			nQueues++;
		}
	}
	
	/* note: when creating new queues, the vector might became full. 
	 * Use the same strategy applied in classes Stack or Queue
	 * (ie, the new vector will have twice the elements) to achieve 
	 * more space.
	 */

	/**
	 * @return a textual description of the queue system state 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<nQueues; i++) {
			sb.append(queues[i]).append("\n");
		}
		
		return sb.toString();
	}

	/**
	 * Inform which is the current queue
	 * @return the index of the current queue
	 */
	@Override
	public int current() {
		return currentQueue;
	}

	/** 
	 * Add an element to the rear of the current queue
	 * @requires isActivated(current())
	 * @param e The object to insert.
	 * @throws IllegalQueueRequest if the queue is not activated 
	 */
	@Override
	public void enqueue(E e) throws IllegalQueueRequest {
		if (isActivated(current())) {
			queues[current()].enqueue(e);
		} else {
			throw new IllegalQueueRequest("The current queue is not activated!");
		}
	}

	/** 
	 * Remove the element at the front of the current queue.
	 * @requires isActivated(current())
	 * @requires !isEmpty()
	 * @throws IllegalQueueRequest if the queue is not activated or is empty
	 */
	@Override
	public void dequeue() throws IllegalQueueRequest {
		if (isActivated(current()) && !isEmpty()) {
			queues[current()].dequeue();
		} else {
			throw new IllegalQueueRequest("The current queue is not activated or it's empty!");
		}
	}

	/** 
	 * @return e The element at the front of the current queue.
	 * @requires isActivated(current())
	 * @requires !isEmpty() 
	 * @throws IllegalQueueRequest if the queue is not activated or is empty 
	 */
	@Override
	public E front() throws IllegalQueueRequest {
		if (isActivated(current()) && !isEmpty()) {
			return queues[current()].front();
		} else {
			throw new IllegalQueueRequest("The current queue is not activated or it's empty!");
		}
	}

	/** 
	 * @return If the current queue is empty. 
	 */
	@Override
	public boolean isEmpty() {
		return queues[current()].isEmpty();
	}

	/**
	 * @return the number of all existing queues
	 */
	@Override
	public int howManyQueues() {
		return nQueues;
	}

	/**
	 * @return the number of active queues
	 */
	@Override
	public int howManyActiveQueues() {
		return activeQueues.size();
	}

	/**
	 * The number of elements inside the queue system
	 * @return the number of elements in the queue system
	 */
	@Override
	public int size() {
		int total = 0;
		for (int i = 0; i < howManyQueues(); i++) {
			total+=queues[i].size();
		}
		return total;
	}

	/**
	 * Create a new queue at the end of the system,
	 * which starts non activated
	 * @ensures !isActivated(howManyQueues()-1)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void create() {
		if (nQueues < queues.length) {
			queues[howManyQueues()] = new ArrayQueue<E>();
			nQueues++;
			assert (!isActivated(howManyQueues()-1));
		} else {
			Queue<E> [] newQueues = (Queue<E>[])Array.newInstance(Queue.class, queues.length * 2);
			System.arraycopy(queues, 0, newQueues, 0, nQueues);
			queues = newQueues;
			create();
		}
	}

	/**
	 * Verify if the i-th queue is an active queue
	 * @param i the index of the queue
	 */
	@Override
	public boolean isActivated(int i) {
		return activeQueues.contains(i);
	}

	/**
	 * Make the i-th queue an active queue
	 * @ensures isActivated(i)
	 * @param i the index of the queue
	 */
	@Override
	public void activate(int i) {
		activeQueues.add(i);
		assert (isActivated(i));
	}

	/**
	 * Make the i-th queue a deactivated queue
	 * @requires isEmpty()
	 * @ensures !isActivated(i)
	 * @param i the index of the queue
	 * @throws IllegalQueueRequest if the queue is not empty 
	 */
	@Override
	public void deactivate(int i) throws IllegalQueueRequest {
		if (queues[i].isEmpty()) {
			activeQueues.remove(i);
			assert (isActivated(i));
		} 
		if (i == current()) {
			List<Integer> temp = new ArrayList<Integer>(activeQueues);
			if (!temp.isEmpty()) {
				currentQueue = temp.get(0);
			} else {
				currentQueue = 0;
			}
		} else {
			throw new IllegalQueueRequest("The specified queue is not empty!");
		}
	}

	/**
	 * Make the i-th queue, the current queue
	 * @requires isActivated(i)
	 * @ensures current()==i
	 * @param i the index of the new current queue
	 * @throws IllegalQueueRequest if the queue is not activated 
	 */
	@Override
	public void focus(int i) throws IllegalQueueRequest {
		if (isActivated(i)) {
			currentQueue = i;
			assert (current()==i);
		} else {
			throw new IllegalQueueRequest("The specified queue is not activated!");
		}
	}

	/**
	 * Focus on a queue with minimal occupation 
	 * (if several equal, pick the queue with the least index)
	 * @ensures isActivated(current())
	 * @return the number of elements in that queue 
	 */
	@Override
	public int focusMin() {
		int ocuppation = queues[current()].size();
		int min = queues.length;
		for (int i = howManyQueues() - 1; i > 0; i--) {
			if (queues[i].size() <= ocuppation && !(queues[i].isEmpty())) {
				ocuppation = queues[i].size();
				min = i;
			}
		}
		activate(min);
		currentQueue = min;
		assert (isActivated(current()));
		return ocuppation;
	}

	/**
	 * Focus on a queue with maximal occupation
	 * (if several equal, pick the queue with the least index)
	 * @ensures isActivated(current())
	 * @return the number of elements in that queue 
	 */
	@Override
	public int focusMax() {
		int ocuppation = 0;
		int max = queues.length;
		for (int i = howManyQueues() - 1; i >= 0; i--) {
			if (queues[i].size() >= ocuppation) {
				ocuppation = queues[i].size();
				max = i;
			}
		}
		activate(max);
		currentQueue = max;
		assert (isActivated(current()));
		return ocuppation;
	}

}