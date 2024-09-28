package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;

import java.util.HashMap;
import java.util.Map;

/**
 *  Runs a game of Klondike Solitaire with the following restrictions:
 *  This version of Solitaire allows a limited number of redraw attempts.
 *  In essence, a card can only be discarded a certain number of times
 *  before it is permanently discarded.
 */
public class LimitedDrawKlondike extends KlondikeGame {
  private int numTimesRedrawAllowed;
  private Map<Card, Integer> redrawMap;

  /**
   * Constructor for a game of limited draw Klondike Solitaire.
   * @param numTimesRedrawAllowed is the number of allowed redraws.
   */
  public LimitedDrawKlondike(int numTimesRedrawAllowed) {
    super();
    if (numTimesRedrawAllowed < 0) {
      throw new IllegalArgumentException("Cannot have negative redraw attempts");
    }
    this.numTimesRedrawAllowed = numTimesRedrawAllowed;
    this.initRedrawMap();
  }

  @Override
  public void discardDraw() {
    super.errorIfGameHasNotStarted();
    if (super.drawCards.isEmpty()) {
      throw new IllegalStateException("No draw cards remaining");
    }
    Card drawCard = super.getDrawCards().get(0);
    //remove card if the maximum number of discards has been used.
    if (redrawMap.get(drawCard) == 0) {
      super.drawCards.remove(0);
    }
    else {
      //otherwise, decrease the number of discards remaining, and discard it
      redrawMap.replace(drawCard, redrawMap.get(drawCard) - 1);
      super.drawCards.add(super.drawCards.remove(0));
    }
  }

  /**
   * Assigns a value to each card in the deck.
   * This value represents how many times the card can be redrawn if it's
   * in the draw pile.
   */
  private void initRedrawMap() {
    redrawMap = new HashMap<>();
    for (Card card : super.getDeck()) {
      redrawMap.put(card, numTimesRedrawAllowed);
    }
  }
}
