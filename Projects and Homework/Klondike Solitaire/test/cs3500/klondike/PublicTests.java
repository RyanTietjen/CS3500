package cs3500.klondike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.view.KlondikeTextualView;

/**
 * Tests the public methods in BasicKlondike.
 */
public class PublicTests {
  BasicKlondike model;
  List<Card> deck;
  KlondikeTextualView textView;
  BasicKlondike noDrawCards;
  List<Card> threeCardDeck;

  @Before
  public void init() {
    model = new BasicKlondike();
    deck = model.getDeck();
    textView = new KlondikeTextualView(model);

    noDrawCards = new BasicKlondike();
    threeCardDeck = new ArrayList<Card>();
    threeCardDeck.add(new BasicCard(Value.Ace, Suit.Spades));
    threeCardDeck.add(new BasicCard(Value.Two, Suit.Spades));
    threeCardDeck.add(new BasicCard(Value.Three, Suit.Spades));
  }

  @Test
  public void testStartGame() {
    this.init();
    //card verification is tested in PrivateTests
    List<Card> nullDeck = new ArrayList<Card>();
    for (int i = 0; i < 52; i++) {
      nullDeck.add(null);
    }
    Assert.assertThrows("cannot have zero/negative numPiles",
            IllegalArgumentException.class, () -> model.startGame(deck,
                    false, 0, 3));
    Assert.assertThrows("cannot have zero/negative numDraw",
            IllegalArgumentException.class, () -> model.startGame(deck,
                    false, 7, 0));
    Assert.assertThrows("cannot have null deck",
            IllegalArgumentException.class, () -> model.startGame(null,
                    false, 7, 0));
    Assert.assertThrows("cannot have deck with null cards",
            IllegalArgumentException.class, () -> model.startGame(nullDeck,
                    false, 7, 0));
    Assert.assertThrows("too many cascade piles",
            IllegalArgumentException.class, () -> model.startGame(deck,
                    false, 10, 0));

    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("cannot start game twice",
            IllegalStateException.class, () -> model.startGame(deck,
                    false, 7, 3));
    Assert.assertEquals("should have 7 numPiles", model.getNumPiles(), 7);
    Assert.assertEquals("should have 3 numDraw", model.getNumDraw(), 3);
    Assert.assertEquals("should have 7 rows", model.getNumRows(), 7);
    Assert.assertEquals("should have 4 foundation piles",
            model.getNumFoundations(), 4);
  }

  @Test
  public void testMovePile() {
    this.init();
    //disallowed moves are tested in PrivateTests
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.movePile(0, 1, 1));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("first pile is invalid",
            IllegalArgumentException.class, () -> model.movePile(-1,
                    1, 1));
    Assert.assertThrows("dest pile is invalid",
            IllegalArgumentException.class, () -> model.movePile(1,
                    1, -1));
    Assert.assertThrows("num of cards is invalid",
            IllegalArgumentException.class, () -> model.movePile(1,
                    0, 2));
    Assert.assertThrows("src cannot equal dest pile",
            IllegalArgumentException.class, () -> model.movePile(1,
                    1, 1));
  }

  @Test
  public void testMoveDraw() {
    this.init();
    //disallowed moves are tested in PrivateTests
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.moveDraw(0));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("dest pile is invalid",
            IllegalArgumentException.class, () -> model.moveDraw(-1));
    noDrawCards.startGame(threeCardDeck, false, 2, 3);
    Assert.assertThrows("draw pile empty",
            IllegalStateException.class, () -> model.moveDraw(0));
  }

  @Test
  public void testMoveToFoundation() {
    this.init();
    //disallowed moves are tested in PrivateTests
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.moveToFoundation(0, 1));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("first pile is invalid",
            IllegalArgumentException.class, () -> model.moveToFoundation(-1,
                    1));
    Assert.assertThrows("dest pile is invalid",
            IllegalArgumentException.class, () -> model.moveToFoundation(1, -1));
  }

  @Test
  public void testMoveDrawToFoundation() {
    this.init();
    //disallowed moves are tested in PrivateTests
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.moveDrawToFoundation(0));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("dest pile is invalid",
            IllegalArgumentException.class, () -> model.moveDrawToFoundation(-1));
    noDrawCards.startGame(threeCardDeck, false, 2, 3);
    Assert.assertThrows("draw pile empty",
            IllegalStateException.class, () -> model.moveDrawToFoundation(0));
  }

  @Test
  public void testDiscardDraw() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.discardDraw());
    model.startGame(deck, false, 7, 3);
    Assert.assertTrue("test before mutation",
            model.getDrawCards().get(0).equals(new BasicCard(Value.Three, Suit.Hearts, true)));
    model.discardDraw();
    Assert.assertTrue("test after mutation",
            model.getDrawCards().get(0).equals(new BasicCard(Value.Four, Suit.Hearts, true)));
    Assert.assertThrows("draw pile is empty",
            IllegalStateException.class, () -> noDrawCards.discardDraw());
  }

  @Test
  public void testGetNumRows() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getNumRows());
    model.startGame(deck, false, 7, 3);
    Assert.assertEquals("standard game should start with 7 rows",
            model.getNumRows(), 7);
    for (int i = 0; i < 6; i++) {
      model.discardDraw();
    }
    model.moveDraw(4);
    model.movePile(1, 1, 4);
    for (int i = 0; i < 10; i++) {
      model.discardDraw();
    }
    model.moveDraw(4);
    Assert.assertEquals("standard game should now have 8 rows",
            model.getNumRows(), 8);
  }

  @Test
  public void testGetNumPiles() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getNumPiles());
    model.startGame(deck, false, 7, 3);
    Assert.assertEquals("standard game should always have 7 rows",
            model.getNumPiles(), 7);
  }

  @Test
  public void testGetNumDraw() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getNumPiles());
    model.startGame(deck, false, 7, 3);
    Assert.assertEquals("standard game should always start with 3 cards in draw pile",
            model.getNumDraw(), 3);
    noDrawCards.startGame(threeCardDeck, false, 2, 2);
    Assert.assertEquals("game with no draw cards should have 0 visible draw cards",
            noDrawCards.getNumDraw(), 0);
  }

  @Test
  public void testIsGameOver() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.isGameOver());
    noDrawCards.startGame(threeCardDeck, false, 2, 3);
    Assert.assertFalse("can move ace to foundation pile", noDrawCards.isGameOver());
    noDrawCards.moveToFoundation(0, 0);
    Assert.assertTrue("no more legal move to be made", noDrawCards.isGameOver());

    model.startGame(deck, false, 7, 3);
    model.moveToFoundation(0, 0);
    model.moveToFoundation(2, 1);
    Assert.assertFalse("can move a draw card", model.isGameOver());

    BasicKlondike gameForAnts = new BasicKlondike();
    List<Card> deckForAnts = new ArrayList<Card>();
    deckForAnts.add(new BasicCard(Value.Ace, Suit.Spades));
    gameForAnts.startGame(deckForAnts, true, 1, 1);
    Assert.assertFalse("ace can be moved to foundation pile", gameForAnts.isGameOver());
    gameForAnts.moveToFoundation(0, 0);
    Assert.assertTrue("game is over", gameForAnts.isGameOver());
  }

  @Test
  public void testGetScore() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getScore());
    model.startGame(deck, false, 6, 3);
    Assert.assertEquals("score should be 0 at start of game",
            model.getScore(), 0);
    model.moveToFoundation(0, 0);
    Assert.assertEquals("score should be 1 after moving ace",
            model.getScore(), 1);
    for (int i = 0; i < 5; i++) {
      model.discardDraw();
    }
    for (int i = 0; i < 5; i++) {
      model.moveDrawToFoundation(1);
    }
    Assert.assertEquals("score should be 6 with 6 cards in the foundation piles",
            model.getScore(), 6);
  }

  @Test
  public void testGetPileHeight() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getPileHeight(0));
    model.startGame(deck, false, 7, 3);

    Assert.assertThrows("cannot be a pile at index -1",
            IllegalArgumentException.class, () -> model.getPileHeight(-1));
    Assert.assertThrows("cannot be a pile at index 7",
            IllegalArgumentException.class, () -> model.getPileHeight(7));

    //Piles should be of correct heights
    Assert.assertEquals(model.getPileHeight(0), 1);
    Assert.assertEquals(model.getPileHeight(1), 2);
    Assert.assertEquals(model.getPileHeight(2), 3);
    Assert.assertEquals(model.getPileHeight(3), 4);
    Assert.assertEquals(model.getPileHeight(4), 5);
    Assert.assertEquals(model.getPileHeight(5), 6);
    Assert.assertEquals(model.getPileHeight(6), 7);

  }

  @Test
  public void testIsCardVisible() {
    //making proper cards visible is tested in PrivateTests
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.isCardVisible(0, 0));
    model.startGame(deck, false, 6, 3);
    Assert.assertThrows("pileNum cannot be negative",
            IllegalArgumentException.class, () -> model.isCardVisible(-1, 0));
    Assert.assertThrows("card num cannot be negative",
            IllegalArgumentException.class, () -> model.isCardVisible(0, -1));
    Assert.assertThrows("cannot get card at idx greater than num of piles",
            IllegalArgumentException.class, () -> model.isCardVisible(7, 0));
    Assert.assertThrows("cannot get card at idx greater than number " +
                    "of cards in a given piles",
            IllegalArgumentException.class, () -> model.isCardVisible(0, 1));


    Assert.assertTrue("top card of cascade pile should be visible",
            model.isCardVisible(0, 0));
    Assert.assertTrue("top card of cascade pile should be visible",
            model.isCardVisible(1, 1));
    Assert.assertFalse("non-top card of cascade pile should not be visible",
            model.isCardVisible(1, 0));
  }

  @Test
  public void testGetCardAtCascade() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getCardAt(0, 0));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("pileNum cannot be negative",
            IllegalArgumentException.class, () -> model.getCardAt(-1, 0));
    Assert.assertThrows("card num cannot be negative",
            IllegalArgumentException.class, () -> model.getCardAt(0, -1));
    Assert.assertThrows("cannot get card at idx greater than num of piles",
            IllegalArgumentException.class, () -> model.getCardAt(7, 0));
    Assert.assertThrows("cannot get card at idx greater than number " +
                    "of cards in a given piles",
            IllegalArgumentException.class, () -> model.getCardAt(0, 1));
    Assert.assertThrows("cannot get invisible card",
            IllegalArgumentException.class, () -> model.getCardAt(1, 0));
    Assert.assertTrue("card should be able to be obtained",
            model.getCardAt(0, 0).equals(new BasicCard(Value.Ace, Suit.Spades, true)));
  }

  @Test
  public void testGetCardAtFoundation() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getCardAt(0, 0));
    model.startGame(deck, false, 7, 3);
    Assert.assertThrows("foundationPile cannot be negative",
            IllegalArgumentException.class, () -> model.getCardAt(-1));
    Assert.assertThrows("foundationPile cannot exceed number of foundation piles",
            IllegalArgumentException.class, () -> model.getCardAt(4));
    Assert.assertEquals("empty foundation pile should return null",
            model.getCardAt(0), null);
    model.moveToFoundation(0, 0);
    Assert.assertTrue("foundation pile should an ace",
            model.getCardAt(0).equals(new BasicCard(Value.Ace, Suit.Spades, true)));
  }

  @Test
  public void testGetDrawCards() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getDrawCards());
    model.startGame(deck, false, 7, 3);
    List<Card> temp = model.getDrawCards();
    Assert.assertTrue("first card should be a 3 of hearts",
            temp.get(0).equals(new BasicCard(Value.Three, Suit.Hearts, true)));
    Assert.assertTrue("second card should be a 4 of hearts",
            temp.get(1).equals(new BasicCard(Value.Four, Suit.Hearts, true)));
    Assert.assertTrue("third card should be a 5 of hearts",
            temp.get(2).equals(new BasicCard(Value.Five, Suit.Hearts, true)));
    for (int i = 0; i < 115; i++) {
      model.discardDraw();
    }

    temp = model.getDrawCards();
    Assert.assertTrue("first card should be a 9 of diamonds",
            temp.get(0).equals(new BasicCard(Value.Nine, Suit.Diamonds, true)));
    Assert.assertTrue("second card should be a 10 of diamonds",
            temp.get(1).equals(new BasicCard(Value.Ten, Suit.Diamonds, true)));
    Assert.assertTrue("third card should be a jack of diamonds",
            temp.get(2).equals(new BasicCard(Value.Jack, Suit.Diamonds, true)));
  }

  @Test
  public void testGetNumFoundations() {
    this.init();
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getNumFoundations());
    model.startGame(deck, false, 7, 3);
    Assert.assertEquals("standard game should have 4 foundations",
            model.getNumFoundations(), 4);

    noDrawCards.startGame(threeCardDeck, false, 2, 3);
    Assert.assertEquals("standard game should have 1 foundation",
            noDrawCards.getNumFoundations(), 1);
  }

  @Test
  public void testTextModelView() {
    this.init();
    model.startGame(deck, false, 7, 3);
    Assert.assertEquals("text view should be accurate", textView.toString(), "Draw: 3♡, 4♡, 5♡\n" +
            "Foundation: <none>, <none>, <none>, <none>\n" +
            " A♠  ?  ?  ?  ?  ?  ?\n" +
            "    8♠  ?  ?  ?  ?  ?\n" +
            "       A♣  ?  ?  ?  ?\n" +
            "          6♣  ?  ?  ?\n" +
            "            10♣  ?  ?\n" +
            "                K♣  ?\n" +
            "                   2♡");
    model.moveToFoundation(0, 0);
    Assert.assertEquals("should be an X when cascade pile is empty",
            textView.toString(), "Draw: 3♡, 4♡, 5♡\n" +
                    "Foundation: A♠, <none>, <none>, <none>\n" +
                    "  X  ?  ?  ?  ?  ?  ?\n" +
                    "    8♠  ?  ?  ?  ?  ?\n" +
                    "       A♣  ?  ?  ?  ?\n" +
                    "          6♣  ?  ?  ?\n" +
                    "            10♣  ?  ?\n" +
                    "                K♣  ?\n" +
                    "                   2♡");
    noDrawCards.startGame(threeCardDeck, false, 1, 2);
    noDrawCards.moveToFoundation(0, 0);
    noDrawCards.moveDrawToFoundation(0);
    noDrawCards.moveDrawToFoundation(0);
    KlondikeTextualView temp = new KlondikeTextualView(noDrawCards);
    Assert.assertEquals("completed game should be displayed properly",
            temp.toString(), "Draw: \n" +
                    "Foundation: 3♠\n" +
                    "  X");
  }
}
