/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;

import static org.junit.Assert.*;

/**
 * This is the abstract test-case for implementations of
 * {@link net.sf.mmm.util.pojo.api.PojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilderTest {
  
  /**
   * This method checks read/write accessors to the property
   * <code>propertyName</code> of the <code>pojoDescriptor</code> according
   * to the given <code>readType</code> and <code>writeType</code>.
   * 
   * @param pojoDescriptor is the descriptor.
   * @param propertyName is the name of the property to check.
   * @param readType is the expected read-type or <code>null</code> if NOT to
   *        check.
   * @param writeType is the expected write-type or <code>null</code> if NOT
   *        to check.
   */
  protected void checkProperty(PojoDescriptor<?> pojoDescriptor, String propertyName,
      Class readType, Class writeType) {

    PojoPropertyDescriptor propertyDescriptor = pojoDescriptor.getPropertyDescriptor(propertyName);
    assertNotNull(propertyDescriptor);
    assertEquals(propertyName, propertyDescriptor.getName());
    PojoPropertyAccessor accessor;
    // test read accessor
    accessor = propertyDescriptor.getAccessor(PojoPropertyAccessMode.READ);
    if (readType == null) {
      assertNull(accessor);
    } else {
      assertEquals(propertyName, accessor.getName());
      assertEquals(readType, accessor.getPropertyClass());
    }
    // test write accessor
    accessor = propertyDescriptor.getAccessor(PojoPropertyAccessMode.WRITE);
    if (writeType == null) {
      assertNull(accessor);
    } else {
      assertEquals(propertyName, accessor.getName());
      assertEquals(writeType, accessor.getPropertyClass());
    }
  }

}
