package sanguine.controller.stub.controller;

import java.io.File;
import sanguine.controller.FeaturesListener;
import sanguine.controller.TurnListener;
import sanguine.controller.player.SanguinePlayer;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineView;

/**
 * Placeholder asynchronous controller.
 * The Subscriber (The class that wants to react to events)
 */
public class StubSanguineController implements StubSanguineControllerInterface, FeaturesListener,
        TurnListener {

  private final SanguineModel model;
  private final SanguineView redView;
  private final SanguineView blueView;

  private int currentRow;
  private int currentColumn;
  private int currentCard;

  private SanguinePlayer redBot;
  private SanguinePlayer blueBot;

  /**
   * Represents an instance of this placeholder controller.
   */
  public StubSanguineController(SanguineModel model, SanguineView view1, SanguineView view2) {
    if (model == null || view1 == null || view2 == null) {
      throw new IllegalArgumentException("View and model cannot be null");
    }

    this.model = model;
    this.redView = view1;
    this.blueView = view2;

    this.redView.addListener(this);
    this.blueView.addListener(this);
    this.model.addTurnListener(this);

    currentRow = -1;
    currentColumn = -1;
    currentCard = -1;

    redBot = null;
    blueBot = null;
  }

  @Override
  public void setRedBot(SanguinePlayer redBot) {
    this.redBot = redBot;
  }

  @Override
  public void setBlueBot(SanguinePlayer blueBot) {
    this.blueBot = blueBot;
  }

  @Override
  public void playGame(int numRows, int numCols, int handSize, boolean shuffle,
                       File redDeck, File blueDeck) {
    model.startGame(numRows, numCols, handSize, shuffle, redDeck, blueDeck);
    blueView.makeVisible();
    redView.makeVisible();

    if (redBot != null) {
      runBotTurn();
    }
  }

  @Override
  public void onClickCell(int row, int col) {
    if (model.getCurrentPlayer() == Player.RED) {
      redView.clickedCell(row, col);
      currentRow = row;
      currentColumn = col;
    } else if (model.getCurrentPlayer() == Player.BLUE) {
      blueView.clickedCell(row, col);
      currentRow = row;
      currentColumn = col;
    }
    System.out.println(row + ", " + col);
  }

  @Override
  public void onClickCard(int cardIdx) {
    /*
    if (cardOwner == Player.RED) {
      redView.clickedCard(cardIdx);
      System.out.print("Red's Card: ");
    } else {
      blueView.clickedCard(cardIdx);
      System.out.print("Blue's Card: ");
    }
     */
    currentCard = cardIdx;

    System.out.println(cardIdx);
  }

  @Override
  public void onPressConfirmMove() {
    System.out.println(model.getCurrentPlayer() + " played a card.");
    if (model.getCurrentPlayer() == Player.RED) {
      model.playCard(model.getRedHand().get(currentCard), currentRow, currentColumn);
    } else if (model.getCurrentPlayer() == Player.BLUE) {
      model.playCard(model.getBlueHand().get(currentCard), currentRow, currentColumn);
    }
    clearHighlights();

    runBotTurn();
  }

  @Override
  public void onPressConfirmPass() {

    System.out.println(model.getCurrentPlayer() + " turn passed.");
    model.passTurn();
    clearHighlights();

    runBotTurn();
  }

  // Clears highlighting from previous turn
  private void clearHighlights() {
    currentRow = -1;
    currentColumn = -1;
    currentCard = -1;

    redView.clearHighlights();
    blueView.clearHighlights();
  }

  // Runs the Bot's turn
  private void runBotTurn() {
    boolean ranAi = false;

    if (model.getCurrentPlayer() == Player.RED && redBot != null) {
      redBot.playTurn(model);
      ranAi = true;
    } else if (model.getCurrentPlayer() == Player.BLUE && blueBot != null) {
      blueBot.playTurn(model);
      ranAi = true;
    }

    if (ranAi) {
      redView.refresh();
      blueView.refresh();

      // Recursive Call; Ensures that if both players are AI, they continue to play
      runBotTurn();
    }
  }

  @Override
  public void nextTurnStarted() {
    System.out.println(model.getCurrentPlayer() + " turn started.");
  }
}
