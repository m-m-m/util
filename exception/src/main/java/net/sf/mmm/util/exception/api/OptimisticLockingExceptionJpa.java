/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import javax.persistence.OptimisticLockException;

/**
 * The {@link OptimisticLockingException} for {@link OptimisticLockException} from JPA.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class OptimisticLockingExceptionJpa extends OptimisticLockingException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the {@link OptimisticLockException}.
   */
  public OptimisticLockingExceptionJpa(OptimisticLockException cause) {
    super(cause, getEntityRepresentation(cause.getEntity()), getId(cause.getEntity()));
  }

}
