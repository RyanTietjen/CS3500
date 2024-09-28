package cs3500.klondike.model.hw02;

/**
 * Represents a standard playing card.
 */
public class BasicCard implements Card {

  private Value value;
  private Suit suit;
  // A card either displays the face side (i.e. is visible) or the
  // blank side (i.e. is not visible). You can either see the face of the card
  // or you cannot (if a card was being bent or held such that both sides were visible,
  // the card would still be "face-up" since the face value is visible).
  // If being visible/invisible was not an intrinsic property of
  // a card, then cards would not have a blank side.
  private boolean isFaceUp;

  /**
   * Constructor for a playing card.
   * @param value is the value of the card.
   * @param suit is the suit of the card.
   */
  public BasicCard(Value value, Suit suit) {
    this.value = value;
    this.suit = suit;
    this.isFaceUp = false;
  }

  /**
   * Constructor for a playing card.
   * @param value is the value of the card.
   * @param suit is the suit of the card.
   * @param isVisible is if the card is heads up.
   */
  public BasicCard(Value value, Suit suit, boolean isVisible) {
    this.value = value;
    this.suit = suit;
    this.isFaceUp = isVisible;
  }

  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  @Override
  public boolean isSameValue(Card other) {
    return this.value.isSameValue(other.getValue());
  }

  @Override
  public boolean isValueOneLessThanOther(Card other) {
    return this.value.isThisValueOneLessThanOther(other.getValue());
  }

  @Override
  public boolean isSameSuit(Card other) {
    return this.suit.isSameSuit(other.getSuit());
  }

  @Override
  public boolean isSameColor(Card other) {
    return this.suit.isSameColor(other.getSuit());
  }

  @Override
  public void flipUp() {
    this.isFaceUp = true;
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Card)) {
      throw new IllegalArgumentException("cannot compare a card and a non-card");
    }
    Card temp = (Card) other;
    return this.value.isSameValue(temp.getValue()) && this.suit.isSameSuit(temp.getSuit())
            && (this.isFaceUp == temp.isFaceUp());
  }

  @Override
  public Value getValue() {
    return this.value;
  }

  @Override
  public Suit getSuit() {
    return this.suit;
  }

  @Override
  public boolean isFaceUp() {
    return this.isFaceUp;
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }


}
