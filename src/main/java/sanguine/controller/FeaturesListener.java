package sanguine.controller;

import sanguine.model.enums.Player;

/**
 * This interface represents a Listener (of events) for a game of Sanguine.
 * Each behavior of the game is an event that should be represented in this interface.
 * This Interface should be stored along with the Subscriber
 *
 * <P>Note: Needs listeners for both mouse clicks & keyboard presses
 */
public interface FeaturesListener {

  /**
   * Decides what happens when a user clicks a cell on the board during a game of sanguine.Sanguine.
   *
   * @param row row of the board
   * @param col column of the board
   */
  void onClickCell(int row, int col);

  /**
   * Decides what happens when a user clicks a card on their hand during a game of sanguine.
   *
   * @param cardIdx   index of the Card in the current Player's hand
   */
  void onClickCard(int cardIdx);

  /**
   * Confirms moving a card onto a cell.
   *
   * <P>In order to be able to confirm a move, the current Player must first click on a Card,
   * then click on a cell, then finally click on the onPressConfirmMove keybind
   */
  void onPressConfirmMove();

  /**
   * Confirms passing the current Player's turn.
   */
  void onPressConfirmPass();
}
