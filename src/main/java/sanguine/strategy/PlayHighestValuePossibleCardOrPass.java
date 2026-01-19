package sanguine.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Card;
import sanguine.model.SanguineModel;
import sanguine.model.enums.Player;

/**
 * This class represents a Strategy for the game of sanguine.Sanguine.
 *
 * <p>In this strategy, the player attempts to place the card with the highest value in their hand.
 * The card will be attempted to be played in the first possible cell.
 * If no moves are possible, the player passes their turn.
 */
public class PlayHighestValuePossibleCardOrPass implements SanguineStrategy {

  @Override
  public void playTurn(SanguineModel model) {
    tryAllPlays(model);
  }

  // Attempts plays until a valid one is made; the first card in hand is attempted first
  private void tryAllPlays(SanguineModel model) {
    Player player = model.getCurrentPlayer();
    List<Card> hand = new ArrayList<>(
            (player == Player.RED) ? model.getRedHand() : model.getBlueHand());
    sortHandByValue(hand);

    boolean played = false;
    // Each Card
    for (Card card : hand) {
      // Each Row
      for (int row = 0; row < model.getBoard().length && !played; row++) {
        // Each Column
        for (int col = 0; col < model.getBoard()[0].length && !played; col++) {
          // Keeping try to make a successful play
          try {
            model.playCard(card, row, col);
            played = true;
          } catch (IllegalStateException e) {
            // If a play is invalid, try the next play
          }
        }
      }
      // If a play is made, stop checking for valid plays (as one has been made)
      if (played) {
        break;
      }
    }
    // If no play was made during this player's entire turn, skip their turn
    if (!played) {
      model.passTurn();
    }
  }

  // Sorts hand by Value in Descending order
  private void sortHandByValue(List<Card> hand) {
    for (int currentCardIndex = 0; currentCardIndex < hand.size(); currentCardIndex++) {
      int biggestCardIndex = currentCardIndex;
      for (int comparingCardIndex = currentCardIndex; comparingCardIndex < hand.size();
           comparingCardIndex++) {
        if (hand.get(biggestCardIndex).value() < hand.get(comparingCardIndex).value()) {
          biggestCardIndex = comparingCardIndex;
        }
      }

      Card currentCard = hand.get(currentCardIndex);
      Card biggestCard = hand.get(biggestCardIndex);
      hand.set(currentCardIndex, biggestCard);
      hand.set(biggestCardIndex, currentCard);
    }
  }
}
