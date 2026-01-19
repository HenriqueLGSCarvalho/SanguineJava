package sanguine.model.enums;

/**
 * This enum represents a Pawn in the game of sanguine.
 * Pawns are either blue or red,
 */
public enum Pawn {
  RED("RED"),
  BLUE("BLUE");

  private final String color;

  /**
   * Represents a pawn in the game of sanguine.
   *
   * @param color color of the pawn
   */
  Pawn(String color) {
    this.color = color;
  }

  // returns the color of the Pawn
  public String getColor() {
    return color;
  }
}
