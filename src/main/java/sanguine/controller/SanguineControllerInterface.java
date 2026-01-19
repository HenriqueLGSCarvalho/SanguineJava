package sanguine.controller;

import java.io.File;

/**
 * This interface represents a placeholder controller.
 * Will be used to stand in for an actual controller until one is made.
 */
public interface SanguineControllerInterface {

  /**
   * Runs a game of sanguine.Sanguine.
   *
   * @param numRows  number of rows on the board
   * @param numCols  number of columns on the board
   * @param handSize number of starting cards on each player's hand
   * @param shuffle  if the deck is shuffled or not
   * @param redDeck  red's entire deck of cards
   * @param blueDeck blue's entire deck of cards
   */
  void playGame(int numRows, int numCols, int handSize, boolean shuffle,
                File redDeck, File blueDeck);
}
