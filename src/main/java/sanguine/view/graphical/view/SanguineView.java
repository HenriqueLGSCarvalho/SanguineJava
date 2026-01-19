package sanguine.view.graphical.view;

import sanguine.controller.FeaturesListener;

/**
 * This interface represents a GUI for the game of sanguine.Sanguine.
 * Includes all the functionality needed for a frame
 */
public interface SanguineView {

  /**
   * Refreshes the view to show any changes in game state.
   */
  void refresh();

  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Checks which Cell was clicked on.
   *
   * @param row the row where the Cell is located
   * @param col the column where the Cell is located
   */
  void clickedCell(int row, int col);

  /**
   * Checks which card was clicked on.
   *
   * @param cardIndex the index of the Card
   */
  void clickedCard(int cardIndex);

  /**
   * Clears the highlights of the previous Player's turn.
   */
  void clearHighlights();


  /**
   * Sets the panel with error message to be visible.
   * Effectively, shows a user an error message.
   */
  void showErrorPanel();

  /**
   * Adds the Listener to any class who wants to be notified when the event fires.
   *
   * @param listener The Listener
   */
  void addListener(FeaturesListener listener);
}
