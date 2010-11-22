/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.io.api.DetectorStream;

/**
 * This is the abstract base implementation of a {@link DetectorStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractDetectorStream implements DetectorStream {

  /** @see #getMetadata() */
  private final Map<String, Object> mutableMetadata;

  /** @see #getMetadata() */
  private final Map<String, Object> metadata;

  /** @see #isDone() */
  private boolean done;

  /**
   * The constructor.
   */
  public AbstractDetectorStream() {

    this(new HashMap<String, Object>());
  }

  /**
   * The constructor.
   * 
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   */
  public AbstractDetectorStream(Map<String, Object> mutableMetadata) {

    super();
    this.mutableMetadata = mutableMetadata;
    this.metadata = Collections.unmodifiableMap(this.mutableMetadata);
    this.done = false;
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Object> getMetadata() {

    return this.metadata;
  }

  /**
   * This method gets the mutable {@link #getMetadata() Metadata}.
   * 
   * @return the internal {@link #getMetadata() Metadata}-Map that is mutable.
   */
  protected Map<String, Object> getMutableMetadata() {

    return this.mutableMetadata;
  }

  /**
   * This method sets the {@link #isDone() done} flag.
   */
  protected void setDone() {

    this.done = true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDone() {

    return this.done;
  }

}
