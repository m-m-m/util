/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.gui.model.content.api.ContentReflectionModelManager;
import net.sf.mmm.gui.model.content.api.FieldTableModel;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This is the implementation of the {@link ContentReflectionModelManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentReflectionModelManagerImpl implements ContentReflectionModelManager {
  
  /** the map with the table-models */
  private final Map<ContentId, ContentClassFieldTableModel> id2modelMap;

  /** @see #setContentModelService(ContentModelService) */
  private ContentModelService contentModel;

  /** @see #getContentClassListModel() */
  private ContentClassListModel classListModel;
  
  /** @see #getContentClassTreeModel() */
  private ContentClassTreeModel classTreeModel;

  /**
   * The constructor.
   */
  public ContentReflectionModelManagerImpl() {

    super();
    this.id2modelMap = new HashMap<ContentId, ContentClassFieldTableModel>();
  }

  /**
   * This method injects the content-model-service required by this component.
   * 
   * @param modelService is the content-model-service.
   */
  @Resource
  public void setContentModelService(ContentModelService modelService) {

    this.contentModel = modelService;
  }

  /**
   * This method initializes this class. It has to be called after construction 
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (this.contentModel == null) {
      throw new ResourceMissingException("contentModel");
    }
    this.classListModel = new ContentClassListModel(this.contentModel);
    this.classTreeModel = new ContentClassTreeModel(this.contentModel);
  }
  
  /**
   * {@inheritDoc}
   */
  public FieldTableModel getFieldTableModel(ContentClass contentClass) {

    synchronized (this.contentModel) {
      ContentClassFieldTableModel tableModel = this.id2modelMap.get(contentClass.getId());
      if (tableModel == null) {
        tableModel = new ContentClassFieldTableModel(contentClass, this.contentModel);
        this.id2modelMap.put(contentClass.getId(), tableModel);
      }
      return tableModel;
    }
  }

  /**
   * {@inheritDoc}
   */
  public UIListModel<ContentClass> getContentClassListModel() {

    return this.classListModel;
  }

  /**
   * {@inheritDoc}
   */
  public UITreeModel<ContentClass> getContentClassTreeModel() {

    return this.classTreeModel;
  }
  
  /**
   * This method disposes this object. It has to be called when this object
   * is no longer needed by the system so it can free allocated resources.
   */
  @PreDestroy
  public void dispose() {

    if (this.classListModel != null) {
      this.classListModel.dispose();
    }
    if (this.classTreeModel != null) {
      this.classTreeModel.dispose();
    }
  }
  
}
