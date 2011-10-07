/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.base;

import net.sf.mmm.content.datatype.api.Link;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link Link} interface.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLink<CLASS> implements Link<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8830581540069493211L;

  /** @see #getClassifier() */
  private final String classifier;

  /** @see #getTargetObject() */
  private final CLASS targetObject;

  /**
   * The constructor.
   * 
   * @param targetObject is the {@link #getTargetObject() linked object}.
   * @param classifier is the {@link #getClassifier() classifier}. May be
   *        <code>null</code>.
   */
  public AbstractLink(CLASS targetObject, String classifier) {

    super();
    NlsNullPointerException.checkNotNull("targetObject", targetObject);
    this.targetObject = targetObject;
    this.classifier = classifier;
  }

  /**
   * {@inheritDoc}
   */
  public CLASS getValue() {

    return getTargetObject();
  }

  /**
   * {@inheritDoc}
   */
  public CLASS getTargetObject() {

    return this.targetObject;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return "->" + this.targetObject;
  }

  /**
   * {@inheritDoc}
   */
  public String getClassifier() {

    return this.classifier;
  }

  /**
   * NOTE: Override {@link #getTitle()} instead of this method.<br/>
   * 
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    return getTitle();
  }

}
