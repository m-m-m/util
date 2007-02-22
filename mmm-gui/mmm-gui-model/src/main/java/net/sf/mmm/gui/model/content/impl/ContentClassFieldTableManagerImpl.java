/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.gui.model.content.api.ContentClassFieldTableManager;
import net.sf.mmm.gui.model.content.api.FieldTableModel;

/**
 * This is the implementation of the {@link ContentClassFieldTableManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassFieldTableManagerImpl implements ContentClassFieldTableManager {

  /** @see #setContentModelService(ContentModelService) */
  private ContentModelService model;

  /** the map with the table-models */
  private final Map<Id, ContentClassTableModel> id2modelMap;

  /**
   * The constructor.
   */
  public ContentClassFieldTableManagerImpl() {

    super();
    this.id2modelMap = new HashMap<Id, ContentClassTableModel>();
  }

  /**
   * This method injects the content-model-service required by this component.
   * 
   * @param modelService
   *        is the content-model-service.
   */
  @Resource
  public void setContentModelService(ContentModelService modelService) {

    this.model = modelService;
  }

  /**
   * @see net.sf.mmm.gui.model.content.api.ContentClassFieldTableManager#getFieldTableModel(net.sf.mmm.content.model.api.ContentClass)
   */
  public FieldTableModel getFieldTableModel(ContentClass contentClass) {

    synchronized (this.model) {
      ContentClassTableModel tableModel = this.id2modelMap.get(contentClass.getId());
      if (tableModel == null) {
        tableModel = new ContentClassTableModel(contentClass, this.model);
        this.id2modelMap.put(contentClass.getId(), tableModel);
      }
      return tableModel;
    }
  }

}
