package sanguine.controller;

import sanguine.controller.player.SanguinePlayer;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.view.graphical.view.SanguineView;

/**
 * This class represents a controller for the Blue Player.
 * This class does not override playGame in order to call startGame, as calling startGame twice
 * would break the program & is unnecessary.
 */
public class BluePlayerController extends AbstractPlayerController {

  /**
   * An Instance of a Controller for Player Blue.
   *
   * @param model a Sanguine model
   * @param view a Sanguine view
   * @param player a Sanguine player (either a SanguinePlayer (AI Bot) or null (Human))
   */
  public BluePlayerController(SanguineModel model, SanguineView view, SanguinePlayer player) {
    super(model, view, player, Player.BLUE);
  }
}
