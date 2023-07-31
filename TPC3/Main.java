package main;

/**
 * 
 * @author Alexandre Rodrigues 54472
 *
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FibonacciGenerator fg = new FibonacciGenerator ();

		for(int i =0; i <5; i ++)
			fg.next ();

		for(int i =0; i <4; i ++)
			System . out . printf ("%d ", fg.prev ());

		for(int i =0; i <8; i ++)
			System . out . printf ("%d ", fg.next ());

		fg.reset ();

		for(int i =0; i <5; i ++)
			System . out . printf ("%d ", fg.next ());

	}

}
