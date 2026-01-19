package sanguine.controller.tests.controller.mocks;

import sanguine.controller.FeaturesListener;
import sanguine.view.graphical.view.SanguineView;

/**
 * This class is a mock of a SanguineView GUI.
 * Useful for testing other components of MVC.
 *
 * <p>Does not actually implement code into any of the methods, as it is unnecessary for
 * my purposes.
 */
public class ViewMock implements SanguineView {
  @Override
  public void refresh() {

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void clickedCell(int row, int col) {

  }

  @Override
  public void clickedCard(int cardIndex) {

  }

  @Override
  public void clearHighlights() {

  }

  @Override
  public void showErrorPanel() {

  }

  @Override
  public void addListener(FeaturesListener listener) {

  }
}
