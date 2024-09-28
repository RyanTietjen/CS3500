package cs3500.lab2.skills;


abstract class AbstractSkill implements Skill {

  protected boolean isSatisfiedBy(Years other) {
    return false;
  }

  protected boolean isSatisfiedBy(Ability other) {
    return false;
  }

  public boolean equals(Object other) {
    if ((other instanceof Years)) {
      Years that = (Years)other;
      return this.sameYear(that);
    }

    if (other instanceof Ability) {
      Ability that = (Ability)other;
      return this.sameAbility(that);
    }

    return false;
  }

  public int hashCode() {
    return 1;
  }

  protected boolean sameYear(Years other) {
    return false;
  }

  protected boolean sameAbility(Ability other) {
    return false;
  }

}
