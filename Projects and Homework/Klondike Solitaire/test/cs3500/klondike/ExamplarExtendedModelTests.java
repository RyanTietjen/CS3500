package cs3500.klondike;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Represents some tests regarding limited and whitehead klondike.
 */
public class ExamplarExtendedModelTests {

  LimitedDrawKlondike limitedDrawKlondike;
  WhiteheadKlondike whiteheadKlondike;


  /**
   * Adds a given card to a deck from another deck.
   * @param temp is the original deck.
   * @param actualDeck is the deck the card should be added to.
   * @param card is the card.
   */
  public void addCard(List<Card> temp, List<Card> actualDeck, String card) {
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().contains(card)) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }


  @Before
  public void init() {
    limitedDrawKlondike = new LimitedDrawKlondike(0);
    whiteheadKlondike = new WhiteheadKlondike();
  }

  @Test
  public void limitedDrawTestDiscardDrawWithEmptyDrawPile() {
    this.init();
    limitedDrawKlondike.startGame(limitedDrawKlondike.getDeck(), false, 9, 7);
    for (int i = 0; i < 7; i++) {
      limitedDrawKlondike.discardDraw();
    }
    Assert.assertThrows("draw pile should be empty",
        IllegalStateException.class,  () -> limitedDrawKlondike.discardDraw());
  }

  @Test
  public void limitedDrawTestCardsShouldNotBeRemovedInValidCase() {
    this.init();
    limitedDrawKlondike = new LimitedDrawKlondike(2);
    limitedDrawKlondike.startGame(limitedDrawKlondike.getDeck(), false, 9, 7);
    for (int i = 0; i < 14; i++) {
      limitedDrawKlondike.discardDraw();
    }
    Assert.assertTrue("There should still be 7 draw cards available",
        limitedDrawKlondike.getNumDraw() == 7);
  }

  @Test
  public void whiteheadAnyCardValueCanBeMovedToEmptyCascadePile() {
    this.init();
    List<Card> deckForAnts = new ArrayList<>();
    List<Card> temp = new ArrayList<>();
    temp.addAll(whiteheadKlondike.getDeck());

    this.addCard(temp, deckForAnts, "A♡");
    this.addCard(temp, deckForAnts, "2♡");

    whiteheadKlondike.startGame(deckForAnts, false, 1, 3);
    whiteheadKlondike.moveToFoundation(0, 0);
    Assert.assertEquals("Ace should be moved out.",
        whiteheadKlondike.getPileHeight(0), 0);
    whiteheadKlondike.moveDraw(0);
    Assert.assertEquals("2 should be moved in",
        whiteheadKlondike.getPileHeight(0), 1);
  }

  @Test
  public void whiteheadTestSameSuitCardCanBeMovedToCascadePile() {
    this.init();
    List<Card> deckForAnts = new ArrayList<>();
    List<Card> temp = new ArrayList<>();
    temp.addAll(whiteheadKlondike.getDeck());

    this.addCard(temp, deckForAnts, "A♡");
    this.addCard(temp, deckForAnts, "A♢");
    this.addCard(temp, deckForAnts, "2♢");
    this.addCard(temp, deckForAnts, "2♡");


    whiteheadKlondike.startGame(deckForAnts, false, 2, 3);
    whiteheadKlondike.movePile(0, 1, 1);
    Assert.assertEquals("2 of diamonds should be on top of ace of hearts",
        whiteheadKlondike.getPileHeight(1), 3);
  }

  @Test
  public void whiteheadTestDifferentSuitCardCannotBeMovedToCascadePile() {
    this.init();
    List<Card> deckForAnts = new ArrayList<>();
    List<Card> temp = new ArrayList<>();
    temp.addAll(whiteheadKlondike.getDeck());

    this.addCard(temp, deckForAnts, "A♡");
    this.addCard(temp, deckForAnts, "A♠");
    this.addCard(temp, deckForAnts, "2♠");
    this.addCard(temp, deckForAnts, "2♡");

    whiteheadKlondike.startGame(deckForAnts, false, 2, 3);
    Assert.assertThrows("2 of diamonds cannot be on top of ace of hearts",
        IllegalStateException.class, () -> whiteheadKlondike.movePile(0, 1, 1));
  }

  @Test
  public void testMovingBuildsMustConsistOfSameSuitCardsNotJustSameColor() {
    this.init();
    List<Card> deckForAnts = new ArrayList<>();
    List<Card> temp = new ArrayList<>();
    temp.addAll(whiteheadKlondike.getDeck());

    this.addCard(temp, deckForAnts, "A♡");
    this.addCard(temp, deckForAnts, "A♢");
    this.addCard(temp, deckForAnts, "2♢");
    this.addCard(temp, deckForAnts, "3♡");
    this.addCard(temp, deckForAnts, "3♢");
    this.addCard(temp, deckForAnts, "2♡");


    whiteheadKlondike.startGame(deckForAnts, false, 2, 3);
    whiteheadKlondike.movePile(0, 1, 1);
    whiteheadKlondike.moveDraw(0);
    Assert.assertThrows("A build consisting of an ace of hearts and 2 of diamonds cannot be moved",
        IllegalStateException.class, () -> whiteheadKlondike.movePile(1, 2, 0));
  }
}
