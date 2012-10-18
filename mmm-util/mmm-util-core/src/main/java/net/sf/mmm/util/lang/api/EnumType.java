/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a particular <em>enumeration</em>. An enumeration has a finite set of
 * characteristics. This can be a java {@link Enum} but also something more complex such as a dynamic
 * enumeration that is configured via a database. For this reason it is called {@link EnumType} rather than
 * <code>EnumDatatype</code>. However, for convenience and simplicity it extends {@link Datatype}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EnumType<V> extends Datatype<V> {

  // nothing to add...

}
