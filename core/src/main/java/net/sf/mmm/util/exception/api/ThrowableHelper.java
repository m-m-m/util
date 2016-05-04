/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.lang.reflect.Field;
import java.util.List;

import net.sf.mmm.util.reflect.api.AccessFailedException;

import org.slf4j.LoggerFactory;

/**
 * This is a small hack to truncate exceptions.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
final class ThrowableHelper {

  private  static Helper helper;

  /**
   * Construction forbidden.
   */
  private ThrowableHelper() {

  }

  /**
   * @return the lazy initialized {@link Helper} instance.
   */
  private static Helper getHelper() {

    if (helper == null) {
      helper = new Helper();
    }
    return helper;
  }

  /**
   * @see NlsThrowable#createCopy(ExceptionTruncation)
   *
   * @param throwable is the {@link Throwable} to truncate.
   * @param truncation the {@link ExceptionTruncation} settings.
   */
  static void removeDetails(Throwable throwable, ExceptionTruncation truncation) {

    if (truncation.isRemoveStacktrace()) {
      throwable.setStackTrace(ExceptionUtil.NO_STACKTRACE);
    }
    if (truncation.isRemoveCause()) {
      getHelper().setCause(throwable, null);
    }
    if (truncation.isRemoveSuppressed()) {
      getHelper().setSuppressed(throwable, null);
    }
  }

  /**
   * Class for reflective access to {@link Throwable}s.
   */
  private static class Helper {

    /** @see #setCause(Throwable, Throwable) */
    private final Field causeField;

    /** @see #setSuppressed(Throwable, List) */
    private final Field suppressedField;

    /**
     * The constructor.
     */
    public Helper() {

      super();
      this.causeField = getField("cause");
      this.suppressedField = getField("suppressedExceptions");
    }

    /**
     * @param name is the {@link Field#getName()}.
     * @return the corresponding {@link Field} of {@link Throwable}.
     */
    private Field getField(String name) {

      try {
        Field field = Throwable.class.getField(name);
        field.setAccessible(true);
        return field;
      } catch (Exception e) {
        LoggerFactory.getLogger(ThrowableHelper.class).error(
            "Exception truncation not possible due to reflection limitation!", e);
      }
      return null;
    }

    /**
     * @param exception is the {@link Throwable} to manipulate.
     * @param cause is the new {@link Throwable#getCause()}.
     */
    private void setCause(Throwable exception, Throwable cause) {

      setFieldValue(this.causeField, exception, cause);
    }

    /**
     * @param exception is the {@link Throwable} to manipulate.
     * @param suppressed are the new {@link Throwable#getSuppressed() suppressed exceptions}.
     */
    private void setSuppressed(Throwable exception, List<Throwable> suppressed) {

      setFieldValue(this.suppressedField, exception, suppressed);
    }

    /**
     * @param field is the {@link Field} to {@link Field#set(Object, Object) set}.
     * @param exception is the instance where to set the {@link Field} value.
     * @param value is the new {@link Field} {@link Field#get(Object) value}.
     */
    private void setFieldValue(Field field, Throwable exception, Object value) {

      if (field != null) {
        try {
          field.set(exception, value);
        } catch (IllegalAccessException e) {
          throw new AccessFailedException(e, field);
        }
      }
    }

  }

}
