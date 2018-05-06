package game;

import board.ChessBoard;
import board.ChessLocation;
import pieces.*;

import java.util.ArrayList;
/**
 * Creates a new chess board and places pieces on the board
 *
 * @author Cameron Rushton 101002958
 * @date of submission Oct 9, 2016
 */
public class ChessGame
{
    private static ChessBoard board;
    private static ChessPlayer player1, player2;
    private static ArrayList<ChessPiece> pieces;
    /**
     * Constructor for objects of class ChessGame
     */
    public ChessGame(String player_1, String player_2) {

        //Initialize board by calling the ChessBoard constructor
        board = new ChessBoard();
        pieces = new ArrayList<>();
        //Initialize the players
        player1 = new ChessPlayer(player_1);
        player2 = new ChessPlayer(player_2);
        //Initialize the pieces and add them to list
        Rook B_Rook1 = new Rook(player1, new ChessLocation(0,0), this); pieces.add(B_Rook1);
        Knight B_Knight1 = new Knight(player1, new ChessLocation(0,1), this); pieces.add(B_Knight1);
        Bishop B_Bishop1 = new Bishop(player1, new ChessLocation(0,2), this); pieces.add(B_Bishop1);
        Queen B_Queen = new Queen(player1, new ChessLocation(0,3), this); pieces.add(B_Queen);
        King B_King = new King(player1, new ChessLocation(0,4), this); pieces.add(B_King);
        Bishop B_Bishop2 = new Bishop(player1, new ChessLocation(0,5), this); pieces.add(B_Bishop2);
        Knight B_Knight2 = new Knight(player1, new ChessLocation(0,6), this); pieces.add(B_Knight2);
        Rook B_Rook2 = new Rook(player1, new ChessLocation(0,7), this); pieces.add(B_Rook2);
        Pawn B_Pawn1 = new Pawn(player1, new ChessLocation(1,0), this); pieces.add(B_Pawn1);
        Pawn B_Pawn2 = new Pawn(player1, new ChessLocation(1,1), this); pieces.add(B_Pawn2);
        Pawn B_Pawn3 = new Pawn(player1, new ChessLocation(1,2), this); pieces.add(B_Pawn3);
        Pawn B_Pawn4 = new Pawn(player1, new ChessLocation(1,3), this); pieces.add(B_Pawn4);
        Pawn B_Pawn5 = new Pawn(player1, new ChessLocation(1,4), this); pieces.add(B_Pawn5);
        Pawn B_Pawn6 = new Pawn(player1, new ChessLocation(1,5), this); pieces.add(B_Pawn6);
        Pawn B_Pawn7 = new Pawn(player1, new ChessLocation(1,6), this); pieces.add(B_Pawn7);
        Pawn B_Pawn8 = new Pawn(player1, new ChessLocation(1,7), this); pieces.add(B_Pawn8);

        Pawn W_Pawn1 = new Pawn(player2, new ChessLocation(6,0), this); pieces.add(W_Pawn1);
        Pawn W_Pawn2 = new Pawn(player2, new ChessLocation(6,1), this); pieces.add(W_Pawn2);
        Pawn W_Pawn3 = new Pawn(player2, new ChessLocation(6,2), this); pieces.add(W_Pawn3);
        Pawn W_Pawn4 = new Pawn(player2, new ChessLocation(6,3), this); pieces.add(W_Pawn4);
        Pawn W_Pawn5 = new Pawn(player2, new ChessLocation(6,4), this); pieces.add(W_Pawn5);
        Pawn W_Pawn6 = new Pawn(player2, new ChessLocation(6,5), this); pieces.add(W_Pawn6);
        Pawn W_Pawn7 = new Pawn(player2, new ChessLocation(6,6), this); pieces.add(W_Pawn7);
        Pawn W_Pawn8 = new Pawn(player2, new ChessLocation(6,7), this); pieces.add(W_Pawn8);
        Rook W_Rook1 = new Rook(player2, new ChessLocation(7,0), this); pieces.add(W_Rook1);
        Knight W_Knight1 = new Knight(player2, new ChessLocation(7,1), this); pieces.add(W_Knight1);
        Bishop W_Bishop1 = new Bishop(player2, new ChessLocation(7,2), this); pieces.add(W_Bishop1);
        Queen W_Queen = new Queen(player2, new ChessLocation(7,3), this); pieces.add(W_Queen);
        King W_King = new King(player2, new ChessLocation(7,4), this); pieces.add(W_King);
        Bishop W_Bishop2 = new Bishop(player2, new ChessLocation(7,5), this); pieces.add(W_Bishop2);
        Knight W_Knight2 = new Knight(player2, new ChessLocation(7,6), this); pieces.add(W_Knight2);
        Rook W_Rook2 = new Rook(player2, new ChessLocation(7,7), this); pieces.add(W_Rook2);

        for (ChessPiece piece : pieces) {
            piece.updatePossibleMoveLocations();
        }
    }

    public static ChessPlayer getPlayer1() {
        return player1;
    }

    public static ChessPlayer getPlayer2() {
        return player2;
    }

    public static ChessBoard getBoard() {
        return board;
    }

    public static ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    public static void removeListPiece(ChessPiece piece) {
        pieces.remove(piece);
    }

    public static void addListPiece(ChessPiece piece) {
        pieces.add(piece);
    }
}

