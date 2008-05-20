/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;

import net.sf.mmm.util.reflect.base.AnnotationFilter;

/**
 * This is the test-case for the class {@link AnnotationFilter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AnnotationFilterTest {

  @Test
  public void testFilter() {

    AnnotationFilter filter = new AnnotationFilter(Resource.class);
    assertFalse(filter.accept(null));
    assertTrue(filter.accept(TestClass1.class));
    assertFalse(filter.accept(TestClass2.class));
    assertFalse(filter.accept(TestClass3.class));
    filter = new AnnotationFilter(Resource.class, true);
    assertTrue(filter.accept(TestClass1.class));
    assertFalse(filter.accept(TestClass2.class));
    assertTrue(filter.accept(TestClass3.class));
  }

  @Test
  public void testFilterFail() {

    try {
      AnnotationFilter filter = new AnnotationFilter(SuppressWarnings.class);
      fail("Exception expected");
    } catch (IllegalArgumentException e) {
    }
  }

  @Resource
  private static class TestClass1 {

  }

  private static class TestClass2 {

  }

  private static class TestClass3 extends TestClass1 {

  }

}
