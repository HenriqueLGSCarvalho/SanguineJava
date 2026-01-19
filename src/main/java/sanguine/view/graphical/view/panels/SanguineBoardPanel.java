package sanguine.view.graphical.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;
import sanguine.controller.FeaturesListener;
import sanguine.model.Card;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.enums.Pawn;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineViewRedFrame;
import sanguine.view.graphical.view.listeners.MouseClickListener;

/**
 * This class represents the panel for the board of sanguine.Sanguine.
 * A board is composed of many cells.
 */
public class SanguineBoardPanel extends JPanel {

  private final ReadOnlySanguineModel model;
  private int selectedRow;
  private int selectedCol;

  public static final double LOGICAL_X = 150.0;
  public static final double LOGICAL_Y = 150.0;

  /**
   * Represents an instance of the panel for a Board in sanguine.Sanguine.
   *
   * @param model the model of a game of sanguine.Sanguine
   */
  public SanguineBoardPanel(ReadOnlySanguineModel model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    this.model = model;
    this.selectedRow = -1;
    this.selectedCol = -1;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 350);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();
    int modelNumRow = model.getBoard().length;
    int modelNumCol = model.getBoard()[0].length;

    // Converts coordinates of this panel from physical X & Y to logical X & Y
    g2d.scale(getWidth() / LOGICAL_X, getHeight() / LOGICAL_Y);

    // Horizontal Lines (columns)
    for (int numLines = modelNumCol - 1; numLines != 0; numLines--) {
      drawBoard(g2d, 0, numLines, modelNumRow, numLines);
    }
    // Vertical Lines (rows)
    for (int numLines = modelNumRow - 1; numLines != 0; numLines--) {
      drawBoard(g2d, numLines, 0, numLines, modelNumCol);
    }

    for (int row = 0; row < modelNumRow; row++) {
      for (int col = 0; col < modelNumCol; col++) {
        // If cell contains Pawns
        if (model.getCell(row, col).getContents() instanceof List<?> listOfPawn) {
          if (listOfPawn.getFirst() instanceof Pawn) {
            List<Pawn> confirmedListOfPawn = (List<Pawn>) listOfPawn;
            if (confirmedListOfPawn.getFirst().getColor().equals("RED")) {
              drawPawns(g2d, row, col, Color.RED, confirmedListOfPawn.size());
            } else {
              drawPawns(g2d, row, col, Color.BLUE, confirmedListOfPawn.size());
            }
          }
        }
        // If cell contains a Card
        if (model.getCell(row, col).getContents() instanceof Card card) {
          int cardValue = card.value();
          drawPlacedCard(g2d, row, col, model.getCell(row, col).getCardOwner(), cardValue);
        }
      }
    }

    highlightClickedCell(g2d);

  }

  // Draws the grid lines for a board in sanguine.Sanguine.
  private void drawBoard(Graphics2D g2d, int row1, int col1, int row2, int col2) {
    int modelNumRow = model.getBoard().length;
    int modelNumCol = model.getBoard()[0].length;
    double modelRowToLogicalY = LOGICAL_Y / modelNumRow;
    double modelColToLogicalX = LOGICAL_X / modelNumCol;

    g2d.drawLine(
            (int) (col1 * modelColToLogicalX),
            (int) (row1 * modelRowToLogicalY),
            (int) (col2 * modelColToLogicalX),
            (int) (row2 * modelRowToLogicalY));

    repaint();
  }

  // Draws Pawns; Represented as ovals with a #.
  private void drawPawns(Graphics2D g2d, int row, int col, Color color, int pawnAmount) {
    int modelNumRow = model.getBoard().length;
    int modelNumCol = model.getBoard()[0].length;

    double modelRowToLogicalY = LOGICAL_Y / modelNumRow;
    double modelColToLogicalX = LOGICAL_X / modelNumCol;

    double cellCol = col * modelColToLogicalX;
    double cellRow = row * modelRowToLogicalY;

    int pawnWidth = (int) (modelColToLogicalX * 0.4);
    int pawnHeight = (int) (modelRowToLogicalY * 0.4);

    int ovalX = (int) (cellCol + (modelColToLogicalX - pawnWidth) / 2);
    int ovalY = (int) (cellRow + (modelRowToLogicalY - pawnHeight) / 2);

    g2d.setColor(color);

    // Create Pawn along with its color
    g2d.fillOval(ovalX, ovalY, pawnWidth, pawnHeight);

    String valueText = String.valueOf(pawnAmount);
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 5));

    FontMetrics font = g2d.getFontMetrics();
    int textWidth = font.stringWidth(valueText);
    int textHeight = font.getAscent();

    int textX = ovalX + (pawnWidth - textWidth) / 2;
    int textY = ovalY + (pawnHeight + textHeight) / 2;

    // Show amount of pawns
    g2d.drawString(valueText, textX, textY);

    repaint();
  }

  // Draws a card onto the board; card is printed with its value showing
  private void drawPlacedCard(Graphics2D g2d, int row, int col, Player player, int cardValue) {
    int modelNumRow = model.getBoard().length;
    int modelNumCol = model.getBoard()[0].length;

    double modelRowToLogicalY = LOGICAL_Y / modelNumRow;
    double modelColToLogicalX = LOGICAL_X / modelNumCol;

    double x = modelColToLogicalX * col;
    double y = modelRowToLogicalY * row;

    if (player.getColor().equals("RED")) {
      g2d.setColor(new Color(218, 73, 73));
    } else if (player.getColor().equals("BLUE")) {
      g2d.setColor(new Color(73, 114, 218));
    }

    g2d.fillRect((int) x, (int) y, (int) modelColToLogicalX, (int) modelRowToLogicalY);

    String valueText = String.valueOf(cardValue);
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 5));

    FontMetrics font = g2d.getFontMetrics();
    int textWidth = font.stringWidth(valueText);
    int textHeight = font.getAscent();
    int textX = (int) (x + (modelColToLogicalX - textWidth) / 2);
    int textY = (int) (y + (modelRowToLogicalY + textHeight) / 2);

    g2d.drawString(valueText, textX, textY);

    repaint();
  }

  // Highlights a cell whenever clicked
  private void highlightClickedCell(Graphics2D g2d) {
    int modelNumRow = model.getBoard().length;
    int modelNumCol = model.getBoard()[0].length;

    double modelRowToLogicalY = LOGICAL_Y / modelNumRow;
    double modelColToLogicalX = LOGICAL_X / modelNumCol;

    double x = modelColToLogicalX * selectedCol;
    double y = modelRowToLogicalY * selectedRow;

    g2d.setColor(new Color(159, 246, 226, 160));
    g2d.fillRect((int) x, (int) y, (int) modelColToLogicalX, (int) modelRowToLogicalY);
  }

  /**
   * Identifies which Cell was pressed last.
   * If a cell was pressed twice in a row, unselect the cell
   *
   * @param row the row of the board
   * @param col the column of the board
   */
  public void clickedCell(int row, int col) {
    if (row == selectedRow && col == selectedCol) {
      selectedRow = -1;
      selectedCol = -1;
    } else {
      selectedRow = row;
      selectedCol = col;
    }
  }

  /**
   * Clears the highlights of any clicked cell.
   */
  public void clearHighlights() {
    selectedRow = -1;
    selectedCol = -1;
    repaint();
  }

  /**
   * Adds the mouseListener to this panel.
   *
   * @param listener the listener
   */
  public void addListener(FeaturesListener listener) {
    this.addMouseListener(new MouseClickListener(listener, SanguineViewRedFrame.Clicks.CELL,
            this, model));
  }
}
