/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.util.Date;
import java.util.List;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.Comparator;
import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link DatatypeDescriptorManager} or technically
 * {@link net.sf.mmm.util.lang.base.datatype.descriptor.DatatypeDescriptorManagerImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DatatypeDescriptorManagerTest extends Assert {

  /**
   * @return the {@link DatatypeDescriptorManager} to test.
   */
  public DatatypeDescriptorManager getDatatypeDescriptorManager() {

    return SpringContainerPool.getInstance().get(DatatypeDescriptorManager.class);
  }

  private <T> void checkSegment(DatatypeSegmentDescriptor<T, ?> segment, String name, Class<?> type, boolean optional,
      T instance, Object value) {

    assertNotNull(segment);
    assertEquals(name, segment.getName());
    assertSame(type, segment.getType());
    assertTrue(optional == segment.isOptional());
    assertNull(segment.getSegment(null));
    assertEquals(value, segment.getSegment(instance));
  }

  /**
   * Test of {@link Comparator} that is an {@link Enum} implementing
   * {@link net.sf.mmm.util.lang.api.SimpleDatatype}.
   */
  @Test
  public void testSimpleDatatypeEnumComparator() {

    // given
    DatatypeDescriptorManager manager = getDatatypeDescriptorManager();
    DatatypeDescriptor<Comparator> descriptor = manager.getDatatypeDescriptor(Comparator.class);
    // then
    assertNotNull(descriptor);
    // disassembly
    List<DatatypeSegmentDescriptor<Comparator, ?>> segments = descriptor.getSegmentDescriptors();
    assertEquals(1, segments.size());
    DatatypeSegmentDescriptor<Comparator, ?> segment = segments.get(0);
    checkSegment(segment, "value", String.class, false, Comparator.LESS_THAN, "<");
    assertEquals("<", descriptor.getSegment(Comparator.LESS_THAN, 0));
    assertNull(descriptor.getSegment(null, 0));
    // assembly
    Comparator comparator = descriptor.create("<");
    assertSame(Comparator.LESS_THAN, comparator);
    // full cycle
    for (Comparator c : Comparator.values()) {
      Object value = segment.getSegment(c);
      assertEquals(c.getValue(), value);
      assertSame(c, descriptor.create(value));
    }
  }

  /**
   * Test of pseudo-datatype {@link Date}.
   */
  @Test
  public void testDate() {

    DatatypeDescriptorManager manager = getDatatypeDescriptorManager();
    DatatypeDescriptor<Date> descriptor = manager.getDatatypeDescriptor(Date.class);
    // then
    assertNotNull(descriptor);
    // disassembly
    List<DatatypeSegmentDescriptor<Date, ?>> segments = descriptor.getSegmentDescriptors();
    assertEquals(6, segments.size());
    Date date = Iso8601UtilImpl.getInstance().parseDate("2013-12-31T23:59:58");
    Integer year = Integer.valueOf(2013);
    checkSegment(segments.get(0), "year", Integer.class, false, date, year);
    Integer month = Integer.valueOf(12);
    checkSegment(segments.get(1), "month", Integer.class, false, date, month);
    Integer day = Integer.valueOf(31);
    checkSegment(segments.get(2), "day", Integer.class, false, date, day);
    Integer hour = Integer.valueOf(23);
    checkSegment(segments.get(3), "hour", Integer.class, true, date, hour);
    Integer minute = Integer.valueOf(59);
    checkSegment(segments.get(4), "minute", Integer.class, true, date, minute);
    Integer second = Integer.valueOf(58);
    checkSegment(segments.get(5), "second", Integer.class, true, date, second);
    // assembly
    assertEquals(date, descriptor.create(year, month, day, hour, minute, second));
    assertEquals(Iso8601UtilImpl.getInstance().parseDate("2013-12-31T23:59:00"),
        descriptor.create(year, month, day, hour, minute));
    assertEquals(Iso8601UtilImpl.getInstance().parseDate("2013-12-31T23:00:00"),
        descriptor.create(year, month, day, hour));
    assertEquals(Iso8601UtilImpl.getInstance().parseDate("2013-12-31T00:00:00"), descriptor.create(year, month, day));

    try {
      descriptor.create(year, month);
      ExceptionHelper.failExceptionExpected();
    } catch (InstantiationFailedException e) {
      ExceptionHelper.assertCause(e, ValueOutOfRangeException.class);
    }
  }
}
