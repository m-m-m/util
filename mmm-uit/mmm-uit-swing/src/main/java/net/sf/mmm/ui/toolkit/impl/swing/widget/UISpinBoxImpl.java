/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JSpinner;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.SpinnerModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBox} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISpinBoxImpl<E> extends AbstractUIWidget implements UISpinBox<E> {

  /** the native Swing widget */
  private final JSpinner spinBox;

  /** the model used */
  private final SpinnerModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param model
   *        is the model for this spin-box.
   */
  public UISpinBoxImpl(UIFactorySwing uiFactory, UINode parentObject, UIListModel<E> model) {

    super(uiFactory, parentObject);
    this.modelAdapter = new SpinnerModelAdapter<E>(model);
    this.spinBox = new JSpinner(this.modelAdapter);

  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    this.spinBox.addChangeListener(createChangeListener());
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.spinBox;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UISpinBox#getModel()
   */
  public UIListModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {

    this.modelAdapter.setSelectedIndex(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.modelAdapter.getSelectedIndex();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    this.modelAdapter.setSelectedValue(newValue);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    return this.modelAdapter.getSelectedValue();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable()
   */
  public boolean isEditable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#setEditable(boolean)
   */
  public void setEditable(boolean editableFlag) {

  // this.spinBox.getEditor().set
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
  }

}
