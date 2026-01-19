package sanguine.view.textual.view;

import java.util.List;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Pawn;
import sanguine.model.enums.Player;

/**
 * Textual View for the game of sanguine.Sanguine.
 * Will contain usable toString() method
 */
public class BasicSanguineTextualView implements SanguineTextualView {
  SanguineModel model;

  /**
   * Represents the textual view for sanguine.Sanguine.
   *
   * @param model sanguine.Sanguine model
   */
  public BasicSanguineTextualView(SanguineModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return buildBoard();
  }

  // Builds the board textually according to the current state of the game
  private String buildBoard() {
    StringBuilder board = new StringBuilder();
    int rowIndex = 0;

    for (Cell[] row : model.getBoard()) {
      board.append(model.getRedRowScores()[rowIndex] + " ");

      for (Cell cell : row) {
        if (cell.getContents() instanceof List<?> maybeListOfPawn
            && maybeListOfPawn.getFirst() instanceof Pawn) {
          List<Pawn> listOfPawn = (List<Pawn>) maybeListOfPawn;
          board.append(listOfPawn.size());
        }
        if (cell.getContents() instanceof Card) {
          if (cell.getCardOwner().equals(Player.RED)) {
            board.append("R");
          }
          if (cell.getCardOwner().equals(Player.BLUE)) {
            board.append("B");
          }
        }
        if (cell.getContents() == null) {
          board.append("_");
        }
      }
      board.append(" " + model.getBlueRowScores()[rowIndex] + System.lineSeparator());
      rowIndex++;
    }
    return board.toString();
  }
}
