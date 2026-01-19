package sanguine.controller.deck.reader;

import java.util.List;
import sanguine.model.Card;

/**
 * Responsible for reading sanguine.Sanguine deck files.
 * Converts sanguine.Sanguine deck files into List of Cards.
 */
public interface DeckReaderInterface {

  /**
   * Reads a valid file, containing a deck for the game of sanguine.Sanguine,
   * and transforms it into a List of Cards.
   *
   * @return A List of Cards which is a valid deck for sanguine.Sanguine
   * @throws IllegalArgumentException if file does not exist or cannot be accessed
   */
  List<Card> readDeck() throws IllegalArgumentException;
}
