/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a particular <em>enumeration</em>. An enumeration has a finite set of
 * characteristics. This can be a java {@link Enum} but also something more complex such as a dynamic
 * enumeration that is configured via a database. For this reason it is called {@link EnumType} rather than
 * {@code EnumDatatype}. However, for convenience and simplicity it extends {@link Datatype}. If you have an
 * {@link Enum} with a {@link EnumTypeWithCategory#getCategory() category} you will need to implement
 * {@link EnumTypeWithCategory} for according support. <br>
 * <b>NOTE:</b><br>
 * It is strongly recommended to use a {@link Datatype} or at least a transfer-object to represent the
 * enumeration instances. Using an {@link javax.persistence.Entity} as {@link EnumType} should be avoided.
 *
 * @param <V> is the generic type of the {@link #getValue() value}. This may be a simple datatype such as
 *        {@link String} or {@link Long} or this type itself implementing {@link EnumType}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EnumType<V> extends SimpleDatatype<V> {

  // nothing to add...

}
