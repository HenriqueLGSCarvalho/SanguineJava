package sanguine.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a card for the game of sanguine.Sanguine.
 * All fields are made to be immutable, so once a card is made, it is immutable.
 */
public class Card implements SanguineCard {
  private final String name;
  private final int cost;
  private final int value;
  private final String[] influenceGrid;

  /**
   * Represents an instance of a Card for the game of sanguine.Sanguine.
   *
   * @param name          name of the card
   * @param cost          cost of the card
   * @param value         value of the card
   * @param influenceGrid influence grid of the card
   */
  private Card(String name, int cost, int value, String[] influenceGrid) {
    if (name == null || influenceGrid == null) {
      throw new IllegalArgumentException("name and influenceGrid of card cannot be null");
    }
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("cost of card must be between 1 and 3 inclusive");
    }
    if (value <= 0) {
      throw new IllegalArgumentException("value of card must be positive");
    }

    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceGrid = Arrays.copyOf(influenceGrid, influenceGrid.length);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public int cost() {
    return cost;
  }

  @Override
  public int value() {
    return value;
  }

  @Override
  public String[] influenceGrid() {
    return Arrays.copyOf(influenceGrid, influenceGrid.length);
  }

  @Override
  public String toString() {
    StringBuilder card = new StringBuilder();
    card.append(name + " " + cost + " " + value);

    for (String currentRow : influenceGrid) {
      card.append("\n" + currentRow);
    }

    return card.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Card other)) {
      return false;
    }

    return this.name.equals(other.name) && this.cost == other.cost && this.value == other.value
            && Arrays.equals(this.influenceGrid, other.influenceGrid);
  }

  @Override
  public int hashCode() {
    int influenceGridHash = Arrays.hashCode(this.influenceGrid);
    return Objects.hash(this.name, this.cost, this.value, influenceGridHash);
  }

  /**
   * Factory Method.
   * Builds a Builder Object, so a Card can be constructed
   *
   * @return A Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * This inner class is responsible for building a Card Object.
   * A card must contain exactly a name, cost, value, and an influenceGrid.
   */
  public static class Builder {
    private String name;
    private int cost;
    private int value;
    private String[] influenceGrid;

    /**
     * builds the name of the Card.
     *
     * @param name name of the Card
     * @return the Builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Builds the cost of the Card.
     *
     * @param cost cost of the Card
     * @return the Builder
     */
    public Builder cost(int cost) {
      this.cost = cost;
      return this;
    }

    /**
     * Builds the value of the Card.
     *
     * @param value value of the Card
     * @return The Builder
     */
    public Builder value(int value) {
      this.value = value;
      return this;
    }

    /**
     * Builds the influenceGrid of the Card.
     *
     * @param influenceGrid influenceGrid of the Card
     * @return The Builder
     */
    public Builder influenceGrid(String[] influenceGrid) {
      this.influenceGrid = influenceGrid;
      return this;
    }

    /**
     * Composes the pieces of the Card together, and builds it.
     *
     * @return the fully build Card
     */
    public Card build() {
      return new Card(name, cost, value, influenceGrid);
    }
  }
}
