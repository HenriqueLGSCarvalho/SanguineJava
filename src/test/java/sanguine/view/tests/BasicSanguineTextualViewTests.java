package sanguine.view.tests;

import java.io.File;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.SanguineModel;
import sanguine.view.textual.view.BasicSanguineTextualView;
import sanguine.view.textual.view.SanguineTextualView;

/**
 * Responsible for testing the textual view.
 * tests toString
 */
public class BasicSanguineTextualViewTests {

  // Tests the textual view
  @Test
  public void basicSanguineTextualViewTest() {
    SanguineModel model = new BasicSanguine();
    SanguineTextualView view = new BasicSanguineTextualView(model);

    String path = "docs" + File.separator + "15CardDeck1";
    File deck = new File(path);

    model.startGame(3, 5, 3, false, deck, deck);
    System.out.println(view.toString());
    model.playCard(model.getRedHand().get(2), 1, 0);
    model.playCard(model.getBlueHand().get(2), 1, 4);
    System.out.println(view.toString());
  }

}
