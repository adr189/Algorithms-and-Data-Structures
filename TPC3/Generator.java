package main;

public interface Generator <E> {
	public void reset ();

	// @pre : generator not at first value
	public E prev ();

	public E next ();
}

