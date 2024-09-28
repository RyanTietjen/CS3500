package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents some tests for a game of klondike solitaire.
 */
public class ExamplarModelTests {
  BasicKlondike standardGame;
  List<Card> temp;
  List<Card> actualDeck;

  @Before
  public void init() {
    standardGame = new BasicKlondike();
    temp = standardGame.getDeck();
    actualDeck = new ArrayList<Card>();
  }

  /**
   * Adds a random ace to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addRandomAce(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().contains("A")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds a random eight to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addRandomEight(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().contains("8")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds a random king to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addRandomKing(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().contains("K")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds a random non-king to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addRandomNonKing(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("K")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the two of hearts to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addTwoHearts(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("2♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the ace of hearts to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addAceHearts(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("A♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the three of hearts to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addThreeHearts(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("3♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the three of diamonds to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addThreeDiamonds(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("3♢")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the four of hearts to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addFourHearts(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("4♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }

  /**
   * Adds the five of hearts to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addFiveHearts(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("5♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
  }


  @Test
  public void moveAceToEmptyFoundationPile() {
    this.init();
    standardGame.startGame(temp, false, 7, 1);
    if (standardGame.getCardAt(0,0).toString().contains("A")) {
      standardGame.moveToFoundation(0,0);
    }
    Assert.assertEquals("score should be 1 after moving ace to foundation",
            standardGame.getScore(), 1);
  }


  @Test
  public void moveNewAceToOccupiedFoundation() {
    this.init();
    this.addRandomAce(temp, actualDeck);
    actualDeck.addAll(temp);
    standardGame = new BasicKlondike();
    standardGame.startGame(actualDeck, false, 7, 1);
    standardGame.moveToFoundation(0, 0);
    Assert.assertThrows("foundation pile is occupied",
            IllegalStateException.class, () -> standardGame.moveToFoundation(1,
                    0));
  }

  @Test
  public void moveKingToEmptyFoundation() {
    this.init();

    this.addRandomAce(temp, actualDeck);
    this.addRandomEight(temp, actualDeck);
    this.addRandomKing(temp, actualDeck);

    actualDeck.addAll(temp);
    standardGame = new BasicKlondike();
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.moveToFoundation(0, 0);
    standardGame.movePile(1,1,0);
    Assert.assertEquals("King should be in 0th foundation pile",
            standardGame.getPileHeight(0), 1);
  }

  @Test
  public void moveNonKingToEmptyFoundation() {
    this.init();
    this.addRandomAce(temp, actualDeck);
    this.addRandomEight(temp, actualDeck);
    this.addRandomNonKing(temp, actualDeck);

    actualDeck.addAll(temp);
    standardGame = new BasicKlondike();
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.moveToFoundation(0, 0);
    Assert.assertThrows("you cannot move a non-king to a new pile",
            IllegalStateException.class, () -> standardGame.movePile(1,
                    1,0));
  }

  @Test
  public void stackingDifferentSuitButNotDifferentColor() {
    this.init();

    this.addTwoHearts(temp, actualDeck);
    this.addRandomEight(temp, actualDeck);
    this.addThreeDiamonds(temp, actualDeck);

    actualDeck.addAll(temp);
    standardGame.startGame(actualDeck, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> standardGame.movePile(0,
            1,1));
  }

  @Test
  public void stackingSameSuit() {
    this.init();
    this.addTwoHearts(temp, actualDeck);
    this.addRandomEight(temp, actualDeck);
    this.addThreeDiamonds(temp, actualDeck);
    actualDeck.addAll(temp);
    standardGame.startGame(actualDeck, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> standardGame.movePile(0,
            1,1));
  }

  @Test
  public void isNextCardVisibleWhenMovingPiles() {
    //bad test, not changing because it's in the original submission
    this.init();
    //add two of hearts
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("3♣")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add random unimportant card
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().toLowerCase().contains("8")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add three of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("2♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    actualDeck.addAll(temp);
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.movePile(1, 1,0);
    Assert.assertTrue("When all other cards have been moved off a cascade pile, the " +
            "card underneath should become visible",standardGame.isCardVisible(1, 0));
  }

  @Test
  public void moveAceFromDrawPileIntoFoundation() {
    //bad test, not changing because it's in the original submission
    this.init();
    //add two of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("3♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add four of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().toLowerCase().contains("4♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add three of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("2♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add ace of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("A♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.moveDrawToFoundation(0);
    Assert.assertEquals("foundation pile should have height 1, so 1 score",
            standardGame.getScore(), 1);
  }

  @Test
  public void moveNonAceFromDrawPileIntoFoundationPile() {
    this.init();
    this.addThreeHearts(temp, actualDeck);
    this.addFourHearts(temp, actualDeck);
    this.addTwoHearts(temp, actualDeck);
    this.addFiveHearts(temp, actualDeck);
    this.addAceHearts(temp, actualDeck);
    standardGame.startGame(actualDeck, false, 2, 1);
    Assert.assertThrows("You cannot move a non-ace to an empty pile",
            IllegalStateException.class, () -> standardGame.moveDrawToFoundation(0));
  }

  @Test
  public void moveDrawPileToFoundationAfterCyclingThrewDrawPile() {
    this.init();
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("3♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add four of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().toLowerCase().contains("4♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add three of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("2♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add "random" card
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("5♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add ace of hearts to make sure no errors are thrown
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("A♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.discardDraw();
    standardGame.moveDrawToFoundation(0);
    Assert.assertEquals("foundation pile should have height 1, so 1 score",
            standardGame.getScore(), 1);

  }

  @Test
  public void moveCardFromDrawPileToCascadePile() {
    standardGame = new BasicKlondike();
    List<Card> temp = standardGame.getDeck();
    List<Card> actualDeck = new ArrayList<Card>();
    //add two of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("4♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    //add four of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().toLowerCase().contains("5♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    //add three of diamonds
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("6♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    //add "random" card
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("3♠")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    actualDeck.addAll(temp);
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.moveDraw(0);
    Assert.assertEquals("foundation pile should have height 1, so 1 score",
            standardGame.getPileHeight(0), 2);
  }

  @Test
  public void moveAceIntoNonFirstFoundationPile() {
    standardGame = new BasicKlondike();
    List<Card> temp = standardGame.getDeck();
    standardGame.startGame(temp, false, 7, 1);
    if (standardGame.getCardAt(0,0).toString().contains("A")) {
      standardGame.moveToFoundation(0,1);
    }
    Assert.assertEquals("foundation pile should have height 1, so 1 score",
            standardGame.getScore(), 1);
  }

  @Test
  public void foundationPileSameSuitUnstackableNumber() {
    standardGame = new BasicKlondike();
    List<Card> temp = standardGame.getDeck();
    List<Card> actualDeck = new ArrayList<Card>();
    //add two of hearts
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("A♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add random unimportant card
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().toLowerCase().contains("8")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }

    //add three of hearts
    for (int i = 0; i < temp.size(); i++) {
      if (temp.get(i).toString().equals("4♡")) {
        actualDeck.add(temp.remove(i));
        break;
      }
    }
    actualDeck.addAll(temp);
    standardGame.startGame(actualDeck, false, 2, 1);
    standardGame.moveToFoundation(0, 0);
    Assert.assertThrows("should not be able to move card of same suit and incorrect " +
                    "number onto foundation pile",
            IllegalStateException.class, () -> standardGame.moveToFoundation(1,
                    0));
  }

  @Test
  public void directMutationOfDrawCards() {
    standardGame = new BasicKlondike();
    List<Card> temp = standardGame.getDeck();
    standardGame.startGame(temp, false, 2, 1);
    Assert.assertThrows("you should not be able to directly remove draw cards",
            UnsupportedOperationException.class, () -> standardGame.getDrawCards().remove(0));
  }
}
