package cs3500.klondike;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw04.KlondikeCreator;
import cs3500.klondike.model.hw04.KlondikeCreator.GameType;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the KlondikeCreator class.
 */
public class KlondikeCreatorTests {

  @Test
  public void testCreateWithGameType() {
    Assert.assertEquals("should be equal",
        KlondikeCreator.create(GameType.BASIC).getClass(),
        BasicKlondike.class);
    Assert.assertEquals("should be equal",
        KlondikeCreator.create(GameType.LIMITED).getClass(),
        LimitedDrawKlondike.class);
    Assert.assertEquals("should be equal",
        KlondikeCreator.create(GameType.WHITEHEAD).getClass(),
        WhiteheadKlondike.class);
  }

  @Test
  public void testCreateWithString() {
    Assert.assertEquals("should be equal",
        KlondikeCreator.create("basic").getClass(),
        BasicKlondike.class);
    Assert.assertEquals("should be equal",
        KlondikeCreator.create("limited").getClass(),
        LimitedDrawKlondike.class);
    Assert.assertEquals("should be equal",
        KlondikeCreator.create("limited", 100).getClass(),
        LimitedDrawKlondike.class);
    Assert.assertThrows("Cannot make limited draw game with negative redraw attempsts.",
        IllegalArgumentException.class, () -> KlondikeCreator.create("limited", -1));
    Assert.assertEquals("should be equal",
        KlondikeCreator.create("whitehead").getClass(),
        WhiteheadKlondike.class);
  }

}
