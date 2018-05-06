package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

/**
 * Outlines a bishop piece in a chess game.
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public class Bishop extends ChessPiece {

    /**
     * Constructor for objects of class Bishop
     * @param player, initialLocation, game
     */
    public Bishop(ChessPlayer player, ChessLocation initialLocation, ChessGame game)
    {
        super(player, initialLocation, game); //Constructor is set here
        if (player == ChessGame.getPlayer2()) {
            super.ID = 'b';
        } else {
            super.ID = 'B';
        }
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());

    }

    /**
     * Updates possible move locations by checking each diagonal direction for squares it can move.
     */
    @Override
    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        /*
        check line of sight to maximum distance in each of the 4 directions, if no piece location was returned, add all locations to the list.
        otherwise, add all locations up to that piece and including only if it is a piece that can be taken.
         */
        int startRow = super.getLocation().getRow();
        int startCol = super.getLocation().getCol();
        int row = startRow;
        int col = startCol;
        //look to top left
        while (col > 0 && row > 0) { //advance to farthest square possible NW
            row--;
            col--;
        }
        ChessPiece pieceFound = super.checkLineOfSight(new ChessLocation(startRow, startCol), new ChessLocation(row, col)); //Check for blocking piece
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                row = pieceFound.getLocation().getRow();
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                row = pieceFound.getLocation().getRow() + 1;
                col = pieceFound.getLocation().getCol() + 1;
            }
        }
        while(row < startRow && col < startCol) { //The plus one is to not add the location farthest away due to above
            possibleMoveLocations.add(new ChessLocation(row, col)); //add rest of the squares to pool from farthest to start location
            row++;
            col++;
        }

        //look to top right
        row = startRow;
        col = startCol;
        while (col < 7 && row > 0) {
            row--;
            col++;
        }
        pieceFound = super.checkLineOfSight(new ChessLocation(startRow, startCol), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                row = pieceFound.getLocation().getRow();
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                row = pieceFound.getLocation().getRow() + 1;
                col = pieceFound.getLocation().getCol() - 1;
            }
        }
        while(row < startRow && col > startCol) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            row++;
            col--;
        }

        //look bottom left
        row = startRow;
        col = startCol;
        while (col > 0 && row < 7) {
            row++;
            col--;
        }
        pieceFound = super.checkLineOfSight(new ChessLocation(startRow, startCol), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                row = pieceFound.getLocation().getRow();
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                row = pieceFound.getLocation().getRow() - 1;
                col = pieceFound.getLocation().getCol() + 1;
            }
        }
        while(row > startRow && col < startCol) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            row--;
            col++;
        }

        //look bottom right
        row = startRow;
        col = startCol;
        while (col < 7 && row < 7) {
            row++;
            col++;
        }
        pieceFound = super.checkLineOfSight(new ChessLocation(startRow, startCol), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                row = pieceFound.getLocation().getRow();
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                row = pieceFound.getLocation().getRow() - 1;
                col = pieceFound.getLocation().getCol() - 1;
            }
        }
        while(row > startRow && col > startCol) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            row--;
            col--;
        }

    }

}
