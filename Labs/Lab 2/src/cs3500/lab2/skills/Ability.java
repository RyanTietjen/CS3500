package cs3500.lab2.skills;

import java.util.Objects;

/**
 * Represents an Ability (e.g. "programming").
 */
public class Ability extends AbstractSkill {

  final String ability;

  public Ability(String ability) {
    Objects.requireNonNull(ability);
    this.ability = ability;
  }

  @Override
  public boolean satisfiesReq(Skill requirement) {
    if (!(requirement instanceof AbstractSkill)) {
      return false;
    }
    AbstractSkill that = (AbstractSkill) requirement;
    return that.isSatisfiedBy(this);
  }

  @Override
  protected boolean isSatisfiedBy(Ability other) {
    return other.ability.equals(this.ability);
  }

  @Override
  public boolean sameAbility(Ability that) {
    return that.ability.equals(this.ability);
  }
}
