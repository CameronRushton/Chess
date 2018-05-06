import ai.AI;
import board.ChessLocation;
import game.ChessGame;
import gui.Table;
import pieces.ChessPiece;

import java.util.Scanner;
import java.lang.String;
/**
 * Main class for a simple chess game.
 * It utilizes the other classes to create a chessboard and pieces that
 * can be moved by the user.
 * Run the main method to start.
 * @author Cameron Rushton 101002958
 * @date of submission November 4, 2016
 */
public class PlayGame {
    //intRow and intCol are general variables used to store user input
    private static int intRow = -1, intCol = -1;
    private static final String player1 = "B", player2 = "W"; //TODO: Move this to ChessPlayer

    /**
     * Description: Main method that creates a new game and
     * fetches required input from the user.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Type 'two player' to start a two player game, otherwise type anything else to play the ai.");
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("two player"))
            startGame(false);
        else {
            startGame(true);
        }
    }

    private static void startGame(boolean EnableAI) {
        while (true) {
            Scanner input = new Scanner(System.in);

            ChessGame game = new ChessGame(player1, player2);

            int oldRow = -1, oldCol = -1;
            int loc, loc2;
            int turnNum = 1;
            boolean isFound = false, switchPlayers, restart = false;
            String inputString, currentPlayer = player2;
            AI bot = new AI(game);
            ChessGame.getBoard().printBoard();
            Table table = new Table(game);


            //Piece Selection
            do {
                callOut(currentPlayer);
                if (currentPlayer.equals(player2) && EnableAI) {
                    bot.makeMove();
                    turnNum += 1;
                    if (turnNum % 2 == 1) { //P2 plays on turns 1, 3, 5, etc...
                        currentPlayer = player2;
                    } else {
                        currentPlayer = player1;
                    }

                }
                do {

                    System.out.print("Enter piece coordinates, \"restart\", or \"quit\": "); // Example input: "quit","(2,3)","2,3"
                    inputString = input.next();
                    //Note that this can only find values within range, so no need to check for out of bounds
                    loc = checkForInt(inputString, 0); //Take the string and starting location and look for int 1
                    loc2 = checkForInt(inputString, loc + 1); //Continue from where we left off to find int 2

                    if (inputString.equalsIgnoreCase("quit")) {

                        System.out.println("Thanks for playing!");
                        System.exit(0);

                    } else if (inputString.equalsIgnoreCase("restart")) {
                        restart = true;
                        break;
                    } else if (loc == -2 || loc2 == -2) { //If the int finder cant find anything at any time
                        //if a space is entered, this is printed twice. need a type of reset?
                        System.out.println("Oops, there's been an input error"); //...limitation of next()?

                    } else { //If everything checks out
                        isFound = game.getBoard().isPieceAt(intRow, intCol);
                        if (isFound) { //Insert a "piece found" notice?
                            ChessLocation location = new ChessLocation(intRow, intCol);
                            ChessPiece piece = game.getBoard().getPieceAt(location);
                            if (currentPlayer.equals(piece.getPlayer().getName())) {
                                System.out.println(piece);
                                oldRow = intRow; //Prep for a piece move. The intRow/Col are going to be overwritten
                                oldCol = intCol; //...with the destination.
                            } else {
                                System.out.println("Please select your own piece.");
                                isFound = false; //Forcing a loop
                            }
                        } else {
                            System.out.println("Piece not found, please try again.");
                        }
                    }
                } while(!isFound); //Loop as long as a piece isn't found

                //Move Selection
                if (!restart) {
                    System.out.print("Enter move coordinates: ");
                    inputString = input.next();

                    loc = checkForInt(inputString, 0);
                    loc2 = checkForInt(inputString, loc + 1);
                }
                if (inputString.equalsIgnoreCase("quit")) {

                    System.out.println("Thanks for playing!");
                    System.exit(0);
                }else if (restart) {
                    System.out.print("Are you sure you want to restart? (yes/no): ");
                    inputString = input.next();
                    if (inputString.equalsIgnoreCase("yes")) {
                        System.out.println("Restarting...");
                    } else {
                        System.out.println("Restart canceled");
                        restart = false;
                    }
                }
                else if (loc == -2 || loc2 == -2) {

                    System.out.println("Oops, there's been an input error");

                } else { //If everything checks out

                    ChessLocation newLocation = new ChessLocation(intRow,intCol);
                    ChessLocation oldLocation = new ChessLocation(oldRow, oldCol);

                    ChessPiece piece = game.getBoard().getPieceAt(oldLocation);
                    switchPlayers = piece.moveTo(newLocation); //Try to move piece
                    //if the piece moved, switch players
                    if (switchPlayers) {
                        turnNum += 1;
                        if (turnNum % 2 == 1) { //P2 plays on turns 1, 3, 5, etc...
                            currentPlayer = player2;
                        } else {
                            currentPlayer = player1;
                        }
                    }
                }
                isFound = false; //reset
            } while(!restart); //Loop through select/move indefinitely until user quits
        }
    }

    private static void callOut (String currentPlayer) {
        if (currentPlayer.equals(player2)) System.out.println("White's Turn");
        if (currentPlayer.equals(player1)) System.out.println("Black's Turn");
    }

    /**
     * Description: Searches s for an integer starting @param start. When found, save it to intRow or intCol
     * and return its location.
     * @param s, user's input string
     * @return int This returns the location of the integer found or an invalid location if none were found.
     * @throws IndexOutOfBoundsException
     * @see IndexOutOfBoundsException
     */
    private static int checkForInt (String s, int start) {
        //Look at each char in the string and try to make it an integer
        int integer;
        for (int i = start; i < s.length(); i++) {
            try {

                integer = (int)s.charAt(i); //Break string up into chars, type cast to int and store

                if (integer >= 48 && integer <= 55) {//Found the number (0 - 7 in ASCII)
                    //if start (first run through) = 0, put in first one
                    if (start == 0) {
                        intRow = integer - 48;
                    } else {
                        intCol = integer - 48;
                    }
                    return i;
                }
            }
            catch (IndexOutOfBoundsException e) {
                //Do nothing
            }
        }
        return -2; //Return invalid location if not found
    }

    /**
     * Halts the program for an amount of time in milliseconds
     * @param time, in seconds (int)
     * @return Nothing
     * @throws InterruptedException
     */
    private static void delay (int time) {
        try {

            Thread.sleep(time);

        } catch (InterruptedException e) {

        }
    }
}
