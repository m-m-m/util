/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Enum} contains the available types of the {@link Segment segments} of a {@link Color}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ColorSegmentType {

  /** Type of {@link Segment} for {@link Red}. */
  RED,

  /** Type of {@link Segment} for {@link Green}. */
  GREEN,

  /** Type of {@link Segment} for {@link Blue}. */
  BLUE,

  /** Type of {@link Segment} for {@link Hue}. */
  HUE,

  /** Type of {@link Segment} for {@link Saturation} in {@link ColorModel#HSB}. */
  SATURATION_HSB,

  /** Type of {@link Segment} for {@link Saturation} in {@link ColorModel#HSL}. */
  SATURATION_HSL,

  /** Type of {@link Segment} for {@link Brightness}. */
  BRIGHTNESS,

  /** Type of {@link Segment} for {@link Lightness}. */
  LIGHTNESS,

  /** Type of {@link Segment} for {@link Alpha}. */
  ALPHA

}
