/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JSlider;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.SlideBarModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISlideBar} interface using Swing
 * as the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISlideBarImpl<E> extends AbstractUIWidget implements UISlideBar<E> {

  /** the native swing widget */
  private final JSlider slideBar;

  /** the controller */
  private final SlideBarModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param orientation
   *        is the orientation of the slide-bar.
   * @param model
   *        is the list model containing the data. See
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel}.
   */
  public UISlideBarImpl(UIFactorySwing uiFactory, UINode parentObject, Orientation orientation,
      UIListModel<E> model) {

    super(uiFactory, parentObject);
    int style;
    if (orientation == Orientation.HORIZONTAL) {
      style = JSlider.HORIZONTAL;
    } else {
      style = JSlider.VERTICAL;
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
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    this.slideBar.addChangeListener(createChangeListener());
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.slideBar;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadOrientation#getOrientation()
   */
  public Orientation getOrientation() {

    if (this.slideBar.getOrientation() == JSlider.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {

    this.slideBar.setValue(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.slideBar.getValue();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#getModel()
   */
  public UIListModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    int index = this.modelAdapter.getModel().getIndexOf(newValue);
    if (index != -1) {
      this.slideBar.setValue(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    return this.modelAdapter.getModel().getElement(this.slideBar.getValue());
  }

}
