/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;

/**
 * This is the interface for a <em>datatype</em>. A datatype is an object representing a value of a specific
 * type with the following aspects:
 * <ul>
 * <li>It has a technical or business specific <em>semantic</em>.</li>
 * <li>Its JavaDoc explains the meaning and semantic of the value.</li>
 * <li>It is <em>immutable</em> and therefore stateless (its value assigned at construction time and can not
 * be modified).</li>
 * <li>It is {@link Serializable}.</li>
 * <li>It implements {@link #equals(Object)} and {@link #hashCode()} properly (two different instances with
 * the same value are {@link #equals(Object) equal} and have the same {@link #hashCode() hash}).</li>
 * <li>It shall ensure syntactical validation so it is NOT possible to create an instance with an invalid
 * value.</li>
 * <li>It is responsible for formatting its value to a {@link #toString() string representation} suitable for
 * sinks such as UI, loggers, etc. Also consider cases like a {@link Datatype} representing a password where
 * {@link #toString()} should return something like "********" instead of the actual password to prevent
 * security accidents.</li>
 * <li>It is responsible for parsing the value from other representations such as a {@link #toString() string}
 * (as needed).</li>
 * <li>It shall provide required logical operations on the value to prevent redundancies. Due to the immutable
 * attribute all manipulative operations have to return a new {@link Datatype} instance (see e.g.
 * {@link java.math.BigDecimal#add(java.math.BigDecimal)}).</li>
 * <li>It should implement {@link Comparable} if a natural order is defined.</li>
 * </ul>
 * Based on the {@link Datatype} a presentation layer can decide how to view and how to edit the value.
 * Therefore a structured data model should make use of custom datatypes in order to be expressive. <br>
 * Common generic datatypes are {@link String}, {@link Boolean}, {@link Number} and its subclasses,
 * {@link java.util.Currency}, etc. They should always be accepted and supported as datatypes (even though
 * they obviously do NOT implement this interface). <br>
 * Please note that both {@link java.util.Date} and {@link java.util.Calendar} are mutable and have very
 * confusing APIs. Therefore, use JSR-310 or jodatime instead. <br>
 * Even if a datatype is technically nothing but a {@link String} or a {@link Number} but logically something
 * special it is worth to define it as a dedicated datatype class already for the purpose of having a central
 * javadoc to explain it. On the other side avoid to introduce technical datatypes like <code>String32</code>
 * for a {@link String} with a maximum length of 32 characters as this is not adding value in the sense of a
 * real {@link Datatype}. <br>
 * An implementation of this interface should usually declare its {@link java.lang.reflect.Field}s as final to
 * ensure being immutable. However, we are supporting Google Web Toolkit (GWT) that has additional
 * requirements for serialization:
 * <ul>
 * <li>{@link java.lang.reflect.Field}s are NOT {@link java.lang.reflect.Modifier#isFinal(int) final}. See <a
 * href="http://code.google.com/p/google-web-toolkit/issues/detail?id=1054">GWT issue 1054</a>.</li>
 * <li>It has a protected non-arg {@link java.lang.reflect.Constructor}.</li>
 * </ul>
 * It is suitable and in most cases also recommended to use the class implementing the datatype as API
 * omitting a dedicated interface.
 * 
 * @see AbstractSimpleDatatype
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Datatype extends Serializable {

  /**
   * Returns the official {@link String} representation of this {@link Datatype}. While the general contract
   * of {@link Object#toString()} is very weak and mainly used for debugging, the contract here is very
   * strong. The returned {@link String} has to be suitable for end-users and official output to any kind of
   * sink. <br>
   * <b>ATTENTION:</b><br>
   * This method is not supposed to do internationalization (I18N). See
   * {@link net.sf.mmm.util.nls.api.NlsMessage} for this purpose and implement
   * {@link net.sf.mmm.util.nls.api.NlsObject} if you want to support I18N/L10N.
   * 
   * @return the official {@link String} representation to display the value of this {@link Datatype}.
   */
  @Override
  String toString();

}
