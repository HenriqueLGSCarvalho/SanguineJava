package sanguine.controller.stub.controller;

import java.io.File;
import sanguine.controller.player.SanguinePlayer;

/**
 * This interface represents a placeholder controller.
 * Will be used to stand in for an actual controller until one is made.
 */
public interface StubSanguineControllerInterface {

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

  /**
   * Sets the red Player to be as a bot, which will automatically play their turn.
   *
   * @param redBot a SanguinePlayer (a simple AI that play's their turn)
   */
  void setRedBot(SanguinePlayer redBot);

  /**
   * Sets the blue Player to be as a bot, which will automatically play their turn.
   *
   * @param blueBot a SanguinePlayer (a simple AI that play's their turn)
   */
  void setBlueBot(SanguinePlayer blueBot);
}
