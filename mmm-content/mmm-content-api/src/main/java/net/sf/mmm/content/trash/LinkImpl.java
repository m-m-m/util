/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.trash;

import net.sf.mmm.content.api.ContentObject;

/**
 * This is the implementation of {@link net.sf.mmm.content.trash.Link}.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LinkImpl<CLASS extends ContentObject> extends AbstractLink<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3818102518986171086L;

  /**
   * The constructor.
   * 
   * @param targetObject is the {@link #getTargetObject() linked object}.
   * @param classifier is the {@link #getClassifier() classifier}. May be
   *        <code>null</code>.
   */
  public LinkImpl(CLASS targetObject, String classifier) {

    super(targetObject, classifier);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "->" + getTargetObject().getContentId();
  }

}
