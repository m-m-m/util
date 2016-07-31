/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.lang.api.Visitor;

/**
 * This is the interface for a formatter of a message-text. It is a simplified view on something like
 * {@link java.text.MessageFormat}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessageFormatter extends NlsFormatter<Void> {

  /**
   * This method formats the underlying pattern by filling in the given {@code arguments} and writing the result into
   * the given {@code buffer}.
   *
   * {@inheritDoc}
   *
   * @param nothing has to be {@code null}. Only for generic compatibility.
   */
  @Override
  void format(Void nothing, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException;

  /**
   * Visits the child elements of this {@link NlsMessageFormatter}.
   *
   * @param staticTextVisitor a {@link Visitor} for the static text segments.
   * @param dynamicArgumentVisitor a {@link Visitor} for the {@link NlsArgument}s.
   */
  void visit(Visitor<String> staticTextVisitor, Visitor<NlsArgument> dynamicArgumentVisitor);

}
