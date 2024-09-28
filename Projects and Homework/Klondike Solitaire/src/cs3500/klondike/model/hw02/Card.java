package cs3500.klondike.model.hw02;


/**
 * Represents a playing card that has a Suit and a Value.
 */
public interface Card {
  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   * @return the formatted card
   */
  public String toString();

  /**
   * Determines if two cards have the same value.
   * @param other is the other card.
   * @return true if the cards have the same value, false otherwise.
   */
  public boolean isSameValue(Card other);

  /**
   * Determines if this card's value is one less than the other.
   * @param other is the other card
   * @return true if this card's value is one less than the other, false otherwise.
   */
  public boolean isValueOneLessThanOther(Card other);

  /**
   * Determines if two cards have the same suit.
   * @param other is the other card.
   * @return true if two cards have the same value, false otherwise.
   */
  public boolean isSameSuit(Card other);

  /**
   * Determines if two cards have the same color.
   * @param other is the other card.
   * @return true if two cards have the same color, false otherwise.
   */
  public boolean isSameColor(Card other);

  /**
   * Flips this card up.
   */
  public void flipUp();

  /**
   * Flips this card down.
   */
  public boolean equals(Object other);

  /**
   * Returns the value of the card.
   * @return the value of the card.
   */
  public Value getValue();

  /**
   * Returns the suit of the card.
   * @return the suit of the card.
   */
  public Suit getSuit();

  /**
   * Flips the card up.
   */
  public boolean isFaceUp();

  /**
   * Returns the hashCode of this card.
   * @return the hashCode of this card
   */
  public int hashCode();
}
