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
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
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
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param sliderOrientation is the orientation of the slide-bar.
   * @param sliderModel is the list model containing the data. See
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel}.
   */
  public UISlideBarImpl(UIFactorySwt uiFactory, UISwtNode parentObject,
      Orientation sliderOrientation, UIListModel<E> sliderModel) {

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
   * {@inheritDoc}
   */
  @Override
  public SyncSliderAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
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

    return this.orientation;
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setSelection(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
    this.model = newModel;
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    int index = getSelectedIndex();
    if (index >= 0) {
      return this.model.getElement(index);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int index = this.model.getIndexOf(newValue);
    if (index >= 0) {
      setSelectedIndex(index);
    }
  }

}
