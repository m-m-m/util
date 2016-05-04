/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsMessage} for composing other objects or
 * messages.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class ComposedNlsMessage extends AbstractNlsMessage {

  private static final long serialVersionUID = 1L;

  private  Object[] arguments;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ComposedNlsMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param arguments are the {@link #getArgument(int) arguments}.
   */
  public ComposedNlsMessage(Object[] arguments) {

    super();
    assert (arguments != null);
    this.arguments = arguments;
  }

  @Override
  public Object getArgument(int index) {

    if (index >= this.arguments.length) {
      return null;
    }
    return this.arguments[index];
  }

  @Override
  public int getArgumentCount() {

    return this.arguments.length;
  }

  @Override
  public String getInternationalizedMessage() {

    return "";
  }

  @Override
  public Object getArgument(String key) {

    try {
      int index = Integer.parseInt(key);
      return getArgument(index);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer)
      throws RuntimeIoException {

    try {
      boolean newline = false;
      for (Object message : this.arguments) {
        if (newline) {
          buffer.append(StringUtil.LINE_SEPARATOR_LF);
        } else {
          newline = true;
        }
        if (message == null) {
          buffer.append("null");
        } else if (message instanceof NlsObject) {
          NlsObject nlsObject = (NlsObject) message;
          nlsObject.toNlsMessage().getLocalizedMessage(locale, resolver, buffer);
        } else {
          buffer.append(message.toString());
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

}
