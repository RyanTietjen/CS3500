package cs3500.lab2.offers;

import java.util.List;
import java.util.Objects;

import cs3500.lab2.skills.Skill;

/**
 * Offers can calculate their salary and check if
 * an application satisfies the requirements for the
 * offer.
 */
abstract class AOffer implements Offer {
  String jobDescription;
  final List<Skill> requirements;

  AOffer(String jobDescription, List<Skill> requirements) {
    this.jobDescription = Objects.requireNonNull(jobDescription);
    this.requirements = Objects.requireNonNull(requirements);
  }

  /**
   * Calculates the yearly salary for the offer,
   * assuming 52 weeks in a year.
   * The default yearly salary is 0 since
   * not every offer will guarantee pay.
   * Should be overridden as appropriate
   * @return yearly salary for the offer
   */
  public int calculateSalary() {
    return 0;
  }

  /**
   * Returns true iff some subset of skills in the application
   * satisfy all required skills for the offer.
   * @param application the set of skills
   * @return true iff all required skills are satisfied
   */
  public boolean satisfiesRequirements(List<Skill> application) {
    for (Skill req : requirements) {
      if (!application.stream().anyMatch((appSkill) -> appSkill.satisfiesReq(req))) {
        return false;
      }
    }
    return true;
  }
}

