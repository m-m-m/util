/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.ComboBoxModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiList} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiComboBoxImpl<E> extends AbstractUiWidgetSwing<JComboBox> implements UiComboBox<E> {

  /** the combo-box model adapter */
  private ComboBoxModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiComboBoxImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JComboBox());
    this.modelAdapter = null;
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

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new ComboBoxModelAdapter<E>(newModel);
    JComboBox combo = getAdapter().getDelegate();
    combo.setModel(this.modelAdapter);
    combo.setRenderer(new Renderer());
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return getAdapter().getDelegate().getSelectedIndex();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    getAdapter().getDelegate().setSelectedIndex(newIndex);
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
  public boolean isEditable() {

    return getAdapter().getDelegate().isEditable();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    getAdapter().getDelegate().setEditable(editableFlag);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return (String) getAdapter().getDelegate().getSelectedItem();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getAdapter().getDelegate().setSelectedItem(text);
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
    if (newIndex != -1) {
      JComboBox combo = getAdapter().getDelegate();
      combo.setSelectedIndex(newIndex);
      combo.repaint();
    }
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    int index = getAdapter().getDelegate().getSelectedIndex();
    if (index == -1) {
      return null;
    }
    return this.modelAdapter.getModel().getElement(index);
  }

  /**
   * This inner class is the renderer used to layout the list items.
   */
  private class Renderer extends BasicComboBoxRenderer {

    /** UID for serialization. */
    private static final long serialVersionUID = 511949987370124270L;

    /**
     * The constructor.
     */
    public Renderer() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList swingList, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

      super.getListCellRendererComponent(swingList, value, index, isSelected, cellHasFocus);
      String text;
      if (value == null) {
        text = UiComboBoxImpl.this.modelAdapter.getModel().getNullString();
      } else {
        text = UiComboBoxImpl.this.modelAdapter.getModel().toString((E) value);
      }
      setText(text);
      return this;
    }
  }

}
