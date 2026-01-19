package sanguine.controller.player;

import sanguine.model.SanguineModel;
import sanguine.strategy.SanguineStrategy;

/**
 * This class implements a Player in sanguine.Sanguine.
 * Uses a strategy to determine how that player will play each turn
 */
public class SanguinePlayerImpl implements SanguinePlayer {

  // Delegate of a SanguineStrategy
  private final SanguineStrategy strategy;

  /**
   * Represents an instance of a Player in sanguine.Sanguine.
   *
   * @param strategy a strategy used to play a turn
   */
  public SanguinePlayerImpl(SanguineStrategy strategy) {
    this.strategy = strategy;
  }

  @Override
  public void playTurn(SanguineModel model) {
    strategy.playTurn(model);
  }
}
