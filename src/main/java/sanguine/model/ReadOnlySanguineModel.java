package sanguine.model;

import java.util.List;
import sanguine.model.enums.Player;

/**
 * Contains all the observer methods within a game of sanguine.Sanguine.
 * Does not contain any behaviors
 */
public interface ReadOnlySanguineModel {

  /**
   * Returns true if game has started, returns false if game hasn't started.
   *
   * @return if the game has started or not
   */
  boolean isGameStarted();

  /**
   * Returns the current player whose turn it is.
   *
   * @return the current player
   * @throws IllegalStateException if the game hasn't started
   */
  Player getCurrentPlayer();

  /**
   * Returns a copy of the red deck,
   * which should be separate reference from the original red deck.
   * Therefore, can only be used to view the current red deck,
   * mutating using this method will do nothing.
   *
   * @return red deck, which is a List of Strings
   * @throws IllegalStateException if game hasn't started
   */
  List<Card> getRedDeck() throws IllegalStateException;

  /**
   * Returns a copy of the blue deck,
   * which should be separate reference from the original blue deck.
   * Therefore, can only be used to view the current blue deck,
   * mutating using this method will do nothing.
   *
   * @return blue deck, which is a List of Strings
   * @throws IllegalStateException if game hasn't started
   */
  List<Card> getBlueDeck() throws IllegalStateException;

  /**
   * Returns a copy of the red hand,
   * which should be separate reference from the original red hand.
   * Therefore, can only be used to view the current red hand,
   * mutating using this method will do nothing.
   *
   * @return red hand, which is a List of Strings
   * @throws IllegalStateException if game hasn't started
   */
  List<Card> getRedHand() throws IllegalStateException;

  /**
   * Returns a copy of the blue hand,
   * which should be separate reference from the original blue hand.
   * Therefore, can only be used to view the current blue hand,
   * mutating using this method will do nothing.
   *
   * @return blue hand, which is a List of Strings
   * @throws IllegalStateException if game hasn't started
   */
  List<Card> getBlueHand() throws IllegalStateException;

  /**
   * Returns a copy of a chosen Cell,
   * which should be separate reference from the original board.
   * Therefore, can only be used to view the current board,
   * mutating using this method will do nothing.
   *
   * @param row row of the cell
   * @param col column of the cell
   * @return the chosen cell
   * @throws IllegalArgumentException if row or col are out of bounds
   * @throws IllegalStateException    if the game hasn't started
   */
  Cell getCell(int row, int col) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns a copy of the board,
   * which should be separate reference from the original board.
   * Therefore, can only be used to view the current board,
   * mutating using this method will do nothing.
   *
   * @return the board
   * @throws IllegalStateException if game hasn't started
   */
  Cell[][] getBoard() throws IllegalStateException;

  /**
   * Returns how many cells are contained within the board,
   * doesn't matter what is in the cells.
   *
   * @return the size of the board
   * @throws IllegalStateException if game hasn't started
   */
  int getBoardSize() throws IllegalStateException;

  /**
   * returns the current scores of the red player and the blue player.
   * This array of ints will hold the scores of each player.
   *
   * <p>In a Two player game, such as sanguine.Sanguine, the first player's (red) score will be the
   * first index in the array, followed by the second player's (blue) score in the second
   * index of array. There should not be a 3rd index under any circumstances, as there are only
   * two players.
   *
   * <p>The score of a game is determined on the scores of each row (row scores).
   * In order to determine the score of a row for each player, they must add up the scores
   * of each card of their color within that row. Then, the row scores of both player must be
   * compared against one another; only the player with the higher row score keeps the points,
   * the player with the lower score gets 0 points for that row. If the both players have the same
   * row score, they both get 0 points for that row.
   *
   * <p>To get the final score, the valid row scores must be calculated & added
   * together for each respective player.
   *
   * <p>At the end of the game, the player with the higher score wins.
   * If both players have an equal score, the game ends in a tie.
   *
   * @return The scores of each player
   * @throws IllegalStateException If the game hasn't started
   */
  int[] getScore() throws IllegalStateException;

  /**
   * Returns true if the game is over, returns false if the game isn't over.
   *
   * <p>The game is only over when both players consecutively pass their turns. For example,
   * if red player passes their turn, if the next turn, blue player passes their turn, the game
   * ends.
   *
   * @return If the game is over or not
   * @throws IllegalStateException If the game hasn't started
   */
  boolean isGameOver();

  /**
   * Returns the row scores for each row for the Red Player.
   *
   * @return Red's row scores
   * @throws IllegalStateException if the game hasn't started
   */
  int[] getRedRowScores() throws IllegalStateException;

  /**
   * Returns the row scores for each row for the Blue Player.
   *
   * @return Blue's row scores
   * @throws IllegalStateException if the game hasn't started
   */
  int[] getBlueRowScores() throws IllegalStateException;
}
