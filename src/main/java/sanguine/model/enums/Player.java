package sanguine.model.enums;

/**
 * Represents a player in the game of sanguine.
 * A player could be:
 * - Red
 * - Blue
 */
public enum Player {
  RED("RED"),
  BLUE("BLUE");

  private final String color;

  /**
   * this represents an instance of a player.
   *
   * @param color color of the player
   */
  Player(String color) {
    this.color = color;
  }

  // returns the color of the player
  public String getColor() {
    return color;
  }
}
