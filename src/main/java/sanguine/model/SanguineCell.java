package sanguine.model;

import sanguine.model.enums.Player;

/**
 * Represents a cell in the board in the game of sanguine.Sanguine.
 * A cell can contain Pawns, a Card, or nothing (null).
 *
 * <p>A Cell cannot contain Pawns of different colors. For example, if a Cell contains a red Pawn,
 * only other red Pawns can be added to the Cell, not any blue Pawns.
 */
public interface SanguineCell {

  /**
   * Returns either Pawns, a Card, or null.
   * These are the only possible Objects that could be in a Cell.
   *
   * <p>The only way to create an instance of a Cell is through building it
   * with a Pawn or fully empty. You also have the ability to added Pawns and a Card into a Cell.
   * This guarantees the Object that is returned from this method
   * is either a List of Pawns or a Card (along with null if no Object is present).
   * This makes it safe to use instanceOf Card or Pawn along with this method, as well as any
   * casting necessary along with it.
   *
   * @return either Pawns, a Card, or null
   */
  Object getContents();

  /**
   * Returns the amount of Pawns inside a cell.
   *
   * @return the amount of Pawns in the Cell
   */
  int getPawnCount();

  /**
   * Returns the color of the Pawns in the cell.
   * Only needs to check one of the Pawns, as a cell cannot contain Pawns of multiple colors
   *
   * @return the color of the Pawns inside the cell
   */
  String getPawnColor();

  /**
   * Returns the owner of the Card in this cell.
   * The owner of the Card is the Player who placed it
   *
   * @return the Player that placed the card
   */
  Player getCardOwner();

  /**
   * Places a given Card in this Cell.
   *
   * @param card          the chosen Card which will be placed in the cell
   * @param currentPlayer the current Player placing the Card
   */
  void addCard(Card card, Player currentPlayer);

  /**
   * Adds a Pawn to this Cell.
   * If a Pawn is added to an empty cell, a List of Pawns is created with one pawn,
   * if a Pawn is added to a cell with other Pawns, it is added to the List.
   *
   * @param currentPlayer the current Player placing the Pawn
   */
  void addPawn(Player currentPlayer);

  /**
   * Removes all the Pawn(s) from this Cell.
   */
  void removeAllPawns();
}
