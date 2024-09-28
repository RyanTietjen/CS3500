package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;


/**
 * Represents a factory class for a game of Klondike Solitaire.
 * This implementation supports basic, limited, and whitehead klondike.
 */
public class KlondikeCreator {

  /**
   * Represents a certain type of Klondike Solitaire.
   */
  public enum GameType {
    BASIC, LIMITED, WHITEHEAD;
  }

  /**
   * Gets the appropriate subclass of klondike given user input,
   * with a default value of 2 for limited klondike.
   * @param game is the type of klondike.
   * @return an instance of (an appropriate subclass of) KlondikeModel.
   */
  public static KlondikeModel create(GameType game) {
    return getKlondikeGame(game, 2);
  }

  /**
   * Gets the appropriate subclass of klondike given user input.
   * @param game is the user's input.
   * @param num is the number of redraw attempts allowed (only applicable to limited klondike).
   * @return an instance of (an appropriate subclass of) KlondikeModel.
   */
  public static KlondikeModel create(String game, int num) {
    return getKlondikeGame(stringToGameType(game), num);
  }

  /**
   * Gets the appropriate subclass of klondike given user input.
   * @param game is the user's input.
   * @return an instance of (an appropriate subclass of) KlondikeModel.
   */
  public static KlondikeModel create(String game) {
    return getKlondikeGame(stringToGameType(game), 2);
  }

  /**
   * Gets the appropriate subclass of klondike given a GameType.
   * @param game is the type of game
   * @param num is the number of redraw attempts allowed (only applicable to limited klondike).
   * @return an instance of (an appropriate subclass of) KlondikeModel.
   */
  private static KlondikeGame getKlondikeGame(GameType game, int num) {
    switch (game) {
      case BASIC:
        return new BasicKlondike();
      case LIMITED:
        if (num <= 0) {
          throw new IllegalArgumentException("Cannot create game with negative redraw attempts");
        }
        return new LimitedDrawKlondike(num);
      case WHITEHEAD:
        return new WhiteheadKlondike();
      default:
        throw new IllegalArgumentException("GameType does not exist.");
    }
  }

  /**
   * Helper function that converts user input to correlated type of klondike.
   * @param game is the user input.
   * @return the GameType corresponding to the user input.
   */
  public static GameType stringToGameType(String game) {
    switch (game) {
      case "basic":
        return GameType.BASIC;
      case "limited":
        return GameType.LIMITED;
      case "whitehead":
        return GameType.WHITEHEAD;
      default:
        throw new IllegalArgumentException("GameType does not exist.");
    }
  }
}

