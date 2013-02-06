/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.Message;

/**
 * This is the abstract base implementation of {@link Message}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public abstract class AbstractMessage implements Message {

  /** UID for serialization. */
  private static final long serialVersionUID = -88269463788978385L;

  /** @see #getCode() */
  private String code;

  /** @see #getSource() */
  private String source;

  /**
   * The constructor for de-serialization.
   */
  protected AbstractMessage() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   */
  public AbstractMessage(String code, String source) {

    super();
    this.code = code;
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return this.code;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getMessage();
  }

}
