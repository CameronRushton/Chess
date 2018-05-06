package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

import java.lang.Math;
/**
 * Outlines a Knight piece of a chess game
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public class Knight extends ChessPiece {
    /**
     * Constructor for objects of class Knight
     */
    public Knight(ChessPlayer player, ChessLocation initialLocation, ChessGame game) {
        super(player, initialLocation, game);
        if (player == ChessGame.getPlayer2()) {
            super.ID = 'n';
        } else {
            super.ID = 'N';
        }
        game.getBoard().setBoard(super.getLocation().getRow(), super.getLocation().getCol(), super.getID());

    }

    /**
     * check 'L' shapes (8 locations)
     * row-2 col-1
     * row-2 col+1
     * row-1 col-2
     * row-1 col+2
     * row+1 col+2
     * row+1 col-2
     * row+2 col+1
     * row+2 col-1
     */
    public void updatePossibleMoveLocations() {
        possibleMoveLocations.clear();
        int startRow = this.getLocation().getRow();
        int startCol = this.getLocation().getCol();
        int col = -1;
        int x = 1;

        for (int r = startRow-2; r <= startRow+2; r++) {
            if (r == startRow || col == startCol) continue;
            if (r == startRow+2)
                x = 1;
            for (int i = 1; i <= 2; i++) {
                col = startCol + x;
                if (isMoveValid(new ChessLocation(r, col))) {
                    possibleMoveLocations.add(new ChessLocation(r, col));
                }
                x *= -1;
            }
            x = 2;
        }

    }

    private boolean isMoveValid(ChessLocation destination) {
        int oldRow = super.getLocation().getRow();
        int oldCol = super.getLocation().getCol();

        int newRow = destination.getRow();
        int newCol = destination.getCol();

        int rowDiff = Math.abs(newRow - oldRow);
        int colDiff = Math.abs(newCol - oldCol);

        ChessPiece takenPiece = getGame().getBoard().getPieceAt(destination);
        if ((rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2)
                && newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
            try {
                if (!takenPiece.getPlayer().getName().equals(this.getPlayer().getName())) {
                    takenPiece.updateThreateningLocations(takenPiece.getLocation(), true, false);
                    return true;
                }
            } catch (NullPointerException e) {
                return true;
            }
        }

        //System.out.println("Move is invalid");
        return false;
    }

}
