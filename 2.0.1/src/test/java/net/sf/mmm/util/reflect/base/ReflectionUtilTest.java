/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

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

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.impl.GenericTypeImpl;
import net.sf.mmm.util.reflect.impl.TypeVariableImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;

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
    Assert.assertEquals(String.class, type.getType());
    Assert.assertEquals(String.class, type.getAssignmentClass());
    Assert.assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringList").getComponentType();
    Assert.assertEquals(String.class, type.getType());
    Assert.assertEquals(String.class, type.getAssignmentClass());
    Assert.assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringListUpperWildcard").getComponentType();
    Assert.assertEquals(String.class, type.getAssignmentClass());
    Assert.assertEquals(String.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getStringListLowerWildcard").getComponentType();
    Assert.assertEquals(String.class, type.getAssignmentClass());
    Assert.assertEquals(Object.class, type.getRetrievalClass());
    type = getReturnType(TestClass.class, "getGenericStringArray").getComponentType();
    Assert.assertEquals(String.class, type.getAssignmentClass());
    Assert.assertEquals(String.class, type.getRetrievalClass());
    // the really hard ones...
    type = getReflectionUtil().createGenericType(
        ExParameterizedStringList.class.getGenericSuperclass()).getComponentType();
    Assert.assertEquals(String.class, type.getRetrievalClass());
    type = getReflectionUtil().createGenericType(StringList.class).getComponentType();
    Assert.assertEquals(String.class, type.getRetrievalClass());

    // test map key
    type = getReturnType(AssignableFromTestClass.class, "getMapSubType");
    Assert.assertEquals(String.class, type.getComponentType().getRetrievalClass());
    Assert.assertEquals(Integer.class, type.getKeyType().getRetrievalClass());
  }

  private void checkTypeParser(String typeString) throws Exception {

    Type type = getReflectionUtil().toType(typeString);
    String toString = getReflectionUtil().toString(type);
    Assert.assertEquals(typeString, toString);
  }

  @Test
  public void testGenericTypeWithTypeVariable() throws Exception {

    Assert.assertEquals(Long.class, getReturnClass(TestClass.class, "getA"));
    Assert.assertEquals(Integer.class, getReturnClass(TestClass.class, "getB"));
    Assert.assertEquals(String.class, getReturnClass(TestClass.class, "getC"));

    // Collection<SubSubBar<? extends Byte>>
    GenericType subSubBarListType = getReturnType(TestClass.class, "getSubSubBarList");
    GenericType subSubBarType = subSubBarListType.getComponentType();
    Class<?> subSubBarClass = subSubBarType.getRetrievalClass();
    Assert.assertEquals(SubSubBar.class, subSubBarClass);
    Assert.assertEquals(Short.class, getReturnClass(subSubBarType, "getF"));
    Assert.assertEquals(Byte.class, getReturnClass(subSubBarType, "getG"));
    GenericType hList = getReturnType(subSubBarClass, "getH");
    Assert.assertEquals(List.class, hList.getRetrievalClass());
    Assert.assertEquals(Double.class, hList.getComponentType().getRetrievalClass());
  }

  @Test
  public void testGenericTypeAssignableFrom() throws Exception {

    GenericType subType;
    GenericType superType;

    subType = getReturnType(AssignableFromTestClass.class, "getMapSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getMapSuperType");
    Assert.assertTrue(subType.isAssignableFrom(subType));
    Assert.assertTrue(superType.isAssignableFrom(superType));
    Assert.assertTrue(superType.isAssignableFrom(subType));

    subType = getReturnType(AssignableFromTestClass.class, "getListSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getListSuperType");
    Assert.assertTrue(superType.isAssignableFrom(subType));

    subType = getReturnType(AssignableFromTestClass.class, "getBarSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getBarSuperType");
    Assert.assertTrue(superType.isAssignableFrom(subType));
    superType = getReturnType(AssignableFromTestClass.class, "getBarNoSuperType");
    Assert.assertFalse(superType.isAssignableFrom(subType));
  }

  @Test
  public void testGenericTypeCreate() throws Exception {

    ReflectionUtil util = getReflectionUtil();

    Type type;
    GenericType genericType;

    genericType = util.createGenericType(String.class);
    Assert.assertEquals(String.class, genericType.getType());
    Assert.assertEquals(String.class, genericType.getAssignmentClass());
    Assert.assertEquals(String.class, genericType.getRetrievalClass());
    Assert.assertEquals(0, genericType.getTypeArgumentCount());
    Assert.assertNull(genericType.getComponentType());

    type = util.toType("?");
    genericType = util.createGenericType(type);
    Assert.assertEquals(type, genericType.getType());
    Assert.assertEquals(Object.class, genericType.getAssignmentClass());
    Assert.assertEquals(Object.class, genericType.getRetrievalClass());
    Assert.assertEquals(0, genericType.getTypeArgumentCount());
    Assert.assertNull(genericType.getComponentType());

    type = util.toType("? extends java.lang.Integer");
    genericType = util.createGenericType(type);
    Assert.assertEquals(type, genericType.getType());
    Assert.assertEquals(Integer.class, genericType.getAssignmentClass());
    Assert.assertEquals(Integer.class, genericType.getRetrievalClass());
    Assert.assertEquals(0, genericType.getTypeArgumentCount());
    Assert.assertNull(genericType.getComponentType());

    type = util.toType("java.util.List<java.lang.String>");
    genericType = util.createGenericType(type);
    Assert.assertEquals(type, genericType.getType());
    Assert.assertEquals(List.class, genericType.getAssignmentClass());
    Assert.assertEquals(List.class, genericType.getRetrievalClass());
    Assert.assertEquals(1, genericType.getTypeArgumentCount());
    GenericType componentType = genericType.getComponentType();
    Assert.assertEquals(componentType, genericType.getTypeArgument(0));
    Assert.assertEquals(String.class, componentType.getType());
    Assert.assertEquals(String.class, componentType.getAssignmentClass());
    Assert.assertEquals(String.class, componentType.getRetrievalClass());
    Assert.assertEquals(0, componentType.getTypeArgumentCount());
    Assert.assertNull(componentType.getComponentType());
  }

  @Test
  public void testToType() throws Exception {

    Assert.assertEquals(String.class, getReflectionUtil().toType("java.lang.String"));
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
    resourceNameSet = util.findResourceNames("net.sf.mmm.util", false, filter);
    Assert.assertFalse(resourceNameSet.contains("net/sf/mmm/util/text/hyphenation.xml"));
    resourceNameSet = util.findResourceNames("net.sf.mmm.util.text", false, filter);
    Assert.assertTrue(resourceNameSet.contains("net/sf/mmm/util/text/hyphenation.xml"));
    resourceNameSet = util.findResourceNames("net.sf.mmm.util", true, filter);
    Assert.assertTrue(resourceNameSet.contains("net/sf/mmm/util/text/hyphenation.xml"));
  }

  @Test
  public void testFindClassNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test directories
    Set<String> classNameSet = util.findClassNames(ReflectionUtilImpl.class.getPackage().getName(),
        false);
    Assert.assertTrue(classNameSet.contains(ReflectionUtilImpl.class.getName()));
    Assert.assertTrue(classNameSet.contains(ReflectionUtilTest.class.getName()));

    // test sub-package functionality
    Assert.assertFalse(classNameSet.contains(TypeVariableImpl.class.getName()));
    classNameSet = util.findClassNames(GenericTypeImpl.class.getPackage().getName(), true);
    Assert.assertTrue(classNameSet.contains(TypeVariableImpl.class.getName()));

    // test JAR files
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), false);
    Assert.assertTrue(classNameSet.contains(Test.class.getName()));
    Assert.assertTrue(classNameSet.contains(Assert.class.getName()));

    // test sub-package functionality
    Assert.assertFalse(classNameSet.contains(Result.class.getName()));
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), true);
    Assert.assertTrue(classNameSet.contains(Result.class.getName()));

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
