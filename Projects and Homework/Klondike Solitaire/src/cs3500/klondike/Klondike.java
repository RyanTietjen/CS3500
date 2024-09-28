package cs3500.klondike;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.KlondikeCreator;
import java.io.InputStreamReader;

/**
 * Represents the main "entry point" for games of Klondike Solitaire.
 * In this implementation, basic, limited, and whitehead solitaire are possible.
 */
public final class Klondike {

  /**
   * Takes inputs as command line arguments to initialize a game of Klondike Solitaire.
   * @param args are the arguments supplied by the user
   */
  public static void main(String[] args) {
    //cannot have no args
    if (args.length == 0) {
      throw new IllegalArgumentException("Cannot have no args");
    }

    //process arguments
    int numRedraw = processRedraw(args);
    int numPiles = processNumPiles(args);
    int numDraw = processNumDraw(args);

    //get game type
    KlondikeModel model = KlondikeCreator.create(args[0], numRedraw);

    //run game
    runGame(model, numPiles, numDraw);
  }

  /**
   * Process input for the numRedraw argument.
   * @param args are the arguments supplied by the user.
   * @return the user specified number of redraw attempts, 2 if unspecified.
   */
  private static int processRedraw(String[] args) {
    if (!args[0].equals("limited")) {
      return 2;
    }
    else if (args.length > 1) {
      return getNumFromInput(args[1], 2);
    }
    return 2;
  }


  /**
   * Process input for the numPiles argument.
   * @param args are the arguments supplied by the user.
   * @return the user specified number of redraw attempts, 7 if unspecified.
   */
  private static int processNumPiles(String[] args) {
    int neededIdx = getNeededIdx(args, 1);
    if (neededIdx < args.length) {
      return getNumFromInput(args[neededIdx], 7);
    }
    return 7;
  }

  /**
   * Process input for the numDraw argument.
   * @param args are the arguments supplied by the user.
   * @return the user specified number of redraw attempts, 3 if unspecified.
   */
  private static int processNumDraw(String[] args) {
    int neededIdx = getNeededIdx(args, 2);
    if (neededIdx < args.length) {
      return getNumFromInput(args[neededIdx], 3);
    }
    return 3;
  }

  /**
   * Determines the idx in args where the input will be.
   * @param args are the arguments supplied by the user.
   * @param originalIdx is the initial idx where the input is expected to be.
   * @return the original idx if the GameType isn't "limited," returns originalIdx + 1 otherwise.
   */
  private static int getNeededIdx(String[] args, int originalIdx) {
    int neededIdx = originalIdx;
    if (args[0].equals("limited")) {
      neededIdx = originalIdx + 1;
    }
    return neededIdx;
  }

  /**
   * Obtains a number given user input.
   * @param str is the user input.
   * @param defaultNum is a specified default value.
   * @return the number supplied, returns the defualt number if user input cannot be parsed.
   */
  private static int getNumFromInput(String str, int defaultNum) {
    try {
      return Integer.parseInt(str);
    }
    catch (Exception e) {
      return defaultNum;
    }
  }

  /**
   * Runs the specified game of klondike solitaire.
   * @param model is the model of the game.
   * @param numPiles is the number of piles.
   * @param numDraw is the number of draw cards.
   */
  private static void runGame(KlondikeModel model, int numPiles, int numDraw) {
    KlondikeController controller = new KlondikeTextualController(
        new InputStreamReader(System.in), System.out);
    try {
      controller.playGame(model, model.getDeck(), false, numPiles, numDraw);
    }
    catch (IllegalStateException e) {
      System.out.println("cannot run game");
    }
  }
}