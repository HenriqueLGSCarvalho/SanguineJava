package sanguine.view.graphical.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import sanguine.controller.FeaturesListener;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineViewRedFrame;
import sanguine.view.graphical.view.listeners.MouseClickListener;

/**
 * This class represents the panel for the blue Player's hand of card.
 * Fully uncorrelated with the panel for the red Player's hand.
 */
public class BlueHandPanel extends JPanel {
  private final ReadOnlySanguineModel model;
  private int selectedCard;

  public static final double LOGICAL_X = 150.0;
  public static final double LOGICAL_Y = 150.0;

  /**
   * This represents an instance of blue's hand panel.
   *
   * @param model the model of a game of sanguine.Sanguine
   */
  public BlueHandPanel(ReadOnlySanguineModel model) {
    if (model == null) {
      throw new IllegalStateException("model is null");
    }

    this.model = model;
    selectedCard = -1;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 250);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();
    int handSize = model.getBlueHand().size();

    g2d.scale(getWidth() / LOGICAL_X, getHeight() / LOGICAL_Y);

    // Card separation lines
    // If hand size is initially 4, 5 cards will show up because you draw upon your turn starting
    for (int numLine = handSize - 1; numLine != 0; numLine--) {
      drawCardLines(g2d, numLine);
    }

    // Card details
    for (int cardIndex = 0; cardIndex < handSize; cardIndex++) {
      drawCardInfo(g2d, cardIndex,
              model.getBlueHand().get(cardIndex).name(),
              "cost: " + model.getBlueHand().get(cardIndex).cost(),
              "value: " + model.getBlueHand().get(cardIndex).value(),
              model.getBlueHand().get(cardIndex).influenceGrid()[0],
              model.getBlueHand().get(cardIndex).influenceGrid()[1],
              model.getBlueHand().get(cardIndex).influenceGrid()[2],
              model.getBlueHand().get(cardIndex).influenceGrid()[3],
              model.getBlueHand().get(cardIndex).influenceGrid()[4]);
    }

    if (!model.isGameOver()) {
      highlightClickedCard(g2d);
    }

    if (model.isGameOver()) {
      gameOverScreen(g2d);
    }
  }

  // Draws the Game Over screen when the game is over
  private void gameOverScreen(Graphics2D g2d) {
    g2d.setColor(new Color(0, 0, 0, 170));
    g2d.fillRect(0, 0, (int) LOGICAL_X, (int) LOGICAL_Y);

    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 20));

    String text = "GAME OVER!";
    FontMetrics fontMetrics = g2d.getFontMetrics();
    int textWidth = fontMetrics.stringWidth(text);
    int textHeight = fontMetrics.getHeight();

    g2d.drawString(text,
            (int) ((LOGICAL_X - textWidth) / 2),
            (int) ((LOGICAL_Y - textHeight) / 2));

    int redScore = model.getScore()[0];
    int blueScore = model.getScore()[1];

    String gameWinner = "";
    int winnerScore = 0;

    if (redScore > blueScore) {
      gameWinner = "RED WINS:";
      winnerScore = redScore;
    } else if (blueScore > redScore) {
      gameWinner = "BLUE WINS:";
      winnerScore = blueScore;
    } else {
      gameWinner = "TIE GAME:";
      winnerScore = redScore;
    }

    g2d.setFont(new Font("Arial", Font.PLAIN, 14));
    fontMetrics = g2d.getFontMetrics();
    textWidth = fontMetrics.stringWidth(gameWinner);

    g2d.drawString(gameWinner + " " + winnerScore,
            (int) ((LOGICAL_X - textWidth) / 2),
            (int) ((LOGICAL_Y - textHeight) / 2) + textHeight + 10);
  }

  // Highlights the card which was clicked on
  private void highlightClickedCard(Graphics2D g2d) {
    int numCards = model.getBlueHand().size();

    double modelColToLogicalX = LOGICAL_X / numCards;
    double modelRowToLogicalY = LOGICAL_Y;

    double x = modelColToLogicalX * selectedCard;
    double y = 0;

    g2d.setColor(new Color(159, 246, 226, 160));
    g2d.fillRect((int) x, (int) y, (int) modelColToLogicalX, (int) modelRowToLogicalY);

    repaint();
  }

  // Draws the lines separating each card from each other
  private void drawCardLines(Graphics2D g2d, int lineNum) {
    int numCards = model.getBlueHand().size();
    double modelColToLogicalX = LOGICAL_X / numCards; // the space of 1 card

    g2d.drawLine(
            (int) (lineNum * modelColToLogicalX),
            0,
            (int) (lineNum * modelColToLogicalX),
            (int) LOGICAL_Y);
  }

  // Draws the details of each card
  private void drawCardInfo(Graphics2D g2d, int cardIndex, String name, String cost, String value,
                            String influence1, String influence2, String influence3,
                            String influence4, String influence5) {
    int numCards = model.getBlueHand().size();

    double modelColToLogicalX = LOGICAL_X / numCards;
    double modelRowToLogicalY = LOGICAL_Y;

    double slotX = cardIndex * modelColToLogicalX;
    double slotY = 0;

    g2d.setColor(Color.WHITE);

    float fontSize = (float) (modelColToLogicalX * 0.15);
    Font baseFont = new Font("Arial", Font.BOLD, 1);
    Font finalFont = baseFont.deriveFont(fontSize);
    g2d.setFont(finalFont);

    FontMetrics fontMetrics = g2d.getFontMetrics();
    int lineHeight = fontMetrics.getHeight();

    int textX = (int) (slotX);
    int textY = (int) (slotY + lineHeight);

    g2d.drawString(name, textX, textY);
    g2d.drawString(cost, textX, textY + lineHeight);
    g2d.drawString(value, textX, textY + lineHeight * 2);
    g2d.drawString("", textX, textY + lineHeight * 3);
    g2d.drawString(influence1, textX, textY + lineHeight * 4);
    g2d.drawString(influence2, textX, textY + lineHeight * 5);
    g2d.drawString(influence3, textX, textY + lineHeight * 6);
    g2d.drawString(influence4, textX, textY + lineHeight * 7);
    g2d.drawString(influence5, textX, textY + lineHeight * 8);
  }

  /**
   * Clears the highlights of any clicked card.
   */
  public void clearHighlights() {
    selectedCard = -1;
    repaint();
  }

  /**
   * Determines which card was clicked.
   * If a card is clicked on twice in a row, the card is "unclicked"
   *
   * @param cardIndex the card's index
   */
  public void clickedCard(int cardIndex) {
    if (model.getCurrentPlayer() == Player.BLUE) {
      if (cardIndex == selectedCard) {
        selectedCard = -1;
      } else {
        selectedCard = cardIndex;
      }
    }
  }

  /**
   * Adds the mouseListener to this panel.
   *
   * @param listener the listener
   */
  public void addListener(FeaturesListener listener) {
    this.addMouseListener(new MouseClickListener(listener, SanguineViewRedFrame.Clicks.BLUE_CARD,
            this, model));
  }
}
