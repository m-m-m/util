/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import java.util.Objects;

import net.sf.mmm.util.lang.api.AbstractDatatype;
import net.sf.mmm.util.lang.api.BasicHelper;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a {@link Color} based on {@link Factor factors}. <br>
 * <b>Note:</b><br>
 * Use {@link Color} for simple and efficient representation and transport of color information. However, if precision
 * is required or for transformation between different {@link ColorModel color models} use this class instead. <br>
 * <b>Credits:</b><br>
 * The algorithms for transformation of the color models are mainly taken from
 * <a href="http://en.wikipedia.org/wiki/HSL_and_HSV">HSL and HSV on wikipedia</a>. <br>
 * <b>ATTENTION:</b><br>
 * This implementation does not support color profiles or the Adobe RGB color space.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class GenericColor extends AbstractDatatype {

  private static final long serialVersionUID = 3175467633850341788L;

  private Alpha alpha;

  private Hue hue;

  private Saturation saturationHsb;

  private Saturation saturationHsl;

  private Brightness brightness;

  private Lightness lightness;

  private Chroma chroma;

  private Red red;

  private Blue blue;

  private Green green;

  /**
   * The constructor. Use {@code valueOf} methods to instantiate.
   */
  protected GenericColor() {

    super();
  }

  /**
   * Parses the {@link GenericColor} given as {@link String} representation.
   *
   * @param color is the color as {@link String}.
   * @return the parsed {@link GenericColor}.
   */
  public static GenericColor valueOf(String color) {

    Objects.requireNonNull(color, "color");
    Color namedColor = Color.fromName(color);
    if (namedColor != null) {
      return valueOf(namedColor);
    }
    int length = color.length();
    Throwable cause = null;
    try {
      // "#RRGGBB" / #AARRGGBB
      Color hexColor = Color.parseHexString(color);
      if (hexColor != null) {
        return valueOf(hexColor);
      }
      // "rgb(1,1,1)" = 10
      if (length >= 7) {
        String model = BasicHelper.toUpperCase(color.substring(0, 3));
        ColorModel colorModel = ColorModel.valueOf(model);
        int index = 3;
        boolean hasAlpha = false;
        char c = Character.toLowerCase(color.charAt(index));
        if (c == 'a') {
          hasAlpha = true;
          index++;
          c = color.charAt(index);
        }
        if (c == '(') {
          index++;
          int endIndex = color.indexOf(',', index);
          if (endIndex > 0) {
            String firstSegment = color.substring(index, endIndex).trim();
            index = endIndex + 1;
            endIndex = color.indexOf(',', index);
            if (endIndex > 0) {
              String secondSegment = color.substring(index, endIndex).trim();
              index = endIndex + 1;
              if (hasAlpha) {
                endIndex = color.indexOf(',', index);
              } else {
                endIndex = length - 1;
              }
              if (endIndex > 0) {
                String thirdSegment = color.substring(index, endIndex).trim();
                Alpha alpha;
                if (hasAlpha) {
                  alpha = new Alpha(color.substring(endIndex + 1, length - 1));
                } else {
                  alpha = Alpha.OPAQUE;
                }
                switch (colorModel) {
                  case RGB:
                    return valueOf(new Red(firstSegment), new Green(secondSegment), new Blue(thirdSegment), alpha);
                  case HSL:
                    return valueOf(new Hue(firstSegment), new Saturation(secondSegment), new Lightness(thirdSegment), alpha);
                  case HSV:
                  case HSB:
                    return valueOf(new Hue(firstSegment), new Saturation(secondSegment), new Brightness(thirdSegment), alpha);
                  default :
                    throw new IllegalStateException("" + colorModel);
                }
              }
            }
          }
        }
      }
    } catch (RuntimeException e) {
      cause = e;
    }
    throw new IllegalArgumentException(color, cause);
  }

  /**
   * Converts the given {@link Color} to a {@link GenericColor}.
   *
   * @param color is the discrete RGBA {@link Color}.
   * @return the corresponding {@link GenericColor}.
   */
  public static GenericColor valueOf(Color color) {

    Objects.requireNonNull(color, "color");
    Red red = new Red(color.getRed());
    Green green = new Green(color.getGreen());
    Blue blue = new Blue(color.getBlue());
    Alpha alpha = new Alpha(color.getAlpha());
    return valueOf(red, green, blue, alpha);
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#RGB}.
   *
   * @param red is the {@link Red} part.
   * @param green is the {@link Green} part.
   * @param blue is the {@link Blue} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Red red, Green green, Blue blue, Alpha alpha) {

    Objects.requireNonNull(red, "red");
    Objects.requireNonNull(green, "green");
    Objects.requireNonNull(blue, "blue");
    Objects.requireNonNull(alpha, "alpha");
    GenericColor genericColor = new GenericColor();
    genericColor.red = red;
    genericColor.green = green;
    genericColor.blue = blue;
    genericColor.alpha = alpha;
    // calculate min/max
    double r = red.getValueAsFactor();
    double g = green.getValueAsFactor();
    double b = blue.getValueAsFactor();
    double max = r;
    if (g > max) {
      max = g;
    }
    if (b > max) {
      max = b;
    }
    double min = r;
    if (g < min) {
      min = g;
    }
    if (b < min) {
      min = b;
    }
    double chroma = max - min;
    genericColor.chroma = new Chroma(chroma);

    double hue = calculateHue(r, g, b, max, chroma);
    genericColor.hue = new Hue(hue);
    double s;
    if (max == 0) {
      s = 0;
    } else {
      s = chroma / max;
    }
    genericColor.saturationHsb = new Saturation(s);
    double lightness = (max + min) / 2;
    genericColor.lightness = new Lightness(lightness);
    double saturationHsl = calculateSaturationHsl(chroma, lightness);
    genericColor.saturationHsl = new Saturation(saturationHsl);
    genericColor.brightness = new Brightness(max);
    return genericColor;
  }

  /**
   * Calculate the {@link Saturation} for {@link ColorModel#HSL}.
   *
   * @param chroma is the {@link Chroma} value.
   * @param lightness is the {@link Lightness} value.
   * @return the {@link Saturation}.
   */
  private static double calculateSaturationHsl(double chroma, double lightness) {

    double d = 1 - Math.abs(2 * lightness - 1);
    if (d == 0) {
      return 0;
    }
    return chroma / d;
  }

  /**
   * Calculate the {@link Hue}.
   *
   * @param red is the {@link Red} value.
   * @param green is the {@link Green} value.
   * @param blue is the {@link Blue} value.
   * @param max is the maximum of RGB.
   * @param chroma is the {@link Chroma} value.
   * @return the {@link Saturation}.
   */
  private static double calculateHue(double red, double green, double blue, double max, double chroma) {

    if (chroma == 0) {
      return 0;
    } else {
      double hue;
      if (red == max) {
        hue = (green - blue) / chroma;
      } else if (green == max) {
        hue = (blue - red) / chroma + 2;
      } else {
        hue = (red - green) / chroma + 4;
      }
      hue = hue * 60.0;
      if (hue < 0) {
        hue = hue + Hue.MAX_VALUE;
      }
      return hue;
    }
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#HSB}.
   *
   * @param hue is the {@link Hue} part.
   * @param saturation is the {@link Saturation} part.
   * @param brightness is the {@link Brightness} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Hue hue, Saturation saturation, Brightness brightness, Alpha alpha) {

    Objects.requireNonNull(hue, "hue");
    Objects.requireNonNull(saturation, "saturation");
    Objects.requireNonNull(brightness, "brightness");
    Objects.requireNonNull(alpha, "alpha");
    GenericColor genericColor = new GenericColor();
    genericColor.hue = hue;
    genericColor.saturationHsb = saturation;
    genericColor.brightness = brightness;
    genericColor.alpha = alpha;
    double b = brightness.getValueAsFactor();
    double chroma = b * saturation.getValueAsFactor();
    genericColor.chroma = new Chroma(chroma);
    double min = b - chroma;
    double lightness = (min + b) / 2;
    genericColor.lightness = new Lightness(lightness);
    double saturationHsl = calculateSaturationHsl(chroma, lightness);
    genericColor.saturationHsl = new Saturation(saturationHsl);
    calculateRgb(genericColor, hue, min, chroma);
    return genericColor;
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#HSL}.
   *
   * @param hue is the {@link Hue} part.
   * @param saturation is the {@link Saturation} part.
   * @param lightness is the {@link Lightness} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Hue hue, Saturation saturation, Lightness lightness, Alpha alpha) {

    Objects.requireNonNull(hue, "hue");
    Objects.requireNonNull(saturation, "saturation");
    Objects.requireNonNull(lightness, "lightness");
    Objects.requireNonNull(alpha, "alpha");
    GenericColor genericColor = new GenericColor();
    genericColor.hue = hue;
    genericColor.saturationHsl = saturation;
    genericColor.lightness = lightness;
    genericColor.alpha = alpha;
    double l = lightness.getValueAsFactor();
    double chroma;
    if (l >= 0.5) {
      chroma = saturation.getValueAsFactor() * (2 - 2 * l);
    } else {
      chroma = saturation.getValueAsFactor() * 2 * l;
    }
    double m = l - (chroma / 2);
    double saturationHsb;
    double b = chroma + m;
    genericColor.brightness = new Brightness(b);
    if (l == 0) {
      saturationHsb = 0;
    } else {
      saturationHsb = chroma / b;
    }
    genericColor.saturationHsb = new Saturation(saturationHsb);
    calculateRgb(genericColor, hue, m, chroma);
    return genericColor;
  }

  /**
   * Calculates and the RGB values and sets them in the given {@link GenericColor}.
   *
   * @param genericColor is the {@link GenericColor} to complete.
   * @param hue is the {@link Hue} value.
   * @param min is the minimum {@link Factor} of R/G/B.
   * @param chroma is the {@link Chroma} value.
   */
  private static void calculateRgb(GenericColor genericColor, Hue hue, double min, double chroma) {

    genericColor.chroma = new Chroma(chroma);
    double hueX = hue.getValue().doubleValue() / 60;
    double x = chroma * (1 - Math.abs((hueX % 2) - 1));
    double red, green, blue;
    if (hueX < 1) {
      red = chroma + min;
      green = x + min;
      blue = min;
    } else if (hueX < 2) {
      red = x + min;
      green = chroma + min;
      blue = min;
    } else if (hueX < 3) {
      red = min;
      green = chroma + min;
      blue = x + min;
    } else if (hueX < 4) {
      red = min;
      green = x + min;
      blue = chroma + min;
    } else if (hueX < 5) {
      red = x + min;
      green = min;
      blue = chroma + min;
    } else {
      red = chroma + min;
      green = min;
      blue = x + min;
    }
    genericColor.red = new Red(red);
    genericColor.green = new Green(green);
    genericColor.blue = new Blue(blue);
  }

  /**
   * @return the {@link Alpha alpha value as factor}.
   */
  public Alpha getAlpha() {

    return this.alpha;
  }

  /**
   * @return the {@link Hue}.
   */
  public Hue getHue() {

    return this.hue;
  }

  /**
   * @see ColorSegmentType#SATURATION_HSB
   * @return the {@link Saturation} in {@link ColorModel#HSB}/{@link ColorModel#HSV} color model (hexcone).
   */
  public Saturation getSaturationHsb() {

    return this.saturationHsb;
  }

  /**
   * @see ColorSegmentType#SATURATION_HSL
   * @return the {@link Saturation} in {@link ColorModel#HSL} {@link ColorModel color model} (bi-hexcone).
   */
  public Saturation getSaturationHsl() {

    return this.saturationHsl;
  }

  /**
   * @return the brightness
   */
  public Brightness getBrightness() {

    return this.brightness;
  }

  /**
   * @return the lightness
   */
  public Lightness getLightness() {

    return this.lightness;
  }

  /**
   * @return the chroma
   */
  public Chroma getChroma() {

    return this.chroma;
  }

  /**
   * @return the red
   */
  public Red getRed() {

    return this.red;
  }

  /**
   * @return the blue
   */
  public Blue getBlue() {

    return this.blue;
  }

  /**
   * @return the green
   */
  public Green getGreen() {

    return this.green;
  }

  /**
   * @param type is the {@link ColorSegmentType} identifying the requested {@link Segment}.
   * @return the {@link Segment} of the given {@code type}.
   */
  public AbstractDoubleSegment<?> getSegment(ColorSegmentType type) {

    Objects.requireNonNull(type, "type");
    switch (type) {
      case RED:
        return this.red;
      case GREEN:
        return this.green;
      case BLUE:
        return this.blue;
      case HUE:
        return this.hue;
      case SATURATION_HSB:
        return this.saturationHsb;
      case SATURATION_HSL:
        return this.saturationHsl;
      case BRIGHTNESS:
        return this.brightness;
      case LIGHTNESS:
        return this.lightness;
      case ALPHA:
        return this.alpha;
      default :
        throw new IllegalStateException("" + type);
    }
  }

  /**
   * @return the converted {@link Color} corresponding to this {@link GenericColor}.
   */
  public Color toColor() {

    return new Color(this.red.getValueAsByte(), this.green.getValueAsByte(), this.blue.getValueAsByte(), this.alpha.getValueAsByte());
  }

  /**
   * @param model the {@link ColorModel} indicating the {@link Segment}s to {@link AbstractDoubleSegment#invert()
   *        invert}. Typically {@link ColorModel#RGB} to build the complement of the color.
   * @return the complementary (or inverse) color.
   */
  public GenericColor invert(ColorModel model) {

    switch (model) {
      case RGB:
        return valueOf(this.red.invert(), this.green.invert(), this.blue.invert(), this.alpha);
      case HSL:
        return valueOf(this.hue.invert(), this.saturationHsl.invert(), this.lightness.invert(), this.alpha);
      case HSB:
      case HSV:
        return valueOf(this.hue.invert(), this.saturationHsb.invert(), this.brightness.invert(), this.alpha);
      default :
        throw new IllegalStateException("" + model);
    }
  }

  /**
   * Lightens this color by the given {@code factor}.
   *
   * @param factor is the factor to increase by. E.g. {@code 0.0} will cause no change, while {@code 1.0} will return
   *        {@link Color#WHITE white}.
   * @return a new color lighter by the given {@code factor}.
   */
  public GenericColor lighten(ColorFactor factor) {

    return valueOf(this.red.increase(factor), this.green.increase(factor), this.blue.increase(factor), this.alpha);
  }

  /**
   * Darkens this color by the given {@code factor}.
   *
   * @param factor is the factor to decrease by. E.g. {@code 0.0} will cause no change, while {@code 1.0} will return
   *        {@link Color#BLACK black}.
   * @return a new color darker by the given {@code factor}.
   */
  public GenericColor darken(ColorFactor factor) {

    return valueOf(this.red.decrease(factor), this.green.decrease(factor), this.blue.decrease(factor), this.alpha);
  }

  @Override
  public String toString() {

    return toString(ColorModel.RGB);
  }

  /**
   * @param colorModel is the {@link ColorModel}.
   * @return this color as {@link String} in notation of the given {@link ColorModel} (e.g. "rgba(255, 128, 64, 1.0)"
   *         for {@link ColorModel#RGB}).
   */
  public String toString(ColorModel colorModel) {

    StringBuilder buffer = new StringBuilder(BasicHelper.toLowerCase(colorModel.toString()));
    buffer.append("a(");
    buffer.append(getSegment(colorModel.getFirstSegmentType()));
    buffer.append(',');
    buffer.append(getSegment(colorModel.getSecondSegmentType()));
    buffer.append(',');
    buffer.append(getSegment(colorModel.getThirdSegmentType()));
    buffer.append(',');
    buffer.append(this.alpha);
    buffer.append(')');
    return buffer.toString();
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + this.alpha.hashCode();
    result = prime * result + this.blue.hashCode();
    result = prime * result + this.brightness.hashCode();
    result = prime * result + this.chroma.hashCode();
    result = prime * result + this.green.hashCode();
    result = prime * result + this.hue.hashCode();
    result = prime * result + this.lightness.hashCode();
    result = prime * result + this.red.hashCode();
    result = prime * result + this.saturationHsb.hashCode();
    result = prime * result + this.saturationHsl.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GenericColor other = (GenericColor) obj;
    if (!Objects.equals(this.alpha, other.alpha)) {
      return false;
    }
    if (!Objects.equals(this.red, other.red)) {
      return false;
    }
    if (!Objects.equals(this.green, other.green)) {
      return false;
    }
    if (!Objects.equals(this.blue, other.blue)) {
      return false;
    }
    if (!Objects.equals(this.brightness, other.brightness)) {
      return false;
    }
    if (!Objects.equals(this.chroma, other.chroma)) {
      return false;
    }
    if (!Objects.equals(this.hue, other.hue)) {
      return false;
    }
    if (!Objects.equals(this.lightness, other.lightness)) {
      return false;
    }
    if (!Objects.equals(this.saturationHsb, other.saturationHsb)) {
      return false;
    }
    if (!Objects.equals(this.saturationHsl, other.saturationHsl)) {
      return false;
    }
    return true;
  }

}
