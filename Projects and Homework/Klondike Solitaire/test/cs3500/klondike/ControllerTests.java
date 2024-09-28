package cs3500.klondike;

import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import cs3500.klondike.view.KlondikeTextualView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

/**
 * Tests the KlondikeTextualController class.
 */
public class ControllerTests {

  KlondikeTextualController controller;
  BasicKlondike model;
  Appendable appendable;

  @Before
  public void init() {
    model = new BasicKlondike();
    appendable = new StringBuilder();
  }

  @Test
  public void testControllerThrowsErrors() {
    this.init();
    Readable readable = new StringReader("q");
    Assert.assertThrows("readable cannot be null",
        IllegalArgumentException.class, () ->  new KlondikeTextualController(null, appendable));
    Assert.assertThrows("appendable cannot be null",
        IllegalArgumentException.class, () ->  new KlondikeTextualController(readable, null));
  }

  @Test
  public void testPlayGameThrowsErrors() {
    this.init();
    controller = new KlondikeTextualController(new StringReader(""), appendable);
    Assert.assertThrows("model cannot be null",
        IllegalArgumentException.class, () -> controller.playGame(
            null, model.getDeck(), false, 7,3 ));
    Assert.assertThrows("deck cannot be null",
        IllegalArgumentException.class, () -> controller.playGame(
            model, null, false, 7,3 ));
    Assert.assertThrows("cannot start an illegal game",
        IllegalStateException.class, () -> controller.playGame(
            model, model.getDeck(), false, 100,3 ));
    Assert.assertThrows("cannot play game with no input",
        IllegalStateException.class, () -> controller.playGame(
            model, model.getDeck(), false, 7,3 ));
  }

  @Test
  public void testValidInputContainsLetter() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp a 1 1 7 q"), appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue(appendable.toString().contains("Invalid number. "
        + "Please enter another number: "));
    Assert.assertTrue("move should have been made",
        model.getPileHeight(0) == 0);
  }

  @Test
  public void testTooLongInputHasNoEffectOnValidInput() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp 1 1 7 7 q"), appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertFalse("no request for further input",
        appendable.toString().contains("Invalid number. Please enter another number: "));
    Assert.assertTrue("There should be an invalid move prompt after the move has been made",
        appendable.toString().contains("Invalid move."));
    Assert.assertTrue("move should have been made",
        model.getPileHeight(0) == 0);
  }

  @Test
  public void testNegativeNumbersAreNotAccepted() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp -1 1 1 7 q"), appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("should request further input",
        appendable.toString().contains("Invalid number. Please enter another number: "));
    Assert.assertTrue("move should have been made",
        model.getPileHeight(0) == 0);
  }

  @Test
  public void testGarbageInputDoesNotAffectValidInput() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp this song is a cover"
        + " 1 of that famous 1 is she 7 UN Owen q"), appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("should request  further input",
        appendable.toString().contains("Invalid number. Please enter another number: "));
    Assert.assertTrue("move should have been made",
        model.getPileHeight(0) == 0);
  }


  @Test
  public void testMPPFunctionsCorrectly() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp 1 1 7 mpp 6 1 1 mpp 2 1 3 q"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);

    //tests that legal mpp moves are performed
    Assert.assertTrue("King should be able to be moved to empty pile",
        model.getCardAt(0, 0).equals(new BasicCard(Value.King, Suit.Clubs, true)));
    Assert.assertTrue("Ace of spades can be moved under a two of hearts",
        model.getCardAt(6, 7).equals(new BasicCard(Value.Ace, Suit.Spades, true)));

    //tests that an illegal move cannot be performed
    Assert.assertTrue("cannot move cards of same suit/disconnected numbers on top of each other",
        appendable.toString().contains("Invalid move. Play again."));
  }

  @Test
  public void testMPFFunctionsCorrectly() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpf 1 1\nmpf 3 2\nmpf 3 1\nq"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);

    //tests that legal mpf moves were performed
    Assert.assertEquals("two aces should be moved to foundation piles", model.getScore(), 2);

    //tests that illegal mpf moves are invalid
    Assert.assertTrue("cannot move 9 of spades to foundation pile containing only an ace of spades",
        appendable.toString().contains("Invalid move. Play again."));
  }

  @Test
  public void testMDFunctionsCorrectly() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("dd dd md 3 md 4 q"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7 ,3);
    Assert.assertTrue("cannot make invalid move",
        model.getPileHeight(2) == 3);
    Assert.assertTrue("should make valid move",
        model.getPileHeight(3) == 5);
  }

  @Test
  public void testMDFAndDDFunctionCorrectly() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("dd dd dd dd"
        + " dd dd dd dd dd dd mdf 1 dd mdf 2 q"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);

    Assert.assertTrue("cannot make invalid move",
        model.getCardAt(0) == null);
    Assert.assertTrue("should make valid move",
        model.getScore() == 1);
  }

  @Test
  public void testGameDoesNotEndWithInsufficientInput() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("dd dd"),
        appendable);
    Assert.assertThrows("input cannot be endless",
        IllegalStateException.class, () -> controller.playGame(model, model.getDeck(),
            false, 7, 3));
  }

  @Test
  public void testCaseInsensitiveQuitProducesCorrectOutput() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("Q"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("quit should produce specific message",
        appendable.toString().contains("State of game when quit:"));
  }

  @Test
  public void testGameEndsWhenWonWithValidButGarbageInput() {
    List<Card> deckForAnts = new ArrayList<Card>();
    deckForAnts.add(new BasicCard(Value.Ace, Suit.Spades));
    controller = new KlondikeTextualController(new StringReader(
        "mpf  dndsfbdslfhdshfds dkjsfnkjds mpf 0 0 mpf 1 1"), appendable);
    controller.playGame(model, deckForAnts, false, 1, 3);
    Assert.assertTrue("game won should produce a specific message",
        appendable.toString().contains("You win!"));
  }

  @Test
  public void testGameEndsWhenLostWithProperOutput() {
    List<Card> smallDeck = new ArrayList<Card>();
    smallDeck.add(new BasicCard(Value.Two, Suit.Spades));
    smallDeck.add(new BasicCard(Value.Ace, Suit.Spades));
    smallDeck.add(new BasicCard(Value.Three, Suit.Spades));

    controller = new KlondikeTextualController(new StringReader(""), appendable);
    controller.playGame(model, smallDeck, false, 2, 3);
    Assert.assertTrue("game lost should produce a specific message",
        appendable.toString().contains("Game over. Score: 0"));
  }

  @Test
  public void testQuitAfterOperationProvidedStillQuitsInSrcArg() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp q 1 7"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    KlondikeTextualView textualView = new KlondikeTextualView(model);
    Assert.assertTrue("quit should produce specific message",
        appendable.toString().contains("State of game when quit:"));
  }

  @Test
  public void testQuitAfterOperationProvidedStillQuitsInCardArg() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp 1 q 7"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    KlondikeTextualView textualView = new KlondikeTextualView(model);
    Assert.assertTrue("quit should produce specific message",
        appendable.toString().contains("State of game when quit:"));
  }

  @Test
  public void testQuitAfterOperationProvidedStillQuitsInDestArg() {
    this.init();
    controller = new KlondikeTextualController(new StringReader("mpp 1 1 q"),
        appendable);
    controller.playGame(model, model.getDeck(), false, 7, 3);
    Assert.assertTrue("quit should produce specific message",
        appendable.toString().contains("State of game when quit:"));
  }

  @Test
  public void testControllerWorksWithLimitedDrawKlondike() {
    this.init();
    KlondikeModel limitedDrawKlondike = new LimitedDrawKlondike(0);
    controller = new KlondikeTextualController(new StringReader("dd dd dd dd dd dd dd q"),
        appendable);
    controller.playGame(limitedDrawKlondike,
        limitedDrawKlondike.getDeck(), false, 9, 7);
    Assert.assertTrue("should be no draw cards left",
        appendable.toString().contains("Draw: \nFoundation:"));
  }

  @Test
  public void testControllerWorksWithWhiteheadKlondike() {
    this.init();
    WhiteheadKlondike whiteheadKlondike = new WhiteheadKlondike();
    controller = new KlondikeTextualController(new StringReader("mpp 2 2 1 mpp 1 3 2 q"),
        appendable);
    List<Card> tempDeck = whiteheadKlondike.getDeck();
    Collections.reverse(tempDeck);
    controller.playGame(whiteheadKlondike, tempDeck, false, 2, 3);
    Assert.assertEquals("Moves should have been made",
        whiteheadKlondike.getPileHeight(1), 3);
  }
}
