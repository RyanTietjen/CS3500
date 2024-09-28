package cs3500.lab2.skills;

/**
 * Represents the number of years of experience.
 * As a requirement, it is satisfied if another Year
 * meets or exceeds the number of years in this skill.
 */
public class Years extends AbstractSkill {

  public int numYears;

  /**
   * A number of years that must be non-negative.
   * @param y is the num of years
   */
  public Years(int y) {
    if (y < 0) {
      throw new IllegalArgumentException("Years cannot be negative");
    }
    this.numYears = y;
  }

  @Override
  public boolean satisfiesReq(Skill requirement) {
    if (!(requirement instanceof AbstractSkill)) {
      return false;
    }
    AbstractSkill that = (AbstractSkill) requirement;
    return that.isSatisfiedBy(this);
  }

  protected boolean isSatisfiedBy(Years that) {
    return that.numYears >= this.numYears;
  }

  @Override
  protected boolean sameYear(Years that) {
    return this.numYears == that.numYears;
  }
}
