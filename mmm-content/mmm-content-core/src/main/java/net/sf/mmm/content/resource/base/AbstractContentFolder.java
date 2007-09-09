/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import java.util.List;

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
public abstract class AbstractContentFolder extends AbstractContentObject implements ContentFolder {

  /** @see #getContentClass() */
  private static ContentClass virtualFolderClass;

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
  public ContentClass getContentClass() {

    return virtualFolderClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends ContentResource> getChildren() {
  
    return null;
  }
  
  /**
   * This method sets the {@link #getContentClass() content-class} of this
   * folder.
   * 
   * @param contentClass is the content-class.
   */
  public static void setContentClass(ContentClass contentClass) {

    assert ((virtualFolderClass == null) || (virtualFolderClass == contentClass));
    assert (contentClass != null);
    virtualFolderClass = contentClass;
  }

}
