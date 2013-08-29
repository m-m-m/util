/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a {@link Color}. It is based on RGB values and
 * does not contain an alpha value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Color extends AbstractSimpleDatatype<Integer> {

  /** Number of bits to shift for {@link #getGreen() green} value. */
  private static final int SHIFT_GREEN = 8;

  /** Number of bits to shift for {@link #getRed() red} value. */
  private static final int SHIFT_RED = 16;

  /** The prefix for the {@link #getTitle() title}. */
  private static final String PREFIX = "#";

  /** @see #Color(Integer) */
  private static final Integer MIN_VALUE = Integer.valueOf(0);

  /** @see #Color(Integer) */
  private static final Integer MAX_VALUE = Integer.valueOf(0xFFFFFF);

  /** @see #parseString(String) */
  private static final int MIN_STRING_LENGTH = 1 + PREFIX.length();

  /** @see #parseString(String) */
  private static final int MAX_STRING_LENGTH = 6 + PREFIX.length();

  /** Bit-mask for a single R/G/B value such as {@link #getBlue() blue}. */
  private static final int MASK = 0xFF;

  /** @see #Color(int, int, int) */
  private static final Integer MAX_SEGMENT = Integer.valueOf(MASK);

  /** The maximum of a single R/G/B value as float. */
  private static final float MASK_FLOAT = 255.0f;

  /** The {@link java.util.regex.Pattern} for a valid {@link Color} {@link #getTitle() as string}. */
  private static final String PATTERN = "#[0-9A-F]{1,6}";

  /** UID for serialization. */
  private static final long serialVersionUID = 4307511006443832245L;

  /**
   * The constructor for de-serialization.
   */
  protected Color() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param red is the {@link #getRed() red} value.
   * @param green is the {@link #getGreen() green} value.
   * @param blue is the {@link #getBlue() blue} value.
   */
  public Color(int red, int green, int blue) {

    this((red << SHIFT_RED) + (green << SHIFT_GREEN) + blue);
    verifySegment(red, "red");
    verifySegment(green, "green");
    verifySegment(blue, "blue");
  }

  /**
   * The constructor.
   * 
   * @param value is the actual color value.
   */
  public Color(int value) {

    this(Integer.valueOf(value));
  }

  /**
   * The constructor.
   * 
   * @param value is the actual color value.
   */
  public Color(Integer value) {

    super(value);
    ValueOutOfRangeException.checkRange(value, MIN_VALUE, MAX_VALUE, null);
  }

  /**
   * Verifies that the given segment is in the valid range from {@link #MIN_VALUE} to {@link #MAX_SEGMENT}.
   * 
   * @param segment is the segment to check.
   * @param source is the source of the <code>segment</code>.
   */
  private void verifySegment(int segment, String source) {

    if ((segment < 0) || (segment > MASK)) {
      throw new ValueOutOfRangeException(Integer.valueOf(segment), MIN_VALUE, MAX_SEGMENT, source);
    }
  }

  /**
   * The constructor.
   * 
   * @param title is the {@link #getTitle() string representation} of the {@link Color} to create.
   */
  public Color(String title) {

    super(parseString(title));
  }

  /**
   * @see #Color(String)
   * 
   * @param title is the {@link #getTitle() string representation} of the {@link Color} to create.
   * @return the parsed {@link #getValue() value}.
   */
  private static Integer parseString(String title) {

    NlsNullPointerException.checkNotNull("color", title);
    if ((title.length() < MIN_STRING_LENGTH) || (title.length() > MAX_STRING_LENGTH) || (!title.startsWith(PREFIX))) {
      throw new NlsParseException(title, PATTERN, Color.class);
    }
    String hexColors = title.substring(PREFIX.length());
    Integer result;
    try {
      result = Integer.valueOf(hexColors, 16);
    } catch (NumberFormatException e) {
      throw new NlsParseException(e, title, PATTERN, Color.class);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    StringBuilder buffer = new StringBuilder(MAX_STRING_LENGTH);
    buffer.append(PREFIX);
    String hexString = Integer.toHexString(getValue().intValue());
    int delta = 6 - hexString.length();
    for (int i = delta; i > 0; i--) {
      buffer.append('0');
    }
    buffer.append(hexString);
    return buffer.toString();
  }

  /**
   * @return the red part of the {@link #getValue() color value} in the range from <code>0-255</code>.
   */
  public int getRed() {

    return getValue().intValue() >> SHIFT_RED;
  }

  /**
   * @return the green part of the {@link #getValue() color value} in the range from <code>0-255</code>.
   */
  public int getGreen() {

    return (getValue().intValue() >> SHIFT_GREEN) & MASK;
  }

  /**
   * @return the blue part of the {@link #getValue() color value} in the range from <code>0-255</code>.
   */
  public int getBlue() {

    return getValue().intValue() & MASK;
  }

  /**
   * @return the brightness part of the {@link #getValue() color value} in HSB/HSV scheme.
   */
  public float getBrightness() {

    int r = getRed();
    int g = getGreen();
    int b = getBlue();
    int max = r;
    if (g > max) {
      max = g;
    }
    if (b > max) {
      max = b;
    }
    return max / MASK_FLOAT;
  }

  /**
   * @return the saturation part of the {@link #getValue() color value} in HSB/HSV scheme.
   */
  public float getSaturation() {

    if (getValue().intValue() == 0) {
      return 0;
    }
    int r = getRed();
    int g = getGreen();
    int b = getBlue();
    int max = r;
    int min = r;
    if (g > max) {
      max = g;
    }
    if (g < min) {
      min = g;
    }
    if (b > max) {
      max = b;
    }
    if (b < min) {
      min = b;
    }
    float delta = max - min;
    return delta / max;
  }

  /**
   * @return the hue part of the {@link #getValue() color value} in HSB/HSV scheme.
   */
  public float getHue() {

    if (getValue().intValue() == 0) {
      return 0;
    }
    int r = getRed();
    int g = getGreen();
    int b = getBlue();
    int max = r;
    int min = r;
    if (g > max) {
      max = g;
    }
    if (g < min) {
      min = g;
    }
    if (b > max) {
      max = b;
    }
    if (b < min) {
      min = b;
    }
    float delta = max - min;
    if (delta == 0) {
      return 0;
    }
    float redc = (max - r) / delta;
    float greenc = (max - g) / delta;
    float bluec = (max - b) / delta;
    float hue;
    if (r == max) {
      hue = bluec - greenc;
    } else if (g == max) {
      hue = 2.0f + redc - bluec;
    } else {
      hue = 4.0f + greenc - redc;
    }
    hue = hue / 6.0f;
    if (hue < 0) {
      hue = hue + 1.0f;
    }
    return hue;
  }

  /**
   * Creates a new {@link Color} from the given HSB/HSV values.
   * 
   * @param hue is the {@link #getHue() hue} value.
   * @param saturation is the {@link #getSaturation() saturation} value.
   * @param brightness is the {@link #getBrightness() brightness} value.
   * @return the new {@link Color}.
   */
  public static Color fromHsb(float hue, float saturation, float brightness) {

    return new Color(hsbToRgb(hue, saturation, brightness));
  }

  /**
   * Converts the given HSB/HSV values to a combined RGB value.
   * 
   * @param hue is the {@link #getHue() hue} value.
   * @param saturation is the {@link #getSaturation() saturation} value.
   * @param brightness is the {@link #getBrightness() brightness} value.
   * @return the converted {@link #getValue() RGB value}.
   */
  public static int hsbToRgb(float hue, float saturation, float brightness) {

    float rf;
    float gf;
    float bf;
    if (saturation == 0) {
      rf = brightness;
      gf = brightness;
      bf = brightness;
    } else {
      float h = (hue - (float) Math.floor(hue)) * 6;
      float f = h - (float) Math.floor(h);
      float t = brightness * (1 - (saturation * (1 - f)));
      float p = brightness * (1 - saturation);
      float q = brightness * (1 - saturation * f);
      switch ((int) h) {
        case 0:
          rf = brightness;
          gf = t;
          bf = p;
          break;
        case 1:
          rf = q;
          gf = brightness;
          bf = p;
          break;
        case 2:
          rf = p;
          gf = brightness;
          bf = t;
          break;
        case 3:
          rf = p;
          gf = q;
          bf = brightness;
          break;
        case 4:
          rf = t;
          gf = p;
          bf = brightness;
          break;
        case 5:
          rf = brightness;
          gf = p;
          bf = q;
          break;
        default :
          throw new IllegalCaseException("h=" + h);
      }
    }
    int red = (int) (rf * MASK_FLOAT + 0.5f);
    int green = (int) (gf * MASK_FLOAT + 0.5f);
    int blue = (int) (bf * MASK_FLOAT + 0.5f);
    return (red << SHIFT_RED) | (green << SHIFT_GREEN) | blue;
  }

}
