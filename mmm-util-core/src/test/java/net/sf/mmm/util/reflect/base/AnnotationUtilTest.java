/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.junit.Test;

import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is the {@link TestCase} for the class {@link AnnotationUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AnnotationUtilTest {

  private static final String FOO_IF = "interface";

  private static final String FOO_CLASS = "Foo-class";

  private static final String BAR_CLASS = "Foo-class";

  protected AnnotationUtil getAnnotationUtil() {

    return AnnotationUtilImpl.getInstance();
  }

  @Test
  public void testMethodAnnotation() throws Exception {

    AnnotationUtil util = getAnnotationUtil();
    // annotation with retention "runtime" ?
    assertTrue(util.isRuntimeAnnotation(MyAnnotation.class));
    assertFalse(util.isRuntimeAnnotation(SuppressWarnings.class));
    assertTrue(util.isRuntimeAnnotation(Inherited.class));

    // annotation target type
    assertTrue(util.isAnnotationForType(MyAnnotation.class, ElementType.TYPE));
    assertTrue(getAnnotationUtil().isAnnotationForType(Inherited.class, ElementType.ANNOTATION_TYPE));
    assertFalse(util.isAnnotationForType(Inherited.class, ElementType.TYPE));

    // annotation inheritance
    MyAnnotation classAnnotation = util.getClassAnnotation(Bar.class, MyAnnotation.class);
    assertNotNull(classAnnotation);
    assertEquals(FOO_CLASS, classAnnotation.value());
    Method barMethod = Bar.class.getMethod("foo", ReflectionUtil.NO_PARAMETERS);
    MyAnnotation methodAnnotation = util.getMethodAnnotation(barMethod, MyAnnotation.class);
    assertNotNull(methodAnnotation);
    assertEquals(FOO_IF, methodAnnotation.value());

    assertNotNull(FooIF.class.getAnnotation(SomeAnnotation.class));

    SomeAnnotation typeAnnotation = util.getTypeAnnotation(Bar.class, SomeAnnotation.class);
    assertNotNull(typeAnnotation);
    assertEquals(FOO_IF, typeAnnotation.value());

  }

  @Retention(RetentionPolicy.RUNTIME)
  private @interface MyAnnotation {

    String value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  private @interface SomeAnnotation {

    String value();
  }

  @SomeAnnotation(FOO_IF)
  private static interface FooIF {

    @MyAnnotation(FOO_IF)
    void foo();

  }

  private static interface BarIF extends FooIF {

  }

  @MyAnnotation(FOO_CLASS)
  private static class Foo implements BarIF, Comparable<Foo> {

    public void foo() {

    }

    public int compareTo(Foo o) {

      return 0;
    }

  }

  private static class Bar extends Foo {

    public void foo() {

    }
  }

}
