package sanguine.view.textual.view;

/**
 * Interface for the textual view for a game of sanguine.Sanguine.
 * Details how the toString method will display the game
 */
public interface SanguineTextualView {

  /**
   * Builds a board with the current game state taken from a model of sanguine.Sanguine.
   *
   * <p>The board is represented with Cards, Pawns, and Empty Cells.
   * A Card is represented by the color initial of the Player who owns it (For example: R and B).
   * A Pawns are represented by a number between 1-3, depending on how many Pawns are in that Cell,
   * however, the Player who owns the Pawn is not represented in this toString().
   * Empty Cell are represented by an underscore '_'.
   *
   * <p>On the left and right side of the board, there are numbers representing
   * the score for each row. The Scores on the left are the Row Score's for the Red Player,
   * while the Scores on the right are the Row Score's for the Blue Player.
   *
   * <p>Calling view.ToString() should be all that is needed to get the visual representation
   * from this method
   *
   * @return String textual representation of the current board
   */
  String toString();
}
