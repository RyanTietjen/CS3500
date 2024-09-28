package cs3500.lab2.offers;

import java.util.List;

import cs3500.lab2.skills.Skill;

/**
 * Represents a volunteer offer that has
 * no form of pay.
 */
public class Volunteer extends AOffer {

  public Volunteer(String description, List<Skill> reqs) {
    super(description, reqs);
  }
}
