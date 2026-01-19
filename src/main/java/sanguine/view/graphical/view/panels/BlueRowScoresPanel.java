package sanguine.view.graphical.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import sanguine.model.ReadOnlySanguineModel;

/**
 * This class represents a panel with the red Player's row scores.
 * If blue's row score > red's row score then the row score for that row will be highlighted blue
 */
public class BlueRowScoresPanel extends JPanel {

  public static final double LOGICAL_X = 150.0;
  public static final double LOGICAL_Y = 150.0;
  private final ReadOnlySanguineModel model;

  /**
   * Represents an instance RedRowScore's Panel.
   *
   * @param model the model of a game of sanguine.Sanguine.
   */
  public BlueRowScoresPanel(ReadOnlySanguineModel model) {
    this.model = model;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 350);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();
    int modelNumRow = model.getBoard().length;

    // Converts coordinates of this panel from physical X & Y to logical X & Y
    g2d.scale(getWidth() / LOGICAL_X, getHeight() / LOGICAL_Y);

    for (int row = 0; row < modelNumRow; row++) {
      drawScore(g2d, row, model.getBlueRowScores()[row]);
    }
  }

  // Draws each rows score for the blue's team
  private void drawScore(Graphics2D g2d, int row, int rowScore) {
    int modelNumRow = model.getBoard().length;

    double modelRowToLogicalY = LOGICAL_Y / modelNumRow;
    double modelColToLogicalX = LOGICAL_X;

    double cellCol = 0;
    double cellRow = row * modelRowToLogicalY;

    int pawnSize = (int) (modelRowToLogicalY * 0.9);

    int rectX = (int) (cellCol + (modelColToLogicalX - pawnSize) / 2);
    int rectY = (int) (cellRow + (modelRowToLogicalY - pawnSize) / 2);

    g2d.setColor(new Color(205, 203, 203));
    if (model.getRedRowScores()[row] < model.getBlueRowScores()[row]) {
      g2d.setColor(new Color(73, 114, 218));
    }

    // Create circle along with its color
    g2d.drawRect(rectX, rectY, pawnSize, pawnSize);

    String valueText = String.valueOf(rowScore);
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 20));

    FontMetrics font = g2d.getFontMetrics();
    int textWidth = font.stringWidth(valueText);
    int textHeight = font.getAscent();

    int textX = rectX + (pawnSize - textWidth) / 2;
    int textY = rectY + (pawnSize + textHeight) / 2;

    // Shows row score
    g2d.drawString(valueText, textX, textY);
  }
}
