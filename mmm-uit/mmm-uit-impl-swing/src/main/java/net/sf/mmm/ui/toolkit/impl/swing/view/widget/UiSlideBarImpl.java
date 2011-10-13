/* $Id: UISlideBarImpl.java 976 2011-03-02 21:36:59Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.SlideBarModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar} interface using
 * Swing as the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSlideBarImpl<E> extends AbstractUiWidgetSwing<JSlider> implements UiSlideBar<E> {

  /** the controller */
  private final SlideBarModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the orientation of the slide-bar.
   * @param model is the list model containing the data. See
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel}.
   */
  public UiSlideBarImpl(UiFactorySwing uiFactory, Orientation orientation, UiListMvcModel<E> model) {

    super(uiFactory, new JSlider());
    JSlider slider = getDelegate();
    int style;
    if (orientation == Orientation.HORIZONTAL) {
      style = SwingConstants.HORIZONTAL;
    } else {
      style = SwingConstants.VERTICAL;
    }
    slider.setOrientation(style);
    slider.setMinimum(0);
    slider.setMaximum(100);
    slider.setValue(0);
    // slider.setExtent(5);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(5);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSnapToTicks(false);
    this.modelAdapter = new SlideBarModelAdapter<E>(slider, model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().addChangeListener(getAdapter());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    if (getDelegate().getOrientation() == SwingConstants.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    getDelegate().setValue(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return getDelegate().getValue();
  }

  /**
   * {@inheritDoc}
   */
  public UiListMvcModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int index = this.modelAdapter.getModel().getIndexOf(newValue);
    if (index != -1) {
      getDelegate().setValue(index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    return this.modelAdapter.getModel().getElement(getDelegate().getValue());
  }

}
