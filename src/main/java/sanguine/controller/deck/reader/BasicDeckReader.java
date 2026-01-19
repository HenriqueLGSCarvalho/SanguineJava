package sanguine.controller.deck.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sanguine.model.Card;

/**
 * This class is responsible for reading deck files and translating them.
 * The deck file will be translated into a List of Strings,
 * which is then transformed into a List of Cards,
 * which is the format our decks will be finalized into.
 *
 * <p>Assumes only valid deck files for the game of sanguine.Sanguine are given.
 * a valid deck file is a deck which contains at least 1 card from the game of sanguine.Sanguine.
 * a card from the game of sanguine.Sanguine contains a name, a cost, and a value, followed by a
 * 5 by 5 grid containing the influence of that card.
 *
 * <p>Uses SanguineCard Interface & Card class to create a Card from the original String
 */
public class BasicDeckReader implements DeckReaderInterface {
  private final File file;
  private final List<String> deck;

  /**
   * This creates an instance of BasicDeckReader, which has to purpose of reading a file
   * and transforming it into a valid deck which is a List of Strings,
   * which is then transformed into a List of Cards.
   * Class Invariant: deck is never null
   * - Constructor sets deck to be an empty ArrayList
   * - Methods never set deck to be null, nor would they be able to since it is final and therefore
   * cannot point to another reference
   *
   * @param file a file containing a deck for the game of sanguine.Sanguine
   * @throws IllegalArgumentException if file passed in does not exist
   */
  public BasicDeckReader(File file) {
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist");
    }
    this.file = file;
    this.deck = new ArrayList<>();
  }

  @Override
  public List<Card> readDeck() throws IllegalArgumentException {
    this.deck.clear();
    try {
      FileReader deckFile = new FileReader(this.file);
      Scanner scan = new Scanner(deckFile);
      scan.useDelimiter("\\r?\\n");

      int deckIndex = 0;
      while (scan.hasNextLine()) {
        String card = scan.nextLine() + System.lineSeparator()
                + scan.nextLine() + System.lineSeparator()
                + scan.nextLine() + System.lineSeparator()
                + scan.nextLine() + System.lineSeparator()
                + scan.nextLine() + System.lineSeparator()
                + scan.nextLine();
        this.deck.add(deckIndex, card);
        deckIndex++;
      }
      scan.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File is unaccessible");
    }

    return convertStringDeckToCardDeck(this.deck);
  }

  // Responsible for converting a List<String> into a List<Card>
  private List<Card> convertStringDeckToCardDeck(List<String> deckOfStrings) {
    List<Card> deckOfCards = new ArrayList<>();

    for (String currentCard : deckOfStrings) {
      String name = currentCard.split(" ")[0];
      int cost = Integer.parseInt(currentCard.split(" ")[1]);
      int value = Integer.parseInt(currentCard.split(" ")[2].split("\\r?\\n")[0]);
      String[] influenceGrid = new String[]{
              currentCard.split("\\r?\\n")[1],
              currentCard.split("\\r?\\n")[2],
              currentCard.split("\\r?\\n")[3],
              currentCard.split("\\r?\\n")[4],
              currentCard.split("\\r?\\n")[5],
      };

      deckOfCards.add(Card.builder()
              .name(name)
              .cost(cost)
              .value(value)
              .influenceGrid(influenceGrid).build());
    }
    return deckOfCards;
  }
}
