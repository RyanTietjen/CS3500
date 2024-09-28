package cs3500.klondike.controller;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;

import java.util.List;
import java.util.Scanner;

/**
 * Represents the controller for a text-based game of Klondike Solitaire.
 */
public class KlondikeTextualController implements cs3500.klondike.controller.KlondikeController {

  private Appendable appendable;
  private Scanner sc;
  private KlondikeModel model;

  /**
   * Represents the controller for a text-based game of Klondike Solitaire.
   */
  public KlondikeTextualController(Readable r, Appendable a) {
    if (r == null || a == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null");
    }
    this.appendable = a;
    sc = new Scanner(r);
  }

  @Override
  public void playGame(KlondikeModel model, List<Card> deck, boolean shuffle, int numPiles,
      int numDraw) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null");
    }
    //run the game
    try {
      model.startGame(deck, shuffle, numPiles, numDraw);
    }
    catch (Exception e) {
      throw new IllegalStateException("Cannot start game");
    }
    this.model = model;
    KlondikeTextualView textView = new KlondikeTextualView(model, appendable);

    //play each round until user quits or game is over
    while (!model.isGameOver()) {
      //transmits game state
      this.renderBoard(textView);

      //transmits score
      this.writeMessage("Score: " + model.getScore());

      if (!sc.hasNext()) {
        throw new IllegalStateException("Readable cannot be endless");
      }

      if (this.processInput()) {
        this.writeMessage("Game quit!");
        this.writeMessage("State of game when quit:");
        break;
      }
    }

    //game is over
    this.renderBoard(textView);

    if (model.getScore() == deck.size()) {
      this.writeMessage("You win!");
    }
    String temp = "";
    if (model.isGameOver()) {
      temp += "Game over. ";
    }
    this.writeMessage(temp + "Score: " + model.getScore());

  }

  /**
   * Determines if the user is requesting to quit based on a given input.
   * @param input is user input.
   * @return true if the input equals q (case-insensitive), false otherwise.
   */
  private boolean inputContainsQuit(String input) {
    return input.equalsIgnoreCase("q");
  }

  /**
   * Process user input.
   * @return true if the user requests to quit, false otherwise.
   */
  private boolean processInput() {
    String input = sc.next();
    //check if user wants to quit
    if (this.inputContainsQuit(input)) {
      return true;
    }

    switch (input) {
      case "mpp":
        return this.handleMovePile();
      case "md":
        return this.handleMoveDraw();
      case "mpf":
        return this.handleMovePileFoundation();
      case "mdf":
        return this.handleMoveDrawFoundation();
      case "dd":
        this.handleDiscardDraw();
        break;
      default:
        this.invalidMoveCase("operation unspecified.");
    }
    return false;
  }

  /**
   * Appends the "invalid move" message.
   * @param reason is the reason why a move is invalid.
   */
  private void invalidMoveCase(String reason) {
    this.writeMessage("Invalid move. Play again. " + reason);
  }

  /**
   * Appends any message.
   * @param str is the message.
   */
  private void writeMessage(String str) {
    try {
      this.appendable.append(str).append(System.lineSeparator());
    } catch (Exception e) {
      throw new IllegalStateException("Cannot append");
    }
  }

  /**
   * Renders the board in its current state.
   * @param textView is the view that should display it
   */
  private void renderBoard(KlondikeTextualView textView) {
    try {
      textView.render();
      this.writeMessage("");
    } catch (Exception e) {
      throw new IllegalStateException("Could not render board");
    }
  }

  /**
   * Forces user to input values until it receives a non-negative integer.
   * @param input is the user input.
   * @return -1 if the user requests to quit, otherwise the value eventually entered by the user.
   */
  private int forceNonNegInt(String input) {
    int num = -1;
    boolean invalidPile = false;
    if (this.inputContainsQuit(input)) {
      return -1;
    }
    if (!this.isANumber(input)) {
      invalidPile = true;
    }
    else {
      num = Integer.parseInt(input);
    }
    if (invalidPile || num < 0) {
      this.writeMessage("Invalid number. Please enter another number: ");
      String str = sc.next();
      return this.forceNonNegInt(str);
    }
    return num;
  }

  /**
   * Determines if a string is composed of only numbers.
   * @param str is the string
   * @return true if a string can be parsed to an integer, false otherwise.
   */
  private boolean isANumber(String str) {
    try {
      int temp = Integer.parseInt(str);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Handles the case where the user provides "mpp".
   * @return true if the user wants to quit, false otherwise.
   */
  private boolean handleMovePile() {
    int srcCascadePile = this.forceNonNegInt(sc.next()) - 1;
    if (srcCascadePile == -2) {
      return true;
    }
    int card = this.forceNonNegInt(sc.next());
    if (card == -1) {
      return true;
    }
    int destCascadePile = this.forceNonNegInt(sc.next()) - 1;
    if (destCascadePile == -2) {
      return true;
    }
    try {
      model.movePile(srcCascadePile, card, destCascadePile);
    } catch (Exception e) {
      this.invalidMoveCase("Invalid to move source pile to destination pile.");
    }
    return false;
  }

  /**
   * Handles the case where the user provides "md".
   * @return true if the user wants to quit, false otherwise.
   */
  private boolean handleMoveDraw() {
    int destCascadePile = this.forceNonNegInt(sc.next()) - 1;
    if (destCascadePile == -2) {
      return true;
    }
    try {
      model.moveDraw(destCascadePile);
    } catch (Exception e) {
      this.invalidMoveCase("Invalid to move draw card to cascade pile.");
    }
    return false;
  }

  /**
   * Handles the case where the user provides "mpf".
   * @return true if the user wants to quit, false otherwise.
   */
  private boolean handleMovePileFoundation() {
    int srcCascadePile = this.forceNonNegInt(sc.next()) - 1;
    if (srcCascadePile == -2) {
      return true;
    }
    int foundationPile = this.forceNonNegInt(sc.next()) - 1;
    if (foundationPile == -2) {
      return true;
    }
    try {
      model.moveToFoundation(srcCascadePile, foundationPile);
    } catch (Exception e) {
      this.invalidMoveCase("Cannot move cascade pile card to foundation.");
    }
    return false;
  }

  /**
   * Handles the case where the user provides "mdf".
   * @return true if the user wants to quit, false otherwise.
   */
  private boolean handleMoveDrawFoundation() {
    int foundationPile = this.forceNonNegInt(sc.next()) - 1;
    if (foundationPile == -2) {
      return true;
    }
    try {
      model.moveDrawToFoundation(foundationPile);
    } catch (Exception e) {
      this.invalidMoveCase("Cannot move draw card to foundation.");
    }
    return false;
  }

  /**
   * Handles the case where the user provides "dd".
   * @return true if the user wants to quit, false otherwise.
   */
  private void handleDiscardDraw() {
    try {
      model.discardDraw();
    } catch (Exception e) {
      this.invalidMoveCase("Cannot discard draw.");
    }
  }
}
