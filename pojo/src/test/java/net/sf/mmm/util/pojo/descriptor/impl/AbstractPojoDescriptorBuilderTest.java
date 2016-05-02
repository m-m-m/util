/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;

import org.junit.Assert;

/**
 * This is the abstract test-case for implementations of
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilderTest extends Assert {

  /**
   * This method checks read/write accessors to the property {@code propertyName} of the
   * {@code pojoDescriptor} according to the given {@code readType} and {@code writeType}.
   *
   * @param pojoDescriptor is the descriptor.
   * @param propertyName is the name of the property to check.
   * @param readType is the expected read-type or {@code null} if NOT to check.
   * @param writeType is the expected write-type or {@code null} if NOT to check.
   */
  protected void checkProperty(PojoDescriptor<?> pojoDescriptor, String propertyName, Class<?> readType,
      Class<?> writeType) {

    PojoPropertyDescriptor propertyDescriptor = pojoDescriptor.getPropertyDescriptor(propertyName);
    assertNotNull(propertyDescriptor);
    assertEquals(propertyName, propertyDescriptor.getName());

    // test read accessor
    PojoPropertyAccessorNonArg getAccessor = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
    if (readType == null) {
      assertNull(getAccessor);
    } else {
      assertNotNull(getAccessor);
      assertEquals(propertyName, getAccessor.getName());
      assertEquals(readType, getAccessor.getPropertyClass());
      assertEquals(readType, getAccessor.getPropertyType().getRetrievalClass());
      assertEquals(getAccessor.getPropertyType(), getAccessor.getReturnType());
      assertSame(getAccessor.getPropertyClass(), getAccessor.getReturnClass());
    }
    // test write accessor
    PojoPropertyAccessorOneArg setAccessor = propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
    if (writeType == null) {
      assertNull(setAccessor);
    } else {
      assertNotNull(setAccessor);
      assertEquals(propertyName, setAccessor.getName());
      assertEquals(writeType, setAccessor.getPropertyClass());
      assertEquals(writeType, setAccessor.getPropertyType().getAssignmentClass());
    }
  }

}
