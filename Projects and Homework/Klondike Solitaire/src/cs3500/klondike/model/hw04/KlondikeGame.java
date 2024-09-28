package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.BasicCard;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw02.Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a general game of Klondike Solitaire with "default" rules/restrictions as described:
 * -Cascade and Foundation Piles are 0-indexed.
 * -Only the top cards in the cascade piles are dealt face-up, the rest are dealt face down.
 * -For a card to be placed in a foundation pile, the first card in an empty pile must be an Ace,
 * while subsequent cards in the same pile must be of the same suit and one value higher than the
 * top card.
 * -The face-up cards should form builds with consecutive, descending values and alternating colors.
 * -When moving multiple cards from one cascade pile to another, all cards must form a valid build.
 * -When moving a card into an empty cascade pile, it must be a king.
 * -There is an infinite number of redraw attempts.
 */
public abstract class KlondikeGame implements cs3500.klondike.model.hw02.KlondikeModel {

  private List<Card> deck;
  private int numDraw;
  private List<List<Card>> foundationPiles;
  protected List<Card> drawCards;
  protected List<List<Card>> cascadePiles;
  private boolean gameStarted;

  protected KlondikeGame() {
    this.deck = this.generateStandardDeck();
  }

  public List<Card> getDeck() {
    return this.deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
      throws IllegalArgumentException {
    this.validateStartGameArgs(deck, numPiles, numDraw);
    if (this.gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    this.gameStarted = true;
    this.numDraw = numDraw;
    if (shuffle) {
      Collections.shuffle(deck);
    }

    //count the aces
    this.foundationPiles = new ArrayList<>();
    for (Card card : deck) {
      if (card.isSameValue(new BasicCard(Value.Ace, Suit.Hearts))) {
        this.foundationPiles.add(new ArrayList<Card>());
      }
    }
    this.verifyDeck(deck);

    //Separate list that can be modified
    List<Card> modList = new ArrayList<Card>(deck);

    //form cascade and draw piles
    this.cascadePiles = this.formCascadePiles(modList, numPiles);
    this.drawCards = new ArrayList<Card>();
    for (Card c : modList) {
      c.flipUp();
    }
    this.drawCards.addAll(modList);
  }


  @Override
  public void movePile(int srcPile, int numCards, int destPile)
      throws IllegalStateException {
    //check if conditions are met
    this.errorIfGameHasNotStarted();
    this.validateSrcAndDestPile(srcPile,destPile);
    this.validateNumCards(numCards, srcPile);

    Card srcCard = this.cascadePiles.get(srcPile).
        get(this.cascadePiles.get(srcPile).size() - numCards);
    if (this.canBeAddedToCascadePile(srcCard, destPile)) {
      this.moveAllCardsToPile(srcPile, numCards, destPile);
    }
    else {
      throw new IllegalStateException("Illegal move");
    }
    this.flipExposedCard();
  }

  @Override
  public void moveDraw(int destPile) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    this.validateCascadePile(destPile);
    this.validateDrawPile();

    Card srcCard = this.drawCards.get(0);
    if (this.canBeAddedToCascadePile(srcCard, destPile)) {
      this.cascadePiles.get(destPile).add(this.drawCards.remove(0));
    } else {
      throw new IllegalStateException("move not permissible");
    }
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile)
      throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    this.validateCascadePile(srcPile);
    this.validateFoundationPile(foundationPile);
    this.validatePileIsNotEmpty(srcPile);

    Card srcCard = this.cascadePiles.get(srcPile).get(this.cascadePiles.get(srcPile).size() - 1);
    if (this.canBeAddedToFoundationPile(srcCard, foundationPile)) {
      this.foundationPiles.get(foundationPile).
          add(this.cascadePiles.get(srcPile).
              remove(this.cascadePiles.get(srcPile).size() - 1));
    } else {
      throw new IllegalStateException("move not permissible");
    }
    this.flipExposedCard();
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    this.validateFoundationPile(foundationPile);
    this.validateDrawPile();

    Card drawCard = this.drawCards.get(0);
    if (this.canBeAddedToFoundationPile(drawCard, foundationPile)) {
      this.foundationPiles.get(foundationPile).add(this.drawCards.remove(0));
    } else {
      throw new IllegalStateException("move not permissible");
    }
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    this.validateDrawPile();
    this.drawCards.add(this.drawCards.remove(0));
  }

  @Override
  public int getNumRows() {
    this.errorIfGameHasNotStarted();
    int num = 0;
    for (int i = 0; i < this.getNumPiles(); i++) {
      if (num < this.cascadePiles.get(i).size()) {
        num = this.cascadePiles.get(i).size();
      }
    }
    return num;
  }

  @Override
  public int getNumPiles() {
    this.errorIfGameHasNotStarted();
    return this.cascadePiles.size();
  }

  @Override
  public int getNumDraw() {
    this.errorIfGameHasNotStarted();
    return Math.min(this.numDraw, this.drawCards.size());
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    //game won
    if (this.getScore() == this.deck.size()) {
      return true;
    }

    //it is possible for a move to be made
    return (!(this.anyCardCanBeAddedToAnyCascadePile() ||
        this.anyCardCanBeAddedToAnyFoundationPile() ||
        this.anyDrawCardCanBeAddedToAnyCascadePile() ||
        this.anyDrawCardCanBeAddedToAnyFoundationPile()));
  }

  @Override
  public int getScore() throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    int num = 0;
    for (List<Card> l : this.foundationPiles) {
      num += l.size();
    }
    return num;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    if (pileNum < 0 || pileNum >= this.getNumPiles()) {
      throw new IllegalArgumentException("pileNum cannot be < 0 or " +
          "greater than the number of piles");
    }
    return this.cascadePiles.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    if (pileNum < 0 || pileNum >= this.getNumPiles()) {
      throw new IllegalArgumentException("pile number out of bounds");
    }
    if (card < 0 || card >= this.cascadePiles.get(pileNum).size()) {
      throw new IllegalArgumentException("card number out of bounds");
    }
    return this.cascadePiles.get(pileNum).get(card).isFaceUp();
  }

  @Override
  public Card getCardAt(int pileNum, int card) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    if (pileNum < 0 ||
        card < 0 ||
        pileNum >= this.getNumPiles() ||
        card >= this.getNumRows()) {
      throw new IllegalArgumentException("arguments out of bounds");
    }
    if (!this.isCardVisible(pileNum, card)) {
      throw new IllegalArgumentException("cannot get an invisible card");
    }
    return this.cascadePiles.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int foundationPile) throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    if (foundationPile < 0 || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("argument out of bounds");
    }

    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      return null;
    }
    if (!this.foundationPiles.get(foundationPile).
        get(this.foundationPiles.get(foundationPile).size() - 1).isFaceUp()) {
      throw new IllegalArgumentException("cannot get an invisible card");
    }
    return this.foundationPiles.get(foundationPile).
        get(this.foundationPiles.get(foundationPile).size() - 1);
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    List<Card> temp = new ArrayList<Card>();
    for (int i = 0; i < this.getNumDraw(); i++) {
      temp.add(drawCards.get(i));
    }
    return temp;
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    this.errorIfGameHasNotStarted();
    return this.foundationPiles.size();
  }

  /**
   * Checks if the game has started, throws an error if it has not.
   */
  protected void errorIfGameHasNotStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }
  }

  /**
   * Puts the cards into the cascade piles.
   *
   * @param deck is the deck supplied
   * @return an arraylist of arraylists representing the cascade piles
   */
  protected List<List<Card>> formCascadePiles(List<Card> deck, int numPiles) {
    List<List<Card>> temp = new ArrayList<>();
    //add appropriate number of arraylists
    for (int i = 0; i < numPiles; i++) {
      temp.add(new ArrayList<Card>());
    }
    //add cards to the cascade piles
    for (int i = 0; i < numPiles; i++) {
      for (int j = i; j < numPiles; j++) {
        if (deck.isEmpty()) {
          throw new IllegalArgumentException("Number of cascade piles too large");
        }
        temp.get(j).add(deck.remove(0));
        if (i == j) {
          temp.get(j).get(i).flipUp();
        }
      }
    }
    return temp;
  }

  /**
   * Verifies that the deck is valid, throws an exception if it isn't.
   */
  private void verifyDeck(List<Card> deck) {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("invalid deck");
    }
    //make copy of the deck that can be freely modified
    List<Card> actualDeck = new ArrayList<Card>(deck);

    //adds number of piles corresponding to the number of foundation piles
    List<List<Card>> piles = new ArrayList<>();
    for (int i = 0; i < this.getNumFoundations(); i++) {
      piles.add(new ArrayList<Card>());
    }

    //add the aces to the piles
    int counter = 0;
    for (int i = 0; i < actualDeck.size(); i++) {
      if (actualDeck.get(i).isSameValue(new BasicCard(Value.Ace, Suit.Hearts))) {
        piles.get(counter).add(actualDeck.remove(i));
        counter++;
        i--;
      }
    }

    //add the rest of the cards
    for (List<Card> p : piles) {
      for (int i = 0; i < actualDeck.size(); i++) {
        if (p.get(p.size() - 1).isValueOneLessThanOther(actualDeck.get(i)) &&
            p.get(0).isSameSuit(actualDeck.get(i))) {
          p.add(actualDeck.remove(i));
          i = -1;
        }
      }
    }

    //check if the deck is invalid
    int listSize = piles.get(0).size();
    boolean allSameSize = true;
    for (List<Card> pile : piles) {
      if (pile.size() != listSize) {
        allSameSize = false;
        break;
      }
    }
    if (!actualDeck.isEmpty() || !allSameSize) {
      throw new IllegalArgumentException("Deck is invalid");
    }
  }

  /**
   * Flips up card in the cascade piles that should be visible.
   */
  private void flipExposedCard() {
    for (int i = 0; i < this.getNumPiles(); i++) {
      if (!this.cascadePiles.get(i).isEmpty()) {
        this.forceGetCardAt(i, this.cascadePiles.get(i).size() - 1).flipUp();
      }
    }
  }

  /**
   * Returns card at specified pile regardless of whether it is visible.
   *
   * @param pileNum is the 0-indexed pile
   * @param card    is the number card in the pile
   * @return card at given index
   */
  private Card forceGetCardAt(int pileNum, int card) {
    this.errorIfGameHasNotStarted();
    if (pileNum < 0 ||
        card < 0 ||
        pileNum >= this.getNumPiles() ||
        card >= this.getNumRows()) {
      throw new IllegalArgumentException("arguments out of bounds");
    }
    return this.cascadePiles.get(pileNum).get(card);
  }

  /**
   * Returns top card at specified pile regardless of whether it is visible.
   *
   * @param foundationPile is the 0-indexed foundation pile
   * @return card at top of foundation pile
   */
  private Card forceGetCardAt(int foundationPile) {
    this.errorIfGameHasNotStarted();
    if (foundationPile < 0 || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("argument out of bounds");
    }
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      return null;
    }
    return this.foundationPiles.get(foundationPile).
        get(this.foundationPiles.get(foundationPile).size() - 1);
  }

  /**
   * Determines if a given card is eligible to be added to a foundation pile.
   *
   * @param card           is the card
   * @param foundationPile is the 0-based index of the foundation pile
   * @return true if the card can be added, false otherwise.
   */
  private boolean canBeAddedToFoundationPile(Card card, int foundationPile) {
    //if pile is empty and card is an ace, can be added
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      return card.isSameValue(new BasicCard(Value.Ace, Suit.Spades));
    }
    //if card is same suit and one value higher than foundation, can be added
    else {
      return card.isSameSuit(this.forceGetCardAt(foundationPile)) &&
          this.forceGetCardAt(foundationPile).isValueOneLessThanOther(card);
    }
  }

  /**
   * Determines if a given card is eligible to be added to a cascade pile.
   *
   * @param card        is the card
   * @param cascadePile is the 0-based index of the pile
   * @return true if the card can be added, false otherwise.
   */
  protected boolean canBeAddedToCascadePile(Card card, int cascadePile) {
    if (!card.isFaceUp()) {
      return false;
    }
    int topCascadeIdx = this.cascadePiles.get(cascadePile).size() - 1;
    //if pile is empty and card is a king, can be added
    if (this.cascadePiles.get(cascadePile).isEmpty()) {
      return card.isSameValue(new BasicCard(Value.King, Suit.Spades));
    }
    //if card is different color and one value higher than cascade, can be added
    else {
      return !card.isSameColor(this.forceGetCardAt(cascadePile, topCascadeIdx)) &&
          card.isValueOneLessThanOther(this.forceGetCardAt(cascadePile, topCascadeIdx));
    }
  }

  /**
   * For each card in the cascade piles, check if it can be added to any cascade piles.
   *
   * @return true if a card can be added, false otherwise
   */
  private boolean anyCardCanBeAddedToAnyCascadePile() {
    for (int i = 0; i < this.getNumPiles(); i++) {
      for (int j = 0; j < this.cascadePiles.get(i).size(); j++) {
        for (int z = 0; z < this.getNumPiles(); z++) {
          if (this.canBeAddedToCascadePile(this.cascadePiles.get(i).get(j), z) &&
              this.isCardVisible(i, j)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * For each card in the cascade piles, check if it can be added to any foundation piles.
   *
   * @return true if a card can be added, false otherwise
   */
  private boolean anyCardCanBeAddedToAnyFoundationPile() {
    for (int i = 0; i < this.getNumPiles(); i++) {
      for (int j = 0; j < this.cascadePiles.get(i).size(); j++) {
        for (int z = 0; z < this.getNumFoundations(); z++) {
          if (this.canBeAddedToFoundationPile(this.cascadePiles.get(i).get(j), z) &&
              this.isCardVisible(i, j)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * For each draw card, check if it can be added to any cascade pile.
   *
   * @return true if a card can be added, false otherwise
   */
  private boolean anyDrawCardCanBeAddedToAnyCascadePile() {
    for (Card card : this.drawCards) {
      for (int j = 0; j < this.cascadePiles.size(); j++) {
        if (this.canBeAddedToCascadePile(card, j)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * For each draw card, check if it can be added to any foundation pile.
   *
   * @return true if a card can be added, false otherwise
   */
  private boolean anyDrawCardCanBeAddedToAnyFoundationPile() {
    for (Card card : this.drawCards) {
      for (int j = 0; j < this.foundationPiles.size(); j++) {
        if (this.canBeAddedToFoundationPile(card, j)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Generates a standard deck of cards containing 4 suits(♣, ♠, ♡, ♢) of 2 through Ace cards. 52
   * cards are in this deck.
   *
   * @return the deck of cards.
   */
  protected List<Card> generateStandardDeck() {
    List<Card> temp = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (Value value : Value.values()) {
        temp.add(new BasicCard(value, suit));
      }
    }
    return temp;
  }

  /**
   * Ensures that a deck doesn't violate any restrictions.
   * This method does not verify that a deck contains valid cards.
   * @param deck is the deck supplied.
   */
  private void validateDeck(List<Card> deck) {
    if (deck == null || deck.contains(null)) {
      throw new IllegalArgumentException("deck and/or cards cannot be null");
    }
    if (deck.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be empty");
    }
  }

  /**
   * Ensures that starting the game won't violate any restrictions.
   * @param deck is the deck of the game.
   * @param numPiles is the number of cascade piles.
   * @param numDraw is the number of draw piles.
   */
  private void validateStartGameArgs(List<Card> deck, int numPiles, int numDraw) {
    this.validateDeck(deck);
    if (numDraw <= 0 || numPiles <= 0) {
      throw new IllegalArgumentException("argument(s) cannot be <= 0");
    }
  }

  /**
   * Ensures that a given cascade pile is valid.
   * @param pileNum is the index of the pile.
   */
  private void validateCascadePile(int pileNum) {
    if (pileNum < 0 || pileNum >= this.getNumPiles()) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
  }

  /**
   * Ensures that a given foundation pile is valid.
   * @param pileNum is the index of the pile.
   */
  private void validateFoundationPile(int pileNum) {
    if (pileNum < 0 || pileNum >= this.getNumFoundations()) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
  }

  /**
   * Ensures that given source and destination cascade piles are valid.
   * @param srcPile is the source pile.
   * @param destPile is the destination pile.
   */
  private void validateSrcAndDestPile(int srcPile, int destPile) {
    if (srcPile == destPile) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    this.validateCascadePile(srcPile);
    this.validateCascadePile(destPile);
  }

  /**
   * Ensures that the number of cards provided doesn't violate any restrictions.
   * @param numCards is the number of cards.
   * @param srcPile is the index of the cascade pile.
   */
  private void validateNumCards(int numCards, int srcPile) {
    if (numCards <= 0 || numCards > this.cascadePiles.get(srcPile).size()) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
  }

  /**
   * Ensures that the draw pile can be taken from.
   */
  private void validateDrawPile() {
    if (this.drawCards.isEmpty()) {
      throw new IllegalStateException("No draw cards remaining");
    }
  }

  /**
   * Ensures that a cascade pile is not empty.
   * @param srcPile is the index of the cascade pile.
   */
  private void validatePileIsNotEmpty(int srcPile) {
    if (this.cascadePiles.get(srcPile).isEmpty()) {
      throw new IllegalStateException("source pile is empty");
    }
  }

  /**
   * Moves the specified number of cards from one cascade pile to another.
   * @param srcPile is the source cascade pile.
   * @param numCards is the number of cards.
   * @param destPile is the destionation cascade pile.
   */
  protected void moveAllCardsToPile(int srcPile, int numCards, int destPile) {
    for (int i = 0; i < numCards; i++) {
      this.cascadePiles.get(destPile).
          add(this.cascadePiles.get(srcPile).
              remove(this.cascadePiles.get(srcPile).size() - numCards + i));
    }
  }
}

