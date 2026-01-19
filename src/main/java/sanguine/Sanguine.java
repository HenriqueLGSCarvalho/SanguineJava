package sanguine;

import java.io.File;
import java.util.List;
import sanguine.controller.BluePlayerController;
import sanguine.controller.RedPlayerController;
import sanguine.controller.SanguineControllerInterface;
import sanguine.controller.player.SanguinePlayer;
import sanguine.controller.player.SanguinePlayerImpl;
import sanguine.model.BasicSanguine;
import sanguine.model.Card;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.strategy.PlayFirstPossibleCardOrPass;
import sanguine.strategy.PlayHighestValuePossibleCardOrPass;
import sanguine.view.graphical.view.SanguineView;
import sanguine.view.graphical.view.SanguineViewBlueFrame;
import sanguine.view.graphical.view.SanguineViewRedFrame;
import sanguine.view.textual.view.SanguineTextualView;

/**
 * Class which contains the main method for the game of sanguine.Sanguine.
 * Allows for the playing of a game of Sanguine.
 *
 * <p>At least 6 arguments are needed for this main method to run (more detail in constructor).
 * In order for arguments [4] & [5] to be valid, they must be "strategy1", "strategy2", or "human".
 * "strategy1" represents a Bot using the PlayFirstPossibleCardOrPass Strategy
 * "strategy2" represents a Bot using the PlayHighestValuePossibleCardOrPass Strategy
 * "human" represents a Human Player, who will select moves from the GUI manually
 */
public class Sanguine {
  /**
   * Main method for Sanguine.
   * Runs the game appropriately
   *
   * @param args given arguments, minimum of 6 are expected:
   *               - [0] number of rows
   *               - [1] number of columns
   *               - [2] Red Player's deck file
   *               - [3] Blue Player's deck file
   *               - [4] Red Player (Human or bot Strategy)
   *               - [5] Blue Player (Human or bot Strategy)
   */
  public static void main(String[] args) {
    if (args.length < 6 || args[0].isEmpty() ||  args[1].isEmpty() ||  args[2].isEmpty()
            || args[3].isEmpty() ||  args[4].isEmpty() ||  args[5].isEmpty()) {
      System.out.println("Missing argument(s)!");
      return;
    }

    // Sets Deck Files:
    File redDeckFile = new File(args[2]);
    File blueDeckFile = new File(args[3]);
    if (!redDeckFile.exists() || !blueDeckFile.exists()) {
      System.out.println("Selected file(s) do not exist");
      return;
    }

    // Sets Players:
    SanguinePlayer redPlayer;
    SanguinePlayer bluePlayer;
    switch (args[4].toLowerCase()) {
      case "human" ->
              redPlayer = null;
      case "strategy1" ->
              redPlayer = new SanguinePlayerImpl(new PlayFirstPossibleCardOrPass());
      case "strategy2" ->
              redPlayer = new SanguinePlayerImpl(new PlayHighestValuePossibleCardOrPass());
      default -> {
        System.out.println("Invalid Player!");
        return;
      }
    }
    switch (args[5].toLowerCase()) {
      case "human" ->
              bluePlayer = null;
      case "strategy1" ->
              bluePlayer = new SanguinePlayerImpl(new PlayFirstPossibleCardOrPass());
      case "strategy2" ->
              bluePlayer = new SanguinePlayerImpl(new PlayHighestValuePossibleCardOrPass());
      default -> {
        System.out.println("Invalid Player!");
        return;
      }
    }

    // Sets Titles
    String redTitle = "Player: RED";
    String blueTitle = "Player: BLUE";

    // Sets Model
    SanguineModel model = new BasicSanguine();

    // Sets Views
    SanguineView redView = new SanguineViewRedFrame((ReadOnlySanguineModel) model, redTitle);
    SanguineView blueView = new SanguineViewBlueFrame((ReadOnlySanguineModel) model, blueTitle);

    // Sets Controller
    SanguineControllerInterface redController = new RedPlayerController(model, redView,
            redPlayer);
    SanguineControllerInterface blueController = new BluePlayerController(model, blueView,
            bluePlayer);

    // Sets the board's number of rows and columns
    int numRow = Integer.parseInt(args[0]);
    int numCol = Integer.parseInt(args[1]);

    // Executes Controllers
    redController.playGame(numRow, numCol, 3,
            true, redDeckFile, blueDeckFile);
    blueController.playGame(0, 0, 0,
            false, null, null);

    /*
    // Automatic Full Game (Textual Representation)

    SanguineModel modelTest = new BasicSanguine();
    SanguineTextualView viewTextual = new BasicSanguineTextualView(modelTest);

    modelTest.startGame(3, 5, 5, false, file, file);

    while (!modelTest.isGameOver()) {
      tryAllPlays(modelTest, viewTextual);
      System.out.println(viewTextual.toString());
    }
    System.out.println("Game is over!");
     */
  }

  // Try all versions of model.playCard()
  private static void tryAllPlays(SanguineModel model, SanguineTextualView view) {
    Player player = model.getCurrentPlayer();
    List<Card> hand = (player == Player.RED) ? model.getRedHand() : model.getBlueHand();

    boolean played = false;
    // Each Card
    for (Card card : hand) {
      // Each Row
      for (int row = 0; row < model.getBoard().length && !played; row++) {
        // Each Column
        for (int col = 0; col < model.getBoard()[0].length && !played; col++) {
          // Keeping try to make a successful play
          try {
            model.playCard(card, row, col);
            played = true;
          } catch (IllegalStateException e) {
            // If a play is invalid, try the next play
          }
        }
      }
      // If a play is made, stop checking for valid plays (as one has been made)
      if (played) {
        break;
      }
    }
    // If no play was made during this player's entire turn, skip their turn
    if (!played) {
      model.passTurn();
    }
  }
}
