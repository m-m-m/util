/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.impl.GenericTypeImpl;
import net.sf.mmm.util.reflect.impl.TypeVariableImpl;

/**
 * This is the test for {@link ReflectionUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilTest {

  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtilImpl.getInstance();
  }

  private GenericType getReturnType(Type declaringType, String methodName) throws Exception {

    GenericType definingType = getReflectionUtil().createGenericType(declaringType);
    return getReturnType(definingType, methodName);
  }

  private GenericType getReturnType(GenericType definingType, String methodName) throws Exception {

    Class<?> declaringClass = definingType.getRetrievalClass();
    Method method = declaringClass.getMethod(methodName, ReflectionUtil.NO_PARAMETERS);
    return getReflectionUtil().createGenericType(method.getGenericReturnType(), definingType);
  }

  private Class<?> getReturnClass(Type declaringType, String methodName) throws Exception {

    GenericType type = getReturnType(declaringType, methodName);
    return type.getRetrievalClass();
  }

  private Class<?> getReturnClass(GenericType declaringType, String methodName) throws Exception {

    GenericType type = getReturnType(declaringType, methodName);
    return type.getRetrievalClass();
  }

  private Class getComponentType(Class<?> declaringClass, String methodName) throws Exception {

    GenericType type = getReturnType(declaringClass, methodName);
    return type.getComponentType().getRetrievalClass();
  }

  @Test
  public void testGenericTypeComponent() throws Exception {

    GenericType type;
    type = getReturnType(TestClass.class, "getStringArray").getComponentType();
    assertEquals(String.class, type.getType());
    assertEquals(String.class, type.getAssignmentClass());
    assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringList").getComponentType();
    assertEquals(String.class, type.getType());
    assertEquals(String.class, type.getAssignmentClass());
    assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringListUpperWildcard").getComponentType();
    assertEquals(String.class, type.getAssignmentClass());
    assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringListLowerWildcard").getComponentType();
    assertEquals(String.class, type.getAssignmentClass());
    assertEquals(Object.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getGenericStringArray").getComponentType();
    assertEquals(String.class, type.getAssignmentClass());
    assertEquals(String.class, type.getRetrievalClass());
    // the really hard ones...
    type = getReflectionUtil().createGenericType(
        ExParameterizedStringList.class.getGenericSuperclass()).getComponentType();
    assertEquals(String.class, type.getRetrievalClass());
    type = getReflectionUtil().createGenericType(StringList.class).getComponentType();
    assertEquals(String.class, type.getRetrievalClass());
  }

  private void checkTypeParser(String typeString) throws Exception {

    Type type = getReflectionUtil().toType(typeString);
    String toString = getReflectionUtil().toString(type);
    assertEquals(typeString, toString);
  }

  @Test
  public void testGenericTypeWithTypeVariable() throws Exception {

    assertEquals(Long.class, getReturnClass(TestClass.class, "getA"));
    assertEquals(Integer.class, getReturnClass(TestClass.class, "getB"));
    assertEquals(String.class, getReturnClass(TestClass.class, "getC"));

    // Collection<SubSubBar<? extends Byte>>
    GenericType subSubBarListType = getReturnType(TestClass.class, "getSubSubBarList");
    GenericType subSubBarType = subSubBarListType.getComponentType();
    Class<?> subSubBarClass = subSubBarType.getRetrievalClass();
    assertEquals(SubSubBar.class, subSubBarClass);
    assertEquals(Short.class, getReturnClass(subSubBarType, "getF"));
    assertEquals(Byte.class, getReturnClass(subSubBarType, "getG"));
    GenericType hList = getReturnType(subSubBarClass, "getH");
    assertEquals(List.class, hList.getRetrievalClass());
    assertEquals(Double.class, hList.getComponentType().getRetrievalClass());
  }

  @Test
  public void testGenericTypeAssignableFrom() throws Exception {

    GenericType subType;
    GenericType superType;

    subType = getReturnType(AssignableFromTestClass.class, "getMapSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getMapSuperType");
    assertTrue(subType.isAssignableFrom(subType));
    assertTrue(superType.isAssignableFrom(superType));
    assertTrue(superType.isAssignableFrom(subType));

    subType = getReturnType(AssignableFromTestClass.class, "getListSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getListSuperType");
    assertTrue(superType.isAssignableFrom(subType));

    subType = getReturnType(AssignableFromTestClass.class, "getBarSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getBarSuperType");
    assertTrue(superType.isAssignableFrom(subType));
    superType = getReturnType(AssignableFromTestClass.class, "getBarNoSuperType");
    assertFalse(superType.isAssignableFrom(subType));
  }

  @Test
  public void testGenericTypeCreate() throws Exception {

    ReflectionUtil util = getReflectionUtil();

    Type type;
    GenericType genericType;

    genericType = util.createGenericType(String.class);
    assertEquals(String.class, genericType.getType());
    assertEquals(String.class, genericType.getAssignmentClass());
    assertEquals(String.class, genericType.getRetrievalClass());
    assertEquals(0, genericType.getTypeArgumentCount());
    assertNull(genericType.getComponentType());

    type = util.toType("?");
    genericType = util.createGenericType(type);
    assertEquals(type, genericType.getType());
    assertEquals(Object.class, genericType.getAssignmentClass());
    assertEquals(Object.class, genericType.getRetrievalClass());
    assertEquals(0, genericType.getTypeArgumentCount());
    assertNull(genericType.getComponentType());

    type = util.toType("? extends java.lang.Integer");
    genericType = util.createGenericType(type);
    assertEquals(type, genericType.getType());
    assertEquals(Integer.class, genericType.getAssignmentClass());
    assertEquals(Integer.class, genericType.getRetrievalClass());
    assertEquals(0, genericType.getTypeArgumentCount());
    assertNull(genericType.getComponentType());

    type = util.toType("java.util.List<java.lang.String>");
    genericType = util.createGenericType(type);
    assertEquals(type, genericType.getType());
    assertEquals(List.class, genericType.getAssignmentClass());
    assertEquals(List.class, genericType.getRetrievalClass());
    assertEquals(1, genericType.getTypeArgumentCount());
    GenericType componentType = genericType.getComponentType();
    assertEquals(componentType, genericType.getTypeArgument(0));
    assertEquals(String.class, componentType.getType());
    assertEquals(String.class, componentType.getAssignmentClass());
    assertEquals(String.class, componentType.getRetrievalClass());
    assertEquals(0, componentType.getTypeArgumentCount());
    assertNull(componentType.getComponentType());
  }

  @Test
  public void testToType() throws Exception {

    assertEquals(String.class, getReflectionUtil().toType("java.lang.String"));
    checkTypeParser("?");
    checkTypeParser("? extends java.lang.String");
    checkTypeParser("? super java.lang.String");
    checkTypeParser("java.lang.String");
    checkTypeParser("java.util.List<java.lang.String>");
    checkTypeParser("java.util.Map<java.lang.String, java.lang.String>");
    checkTypeParser("java.util.List<?>");
    checkTypeParser("java.util.Map<? super java.lang.String, ? extends java.lang.String>");
    checkTypeParser("java.util.Map<java.util.List<java.lang.String>, java.util.Set<java.lang.String>>");
    checkTypeParser("?[]");
    checkTypeParser("java.util.List<?>[]");
  }

  @Test
  public void testFindResourceNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test resources
    Set<String> resourceNameSet;
    Filter<String> filter = new Filter<String>() {

      public boolean accept(String value) {

        if (value.endsWith(".xml")) {
          return true;
        }
        return false;
      }
    };
    resourceNameSet = util.findResourceNames("net.sf.mmm.util.reflect", true, filter);
    assertTrue(resourceNameSet.contains("net/sf/mmm/util/reflect/beans-util-reflect.xml"));
  }

  @Test
  public void testFindClassNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test directories
    Set<String> classNameSet = util.findClassNames(ReflectionUtilImpl.class.getPackage().getName(),
        false);
    assertTrue(classNameSet.contains(ReflectionUtilImpl.class.getName()));
    assertTrue(classNameSet.contains(ReflectionUtilTest.class.getName()));

    // test sub-package functionality
    assertFalse(classNameSet.contains(TypeVariableImpl.class.getName()));
    classNameSet = util.findClassNames(GenericTypeImpl.class.getPackage().getName(), true);
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

  public static class StringList extends ArrayList<String> {

  }

  public static class ParameterizedStringList<E> extends StringList {

  }

  public static class ExParameterizedStringList extends ParameterizedStringList<Integer> {

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

  public static class AssignableFromTestClass {

    public Map<? extends Number, ? extends CharSequence> getMapSuperType() {

      // legal downcast...
      return getMapSubType();
    }

    public HashMap<Integer, String> getMapSubType() {

      return new HashMap<Integer, String>();
    }

    public List<? super Integer> getListSuperType() {

      // legal downcast...
      return getListSubType();
    }

    public ArrayList<Number> getListSubType() {

      return new ArrayList<Number>();
    }

    // -------

    public Bar<Short, Number, Double> getBarSuperType() {

      // legal downcast...
      return getBarSubType();
    }

    public Bar<Long, Number, Double> getBarNoSuperType() {

      return null;
    }

    public SubSubBar<Number> getBarSubType() {

      return null;
    }
  }

}
