package sanguine.strategy;

import sanguine.model.SanguineModel;

/**
 * This interface is responsible for establishing the supported strategies for running this game.
 * Supported Strategies:
 * - AI who makes the first possible valid move; if no moves are valid, they pass turn.
 */
public interface SanguineStrategy {

  /**
   * Plays the next turn in a game of sanguine.Sanguine.
   *
   * @param model the model
   */
  void playTurn(SanguineModel model);
}
