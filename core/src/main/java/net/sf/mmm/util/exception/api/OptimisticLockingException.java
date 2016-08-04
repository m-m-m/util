/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.lang.reflect.Method;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * This is a variant of exceptions like {@link javax.persistence.OptimisticLockException} that is suitable for end-users
 * and support NLS/I18N.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class OptimisticLockingException extends NlsRuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the cause such as {@link javax.persistence.OptimisticLockException} or
   *        {@link org.springframework.orm.ObjectOptimisticLockingFailureException}.
   * @param entity the entity representation (e.g. simple name of the entity class).
   * @param id the ID of the entity.
   */
  public OptimisticLockingException(Throwable cause, Object entity, Object id) {
    super(cause, createBundle(NlsBundleUtilExceptionRoot.class).errorOptimisticLocking(entity, id));
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link javax.persistence.OptimisticLockException}.
   */
  public OptimisticLockingException(javax.persistence.OptimisticLockException cause) {
    this(cause, getEntityRepresentation(cause.getEntity()), getId(cause.getEntity()));
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link org.springframework.orm.ObjectOptimisticLockingFailureException}.
   */
  public OptimisticLockingException(org.springframework.orm.ObjectOptimisticLockingFailureException cause) {
    this(cause, getEntityRepresentation(cause.getPersistentClassName()), cause.getIdentifier());
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link org.hibernate.StaleObjectStateException}.
   */
  public OptimisticLockingException(org.hibernate.StaleObjectStateException cause) {
    this(cause, getEntityRepresentation(cause.getEntityName()), cause.getIdentifier());
  }

  private static Object getId(Object entity) {

    if (entity == null) {
      return null;
    }
    try {
      Method method = entity.getClass().getMethod("getId", ReflectionUtilLimited.NO_PARAMETERS);
      return method.invoke(entity, ReflectionUtilLimited.NO_ARGUMENTS);
    } catch (Throwable t) {
      // ignore MethodNotFoundException, etc.
      return null;
    }
  }

  private static String getEntityRepresentation(Object entity) {

    if (entity == null) {
      return null;
    }
    if (entity instanceof CharSequence) {
      String name = entity.toString();
      int lastDot = name.lastIndexOf('.');
      if (lastDot > 0) {
        return name.substring(lastDot + 1);
      }
      return name;
    } else {
      Class<?> type;
      try {
        type = org.hibernate.Hibernate.getClass(entity);
      } catch (Throwable t) {
        // ignore ClassNotFoundException, etc.
        type = entity.getClass();
      }
      return type.getSimpleName();
    }
  }

  @Override
  public boolean isForUser() {

    return true;
  }

}
