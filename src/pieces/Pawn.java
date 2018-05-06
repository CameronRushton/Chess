package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

/**
 * Outlines the behaviour of a pawn in the game of chess.
 *
 * @author Cameron Rushton
 * @version November 5, 2016
 */
public class Pawn extends ChessPiece  {

    private boolean firstMove;
    /**
     * Constructor for objects of class Pawn
     * Takes in which player the pawn belongs to, its initialLocation and
     * which chess game the piece belongs to.
     * @param player, initialLocation, game
     */
    public Pawn(ChessPlayer player, ChessLocation initialLocation, ChessGame game) {
        super(player, initialLocation, game);
        if (player == ChessGame.getPlayer2()) {
            super.ID = 'p';
        } else {
            super.ID = 'P';
        }
        firstMove = true;
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());
    }

    /**
     * 8 possible destinations for a pawn (Three in front and one that's two in front on first move and same with other direction).
     * row-1 col
     * row-1 col-1
     * row-1 col+1
     * row-2 col
     *
     * row+1 col
     * row+1 col-1
     * row+1 col+1
     * row+2 col
     */
    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        int startRow = super.getLocation().getRow();
        int startCol = super.getLocation().getCol();
        ChessPiece pieceFound;
        boolean onBoard;

        if (this.getPlayer() == getGame().getPlayer1()) {
            //black pieces (player 1 - top of board)
            //Check for possible movement down
            onBoard = getGame().getBoard().isOnBoard(startRow+2, startCol);
            pieceFound = super.checkLineOfSight(super.getLocation(), new ChessLocation(startRow + 2, startCol));
            if (firstMove && pieceFound == null && onBoard) {
                possibleMoveLocations.add(new ChessLocation(startRow + 2, startCol));
            }
            onBoard = getGame().getBoard().isOnBoard(startRow+1, startCol);
            pieceFound = super.checkLineOfSight(super.getLocation(), new ChessLocation(startRow + 1, startCol));
            if (pieceFound == null && onBoard) {
                possibleMoveLocations.add(new ChessLocation(startRow + 1, startCol));
            }
            //check for possible enemies
            onBoard = getGame().getBoard().isOnBoard(startRow+1, startCol-1);
            if (onBoard && super.getGame().getBoard().isPieceAt(startRow + 1, startCol - 1)) {
                pieceFound = super.getGame().getBoard().getPieceAt(startRow + 1, startCol - 1);
                if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                    possibleMoveLocations.add(new ChessLocation(startRow + 1, startCol - 1));
                    pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);
                }
            }
            onBoard = getGame().getBoard().isOnBoard(startRow+1, startCol+1);
            if (onBoard && super.getGame().getBoard().isPieceAt(startRow + 1, startCol + 1)) {
                pieceFound = super.getGame().getBoard().getPieceAt(startRow + 1, startCol + 1);
                if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                    possibleMoveLocations.add(new ChessLocation(startRow + 1, startCol + 1));
                    pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);
                }
            }

        } else {
            //white pieces
            //Check for possible movement up
            onBoard = getGame().getBoard().isOnBoard(startRow-2, startCol);
            pieceFound = super.checkLineOfSight(super.getLocation(), new ChessLocation(startRow - 2, startCol));
            if (onBoard && firstMove && pieceFound == null) {
                possibleMoveLocations.add(new ChessLocation(startRow - 2, startCol));
            }
            onBoard = getGame().getBoard().isOnBoard(startRow-1, startCol);
            pieceFound = super.checkLineOfSight(super.getLocation(), new ChessLocation(startRow - 1, startCol));
            if (onBoard && pieceFound == null) {
                possibleMoveLocations.add(new ChessLocation(startRow - 1, startCol));
            }
            //check for possible enemies
            onBoard = getGame().getBoard().isOnBoard(startRow-1, startCol-1);
            if (onBoard && super.getGame().getBoard().isPieceAt(startRow - 1, startCol - 1)) {
                pieceFound = super.getGame().getBoard().getPieceAt(startRow - 1, startCol - 1);
                if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                    possibleMoveLocations.add(new ChessLocation(startRow - 1, startCol - 1));
                    pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);
                }
            }
            onBoard = getGame().getBoard().isOnBoard(startRow-1, startCol+1);
            if (onBoard && super.getGame().getBoard().isPieceAt(startRow - 1, startCol + 1)) {
                pieceFound = super.getGame().getBoard().getPieceAt(startRow - 1, startCol + 1);
                if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName())) {
                    possibleMoveLocations.add(new ChessLocation(startRow - 1, startCol + 1));
                    pieceFound.updateThreateningLocations(pieceFound.getLocation(), true, false);
                }
            }
        }
    }

    public boolean moveTo(ChessLocation l) {
        firstMove = false;

        if (this.getID() == 'p' && l.getRow() == 0
                || this.getID() == 'P' && l.getRow() == 7) {

            ChessLocation oldLocation = getLocation();
            //remove itself from the master list of pieces and from the board
            super.getGame().getBoard().removePiece(this.getLocation());
            super.getGame().removeListPiece(this);
            //Create and add a new queen to the master list
            Queen q = new Queen(this.getPlayer(), l, getGame());
            getGame().addListPiece(q);
            //update the queen's data and surrounding pieces' data
            q.updatePossibleMoveLocations();
            q.updateThreateningLocations(oldLocation, false, true);
            q.updateThreateningLocations(l, true, true);
            //print board
            getGame().getBoard().printBoard();

            return true;
        } else {
            return super.moveTo(l);
        }
    }

}
