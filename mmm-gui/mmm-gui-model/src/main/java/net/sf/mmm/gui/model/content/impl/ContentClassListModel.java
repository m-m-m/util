/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.impl;

import javax.annotation.PreDestroy;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.ui.toolkit.base.model.AbstractUIListModel;
import net.sf.mmm.util.event.EventListener;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModel UIListModel} interface for
 * the {@link ContentModelService#getClasses() classes} of the
 * {@link ContentModelService content-model}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassListModel extends AbstractUIListModel<ContentClass> implements
    EventListener<ContentModelEvent> {

  /** the content-model to adapt. */
  private final ContentModelService contentModel;

  /**
   * The constructor.
   * 
   * @param contentModel is the service providing the content-model.
   */
  public ContentClassListModel(ContentModelService contentModel) {

    super();
    this.contentModel = contentModel;
    this.contentModel.getEventRegistrar().addListener(this);
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getElement(int index) {

    return this.contentModel.getClasses().get(index);
  }

  /**
   * {@inheritDoc}
   */
  public int getElementCount() {

    return this.contentModel.getClasses().size();
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOfString(String element) {

    ContentClass contentClass = this.contentModel.getClass(element);
    return this.contentModel.getClasses().indexOf(contentClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(ContentClass element) {

    if (element == null) {
      return null;
    } else {
      return element.getName();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void handleEvent(ContentModelEvent event) {

    if (event.getContentField() == null) {
      ContentClass changedClass = event.getContentClass();
      int index = this.contentModel.getClasses().indexOf(changedClass);
      fireChangeEvent(event.getType(), index, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOf(ContentClass element) {
  
    return this.contentModel.getClasses().indexOf(element);
  }
  
  /**
   * This method disposes this object. It has to be called when this object is
   * no longer needed by the system so it can free allocated resources. After
   * the call of this method this object should NOT be used anymore.
   */
  @PreDestroy
  public void dispose() {

    this.contentModel.getEventRegistrar().removeListener(this);
  }
  
}
