/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.ComboBoxModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIList} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIComboBoxImpl<E> extends AbstractUIWidget implements UIComboBox<E> {

  /** the swing combo-box */
  private final JComboBox comboBox;

  /** the combo-box model adapter */
  private ComboBoxModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UIComboBoxImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.comboBox = new JComboBox();
    this.modelAdapter = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.comboBox;
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

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new ComboBoxModelAdapter<E>(newModel);
    this.comboBox.setModel(this.modelAdapter);
    this.comboBox.setRenderer(new Renderer());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.comboBox.getSelectedIndex();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {

    this.comboBox.setSelectedIndex(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable()
   */
  public boolean isEditable() {

    return this.comboBox.isEditable();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#setEditable(boolean)
   */
  public void setEditable(boolean editableFlag) {

    this.comboBox.setEditable(editableFlag);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return (String) this.comboBox.getSelectedItem();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.comboBox.setSelectedItem(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
    if (newIndex != -1) {
      this.comboBox.setSelectedIndex(newIndex);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    int index = this.comboBox.getSelectedIndex();
    if (index == -1) {
      return null;
    }
    return this.modelAdapter.getModel().getElement(index);
  }

  /**
   * This inner class is the renderer used to layout the list items.
   */
  private class Renderer extends BasicComboBoxRenderer {

    /** UID for serialization */
    private static final long serialVersionUID = 511949987370124270L;

    /**
     * The constructor.
     */
    public Renderer() {

      super();
    }

    /**
     * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
     *      java.lang.Object, int, boolean, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList swingList, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

      super.getListCellRendererComponent(swingList, value, index, isSelected, cellHasFocus);
      setText(UIComboBoxImpl.this.modelAdapter.getModel().toString((E) value));
      return this;
    }
  }

}
