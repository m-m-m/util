/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.value.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.content.model.base.ValueTypeService;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUIListModel;

/**
 * This is the implementation of the {@link UIListModel} interface for the
 * values provided by the {@link ValueTypeService value-service}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(type = UIListModel.class, name = "ValueTypeListModel")
public class ValueTypeListModel extends AbstractUIListModel<String> {

  /** @see #setValueService(ValueTypeService) */
  private ValueTypeService valueService;

  /** @see #initialize() */
  private String[] valueNames;

  /**
   * The constructor.
   */
  public ValueTypeListModel() {

    super();
    this.valueService = null;
  }

  /**
   * This method sets the valueService.
   * 
   * @param service is the value-service to set.
   */
  @Resource
  public void setValueService(ValueTypeService service) {

    this.valueService = service;
  }

  /**
   * This method initializes the model.
   */
  @PostConstruct
  public void initialize() {

    // TODO: the valueService may be a dynamic service!
    Collection<String> managerCollection = this.valueService.getTypeNames();
    this.valueNames = managerCollection.toArray(new String[managerCollection.size()]);
  }

  /**
   * {@inheritDoc}
   */
  public String getElement(int index) {

    return this.valueNames[index];
  }

  /**
   * {@inheritDoc}
   */
  public int getElementCount() {

    return this.valueNames.length;
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOfString(String element) {

    return getIndexOf(element);
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOf(String element) {
  
    for (int i = 0; i < this.valueNames.length; i++) {
      if (this.valueNames[i].equals(element)) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(String element) {

    if (element == null) {
      return "";
    }
    return element;
  }

}
