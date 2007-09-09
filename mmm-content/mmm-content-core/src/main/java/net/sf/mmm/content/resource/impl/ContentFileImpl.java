/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.impl;

import net.sf.mmm.content.resource.api.ContentFile;
import net.sf.mmm.content.resource.base.AbstractContentDocument;
import net.sf.mmm.content.value.api.MutableBlob;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the {@link ContentFile} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ContentFileImpl extends AbstractContentDocument implements ContentFile {

  /** UID for serialization. */
  private static final long serialVersionUID = -6514585506345939308L;
  
  /** @see #getBlob() */
  private MutableBlob blob;

  /**
   * The constructor.
   */
  public ContentFileImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the file.
   */
  public ContentFileImpl(String name) {

    super(name);
  }

  /**
   * The constructor.
   *
   * @param id
   * @param name
   */
  public ContentFileImpl(SmartId id, String name) {

    super(id, name);
  }

  /**
   * {@inheritDoc}
   */
  public MutableBlob getBlob() {

    return this.blob;
  }

  /**
   * This method sets the {@link #getBlob() blob}.
   * 
   * @param blob is the blob to set.
   */
  protected void setBlob(MutableBlob blob) {

    this.blob = blob;
  }

}
