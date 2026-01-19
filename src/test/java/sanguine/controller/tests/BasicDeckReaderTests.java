package sanguine.controller.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.controller.deck.reader.BasicDeckReader;
import sanguine.model.Card;

/**
 * This test class is responsible for testing BasicDeckReader.
 * Tests that files are correctly found and translated into usable Lists of Strings
 */
public class BasicDeckReaderTests {
  // Tests readDeck() fully reads a valid file of a deck and translates it into a List of Strings
  @Test
  public void testDeckReader() {
    String path = "docs" + File.separator + "15CardDeck1";
    File config = new File(path);

    BasicDeckReader deckReader = new BasicDeckReader(config);
    List<Card> deck = deckReader.readDeck();

    System.out.println(deck.toString());
    assertEquals(15, deck.size());
  }
}
