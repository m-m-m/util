/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;

import net.sf.mmm.util.reflect.type.TypeVariableImpl;

/**
 * This is the test for {@link ReflectionUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilTest {

  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtil.getInstance();
  }

  private Type getReturnType(Class<?> declaringClass, String methodName) throws Exception {

    Method method = declaringClass.getMethod(methodName, ReflectionUtil.NO_PARAMETERS);
    return method.getGenericReturnType();
  }

  private Class<?> getReturnClass(Class<?> declaringClass, String methodName) throws Exception {

    Type type = getReturnType(declaringClass, methodName);
    return getReflectionUtil().getClass(type, false, declaringClass);
  }

  private Class<?> getReturnClass(Type declaringType, String methodName) throws Exception {

    Class<?> declaringClass = getReflectionUtil().getClass(declaringType, false);
    Type type = getReturnType(declaringClass, methodName);
    return getReflectionUtil().getClass(type, false, declaringType);
  }

  private Class getComponentType(Class<?> declaringClass, String methodName) throws Exception {

    Type type = getReturnType(declaringClass, methodName);
    Type componentType = getReflectionUtil().getComponentType(type, false);
    return getReflectionUtil().getClass(componentType, false, TestClass.class);
  }

  @Test
  public void testComponentType() throws Exception {

    assertEquals(String.class, getComponentType(TestClass.class, "getStringArray"));
    assertEquals(String.class, getComponentType(TestClass.class, "getStringList"));
    assertEquals(String.class, getComponentType(TestClass.class, "getStringListUpperWildcard"));
    assertEquals(Object.class, getComponentType(TestClass.class, "getStringListLowerWildcard"));
    assertEquals(String.class, getComponentType(TestClass.class, "getGenericStringArray"));
    assertEquals(String.class, getComponentType(TestClass.class, "getStringArray"));
  }

  private void checkTypeParser(String typeString) throws Exception {

    Type type = getReflectionUtil().toType(typeString);
    String toString = getReflectionUtil().toString(type);
    assertEquals(typeString, toString);
  }

  @Test
  public void testGetClassWithTypeVariable() throws Exception {

    assertEquals(Long.class, getReturnClass(TestClass.class, "getA"));
    assertEquals(Integer.class, getReturnClass(TestClass.class, "getB"));
    assertEquals(String.class, getReturnClass(TestClass.class, "getC"));

    Type subSubBarListType = getReturnType(TestClass.class, "getSubSubBarList");
    Type subSubBarType = getReflectionUtil().getComponentType(subSubBarListType, true);
    Class<?> subSubBarClass = getReflectionUtil().getClass(subSubBarType);
    assertEquals(SubSubBar.class, subSubBarClass);
    assertEquals(Short.class, getReturnClass(subSubBarType, "getF"));
    assertEquals(Byte.class, getReturnClass(subSubBarType, "getG"));
    Type hList = getReturnType(subSubBarClass, "getH");
    assertEquals(List.class, getReflectionUtil().getClass(hList));
    Type hType = getReflectionUtil().getComponentType(hList, true);
    assertEquals(Double.class, getReflectionUtil().getClass(hType, false, subSubBarType));
  }

  @Test
  public void testGetClass() throws Exception {

    ReflectionUtil util = getReflectionUtil();

    assertEquals(String.class, util.getClass(String.class, false));

    Type type = util.toType("?");
    assertEquals(Object.class, util.getClass(type, false));

    type = util.toType("? extends java.lang.Integer");
    assertEquals(Integer.class, util.getClass(type, false));

    type = util.toType("java.util.List<java.lang.String>");
    assertEquals(List.class, util.getClass(type, false));
  }

  @Test
  public void testTypeParser() throws Exception {

    checkTypeParser("?");
    checkTypeParser("? extends java.lang.String");
    checkTypeParser("? super java.lang.String");
    checkTypeParser("java.lang.String");
    assertEquals(String.class, getReflectionUtil().toType("java.lang.String"));
    checkTypeParser("java.util.List<java.lang.String>");
    checkTypeParser("java.util.Map<java.lang.String, java.lang.String>");
    checkTypeParser("java.util.List<?>");
    checkTypeParser("java.util.Map<? super java.lang.String, ? extends java.lang.String>");
    checkTypeParser("java.util.Map<java.util.List<java.lang.String>, java.util.Set<java.lang.String>>");
    checkTypeParser("?[]");
    checkTypeParser("java.util.List<?>[]");
  }

  @Test
  public void testFindClassNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test directories
    Set<String> classNameSet = util.findClassNames(ReflectionUtil.class.getPackage().getName(),
        false);
    assertTrue(classNameSet.contains(ReflectionUtil.class.getName()));
    assertTrue(classNameSet.contains(ReflectionUtilTest.class.getName()));

    // test sub-package functionality
    assertFalse(classNameSet.contains(TypeVariableImpl.class.getName()));
    classNameSet = util.findClassNames(ReflectionUtil.class.getPackage().getName(), true);
    assertTrue(classNameSet.contains(TypeVariableImpl.class.getName()));

    // test JAR files
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), false);
    assertTrue(classNameSet.contains(Test.class.getName()));
    assertTrue(classNameSet.contains(Assert.class.getName()));

    // test sub-package functionality
    assertFalse(classNameSet.contains(Result.class.getName()));
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), true);
    assertTrue(classNameSet.contains(Result.class.getName()));
  }

  public static class Foo<A, B extends Number, C> {

    public A getA() {

      return null;
    }

    public B getB() {

      return null;
    }

    public C getC() {

      return null;
    }
  }

  public static class SubFoo<X, Y> extends Foo<X, Integer, Y> {

  }

  public static class SubSubFoo<E> extends SubFoo<Long, E> {

  }

  public static interface Bar<F, G, H> {

    F getF();

    G getG();

    List<H> getH();

  }

  public static interface SubBar<I, J> extends Bar<I, J, Double>, Cloneable, Serializable {

  }

  public static interface SubSubBar<K> extends SubBar<Short, K>, EventListener {

  }

  public static class TestClass extends SubSubFoo<String> {

    public Collection<SubSubBar<? extends Byte>> getSubSubBarList() {

      return null;
    }

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
