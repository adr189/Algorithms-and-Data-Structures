package projetoIndividual;

/**
 * API for mutable queues. 
 *
 * @param <E> The type of the elements in the queue.
 */
interface Queue<E> {

	/** Add an element to the rear of this queue
	 * @param e The object to insert. 
	 */
	public void enqueue(E e);

	/** Remove the element at the front of the queue.
	 * @requires !isEmpty()
	 */
	public void dequeue();

	/** 
	 * @return e  The element at the front of the queue.
	 * @requires !isEmpty() 
	 */
	public E front();

	/** 
	 * @return If this queue is empty. 
	 */
	public boolean isEmpty();

	/**
	 * How many elements are there
	 * @return the number of elements in the queue 
	 */
	public int size();
	
	/**
	 * Print the current state of the queue in the format
	 *    <a1, a2, a3, ..., an<
	 * @return the current state of the queue
	 */
	public String toString();
}