/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsParseException;

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
        checkStringConversion(color, colorModel);
      }
    } else {
      checkStringConversion(color, model);
    }

  }

  /**
   * Checks the conversion of {@link GenericColor} {@link GenericColor#valueOf(String) from} and
   * {@link GenericColor#toString(ColorModel) to} {@link String}.
   * 
   * @param color is the {@link GenericColor} to test.
   * @param colorModel is the expected {@link ColorModel}.
   */
  private void checkStringConversion(GenericColor color, ColorModel colorModel) {

    String colorString = color.toString(colorModel);
    GenericColor copy = GenericColor.valueOf(colorString);
    assertEquals(color, copy);
    assertEquals(color.hashCode(), copy.hashCode());
    if (colorModel == ColorModel.RGB) {
      assertEquals(colorString, color.getValue());
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
    assertEquals(color, GenericColor.valueOf("rgb(0.5, 0.5, 0.5)"));
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
    assertEquals(color, GenericColor.valueOf("rgb(0, 0, 0)"));
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
  public void testExampleHsl7E7EB8() {

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
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSL}.
   */
  @Test
  public void testExampleHslA0A424() {

    // given
    ColorModel model = ColorModel.HSL;
    Hue hue = new Hue(61.8);
    Saturation saturationHsl = new Saturation(0.638);
    Lightness lightness = new Lightness(0.393);
    Alpha alpha = new Alpha(0.125);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsl, lightness, alpha);

    // then
    checkColor(color, new Red(0.62869), new Green(0.643734), new Blue(0.142266), hue, new Saturation(0.778998779),
        saturationHsl, new Brightness(0.643734), lightness, new Chroma(0.501468), alpha, false, model);
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSL}.
   */
  @Test
  public void testExampleHsl19CB97() {

    // given
    ColorModel model = ColorModel.HSL;
    Hue hue = new Hue(162.4);
    Saturation saturationHsl = new Saturation(0.779);
    Lightness lightness = new Lightness(0.447);
    Alpha alpha = new Alpha(0.3333);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsl, lightness, alpha);

    // then
    checkColor(color, new Red(0.098787), new Green(0.795213), new Blue(0.590928), hue, new Saturation(0.875773),
        saturationHsl, new Brightness(0.795213), lightness, new Chroma(0.696426), alpha, false, model);
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSB}.
   */
  @Test
  public void testExampleHsbED7651() {

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
    // check generic access
    assertSame(alpha, color.getSegment(ColorSegmentType.ALPHA));
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSB}.
   */
  @Test
  public void testExampleHsb80FFFF() {

    // given
    ColorModel model = ColorModel.HSB;
    Hue hue = new Hue(180.0);
    Saturation saturationHsb = new Saturation(0.500);
    Brightness brightness = new Brightness(1.000);
    Alpha alpha = new Alpha(0.9991);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsb, brightness, alpha);

    // then
    Red red = new Red(0.500);
    Green green = new Green(1.000);
    Blue blue = new Blue(1.000);
    Lightness lightness = new Lightness(0.750);
    Saturation saturationHsl = new Saturation(1.000);
    Chroma chroma = new Chroma(0.500);
    checkColor(color, red, green, blue, hue, saturationHsb, saturationHsl, brightness, lightness, chroma, alpha, true,
        model);
    // check additional precision...
    assertEquals(red, color.getRed());
    assertEquals(lightness, color.getLightness());
    assertEquals(chroma, color.getChroma());
    // check generic access
    assertSame(alpha, color.getSegment(ColorSegmentType.ALPHA));
  }

  /**
   * Tests of {@link GenericColor} for special color created with {@link ColorModel#HSL}.
   */
  @Test
  public void testExampleHslBF40BF() {

    // given
    ColorModel model = ColorModel.HSL;
    Hue hue = new Hue(300.0);
    Saturation saturationHsl = new Saturation(0.500);
    Lightness lightness = new Lightness(0.500);
    Alpha alpha = new Alpha(0.12345);

    // when
    GenericColor color = GenericColor.valueOf(hue, saturationHsl, lightness, alpha);

    // then
    Red red = new Red(0.750);
    Green green = new Green(0.250); // will not match exactly due to poor precision of double
    Blue blue = new Blue(0.750);
    Brightness brightness = new Brightness(0.750);
    Saturation saturationHsb = new Saturation(0.6666666666666666);
    Chroma chroma = new Chroma(0.500);
    checkColor(color, red, green, blue, hue, saturationHsb, saturationHsl, brightness, lightness, chroma, alpha, false,
        model);
    // check additional precision...
    assertEquals(red, color.getRed());
    assertEquals(lightness, color.getLightness());
    assertEquals(chroma, color.getChroma());
    // check generic access
    assertSame(alpha, color.getSegment(ColorSegmentType.ALPHA));
  }

  /**
   * Test of {@link GenericColor#invert(ColorModel)}.
   */
  @Test
  public void testInvert() {

    checkInvert(GenericColor.valueOf(Color.WHITE), GenericColor.valueOf(Color.BLACK));
    checkInvert(GenericColor.valueOf(Color.RED), GenericColor.valueOf(Color.CYAN));
    checkInvert(GenericColor.valueOf(Color.GREEN), GenericColor.valueOf(Color.MAGENTA));
    checkInvert(GenericColor.valueOf(Color.BLUE), GenericColor.valueOf(Color.YELLOW));
  }

  /**
   * @param color is the {@link GenericColor} to test.
   * @param complement is the expected {@link GenericColor#invert(ColorModel) complement}.
   */
  private void checkInvert(GenericColor color, GenericColor complement) {

    assertEquals(complement, color.invert(ColorModel.RGB));
    assertEquals(color, complement.invert(ColorModel.RGB));
  }

  /**
   * Test of {@link GenericColor#lighten(ColorFactor)}
   */
  @Test
  public void testLighten() {

    // given
    GenericColor grey = GenericColor.valueOf(new Red(0.5), new Green(0.5), new Blue(0.5), Alpha.OPAQUE);
    GenericColor black = GenericColor.valueOf(Color.BLACK);
    GenericColor white = GenericColor.valueOf(Color.WHITE);
    GenericColor blueViolet = GenericColor.valueOf(Color.BLUE_VIOLET);
    ColorFactor half = new ColorFactor(0.5);
    ColorFactor full = new ColorFactor(1.0);
    ColorFactor none = new ColorFactor(0.0);

    // then
    // lighten black
    assertEquals(grey, black.lighten(half));
    assertEquals(white, black.lighten(full));
    assertEquals(black, black.lighten(none));

    // lighten blue violet
    double r = 0x8A / 255.0;
    double g = 0x2B / 255.0;
    double b = 0xE2 / 255.0;
    assertEquals(GenericColor.valueOf(new Red(r + (1 - r) * 0.5), new Green(g + (1 - g) * 0.5), new Blue(b + (1 - b)
        * 0.5), Alpha.OPAQUE), blueViolet.lighten(half));
    assertEquals(white, blueViolet.lighten(full));
    assertEquals(blueViolet, blueViolet.lighten(none));
  }

  /**
   * Test of {@link GenericColor#darken(ColorFactor)}
   */
  @Test
  public void testDarken() {

    // given
    GenericColor grey = GenericColor.valueOf(new Red(0.5), new Green(0.5), new Blue(0.5), Alpha.OPAQUE);
    GenericColor black = GenericColor.valueOf(Color.BLACK);
    GenericColor white = GenericColor.valueOf(Color.WHITE);
    GenericColor blueViolet = GenericColor.valueOf(Color.BLUE_VIOLET);
    ColorFactor half = new ColorFactor(0.5);
    ColorFactor full = new ColorFactor(1.0);
    ColorFactor none = new ColorFactor(0.0);

    // then
    // lighten black
    assertEquals(grey, white.darken(half));
    assertEquals(black, white.darken(full));
    assertEquals(white, white.lighten(none));

    // lighten blue violet
    double r = 0x8A / 255.0;
    double g = 0x2B / 255.0;
    double b = 0xE2 / 255.0;
    assertEquals(GenericColor.valueOf(new Red(r * 0.5), new Green(g * 0.5), new Blue(b * 0.5), Alpha.OPAQUE),
        blueViolet.darken(half));
    assertEquals(black, blueViolet.darken(full));
    assertEquals(blueViolet, blueViolet.darken(none));
  }

  /**
   * Tests {@link GenericColor#equals(Object)}.
   */
  @Test
  public void testEquals() {

    // given
    GenericColor color = GenericColor.valueOf(Color.NAVY);

    // then
    assertTrue(color.equals(color));
    assertFalse(color.equals(null));
    assertFalse(color.equals("navy"));
    Red red = color.getRed();
    Green green = color.getGreen();
    Blue blue = color.getBlue();
    Alpha alpha = new Alpha(0.5);
    assertFalse(color.equals(GenericColor.valueOf(red, green, blue, alpha)));
    assertFalse(color.equals(GenericColor.valueOf(red, green, new Blue(1.0), color.getAlpha())));
    assertFalse(color.equals(GenericColor.valueOf(red, new Green(1.0), blue, color.getAlpha())));
    assertFalse(color.equals(GenericColor.valueOf(new Red(1.0), green, blue, color.getAlpha())));
  }

  /**
   * Tests {@link GenericColor#valueOf(String)} with various invalid {@link String}s.
   */
  @Test
  public void testInvalidStrings() {

    NlsNullPointerException npe = checkInvalidString(null, NlsNullPointerException.class);
    assertEquals("color", npe.getNlsMessage().getArgument(NlsObject.KEY_OBJECT));
    checkInvalidString("", NlsParseException.class);
    checkInvalidString("rgba", NlsParseException.class);
    checkInvalidString("rgb(x,y,z)", NumberFormatException.class);
  }

  /**
   * Tests {@link GenericColor#getSegment(ColorSegmentType)} with an invalid segment (<code>null</code>).
   */
  @Test
  public void testInvalidSegment() {

    try {
      GenericColor.valueOf(Color.WHITE).getSegment(null);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      ExceptionHelper.assertCause(e, NlsNullPointerException.class);
    }
  }

  /**
   * @param <T> is the generic type of <code>expectedException</code>.
   * @param colorString is the invalid {@link GenericColor#valueOf(String) color string}.
   * @param expectedException the expected exception.
   * @return the expected exception.
   */
  private <T extends Throwable> T checkInvalidString(String colorString, Class<T> expectedException) {

    try {
      GenericColor.valueOf(colorString);
      ExceptionHelper.failExceptionExpected();
      return null; // will never happen...
    } catch (RuntimeException e) {
      return ExceptionHelper.assertCause(e, expectedException);
    }
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
