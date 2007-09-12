/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import java.util.Collection;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.api.ContentResource;

/**
 * This is the abstract base implementation of the {@link ContentFolder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentFolder extends AbstractContentResource implements ContentFolder {

  /**
   * The constructor.
   */
  public AbstractContentFolder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public ContentFolder getParent() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends ContentResource> getChildren() {
  
    return null;
  }

}
