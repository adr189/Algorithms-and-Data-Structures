import java.util.HashMap;
import java.util.Stack;

public class ThreeStackQueue<T> {
	int curFront = 0;
	int three = 3;
	HashMap<Integer, Stack<T>> queues = new HashMap<Integer, Stack<T>>();
	int curTail = 0;

	ThreeStackQueue() {
		init();
	}

	public void init() {
		for (int x = 0; x < 3; x++) {
			queues.put(x, new Stack<T>());
		}
		System.out.println("Stack Init Success");
	}

	public void enqueue(T item) {
		int index = curTail % three;
		queues.get(index).push(item);
		curTail++;
	}

	private Stack<T> flipStack(Stack<T> input) {
		Stack<T> returnValue = new Stack<T>();

		for (T t : input) {
			returnValue.push(t);
		}

		return returnValue;

	}

	public T dequeue() {
		T returnValue;

		returnValue = this.dequeue(curFront % three);

		curFront++;


		return returnValue;
	}

	private T dequeue(int index) {
		// flip selected stack
		Stack<T> tmpStack = new Stack<T>();
		// flip stacks
		T returnValue;
		while (!queues.get(index).isEmpty()) {
			tmpStack.push(queues.get(index).pop());

		}

		returnValue = tmpStack.pop();

		while (!tmpStack.isEmpty()) {
			queues.get(index).push(tmpStack.pop());
		}

		return returnValue;

	}

}
