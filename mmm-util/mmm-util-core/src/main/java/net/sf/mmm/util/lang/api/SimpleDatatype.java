/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the interface for a simple {@link Datatype}, that can be represented by a single standard
 * {@link #getValue() value}.<br/>
 * A legal implementation should have a {@link java.lang.reflect.Constructor} that is compatible with
 * {@link #getValue()}. An {@link EnumType} implementing this interface should also offer a static method
 * called <code>fromValue(V value)</code> that returns the appropriate {@link EnumType} instance or
 * <code>null</code> if no such instance exists. *
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface SimpleDatatype<V> extends Datatype, AttributeReadValue<V> {

  /**
   * This method returns the raw value of this datatype. This will typically be a standard datatype (like
   * {@link String}, {@link Character}, {@link Boolean}, any type of {@link Number}, any type of
   * {@link java.time.LocalDate}, etc.).
   * 
   * @return the value of this datatype.
   */
  @Override
  V getValue();

}
