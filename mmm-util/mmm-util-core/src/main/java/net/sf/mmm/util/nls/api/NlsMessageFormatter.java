/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * This is the interface for a formatter of a message-text. It is a simplified view on something like
 * {@link java.text.MessageFormat}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessageFormatter extends NlsFormatter<Void> {

  /**
   * This method formats the underlying pattern by filling in the given <code>arguments</code> and writing the
   * result into the given <code>buffer</code>.
   * 
   * {@inheritDoc}
   * 
   * @param nothing has to be <code>null</code>. Only for generic compatibility.
   */
  void format(Void nothing, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException;

}
