package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.Card;

import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs a game of Klondike Solitaire with the following restrictions:
 * -All the cards in the cascade piles are dealt face-up.
 * -Instead of alternating colors, builds must be single-colored – red cards on red cards,
 * black cards on black cards.
 * -When moving multiple cards from one cascade pile to another,
 * all the moved cards must be all the same suit, not merely a valid build.
 * -When moving a card into an empty cascade pile, it can be any card value, not just a king.
 * (When moving multiple cards into a cascade pile, it must be a single-suit run,
 * as above, but it can start from any value.).
 */
public class WhiteheadKlondike extends KlondikeGame {

  /**
   * Constructor for a game of whitehead Klondike Solitaire.
   */
  public WhiteheadKlondike() {
    super();
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    int topCascadeIdx = super.cascadePiles.get(srcPile).size() - numCards;
    Card topCard = super.getCardAt(srcPile, topCascadeIdx);

    if (this.allCardsCanBeMoved(numCards, srcPile) &&
        this.canBeAddedToCascadePile(topCard, destPile)) {
      super.moveAllCardsToPile(srcPile, numCards, destPile);
    }
    else {
      throw new IllegalStateException("move not permissible");
    }
  }

  /**
   * Generates a standard deck of cards with all cards being face up.
   * @return the deck of cards.
   */
  protected List<Card> generateStandardDeck() {
    List<Card> temp = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        temp.add(new BasicCard(value, suit, true));
      }
    }
    return temp;
  }

  /**
   * Determines if the card can be added to a given cascade pile.
   * A card can be added if it is the same color and one less in value than the top card on
   * the destination cascade pile.
   * If there is no card in the destination cascade pile, it can be added.
   * @param card        is the card
   * @param cascadePile is the 0-based index of the pile
   * @return
   */
  protected boolean canBeAddedToCascadePile(Card card, int cascadePile) {
    int topCascadeIdx = super.cascadePiles.get(cascadePile).size() - 1;
    //if pile is empty, any card can be added
    if (super.cascadePiles.get(cascadePile).isEmpty()) {
      return true;
    }
    //if card is same color and one value higher than cascade, can be added
    else {
      return card.isSameColor(super.getCardAt(cascadePile, topCascadeIdx)) &&
          card.isValueOneLessThanOther(super.getCardAt(cascadePile, topCascadeIdx));
    }
  }

  /**
   * Determines if a specified number of cards in a cascade pile are all the same suit.
   * @param cards is the number of cards.
   * @param cascadePile is the index of the cascade pile.
   * @return true if all cards can be moved, false otherwise.
   */
  protected boolean allCardsCanBeMoved(int cards, int cascadePile) {
    int topCascadeIdx = this.cascadePiles.get(cascadePile).size() - 1;
    List<Card> temp = new ArrayList<Card>();
    for (int i = 0; i < cards; i++) {
      temp.add(super.cascadePiles.get(cascadePile).get(topCascadeIdx - i));
    }
    for (int i = 0; i < temp.size() - 1; i++) {
      if (!temp.get(i).isSameSuit(temp.get(i + 1))) {
        return false;
      }
    }
    return true;
  }
}
