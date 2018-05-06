package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

/**
 * Outlines the Queen piece of a chess game.
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public class Queen extends ChessPiece {

    /**
     * Constructor for objects of class Queen
     * @param player, initialLocation, game
     */
    public Queen(ChessPlayer player, ChessLocation initialLocation, ChessGame game) {
        super(player, initialLocation, game);
        if (player == ChessGame.getPlayer2()) {
            super.ID = 'q';
        } else {
            super.ID = 'Q';
        }
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());

    }

    @Override
    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        //Bishop code
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

        //Rook code
        //look down
        row = 7;
        col = startCol;
        pieceFound = super.checkLineOfSight(getLocation(), new ChessLocation(row, col));
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
