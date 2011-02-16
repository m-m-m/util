/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.MutableComboBoxModel;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel} to a
 * swing {@link javax.swing.ComboBoxModel}.
 * 
 * @param <E> is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComboBoxModelAdapter<E> extends ListModelAdapter<E> implements MutableComboBoxModel {

  /** uid for serialization */
  private static final long serialVersionUID = 7187832628689261207L;

  /** the selected item */
  private Object selectedItem;

  /**
   * The constructor.
   * 
   * @param listModel
   */
  public ComboBoxModelAdapter(UiListMvcModel<E> listModel) {

    super(listModel);
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedItem(Object anItem) {

    this.selectedItem = anItem;
  }

  /**
   * {@inheritDoc}
   */
  public Object getSelectedItem() {

    return this.selectedItem;
  }

  /**
   * {@inheritDoc}
   */
  public void addElement(Object obj) {

  // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void removeElement(Object obj) {

  // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void insertElementAt(Object obj, int index) {

  // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void removeElementAt(int index) {

  // TODO Auto-generated method stub

  }

}
