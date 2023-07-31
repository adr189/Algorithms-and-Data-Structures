package trabalho2;

// At Eclipse Oxygen it gave me an access problem:
//    "Access restriction: The type 'ImageIO' is not API"

// The solution is to change the access restrictions. Go to the properties of your Java project. 
// There, go to "Java Build Path", tab "Libraries". 
// There, expand the library entry, select "Access rules", "Edit..." and "Add..." a 
// "Resolution: Accessible" with a corresponding rule pattern. For me was "javax/**"
// ref: https://stackoverflow.com/questions/25222811
/**
 * 
 * @author Alexandre Rodrigues 54472
 *
 */
public class Trabalho2 {
	
    // directions:                 n, ne,  e, se,  s, sw,  w, nw
    private static int[] xDirs = { 0,  1,  1,  1,  0, -1, -1, -1};
    private static int[] yDirs = {-1, -1,  0,  1,  1,  1,  0, -1};
    
    // ref: https://introcs.cs.princeton.edu/java/23recursion/
    public static void sierpinskiSquare(double x0, double y0, double size, int iter) {
    	if (iter==0)
    		return;
    	
    	StdDraw.filledSquare(x0, y0, size);
    	
    	for(int i=0; i<8; i++)
    		// recursively draw smaller squares (a third of the size) in all 8 directions
    		sierpinskiSquare(x0+xDirs[i]*(2*size), y0+yDirs[i]*(2*size), size/3, iter-1);
    }
    
    public static void kochCircle(double x0, double y0, double size, int iter ) {
    	if (iter==0)
    		return;
    	
    	StdDraw.circle(x0, y0, size);
    	
    	for(int i=0; i<8; i+=2)
    		kochCircle(x0+xDirs[i]*(size/2), y0+yDirs[i]*(size/2), size/3, iter-1);
    }
    
    public static void planeTree(double x0, double y0, double length, int iter) {
    	if (iter==0)
    		return;
    	
    	StdDraw.line(x0 + xDirs[6]*length, y0, x0 + xDirs[2]*length, y0);
    	StdDraw.line(x0 + xDirs[6]*length, y0 + yDirs[0]*length , x0 + xDirs[6]*length, y0 + yDirs[4]*length);
    	StdDraw.line(x0 + xDirs[2]*length, y0 + yDirs[0]*length , x0 + xDirs[2]*length, y0 + yDirs[4]*length);
    	
    	for(int i=1; i<8; i+=2)
    		planeTree(x0+xDirs[i]*(length), y0+yDirs[i]*(length), length/3, iter-1);
    }
    
    //////////////////////////////////////////////
 
    public static void main(String[] args) {
    	
        double windowSize = 128;
    	
        // class StdDraw @ introcs.cs.princeton.edu/java/stdlib/StdDraw.java
        StdDraw.setXscale(0, windowSize);
        StdDraw.setYscale(0, windowSize);
        StdDraw.clear(StdDraw.WHITE);  
        StdDraw.setPenColor(StdDraw.BLACK);
               
        //sierpinskiSquare(windowSize/2, windowSize/2, windowSize/5, 5);
        //kochCircle(windowSize/2, windowSize/2, windowSize/5, 5);
        planeTree(windowSize/2, windowSize/2, windowSize/5, 5);
    }
}