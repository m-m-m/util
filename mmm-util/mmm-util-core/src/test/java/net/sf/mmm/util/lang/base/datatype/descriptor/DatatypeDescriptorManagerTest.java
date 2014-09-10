/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.util.List;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.lang.api.Comparator;
import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;

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

  @Test
  public void testSimpleDatatypeEnumComparator() {

    DatatypeDescriptorManager manager = getDatatypeDescriptorManager();
    DatatypeDescriptor<Comparator> descriptor = manager.getDatatypeDescriptor(Comparator.class);
    assertNotNull(descriptor);
    List<DatatypeSegmentDescriptor<Comparator, ?>> segments = descriptor.getSegmentDescriptors();
    assertEquals(1, segments.size());
    DatatypeSegmentDescriptor<Comparator, ?> segment = segments.get(0);
    assertNotNull(segment);
    assertEquals(String.class, segment.getType());
    assertEquals("value", segment.getName());
    assertEquals("<", segment.getSegment(Comparator.LESS_THAN));
  }

}
