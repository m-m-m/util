/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link ReflectionUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilTest extends TestCase {

  private Class getComponentType(String methodName) throws Exception {

    Method method = TestClass.class.getMethod(methodName, ReflectionUtil.NO_PARAMETERS);
    Type type = method.getGenericReturnType();
    return ReflectionUtil.getComponentType(type);
  }

  @Test
  public void testComponentType() throws Exception {

    assertEquals(String.class, getComponentType("getStringArray"));
    assertEquals(String.class, getComponentType("getStringList"));
    assertEquals(String.class, getComponentType("getStringListUpperWildcard"));
    assertEquals(String.class, getComponentType("getStringListLowerWildcard"));
    assertEquals(String.class, getComponentType("getGenericStringArray"));
  }

  public static class TestClass {

    public String[] getStringArray() {

      return null;
    }

    public List<String> getStringList() {

      return null;
    }

    public List<? extends String> getStringListUpperWildcard() {

      return null;
    }

    public List<? super String> getStringListLowerWildcard() {

      return null;
    }

    public <T extends String> T[] getGenericStringArray() {

      return null;
    }

  }

}
