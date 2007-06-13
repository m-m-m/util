/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.value.impl;

import javax.annotation.Resource;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.value.api.ValueManager;

/**
 * This is the
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueTypeWidgetManager {

  private UIListModel<ValueManager> model;

  /**
   * The constructor.
   */
  public ValueTypeWidgetManager() {

    super();
  }

  /**
   * This method sets the model.
   * 
   * @param model is the model to set.
   */
  @Resource(name = "ValueTypeModel")
  public void setModel(UIListModel<ValueManager> valueModel) {

    this.model = valueModel;
  }

  /**
   * 
   * @param factory
   * @return
   */
  public UIList<ValueManager> createValueTypeList(UIFactory factory) {

    return factory.createList(this.model);
  }

  /**
   * 
   * @param factory
   * @return
   */
  public UIComboBox<ValueManager> createValueTypeCombo(UIFactory factory) {

    return factory.createComboBox(this.model);
  }

}
