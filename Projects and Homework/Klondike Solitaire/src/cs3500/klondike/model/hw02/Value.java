package cs3500.klondike.model.hw02;

/**
 * Represents the Value of a standard playing card.
 */
public enum Value {

  Ace("A", 1),
  Two("2", 2),
  Three("3", 3),
  Four("4", 4),
  Five("5", 5),
  Six("6", 6),
  Seven("7", 7),
  Eight("8", 8),
  Nine("9", 9),
  Ten("10", 10),
  Jack("J", 11),
  Queen("Q", 12),
  King("K", 13);


  private final String description;
  private final int value;

  Value(String description, int value) {
    this.description = description;
    this.value = value;
  }

  /**
   * Displays this value as a string.
   * @return a representation of this Suit as a String
   */
  public String toString() {
    return this.description;
  }

  /**
   * Determines if this Value is the same as another.
   * @param other is the other Value
   * @return true if this Value is the same as the other Value, false otherwise
   */
  public boolean isSameValue(Value other) {
    if (other == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
    return this.description.equals(other.description);
  }

  /**
   * Determines if this Value is one less than the other Value.
   * @param other is the other value
   * @return true is this value is one less than the other value.
   */
  public boolean isThisValueOneLessThanOther(Value other) {
    if (other == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
    return (this.value + 1) == other.value;
  }
}
