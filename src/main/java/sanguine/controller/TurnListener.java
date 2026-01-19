package sanguine.controller;

/**
 * This interface represents a Listener (of the model) for a game of Sanguine.
 * Will notify subscribers when the next turn of play begins.
 */
public interface TurnListener {

  /**
   * Notifies a subscriber (likely the controller) when the current Player's turn has ended,
   * and therefore when the other Player's turn has begun.
   *
   */
  void nextTurnStarted();
}
