package main;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigSqrt {

	private static BigDecimal digits;
	private static BigDecimal precision;

	/**
	 * Private utility method used to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision){
		BigDecimal fx = xn.pow(2).add(c.negate());
		BigDecimal fpx = xn.multiply(new BigDecimal(2));
		BigDecimal xn1 = fx.divide(fpx,2*digits.intValue(), RoundingMode.HALF_DOWN);
		xn1 = xn.add(xn1.negate());
		BigDecimal currentSquare = xn1.pow(2);
		BigDecimal currentPrecision = currentSquare.subtract(c);
		currentPrecision = currentPrecision.abs();
		if (currentPrecision.compareTo(precision) <= -1){
			return xn1;
		}
		return sqrtNewtonRaphson(c, xn1, precision);
	}

	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	public static BigDecimal bigSqrt(BigDecimal c){
		precision = new BigDecimal(10).pow(digits.intValue());
		return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(precision));
	}


	public static long run ( BigDecimal x ) {
		long startTime = System . nanoTime ();
		bigSqrt ( x );
		long endTime = System . nanoTime ();
		return endTime - startTime ;
	}

	
	/**
	 * @author Alexandre Rodrigues 54472
	 * @param args
	 */
	public static void main(String[] args) {

		int[] testDigits = new int[] {64, 128, 256, 512, 1024, 2048, 4096, 8192};
		BigDecimal x = new BigDecimal("2"); 
		
        for(int i=0; i<8; i++) {
        	digits=new BigDecimal(testDigits[i]);

        	long total = 0;
        	for(int j=0; j<100; j++) {
        		long temp = run(x);
        		total+=temp;
        		System.out.println(temp);
        	}
        	
        	System.out.println("Média: " + total/100);
        }

	}

}
