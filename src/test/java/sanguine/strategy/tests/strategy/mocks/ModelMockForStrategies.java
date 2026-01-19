package sanguine.strategy.tests.strategy.mocks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import sanguine.controller.TurnListener;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;

/**
 * A mock SanguineModel.
 * Usage is for testing strategies correctly pick and place a card from red's hand.
 */
public class ModelMockForStrategies implements SanguineModel {
  Appendable log;

  /**
   * Represents an instance of this mock model.
   *
   * @param log logs which methods have been called (which append to the log).
   */
  public ModelMockForStrategies(Appendable log) {
    this.log = log;
  }

  @Override
  public void startGame(int numRows, int numCols, int handSize,
                        boolean shuffle, File redDeck, File blueDeck)
          throws IllegalArgumentException, IllegalStateException {
  }

  @Override
  public void playCard(Card card, int row, int col) throws IllegalStateException {
    try {
      log.append("Played card (" + card.name() + ", Value = " + card.value()
              + ", Cost = " + card.cost() + ")" + " on cell [" + row + ", " + col + "]");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void passTurn() throws IllegalStateException {
  }

  @Override
  public void drawCard() throws IllegalStateException {

  }

  @Override
  public void addTurnListener(TurnListener turnListener) {

  }

  @Override
  public boolean isGameStarted() {
    return true;
  }

  @Override
  public Player getCurrentPlayer() {
    return Player.RED;
  }

  @Override
  public List<Card> getRedDeck() throws IllegalStateException {
    return List.of();
  }

  @Override
  public List<Card> getBlueDeck() throws IllegalStateException {
    return List.of();
  }

  @Override
  public List<Card> getRedHand() throws IllegalStateException {
    return List.of(
            Card.builder().name("Card 1").cost(1).value(1).influenceGrid(new String[]{""}).build(),
            Card.builder().name("Card 2").cost(1).value(2).influenceGrid(new String[]{""}).build(),
            Card.builder().name("Card 3").cost(1).value(3).influenceGrid(new String[]{""}).build(),
            Card.builder().name("Card 4").cost(1).value(4).influenceGrid(new String[]{""}).build(),
            Card.builder().name("Card 5").cost(1).value(5).influenceGrid(new String[]{""}).build()
    );
  }

  @Override
  public List<Card> getBlueHand() throws IllegalStateException {
    return List.of();
  }

  @Override
  public Cell getCell(int row, int col) throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Cell[][] getBoard() throws IllegalStateException {
    Cell[][] board = new Cell[3][5];

    /*
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        board[row][col] = new Cell();
      }
    }

    board[0][0].addPawn(Player.RED); // first pawn at  [0, 0]
    board[0][1].addPawn(Player.RED); // first pawn at  [0, 1]
    board[0][2].addPawn(Player.RED); // first pawn at  [0, 2]
    board[0][3].addPawn(Player.RED); // first pawn at  [0, 3]
    board[0][3].addPawn(Player.RED); // second pawn at [0, 3]
     */

    return board;
  }

  @Override
  public int getBoardSize() throws IllegalStateException {
    return 0;
  }

  @Override
  public int[] getScore() throws IllegalStateException {
    return new int[0];
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int[] getRedRowScores() throws IllegalStateException {
    return new int[0];
  }

  @Override
  public int[] getBlueRowScores() throws IllegalStateException {
    return new int[0];
  }
}
