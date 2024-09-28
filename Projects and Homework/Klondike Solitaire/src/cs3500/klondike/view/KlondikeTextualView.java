package cs3500.klondike.view;

import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * A simple text-based rendering of Klondike Solitaire.
 */
public class KlondikeTextualView implements TextualView {
  private final KlondikeModel model;
  private final Appendable appendable;

  public KlondikeTextualView(KlondikeModel model) {
    this.appendable = new PrintStream(System.out);
    this.model = model;
  }

  public KlondikeTextualView(KlondikeModel model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    appendable.append(this.toString());
  }

  /**
   * Represents the game in its current state.
   * @return a string representing the game.
   */
  public String toString() {
    String str = "";

    //draw piles
    str += "Draw: ";
    List<Card> temp = model.getDrawCards();
    for (int i = 0; i < model.getNumDraw(); i++) {
      if (i == model.getNumDraw() || i == temp.size()) {
        break;
      }
      str += temp.get(i);
      if (i + 1 != model.getNumDraw()) {
        str += ", ";
      }
    }
    str += "\n";

    //foundation piles
    str += "Foundation: ";
    for (int i = 0; i < model.getNumFoundations(); i++) {
      if (model.getCardAt(i) == null) {
        str += "<none>";
      }
      else {
        str += model.getCardAt(i);
      }
      if (i + 1 != model.getNumFoundations()) {
        str += ", ";
      }
    }
    str += "\n";

    //cascade piles
    //if no cards left in game (i.e. game won)
    if (model.getNumRows() == 0) {
      for (int i = 0; i < model.getNumPiles(); i++) {
        str += "  X";
      }
    }
    else {
      for (int i = 0; i < model.getNumRows(); i++) {
        for (int j = 0; j < model.getNumPiles(); j++) {
          try {
            if (j != model.getNumPiles()) {
              if (!model.getCardAt(j, i).isSameValue(new BasicCard(Value.Ten, Suit.Spades))) {
                str += " ";
              }
            }
            if (model.getCardAt(j, i).isFaceUp()) {
              str += model.getCardAt(j, i).toString();
            }
          } catch (Exception ignored) {
            if (i == 0 && model.getPileHeight(j) == 0) {
              str += "  X";
            } else if (model.getPileHeight(j) > i) {
              str += "  ?";
            } else {
              str += "   ";
            }
          }
        }
        str += "\n";
      }
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }
}
