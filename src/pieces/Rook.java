package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

/**
 * Write a description of class Rook here.
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public class Rook extends ChessPiece {

    /**
     * Constructor for objects of class Rook
     * @param player, initialLocation, game
     */
    public Rook(ChessPlayer player, ChessLocation initialLocation, ChessGame game) {

        super(player, initialLocation, game);
        if (player == ChessGame.getPlayer2()) {
            super.ID = 'r';
        } else {
            super.ID = 'R';
        }
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());

    }

    @Override
    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        int startRow = super.getLocation().getRow();
        int startCol = super.getLocation().getCol();
        //look down
        int row;
        int col;

        row = 7;
        col = startCol;
        ChessPiece pieceFound = super.checkLineOfSight(getLocation(), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                row = pieceFound.getLocation().getRow();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                row = pieceFound.getLocation().getRow() - 1; //The minus 1 is to be sure to not add the square the friendly piece is on
            }
        }
        while (row > startRow) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            row--;
        }

        //look up
        row = 0;
        col = startCol;
        pieceFound = super.checkLineOfSight(getLocation(), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) { //add all spaces from piece to piece found inclusive
                row = pieceFound.getLocation().getRow();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else { //add all spaces from piece to piece found excluding piece found
                row = pieceFound.getLocation().getRow() + 1;
            }
        }
        while (row < startRow) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            row++;

        }
        //look left
        row = startRow;
        col = 0;
        pieceFound = super.checkLineOfSight(getLocation(), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                col = pieceFound.getLocation().getCol() + 1;
            }
        }
        while (col < startCol) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            col++;

        }
        //look right
        row = startRow;
        col = 7;
        pieceFound = super.checkLineOfSight(getLocation(), new ChessLocation(row, col));
        if (pieceFound != null) {
            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                col = pieceFound.getLocation().getCol();
                pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);

            } else {
                col = pieceFound.getLocation().getCol() - 1;
            }
        }
        while (col > startCol) {
            possibleMoveLocations.add(new ChessLocation(row, col));
            col--;
        }

    }

}
