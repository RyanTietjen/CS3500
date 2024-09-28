package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.view.KlondikeTextualView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the WhiteheadKlondikeClass.
 */
public class WhiteheadTests {
  WhiteheadKlondike model;
  List<Card> deck;
  KlondikeTextualView textView;
  WhiteheadKlondike noDrawCards;
  List<Card> threeCardDeck;

  @Before
  public void init() {
    model = new WhiteheadKlondike();
    deck = model.getDeck();
    textView = new KlondikeTextualView(model);

    noDrawCards = new WhiteheadKlondike();
    threeCardDeck = new ArrayList<Card>();
    threeCardDeck.add(new BasicCard(Value.Ace, Suit.Spades));
    threeCardDeck.add(new BasicCard(Value.Two, Suit.Spades));
    threeCardDeck.add(new BasicCard(Value.Three, Suit.Spades));
  }

  @Test
  public void testAllCardsAreFaceUp() {
    this.init();
    model.startGame(deck, false, 7, 3);
    boolean allFaceUp = true;
    for (int pile = 0; pile < model.getNumPiles(); pile++) {
      for (int card = 0; card < model.getPileHeight(pile); card++) {
        if (!model.getCardAt(pile, card).isFaceUp()) {
          allFaceUp = false;
        }
      }
    }
    Assert.assertTrue("All cards should be face up", allFaceUp);
  }

  @Test
  public void testBuildsAreSameColor() {
    this.init();
    model.startGame(deck, false, 5, 3);
    for (int i = 0; i < 15; i++) {
      model.discardDraw();
    }
    Assert.assertThrows("Builds must be the same color",
        IllegalStateException.class, () -> model.moveDraw(1));
    model.movePile(0, 1, 4);
    Assert.assertEquals("Card should have been moved",
        model.getPileHeight(4), 6);
  }

  @Test
  public void testMovingMultipleCardsMustBeSameSuit() {
    this.init();
    model.startGame(deck, false, 5, 3);
    for (int i = 0; i < 15; i++) {
      model.discardDraw();
    }

    model.movePile(0, 1, 4);
    Assert.assertThrows("Builds must be same suit, not just same color",
        IllegalStateException.class, () -> model.movePile(4,2,0));
  }

  @Test
  public void testAnyCardCanBeMovedToEmptyPile() {
    this.init();
    model.startGame(deck, false, 5, 3);
    for (int i = 0; i < 15; i++) {
      model.discardDraw();
    }

    model.movePile(0, 1, 4);

    Assert.assertEquals("Test before mutation",
        model.getPileHeight(0), 0 );
    model.movePile(4,1,0);
    Assert.assertEquals("Test after mutation",
        model.getPileHeight(0), 1);
  }

  @Test
  public void testMovingMultipleCardsOfSameSuitInValidInstance() {
    this.init();
    Collections.reverse(deck);
    model.startGame(deck, false, 2, 100);
    model.movePile(1, 2, 0);
    Assert.assertEquals("Cards should have been moved",
        model.getPileHeight(0),3);
    model.movePile(0, 3, 1);
    Assert.assertEquals("Cards should have been moved",
        model.getPileHeight(1),3);
  }
}
