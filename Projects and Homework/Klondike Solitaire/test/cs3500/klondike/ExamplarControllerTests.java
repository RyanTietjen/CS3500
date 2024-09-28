package cs3500.klondike;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.view.KlondikeTextualView;
import cs3500.klondike.view.TextualView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests the KlondikeTextualController class.
 */
public class ExamplarControllerTests {

  BasicKlondike model;
  Appendable appendable;
  Readable readable;
  KlondikeController controller;
  List<Card> actualDeck;
  List<Card> temp;


  @Before
  public void init() {
    model = new BasicKlondike();
    appendable = new StringBuilder();
    actualDeck = new ArrayList<Card>();
    temp = new ArrayList<Card>(model.getDeck());
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
   * Adds the two of clubs to a deck.
   * @param temp is the deck to be taken from.
   * @param actualDeck is the deck the card is added to.
   */
  public void addTwoClubs(List<Card> temp, List<Card> actualDeck) {
    for (int i = 0; i < temp.size(); i++) {
      if (!temp.get(i).toString().contains("2♣")) {
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
  public void testGameWithoutEndingOrQuitting() {
    this.init();
    controller = new KlondikeTextualController(
            new StringReader("dd"),
            System.out);
    Assert.assertThrows("exception thrown when game runs infinitely",
            IllegalStateException.class, () -> controller.playGame(model,
                    model.getDeck(), false, 7, 3));

  }

  @Test
  public void testInputParametersWithoutOperation() {
    this.init();
    readable = new StringReader("1 1\nq");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    this.addRandomAce(temp, actualDeck);
    controller.playGame(model, actualDeck, false, 1, 3);
    Assert.assertTrue("move without operation is invalid",
            appendable.toString().contains("Invalid move"));
  }

  @Test
  public void testMDNoCardInDrawPile() {
    this.init();
    readable = new StringReader("md 1\nq");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    this.addRandomAce(temp, actualDeck);
    controller.playGame(model, actualDeck, false, 1, 3);
    Assert.assertTrue("cannot move a card from the draw pile if it's empty",
            appendable.toString().contains("Invalid move"));
  }

  @Test
  public void testMPPInvalidMove() {
    this.init();
    readable = new StringReader("mpp 1 1 2\nq");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("cannot move card from srcPile to destPile",
            appendable.toString().contains("Invalid move"));
  }

  @Test
  public void testWordAsParamWithMD() {
    this.init();
    readable = new StringReader("md guts\nq");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    this.addRandomAce(temp, actualDeck);
    this.addTwoHearts(temp, actualDeck);
    controller.playGame(model, actualDeck, false, 1, 3);
    //I would prefer to use an assertTrue/.contains here to check for a certain
    //output message, but the instructions don't specify what the message
    //should be when invalid parameters are passed
    Assert.assertTrue("Parameters for operations should not be words",
            appendable.toString().contains("Draw: A"));
  }

  @Test
  public void testTextViewAfterQuittingImmediately() {
    this.init();
    readable = new StringReader("q");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    TextualView textualView = new KlondikeTextualView(model);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("",
            appendable.toString().contains("Game quit!\n" +
                    "State of game when quit:\n" +
                    textualView.toString()
                    + "\nScore: 0"));
  }

  @Test
  public void testTextViewAfterWinning() {
    this.init();
    readable = new StringReader("mpf 1 1");
    controller = new KlondikeTextualController(
            readable,
            appendable);
    TextualView textualView = new KlondikeTextualView(model);
    this.addRandomAce(temp, actualDeck);
    controller.playGame(model, actualDeck, false, 1, 3);
    Assert.assertTrue("",
            appendable.toString().contains(textualView.toString() + "\nYou win!"));
  }
}
