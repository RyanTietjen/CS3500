package cs3500.klondike.model.hw02;

/**
 * Represents a Suit of a standard playing card.
 */
public enum Suit {
  Spades("♠", "Black"),
  Clubs("♣", "Black"),
  Hearts("♡", "Red"),
  Diamonds("♢", "Red");

  private final String description;
  private final String color;
  Suit(String description, String color) {
    this.description = description;
    this.color = color;
  }

  /**
   * Displays this suit as a string.
   * @return a representation of this Suit as a String
   */
  public String toString() {
    return this.description;
  }

  /**
   * Determines if this suit is the same color as another suit.
   * @param other is the other Suit
   * @return true if same color, false otherwise
   */
  public boolean isSameColor(Suit other) {
    if (other == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
    return this.color.equals(other.color);
  }

  /**
   * Determines if this suit is the same suit as another suit.
   * @param other is the other Suit
   * @return true if same suit, false otherwise
   */
  public boolean isSameSuit(Suit other) {
    if (other == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
    return this.description.equals(other.description);
  }
}