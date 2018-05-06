package board;

import game.ChessGame;
import pieces.ChessPiece;

import java.util.ArrayList;

/**
 * Creates, prints and changes the chess board
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public class ChessBoard
{
    private char [][] board = new char [8][8];
    public static final int NUM_TILES = 64;
    /**
     * Constructor for chess boards.
     * Assigns each space with an underscore
     */
    public ChessBoard() //Set the chess board spaces to _
    {
        for (int i=0; i <= 7; i++) {
            for (int j=0; j <= 7; j++) {
                board[i][j] = '_';
            }
        }
    }
    //getBoard() is not used
    /**
     * Sets the board to a symbol specified at location
     * @param row
     * @param col
     * @param chr
     */
    public void setBoard(int row, int col, char chr) {
        board[row][col] = chr;
    }
    /**
     * Prints the chess board along with the side numbers
     */
    public void printBoard() {
        System.out.print("\t\t\t\t  "); //formatting for numbers along the top edge
        for (int q=0; q <= 7; q++) {
            System.out.print(q + " ");
        }

        for (int i=0; i <= 7; i++) { //printing out the board
            System.out.print("\n\t\t\t\t" + i + " ");
            for (int j=0; j <= 7; j++) {
                System.out.print(board[i][j] + " ");
            }
        }
        System.out.println();
    }

    /**
     * Return true is a chess piece is located at the specified row
     * and column, or false if no piece exists at location.
     * NOTE: There are multiple methods of doing this,
     * 1. Check symbol at space for something other than an underscore
     * 2. Use the instanceOf() function to try and return a chess piece at location
     * 3. Use method getPieceAt() to acquire a piece's position (similar to using instanceOf())
     * @param row
     * @param col
     * @return boolean true if piece exists at location, false if piece does not exist at location
     */
    public boolean isPieceAt(int row, int col) {
        return board[row][col] != '_';
    }
    public boolean isPieceAt(ChessLocation loc) {
        return isPieceAt(loc.getRow(), loc.getCol());
    }

    /**
     * Obtains a list of all chess pieces and scans each location for the desired location
     * If there is a match, return the piece.
     * @param location
     * @return ChessPiece piece
     */
    public ChessPiece getPieceAt(ChessLocation location) {
        ArrayList<ChessPiece> pieces = ChessGame.getPieces();//Get the pieces in an array
        //Look through each piece
        for (ChessPiece piece : pieces) {
            if (piece.getLocation().getRow() == location.getRow() &&
                    piece.getLocation().getCol() == location.getCol()){
                return piece;
            }
        }
        return null;
    }

    /**
     * Obtains a list of all chess pieces and scans each location for the desired location
     * If there is a match, return the piece.
     * @param row
     * @param col
     * @return ChessPiece piece
     */
    public ChessPiece getPieceAt(int row, int col) {

        ArrayList<ChessPiece> pieces = ChessGame.getPieces();//Get the pieces in an array
        //Look through each piece
        for (ChessPiece piece : pieces) {
            if (piece.getLocation().getRow() == row &&
                    piece.getLocation().getCol() == col){
                return piece;
            }
        }
        return null;
    }

    /**
     * Places the given piece on board at given location.
     * If user attempts to add a piece where another exists,
     * that piece gets overwritten with the new one.
     * @param piece
     * @param location
     */
    public void placePieceAt(ChessPiece piece, ChessLocation location) {

        piece.setLocation(location);
        this.setBoard(location.getRow(), location.getCol(), piece.getID());
    }
    /**
     * Function that merely removes a piece from its location to be moved NOT OBLITERATE IT
     *
     * @param location
     */
    public void removePiece(ChessLocation location) {

        ChessPiece piece = this.getPieceAt(location);
        piece.setLocation(null);
        this.setBoard(location.getRow(), location.getCol(),'_');
    }

    public boolean isOnBoard(ChessLocation l) {
        return isOnBoard(l.getRow(), l.getCol());
    }
    public boolean isOnBoard(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    /**
     * Prints the state of the board around a location.
     * USED FOR DEBUGGING
     * @param row
     * @param col
     * @throws IndexOutOfBoundsException
     */
    public void _printAround (int row, int col) {
        try {

            System.out.println(board[row-1][col-1] + " " + board[row-1][col] + " "
                    + board[row-1][col+1]);
            System.out.println(board[row][col-1] + " " + board[row][col] + " "
                    + board[row][col+1]);
            System.out.println(board[row+1][col-1] + " " + board[row+1][col] + " "
                    + board[row+1][col+1]);

        } catch(IndexOutOfBoundsException e) {
            System.out.println("Something was out of bounds");
        }
    }
}
