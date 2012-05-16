/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.base.ComposedNlsMessage;

/**
 * An {@link ComposedException} combines a list of exceptions in one single exception. This is helpful if you
 * are validating something and want to collect all errors and throw them as one exception with all
 * information.<br/>
 * Otherwise you would stop at the first error, the user could fix it, restart and end up with the next error
 * and so on.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class ComposedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3290876155391059885L;

  /** The child errors. */
  private Throwable[] errors;

  /**
   * The constructor.
   * 
   * @param errors are the {@link Throwable errors} that have been collected.
   */
  public ComposedException(Throwable... errors) {

    super(createBundle(NlsMessagesBundleUtilCore.class).errorComposed(createSubMessage(errors)));
    this.errors = errors;
  }

  /**
   * This method creates the {@link NlsMessage} wrapping the given <code>errors</code>.
   * 
   * @param errors are the {@link Throwable errors} that have been collected.
   * @return the combined {@link NlsMessage}.
   */
  private static NlsMessage createSubMessage(Throwable[] errors) {

    Object[] messages = new Object[errors.length];
    for (int i = 0; i < messages.length; i++) {
      if (errors[i] == null) {
        messages[i] = null;
      } else if (errors[i] instanceof NlsThrowable) {
        messages[i] = ((NlsThrowable) errors[i]).getNlsMessage();
      } else {
        messages[i] = errors[i].getLocalizedMessage();
      }
    }
    return new ComposedNlsMessage(messages);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    super.printStackTrace(locale, resolver, buffer);
    try {
      synchronized (buffer) {
        for (Throwable nested : this.errors) {
          if (nested != null) {
            buffer.append("Caused by: ");
            buffer.append(StringUtil.LINE_SEPARATOR);
            if (nested instanceof NlsThrowable) {
              ((NlsThrowable) nested).printStackTrace(locale, resolver, buffer);
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
