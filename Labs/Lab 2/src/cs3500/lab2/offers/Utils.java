package cs3500.lab2.offers;

/**
 * Helper method that helps determine if
 * non-negative parameters are supplied.
 */

class Utils {
  /**
   * Determines if a non-negative number is supplied
   * Does not return anything, simply throws an error
   * if a negative number is supplied.
   */
  void isNonNeg(int num) {
    if (num < 0) {
      throw new IllegalArgumentException("Hourly rate cannot be negative.");
    }
  }
}
