package cs3500.lab2.skills;

/**
 * Represents an ability and a skill level of that ability.
 */
public class ProficientAbility implements Skill {
  Ability ability;
  /**
   * A proficiency is either a "beginner," an "intermediate,"
   * or an "expert".
   */
  String proficiency;

  /**
   * Constructor that determines if the provided proficiency is valid.
   * @param ability is an ability
   * @param proficiency is how skilled the person is at the ability
   */
  public ProficientAbility(Ability ability, String proficiency) {
    this.ability = ability;
    if (proficiency.equals("beginner")
            || proficiency.equals("intermediate")
            || proficiency.equals("expert")) {
      this.proficiency = proficiency;
    }
    else {
      throw new IllegalArgumentException("Invalid proficiency level.");
    }
  }

  public boolean satisfiesReq(Skill requirement) {
    return this.ability.satisfiesReq(requirement);
  }

  /**
   * Determines if skill and proficiency requirements are met.
   * @param requirement is the requirement
   * @param profRequirement is the proficiency
   * @return True if both requirements are met
   */
  public boolean satisfiesReqAndProficiency(Skill requirement, String profRequirement) {
    boolean prof = false;
    if (profRequirement.equals("beginner")) {
      prof = true;
    }
    else if (profRequirement.equals("intermediate")
            && (this.proficiency.equals("intermediate")
            || this.proficiency.equals("expert"))) {
      prof = true;
    }
    else if (profRequirement.equals("expert")
            && this.proficiency.equals("expert")) {
      prof = true;
    }
    return prof && this.satisfiesReq(requirement);
  }
}
