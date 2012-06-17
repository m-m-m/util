/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.model.SlideBarModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncSliderAccess;
import net.sf.mmm.util.lang.api.Orientation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Slider;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar} interface using SWT
 * as the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISlideBarImpl<E> extends AbstractUiWidgetSwt<Slider> implements UiSlideBar<E> {

  /** @see #getAdapter() */
  private final SyncSliderAccess adapter;

  /** the model adapter */
  private final SlideBarModelAdapter modelAdapter;

  /** the orientation of the slider */
  private final Orientation orientation;

  /** the model of the slider */
  private UiListMvcModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param sliderOrientation is the orientation of the slide-bar.
   * @param sliderModel is the list model containing the data. See
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel}.
   */
  public UISlideBarImpl(UiFactorySwt uiFactory, Orientation sliderOrientation,
      UiListMvcModel<E> sliderModel) {

    super(uiFactory);
    this.orientation = sliderOrientation;
    this.model = sliderModel;
    int style = UiFactorySwt.convertOrientation(sliderOrientation);
    this.adapter = new SyncSliderAccess(uiFactory, this, style);
    this.adapter.setMaximum(this.model.getElementCount());
    this.modelAdapter = new SlideBarModelAdapter(this.adapter);
    this.modelAdapter.setModel(this.model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncSliderAccess getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  public void create() {

    // super.create();
    this.modelAdapter.initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.adapter.addListener(SWT.Selection, getAdapter());
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

    return this.adapter.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.adapter.setSelection(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public UiListMvcModel<E> getModel() {

    return this.model;
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

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
