package sanguine.model.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguine;
import sanguine.model.Card;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Pawn;
import sanguine.model.enums.Player;
import sanguine.view.textual.view.BasicSanguineTextualView;
import sanguine.view.textual.view.SanguineTextualView;


/**
 * Responsible for testing behaviors of BasicSanguine.
 * Also ensures Observers properly do their job
 */
public class BasicSanguineTests {

  // Tests BasicSanguine Constructor works when called in a valid state
  @Test
  public void testBasicSanguineConstructor() {
    SanguineModel model = new BasicSanguine();
  }

  // Tests startGame() when given valid arguments, in a valid state
  @Test
  public void testStartGame() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    assertFalse(model.isGameStarted());
    model.startGame(3, 3, 3, false, file, file);

    assertTrue(model.isGameStarted());
    assertEquals(9, model.getBoardSize());
    assertEquals(15 - model.getRedHand().size(), model.getRedDeck().size());
    assertEquals(4, model.getRedHand().size());
    assertEquals(3, model.getBlueHand().size());

  }

  // Tests the decks and hands are correctly initialized and set after a valid startGame() is called
  @Test
  public void testDeckAndHandInitializationAfterValidStartGame() {
    BasicSanguine model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    model.startGame(3, 3, 2, false, file, file);

    // checks each deck is the correct size
    assertEquals(15, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(15, model.getBlueDeck().size() + model.getBlueHand().size());
    // checks each hand is the correct size
    assertEquals(3, model.getRedHand().size());
    assertEquals(2, model.getBlueHand().size());

    String expectedRedHandCard = "Pikachu 1 1\n"
        + "XXXXX\n" + "XXXXX\n" + "XXCXI\n" + "XXXXX\n" + "XXXXX";
    String expectedBlueHandCard = "Pikachu 1 1\n"
        + "XXXXX\n" + "XXXXX\n" + "IXCXX\n" + "XXXXX\n" + "XXXXX";
    String actualCardRedHand = model.getRedHand().getFirst().toString();
    String actualCardBlueHand = model.getBlueHand().getFirst().toString();

    // checks the first card in each hand is the correct card
    assertEquals(expectedRedHandCard, actualCardRedHand);
    assertEquals(expectedBlueHandCard, actualCardBlueHand);

    String expectedRedDeckCard = "Charmeleon 1 1\n"
        + "XXXXX\n" + "XXXXX\n" + "XICIX\n" + "XXXXX\n" + "XXXXX";
    String expectedBlueDeckCard = "Charmander 1 1\n"
        + "XXXXX\n" + "XXXXX\n" + "XICIX\n" + "XXXXX\n" + "XXXXX";
    String actualCardRedDeck = model.getRedDeck().getFirst().toString();
    String actualCardBlueDeck = model.getBlueDeck().getFirst().toString();

    // Checks the first card in the deck (after distributing the hand) is correct
    assertEquals(expectedRedDeckCard, actualCardRedDeck);
    assertEquals(expectedBlueDeckCard, actualCardBlueDeck);
  }

  // Tests flipping of the influence grid of the blue deck Cards after initializing the decks
  @Test
  public void testFlippingBlueInfluenceGrids() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    String[] expectedRedInfluenceGrid = {
        "XXXXX",
        "XXXXX",
        "XXCXI",
        "XXXXX",
        "XXXXX"};
    String[] expectedBlueInfluenceGrid = {
        "XXXXX",
        "XXXXX",
        "IXCXX",
        "XXXXX",
        "XXXXX"};

    model.startGame(3, 3, 1, false, file, file);

    String[] actualRedInfluenceGrid = model.getRedHand().get(0).influenceGrid();
    String[] actualBlueInfluenceGrid = model.getBlueHand().get(0).influenceGrid();

    assertArrayEquals(expectedRedInfluenceGrid, actualRedInfluenceGrid);
    assertArrayEquals(expectedBlueInfluenceGrid, actualBlueInfluenceGrid);
  }

  // Tests startGame() when called with invalid arguments (or in invalid states)
  @Test
  public void testStartGameInvalid() {
    SanguineModel model = new BasicSanguine();

    // Default deck; 15 cards & valid
    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);
    // Alternate deck; 20 cards & valid
    String path3 = "docs" + File.separator + "20CardDeck1";
    final File deck20Card = new File(path3);
    // Invalid deck; 15 cards, but 3 duplicate of same card (pikachu)
    String path2 = "docs" + File.separator + "invalid.decks"
        + File.separator + "3RepeatCards_15TotalCards";
    final File repeat3Card = new File(path2);

    // Tests invalid number of rows for board
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(-1, 3, 5, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(0, 3, 5, false, file, file));
    // Tests invalid number of columns for board
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, -1, 5, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 0, 5, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 1, 5, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 2, 5, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 6, 5, false, file, file));
    // Tests invalid hand size
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 3, -1, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 3, 0, false, file, file));
    assertThrows(IllegalArgumentException.class,
        () -> model.startGame(3, 3, 6, false, file, file));
    // Tests invalid decks
    assertThrows(IllegalArgumentException.class, () ->
        model.startGame(3, 3, 5, false, repeat3Card, file));
    assertThrows(IllegalArgumentException.class, () ->
        model.startGame(3, 3, 5, false, file, repeat3Card));
    assertThrows(IllegalArgumentException.class, () ->
        model.startGame(3, 3, 5, false, deck20Card, file));
    assertThrows(IllegalArgumentException.class, () ->
        model.startGame(6, 3, 5, false, file, file));
    // Test startGame() when game has already started
    model.startGame(3, 3, 5, false, file, file);
    assertThrows(IllegalStateException.class,
        () -> model.startGame(3, 3, 5, false, file, file));
  }

  // Tests calling passTurn() in a valid state, and tests isGameOver() works properly
  @Test
  public void testPassTurnAndIsGameOver() {
    SanguineModel model = new BasicSanguine();
    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    // Tests invalid calls of passTurn() and isGameOver() prior to calling startGame()
    assertThrows(IllegalStateException.class, () -> model.passTurn());
    assertThrows(IllegalStateException.class, () -> model.isGameOver());

    model.startGame(3, 3, 5, false, file, file);
    // Tests game starts with Red as the current player, and tests game isn't over
    assertEquals(Player.RED, model.getCurrentPlayer());
    assertFalse(model.isGameOver());

    model.passTurn();
    // Tests passTurn changes the turn to the other player, and tests game isn't over yet
    assertEquals(Player.BLUE, model.getCurrentPlayer());
    assertFalse(model.isGameOver());

    model.passTurn();
    // Test pass turn changes turn back to other player, and tests
    // game is over when the players pass their turns back-to-back
    assertEquals(Player.RED, model.getCurrentPlayer());
    assertTrue(model.isGameOver());
  }

  // Tests drawCard() works properly when called
  @Test
  public void testDrawCard() {
    SanguineModel model = new BasicSanguine();
    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    // tests invalid call of drawCard() prior to calling startGame()
    assertThrows(IllegalStateException.class, () -> model.drawCard());

    model.startGame(3, 3, 3, false, file, file);

    // size of deck and hand prior to drawing card
    assertEquals(15 - 4, model.getRedDeck().size());
    assertEquals(4, model.getRedHand().size());

    model.drawCard();
    // tests size of deck and hand after drawing 1 card
    assertEquals(15 - 5, model.getRedDeck().size());
    assertEquals(5, model.getRedHand().size());

    for (int turn = 0; turn < 50; turn++) {
      model.drawCard();
    }
    // tests getDraw() doesn't crash program when attempting to draw from empty deck
    assertEquals(0, model.getRedDeck().size());
    assertEquals(15, model.getRedHand().size());
  }

  // Tests playCard() And getScore() works when given a valid call after starting game
  @Test
  public void testValidPlayCardAndGetScore() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    String[] influenceGrid = {"XXXXX", "XXXXX", "XICII", "XXXXX", "XXXXX"};
    Card card = Card.builder().name("Test").cost(1).value(1).influenceGrid(influenceGrid).build();

    assertThrows(IllegalStateException.class, () -> model.playCard(card, 0, 0));
    assertThrows(IllegalStateException.class, () -> model.getScore());
    model.startGame(3, 3, 5, false, file, file);

    // Tests getScore() is initialized correctly
    int[] initialScore = {0, 0};
    assertArrayEquals(initialScore, model.getScore());

    // Tests playCard() ends player's turn after used and switches to the next player
    assertEquals(Player.RED, model.getCurrentPlayer());
    model.playCard(card, 1, 0);
    assertEquals(Player.BLUE, model.getCurrentPlayer());

    // Tests playCard() places the correct Card in the correct Cell
    assertEquals(card, model.getCell(1, 0).getContents());

    // Tests getScore() updates correctly
    int[] expectedScore = {1, 0};
    assertArrayEquals(expectedScore, model.getScore());
  }


  @Test
  public void testPlayCardsInfluence() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    String[] influenceGrid1 = {
        "XXXXX",
        "XXXXX",
        "XICII",
        "XXIXX",
        "XXXXX"};
    Card card1 = Card.builder().name("Test").cost(1).value(1).influenceGrid(influenceGrid1).build();
    String[] influenceGrid2 = {
        "XXXXX",
        "XXIXX",
        "XXCXX",
        "XXXXX",
        "XXXXX"};
    final Card card2 =
            Card.builder().name("Test").cost(1).value(1).influenceGrid(influenceGrid2).build();
    String[] influenceGrid3 = {
        "XXXXX",
        "XXXXX",
        "XXCXX",
        "XIXXX",
        "XXXXX"};
    final Card card3 =
            Card.builder().name("Test").cost(1).value(1).influenceGrid(influenceGrid3).build();

    model.startGame(3, 3, 1, false, file, file);

    model.playCard(card1, 0, 0);
    model.passTurn();

    // Tests when a Card influences an empty Cell, adds one pawn to the cell
    assertEquals(List.of(Pawn.RED), model.getCell(0, 1).getContents());
    // Tests when a Card influences a Cell with other player's pawn, the pawn's become yours
    assertEquals(List.of(Pawn.RED), model.getCell(0, 2).getContents());
    // Tests when a Card influences a Cell with 1 Pawn, it adds 1 Pawn, and becomes 2 Pawns
    assertEquals(List.of(Pawn.RED, Pawn.RED), model.getCell(1, 0).getContents());
    // Tests when a Card influences a Cell with 2 Pawn, it adds 1 Pawn, and becomes 3 Pawns
    model.playCard(card2, 2, 0);
    model.passTurn();
    assertEquals(List.of(Pawn.RED, Pawn.RED, Pawn.RED), model.getCell(1, 0).getContents());
    // Tests when a Card influences a Cell with 3 pawns nothing happens (no 4th pawn)
    model.playCard(card3, 0, 1);
    model.passTurn();
    assertEquals(List.of(Pawn.RED, Pawn.RED, Pawn.RED), model.getCell(1, 0).getContents());
    // Tests that a Card's Influence on a Cell that is out of bounds won't crash the program
    assertThrows(IllegalArgumentException.class, () -> model.getCell(1, -1).getContents());
  }

  // Tests playCard will correctly throw exceptions when called in an Invalid State
  @Test
  public void testInvalidPlayCard() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    String[] influenceGrid = {
        "XXXXX",
        "XXXXX",
        "XXCXX",
        "XXIXX",
        "XXXXX"};
    Card card = Card.builder().name("Test").cost(1).value(1).influenceGrid(influenceGrid).build();
    final Card cardCost2 = Card.builder()
        .name("Test").cost(2).value(1).influenceGrid(influenceGrid).build();

    assertThrows(IllegalStateException.class, () -> model.playCard(card, 0, 0));
    model.startGame(3, 3, 5, false, file, file);

    // Tests invalid arguments for playCard
    assertThrows(IllegalArgumentException.class, () -> model.playCard(null, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, -1, -1));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, -1, 0));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, 0, -1));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, 3, 3));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, 3, 0));
    assertThrows(IllegalArgumentException.class, () -> model.playCard(card, 0, 3));

    // Tests playCard() when moving Card onto an empty cell
    assertThrows(IllegalStateException.class, () -> model.playCard(card, 0, 1));
    // Tests playCard() when moving Card onto a Cell with fewer pawns than the Card's cost
    assertThrows(IllegalStateException.class, () -> model.playCard(cardCost2, 0, 0));
    // Tests playCard() when moving Card onto a Cell with the other player's Pawn
    assertThrows(IllegalStateException.class, () -> model.playCard(card, 0, 2));
    // Tests playCard() when moving Card onto a Cell with another Card
    model.playCard(card, 0, 0);
    model.passTurn(); // other player needs to skip turn
    assertThrows(IllegalStateException.class, () -> model.playCard(card, 0, 0));
  }

  // Tests all the Observer Methods will throw an exceptions when called in an invalid state
  // Also tests if game starts appropriately
  @Test
  public void testInvalidObserverMethodCalls() {
    SanguineModel model = new BasicSanguine();

    String path = "docs" + File.separator + "15CardDeck1";
    final File file = new File(path);

    assertFalse(model.isGameStarted());
    assertThrows(IllegalStateException.class, () -> model.getCurrentPlayer());
    assertThrows(IllegalStateException.class, () -> model.getRedDeck());
    assertThrows(IllegalStateException.class, () -> model.getBlueDeck());
    assertThrows(IllegalStateException.class, () -> model.getRedHand());
    assertThrows(IllegalStateException.class, () -> model.getBlueHand());
    assertThrows(IllegalStateException.class, () -> model.getCell(0, 0));
    assertThrows(IllegalStateException.class, () -> model.getBoard());
    assertThrows(IllegalStateException.class, () -> model.getBoardSize());
    assertThrows(IllegalStateException.class, () -> model.getScore());
    assertThrows(IllegalStateException.class, () -> model.isGameOver());
    assertThrows(IllegalStateException.class, () -> model.getRedRowScores());
    assertThrows(IllegalStateException.class, () -> model.getBlueRowScores());


    model.startGame(3, 3, 1, false, file, file);
    assertTrue(model.isGameStarted());
  }

  // Tests a sequence of valid plays; a complete game with an ending
  @Test
  public void testGameSequence() {
    SanguineModel model = new BasicSanguine();
    final SanguineTextualView view = new BasicSanguineTextualView(model);
    String path = "docs" + File.separator + "15CardDeck1";
    File file = new File(path);

    assertFalse(model.isGameStarted());
    model.startGame(3, 5, 3, false, file, file);

    // Initial Game State Tests:
    assertTrue(model.isGameStarted());
    assertEquals(15, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(15, model.getBlueDeck().size() + model.getBlueHand().size());
    assertEquals(Player.RED, model.getCurrentPlayer());
    int[] initialExpectedScore = {0, 0};
    assertArrayEquals(initialExpectedScore, model.getScore());
    assertFalse(model.isGameOver());

    // First Sequence
    assertEquals(4, model.getRedHand().size());
    model.playCard(model.getRedHand().get(0), 1, 0); // Pikachu
    assertEquals(4, model.getBlueHand().size());
    model.playCard(model.getBlueHand().get(0), 1, 4); // Pikachu

    // Tests after first 2 Moves
    assertEquals(Player.RED, model.getCell(1, 0).getCardOwner());
    assertEquals(Player.BLUE, model.getCell(1, 4).getCardOwner());
    assertEquals(model.getCell(1, 2).getContents(), List.of(Pawn.BLUE));
    assertEquals(14, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(14, model.getBlueDeck().size() + model.getBlueHand().size());
    assertFalse(model.isGameOver());

    // Second Sequence
    assertEquals(4, model.getRedHand().size());
    model.playCard(model.getRedHand().get(2), 2, 0);  // Charmeleon
    assertEquals(4, model.getBlueHand().size());
    model.playCard(model.getBlueHand().get(2), 1, 2); // Charmeleon

    // Tests after next 2 Moves
    assertEquals(Player.RED, model.getCell(2, 0).getCardOwner());
    assertEquals(Player.BLUE, model.getCell(1, 2).getCardOwner());
    assertEquals(model.getCell(2, 1).getContents(), List.of(Pawn.RED));
    assertEquals(model.getCell(1, 1).getContents(), List.of(Pawn.BLUE));
    assertEquals(model.getCell(1, 3).getContents(), List.of(Pawn.BLUE));
    assertEquals(13, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(13, model.getBlueDeck().size() + model.getBlueHand().size());
    assertFalse(model.isGameOver());

    // Third Sequence
    assertEquals(4, model.getRedHand().size());
    model.playCard(model.getRedHand().get(2), 0, 0);  // Bulbasaur
    assertEquals(4, model.getBlueHand().size());
    model.playCard(model.getBlueHand().get(2), 1, 1); // Bulbasaur

    // Tests after next 2 Moves
    assertEquals(Player.RED, model.getCell(0, 0).getCardOwner());
    assertEquals(Player.BLUE, model.getCell(1, 1).getCardOwner());
    assertEquals(model.getCell(0, 1).getContents(), List.of(Pawn.BLUE));
    assertEquals(model.getCell(2, 1).getContents(), List.of(Pawn.BLUE));
    assertEquals(12, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(12, model.getBlueDeck().size() + model.getBlueHand().size());
    assertFalse(model.isGameOver());

    // Fourth Sequence
    assertEquals(4, model.getRedHand().size());
    model.passTurn(); // Pass
    assertEquals(4, model.getBlueHand().size());
    model.playCard(model.getBlueHand().get(1), 2, 1); // Charmander

    // Tests after next 2 Moves
    assertEquals(Player.BLUE, model.getCell(2, 1).getCardOwner());
    assertEquals(model.getCell(2, 2).getContents(), List.of(Pawn.BLUE));
    assertEquals(12, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(11, model.getBlueDeck().size() + model.getBlueHand().size());
    assertFalse(model.isGameOver());

    // Fifth Sequence
    assertEquals(5, model.getRedHand().size());
    model.passTurn(); // Pass
    assertEquals(4, model.getBlueHand().size());
    model.passTurn(); // Pass

    // Final Game State Tests:
    assertEquals(12, model.getRedDeck().size() + model.getRedHand().size());
    assertEquals(11, model.getBlueDeck().size() + model.getBlueHand().size());
    int[] expectedFinalScore = {1, 3};
    assertArrayEquals(expectedFinalScore, model.getScore());
    assertTrue(model.isGameOver());

    System.out.println(view.toString());
  }
}
