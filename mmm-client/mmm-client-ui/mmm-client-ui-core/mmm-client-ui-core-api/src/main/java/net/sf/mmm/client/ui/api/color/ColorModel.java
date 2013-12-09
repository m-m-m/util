/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.color;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the available {@link ColorModel}s supported by {@link GenericColor}. Please note that a
 * color typically has four {@link Segment segments} where only three of them are specific for the
 * {@link ColorModel} and the fourth is {@link Alpha} (opacity). So for each of these {@link ColorModel}s
 * there is a notation with and without {@link Alpha}. E.g. "rgb(255, 255, 255)" is the same as
 * "rgba(255, 255, 255, 1.0)".
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ColorModel implements SimpleDatatype<String> {

  /**
   * The indicator for the RGB color model, where RGB stands for the components {@link Red}, {@link Green},
   * and {@link Blue}.
   */
  RGB {

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.RED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.GREEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.BLUE;
    }
  },

  /**
   * The indicator for the HSB color model, where HSB stands for the components {@link Hue},
   * {@link Saturation}, and {@link Brightness}.
   */
  HSB {

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSB;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.BRIGHTNESS;
    }
  },

  /**
   * The indicator for the HSL color model, where HSL stands for the components {@link Hue},
   * {@link Saturation}, and {@link Lightness}.
   */
  HSL {

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getFirstSegmentType() {

      return ColorSegmentType.HUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getSecondSegmentType() {

      return ColorSegmentType.SATURATION_HSL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorSegmentType getThirdSegmentType() {

      return ColorSegmentType.LIGHTNESS;
    }
  };

  // /**
  // * The indicator for the CYM color model, where CYM stands for the components cyan, yellow, and magenta.
  // */
  // CYM

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return name();
  }

}
