package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.BasicCard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.view.KlondikeTextualView;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the private methods in BasicKlondike.
 */
public class PrivateTests {
  BasicKlondike model;
  List<Card> deck;
  KlondikeTextualView textView;

  @Before
  public void init() {
    model = new BasicKlondike();
    deck = model.getDeck();
    textView = new KlondikeTextualView(model);
  }

  @Test
  public void testCascadePileFormation() {
    this.init();
    model.startGame(deck, false, 7, 3);

    //Piles should be of correct heights
    Assert.assertEquals(model.getPileHeight(0), 1);
    Assert.assertEquals(model.getPileHeight(1), 2);
    Assert.assertEquals(model.getPileHeight(2), 3);
    Assert.assertEquals(model.getPileHeight(3), 4);
    Assert.assertEquals(model.getPileHeight(4), 5);
    Assert.assertEquals(model.getPileHeight(5), 6);
    Assert.assertEquals(model.getPileHeight(6), 7);

    Assert.assertTrue("top card of cascade pile should be visible",
            model.isCardVisible(0, 0));
    Assert.assertTrue("top card of cascade pile should be visible",
            model.isCardVisible(1, 1));
    Assert.assertTrue("top card of cascade pile should be visible",
            model.isCardVisible(6, 6));
    Assert.assertFalse("non-top card of cascade pile should not be visible",
            model.isCardVisible(1, 0));
  }

  @Test
  public void testFoundationPileFormation() {
    this.init();
    model.startGame(deck, false, 7, 3);

    Assert.assertEquals("should be 4 foundation piles in a standard deck",
            model.getNumFoundations(), 4);
  }

  @Test
  public void testVerifyDeck() {
    this.init();
    Assert.assertThrows("10 piles is too many piles for a standard deck",
            IllegalArgumentException.class, () -> model.startGame(deck, true,
                    10, 3));

    model.startGame(deck, true, 7, 3);

    //three complete sets of hearts and spades
    BasicKlondike heartsAndSpades = new BasicKlondike();
    List<Card> heartsAndSpadesDeck = new ArrayList<Card>();
    for (int i = 0; i < 3; i++) {
      heartsAndSpadesDeck.add(new BasicCard(Value.Ace, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Two, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Three, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Four, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Five, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Six, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Seven, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Eight, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Nine, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Ten, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Jack, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.Queen, Suit.Hearts));
      heartsAndSpadesDeck.add(new BasicCard(Value.King, Suit.Hearts));
    }
    for (int i = 0; i < 3; i++) {
      heartsAndSpadesDeck.add(new BasicCard(Value.Ace, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Two, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Three, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Four, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Five, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Six, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Seven, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Eight, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Nine, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Ten, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Jack, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.Queen, Suit.Spades));
      heartsAndSpadesDeck.add(new BasicCard(Value.King, Suit.Spades));
    }
    //should be valid
    heartsAndSpades.startGame(heartsAndSpadesDeck, true, 7, 3);

    //one set of each suit from Ace through Five
    BasicKlondike aceThroughFive = new BasicKlondike();
    List<Card> aceThroughFiveDeck = new ArrayList<Card>();
    aceThroughFiveDeck.add(new BasicCard(Value.Ace, Suit.Hearts));
    aceThroughFiveDeck.add(new BasicCard(Value.Two, Suit.Hearts));
    aceThroughFiveDeck.add(new BasicCard(Value.Three, Suit.Hearts));
    aceThroughFiveDeck.add(new BasicCard(Value.Four, Suit.Hearts));
    aceThroughFiveDeck.add(new BasicCard(Value.Five, Suit.Hearts));

    aceThroughFiveDeck.add(new BasicCard(Value.Ace, Suit.Spades));
    aceThroughFiveDeck.add(new BasicCard(Value.Two, Suit.Spades));
    aceThroughFiveDeck.add(new BasicCard(Value.Three, Suit.Spades));
    aceThroughFiveDeck.add(new BasicCard(Value.Four, Suit.Spades));
    aceThroughFiveDeck.add(new BasicCard(Value.Five, Suit.Spades));

    aceThroughFiveDeck.add(new BasicCard(Value.Ace, Suit.Clubs));
    aceThroughFiveDeck.add(new BasicCard(Value.Two, Suit.Clubs));
    aceThroughFiveDeck.add(new BasicCard(Value.Three, Suit.Clubs));
    aceThroughFiveDeck.add(new BasicCard(Value.Four, Suit.Clubs));
    aceThroughFiveDeck.add(new BasicCard(Value.Five, Suit.Clubs));

    aceThroughFiveDeck.add(new BasicCard(Value.Ace, Suit.Diamonds));
    aceThroughFiveDeck.add(new BasicCard(Value.Two, Suit.Diamonds));
    aceThroughFiveDeck.add(new BasicCard(Value.Three, Suit.Diamonds));
    aceThroughFiveDeck.add(new BasicCard(Value.Four, Suit.Diamonds));
    aceThroughFiveDeck.add(new BasicCard(Value.Five, Suit.Diamonds));
    //should be valid, no error
    aceThroughFive.startGame(aceThroughFiveDeck, true, 3, 3);


    //all thirteen clubs and only the Ace through Five of diamonds
    BasicKlondike invalidExample = new BasicKlondike();
    List<Card> invalidDeck = new ArrayList<Card>();

    invalidDeck.add(new BasicCard(Value.Ace, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Two, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Three, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Four, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Five, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Six, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Seven, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Eight, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Nine, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Ten, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Jack, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.Queen, Suit.Clubs));
    invalidDeck.add(new BasicCard(Value.King, Suit.Clubs));

    invalidDeck.add(new BasicCard(Value.Ace, Suit.Diamonds));
    invalidDeck.add(new BasicCard(Value.Two, Suit.Diamonds));
    invalidDeck.add(new BasicCard(Value.Three, Suit.Diamonds));
    invalidDeck.add(new BasicCard(Value.Four, Suit.Diamonds));
    invalidDeck.add(new BasicCard(Value.Five, Suit.Diamonds));

    Assert.assertThrows("invalid deck",
            IllegalArgumentException.class, () -> invalidExample.startGame(invalidDeck,
                    true, 7, 3));
  }

  @Test
  public void testFlipExposedCard() {
    this.init();
    model.startGame(deck, false, 7, 3);
    model.moveToFoundation(2,0);
    Assert.assertTrue("card should now be visible", model.isCardVisible(2,1));
    Assert.assertFalse("card should not be visible", model.isCardVisible(2,0));
  }

  @Test
  public void testCanBeAddedToCascadePile() {
    this.init();
    model.startGame(deck, false, 7, 3);
    model.discardDraw();
    model.discardDraw();
    model.moveDraw(3);
    Assert.assertThrows("cannot add this card to cascade pile",
            IllegalStateException.class, () -> model.moveDraw(3));
    Assert.assertThrows("cannot add this card to cascade pile",
            IllegalStateException.class, () -> model.movePile(0,1,2));
    Assert.assertTrue("card should be moved to cascade",
            model.getCardAt(3,4).equals(
            new BasicCard(Value.Five, Suit.Hearts)));
    for (int i = 0; i < 16; i++) {
      model.discardDraw();
    }
    model.moveDraw(4);
    model.movePile(1,1,4);
    Assert.assertTrue("card should be moved to cascade",
            model.getCardAt(4,6).equals(
                    new BasicCard(Value.Eight, Suit.Spades)));
  }

  @Test
  public void testCanBeAddedToFoundationPile() {
    this.init();
    model.startGame(deck, false, 7, 3);
    for (int i = 0; i < 11; i++) {
      model.discardDraw();
    }
    model.moveDrawToFoundation(0);
    Assert.assertTrue("card should be moved to foundation",
            model.getCardAt(0).equals(
                    new BasicCard(Value.Ace, Suit.Diamonds)));
    Assert.assertThrows("cannot add this card to foundation pile",
            IllegalStateException.class, () -> model.moveDrawToFoundation(1));
    Assert.assertThrows("cannot add this card to foundation pile",
            IllegalStateException.class, () -> model.moveToFoundation(2,0));
    model.moveToFoundation(0,1);
    Assert.assertTrue("card should be moved to foundation",
            model.getCardAt(1).equals(
                    new BasicCard(Value.Ace, Suit.Spades)));
  }
}
