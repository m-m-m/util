/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.io.base.AppendableWriter;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * An {@link NlsWriter} is a {@link java.io.Writer}, that {@link #write(String) writes} the
 * {@link NlsMessage#getLocalizedMessage(Locale) localized message} of the given text. In other words
 * everything that is written here (except via {@link #printRaw(CharSequence)} and
 * {@link #printlnRaw(CharSequence)}) gets translated via {@link NlsMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsWriter extends AppendableWriter {

  /** The {@link NlsMessageFactory}. */
  private final NlsMessageFactory messageFactory;

  /** The {@link Locale} to translate to. */
  private final Locale locale;

  /** The string used to terminate a line. */
  private final String newline;

  /** The {@link NlsTemplateResolver}. */
  private final NlsTemplateResolver resolver;

  /** @see #getArguments() */
  private Map<String, Object> arguments;

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   */
  public NlsWriter(Appendable appendable) {

    this(appendable, new HashMap<String, Object>());
  }

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   * @param arguments are the {@link #getArguments() arguments}.
   */
  public NlsWriter(Appendable appendable, Map<String, Object> arguments) {

    this(appendable, arguments, Locale.getDefault());
  }

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   * @param arguments are the {@link #getArguments() arguments}.
   * @param locale is the {@link Locale}.
   */
  public NlsWriter(Appendable appendable, Map<String, Object> arguments, Locale locale) {

    this(appendable, arguments, locale, StringUtilImpl.getInstance().getLineSeparator());
  }

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   * @param arguments are the {@link #getArguments() arguments}.
   * @param locale is the {@link Locale}.
   * @param newline is the {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line-separator}.
   */
  public NlsWriter(Appendable appendable, Map<String, Object> arguments, Locale locale, String newline) {

    this(appendable, arguments, locale, newline, NlsAccess.getFactory());
  }

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   * @param arguments are the {@link #getArguments() arguments}.
   * @param locale is the {@link Locale}.
   * @param newline is the {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line-separator}.
   * @param messageFactory is the {@link NlsMessageFactory}.
   */
  public NlsWriter(Appendable appendable, Map<String, Object> arguments, Locale locale, String newline,
      NlsMessageFactory messageFactory) {

    this(appendable, arguments, locale, newline, messageFactory, null);
  }

  /**
   * The constructor.
   * 
   * @param appendable is the {@link Appendable} to delegate to.
   * @param arguments are the {@link #getArguments() arguments}.
   * @param locale is the {@link Locale}.
   * @param newline is the {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line-separator}.
   * @param messageFactory is the {@link NlsMessageFactory}.
   * @param resolver is the {@link NlsTemplateResolver} to use.
   */
  public NlsWriter(Appendable appendable, Map<String, Object> arguments, Locale locale, String newline,
      NlsMessageFactory messageFactory, NlsTemplateResolver resolver) {

    super(appendable);
    this.messageFactory = messageFactory;
    this.locale = locale;
    this.arguments = arguments;
    this.newline = newline;
    this.resolver = resolver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsWriter append(CharSequence csq, int start, int end) throws RuntimeIoException {

    return append(csq.subSequence(start, end));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsWriter append(CharSequence csq) throws RuntimeIoException {

    if (csq == null) {
      super.append("null");
    } else {
      String string = csq.toString();
      NlsMessage message = this.messageFactory.create(string, this.arguments);
      message.getLocalizedMessage(this.locale, this.resolver, getAppendable());
    }
    return this;
  }

  /**
   * This method is the same as {@link #append(CharSequence)} or {@link #write(String)}.
   * 
   * @param csq is the {@link NlsMessage#getInternationalizedMessage() internationalized message} to write.
   * @throws RuntimeIoException if an error occurred while {@link Appendable#append(CharSequence) writing}.
   */
  public void print(CharSequence csq) throws RuntimeIoException {

    append(csq);
  }

  /**
   * This method writes an {@link NlsMessage}.
   * 
   * @param message is the {@link NlsMessage} to write.
   * @throws RuntimeIoException if an error occurred while {@link Appendable#append(CharSequence) writing}.
   */
  public void print(NlsMessage message) throws RuntimeIoException {

    message.getLocalizedMessage(this.locale, this.resolver, getAppendable());
  }

  /**
   * This method writes a newline (line-separator).
   * 
   * @throws RuntimeIoException if an error occurred while {@link Appendable#append(CharSequence) writing}.
   */
  public void println() throws RuntimeIoException {

    super.append(this.newline);
  }

  /**
   * This method is like {@link #append(CharSequence)} or {@link #write(String)} but additionally adds a
   * newline (line-separator) after the message.
   * 
   * @param csq is the {@link NlsMessage#getInternationalizedMessage() internationalized message} to write.
   * @throws RuntimeIoException if an error occurred while {@link Appendable#append(CharSequence) writing}.
   */
  public void println(CharSequence csq) throws RuntimeIoException {

    append(csq);
    super.append(this.newline);
  }

  /**
   * This method {@link #write(String) writes} the raw <code>text</code> without internationalization. It may
   * be used for text that is language independent or already internationalized.
   * 
   * @param text is the raw text to write.
   * @throws RuntimeIoException if an {@link java.io.IOException} occurred while
   *         {@link Appendable#append(CharSequence) writing} to the underlying {@link Appendable}.
   */
  public void printRaw(CharSequence text) throws RuntimeIoException {

    super.append(text);
  }

  /**
   * This method {@link #write(String) writes} the raw <code>text</code> without internationalization. It may
   * be used for text that is language independent or already internationalized.
   * 
   * @param text is the raw text to write.
   * @throws RuntimeIoException if an {@link java.io.IOException} occurred while
   *         {@link Appendable#append(CharSequence) writing} to the underlying {@link Appendable}.
   */
  public void printlnRaw(CharSequence text) throws RuntimeIoException {

    super.append(text);
    super.append(this.newline);
  }

  /**
   * This method gets the {@link NlsMessage#getArgument(String) arguments} for the created {@link NlsMessage}
   * s. The arguments may be modified or {@link #setArguments(Map) replaced} before the next message is
   * {@link #write(String) written}.
   * 
   * @return the arguments.
   */
  public Map<String, Object> getArguments() {

    return this.arguments;
  }

  /**
   * This method sets the {@link #getArguments() arguments}. This allows to replace them before writing
   * another message.
   * 
   * @param arguments is the arguments to set
   */
  public void setArguments(Map<String, Object> arguments) {

    this.arguments = arguments;
  }

}
