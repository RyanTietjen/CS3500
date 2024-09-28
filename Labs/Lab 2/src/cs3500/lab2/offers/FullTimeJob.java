package cs3500.lab2.offers;

import java.util.List;

import cs3500.lab2.skills.Skill;


/**
 * Represents a Full time job offer that has a yearly salary.
 */
public class FullTimeJob extends AOffer {
  private int yearlySalary;

  /**
   * Represents a Full time job offer that has a yearly salary.
   */
  public FullTimeJob(String description, int yearlySalary, List<Skill> reqs) {
    super(description, reqs);
    Utils helper = new Utils();
    helper.isNonNeg(yearlySalary);
    this.yearlySalary = yearlySalary;
  }


  /**
   * No calculation required for the yearly salary.
   * @return yearly salary for the offer
   */
  @Override
  public int calculateSalary() {
    return this.yearlySalary;
  }
}
