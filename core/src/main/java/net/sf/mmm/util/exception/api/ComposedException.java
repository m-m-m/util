/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.base.ComposedNlsMessage;

/**
 * An {@link ComposedException} combines a list of exceptions in one single exception. This is helpful if you are
 * validating something and want to collect all errors and throw them as one exception with all information. <br>
 * Otherwise you would stop at the first error, the user could fix it, restart and end up with the next error and so on.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class ComposedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3290876155391059885L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "Composed";

  /** The child errors. */
  private Throwable[] errors;

  /**
   * The constructor.
   *
   * @param errors are the {@link Throwable errors} that have been collected.
   */
  public ComposedException(Throwable... errors) {

    super(createBundle(NlsBundleUtilExceptionRoot.class).errorComposed(createSubMessage(errors)));
    this.errors = errors;
  }

  /**
   * The constructor.
   *
   * @param errors are the {@link NlsMessage error messages} that have been collected.
   */
  public ComposedException(NlsObject... errors) {

    super(createBundle(NlsBundleUtilExceptionRoot.class).errorComposed(createSubMessage(errors)));
    this.errors = null;
  }

  /**
   * This method creates the {@link NlsMessage} wrapping the given <code>errors</code>.
   *
   * @param errors are the {@link Throwable errors} that have been collected.
   * @return the combined {@link NlsMessage}.
   */
  private static NlsMessage createSubMessage(Object[] errors) {

    Object[] messages = new Object[errors.length];
    for (int i = 0; i < messages.length; i++) {
      if (errors[i] == null) {
        messages[i] = null;
      } else if (errors[i] instanceof NlsObject) {
        messages[i] = ((NlsObject) errors[i]).toNlsMessage();
      } else if (errors[i] instanceof Throwable) {
        messages[i] = ((Throwable) errors[i]).getLocalizedMessage();
      } else {
        messages[i] = errors[i];
      }
    }
    return new ComposedNlsMessage(messages);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, Appendable buffer) {

    super.printStackTrace(locale, buffer);
    if (this.errors != null) {
      try {
        synchronized (buffer) {
          for (Throwable nested : this.errors) {
            if (nested != null) {
              buffer.append("Caused by: ");
              buffer.append(StringUtil.LINE_SEPARATOR);
              if (nested instanceof NlsThrowable) {
                ((NlsThrowable) nested).printStackTrace(locale, buffer);
              } else {
                if (buffer instanceof PrintStream) {
                  nested.printStackTrace((PrintStream) buffer);
                } else if (buffer instanceof PrintWriter) {
                  nested.printStackTrace((PrintWriter) buffer);
                } else {
                  StringWriter writer = new StringWriter();
                  PrintWriter printWriter = new PrintWriter(writer);
                  nested.printStackTrace(printWriter);
                  printWriter.flush();
                  buffer.append(writer.toString());
                }
              }
            }
          }
        }
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
