/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.state.UIReadSize;

/**
 * This class is a container for the layout constraints of a
 * {@link net.sf.mmm.ui.toolkit.api.UIComponent} inside a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UISlicePanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LayoutConstraints {

  /**
   * This inner class is a dummy that does not override the size.
   */
  private static class DummySize implements UIReadSize {

    /**
     * {@inheritDoc}
     */
    public int getHeight() {

      return 0;
    }

    /**
     * {@inheritDoc}
     */
    public int getWidth() {

      return 0;
    }

  }

  /** the dummy for no size override */
  private static final UIReadSize NO_SIZE = new DummySize();

  /**
   * The default layout constraints:<br>
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#BOTH}</li>
   * <li>{@link #weight} = <code>1.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints DEFAULT = new LayoutConstraints(Alignment.CENTER,
      Filling.BOTH, 1.0, Insets.NO_SPACE);

  /**
   * The default layout constraints:<br>
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#BOTH}</li>
   * <li>{@link #weight} = <code>1.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints SCALED = new LayoutConstraints(Alignment.CENTER,
      Filling.BOTH, 1.0, Insets.NO_SPACE);

  /**
   * The default layout constraints:<br>
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#HORIZONTAL}</li>
   * <li>{@link #weight} = <code>1.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints SCALED_HORIZONTAL = new LayoutConstraints(Alignment.CENTER,
      Filling.HORIZONTAL, 1.0, Insets.NO_SPACE);

  /**
   * The default layout constraints:<br>
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#NONE}</li>
   * <li>{@link #weight} = <code>1.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints SCALED_NO_FILL = new LayoutConstraints(Alignment.CENTER,
      Filling.NONE, 1.0, Insets.NO_SPACE);

  /**
   * Layout constraints with:
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#HORIZONTAL}</li>
   * <li>{@link #weight} = <code>0.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints FIXED_HORIZONTAL = new LayoutConstraints(Alignment.CENTER,
      Filling.HORIZONTAL, 0.0, Insets.NO_SPACE);

  /**
   * Layout constraints with:
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#HORIZONTAL}</li>
   * <li>{@link #weight} = <code>0.0</code></li>
   * <li>{@link #insets} = {@link Insets#SMALL_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints FIXED_HORIZONTAL_INSETS = new LayoutConstraints(
      Alignment.CENTER, Filling.HORIZONTAL, 0.0, Insets.SMALL_SPACE);

  /**
   * Layout constraints with:
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#VERTICAL}</li>
   * <li>{@link #weight} = <code>0.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints FIXED_VERTICAL = new LayoutConstraints(Alignment.CENTER,
      Filling.VERTICAL, 0.0, Insets.NO_SPACE);

  /**
   * Layout constraints with:
   * <ul>
   * <li>{@link #alignment} = {@link Alignment#CENTER}</li>
   * <li>{@link #filling} = {@link Filling#VERTICAL}</li>
   * <li>{@link #weight} = <code>0.0</code></li>
   * <li>{@link #insets} = {@link Insets#NO_SPACE}</li>
   * </ul>
   */
  public static final LayoutConstraints FIXED_NONE = new LayoutConstraints(Alignment.CENTER,
      Filling.NONE, 0.0, Insets.NO_SPACE);

  /**
   * The alignment specifies the placement of the component inside its space.
   * This value is ignored if the weight is greater than zero (>0).
   */
  public final Alignment alignment;

  /**
   * The filling specifies if the component's size is extended horizontally
   * and/or vertically if its area is bigger than the component's size.
   */
  public final Filling filling;

  /**
   * The weight is a value in the range of [0,1] (0<=weight<=1). Typical
   * values are -1, 0 and 1.<br>
   * If 0, the component will be sized as preferred by the component's
   * {@link net.sf.mmm.ui.toolkit.api.state.UIReadSize size}.<br>
   * If the weight is positive, the component will be scaled according to the
   * avaliable size and in proportion to the weight. The width (if
   * {@link Orientation#HORIZONTAL horizontal} layout) or height (if
   * {@link Orientation#VERTICAL vertical} layout) proportional to the given
   * weight (absolute) and the size available for this panel. <br>
   * If the weight is negative, the behaviour is like <code>-weight</code>,
   * but only the space for the component is sized and the component itself is
   * sized as its {@link net.sf.mmm.ui.toolkit.api.state.UIReadSize size}. The
   * alignment is used to place the component inside the sized space.
   */
  public final double weight;

  /**
   * The insets define the spacing of the components border.
   */
  public final Insets insets;

  /**
   * The size used to override default size. If
   * {@link UIReadSize#getWidth() width} or
   * {@link UIReadSize#getHeight() height} is greater than <code>0</code>, it
   * overrides the size-value of the associated
   * {@link net.sf.mmm.ui.toolkit.api.UIComponent component}.
   */
  public final UIReadSize size;

  /**
   * The constructor.<br>
   * Using {@link Alignment#CENTER}, {@link Filling#BOTH} and
   * {@link Insets#NO_SPACE}.
   * 
   * @param w is the {@link #weight}
   */
  public LayoutConstraints(double w) {

    this(Alignment.CENTER, Filling.BOTH, w, Insets.NO_SPACE);
  }

  /**
   * The constructor.<br>
   * Using {@link Insets#NO_SPACE}, {@link Filling#NONE} and a weight of
   * <code>0</code>.
   * 
   * @param align is the {@link #alignment}
   */
  public LayoutConstraints(Alignment align) {

    this(align, Filling.NONE, 0, Insets.NO_SPACE);
  }

  /**
   * The constructor. Using {@link Insets#NO_SPACE} and {@link Filling#BOTH}.
   * 
   * @param align is the {@link #alignment}
   * @param w is the {@link #weight}
   */
  public LayoutConstraints(Alignment align, double w) {

    this(align, Filling.BOTH, w, Insets.NO_SPACE);
  }

  /**
   * The constructor. Using {@link Insets#NO_SPACE}.
   * 
   * @param align is the {@link #alignment}
   * @param fill is the {@link #filling}
   */
  public LayoutConstraints(Alignment align, Filling fill) {

    this(align, fill, (fill == Filling.NONE) ? 0 : 1, Insets.NO_SPACE);
  }

  /**
   * The constructor. Using {@link Insets#NO_SPACE}.
   * 
   * @param align is the {@link #alignment}
   * @param fill is the {@link #filling}
   * @param w is the {@link #weight}
   */
  public LayoutConstraints(Alignment align, Filling fill, double w) {

    this(align, fill, w, Insets.NO_SPACE);
  }

  /**
   * The constructor.
   * 
   * @param align is the {@link #alignment}
   * @param fill is the {@link #filling}
   * @param w is the {@link #weight}
   * @param border are the {@link #insets}
   */
  public LayoutConstraints(Alignment align, Filling fill, double w, Insets border) {

    this(align, fill, w, border, NO_SIZE);
  }

  /**
   * The constructor.
   * 
   * @param align is the {@link #alignment}
   * @param fill is the {@link #filling}
   * @param w is the {@link #weight}
   * @param border are the {@link #insets}
   * @param overrideSize is the {@link #size}.
   */
  public LayoutConstraints(Alignment align, Filling fill, double w, Insets border,
      UIReadSize overrideSize) {

    super();
    this.alignment = align;
    this.filling = fill;
    this.weight = w;
    this.insets = border;
    this.size = overrideSize;
  }

}
