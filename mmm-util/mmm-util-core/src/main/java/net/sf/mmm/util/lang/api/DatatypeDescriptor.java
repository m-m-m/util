/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.List;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface to describe a {@link Datatype}. Allows to {@link #getSegment(Object, int)
 * disassemble} a (composite) datatype into its segments as well as to {@link #create(Object...) assemble} a
 * {@link Datatype} from its segments.
 *
 * @param <T> is the generic type of the described {@link Datatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@ComponentSpecification(plugin = true)
// @ThreadSafe
public interface DatatypeDescriptor<T> {

  /**
   * @return the {@link Class} reflecting the {@link Datatype} described by this object.
   */
  Class<T> getDatatype();

  /**
   * Gets the {@link List} of {@link DatatypeSegmentDescriptor}s the {@link #getDatatype() described datatype}
   * is composed of.<br/>
   * E.g. a {@link java.time.LocalDate} is composed out of three segments all of the
   * {@link DatatypeSegmentDescriptor#getType() type} {@link Integer}: year, month, and day.
   *
   * @return the {@link List} of {@link DatatypeSegmentDescriptor}s.
   */
  List<DatatypeSegmentDescriptor<T, ?>> getSegmentDescriptors();

  /**
   * Gets a single segment of the given {@link Datatype}.
   *
   * @see #getSegmentDescriptors()
   *
   * @param datatype is the {@link Datatype} instance to disassemble.
   * @param index is the index of the segment to retrieve. Has to correspond to the {@link List#get(int) list
   *        index} of {@link #getSegmentDescriptors() segment descriptors}.
   * @return the atomic value of the requested segment. Has to correspond to
   *         {@link DatatypeSegmentDescriptor#getType()}.
   */
  Object getSegment(T datatype, int index);

  /**
   * This method creates a new instance of the described {@link #getDatatype() datatype} from the individual
   * {@link #getSegment(Object, int) segments}.
   *
   * @param segments are the {@link #getSegment(Object, int) segment values} in the order given by
   *        {@link #getSegmentDescriptors()}. {@link DatatypeSegmentDescriptor#isOptional() Optional} segments
   *        may be omitted (from the end of the list). The type of each segment has to match the corresponding
   *        {@link DatatypeSegmentDescriptor#getType() segment type}.
   * @return the new datatype instance.
   */
  T create(Object... segments);

}
