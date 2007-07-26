/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the abstract base implementation of the
 * {@link ContentReflectionObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentReflectionObject extends AbstractContentObject implements
    ContentReflectionObject {

  /**
   * {@inheritDoc}
   */
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setName(String name) {

    super.setName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setDeleted(boolean deleted) {

    super.setDeleted(deleted);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setId(SmartId id) {

    // make package visible...
    super.setId(id);
  }

}
