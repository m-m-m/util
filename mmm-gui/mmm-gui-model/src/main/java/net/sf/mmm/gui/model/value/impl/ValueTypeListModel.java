/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.value.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUIListModel;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueService;

/**
 * This is the implementation of the {@link UIListModel} interface for the
 * {@link ValueManager value-managers} provided by the
 * {@link ValueService value-service}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(type = UIListModel.class, name = "ValueTypeListModel")
public class ValueTypeListModel extends AbstractUIListModel<ValueManager> {

  /** @see #setValueService(ValueService) */
  private ValueService valueService;

  /** @see #initialize() */
  private ValueManager[] managers;

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
  public void setValueService(ValueService service) {

    this.valueService = service;
  }

  /**
   * This method initializes the model.
   */
  @PostConstruct
  public void initialize() {

    Collection<ValueManager> managerCollection = this.valueService.getManagers();
    this.managers = managerCollection.toArray(new ValueManager<?>[managerCollection.size()]);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleListenerException(UIListModelListener listener, Throwable t) {

  }

  /**
   * {@inheritDoc}
   */
  public ValueManager getElement(int index) {

    return this.managers[index];
  }

  /**
   * {@inheritDoc}
   */
  public int getElementCount() {

    return this.managers.length;
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOfString(String element) {

    for (int i = 0; i < this.managers.length; i++) {
      if (this.managers[i].getName().equals(element)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(ValueManager element) {

    if (element == null) {
      return "";
    }
    return element.getName();
  }

}
