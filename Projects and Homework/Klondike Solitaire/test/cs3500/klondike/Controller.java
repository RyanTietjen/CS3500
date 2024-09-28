package cs3500.klondike;

import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;

import java.io.InputStreamReader;

/**
 * Represents an interactive controller.
 */
public class Controller {

  /**
   * Represents an interactive controller for a game of klondike solitaire.
   */
  public static void main(String[] args) {
    BasicKlondike model = new BasicKlondike();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    KlondikeTextualController controller = new KlondikeTextualController(rd, ap);
    controller.playGame(model, model.getDeck(), false, 7, 3);
  }
}