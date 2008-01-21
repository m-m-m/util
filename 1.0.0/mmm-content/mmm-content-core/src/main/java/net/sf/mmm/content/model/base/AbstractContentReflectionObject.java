/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.base.FieldAnnotation;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.value.api.Lock;
import net.sf.mmm.content.value.api.MetaDataSet;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the abstract base implementation of the
 * {@link ContentReflectionObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentReflectionObject.CLASS_ID, name = ContentReflectionObject.CLASS_NAME, isExtendable = false)
public abstract class AbstractContentReflectionObject extends AbstractContentObject implements
    ContentReflectionObject {

  /**
   * The constructor.
   */
  public AbstractContentReflectionObject() {

    super();
  }

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name}.
   * @param id is the {@link #getId() ID}.
   */
  public AbstractContentReflectionObject(String name, SmartId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 20)
  public abstract Modifiers getModifiers();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setDeletedFlag(boolean deleted) {

    // make package visible...
    super.setDeletedFlag(deleted);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setLock(Lock lock) {

    // make package visible...
    super.setLock(lock);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setMetaDataSet(MetaDataSet metaDataSet) {

    // make package visible...
    super.setMetaDataSet(metaDataSet);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String name = getName();
    if (name == null) {
      name = "-";
    }
    SmartId id = getId();
    String idString;
    if (id == null) {
      idString = "-";
    } else {
      idString = id.toString();
    }
    StringBuffer buffer = new StringBuffer(name.length() + idString.length() + 3);
    buffer.append(name);
    buffer.append(" [");
    buffer.append(idString);
    buffer.append(']');
    return buffer.toString();
  }

}
