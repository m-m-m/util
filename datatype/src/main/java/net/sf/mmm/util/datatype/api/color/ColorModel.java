/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.util.lang.api.EnumType;

/**
 * This enum contains the available {@link ColorModel}s supported by {@link GenericColor}. Please note that a color
 * typically has four {@link Segment segments} where only three of them are specific for the {@link ColorModel} and the
 * fourth is {@link Alpha} (opacity). So for each of these {@link ColorModel}s there is a notation with and without
 * {@link Alpha}. E.g. "rgb(255, 255, 255)" is the same as "rgba(255, 255, 255, 1.0)".
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ColorModel implements EnumType<String> {

  /**
   * The indicator for the RGB color model, where RGB stands for the components {@link Red}, {@link Green}, and
   * {@link Blue}.
   */
  RGB {

    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.RED;
    }

    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.GREEN;
    }

    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.BLUE;
    }
  },

  /**
   * The indicator for the HSB color model, where HSB stands for the components {@link Hue}, {@link Saturation}, and
   * {@link Brightness}.
   */
  HSB {

    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSB;
    }

    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.BRIGHTNESS;
    }
  },

  /**
   * The indicator for the HSV color model. This is the same as {@link #HSB} with the only difference that
   * {@link Brightness} is named <em>value</em>.
   */
  HSV {

    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSB;
    }

    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.BRIGHTNESS;
    }
  },

  /**
   * The indicator for the HSL color model, where HSL stands for the components {@link Hue}, {@link Saturation}, and
   * {@link Lightness}.
   */
  HSL {

    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSL;
    }

    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.LIGHTNESS;
    }
  };

  // /**
  // * The indicator for the CYM color model, where CYM stands for the components cyan, yellow, and magenta.
  // */
  // CYM

  @Override
  public String toString() {

    // make it explicit, actually overriding not necessary.
    return name();
  }

  /**
   * @return the {@link ColorSegmentType} of the first {@link Segment} of this {@link ColorModel}. E.g.
   *         {@link ColorSegmentType#RED} for {@link #RGB}.
   */
  public abstract ColorSegmentType getFirstSegmentType();

  /**
   * @return the {@link ColorSegmentType} of the second {@link Segment} of this {@link ColorModel}. E.g.
   *         {@link ColorSegmentType#SATURATION_HSL} for {@link #HSL}.
   */
  public abstract ColorSegmentType getSecondSegmentType();

  /**
   * @return the {@link ColorSegmentType} of the first {@link Segment} of this {@link ColorModel}. E.g.
   *         {@link ColorSegmentType#BRIGHTNESS} for {@link #HSB}.
   */
  public abstract ColorSegmentType getThirdSegmentType();

  @Override
  public String getValue() {

    return name();
  }

}
