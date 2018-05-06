package board;

/**
 * Framework for a chess location point
 *
 * @author Cameron Rushton
 * @date of submission Oct 9, 2016
 */
public class ChessLocation
{
    private int row, col;
    private int locationNum;
    /**
     * Constructor for objects of class ChessLocation
     */
    public ChessLocation(int row, int col)
    {
        if (row > 7 || row < 0 || col > 7 || col < 0) {
            //System.out.println("Error in ChessLocation: Location out of range");

            /*StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StackTraceElement element = stackTrace[2];
            System.out.println("Called by method " + element.getMethodName());
            System.out.println("Line: " + element.getLineNumber());
            System.out.println("Class: " + element.getClassName());
            System.out.println("Invalid coords: " + row + "," + col);*/
            //System.exit(1);
        } else {
            this.row = row;
            this.col = col;
            this.locationNum = (row+1) + (7 * row) + col;
        }
    }
    public boolean equals(ChessLocation cp) {
        return cp.getRow() == this.row && cp.getCol() == this.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLocationNum() {
        return this.locationNum;
    }
}
