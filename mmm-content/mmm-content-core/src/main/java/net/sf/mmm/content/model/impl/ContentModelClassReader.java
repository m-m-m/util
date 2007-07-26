/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.AbstractContentModelClassReader;
import net.sf.mmm.content.value.impl.SmartIdManagerImpl;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;

/**
 * This class allows to load the content-model reflectively from the entity
 * classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelClassReader extends AbstractContentModelClassReader {

  /**
   * The constructor.
   */
  public ContentModelClassReader() {

    super();
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (getIdManager() == null) {
      setIdManager(new SmartIdManagerImpl());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractContentClass createClass(Class<? extends ContentObject> type) {

    ContentClassImpl contentClass = new ContentClassImpl();
    contentClass.setImplementation(type);
    return contentClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractContentField createField(PojoPropertyDescriptor methodPropertyDescriptor) {

    ContentFieldImpl contentField = new ContentFieldImpl();
    // contentField.setDescriptor(methodPropertyDescriptor);
    return contentField;
  }

}
