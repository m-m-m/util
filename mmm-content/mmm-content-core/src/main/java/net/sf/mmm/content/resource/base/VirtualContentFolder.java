/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import java.util.Collection;
import java.util.List;

import net.sf.mmm.content.resource.api.ContentResource;

/**
 * This is an implementation of the interface
 * {@link net.sf.mmm.content.resource.api.ContentFolder} that is virtual. Here
 * virtual means that the folder is NOT stored in the persistence and can NOT be
 * modified regularly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class VirtualContentFolder extends AbstractContentFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -3902226716325685268L;

  /** @see #getChildren() */
  private Collection<ContentResource> children;

  /**
   * The constructor.
   */
  public VirtualContentFolder() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param children are the {@link #getChildren() children} of the folder.
   */
  public VirtualContentFolder(Collection<ContentResource> children) {

    super();
    this.children = children;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<ContentResource> getChildren() {

    return this.children;
  }

}
