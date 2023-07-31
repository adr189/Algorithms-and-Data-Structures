package main;

import java.util.Stack;

/**
 * 
 * @author Alexandre Rodrigues 54472
 *
 */
public class FibonacciGenerator implements Generator <Long> {

	private Stack<Long> undo = new Stack<Long>(); //histórico
	private Stack<Long> redo = new Stack<Long>();
	
	/**
	 * 
	 */
	@Override
	public void reset() {
		int stackSize = undo.size();
		for (int i = 1; i < stackSize; i++) {
			redo.add(undo.pop());
		}
	}

	/**
	 * 
	 */
	@Override
	public Long prev() {
		redo.add(undo.pop());
		return redo.peek();
	}

	/**
	 * 
	 */
	@Override
	public Long next() {
		if (undo.isEmpty() && redo.isEmpty()) {
			undo.add(0L);
			undo.add(1L);
		}
		else if (!redo.isEmpty()) {
			undo.add(redo.pop());
		}
		else {
			long last = undo.pop();
			long lastBefore = undo.pop();
			long next = last + lastBefore;
			undo.add(lastBefore);
			undo.add(last);
			undo.add(next);
		}
		return undo.peek();
	}
	
}
