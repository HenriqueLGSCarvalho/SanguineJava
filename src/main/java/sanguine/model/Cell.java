package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.enums.Pawn;
import sanguine.model.enums.Player;

/**
 * This class represents Cells in a board of the game sanguine.Sanguine.
 * A Cell can contain pawns, a card, or nothing (null).
 */
public class Cell implements SanguineCell {
  private List<Pawn> pawn;
  private Card card;
  private Player cardOwner;

  /**
   * Represents an instance of a cell which contains a pawn.
   *
   * @param pawn a pawn in the game of sanguine.Sanguine
   */
  public Cell(Pawn pawn) {
    if (pawn == null) {
      throw new IllegalArgumentException("Pawn cannot be null");
    }
    this.pawn = new ArrayList<>();
    this.pawn.add(pawn);
    this.card = null;
    this.cardOwner = null;
  }

  /**
   * Represents an empty instance of a cell.
   */
  public Cell() {
    this.card = null;
    this.pawn = null;
    this.cardOwner = null;
  }

  @Override
  public Object getContents() {
    if (pawn != null) {
      return new ArrayList<>(pawn);
    } else if (card != null) {
      return card;
    }
    return null;
  }

  @Override
  public int getPawnCount() {
    if (pawn == null) {
      throw new IllegalStateException("Cannot get Pawn count when there are no pawns");
    }
    return pawn.size();
  }

  @Override
  public String getPawnColor() {
    if (pawn == null) {
      throw new IllegalStateException("Cannot get Pawn color when there are no pawns");
    }
    return pawn.getFirst().getColor();
  }

  @Override
  public Player getCardOwner() {
    if (cardOwner == null || card == null) {
      throw new IllegalStateException("Cannot get a Card Owner when this Cell doesn't have a Card");
    }
    return cardOwner;
  }

  @Override
  public void addCard(Card card, Player currentPlayer) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }

    this.card = card;
    this.cardOwner = currentPlayer;
  }

  @Override
  public void addPawn(Player currentPlayer) {
    if (pawn == null) {
      pawn = new ArrayList<>();
      addCorrectPawn(currentPlayer);
    } else {
      addCorrectPawn(currentPlayer);
    }
  }

  // Responsible for adding the pawn, with the correct color, to the cell
  private void addCorrectPawn(Player currentPlayer) {
    if (currentPlayer.getColor().equals("RED")) {
      pawn.add(Pawn.RED);
    } else if (currentPlayer.getColor().equals("BLUE")) {
      pawn.add(Pawn.BLUE);
    }
  }

  @Override
  public void removeAllPawns() {
    pawn = null;
  }

  // Placeholder for now
  @Override
  public String toString() {
    if (pawn != null) {
      return pawn.getFirst().getColor() + " PAWNx" + pawn.size();
    } else if (card != null) {
      return "Card: " + card.name();
    }
    return "___";
  }
}
