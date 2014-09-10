/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a descriptor of a {@link DatatypeDescriptor#getSegment(Object, int) segment}.<br/>
 * E.g. the {@link DatatypeSegmentDescriptor} for {@link java.time.LocalDate#getYear()} behaves as described
 * by the following table:
 * <table border="1">
 * <tr>
 * <th>Method</th>
 * <th>Result</th>
 * </tr>
 * <tr>
 * <td>{@link #getName()}</td>
 * <td>"year"</td>
 * </tr>
 * <tr>
 * <td>{@link #getType()}</td>
 * <td>{@link Integer}.class</td>
 * </tr>
 * <tr>
 * <td>{@link #getSegment(Object) getSegment(x)}</td>
 * <td>x.{@link java.time.LocalDate#getYear() getYear()}</td>
 * </tr>
 * </table>
 *
 * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype} owning this segment.
 * @param <V> is the generic {@link #getType() type} of the {@link #getSegment(Object) segment value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public interface DatatypeSegmentDescriptor<T, V> {

  /**
   * The default {@link #getName() name} for the single segment of
   * {@link net.sf.mmm.util.lang.api.SimpleDatatype atomic datatypes}.
   */
  String DEFAULT_NAME = "value";

  /**
   * @return the name of the segment. For {@link SimpleDatatype} typically just {@link #DEFAULT_NAME} but
   *         significant for composite datatypes where the name is used as field names when the datatype is
   *         serialized to XML, JSON, a {@link java.util.Map} or whatever.
   */
  String getName();

  /**
   * @return the type of the segment. Should typically be a standard Java datatype such as {@link String},
   *         {@link Double}, {@link Long}, {@link Integer}, {@link Boolean}, {@link java.math.BigDecimal},
   *         etc. but may also be another custom {@link Datatype}.
   */
  Class<V> getType();

  /**
   * @return <code>true</code> if this segment is optional and can be omitted, <code>false</code> otherwise.
   *         Serialization is suggested to omit optional segments if their value is <code>null</code>.
   *         Further, {@link DatatypeDescriptor#create(Object...) creation} shall accept less arguments if any
   *         number of optional arguments are skipped what requires that
   *         {@link DatatypeDescriptor#getSegmentDescriptors() segment descriptors are ordered} such that
   *         optional segments are at the end of the list.
   */
  boolean isOptional();

  /**
   * Gets the value of the segment described by this object.
   *
   * @param datatype is the {@link Datatype} to disassemble.
   * @return the value of the segment described by this object.
   */
  V getSegment(T datatype);

}
