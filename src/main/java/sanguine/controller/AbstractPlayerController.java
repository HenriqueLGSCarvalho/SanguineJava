package sanguine.controller;

import java.io.File;
import java.util.List;
import sanguine.controller.player.SanguinePlayer;
import sanguine.model.Card;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineView;

/**
 * This class represents a controller for any player color in Sanguine.
 * Holds most of the functionality needed for a functional controller for Sanguine.
 *
 * <p>This class's constructor is protected (and not public), therefore, only classes which extend
 * it have access to this constructor. Therefore, in order to use this abstract class, a class
 * which extends it must be made.
 *
 * <p>In order for controller to actually start a game of Sanguine, one of the classes that extend
 * it must Override playGame() and add the model's startGame() to its implementation.
 *   - Only one subclass should Override playGame(), as starting the game from the model multiple
 *     times will cause crashes, weird behaviors, and is unnecessary.
 */
public class AbstractPlayerController implements SanguineControllerInterface, FeaturesListener,
        TurnListener {

  protected final SanguineModel model;
  protected final SanguineView view;
  protected final SanguinePlayer player;
  protected final Player thisPlayerColor;

  protected int currentRow;
  protected int currentColumn;
  protected int currentCard;

  /**
   * Represents an instance of the abstract player controller.
   * This constructor is PROTECTED, meanings only subclasses can access it.
   * This constructor should never be used directly to control a player.
   *
   * @param model a Sanguine model
   * @param view a Sanguine view
   * @param player a Sanguine player (either a SanguinePlayer (AI Bot) or null (Human))
   * @param thisPlayerColor the Color of the Player using this controller (should be RED or BLUE)
   */
  protected AbstractPlayerController(SanguineModel model, SanguineView view,
                              SanguinePlayer player, Player thisPlayerColor) {

    if (model == null || view == null || thisPlayerColor == null) {
      // player is allowed to be null intentionally
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.model = model;
    this.view = view;
    this.player = player;
    this.thisPlayerColor = thisPlayerColor;

    currentRow = -1;
    currentColumn = -1;
    currentCard = -1;

    view.addListener(this); // Subscribes to the View (For GUI)
    model.addTurnListener(this); // Subscribes to the Model
  }

  @Override
  public void playGame(int numRows, int numCols, int handSize, boolean shuffle,
                       File redDeck, File blueDeck) {

    view.makeVisible();
  }

  @Override
  public void onClickCell(int row, int col) {
    if (model.getCurrentPlayer() == thisPlayerColor && !model.isGameOver()) {
      System.out.println(thisPlayerColor + ": " + row + ", " + col);

      view.clickedCell(row, col);

      currentRow = row;
      currentColumn = col;
    }
  }

  @Override
  public void onClickCard(int cardIdx) {
    if (model.getCurrentPlayer() == thisPlayerColor && !model.isGameOver()) {
      System.out.println(thisPlayerColor.getColor() + " Card: " + cardIdx);

      view.clickedCard(cardIdx);

      currentCard = cardIdx;
    }
  }

  @Override
  public void onPressConfirmMove() {
    try {
      if (model.getCurrentPlayer() == thisPlayerColor && !model.isGameOver()) {
        System.out.println(thisPlayerColor.getColor() + " played a card.");

        List<Card> hand =
                (thisPlayerColor == Player.RED) ? model.getRedHand() : model.getBlueHand();

        model.playCard(hand.get(currentCard), currentRow, currentColumn);

        clearHighlights();
      }
    } catch (IllegalStateException | IllegalArgumentException | IndexOutOfBoundsException e) {
      System.out.println("Illegal Move!");
      view.showErrorPanel();
    }
  }

  @Override
  public void onPressConfirmPass() {
    if (model.getCurrentPlayer() == thisPlayerColor && !model.isGameOver()) {
      System.out.println(thisPlayerColor.getColor() + " turn passed.");

      model.passTurn();

      clearHighlights();
    }
  }

  @Override
  public void nextTurnStarted() {
    if (model.getCurrentPlayer() == thisPlayerColor && !model.isGameOver()) {
      System.out.println(thisPlayerColor.getColor() + " turn started.");

      runPlayerBotTurn();
      clearHighlights();
      view.refresh();
    }
  }

  // Runs the Bot's turn
  private void runPlayerBotTurn() {
    if (player != null) {
      System.out.println(thisPlayerColor.getColor() + " Bot has played");
      player.playTurn(model);
    }
  }

  // Clears highlighting from previous turn
  private void clearHighlights() {
    currentRow = -1;
    currentColumn = -1;
    currentCard = -1;

    view.clearHighlights();
  }
}
