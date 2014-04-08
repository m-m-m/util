/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.UUID;

import net.sf.mmm.util.lang.base.AbstractMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is the abstract base implementation of {@link ValidationFailure}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractValidationFailure extends AbstractMessage implements ValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -882452608746200225L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractValidationFailure() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   */
  public AbstractValidationFailure(String code, Object source) {

    super(code, source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE_VALIDATION_FAILURE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDetails() {

    // no details by default - override to change...
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UUID getUuid() {

    // UUID is expensive to create and only required to track down errors in log-files what is pointless in
    // validation failures
    return null;
  }
}
