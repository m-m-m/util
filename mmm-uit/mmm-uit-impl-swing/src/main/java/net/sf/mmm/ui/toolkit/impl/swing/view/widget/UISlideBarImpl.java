/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
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
public class UISlideBarImpl<E> extends AbstractUiWidget implements UiSlideBar<E> {

  /** the native swing widget */
  private final JSlider slideBar;

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
  public UISlideBarImpl(UIFactorySwing uiFactory, Orientation orientation, UiListMvcModel<E> model) {

    super(uiFactory);
    int style;
    if (orientation == Orientation.HORIZONTAL) {
      style = SwingConstants.HORIZONTAL;
    } else {
      style = SwingConstants.VERTICAL;
    }
    this.slideBar = new JSlider(style, 0, 100, 0);
    // this.slideBar.setExtent(5);
    this.slideBar.setMajorTickSpacing(10);
    this.slideBar.setMinorTickSpacing(5);
    this.slideBar.setPaintTicks(true);
    this.slideBar.setPaintLabels(true);
    this.slideBar.setSnapToTicks(false);
    this.modelAdapter = new SlideBarModelAdapter<E>(this.slideBar, model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.slideBar.addChangeListener(createChangeListener());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.slideBar;
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

    if (this.slideBar.getOrientation() == SwingConstants.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.slideBar.setValue(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.slideBar.getValue();
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
      this.slideBar.setValue(index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    return this.modelAdapter.getModel().getElement(this.slideBar.getValue());
  }

}
