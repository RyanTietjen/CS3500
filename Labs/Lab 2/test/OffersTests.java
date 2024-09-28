import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.lab2.offers.Coop;
import cs3500.lab2.offers.FullTimeJob;
import cs3500.lab2.offers.Offer;
import cs3500.lab2.offers.Volunteer;
import cs3500.lab2.skills.Ability;
import cs3500.lab2.skills.Skill;
import cs3500.lab2.skills.Years;

/**
 * Tests the methods/classes relating to offers.
 */
public class OffersTests {
  String dev;
  int salary;
  Ability ood;
  Years yrs;

  @Before
  public void init() {
    dev = "Software dev";
    salary = 1000;
    ood = new Ability("OOD");
    yrs = new Years(15);
  }

  @Test
  public void testValidFullTimeJobConstruction() {
    Offer fullTime = new FullTimeJob("Softwared Dev", 1000, new ArrayList<>());
    Assert.assertEquals(1000, fullTime.calculateSalary());
  }

  @Test
  public void testInvalidFullTimeJobConstruction() {
    Assert.assertThrows(NullPointerException.class,
        () -> new FullTimeJob(null, salary, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new FullTimeJob(dev, salary * -1, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class,
        () -> new FullTimeJob(dev, salary, null));
  }

  @Test
  public void testValidCoopConstruction() {
    Offer coop = new Coop("Startup Co-op", 30, 40, new ArrayList<>());
    Assert.assertEquals(30 * 40 * 52, coop.calculateSalary());
  }

  @Test
  public void testInvalidCoopConstruction() {
    int rate = 10;
    int hours = 7;
    Assert.assertThrows(NullPointerException.class,
        () -> new Coop(null, rate, hours, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new Coop("Startup co-op", -5, hours, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new Coop("Startup co-op", rate, -1, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class,
        () -> new Coop("Startup co-op", rate, hours, null));
  }

  @Test
  public void testValidVolunteerConstruction() {
    Offer volunteer = new Volunteer("Big Sibling", new ArrayList<>());
    Assert.assertEquals(0, volunteer.calculateSalary());
  }

  @Test
  public void testInvalidVolunteerConstruction() {
    Assert.assertThrows(NullPointerException.class
        , () -> new Volunteer(null, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class
        , () -> new Volunteer("Big Sibling", null));
  }

  @Test
  public void satisfiesFullTimeJob() {
    Offer fullTime = new FullTimeJob("Software Designer",
            120000
        , List.of(ood, yrs));
    List<Skill> readyApplicant = List.of(ood, yrs);
    List<Skill> needsOODApplicant = List.of(yrs);
    List<Skill> totallyUnqualified = new ArrayList<>();

    Assert.assertTrue(fullTime.satisfiesRequirements(readyApplicant));
    Assert.assertFalse(fullTime.satisfiesRequirements(totallyUnqualified));
    Assert.assertFalse(fullTime.satisfiesRequirements(needsOODApplicant));
  }

  @Test
  public void satisfiesCoop() {
    Offer coopOffer = new Coop("Startup Co-op", 26, 40
        , List.of(ood, new Years(1)));
    List<Skill> qualifiedApplicant = List.of(ood, yrs);
    List<Skill> needsOODApplicant = List.of(yrs);
    List<Skill> unqualifiedApplicant = new ArrayList<>();

    Assert.assertTrue(coopOffer.satisfiesRequirements(qualifiedApplicant));
    Assert.assertFalse(coopOffer.satisfiesRequirements(needsOODApplicant));
    Assert.assertFalse(coopOffer.satisfiesRequirements(unqualifiedApplicant));
  }

  @Test
  public void satisfiesVolunteer() {
    Offer volunteerJob = new Volunteer("Big Sibling"
        , List.of(new Ability("communication"), new Years(3)));
    List<Skill> qualifiedApplicant = List.of(new Ability("communication"), new Years(3));
    List<Skill> needsCommunicationApplicant = List.of(new Years(3));
    List<Skill> unqualifiedApplicant = new ArrayList<>();

    Assert.assertTrue(volunteerJob.satisfiesRequirements(qualifiedApplicant));
    Assert.assertFalse(volunteerJob.satisfiesRequirements(needsCommunicationApplicant));
    Assert.assertFalse(volunteerJob.satisfiesRequirements(unqualifiedApplicant));
  }

  @Test
  public void testSalary() {
    Offer fullTime = new FullTimeJob("Software Designer"
        ,120000
        , List.of(ood, new Years(15)));
    Offer coopOffer = new Coop("Startup Co-op", 26, 40
        , List.of(ood, new Years(1)));
    Offer volunteer = new Volunteer("Big Sibling"
        , List.of(new Ability("communication"), new Years(3)));
    Assert.assertEquals(120000, fullTime.calculateSalary());
    Assert.assertEquals(26 * 40 * 52, coopOffer.calculateSalary());
    Assert.assertEquals(0, volunteer.calculateSalary());
  }
}

