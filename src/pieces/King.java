package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

import java.lang.Math;
/**
 * Outlines a King piece of a chess game
 *
 * @author Cameron Rushton
 * @version November 5, 2016
 */
public class King extends ChessPiece {

    /**
     * Constructor for objects of class King
     * @param player, initialLocation, game
     */
    public King(ChessPlayer player, ChessLocation initialLocation, ChessGame game) {
        super(player, initialLocation, game);
        if (player == ChessGame.getPlayer2()) {
            super.setID('k');
        } else {
            super.setID('K');
        }
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());

    }

    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        //Look around the piece, if there's no piece there or it's an enemy piece, it can move
        int row = this.getLocation().getRow(); //King's location
        int col = this.getLocation().getCol();
        ChessPiece piece;

        for (int r = row-1; r <= row+1; r++) {
            for (int c = col-1; c <= col+1; c++) {
                //Dont check the square the king is currently on & don't check outside of bounds
                if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && !doesLocationMatch(row, col, r, c)) {
                    piece = super.getGame().getBoard().getPieceAt(r, c);
                    if (piece == null) {
                        super.possibleMoveLocations.add(new ChessLocation(r, c));
                    } else if (!piece.getPlayer().getName().equals(this.getPlayer().getName())) {
                        super.possibleMoveLocations.add(new ChessLocation(r, c));
                        piece.updateThreateningLocations(piece.getLocation(), true, false);
                    }

                }
            }
        }


    }
    /**
     * update the threatening locations of all the opponent's pieces. For this you have to retrieve
     * chessBoard object from your instance, "game" variable and go through all the opponent's
     * pieces and update the threatening locations (array)
     * Returns the ChessPiece which may threaten the king if the king is at the destination location.
     * else, return nulls
     *
     public ChessPiece locationInDanger(ChessLocation destination) {


     }
     /**
     * Returns true if the king has any moves left which are not being threatened. (make use
     * of locationInDanger function). returns false if the king can not move without being threatened.
     *
     public boolean anyMovesLeft() {


     }
     /**
     * Returns the ChessPiece which is threatening the king at the position. (Call locationInDanger
     * with the destination location at the parameter). If the king is not being threatened. return null.
     *
     public ChessPiece check() {


     }*/
}
