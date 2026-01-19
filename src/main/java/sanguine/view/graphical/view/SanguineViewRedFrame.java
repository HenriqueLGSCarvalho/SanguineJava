package sanguine.view.graphical.view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import sanguine.controller.FeaturesListener;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.view.graphical.view.listeners.KeyboardPressListener;
import sanguine.view.graphical.view.panels.BlueRowScoresPanel;
import sanguine.view.graphical.view.panels.ErrorMessagePanel;
import sanguine.view.graphical.view.panels.RedHandPanel;
import sanguine.view.graphical.view.panels.RedRowScoresPanel;
import sanguine.view.graphical.view.panels.SanguineBoardPanel;

/**
 * This class represents an implementation of a graphical view for the game of sanguine.Sanguine.
 * This frame represents the red Player's frame.
 * This is the Publisher (the class that is going to send off events)
 */
public class SanguineViewRedFrame extends JFrame implements SanguineView {

  public static final int FRAME_WIDTH = 600;
  public static final int FRAME_HEIGHT = 600;

  private final SanguineBoardPanel boardPanel;
  private final RedHandPanel redHandPanel;

  private final RedRowScoresPanel redRowScoresPanel;
  private final BlueRowScoresPanel blueRowScoresPanel;

  private final ErrorMessagePanel errorPanel;

  /**
   * This represents an instance of red's frame.
   *
   * @param model the model of a game of sanguine.Sanguine
   * @param title the title of the frame
   */
  public SanguineViewRedFrame(ReadOnlySanguineModel model, String title) {
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle(title);

    this.boardPanel = new SanguineBoardPanel(model);
    this.redHandPanel = new RedHandPanel(model);
    this.blueRowScoresPanel = new BlueRowScoresPanel(model);
    this.redRowScoresPanel = new RedRowScoresPanel(model);
    this.errorPanel = new ErrorMessagePanel();

    this.boardPanel.setBackground(Color.LIGHT_GRAY);
    this.redHandPanel.setBackground(Color.DARK_GRAY);
    this.redRowScoresPanel.setBackground(new Color(205, 203, 203));
    this.blueRowScoresPanel.setBackground(new Color(205, 203, 203));

    this.add(boardPanel, BorderLayout.CENTER);
    this.add(redHandPanel, BorderLayout.SOUTH);
    this.add(this.redRowScoresPanel, BorderLayout.WEST);
    this.add(this.blueRowScoresPanel, BorderLayout.EAST);

    this.setGlassPane(this.errorPanel);
    errorPanel.setVisible(false);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showErrorPanel() {
    this.errorPanel.setVisible(true);
  }

  @Override
  public void clickedCell(int row, int col) {
    this.boardPanel.clickedCell(row, col);
  }

  @Override
  public void clickedCard(int cardIndex) {
    this.redHandPanel.clickedCard(cardIndex);
  }

  @Override
  public void clearHighlights() {
    boardPanel.clearHighlights();
    redHandPanel.clearHighlights();
    repaint();
  }

  @Override
  public void addListener(FeaturesListener listener) {
    this.boardPanel.addListener(listener);
    this.redHandPanel.addListener(listener);

    this.addKeyListener(new KeyboardPressListener(listener));
  }

  /**
   * This enum represents the types of clicks on a frame.
   * Types of clicks:
   *   - CELL: A click on a board's cell
   *   - RED_CARD: A click on a card on red's hand
   *   - BLUE_CARD A click on a card on blue's hand
   */
  public enum Clicks {
    CELL,
    RED_CARD,
    BLUE_CARD
  }
}