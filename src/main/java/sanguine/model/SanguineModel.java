package sanguine.model;

import java.io.File;
import sanguine.controller.TurnListener;

/**
 * Contains all the necessary behaviors to play a game of sanguine.Sanguine.
 * Extends the ReadOnlySanguineModel, which contains all the helpful observer methods
 */
public interface SanguineModel extends ReadOnlySanguineModel {

  /**
   * Starts a game of sanguine.Sanguine.
   * You cannot call this method on an already started game.
   *
   * <p>Board starts empty besides the first and last column which respectively contain 1 pawn
   * per cell. The first column contains the Red player's pawns. The last column contains the
   * Blue player's pawns.
   *
   * <p>The Board must have at least 1 row, and at least 3 columns, as this ensures an interesting
   * game. The number of columns must also be odd (for example 3, 5, 7, and 9 are valid column
   * amounts, while 2, 4, 6, and 8 are invalid column amounts)
   *
   * <p>The size of a player's hand must be at least 1, otherwise the game starts in an unplayable
   * state. The player's hand size must also not be greater than 1/3rd of the deck size.
   * This is the initial state of the player's hands, their hands could grow bigger during regular
   * play.
   *
   * <p>A deck is only valid if it contains enough cards to at least fill every cell on the board,
   * and doesn't contain more than 2 of the same card. Both decks must also be of the same size
   * for it to be a valid combination to start the game.
   *
   * @param numRows  number of rows on the board
   * @param numCols  number of columns on the board
   * @param handSize the initial size of the hand of cards of both players
   * @param shuffle  if the decks are randomly shuffled or not
   * @param redDeck  red player's deck
   * @param blueDeck blue player's deck
   * @throws IllegalStateException    if the game has already started,
   * @throws IllegalArgumentException numRows must be greater than 0,
   *                                  numCols must be greater than 1 and must be odd,
   *                                  handSize must be greater than 0,
   *                                  handSize cannot be greater than 1/3rd of the deck size
   *                                  both decks must have at least enough cards to fill every cell
   *                                  on the board
   *                                  both decks can only each have a maximum of 2 of each card
   *                                  both decks must be of the same size
   */
  void startGame(int numRows, int numCols, int handSize, boolean shuffle,
                 File redDeck, File blueDeck)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * plays a Card onto a valid Cell on the board.
   * Only allows the playing of a valid card.
   *
   * <p>Once a Card is placed it can never be removed from the board.
   * A Card cannot be placed in a Cell that contains another card.
   * A Card cannot be placed in a Cell that contains the other player's pawn.
   * A card cannot be placed in a Cell with fewer Pawns than its cost,
   * and therefore cannot be placed within an empty cell.
   *
   * <p>After successfully placing a Card, it will spread its influence onto the game board.
   * If a Card influences a Cell with another Card in it, it will do nothing.
   * If a Card influences a Cell an empty cell, the empty cell will no longer be empty, and will
   * contain a Pawn.
   * If a Card influences a Cell with 1 or 2 Pawns, it will increase the Cell's Pawn count by one.
   * If a Card influences a Cell with 3 Pawns, it will do nothing, as 3 Pawns is the maximum a
   * Cell may contain.
   * If a Card influences a Cell containing the other Player's Pawn(s), the ownership of that Cell's
   * Pawn(s) will be transferred to the current Player who placed the Card (For example, if a Cell
   * contains 3 Pawns that are owned by the other Player, and you placed a Card which influences
   * that Cell, those 3 Pawns will become yours and therefore your color).
   *
   * @param card the chosen card
   * @param row  the chosen row to place card
   * @param col  the chosen column to place card
   * @throws IllegalArgumentException card cannot be null,
   *                                  row must be a valid index of the board,
   *                                  col must be a valid index of the board
   * @throws IllegalStateException    game hasn't started,
   *                                  a card cannot be placed in an empty (null) cell,
   *                                  a card cannot be placed in a cell with another card,
   *                                  a card cannot be placed on a cell with other player's pawn,
   *                                  a card cannot be placed on a cell which contains fewer pawns
   *                                  than the card's cost
   */
  void playCard(Card card, int row, int col) throws IllegalStateException;

  /**
   * The current player pass their turn.
   * Therefore, current player's turn is over, and it becomes other player's turn
   *
   * @throws IllegalStateException if the game hasn't started
   */
  void passTurn() throws IllegalStateException;

  /**
   * The current player draws a card from their deck into their hand.
   * The first card from the player's deck is drawn
   *
   * <p>A card is ALWAYS drawn at the start of a players turn.
   * This includes the very first turn of the game; Player Red will receive their handSize
   * amount of cards plus 1 card from drawCard() at the start of the game.
   *
   * @throws IllegalStateException if the game hasn't started
   */
  void drawCard() throws IllegalStateException;

  /**
   * Adds the Listener to any class who wants to be notified when the model's changes Player's
   * turn.
   *
   * @param turnListener The Listener
   */
  void addTurnListener(TurnListener turnListener);
}
