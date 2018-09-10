/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

/**
 * The {@link OptimisticLockingException} for {@link ObjectOptimisticLockingFailureException} from Spring.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class OptimisticLockingExceptionSpring extends OptimisticLockingException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause the {@link ObjectOptimisticLockingFailureException}. May not be {@code null}.
   */
  public OptimisticLockingExceptionSpring(ObjectOptimisticLockingFailureException cause) {
    super(cause, getEntityRepresentation(cause.getPersistentClassName()), cause.getIdentifier());
  }

}
