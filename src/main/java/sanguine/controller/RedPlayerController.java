package sanguine.controller;

import java.io.File;
import sanguine.controller.player.SanguinePlayer;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineView;

/**
 * This class represents a controller for the Red Player.
 * This class overrides playGame in order to call startGame from the model.
 */
public class RedPlayerController extends AbstractPlayerController {

  /**
   * An Instance of a Controller for Player Red.
   *
   * @param model a Sanguine model
   * @param view a Sanguine view
   * @param player a Sanguine player (either a SanguinePlayer (AI Bot) or null (Human))
   */
  public RedPlayerController(SanguineModel model, SanguineView view, SanguinePlayer player) {
    super(model, view, player, Player.RED);
  }

  // Overrides original playGame in order to start the model whenever red starts playing
  @Override
  public void playGame(int numRows, int numCols, int handSize, boolean shuffle,
                        File redDeck, File blueDeck) {

    model.startGame(numRows, numCols, handSize, shuffle, redDeck, blueDeck);
    view.makeVisible();
  }
}
