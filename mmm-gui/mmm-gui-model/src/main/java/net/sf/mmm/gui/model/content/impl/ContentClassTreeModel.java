/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUITreeModel;
import net.sf.mmm.util.event.EventListener;

/**
 * This is an implementation of the {@link UITreeModel} interface for the
 * {@link ContentClass classes} provided by the {@link ContentModelService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(type = UITreeModel.class, name = "ContentClassTreeModel")
public class ContentClassTreeModel extends AbstractUITreeModel<ContentClass> implements
    EventListener<ContentModelEvent> {

  /** @see #setModelService(ContentModelService) */
  private ContentModelService model;

  /**
   * The constructor.
   */
  public ContentClassTreeModel() {

    super();
    this.model = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<ContentClass> getNodeType() {
  
    return ContentClass.class;
  }
  
  /**
   * This method sets the {@link ContentModelService modelService} adapted by
   * this tree-model.
   * 
   * @param modelService
   *        is the modelService to set.
   */
  @Resource
  public void setModelService(ContentModelService modelService) {

    this.model = modelService;
  }

  /**
   * This method initializes the model.
   */
  @PostConstruct
  public void initialize() {

    this.model.getEventRegistrar().addListener(this);
  }

  /**
   * This method disposes the model.
   */
  @PreDestroy
  public void dispose() {

    this.model.getEventRegistrar().removeListener(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleListenerException(UITreeModelListener listener, Throwable t) {

  }

  /**
   * {@inheritDoc}
   */
  public void handleEvent(ContentModelEvent event) {

    fireChangeEvent(event.getType(), event.getContentClass());
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount(ContentClass node) {

    return node.getSubClasses().size();
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getChildNode(ContentClass node, int index) {

    return node.getSubClasses().get(index);
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getParent(ContentClass node) {

    return node.getSuperClass();
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getRootNode() {

    return this.model.getRootClass();
  }

}
