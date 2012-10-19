/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.lang.api.Formatter;

/**
 * This interface gives read access to the {@link #getFormatter() formatter} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the value to pass to the {@link #getFormatter() formatter}.
 */
public abstract interface AttributeReadFormatter<VALUE> {

  /**
   * This method gets the <em>{@link Formatter}</em> of this object. It is used to
   * {@link Formatter#format(Object) format} a value of this object so it can be displayed to the end-user.
   * 
   * @return the {@link Formatter}.
   */
  Formatter<VALUE> getFormatter();

}
