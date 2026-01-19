package sanguine.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import sanguine.controller.TurnListener;
import sanguine.controller.deck.reader.BasicDeckReader;
import sanguine.model.enums.Pawn;
import sanguine.model.enums.Player;
import sanguine.model.enums.Status;

/**
 * This class represents a game of sanguine.Sanguine with the basic ruleset.
 * All observer methods return a copy, and not the actual reference to the field
 */
public class BasicSanguine implements SanguineModel {
  private List<Card> redDeck;
  private List<Card> blueDeck;
  private final List<Card> redHand;
  private final List<Card> blueHand;
  // INVARIANT: Board is never null (constructor initializes it, methods don't change it to null)
  private Cell[][] board;
  private int[] redRowScores;
  private int[] blueRowScores;
  private Status status;
  private Player currentPlayer;
  private boolean redPassedLastTurn;
  private boolean bluePassedLastTurn;

  private final List<TurnListener> turnListeners;

  /**
   * Represents a regular game of sanguine.Sanguine.
   * Sets up game on an empty configurations, which allows for safe initialization
   */
  public BasicSanguine() {
    this.status = Status.NOT_STARTED;
    this.currentPlayer = Player.RED;
    this.redHand = new ArrayList<>();
    this.blueHand = new ArrayList<>();
    this.board = new Cell[0][0];
    this.redPassedLastTurn = false;
    this.bluePassedLastTurn = false;
    this.turnListeners = new ArrayList<>();
  }

  @Override
  public void addTurnListener(TurnListener turnListener) {
    this.turnListeners.add(turnListener);
  }

  // Listens for the next player's turn starting (if there is a subscriber listening)
  private void nextTurnStarted() {
    if (turnListeners != null) {
      for (TurnListener listener : turnListeners) {
        listener.nextTurnStarted();
      }
    }
  }

  @Override
  public void startGame(int numRows, int numCols, int handSize, boolean shuffle,
                        File redDeckFile, File blueDeckFile)
          throws IllegalArgumentException, IllegalStateException {
    if (status == Status.STARTED) {
      throw new IllegalStateException("Game has already started");
    }
    if (numRows <= 0) {
      throw new IllegalArgumentException("Number of rows must be positive");
    }
    if (numCols <= 1 || numCols % 2 == 0) {
      throw new IllegalArgumentException("Number of columns must be both greater than 1 and odd");
    }

    initializeBoard(numRows, numCols);
    setDecks(redDeckFile, blueDeckFile);
    checkValidDeck();

    if (handSize <= 0 || this.redDeck.size() / 3 < handSize) {
      throw new IllegalArgumentException(
              "Hands size must both be positive and no more than 1/3rd of the size of the deck");
    }

    this.status = Status.STARTED;

    if (shuffle) {
      Collections.shuffle(this.redDeck);
      Collections.shuffle(this.blueDeck);
    }
    setHands(handSize);

    this.redRowScores = new int[numRows];
    Arrays.fill(redRowScores, 0);
    this.blueRowScores = new int[numRows];
    Arrays.fill(blueRowScores, 0);

    drawCard(); // First Player draws a card upon their first turn

    nextTurnStarted(); // A turn has started (Red Player's Turn)
  }

  // Initializes the board with the correct number of rows & columns (only meant for start of game)
  private void initializeBoard(int numRows, int numCols) {
    this.board = new Cell[numRows][numCols];
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        if (board[row][col] == board[row][0] || board[row][col] == board[row][numCols - 1]) {
          board[row][0] = new Cell(Pawn.RED);
          board[row][numCols - 1] = new Cell(Pawn.BLUE);
        } else {
          board[row][col] = new Cell();
        }
      }
    }
  }

  // Checks if the red & blue decks are valid
  private void checkValidDeck() {
    if (this.redDeck.size() != this.blueDeck.size()) {
      throw new IllegalArgumentException("Both player's deck must have the same amount of cards");
    }
    if (this.redDeck.size() < (board.length * board[0].length)
            && this.blueDeck.size() < (board.length * board[0].length)) {
      throw new IllegalArgumentException(
              "Decks must have enough cards to at least fill every cell of the board");
    }
    checkFor3xDuplicates();
  }

  // Ensures the deck does not have more than 2 of each card, if it does, throws exception
  private void checkFor3xDuplicates() {
    for (Card card1 : redDeck) {
      int count = 0;
      for (Card card2 : redDeck) {
        if (card1.equals(card2)) {
          count++;
        }
      }
      if (count > 2) {
        throw new IllegalArgumentException(
                "Red's Deck contains more than 2 of the same card (" + card1.name() + ")");
      }
    }

    for (Card card1 : blueDeck) {
      int count = 0;
      for (Card card2 : blueDeck) {
        if (card1.equals(card2)) {
          count++;
        }
      }
      if (count > 2) {
        throw new IllegalArgumentException(
                "Blue's Deck contains more than 2 of the same card (" + card1.name() + ")");
      }
    }
  }

  // Sets both the red and blue player's decks
  private void setDecks(File redDeckFile, File blueDeckFile) {
    this.redDeck = new BasicDeckReader(redDeckFile).readDeck();
    this.blueDeck = new BasicDeckReader(blueDeckFile).readDeck();

    flipBlueInfluenceGrid(this.blueDeck);
  }

  // Flips the blue deck's card's influence grids
  void flipBlueInfluenceGrid(List<Card> blueDeck) {
    List<Card> flippedBlueDeck = new ArrayList<>();

    for (Card card : blueDeck) {
      String[] originalInfluenceGrid = card.influenceGrid();
      String[] newInfluenceGrid = new String[originalInfluenceGrid.length];

      for (int row = 0; row < originalInfluenceGrid.length; row++) {
        String firstCell = originalInfluenceGrid[row].substring(0, 1);
        String secondCell = originalInfluenceGrid[row].substring(1, 2);
        String thirdCell = originalInfluenceGrid[row].substring(2, 3);
        String fourthCell = originalInfluenceGrid[row].substring(3, 4);
        String fifthCell = originalInfluenceGrid[row].substring(4, 5);
        newInfluenceGrid[row] = fifthCell + fourthCell + thirdCell + secondCell + firstCell;
      }
      flippedBlueDeck.add(Card.builder()
              .name(card.name())
              .cost(card.cost())
              .value(card.value())
              .influenceGrid(newInfluenceGrid).build());
    }

    this.blueDeck = flippedBlueDeck;
  }

  // Sets both the red and blue player's hands
  private void setHands(int handSize) {
    // sets the hands
    for (int index = 0; index < handSize; index++) {
      Card currentRedCard = redDeck.get(index);
      Card currentBlueCard = blueDeck.get(index);

      redHand.add(currentRedCard);
      blueHand.add(currentBlueCard);
    }
    // removes the cards given to the hands from the decks
    redDeck.subList(0, handSize).clear();
    blueDeck.subList(0, handSize).clear();
  }

  @Override
  public void playCard(Card card, int row, int col) throws IllegalStateException {
    checkGameStarted();
    if (card == null) {
      throw new IllegalArgumentException("Card is null");
    }
    if (row < 0 || col < 0 || row > board.length - 1 || col > board[0].length - 1) {
      throw new IllegalArgumentException("Cell is out of bounds");
    }

    Cell currentCell = board[row][col];
    if (currentCell.getContents() == null) {
      throw new IllegalStateException("Cannot place a card - No pawns on this cell");
    }
    if (currentCell.getContents() instanceof Card) {
      throw new IllegalStateException("Cannot place a card - Another card is on this cell");
    }
    if (currentCell.getContents() instanceof List<?> listOfPawns
            && listOfPawns.getFirst() instanceof Pawn
            && currentCell.getPawnCount() < card.cost()) {
      throw new IllegalStateException("Cannot place a card - Not enough pawns on this cell");
    }
    if (currentCell.getContents() instanceof List<?> listOfPawns
            && listOfPawns.getFirst() instanceof Pawn
            && !(currentCell.getPawnColor().equals(currentPlayer.getColor()))) {
      throw new IllegalStateException("Cannot place a card - Pawns on this cell are not yours, "
              + "they are " + currentCell.getPawnColor() + "'s");
    }
    // adds the card to the chosen cell in the board, and removes all the pawns from the cell
    board[row][col].addCard(card, currentPlayer);
    board[row][col].removeAllPawns();
    // Distributes the card's influence
    distributeCardInfluence(card, row, col);
    // Sets current player's last turn as not passed & updates row score & discards card from hand
    // & ends player's turn
    if (currentPlayer == Player.RED) {
      redPassedLastTurn = false;
      redRowScores[row] += card.value();
      redHand.remove(card);
      currentPlayer = Player.BLUE;
      drawCard(); // Other player draws Card upon their turn starting
      nextTurnStarted(); // listens that blue's turn has started
    } else if (currentPlayer == Player.BLUE) {
      bluePassedLastTurn = false;
      blueRowScores[row] += card.value();
      blueHand.remove(card);
      currentPlayer = Player.RED;
      drawCard();
      nextTurnStarted(); // listens that red's turn has started
    }
  }

  // Distributes the card's influence the board appropriately
  private void distributeCardInfluence(Card card, int row, int col) {
    String[] influenceRows = card.influenceGrid();

    for (int influenceRow = 0; influenceRow < influenceRows.length; influenceRow++) {
      for (int influenceCol = 0; influenceCol < influenceRows[0].length(); influenceCol++) {
        String cellInfluence =
                influenceRows[influenceRow].substring(influenceCol, influenceCol + 1);

        // If the Card's influence grid's current cell represents influence, then attempt to
        // influence the matching cell on the game board
        if (cellInfluence.equals("I")) {
          try {
            Cell currentCell = board[row + influenceRow - 2][col + influenceCol - 2];
            // If current cell is empty, add a Pawn
            if (currentCell.getContents() == null) {
              currentCell.addPawn(currentPlayer);
              // If the current cell contains a Pawn, check who owns the Pawn(s)
            } else if (currentCell.getContents() instanceof List<?> maybeListOfPawns
                    && maybeListOfPawns.getFirst() instanceof Pawn) {
              List<Pawn> listOfPawns = (List<Pawn>) maybeListOfPawns;
              // If the current player doesn't own the Pawn(s), they take ownership of the Pawn(s)
              if (!(listOfPawns.getFirst().getColor().equals(currentPlayer.getColor()))) {
                int amountOfPawns = listOfPawns.size();
                currentCell.removeAllPawns();
                for (; amountOfPawns != 0; amountOfPawns--) {
                  currentCell.addPawn(currentPlayer);
                }
                // If the current player owns the Pawn(s) & there are less than 3 Pawns, add 1 Pawn
              } else if (listOfPawns.size() < 3) {
                currentCell.addPawn(currentPlayer);
              }
            }
          } catch (ArrayIndexOutOfBoundsException e) {
            // Skips giving this Cell influence, as this Cell does not exist
          }
        }
      }
    }
  }

  @Override
  public void passTurn() throws IllegalStateException {
    checkGameStarted();

    if (currentPlayer == Player.RED) {
      redPassedLastTurn = true;
    } else if (currentPlayer == Player.BLUE) {
      bluePassedLastTurn = true;
    }

    currentPlayer = (currentPlayer == Player.RED) ? Player.BLUE : Player.RED;
    drawCard(); // Other player draws Card upon their turn starting
    nextTurnStarted(); // listens that the next player's turn has started
  }

  @Override
  public void drawCard() throws IllegalStateException {
    checkGameStarted();

    List<Card> deck = (currentPlayer == Player.RED) ? redDeck : blueDeck;
    List<Card> hand = (currentPlayer == Player.RED) ? redHand : blueHand;

    if (!deck.isEmpty()) {
      hand.add(deck.getFirst());
      deck.remove(deck.getFirst());
    }
  }

  @Override
  public boolean isGameOver() {
    checkGameStarted();
    return redPassedLastTurn && bluePassedLastTurn;
  }

  // Checks if the game has started
  @Override
  public boolean isGameStarted() {
    return status == Status.STARTED;
  }

  @Override
  public List<Card> getRedDeck() throws IllegalStateException {
    checkGameStarted();
    return new ArrayList<>(redDeck);
  }

  @Override
  public List<Card> getBlueDeck() throws IllegalStateException {
    checkGameStarted();
    return new ArrayList<>(blueDeck);
  }

  @Override
  public List<Card> getRedHand() throws IllegalStateException {
    checkGameStarted();
    return new ArrayList<>(redHand);
  }

  @Override
  public List<Card> getBlueHand() throws IllegalStateException {
    checkGameStarted();
    return new ArrayList<>(blueHand);
  }

  // Make this return a deep copy at some point
  @Override
  public Cell[][] getBoard() throws IllegalStateException {
    checkGameStarted();
    return board;
  }

  // Make this return a deep copy at some point
  @Override
  public Cell getCell(int row, int col) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (row < 0 || col < 0 || row > board.length - 1 || col > board[0].length - 1) {
      throw new IllegalArgumentException("Cell is out of bounds");
    }

    return board[row][col];
  }

  @Override
  public int getBoardSize() throws IllegalStateException {
    checkGameStarted();
    return board.length * board[0].length;
  }

  @Override
  public Player getCurrentPlayer() throws IllegalStateException {
    checkGameStarted();
    return currentPlayer;
  }

  @Override
  public int[] getRedRowScores() throws IllegalStateException {
    checkGameStarted();
    return redRowScores;
  }

  @Override
  public int[] getBlueRowScores() throws IllegalStateException {
    checkGameStarted();
    return blueRowScores;
  }

  @Override
  public int[] getScore() throws IllegalStateException {
    checkGameStarted();
    return calculateScores();
  }

  // calculates the scores of the red & blue player; calculates by adding up valid row scores
  private int[] calculateScores() {
    int[] scores = new int[2];

    for (int row = 0; row < redRowScores.length; row++) {
      if (redRowScores[row] > blueRowScores[row]) {
        scores[0] += redRowScores[row];
      } else if (redRowScores[row] < blueRowScores[row]) {
        scores[1] += blueRowScores[row];
      }
    }

    return scores;
  }

  // Checks if game has started; If game hasn't started, throws IllegalStateException
  private void checkGameStarted() {
    if (!isGameStarted()) {
      throw new IllegalStateException("Game hasn't started yet");
    }
  }
}
