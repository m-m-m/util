/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.lang.api.GwtHelper;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a {@link Color}. It is based on values for {@link #getRed()
 * red}, {@link #getGreen() green}, {@link #getBlue()}, and {@link #getAlpha() alpha}. Internally these values are
 * encoded in a single {@link #getValue() ARGB value}. This class is similar to AWT {@link java.awt.Color} but GWT
 * compatible (AWT color is linked with AWT, see e.g.
 * {@link java.awt.Color#createContext(java.awt.image.ColorModel, java.awt.Rectangle, java.awt.geom.Rectangle2D, java.awt.geom.AffineTransform, java.awt.RenderingHints)
 * createContext}) and especially compatible to CSS. <br>
 * For transformations and other {@link ColorModel}s use {@link GenericColor}. <br>
 * <b>ATTENTION:</b><br>
 * Unfortunately the {@link #getAlpha() alpha} value has NOT been designed as a transparency value (inverse logic so
 * {@code 0} means no transparency or in other words a regular color). Then if encoding in A/R/G/B the hexadecimal RGB
 * notation would be compatible with transparency because #2F4F8F would be the same as #002F4F8F. Unfortunately pain is
 * on our side and we have to keep with existing standards. Therefore #2F4F8F is actually the same as #FF2F4F8F. To
 * avoid confusion and to be compatible with CSS, {@link #toString()} will print the hexadecimal RGB form (e.g. #2F4F8F)
 * in case {@link #getAlpha() alpha} has its maximum value (255/FF). Otherwise the CSS RGBA notation is used (e.g.
 * rgba(47,79,143,0.5) instead of #7F2F4F8F).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Color extends AbstractSimpleDatatype<Integer> {

  private static final long serialVersionUID = 4307511006443832245L;

  /** The {@link Integer#parseInt(String, int) radix} for hexadecimal notation. */
  private static final int RADIX_HEX = 16;

  /** Number of bits to shift for {@link #getBlue() blue} value. */
  private static final int SHIFT_BLUE = 0;

  /** Number of bits to shift for {@link #getGreen() green} value. */
  private static final int SHIFT_GREEN = 8;

  /** Number of bits to shift for {@link #getRed() red} value. */
  private static final int SHIFT_RED = 16;

  /** Number of bits to shift for {@link #getAlpha() alpha} value. */
  private static final int SHIFT_ALPHA = 24;

  /** The prefix for the {@link #toString() title}. */
  public static final String HEX_PREFIX = "#";

  /** The prefix for the rgba(r,g,b,a) syntax. */
  private static final String RGBA_PREFIX = "rgba(";

  /** The suffix for the rgba(r,g,b) syntax. */
  private static final String RGBA_SUFFIX = ")";

  /** The separator for the the segments (e.g. in rgb(r,g,b) or rgba(r,g,b,a) syntax). */
  private static final char SEPARATOR = ',';

  private static final Integer MIN_VALUE = Integer.valueOf(0);

  /** The length of an RGB value in hexadecimal notation (#RRGGBB). */
  private static final int LENGTH_HEX_RGB = 7;

  /** The length of an RGBA value in hexadecimal notation (#AARRGGBB). */
  private static final int LENGTH_HEX_RGBA = 9;

  /** Bit-mask for a single R/G/B/A value such as {@link #getBlue() blue}. */
  static final int MASK = 0xFF;

  /** Bit-mask for a the {@link #getAlpha() alpha} value in {@link #getValue() combined value}. */
  private static final int MASK_ALPHA = 0xFF000000;

  /** @see #Color(int, int, int) */
  private static final Integer MAX_SEGMENT = Integer.valueOf(MASK);

  /** The {@link java.util.regex.Pattern} for a valid {@link Color} {@link #toString() as string}. */
  private static final String PATTERN = "#RRGGBB|rgba(r,g,b,a)|hsla(h,s,l,a)";

  /** The {@link Color} transparent (actually not really a color). */
  public static final Color TRANSPARENT = new Color(0);

  /** The {@link Color} black. */
  public static final Color BLACK = new Color(0x0FF000000);

  /** The {@link Color} white. */
  public static final Color WHITE = new Color(0x0FFFFFFFF);

  /** The {@link Color} red. */
  public static final Color RED = new Color(0x0FFFF0000);

  /** The {@link Color} green (lime). */
  public static final Color GREEN = new Color(0x0FF00FF00);

  /** The {@link Color} CSS green (a darker green while real green is called lime in CSS). */
  public static final Color CSS_GREEN = new Color(0x0FF008000);

  /** The {@link Color} blue. */
  public static final Color BLUE = new Color(0x0FF0000FF);

  /** The {@link Color} magenta. */
  public static final Color MAGENTA = new Color(0x0FFFF00FF);

  /** The {@link Color} cyan. */
  public static final Color CYAN = new Color(0x0FF00FFFF);

  /** The {@link Color} yellow. */
  public static final Color YELLOW = new Color(0x0FFFFFF00);

  /** The {@link Color} grey. */
  public static final Color GREY = new Color(0x0FF808080);

  /** The {@link Color} silver. */
  public static final Color SILVER = new Color(0x0FFC0C0C0);

  /** The {@link Color} maroon. */
  public static final Color MAROON = new Color(0x0FF800000);

  /** The {@link Color} navy. */
  public static final Color NAVY = new Color(0x0FF000080);

  /** The {@link Color} olive. */
  public static final Color OLIVE = new Color(0x0FF808000);

  /** The {@link Color} orange. */
  public static final Color ORANGE = new Color(0x0FFFFA500);

  /** The {@link Color} purple. */
  public static final Color PURPLE = new Color(0x0FF800080);

  /** The {@link Color} teal. */
  public static final Color TEAL = new Color(0x0FF008080);

  /** The {@link Color} BlueViolet. */
  public static final Color BLUE_VIOLET = new Color(0x0FF8A2BE2);

  /** The {@link Color} Brown. */
  public static final Color BROWN = new Color(0x0FFA52A2A);

  /** Maps CSS names to {@link Color}s. */
  private static final Map<String, Color> NAME_TO_COLOR_MAP = new HashMap<>();

  static {
    NAME_TO_COLOR_MAP.put("TRANSPARENT", TRANSPARENT);
    NAME_TO_COLOR_MAP.put("BLACK", BLACK);
    NAME_TO_COLOR_MAP.put("WHITE", WHITE);
    NAME_TO_COLOR_MAP.put("RED", RED);
    NAME_TO_COLOR_MAP.put("GREEN", CSS_GREEN);
    NAME_TO_COLOR_MAP.put("LIME", GREEN);
    NAME_TO_COLOR_MAP.put("BLUE", BLUE);
    NAME_TO_COLOR_MAP.put("MAGENTA", MAGENTA);
    NAME_TO_COLOR_MAP.put("FUCHSIA", MAGENTA);
    NAME_TO_COLOR_MAP.put("CYAN", CYAN);
    NAME_TO_COLOR_MAP.put("AQUA", CYAN);
    NAME_TO_COLOR_MAP.put("YELLOW", YELLOW);
    NAME_TO_COLOR_MAP.put("GREY", GREY);
    NAME_TO_COLOR_MAP.put("GRAY", GREY);
    NAME_TO_COLOR_MAP.put("MAROON", MAROON);
    NAME_TO_COLOR_MAP.put("NAVY", NAVY);
    NAME_TO_COLOR_MAP.put("OLIVE", OLIVE);
    NAME_TO_COLOR_MAP.put("ORANGE", ORANGE);
    NAME_TO_COLOR_MAP.put("PURPLE", PURPLE);
    NAME_TO_COLOR_MAP.put("SILVER", SILVER);
    NAME_TO_COLOR_MAP.put("BLUEVIOLET", BLUE_VIOLET);
  }

  /**
   * The constructor for de-serialization in GWT.
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

    this(red, green, blue, MASK);
  }

  /**
   * The constructor.
   *
   * @param red is the {@link #getRed() red} value.
   * @param green is the {@link #getGreen() green} value.
   * @param blue is the {@link #getBlue() blue} value.
   * @param alpha is the {@link #getAlpha() alpha} value.
   */
  public Color(int red, int green, int blue, int alpha) {

    this(createAndVerifyRgba(red, green, blue, alpha));
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
   * @param value is the actual {@link #getValue() color value} .
   */
  public Color(Integer value) {

    super(value);
  }

  /**
   * Parses the {@link Color} given as {@link String} representation.
   *
   * @param color is the {@link Color} given as {@link String} representation.
   * @return the parsed {@link Color}.
   * @throws NlsParseException if the syntax is invalid.
   */
  public static Color valueOf(String color) throws NlsParseException {

    NlsNullPointerException.checkNotNull("color", color);
    Throwable cause = null;
    try {
      Color hexColor = parseHexString(color);
      if (hexColor != null) {
        return hexColor;
      } else {
        String upperColor = GwtHelper.toUpperCase(color);
        Color namedColor = NAME_TO_COLOR_MAP.get(upperColor);
        if (namedColor != null) {
          return namedColor;
        } else if (upperColor.startsWith(ColorModel.RGB.toString())) {
          Color rgbColor = parseRgb(upperColor);
          if (rgbColor != null) {
            return rgbColor;
          }
        } else {
          return GenericColor.valueOf(color).toColor();
        }
      }
    } catch (NumberFormatException e) {
      cause = e;
    }
    throw new NlsParseException(cause, color, PATTERN, Color.class);
  }

  /**
   * @param uppercaseColor is the {@link Color} in rgb(a) notation in {@link String#toUpperCase(java.util.Locale) upper
   *        case}.
   * @return the parsed {@link Color} or {@code null} if NOT valid.
   */
  private static Color parseRgb(String uppercaseColor) {

    int length = uppercaseColor.length();
    if (length > 10) {
      int index = 3; // RGB.length() == 3
      char c = uppercaseColor.charAt(index);
      boolean hasAlpha = false;
      if (c == 'A') {
        hasAlpha = true;
        index++;
        c = uppercaseColor.charAt(index);
      }
      if (c == '(') {
        index++;
        int endIndex = uppercaseColor.indexOf(',', index);
        if (endIndex > 0) {
          String redString = uppercaseColor.substring(index, endIndex).trim();
          int red = Integer.parseInt(redString);
          index = endIndex + 1;
          endIndex = uppercaseColor.indexOf(',', index);
          if (endIndex > 0) {
            String greenString = uppercaseColor.substring(index, endIndex).trim();
            int green = Integer.parseInt(greenString);
            index = endIndex + 1;
            if (hasAlpha) {
              endIndex = uppercaseColor.indexOf(',', index);
            } else {
              endIndex = length - 1;
            }
            if (endIndex > 0) {
              String blueString = uppercaseColor.substring(index, endIndex).trim();
              int blue = Integer.parseInt(blueString);
              Alpha alpha;
              if (hasAlpha) {
                alpha = new Alpha(uppercaseColor.substring(endIndex + 1, length - 1));
              } else {
                alpha = new Alpha(1.0);
              }
              int value = alpha.getValueAsByte();
              return new Color(createRgba(red, green, blue, value));
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * @param hexColor the {@link Color} given as hexadecimal {@link String} pattern.
   * @return the parsed {@link Color} or {@code null} if not in HEX notation.
   */
  static Color parseHexString(String hexColor) {

    if (hexColor.startsWith(HEX_PREFIX)) {
      int length = hexColor.length();
      if ((length == LENGTH_HEX_RGB) || (length == LENGTH_HEX_RGBA)) {
        String hex = hexColor.substring(HEX_PREFIX.length());
        int rgba = (int) Long.parseLong(hex, RADIX_HEX);
        if (length == LENGTH_HEX_RGB) {
          // add alpha if omitted...
          rgba = rgba | MASK_ALPHA;
        }
        return new Color(Integer.valueOf(rgba));
      }
    }
    return null;
  }

  /**
   * @param red - see {@link #getRed()}.
   * @param green - see {@link #getGreen()}.
   * @param blue - see {@link #getBlue()}.
   * @param alpha - see {@link #getAlpha()}.
   * @return the combined RGB value.
   */
  private static int createAndVerifyRgba(int red, int green, int blue, int alpha) {

    verifySegment(red, ColorSegmentType.RED);
    verifySegment(green, ColorSegmentType.GREEN);
    verifySegment(blue, ColorSegmentType.BLUE);
    verifySegment(alpha, ColorSegmentType.ALPHA);
    return createRgba(red, green, blue, alpha);
  }

  /**
   * @param red - see {@link #getRed()}.
   * @param green - see {@link #getGreen()}.
   * @param blue - see {@link #getBlue()}.
   * @param alpha - see {@link #getAlpha()}.
   * @return the combined RGB value.
   */
  private static int createRgba(int red, int green, int blue, int alpha) {

    return (red << SHIFT_RED) + (green << SHIFT_GREEN) + (blue << SHIFT_BLUE) + (alpha << SHIFT_ALPHA);
  }

  /**
   * Verifies that the given segment is in the valid range from {@link #MIN_VALUE} to {@link #MAX_SEGMENT}.
   *
   * @param segment is the segment to check.
   * @param type is the {@link ColorSegmentType type} of the {@code segment}.
   */
  private static void verifySegment(int segment, ColorSegmentType type) {

    if ((segment < 0) || (segment > MASK)) {
      throw new ValueOutOfRangeException(Integer.valueOf(segment), (Number) MIN_VALUE, MAX_SEGMENT, type);
    }
  }

  /**
   * @param name is the name of the {@link Color} (e.g. as defined in CSS). Case is ignored. Examples are "white",
   *        "maroon", "orange", "transparent", etc.
   * @return the {@link Color} for the given {@code name} or {@code null} if no such {@link Color} is defined.
   */
  public static Color fromName(String name) {

    return NAME_TO_COLOR_MAP.get(GwtHelper.toUpperCase(name));
  }

  /**
   * @return the {@link Alpha} part of the {@link #getValue() color value} as {@link Alpha#getValueAsByte() byte value}.
   */
  public int getAlpha() {

    return (getValue().intValue() >> SHIFT_ALPHA) & MASK;
  }

  /**
   * @return the {@link Red} part of the {@link #getValue() color value} as {@link Alpha#getValueAsByte() byte value}.
   */
  public int getRed() {

    return (getValue().intValue() >> SHIFT_RED) & MASK;
  }

  /**
   * @return the {@link Green} part of the {@link #getValue() color value} as {@link Alpha#getValueAsByte() byte value}.
   */
  public int getGreen() {

    return (getValue().intValue() >> SHIFT_GREEN) & MASK;
  }

  /**
   * @return the {@link Blue} part of the {@link #getValue() color value} as {@link Alpha#getValueAsByte() byte value}.
   */
  public int getBlue() {

    return (getValue().intValue() >> SHIFT_BLUE) & MASK;
  }

  @Override
  public String toString() {

    int alpha = getAlpha();
    StringBuilder buffer;
    if (alpha == MASK) {
      buffer = new StringBuilder(LENGTH_HEX_RGB);
      buffer.append(HEX_PREFIX);
      int rgba = getValue().intValue();
      int rgb = rgba & ~MASK_ALPHA;
      String hexString = Integer.toHexString(rgb);
      int delta = 6 - hexString.length();
      for (int i = delta; i > 0; i--) {
        buffer.append('0');
      }
      buffer.append(GwtHelper.toUpperCase(hexString));
    } else {
      buffer = new StringBuilder(RGBA_PREFIX);
      buffer.append(getRed());
      buffer.append(SEPARATOR);
      buffer.append(getGreen());
      buffer.append(SEPARATOR);
      buffer.append(getBlue());
      buffer.append(SEPARATOR);
      buffer.append(new Alpha(alpha).toStringAsFactor());
      buffer.append(RGBA_SUFFIX);
    }
    return buffer.toString();
  }

}
