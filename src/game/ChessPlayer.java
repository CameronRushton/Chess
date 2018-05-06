package game;
/**
 * Defines a user of type ChessPlayer with a name as the only current parameter
 *
 * @author Cameron Rushton
 * @version November 4, 2016
 */
public class ChessPlayer
{
    private String playerName; //*Players cant have the same names* Fix this later
    /**
     * Constructor for objects of class ChessPlayer
     * @param player
     */
    public ChessPlayer(String player)
    {
        this.playerName = player;
    }
    /**
     * Accessor method to get the player name
     * @return String, playerName
     */
    public String getName() {
        return playerName;
    }
}
