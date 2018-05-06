package pieces;

import board.ChessLocation;
import game.ChessGame;
import game.ChessPlayer;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Abstract class that outlines common fields for all chess.
 *
 * @author Cameron Rushton
 * @version April 8, 2018
 */
public abstract class ChessPiece implements ChessPieceInterface
{
    private ChessGame game;
    private ChessPlayer player;
    private ChessLocation location;
    char ID;
    boolean updated;
    private HashSet<ChessLocation> threateningLocations; //A list of locations currently threatening the piece
    protected HashSet<ChessLocation> possibleMoveLocations;

    /**
     * Constructor for chess piece objects
     * @param owner ChessPlayer
     * @param initialLocation ChessLocation
     * @param game ChessGame
     */
    public ChessPiece(ChessPlayer owner, ChessLocation initialLocation, ChessGame game) {

        player = owner;
        location = initialLocation;
        this.game = game;
        updated = true;
        threateningLocations = new HashSet<>();
        possibleMoveLocations = new HashSet<>();
        game.getBoard().placePieceAt(this, initialLocation);

    }

    public abstract void updatePossibleMoveLocations();

    /**
     * Given a location, determine all the locations threatening it
     *
     * Using this method to determine which pieces need to have their possible move locations updated.
     * To do this, look for all pieces regardless of player, that could threaten a piece (in its move space) and
     * only update the list of threatening locations if the piece found is not friendly. Otherwise, send the list
     * of all pieces found to have their possible move locations updated.
     *
     * @param currentLocation
     */
    void updateThreateningLocations(ChessLocation currentLocation, boolean logThreats, boolean updatePieces) {

        threateningLocations.clear();
        int row, col; //multipurpose row/col

        int startRow = currentLocation.getRow();
        int startCol = currentLocation.getCol();
        ChessPiece pieceFound;
        HashSet<ChessPiece> piecesToUpdate = new HashSet<>();

        //Check Right
        ChessLocation location = new ChessLocation(startRow, 7);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Rook || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats)  //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Left
        location = new ChessLocation(startRow, 0);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Rook || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Up
        location = new ChessLocation(0, startCol);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Rook || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Down
        location = new ChessLocation(7, startCol);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Rook || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Diagonally NE
        row = startRow; //reset
        col = startCol;
        while (row > 0 && col < 7) {
            row -= 1;
            col += 1;
        }
        location = new ChessLocation(row, col);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Bishop || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Diagonally NW
        row = startRow; //reset
        col = startCol;
        while (row > 0 && col > 0) {
            row -= 1;
            col -= 1;
        }
        location = new ChessLocation(row, col);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Bishop || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Diagonally SE
        row = startRow; //reset
        col = startCol;
        while (row < 7 && col < 7) {
            row += 1;
            col += 1;
        }
        location = new ChessLocation(row, col);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Bishop || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats)  //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check Diagonally SW
        row = startRow; //reset
        col = startCol;
        while (row < 7 && col > 0) {
            row += 1;
            col -= 1;
        }
        location = new ChessLocation(row, col);
        pieceFound = checkLineOfSight(currentLocation, location);
        if (pieceFound != null) { //if there is a piece in the way
            if (pieceFound instanceof Bishop || pieceFound instanceof Queen) {
                piecesToUpdate.add(pieceFound);
                if (!this.getPlayer().getName().equals(pieceFound.getPlayer().getName()) && logThreats) //if its an enemy piece found
                    threateningLocations.add(pieceFound.getLocation());
            }
        }
        //Check 1 space around for a king and enemy pawns
        for (int i = -1; i <= 1; i++) {
            row = startRow;
            row += i;
            for (int j = -1; j <= 1; j++) {
                col = startCol;
                col += j;
                if (row <= 7 && row >= 0 && col >= 0 && col <= 7) { //if within the board...
                    location = new ChessLocation(row, col);
                    if (game.getBoard().isPieceAt(row, col)) {
                        pieceFound = game.getBoard().getPieceAt(location);

                        if (pieceFound instanceof King) {
                            piecesToUpdate.add(pieceFound);
                            if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName()) && logThreats)
                                threateningLocations.add(pieceFound.getLocation());
                        }
                        if (row != startRow && col != startCol) {
                            if (this.getPlayer().getName().equals("Player 1")) { //Check for pawns coming up (bottom corner squares)
                                if (row == startRow + 1 && col == startCol + 1 || row == startRow + 1 && col == startCol - 1) { //Check for pawns
                                    if (pieceFound instanceof Pawn) {
                                        piecesToUpdate.add(pieceFound);
                                        if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName()) && logThreats)
                                            threateningLocations.add(pieceFound.getLocation());
                                    }
                                }
                            } else if (this.getPlayer().getName().equals("Player 2")) {
                                if (row == startRow - 1 && col == startCol + 1 || row == startRow - 1 && col == startCol - 1) { //Check for pawns
                                    if (pieceFound instanceof Pawn) {
                                        piecesToUpdate.add(pieceFound);
                                        if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName()) && logThreats)
                                            threateningLocations.add(pieceFound.getLocation());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Check for Knights
        for (int i = 1; i <= 8; i++) {
            switch (i) {
                case 1:
                    row = startRow + 1;
                    col = startCol - 2;
                    break;
                case 2:
                    row = startRow - 1;
                    col = startCol - 2;
                    break;
                case 3:
                    row = startRow + 2;
                    col = startCol - 1;
                    break;
                case 4:
                    row = startRow - 2;
                    col = startCol - 1;
                    break;
                case 5:
                    row = startRow + 2;
                    col = startCol + 1;
                    break;
                case 6:
                    row = startRow - 2;
                    col = startCol + 1;
                    break;
                case 7:
                    row = startRow + 1;
                    col = startCol + 2;
                    break;
                case 8:
                    row = startRow - 1;
                    col = startCol + 2;
                    break;
            }
            if (row <= 7 && row >= 0 && col >= 0 && col <= 7) {
                location = new ChessLocation(row, col);
                if (game.getBoard().isPieceAt(row, col)) {
                    pieceFound = game.getBoard().getPieceAt(location);
                    if (pieceFound instanceof Knight) {
                        piecesToUpdate.add(pieceFound);
                        if (!pieceFound.getPlayer().getName().equals(this.getPlayer().getName()) && logThreats)
                            threateningLocations.add(pieceFound.getLocation());
                    }
                }
            }
        }
        //Add pieces that have moves in the space the piece moved to, to the update list
        for (ChessPiece p : game.getPieces()) {
            for (ChessLocation loc : p.getMoves()) {
                if (doesLocationMatch(loc, this.getLocation())) {
                    piecesToUpdate.add(p);
                    break;
                }
            }
        }

        if (updatePieces) {
            for (ChessPiece piece : piecesToUpdate) { //updates other pieces locations after a piece moves
                piece.updatePossibleMoveLocations();
                System.out.println("\nUpdated Piece:\n" + piece.toString());
            }
        }

    }

    /**
     * Moves a piece to its new location, removes the old piece, and prints the board.
     * @param newLocation ChessLocation
     */
    public boolean moveTo(ChessLocation newLocation) {

        ChessLocation oldLocation = getLocation();
        //Simply writing possibleMoveLocations.contains(newLocation) will compare instances, which is likely to always be false
        //Instead, compare values.
        boolean match = false;
        for (ChessLocation l : possibleMoveLocations) {
            if (doesLocationMatch(l, newLocation)) {
                match = true;
                break;
            }
        }

        if (match) {

            ChessPiece takenPiece = game.getBoard().getPieceAt(newLocation);
            //Perform the move
            //Check if another piece is being taken
            if (takenPiece != null && !takenPiece.getPlayer().getName().equals(this.getPlayer().getName())) {
                game.removeListPiece(takenPiece); //Remove piece from the master list
                game.getBoard().removePiece(newLocation); //Remove piece from the board
            }
            game.getBoard().removePiece(getLocation());
            game.getBoard().placePieceAt(this, newLocation);

            System.out.println();
            game.getBoard().printBoard();

            for (ChessPiece p : game.getPieces()) { //event happened; all pieces are eligible for an update
                p.setUpdated(false);
            }
            updatePossibleMoveLocations(); //updates current piece's locations
            updateThreateningLocations(oldLocation, false, true);
            updateThreateningLocations(getLocation(), true, true);

            return true;
        }
        return false;

    }

    protected boolean doesLocationMatch(ChessLocation loc1, ChessLocation loc2) {
        return loc1.getRow() == loc2.getRow() && loc1.getCol() == loc2.getCol();
    }
    protected boolean doesLocationMatch(int row1, int col1, int row2, int col2) {
        return row1 == row2 && col1 == col2;
    }

    /**
     * Determines which direction the move is to be in and if it is a valid lateral move or diagonal move.
     * Once determined valid, check each space between the starting location to the end for pieces.
     * Returns true if piece is found blocking path.
     * @param start ChessLocation
     * @param end ChessLocation
     * @return true if blocked, false if clear
     */
    ChessPiece checkLineOfSight(ChessLocation start, ChessLocation end) {
        //Check for pieces between the points
        /* OVERVIEW OF LOGIC/PROGRAMMER'S NOTE:
         * It is a diagonal move if abs(rowStart - rowEnd) = abs(colStart - colEnd)
         * Slope is -1 when, colStart - colEnd && rowStart - rowEnd = -ve (to down right) or +ve (to up left) (when they are the same sign)
         * Slope is 1 when, colStart - colEnd = -ve && rowStart - rowEnd = +ve (to up right)
         * other way around is to down left.
         * abs(rowStart - rowEnd) (or using col) = # of spaces to move (iff no pieces were found or its moving
         * ...into an enemy piece. If it moves into its own piece, - 1 from the number. If # of spaces to move
         * ...is suddenly 0, print invalid move error.
         */
        ChessPiece piece = null;
        int startRow = start.getRow(), startCol = start.getCol(), endRow = end.getRow(), endCol = end.getCol();
        int spacesToMove = Math.abs(startRow - endRow);
        int colDiff = startCol - endCol, rowDiff = startRow - endRow;
        if (spacesToMove == Math.abs(startCol - endCol)) { //Diagonal
            //Determine vector
            if (colDiff > 0 && rowDiff > 0) { //points up left
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow - i, startCol - i);
                    if (piece != null) return piece;
                }
            } else if (colDiff < 0 && rowDiff < 0) { //to down right
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow + i, startCol + i);
                    if (piece != null) return piece;
                }
            } else if (colDiff < 0 && rowDiff > 0) { //to up right
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow - i, startCol + i);
                    if (piece != null) return piece;
                }
            } else { //to down left
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow + i, startCol - i);
                    if (piece != null) return piece;
                }
            }
        } else { //lateral
            spacesToMove = Math.abs(colDiff + rowDiff);
            if (colDiff < 0) { //going right
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow, startCol + i);
                    if (piece != null) return piece;
                }
            } else if (colDiff > 0) { //going left
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow, startCol - i);
                    if (piece != null) return piece;
                }
            } else if (rowDiff < 0) { //going down
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow + i, startCol);
                    if (piece != null) return piece;
                }
            } else { //going up
                for (int i = 1; i <= spacesToMove; i++) {
                    piece = game.getBoard().getPieceAt(startRow - i, startCol);
                    if (piece != null) return piece;
                }
            }
        }
        return piece;
    }

    /**
     * Get the game object
     * @return ChessGame
     */
    ChessGame getGame() {
        return game;

    }

    public void setPlayer(ChessPlayer playerChoice) {
        player = playerChoice;

    }

    public ChessPlayer getPlayer() {

        return player;

    }

    public void setLocation(ChessLocation newLocation) {
        location = newLocation;

    }

    public ChessLocation getLocation() {
        return location;

    }

    void setID(char setID) {
        ID = setID;

    }

    public char getID() {
        return ID;

    }

    public HashSet<ChessLocation> getThreateningLocations() {
        return threateningLocations;
    }

    public HashSet<ChessLocation> getMoves() {
        return possibleMoveLocations;
    }

    public void setUpdated (boolean update) {
        updated = update;
    }
    public boolean isUpdated () {
        return updated;
    }

    @Override
    public String toString() {
        StringBuilder moves = new StringBuilder();
        for (ChessLocation l : this.getMoves()) {
            moves.append(l.getRow() + "," + l.getCol() + " ");
        }
        StringBuilder threats = new StringBuilder();
        for (ChessLocation loc : this.getThreateningLocations()) {
            threats.append(loc.getRow() + "," + loc.getCol() + " ");
        }
        return "Piece: " + this.getClass() + "\n" +
                "Player: " + this.getPlayer().getName() + "\n" +
                "Location: " + this.getLocation().getRow() + "," + this.getLocation().getCol() + "\n" +
                "Possible moves: " + moves + "\n" +
                "Threats: " + threats;

    }
}
