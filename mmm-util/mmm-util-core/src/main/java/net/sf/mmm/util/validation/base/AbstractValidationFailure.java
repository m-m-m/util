/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is the abstract base implementation of {@link ValidationFailure}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractValidationFailure implements ValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -882452608746200225L;

  /** @see #getCode() */
  private final String code;

  /** @see #getSource() */
  private final String source;

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   */
  public AbstractValidationFailure(String code, String source) {

    super();
    this.code = code;
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  public String getCode() {

    return this.code;
  }

  /**
   * {@inheritDoc}
   */
  public String getSource() {

    return this.source;
  }

}
