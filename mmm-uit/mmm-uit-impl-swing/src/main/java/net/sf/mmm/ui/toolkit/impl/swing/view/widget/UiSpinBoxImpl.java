/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JSpinner;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.SpinnerModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox} interface using Swing
 * as the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSpinBoxImpl<E> extends AbstractUiWidgetSwing<JSpinner> implements UiSpinBox<E> {

  /** the model used */
  private final SpinnerModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param model is the model for this spin-box.
   */
  public UiSpinBoxImpl(UiFactorySwing uiFactory, UiListMvcModel<E> model) {

    super(uiFactory, new JSpinner());
    this.modelAdapter = new SpinnerModelAdapter<E>(model);
    getDelegate().setModel(this.modelAdapter);

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
  public UiListMvcModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.modelAdapter.setSelectedIndex(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.modelAdapter.getSelectedIndex();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    this.modelAdapter.setSelectedValue(newValue);
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    return this.modelAdapter.getSelectedValue();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return getDelegate().getEditor().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    getDelegate().getEditor().setEnabled(editableFlag);
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
  }

}
