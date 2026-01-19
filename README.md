# Sanguine Code Introduction
The purpose of my code is to design an MVC (model, view Controller)
style program for the game of sanguine.

sanguine is a variation of a card game called Queen's Blood, which is
played as a two-player card game.

This code also includes a Player Interface, which has the purpose of creating a
simple AI.

This allows for games to be Human vs. Human, Human vs. AI, and AI vs. AI.

# Usability
This code is only usable when given valid decks for sanguine.
Any invalid deck will not be able to compile through this code, and will therefore crash.

A valid deck is compromised of atleast one card in this format:

name cost value

XXXXX

XXXXX

XXCXX

XXXXX

XXXXX

The name could be anything, but only one word;
the cost and value have to be a whole number; the next 5 lines are the 5
rows of the influence grid, which could contain a X, I, or C. X represents
no influence, I represents influence, and C represents where the card
is placed. C will only ever be placed in the 3rd row, as the 3rd character.

# How To Use
To start uisng this code you need to use the SanguineModel's (the model)
startGame() method. this method takes in a whole number representing the rows
of the board, another whole number representing the columns of the board, another whole number
representing the size of the hand of cards of each player, a boolean representing
if the decks will be shuffled or not, and two files, which are
the files containing the decks for player 1 and player 2.
Starting to use the code would look something like this:

SanguineModel model = new BasicSanguine();

model.startGame(3, 5, 5, true, redDeckFile, blueDeckFile);

Possible Moves After starting:

- model.playCard(card, 0, 1);
    - which plays a card from the active player's hand onto the board
- model.passTurn();
    - which skips the current player's turn

The game ends when both players pass their turn (therefore when they both use model.passTurn())

It is also key to keep in mind the Red Player always starts, and the
Blue Player always plays second

# Purpose of Component - Model
The purpose of my Model is to build the functionality of the game, as well
as provide observers to make the life of the other components easier.

Due to this the model is split into two interfaces: SanguineModel and
ReadOnlySanguineModel. SanguineModel is responsible for the functions of the game.
ReadOnlySanguineModel is responsible for all the observer methods.

The model is comprised by the BasicSanguine model, and helper classes:
Card, Cell, and BasicDeckReader.

Card, Cell, and BasicDeckReader each have their own Interfaces, which determine their usage:
SanguineCell, SanguineCard, DeckReaderInterface.

The BasicSanguine is responsible for the majority of building the game;
The Cell class is responsible for the logic for the cells within the board;
The Card class is responsible for the logic of the playing Cards;
The BasicDeckReader is responsible for converting a deck file into a
then to a List of Cards, which is usable within our code.

There are Enums for Pawns, Players, and Status.
Pawns can be either Red or Blue. Players can be either Red or Blue as well.
Status can be either Started or Not_Started.

# Changes to the Model for Part 2
For the most part, my model has remained the same since part 1.
The only major change was ensuring cards were also drawn at the start
of a player's turn. Previously, cards were drawn only after a player placed
a card or passed their turn.

# Changes to the Model for Part 3
Implemented the ModelListener interface, which is responsible for notifying any interested
class when the current Player's turn has ended (and therefore when the next Player's turn 
has begun).
- The controller implements this interface

Added a addTurnListener() method in the SanguineModel Interface. 
This method takes in a TurnListener.
- Method Signature: public void addTurnListener(TurnListener turnListener);
- The controller can subscribe itself to the model using this method

Added a helper method in BasicSanguine: private void nextTurnStarted();.
The purpose of this helper method is to call the newly added listener any time a Player 
plays a card or passes a turn (therefore whenever the current player's turn ends,
and the next player's turn starts).

# Purpose of Component - View
I have created two separate views: One is a very barebones textual view,
the other is a much more fleshed out GUI.

# Textual View
In order to use see the textual view, you need to create
a view object from the SanguineTextualView Interface and
BasicSanguineTextualView class, and call view.toString()

The board is represented with Cards, Pawns, and Empty Cells.
* A Card is represented by the color initial of the Player who owns it (For example: R and B).
* A Pawns are represented by a number between 1-3, depending on how many Pawns are in that Cell,
    * however, the Player who owns the Pawn is not represented in this toString().
* Empty Cell are represented by an underscore '_'.

On the left and right side of the board, there are numbers representing
* the score for each row. The Scores on the left are the Row Score's for the Red Player,
* while the Scores on the right are the Row Score's for the Blue Player.

# GUI View - Additions in Part 2 & Changes to Main Method in part 3
Currently in my code, in order see the GUI, you must run it in a main method (or .jar file).

Necessary Run Confiqurations for running main method:
* args[0] number of rows
* args[1] number of columns
* args[2] Red Player's deck file
* args[3] Blue Player's deck file
* args[4] Red Player (Human or bot Strategy)
* args[5] Blue Player (Human or bot Strategy)

In order for arguments [4] & [5] to be valid, they must be one of: 
* "strategy1" 
  * represents a Bot using the PlayFirstPossibleCardOrPass Strategy
* "strategy2"
  * represents a Bot using the PlayHighestValuePossibleCardOrPass Strategy
* "human"
  * represents a Human Player, who will select moves from the GUI manually

The view is comprised by two frames, one for Player 1 (Red), and one for Player 2 (Blue).
The frame for player red shows red's hand of cards at the bottom, while directly above it is the
game's board, to the left of the game's board is Red's row scores, and to the right is Blue's
row scores. Blue's frame follows the same exact format, but at the bottom, instead of red's hand
of cards, it is Blue's hand of cards.

Whenever the current player clicks on a Cell within the board, it will be highlighted.
Similarly, Whenever the current player clicks on a card with their hand of cards,
it will be highlighted. If the current player presses on the same card or cell again,
that card or cell will be unselected. Likewise, if the current player presses on another
card or cell, the previously selected card or cell will be unselected.

If after the current player selected a card and cell, they are able to press "c" on their keyboard,
to confirm their move. A player is also able to click "p" in order to pass their turn.

If the non-current player clicks on a Card or cell, nothing will be highlighted.

# GUI View - Changes in Part 3
Added a game over screen.

When the game is over, the new game over screen is overlayed on top of the hands section (panel)
of the frame. This applies to both the red and blue view.
- States the game is over
- Shows which player has won
- If a player won: Shows score of winning player 
- If a game ends in a tie: Shows the score of both players

Created a new panel class ErrorMessagePanel, which is responsible for adding 
an error message Screen.

Whenever a player attempts to confirm an invalid move, this screen is intended to show up.
It states that the player's move is invalid, and requests them to try again.

# Purpose of Component - Controller
Currently, this code does not contain a fully functioning controller. However, it has a basic
stub controller, which allows the game to run, just not fully correctly.

This stub allows for the playing of the game, however, the game will never officially
end, and if a player plays an invalid move, the model will throw an exception and not play the move.
While this exception is not caught and handled yet, it doesn't crash the game, it just doesn't
prompt the player correctly on what to do next. To note, the invalid move is not played, as the
exception is thrown so no mutation happens.

A completed controller will soon be implemented, solving all these issues.

# Real Controller - Additions in part 3
In this portion, a real controller was added, and replaced the old stub controller.

Due to this being a multiplayer game, with 2 players, and 2 frames, I decided to implement
2 different controllers.
- A controller for the Red Player
- A controller for the Blue Player

These controllers are both their own individual class, which extends an Abstract controller class.
- The red controller is the only controller that overrides any methods from the abstract class, as
  it overrides the playGame() method, in order to be able to call startGame() from the model.
- Since startGame() should only be called once per game, the blue controller does not override
  startGame, and simply uses the abstract version.
- The constructor for the abstract class is protected, so the only way to run it is through
  either the blue or red player controller
- The abstract class implements its view interface, the feature listeners interface for the GUI
  view, and finally the new turn listener to listen for when the model switches player turns. 
  It also subscribes itself to both listeners using view.addListener and model.addTurnListener.

If an invalid move of any kind is attempted by a player, the move will not count, and the player
will be shown an invalid move screen. When given that screen, the player will know they attempted
an invalid move, and must press ok to continue playing.

When the game is over (both player's pass their turn in consecutive turns), the player will be
shown a game over screen. This screen will be placed over each player's hand, and will state
that the game is over, the winner (or tie), and the winner's score.
- The both players will also be unable to click on any card or cell, or perform any moves

# Player/AI Interface & Strategies
Another addition to part 2 was a more flesh out Player/AI Interface, and the Strategies it follows.

The Player/AI Interface & its implementation can be found in the controller package. The
Strategy interface and its implementations can be found in its own package (strategy); this is due
to strategies not falling within any component of MVC.

This Player interface uses a given strategy to determine how this Player will play.
This Player can either be an AI playing for red, or an AI playing for blue.

There are 2 strategies currently supported for these AI:
* Play the first card possible in the first cell possible (pass if no possible valid move)
    * This AI is not very smart, it just attempts to play a card as quickly as possible
* Play the first card with the highest value in the first cell possible.
    * This ensures the AI implementing this strategy will focus on having the highest
      possible row scores going from top to bottom, given their options.

To Reiterate what I previously mentioned, these AI are able to play either against a human,
or against another AI (which may implement the same or a different strategy).

# Jar File
In order for a valid call to the Jar file, all 6 needed arguments are required

Number of rows, Number of columns, Red Player Deck, Blue Player Deck, name of strategy,
name of strategy

the 3 possible names of strategies are strategy1, strategy2, and human. If human is selected
that means that a player will be responsible for the moves of the given player.

Example:
java -jar SanguineJava.jar 5 7 docs/35CardDeck1 docs/35CardDeck1 strategy2 human

This will start a game where the board has 5 rows and 7 columns; RedPlayer and BluePlayer will use the deck named "35CardDeck1";
RedPlayer will be a AI using strategy 2, while BluePlayer will be a human who controls their own moves.
