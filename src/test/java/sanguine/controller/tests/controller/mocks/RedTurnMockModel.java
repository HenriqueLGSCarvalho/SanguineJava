package sanguine.controller.tests.controller.mocks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import sanguine.controller.TurnListener;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;

/**
 * This class is a mock of a SanguineModel.
 *
 * <p>Its main purpose is to maintain a state where it is always red's turn,
 * so it is possible to see how the controller will delegate to the model during that state.
 */
public class RedTurnMockModel implements SanguineModel {

  private final Appendable log;

  /**
   * Represents an instance of the mock model for red's turn.
   *
   * @param log the log which contains all the method calls from this class
   */
  public RedTurnMockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public void startGame(int numRows, int numCols, int handSize, boolean shuffle,
                        File redDeck, File blueDeck)
          throws IllegalArgumentException, IllegalStateException {

    try {
      log.append("Game started." + System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void playCard(Card card, int row, int col) throws IllegalStateException {
    try {
      log.append("Played " + card.name() + " on cell [" + row + ", " + col + "]."
              + System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void passTurn() throws IllegalStateException {
    try {
      log.append("Red turn passed." + System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
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
    Card testRedCard1 = Card.builder()
            .name("TestRedCard1")
            .value(1)
            .cost(1)
            .influenceGrid(new String[]{""}).build();

    return List.of(testRedCard1);
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
    return new Cell[0][];
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
