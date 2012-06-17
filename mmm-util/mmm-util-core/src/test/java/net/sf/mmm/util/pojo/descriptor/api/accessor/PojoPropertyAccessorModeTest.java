/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;

/**
 * This is the test-case for the class {@link PojoPropertyAccessorMode} and its subclasses.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoPropertyAccessorModeTest {

  @Test
  public void testModes() {

    assertEquals("get", PojoPropertyAccessorNonArgMode.GET.getName());
    assertEquals("get-size", PojoPropertyAccessorNonArgMode.GET_SIZE.getName());
    assertEquals("set", PojoPropertyAccessorOneArgMode.SET.getName());
    assertEquals("add", PojoPropertyAccessorOneArgMode.ADD.getName());
    assertEquals("remove", PojoPropertyAccessorOneArgMode.REMOVE.getName());
    assertEquals("get-mapped", PojoPropertyAccessorOneArgMode.GET_MAPPED.getName());
    assertEquals("set-mapped", PojoPropertyAccessorTwoArgMode.SET_MAPPED.getName());
    assertEquals("get-indexed", PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED.getName());
    assertEquals("set-indexed", PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED.getName());
  }

}
