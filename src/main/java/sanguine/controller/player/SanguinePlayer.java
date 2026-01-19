package sanguine.controller.player;

import sanguine.model.SanguineModel;

/**
 * An Interface with the task for allowing a simple AI to play as a Player in a game of sanguine.
 * Uses a strategy to play each turn
 *
 */
public interface SanguinePlayer {

  /**
   * Plays one turn of sanguine.Sanguine.
   * How the turn is played depends on the chosen strategy
   *
   * @param model a model of the game sanguine.Sanguine
   */
  void playTurn(SanguineModel model);
}
