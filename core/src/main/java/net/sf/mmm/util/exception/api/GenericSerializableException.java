/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is a very specific exception that allows sending of any {@link Throwable} via a remote service. Some exceptions
 * can not be serialized at all and others may not be de-serialized because they (or one of their {@link #getCause()
 * cause}s or {@link #getSuppressed() suppressed} exceptions) are an implementation secret and are therefore not in the
 * classpath on the other end. This exception allows to wrap any other kind of exception so the {@link #getMessage()
 * message}, {@link #getCode() code}, {@link #getUuid() UUID} and {@link #printStackTrace(java.io.PrintWriter)
 * stacktrace} are preserved while the other end only needs to know this exception class. However the original
 * {@link #getClass() type} of the exception is lost in the manner that catching, handling and instanceof-checks of the
 * actual exception will not work anymore.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class GenericSerializableException extends RuntimeException implements NlsThrowable, NlsMessage {

  private static final long serialVersionUID = -8387886697693002740L;

  private String originalExceptionName;

  private String code;

  private boolean technical;

  private UUID uuid;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected GenericSerializableException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param cause - see {@link #getCause()}. Should be a {@link GenericSerializableException}.
   * @param message - see {@link #getMessage()}.
   * @param originalExceptionName - see {@link #getOriginalExceptionName()}.
   * @param stacktrace - the {@link #getStackTrace() stacktrace} of the original exception.
   * @param technical - see {@link #isTechnical()}.
   * @param code - see {@link #getCode()}.
   * @param uuid - see {@link #getUuid()}.
   */
  public GenericSerializableException(Throwable cause, String message, String originalExceptionName,
      StackTraceElement[] stacktrace, boolean technical, String code, UUID uuid) {

    super(message, cause);
    setStackTrace(stacktrace);
    this.originalExceptionName = originalExceptionName;
    this.technical = technical;
    this.code = code;
    this.uuid = uuid;
  }

  @Override
  public GenericSerializableException createCopy(ExceptionTruncation truncation) {

    Throwable newCause;
    if (truncation.isRemoveCause()) {
      newCause = null;
    } else {
      newCause = getCause();
    }
    StackTraceElement[] newStacktrace;
    if (truncation.isRemoveStacktrace()) {
      newStacktrace = ExceptionUtil.NO_STACKTRACE;
    } else {
      newStacktrace = getStackTrace();
    }
    GenericSerializableException copy = new GenericSerializableException(newCause, getMessage(),
        this.originalExceptionName, newStacktrace, this.technical, this.code, this.uuid);
    if (!truncation.isRemoveSuppressed()) {
      for (Throwable suppressed : getSuppressed()) {
        copy.addSuppressed(suppressed);
      }
    }
    return copy;
  }

  /**
   * @return the originalExceptionName
   */
  public String getOriginalExceptionName() {

    return this.originalExceptionName;
  }

  /**
   * Prevents creating a stacktrace.
   *
   * @return this instance.
   */
  @Override
  public Throwable fillInStackTrace() {

    // do nothing, no stacktrace!
    return this;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public StackTraceElement[] getStackTrace() {
  //
  // return new StackTraceElement[0];
  // }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public UUID getUuid() {

    return this.uuid;
  }

  @Override
  public NlsMessage toNlsMessage() {

    return this;
  }

  @Override
  public String getInternationalizedMessage() {

    return getMessage();
  }

  @Override
  public int getArgumentCount() {

    return 0;
  }

  @Override
  public Object getArgument(String key) {

    return null;
  }

  @Override
  public Object getArgument(int index) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isTechnical() {

    return this.technical;
  }

  @Override
  public boolean isForUser() {

    return true;
  }

  @Override
  public NlsMessage getNlsMessage() {

    return this;
  }

  @Override
  public String getLocalizedMessage(Locale locale) {

    return getLocalizedMessage();
  }

  @Override
  public void getLocalizedMessage(Locale locale, Appendable appendable) {

    getLocalizedMessage(locale, null, appendable);
  }

  @Override
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    return getLocalizedMessage();
  }

  @Override
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer)
      throws IllegalStateException {

    try {
      buffer.append(getLocalizedMessage());
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  @Override
  public void printStackTrace(Locale locale, Appendable buffer) throws IllegalStateException {

    if (buffer instanceof PrintStream) {
      printStackTrace((PrintStream) buffer);
    } else if (buffer instanceof PrintWriter) {
      printStackTrace((PrintWriter) buffer);
    } else {
      StringWriter writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter(writer);
      printStackTrace(printWriter);
      printWriter.flush();
      try {
        buffer.append(writer.toString());
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.WRITE);
      }
    }
  }

  @Override
  public String toString() {

    // We intentionally use the system locale here to prevent mixed languages in log-files...
    return toString(Locale.getDefault(), null).toString();
  }

  @Override
  public String toString(Locale locale) {

    return toString(locale, null).toString();
  }

  @Override
  public Appendable toString(Locale locale, Appendable appendable) {

    Appendable buffer = appendable;
    if (buffer == null) {
      buffer = new StringBuilder(32);
    }
    try {
      Class<?> myClass = getClass();
      buffer.append(myClass.getName());
      buffer.append(':');
      buffer.append(this.originalExceptionName);
      buffer.append(": ");
      if (!this.originalExceptionName.endsWith(this.code)) {
        buffer.append(this.code);
        buffer.append(": ");
      }
      buffer.append(getLocalizedMessage(locale));
      if (this.uuid != null) {
        buffer.append(StringUtil.LINE_SEPARATOR);
        buffer.append(this.uuid.toString());
      }
      return buffer;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

}
