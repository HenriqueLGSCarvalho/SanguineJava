package sanguine.model;

/**
 * Represents a Card for the game of sanguine.Sanguine.
 * A valid card has a name, a cost, a value, and a 5x5 grid which holds its influence on the board;
 * A valid card must have a positive value, and a cost of between 1-3 inclusive.
 *
 * <p>Two cards are equal if they have the same name, cost, value, and influence grid.
 */
public interface SanguineCard {

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  String name();

  /**
   * Returns the cost of the card.
   *
   * @return the cost of the card
   */
  int cost();

  /**
   * Returns the value of the card.
   *
   * @return the value of the card
   */
  int value();

  /**
   * Should return a copy of the influenceGrid of the card.
   * Using this method of mutation should have no affect.
   *
   * @return the influence grid of the card
   */
  String[] influenceGrid();
}
