/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract base implementation of {@link DatatypeDescriptor}.
 *
 * @param <T> is the generic type of the described {@link net.sf.mmm.util.lang.api.Datatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public abstract class AbstractDatatypeDescriptor<T> implements DatatypeDescriptor<T> {

  /** @see #getDatatype() */
  private final Class<T> datatype;

  /** @see #getSegmentDescriptors() */
  private final List<DatatypeSegmentDescriptor<T, ?>> segmentDescriptorList;

  /** The number of {@link #getSegment(Object, int) segments} that are required. */
  private final int requiredSegmentCount;

  /**
   * The constructor.
   *
   * @param datatype - see {@link #getDatatype()}.
   * @param segmentDescriptors - see {@link #getSegmentDescriptors()}.
   */
  @SafeVarargs
  public AbstractDatatypeDescriptor(Class<T> datatype, DatatypeSegmentDescriptor<T, ?>... segmentDescriptors) {

    super();
    this.datatype = datatype;
    this.segmentDescriptorList = Collections.unmodifiableList(Arrays.asList(segmentDescriptors));
    int requiredCount = segmentDescriptors.length;
    int i = 0;
    for (DatatypeSegmentDescriptor<T, ?> segment : segmentDescriptors) {
      if (segment.isOptional()) {
        if (requiredCount == segmentDescriptors.length) {
          requiredCount = i;
        }
      } else if (requiredCount != segmentDescriptors.length) {
        throw new IllegalArgumentException("segment[" + i + "] (" + segment.getName()
            + ") is required but previous segment was optional!");
      }
      i++;
    }
    this.requiredSegmentCount = requiredCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> getDatatype() {

    return this.datatype;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DatatypeSegmentDescriptor<T, ?>> getSegmentDescriptors() {

    return this.segmentDescriptorList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getSegment(T datatypeInstance, int index) {

    return this.segmentDescriptorList.get(index).getSegment(datatypeInstance);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T create(Object... segments) {

    try {
      NlsNullPointerException.checkNotNull("segments", segments);
      if (segments.length != this.segmentDescriptorList.size()) {
        if (this.requiredSegmentCount == this.segmentDescriptorList.size()) {
          throw new ObjectMismatchException(Integer.valueOf(segments.length),
              Integer.valueOf(this.segmentDescriptorList.size()), "segments.length");
        } else if (segments.length < this.requiredSegmentCount) {
          throw new ValueOutOfRangeException(Integer.valueOf(segments.length),
              Integer.valueOf(this.requiredSegmentCount), Integer.valueOf(this.segmentDescriptorList.size()),
              "segments.length");
        }
      }
      return doCreate(segments);
    } catch (Exception e) {
      throw new InstantiationFailedException(e, getDatatype());
    }
  }

  /**
   * Called from {@link #create(Object...)} after input validation has been performed. Implement this method
   * and do not worry about null values, wrong array size, etc.
   *
   * @param segments are the {@link #getSegment(Object, int) segment values}.
   * @return the new datatype instance.
   */
  protected abstract T doCreate(Object... segments);

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "DatatypeDescriptor for " + this.datatype.getName();
  }

}
