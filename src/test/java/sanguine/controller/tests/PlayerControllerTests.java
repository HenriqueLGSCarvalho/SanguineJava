package sanguine.controller.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Test;
import sanguine.controller.AbstractPlayerController;
import sanguine.controller.BluePlayerController;
import sanguine.controller.RedPlayerController;
import sanguine.controller.player.SanguinePlayerImpl;
import sanguine.controller.tests.controller.mocks.BlueTurnMockModel;
import sanguine.controller.tests.controller.mocks.RedTurnMockModel;
import sanguine.controller.tests.controller.mocks.ViewMock;
import sanguine.model.BasicSanguine;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;
import sanguine.strategy.PlayFirstPossibleCardOrPass;
import sanguine.view.graphical.view.SanguineView;

/**
 * This class is responsible for testing the Controller for a game of Sanguine.
 *
 * <p>Tests the controller for both the red & blue player, which are both extended
 * from an abstract controller class
 *
 * <p>Contains both integration tests & unit tests to make sure the controller works
 * both individually without a real model & in tangent with the real model
 */
public class PlayerControllerTests {
  String path = "docs" + File.separator + "15CardDeck1";

  // Tests valid constructor calls initialize, while invalid constructor calls throw an IAE
  @Test
  public void testControllerConstructor() {
    Appendable log = new StringBuilder();
    SanguineModel model = new RedTurnMockModel(log);

    SanguineView view = new ViewMock();

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController redCon =
                      new RedPlayerController(model, null, null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController blueCon =
                      new BluePlayerController(model, null, null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController redCon =
                      new RedPlayerController(null, view, null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController blueCon =
                      new BluePlayerController(null, view, null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController redCon =
                      new RedPlayerController(null, null, null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              AbstractPlayerController blueCon =
                      new BluePlayerController(null, null, null);
            });

    // These two should be valid & initialize:
    AbstractPlayerController redCon = new RedPlayerController(model, view,
            new SanguinePlayerImpl(new PlayFirstPossibleCardOrPass()));
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);
  }

  // Tests playGame() game correctly calls the model's startGame() and starts the game
  @Test
  public void testControllerAndModelIntegrationStartGame() {
    SanguineModel model = new BasicSanguine();

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    assertFalse(model.isGameStarted());

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    assertTrue(model.isGameStarted());
  }

  // Tests the model and controller work together properly
  // Only calls methods from the controller to check if model is correctly mutated
  @Test
  public void testControllerAndModelIntegrationTurns() {
    SanguineModel model = new BasicSanguine();

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    assertTrue(model.isGameStarted());
    assertFalse(model.isGameOver());
    assertEquals(Player.RED, model.getCurrentPlayer());
    assertEquals("RED PAWNx1", model.getCell(0, 0).toString());
    assertEquals("BLUE PAWNx1", model.getCell(0, 4).toString());

    redCon.onClickCell(0, 0);
    redCon.onClickCard(0);
    redCon.onPressConfirmMove(); // red places card at [0, 0]

    assertFalse(model.isGameOver());
    assertEquals(Player.BLUE, model.getCurrentPlayer());
    assertEquals("Card: Pikachu", model.getCell(0, 0).toString());
    assertEquals("BLUE PAWNx1", model.getCell(0, 4).toString());

    blueCon.onClickCell(0, 4);
    blueCon.onClickCard(0);
    blueCon.onPressConfirmMove(); // blue places card at [0, 4]

    assertFalse(model.isGameOver());
    assertEquals(Player.RED, model.getCurrentPlayer());
    assertEquals("Card: Pikachu", model.getCell(0, 0).toString());
    assertEquals("Card: Pikachu", model.getCell(0, 4).toString());

    redCon.onPressConfirmPass();

    assertFalse(model.isGameOver()); // passes red turn
    assertEquals(Player.BLUE, model.getCurrentPlayer());

    blueCon.onPressConfirmPass(); // passes blue turn

    assertTrue(model.isGameOver());
  }

  // Unit Blue Controller Test; Tests if a card is placed on a cell, if a move is attempted
  @Test
  public void testRedControllerPlaceCardOnCell() {
    Appendable log = new StringBuilder();
    SanguineModel model = new RedTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    redCon.onClickCard(0);
    redCon.onClickCell(0, 0); // should be ignored
    redCon.onClickCell(0, 2);
    redCon.onPressConfirmMove();

    String expectedLog = "Game started." + System.lineSeparator()
            + "Played TestRedCard1 on cell [0, 2]." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }

  // Unit Red Controller Test; Tests if a card is placed on a cell, if a move is attempted
  @Test
  public void testBlueControllerPlaceCardOnCell() {
    Appendable log = new StringBuilder();
    SanguineModel model = new BlueTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    blueCon.onClickCard(1);
    blueCon.onClickCell(0, 0); // should be ignored
    blueCon.onClickCell(2, 4);
    blueCon.onPressConfirmMove();

    String expectedLog = "Game started." + System.lineSeparator()
            + "Played TestBlueCard2 on cell [2, 4]." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }

  // Unit Red Controller Test; Tests if the player tries to pass, they pass
  @Test
  public void testRedControllerPassTurn() {
    Appendable log = new StringBuilder();
    SanguineModel model = new RedTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    redCon.onPressConfirmPass();

    String expectedLog = "Game started." + System.lineSeparator()
            + "Red turn passed." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }

  // Unit Blue Controller Test; Tests if the player tries to pass, they pass
  @Test
  public void testBlueControllerPassTurn() {
    Appendable log = new StringBuilder();
    SanguineModel model = new BlueTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    blueCon.onPressConfirmPass();

    String expectedLog = "Game started." + System.lineSeparator()
            + "Blue turn passed." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }

  // Unit Red Controller Test; Tests Blue cannot place card during Red's turn
  @Test
  public void testBlueInputIgnoredWhenRedTurn() {
    Appendable log = new StringBuilder();
    SanguineModel model = new RedTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    blueCon.onClickCard(0);
    blueCon.onClickCell(0, 2);
    blueCon.onPressConfirmMove();

    blueCon.onPressConfirmPass();

    String expectedLog = "Game started." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }

  // Unit Blue Controller Test; Tests Red cannot place card during Blue's turn
  @Test
  public void testRedInputIgnoredWhenBlueTurn() {
    Appendable log = new StringBuilder();
    SanguineModel model = new BlueTurnMockModel(log);

    SanguineView view = new ViewMock();

    AbstractPlayerController redCon = new RedPlayerController(model, view, null);
    AbstractPlayerController blueCon = new BluePlayerController(model, view, null);

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Above is the MVC set up ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    redCon.playGame(3, 5, 3, false, file, file);
    blueCon.playGame(3, 5, 3, false, file, file);

    redCon.onClickCard(0);
    redCon.onClickCell(2, 4);
    redCon.onPressConfirmMove();

    redCon.onPressConfirmPass();

    String expectedLog = "Game started." + System.lineSeparator();

    assertEquals(expectedLog, log.toString());
  }
}
