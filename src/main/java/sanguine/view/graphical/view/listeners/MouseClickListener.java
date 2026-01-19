package sanguine.view.graphical.view.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import sanguine.controller.FeaturesListener;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineViewRedFrame;

/**
 * This class is responsible for listening for mouse clicks.
 * Clicks can be either on a Board's Cell or Hand's Card.
 */
public class MouseClickListener extends MouseAdapter {

  private final FeaturesListener listener;
  private final SanguineViewRedFrame.Clicks type;
  private final JPanel panel;
  private final ReadOnlySanguineModel model;

  private int lastValue;

  /**
   * Represents an instance of this MouseListener.
   *
   * @param listener the Listener
   * @param type     the type of click; can be either on a cell, on red's hand, or on blue's hand
   * @param panel    the panel which is being clicked on
   * @param model    the model of a Game of sanguine.Sanguine
   */
  public MouseClickListener(FeaturesListener listener, SanguineViewRedFrame.Clicks type,
                            JPanel panel, ReadOnlySanguineModel model) {
    if (listener == null) {
      throw new IllegalStateException("Listener cannot be null");
    }

    this.listener = listener;
    this.type = type;
    this.panel = panel;
    this.model = model;

    this.lastValue = -1;
  }

  // Whenever the mouse is clicked, perform whatever this method demands
  @Override
  public void mouseClicked(MouseEvent evt) {
    if (type == SanguineViewRedFrame.Clicks.CELL) {
      double row = evt.getY() * ((double) model.getBoard().length / panel.getHeight());
      double col = evt.getX() * ((double) model.getBoard()[0].length / panel.getWidth());
      listener.onClickCell((int) row, (int) col);
    }
    if (type == SanguineViewRedFrame.Clicks.RED_CARD) {
      double col = evt.getX() * ((double) model.getRedHand().size() / panel.getWidth());
      listener.onClickCard((int) col);
    }
    if (type == SanguineViewRedFrame.Clicks.BLUE_CARD) {
      double col = evt.getX() * ((double) model.getBlueHand().size() / panel.getWidth());
      listener.onClickCard((int) col);
    }
  }
}
