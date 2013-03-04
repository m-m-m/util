/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.link;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.Link;

/**
 * This is the abstract base implementation of the {@link Link} interface.
 * 
 * @param <TARGET> is the type of the linked {@link #getTarget() target entity}. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLink<TARGET extends DataEntity> implements Link<TARGET> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8830581540069493211L;

  /** @see #getClassifier() */
  private String classifier;

  /**
   * The constructor.
   */
  public AbstractLink() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TARGET getValue() {

    return getTarget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getClassifier() {

    return this.classifier;
  }

  /**
   * @param classifier is the classifier to set
   */
  public void setClassifier(String classifier) {

    this.classifier = classifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    StringBuilder buffer = new StringBuilder();
    buffer.append("link");
    if (this.classifier != null) {
      buffer.append('<');
      buffer.append(this.classifier);
      buffer.append('>');
    }
    buffer.append(": ");
    buffer.append(getTarget().getId());
    return buffer.toString();
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
