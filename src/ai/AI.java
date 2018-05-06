package ai;

import game.ChessGame;
import pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Random;

/**
 * Iteration 1 of this ai will move pieces at random to valid locations while prioritising taking enemy pieces.
 */
public class AI {

    private ChessGame game;
    /**
     * Default constructor
     */
    public AI(ChessGame newGame) {
        this.game = newGame;
    }

    public boolean makeMove() {
        pickPiece();
        lookForThreats();
        //movePiece();
        return true;
    }

    /**
     * Look at the board and determine who is in the best position
     * @return
     */
    private int analyzeBoard() {

        return 0;
    }

    private int analyzeMyBoard() {

        //TODO: Make these properties of each piece an then piece.getValue()
        final int KING_VALUE = 900;
        final int QUEEN_VALUE = 90;
        final int KNIGHT_VALUE = 30;
        final int BISHOP_VALUE = 30;
        final int ROOK_VALUE = 50;
        final int PAWN_VALUE = 10;

        int myPiecesTotalValue = 0;

        ArrayList<ChessPiece> myPieces = new ArrayList<>();
        for (ChessPiece piece : game.getPieces()) {
            if (piece.getPlayer().getName().equals("Player 2")) {
                myPieces.add(piece);
            }
        }


        return 0;
    }

    public void pickPiece() {
        Random r = new Random();
        int choice = r.nextInt(15); //0 is included
    }
    public void lookForThreats() {
        //for each piece, updateThreateningLocations
    }
    public void movePiece(ChessPiece piece) {

    }

}
