/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.SlideBarModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncSliderAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISlideBar} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISlideBarImpl<E> extends AbstractUIWidget implements UISlideBar<E> {

  /** the synchron access to the slider */
  private final SyncSliderAccess syncAccess;

  /** the model adapter */
  private final SlideBarModelAdapter modelAdapter;

  /** the orientation of the slider */
  private final Orientation orientation;

  /** the model of the slider */
  private UIListModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param sliderOrientation
   *        is the orientation of the slide-bar.
   * @param sliderModel
   *        is the list model containing the data. See
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel}.
   */
  public UISlideBarImpl(UIFactorySwt uiFactory, UISwtNode parentObject, Orientation sliderOrientation,
      UIListModel<E> sliderModel) {

    super(uiFactory, parentObject);
    this.orientation = sliderOrientation;
    this.model = sliderModel;
    int style = UIFactorySwt.convertOrientation(sliderOrientation);
    this.syncAccess = new SyncSliderAccess(uiFactory, style);
    this.syncAccess.setMaximum(this.model.getElementCount());
    this.modelAdapter = new SlideBarModelAdapter(this.syncAccess);
    this.modelAdapter.setModel(this.model);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public SyncSliderAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#create()
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
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

    return this.orientation;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelection();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setSelection(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#getModel()
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
    this.model = newModel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    int index = getSelectedIndex();
    if (index >= 0) {
      return this.model.getElement(index);
    }
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    int index = this.model.getIndexOf(newValue);
    if (index >= 0) {
      setSelectedIndex(index);
    }
  }

}
