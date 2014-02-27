/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;

/**
 * This is the interface for a <em>datatype</em>. A datatype is an object representing a value of a specific
 * type. It is typically <em>immutable</em> so it gets its value assigned at construction time and can then
 * not be modified anymore. Further, a datatype should ensure validation so it is NOT possible to create an
 * instance with an inconsistent value. Based on the {@link Datatype} a presentation layer can decide how to
 * view and how to edit the value. Therefore a structured data model should make use of custom datatypes in
 * order to be expressive.<br/>
 * Common generic datatypes are {@link String}, {@link Boolean}, {@link Long}, {@link Integer},
 * {@link java.math.BigDecimal}, {@link Double}, etc. They should always be accepted and supported as
 * datatypes (even though they do NOT implement this interface).<br/>
 * Please note that both {@link java.util.Date} and {@link java.util.Calendar} are mutable and have very
 * confusing APIs. Therefore, use JSR-310 or jodatime instead. <br/>
 * Even if a datatype is technically nothing but a {@link String} or a {@link Number} but logically something
 * special it is worth to define it as a dedicated datatype class already for the purpose of having a central
 * javadoc to explain it. On the other side avoid to introduce technical datatypes like <code>String32</code>
 * for a {@link String} with a maximum length of 32 characters as this is not adding value in the sense of a
 * real {@link Datatype}.<br/>
 * A datatype needs proper implementations of {@link #equals(Object)} and {@link #hashCode()}. It should
 * probably also be {@link Comparable}.<br/>
 * If a datatype is <em>mutable</em> this should be documented with an according reason (e.g. a Blob may allow
 * to append data as it would be inefficient to create a copy instead). In such case it is recommended to
 * declare a view interface that only declares methods to read (getters).<br/>
 * An implementation of this interface should NOT declare its {@link java.lang.reflect.Field}s as final in
 * order to be fully {@link java.io.Serializable}. Additionally, it should have a (protected) non-arg
 * {@link java.lang.reflect.Constructor}.<br/>
 * A regular implementation should be immutable and bind all fields at {@link java.lang.reflect.Constructor
 * construction}. It is suitable and in most cases also recommended to use the class implementing the datatype
 * as API omitting a dedicated interface if possible.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Datatype extends Serializable {

  /**
   * Returns the official {@link String} representation of this {@link Datatype}. While the general contract
   * of {@link Object#toString()} is very weak and mainly used for debugging, the contract here is very
   * strong. The returned {@link String} has to be suitable for end-users and official output to any kind of
   * sink.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is not supposed to do internationalization (I18N). See
   * {@link net.sf.mmm.util.nls.api.NlsMessage} for this purpose and implement
   * {@link net.sf.mmm.util.nls.api.NlsObject} if you want to support I18N/L10N.
   * 
   * @return the official {@link String} representation to display the value of this {@link Datatype}.
   */
  @Override
  String toString();

}
