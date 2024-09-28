package cs3500.lab2.offers;

import java.util.List;

import cs3500.lab2.skills.Skill;

/**
 * Represents a Coop offer that has a maximum number of hours and an hourly rate.
 */
public class Coop extends AOffer {
  private final int hourlyRate;
  private final int maxHours;

  /**
   * Represents a Coop offer that has a maximum number of hours and an hourly rate.
   */
  public Coop(String description, int rate, int hours, List<Skill> reqs) {
    super(description, reqs);
    Utils helper = new Utils();
    helper.isNonNeg(rate);
    helper.isNonNeg(hours);
    this.hourlyRate = rate;
    this.maxHours = hours;
  }

  /**
   * Calculates the yearly salary based on the hourly rate and the maximum number of hours.
   * @return yearly salary for a coop
   */
  @Override
  public int calculateSalary() {
    return this.hourlyRate * this.maxHours * 52;
  }
}
