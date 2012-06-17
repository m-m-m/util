/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.lang.reflect.Type;

/**
 * This is the interface for generic conversion of values from a specific source-type (
 * <code>&lt;SOURCE&gt;</code>) to a given target-type ( <code>&lt;TARGET&gt;</code>).<br>
 * <b>ATTENTION:</b><br>
 * An implementation of this interface should be stateless and thread-safe.
 * 
 * @see ComposedValueConverter
 * @see StringValueConverter
 * 
 * @param <SOURCE> is the generic type of the values to convert.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface GenericValueConverter<SOURCE> {

  /**
   * This method converts the given <code>value</code> to the given <code>type</code>.
   * 
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type the <code>value</code> should be converted to.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT <code>null</code> but can NOT be
   *         converted to the given <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass)
      throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method converts the given <code>value</code> to the given <code>type</code>.
   * 
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type the <code>value</code> should be converted to. It is the raw-type of the
   *        given <code>targetType</code>.
   * @param targetType is the type to convert the <code>value</code> to. It is potentially generic and
   *        therefore contains more detailed information than <code>targetClass</code>. E.g. the
   *        <code>targetClass</code> may be <code>java.util.List</code> while this <code>targetType</code>
   *        could be <code>java.util.List&lt;Long&gt;</code>. This could help e.g. if the <code>value</code>
   *        is a string like <code>"2, 47, 4252525"</code>. The caller may supply the <code>targetClass</code>
   *        again here.
   * @return the <code>value</code> converted to <code>type</code>.
   * @throws ValueNotSetException if the given <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT <code>null</code> but can NOT be
   *         converted to the given <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, Type targetType)
      throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method converts the given <code>value</code> to the given <code>type</code>.
   * 
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type the <code>value</code> should be converted to.
   * @param defaultValue is returned if the given <code>value</code> is <code>null</code>. It may also be
   *        <code>null</code>.
   * @return the <code>value</code> converted to <code>type</code> or the <code>defaultValue</code> if
   *         <code>value</code> was <code>null</code>. It will only return <code>null</code> if both
   *         <code>value</code> and <code>defaultValue</code> are <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT <code>null</code> but can NOT be
   *         converted to the given <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, TARGET defaultValue)
      throws WrongValueTypeException;

  /**
   * This method converts the given <code>value</code> to the given <code>type</code>.
   * 
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type the <code>value</code> should be converted to. It is the raw-type of the
   *        given <code>targetType</code>.
   * @param targetType is the type to convert the <code>value</code> to. It is potentially generic and
   *        therefore contains more detailed information than <code>targetClass</code>. E.g. the
   *        <code>targetClass</code> may be <code>java.util.List</code> while this <code>targetType</code>
   *        could be <code>java.util.List&lt;Long&gt;</code>. This could help e.g. if the <code>value</code>
   *        is a string like <code>"2, 47, 4252525"</code>. The caller may supply the <code>targetClass</code>
   *        again here.
   * @param defaultValue is returned if the given <code>value</code> is <code>null</code>. It may also be
   *        <code>null</code>.
   * @return the <code>value</code> converted to <code>type</code> or the <code>defaultValue</code> if
   *         <code>value</code> was <code>null</code>. It will only return <code>null</code> if both
   *         <code>value</code> and <code>defaultValue</code> are <code>null</code>.
   * @throws WrongValueTypeException if the given <code>value</code> is NOT <code>null</code> but can NOT be
   *         converted to the given <code>type</code> (e.g. if <code>value</code> is "12x" and
   *         <code>type</code> is <code>Integer.class</code>).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, Type targetType,
      TARGET defaultValue) throws WrongValueTypeException;

  /**
   * This method converts the given <code>value</code> to a numeric type and also validates that it is in the
   * given range from <code>minimum</code> to <code>maximum</code>.
   * 
   * @param <TARGET> is the numeric-type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g. {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g. {@link Long#MAX_VALUE}) if unbound.
   * @return the requested value in the given range from <code>minimum</code> and <code>maximum</code>.
   * @throws ValueNotSetException if the given <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range from <code>minimum</code> to
   *         <code>maximum</code>.
   */
  <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum, TARGET maximum)
      throws ValueNotSetException, WrongValueTypeException, ValueOutOfRangeException;

  /**
   * This method gets a numeric value and also validates that it is in the given range from
   * <code>minimum</code> to <code>maximum</code>.
   * 
   * @param <TARGET> is the numeric-type to convert to.
   * @param value is the value to convert. It may be <code>null</code>.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g. {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g. {@link Long#MAX_VALUE}) if unbound.
   * @param defaultValue is the default returned if <code>value</code> is <code>null</code>. It may be
   *        <code>null</code>. Else it must be in the given range from <code>minimum</code> to
   *        <code>maximum</code>.
   * @return the given <code>value</code> converted to <code>&lt;TARGET&gt;</code> in the range from
   *         <code>minimum</code> to <code>maximum</code> or the <code>defaultValue</code> if
   *         <code>value</code> is <code>null</code>. Will only be <code>null</code> if both
   *         <code>value</code> and <code>defaultValue</code> are <code>null</code>.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range from <code>minimum</code> to
   *         <code>maximum</code>.
   */
  <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum, TARGET maximum,
      TARGET defaultValue) throws WrongValueTypeException, ValueOutOfRangeException;

}
