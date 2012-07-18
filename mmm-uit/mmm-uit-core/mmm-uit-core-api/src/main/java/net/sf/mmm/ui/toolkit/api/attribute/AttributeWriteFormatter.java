/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.lang.api.Formatter;

/**
 * This interface gives read and write access to the {@link #getFormatter() formatter} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the value to pass to the {@link #getFormatter() formatter}.
 */
public abstract interface AttributeWriteFormatter<VALUE> extends AttributeReadFormatter<VALUE> {

  /**
   * This method sets the {@link #getFormatter() formatter} of this object.<br/>
   * <b>ATTENTION:</b><br/>
   * Please be aware of {@link net.sf.mmm.util.nls.api.NlsMessage i18n} when implementing formatters.
   * 
   * @see net.sf.mmm.util.lang.base.FormatterToString
   * 
   * @param formatter is the new {@link Formatter} to set.
   */
  void setFormatter(Formatter<VALUE> formatter);

}
