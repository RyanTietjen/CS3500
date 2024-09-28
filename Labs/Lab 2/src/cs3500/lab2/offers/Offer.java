package cs3500.lab2.offers;

import java.util.List;

import cs3500.lab2.skills.Skill;

/**
 * Offers can calculate their salary and check if
 * an application satisfies the requirements for the
 * offer.
 */
public interface Offer {

  /**
   * Calculates the yearly salary for the offer,
   * assuming 52 weeks in a year.
   * @return yearly salary for the offer
   */
  int calculateSalary();

  /**
   * Returns true iff some subset of skills in the application
   * satisfy all required skills for the offer.
   * @param application the set of skills
   * @return true iff all required skills are satisfied
   */
  boolean satisfiesRequirements(List<Skill> application);

}