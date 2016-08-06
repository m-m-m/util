/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import org.hibernate.StaleObjectStateException;

/**
 * The {@link OptimisticLockingException} for {@link StaleObjectStateException} from Hibernate.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class OptimisticLockingExceptionHibernate extends OptimisticLockingException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the {@link StaleObjectStateException}.
   */
  public OptimisticLockingExceptionHibernate(StaleObjectStateException cause) {
    super(cause, getEntityRepresentation(cause.getEntityName()), cause.getIdentifier());
  }

}
