package sanguine.strategy.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sanguine.controller.player.SanguinePlayer;
import sanguine.controller.player.SanguinePlayerImpl;
import sanguine.model.SanguineModel;
import sanguine.strategy.PlayFirstPossibleCardOrPass;
import sanguine.strategy.PlayHighestValuePossibleCardOrPass;
import sanguine.strategy.tests.strategy.mocks.ModelMockForStrategies;

/**
 * This class is responsible for testing all implemented strategies.
 * Currently implemented strategies:
 * - playHighestValuePossibleCardOrPass
 * - playFirstPossibleCardOrPass
 */
public class StrategyTest {

  // Tests that the player places the card with the highest possible value down in the
  // first valid cell
  @Test
  public void testPlayHighestValuePossibleCardOrPass() {
    Appendable log = new StringBuilder();
    SanguineModel mockModel = new ModelMockForStrategies(log);

    final SanguinePlayer player = new SanguinePlayerImpl(new PlayHighestValuePossibleCardOrPass());

    // Size of Red's Hand
    assertEquals(5, mockModel.getRedHand().size());
    // Value of all Cards in Red's Hand
    assertEquals(1, mockModel.getRedHand().get(0).value());
    assertEquals(2, mockModel.getRedHand().get(1).value());
    assertEquals(3, mockModel.getRedHand().get(2).value());
    assertEquals(4, mockModel.getRedHand().get(3).value());
    assertEquals(5, mockModel.getRedHand().get(4).value());

    player.playTurn(mockModel);

    String expectedMessage = "Played card (Card 5, Value = 5, Cost = 1) on cell [0, 0]";
    assertEquals(expectedMessage, log.toString());
  }

  // Tests that the player places the card first card on hand in the first valid cell
  @Test
  public void testPlayFirstPossibleCardOrPass() {
    Appendable log = new StringBuilder();
    SanguineModel mockModel = new ModelMockForStrategies(log);

    final   SanguinePlayer player = new SanguinePlayerImpl(new PlayFirstPossibleCardOrPass());

    // Size of Red's Hand
    assertEquals(5, mockModel.getRedHand().size());
    // Value of all Cards in Red's Hand
    assertEquals(1, mockModel.getRedHand().get(0).value());
    assertEquals(2, mockModel.getRedHand().get(1).value());
    assertEquals(3, mockModel.getRedHand().get(2).value());
    assertEquals(4, mockModel.getRedHand().get(3).value());
    assertEquals(5, mockModel.getRedHand().get(4).value());

    player.playTurn(mockModel);

    String expectedMessage = "Played card (Card 1, Value = 1, Cost = 1) on cell [0, 0]";
    assertEquals(expectedMessage, log.toString());
  }
}
