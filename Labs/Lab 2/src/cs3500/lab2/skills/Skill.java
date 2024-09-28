package cs3500.lab2.skills;

/**
 * Skills for job offers can be used as requirements
 * or to satisfy a requirement.
 */
public interface Skill {
  /**
   * Returns true if this skill satisifies a given
   * requirement. The exact details will depend on
   * the given skill.
   * @param requirement the skill to meet or exceed
   * @return true iff the skill meets or exceeds the
   *         requirement
   */
  boolean satisfiesReq(Skill requirement);

  boolean equals(Object other);
}
