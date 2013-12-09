/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.color;

import net.sf.mmm.util.nls.api.IllegalCaseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link GenericColor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericColorTest extends Assert {

  /** The precision for an exact match. */
  private static final double PRECISION_CLOSE_MATCH = 0.0000001;

  /**
   * Checks that the given {@link Segment}s match according to <code>matchType</code>.
   * 
   * @param expected is the expected {@link Segment}.
   * @param actual is the current {@link Segment} to compare with <code>expected</code>.
   * @param matchType is the {@link MatchType}.
   */
  private void checkSegment(Segment<?> expected, Segment<?> actual, MatchType matchType) {

    switch (matchType) {
      case SAME:
        assertSame(expected, actual);
        break;
      case EQUAL:
        assertNotSame(expected, actual);
        assertEquals(expected, actual);
        break;
      case CLOSE_MATCH:
        assertNotSame(expected, actual);
        assertEquals(expected.getValueAsFactor(), actual.getValueAsFactor(), PRECISION_CLOSE_MATCH);
        break;
      default :
        throw new IllegalCaseException(MatchType.class, matchType);
    }
  }

  /**
   * Checks that all {@link Segment}s of the given {@link GenericColor} match to the given values. This method
   * expects that the four {@link Segment}s used to create the {@link GenericColor} via the given
   * {@link ColorModel} have to be same. The others have to be equal (if <code>allEqual</code> is
   * <code>true</code>) or at least match with {@link #PRECISION_CLOSE_MATCH}.
   * 
   * @param color is the {@link GenericColor} to test.
   * @param red is the expected {@link Red} value.
   * @param green is the expected {@link Green} value.
   * @param blue is the expected {@link Blue} value.
   * @param hue is the expected {@link Hue} value.
   * @param saturationHsb is the expected {@link Saturation} value for HSB.
   * @param saturationHsl is the expected {@link Saturation} value for HSL.
   * @param brightness is the expected {@link Brightness} value.
   * @param lightness is the expected {@link Lightness} value.
   * @param chroma is the expected {@link Chroma} value.
   * @param alpha is the expected {@link Alpha} value.
   * @param allEqual - <code>true</code> if all {@link Segment}s have to be equal, <code>false</code> if
   *        {@link #PRECISION_CLOSE_MATCH} is sufficient for all {@link Segment}s except the four used to
   *        create the color.
   * @param model is the {@link ColorModel} that was used to create the given <code>color</code>.
   */
  private void checkColor(GenericColor color, Red red, Green green, Blue blue, Hue hue, Saturation saturationHsb,
      Saturation saturationHsl, Brightness brightness, Lightness lightness, Chroma chroma, Alpha alpha,
      boolean allEqual, ColorModel model) {

    MatchType matchDefault;
    if (allEqual) {
      matchDefault = MatchType.EQUAL;
    } else {
      matchDefault = MatchType.CLOSE_MATCH;
    }
    MatchType matchModel = matchDefault;
    if (model == ColorModel.RGB) {
      matchModel = MatchType.SAME;
    }
    checkSegment(red, color.getRed(), matchModel);
    checkSegment(green, color.getGreen(), matchModel);
    checkSegment(blue, color.getBlue(), matchModel);

    if (model == ColorModel.RGB) {
      matchModel = matchDefault;
    } else {
      matchModel = MatchType.SAME;
    }
    checkSegment(hue, color.getHue(), matchModel);
    if (model == ColorModel.HSB) {
      matchModel = MatchType.SAME;
    } else {
      matchModel = matchDefault;
    }
    checkSegment(saturationHsb, color.getSaturationHsb(), matchModel);
    checkSegment(brightness, color.getBrightness(), matchModel);
    if ((model == ColorModel.HSL) || (model == ColorModel.HSV)) {
      matchModel = MatchType.SAME;
    } else {
      matchModel = matchDefault;
    }
    checkSegment(saturationHsl, color.getSaturationHsl(), matchModel);
    checkSegment(lightness, color.getLightness(), matchModel);

    checkSegment(chroma, color.getChroma(), matchDefault);
    checkSegment(alpha, color.getAlpha(), MatchType.SAME);

    if (allEqual) {
      for (ColorModel colorModel : ColorModel.values()) {
        String colorString = color.toString(colorModel);
        if (!color.equals(GenericColor.valueOf(colorString))) {
          System.out.println("Buh!");
        }
        assertEquals(color, GenericColor.valueOf(colorString));
      }
    } else {
      String colorString = color.toString(model);
      assertEquals(color, GenericColor.valueOf(colorString));
    }

  }

  /**
   * Tests of {@link GenericColor} for {@link Color#WHITE white}.
   */
  @Test
  public void testExampleWhite() {

    // given
    ColorModel model = ColorModel.RGB;
    Red red = new Red(1.0);
    Green green = new Green(1.0);
    Blue blue = new Blue(1.0);
    Alpha alpha = new Alpha(1.0);

    // when
    GenericColor color = GenericColor.valueOf(red, green, blue, alpha);

    // then
    assertEquals(color, GenericColor.valueOf("white"));
    assertEquals(color, GenericColor.valueOf("#FFFFFF"));
    checkColor(color, red, green, blue, new Hue(0.0), new Saturation(0.0), new Saturation(0.0), new Brightness(1.0),
        new Lightness(1.0), new Chroma(0.0), alpha, true, model);
  }

  /**
   * Tests of {@link GenericColor} for medium (0.5) gray.
   */
  @Test
  public void testExampleGray() {

    // given
    ColorModel model = ColorModel.RGB;
    Red red = new Red(0.5);
    Green green = new Green(0.5);
    Blue blue = new Blue(0.5);
    Alpha alpha = new Alpha(1.0);

    // when
    GenericColor color = GenericColor.valueOf(red, green, blue, alpha);

    // then
    assertEquals("#7F7F7F", color.toColor().toString());
    checkColor(color, red, green, blue, new Hue(0.0), new Saturation(0.0), new Saturation(0.0), new Brightness(0.5),
        new Lightness(0.5), new Chroma(0.0), alpha, true, model);
  }

  /**
   * Tests of {@link GenericColor} for {@link Color#BLACK black}.
   */
  @Test
  public void testExampleBlack() {

    // given
    ColorModel model = ColorModel.RGB;
    Red red = new Red(0.0);
    Green green = new Green(0.0);
    Blue blue = new Blue(0.0);
    Alpha alpha = new Alpha(1.0);

    // when
    GenericColor color = GenericColor.valueOf(red, green, blue, alpha);

    // then
    assertEquals(color, GenericColor.valueOf("BLACK"));
    assertEquals(color, GenericColor.valueOf("#000000"));
    checkColor(color, red, green, blue, new Hue(0.0), new Saturation(0.0), new Saturation(0.0), new Brightness(0.0),
        new Lightness(0.0), new Chroma(0.0), alpha, true, model);
  }

  /**
   * Tests of {@link GenericColor} for {@link Color#RED red}.
   */
  @Test
  public void testExampleRed() {

    // given
    ColorModel model = ColorModel.RGB;
    Red red = new Red(1.0);
    Green green = new Green(0.0);
    Blue blue = new Blue(0.0);
    Alpha alpha = new Alpha(1.0);

    // when
    GenericColor color = GenericColor.valueOf(red, green, blue, alpha);

    // then
    assertEquals("#FF0000", color.toColor().toString());
    assertEquals(color, GenericColor.valueOf("rEd"));
    checkColor(color, red, green, blue, new Hue(0.0), new Saturation(1.0), new Saturation(1.0), new Brightness(1.0),
        new Lightness(0.5), new Chroma(1.0), alpha, true, model);
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSL}.
   */
  @Test
  public void testExampleHslSpecial() {

    // given
    ColorModel model = ColorModel.HSL;
    Hue hue = new Hue(240.5);
    Saturation saturationHsl = new Saturation(0.290);
    Lightness lightness = new Lightness(0.607);
    Alpha alpha = new Alpha(0.625);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsl, lightness, alpha);

    // then
    checkColor(color, new Red(0.4949295), new Green(0.4930299), new Blue(0.72097), hue, new Saturation(0.3161574),
        saturationHsl, new Brightness(0.72097), lightness, new Chroma(0.22794), alpha, false, model);
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSB}.
   */
  @Test
  public void testExampleHsbSpecial() {

    // given
    ColorModel model = ColorModel.HSB;
    Hue hue = new Hue(14.3);
    Saturation saturationHsb = new Saturation(0.661);
    Brightness brightness = new Brightness(0.931);
    Alpha alpha = new Alpha(0.625);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsb, brightness, alpha);

    // then
    Red red = new Red(0.931);
    Green green = new Green(0.462277188);
    Blue blue = new Blue(0.315609);
    Lightness lightness = new Lightness(0.6233045);
    Saturation saturationHsl = new Saturation(0.8168282);
    Chroma chroma = new Chroma(0.615391);
    checkColor(color, red, green, blue, hue, saturationHsb, saturationHsl, brightness, lightness, chroma, alpha, false,
        model);
    // check additional precision...
    assertEquals(red, color.getRed());
    assertEquals(lightness, color.getLightness());
    assertEquals(chroma, color.getChroma());
  }

  /**
   * The type of a match.
   */
  private enum MatchType {

    /** Objects must be same (==). */
    SAME,

    /** Objects must be equal. */
    EQUAL,

    /** Objects must be with {@link GenericColorTest#PRECISION_CLOSE_MATCH}. */
    CLOSE_MATCH
  }

}
