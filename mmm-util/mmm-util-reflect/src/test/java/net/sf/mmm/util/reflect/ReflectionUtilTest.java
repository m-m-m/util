/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the test for {@link ReflectionUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilTest {

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

  private void checkTypeParser(String typeString) throws Exception {
    
    Type type = ReflectionUtil.toType(typeString);
    String toString;
    if (type instanceof Class) {
      toString = ((Class) type).getName();
    } else {
      toString = type.toString();
    }
    assertEquals(typeString, toString);
  }
  
  @Test
  public void testTypeParser() throws Exception {
    
    checkTypeParser("java.lang.String");
    checkTypeParser("java.util.List<java.lang.String>");
    checkTypeParser("java.util.Map<java.lang.String, java.lang.String>");
    checkTypeParser("java.util.List<?>");
    checkTypeParser("java.util.Map<? super java.lang.String, ? extends java.lang.String>");
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
