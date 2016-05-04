/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.impl.GenericTypeImpl;
import net.sf.mmm.util.reflect.impl.TypeVariableImpl;

/**
 * This is the test for {@link ReflectionUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilTest extends Assertions {

  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtilImpl.getInstance();
  }

  private GenericType getReturnType(Type declaringType, String methodName) throws Exception {

    GenericType definingType = getReflectionUtil().createGenericType(declaringType);
    return getReturnType(definingType, methodName);
  }

  private GenericType getReturnType(GenericType definingType, String methodName) throws Exception {

    Class<?> declaringClass = definingType.getRetrievalClass();
    Method method = declaringClass.getMethod(methodName, ReflectionUtilLimited.NO_PARAMETERS);
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

  /**
   * Test of {@link ReflectionUtil#getNonPrimitiveType(Class)}.
   */
  @Test
  public void testGetNonPrimitiveType() {

    ReflectionUtil reflectionUtil = getReflectionUtil();
    // primitives...
    assertThat(reflectionUtil.getNonPrimitiveType(int.class)).isEqualTo(Integer.class);
    assertThat(reflectionUtil.getNonPrimitiveType(long.class)).isEqualTo(Long.class);
    assertThat(reflectionUtil.getNonPrimitiveType(double.class)).isEqualTo(Double.class);
    assertThat(reflectionUtil.getNonPrimitiveType(float.class)).isEqualTo(Float.class);
    assertThat(reflectionUtil.getNonPrimitiveType(short.class)).isEqualTo(Short.class);
    assertThat(reflectionUtil.getNonPrimitiveType(byte.class)).isEqualTo(Byte.class);
    assertThat(reflectionUtil.getNonPrimitiveType(char.class)).isEqualTo(Character.class);
    assertThat(reflectionUtil.getNonPrimitiveType(boolean.class)).isEqualTo(Boolean.class);
    assertThat(reflectionUtil.getNonPrimitiveType(void.class)).isEqualTo(Void.class);
    // objects...
    assertThat(reflectionUtil.getNonPrimitiveType(Object.class)).isEqualTo(Object.class);
    assertThat(reflectionUtil.getNonPrimitiveType(int[].class)).isEqualTo(int[].class);
  }

  @Test
  public void testGenericTypeComponent() throws Exception {

    GenericType type;
    type = getReturnType(TestClass.class, "getStringArray").getComponentType();
    assertThat(type.getType()).isEqualTo(String.class);
    assertThat(type.getAssignmentClass()).isEqualTo(String.class);
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);
    type = getReturnType(TestClass.class, "getStringList").getComponentType();
    assertThat(type.getType()).isEqualTo(String.class);
    assertThat(type.getAssignmentClass()).isEqualTo(String.class);
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);
    type = getReturnType(TestClass.class, "getStringListUpperWildcard").getComponentType();
    assertThat(type.getAssignmentClass()).isEqualTo(String.class);
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);
    type = getReturnType(TestClass.class, "getStringListLowerWildcard").getComponentType();
    assertThat(type.getAssignmentClass()).isEqualTo(String.class);
    assertThat(type.getRetrievalClass()).isEqualTo(Object.class);
    type = getReturnType(TestClass.class, "getGenericStringArray").getComponentType();
    assertThat(type.getAssignmentClass()).isEqualTo(String.class);
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);
    // the really hard ones...
    type = getReflectionUtil().createGenericType(ExParameterizedStringList.class.getGenericSuperclass())
        .getComponentType();
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);
    type = getReflectionUtil().createGenericType(StringList.class).getComponentType();
    assertThat(type.getRetrievalClass()).isEqualTo(String.class);

    // test map key
    type = getReturnType(AssignableFromTestClass.class, "getMapSubType");
    assertThat(type.getComponentType().getRetrievalClass()).isEqualTo(String.class);
    assertThat(type.getKeyType().getRetrievalClass()).isEqualTo(Integer.class);
    assertThat(type.toStringSimple()).isEqualTo("HashMap<Integer, String>");
    assertThat(type.toString()).isEqualTo("java.util.HashMap<java.lang.Integer, java.lang.String>");
  }

  @Test
  public void testGenericTypeWithTypeVariable() throws Exception {

    assertThat(getReturnClass(TestClass.class, "getA")).isEqualTo(Long.class);
    assertThat(getReturnClass(TestClass.class, "getB")).isEqualTo(Integer.class);
    assertThat(getReturnClass(TestClass.class, "getC")).isEqualTo(String.class);

    // Collection<SubSubBar<? extends Byte>>
    GenericType subSubBarListType = getReturnType(TestClass.class, "getSubSubBarList");
    GenericType subSubBarType = subSubBarListType.getComponentType();
    Class<?> subSubBarClass = subSubBarType.getRetrievalClass();
    assertThat(subSubBarClass).isEqualTo(SubSubBar.class);
    assertThat(getReturnClass(subSubBarType, "getF")).isEqualTo(Short.class);
    assertThat(getReturnClass(subSubBarType, "getG")).isEqualTo(Byte.class);
    GenericType hList = getReturnType(subSubBarClass, "getH");
    assertThat(hList.getRetrievalClass()).isEqualTo(List.class);
    assertThat(hList.getComponentType().getRetrievalClass()).isEqualTo(Double.class);
  }

  @Test
  public void testGenericTypeAssignableFrom() throws Exception {

    GenericType subType;
    GenericType superType;

    subType = getReturnType(AssignableFromTestClass.class, "getMapSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getMapSuperType");
    assertThat(subType.isAssignableFrom(subType)).isTrue();
    assertThat(superType.isAssignableFrom(superType)).isTrue();
    assertThat(superType.isAssignableFrom(subType)).isTrue();
    assertThat(superType.toStringSimple()).isEqualTo("Map<? extends Number, ? extends CharSequence>");
    assertThat(superType.toString())
        .isEqualTo("java.util.Map<? extends java.lang.Number, ? extends java.lang.CharSequence>");

    subType = getReturnType(AssignableFromTestClass.class, "getListSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getListSuperType");
    assertThat(superType.isAssignableFrom(subType)).isTrue();

    subType = getReturnType(AssignableFromTestClass.class, "getBarSubType");
    superType = getReturnType(AssignableFromTestClass.class, "getBarSuperType");
    assertThat(superType.isAssignableFrom(subType)).isTrue();
    superType = getReturnType(AssignableFromTestClass.class, "getBarNoSuperType");
    assertThat(superType.isAssignableFrom(subType)).isFalse();
  }

  @Test
  public void testGenericTypeCreate() throws Exception {

    ReflectionUtil util = getReflectionUtil();

    Type type;
    GenericType genericType;

    genericType = util.createGenericType(String.class);
    assertThat(genericType.getType()).isEqualTo(String.class);
    assertThat(genericType.getAssignmentClass()).isEqualTo(String.class);
    assertThat(genericType.getRetrievalClass()).isEqualTo(String.class);
    assertThat(genericType.getTypeArgumentCount()).isEqualTo(0);
    assertThat(genericType.getComponentType()).isNull();

    type = util.toType("?");
    genericType = util.createGenericType(type);
    assertThat(genericType.getType()).isEqualTo(type);
    assertThat(genericType.getAssignmentClass()).isEqualTo(Object.class);
    assertThat(genericType.getRetrievalClass()).isEqualTo(Object.class);
    assertThat(genericType.getTypeArgumentCount()).isEqualTo(0);
    assertThat(genericType.getComponentType()).isNull();

    type = util.toType("? extends java.lang.Integer");
    genericType = util.createGenericType(type);
    assertThat(genericType.getType()).isEqualTo(type);
    assertThat(genericType.getAssignmentClass()).isEqualTo(Integer.class);
    assertThat(genericType.getRetrievalClass()).isEqualTo(Integer.class);
    assertThat(genericType.getTypeArgumentCount()).isEqualTo(0);
    assertThat(genericType.getComponentType()).isNull();

    type = util.toType("java.util.List<java.lang.String>");
    genericType = util.createGenericType(type);
    assertThat(genericType.getType()).isEqualTo(type);
    assertThat(genericType.getAssignmentClass()).isEqualTo(List.class);
    assertThat(genericType.getRetrievalClass()).isEqualTo(List.class);
    assertThat(genericType.getTypeArgumentCount()).isEqualTo(1);
    GenericType componentType = genericType.getComponentType();
    assertThat(genericType.getTypeArgument(0)).isEqualTo(componentType);
    assertThat(componentType.getType()).isEqualTo(String.class);
    assertThat(componentType.getAssignmentClass()).isEqualTo(String.class);
    assertThat(componentType.getRetrievalClass()).isEqualTo(String.class);
    assertThat(componentType.getTypeArgumentCount()).isEqualTo(0);
    assertThat(componentType.getComponentType()).isNull();
  }

  @Test
  public void testGenericTypeCreateCollections() throws Exception {

    ReflectionUtil util = getReflectionUtil();

    GenericType<List<String>> listType = util.createGenericTypeOfList(util.createGenericType(String.class));
    assertThat(listType.getAssignmentClass()).isEqualTo(List.class);
    assertThat(listType.getRetrievalClass()).isEqualTo(List.class);
    assertThat(listType.getTypeArgumentCount()).isEqualTo(1);
    assertThat(listType.getTypeArgument(0).getRetrievalClass()).isEqualTo(String.class);
    assertThat(listType.getComponentType().getRetrievalClass()).isEqualTo(String.class);
    GenericType<Set<Long>> setType = util.createGenericTypeOfSet(util.createGenericType(Long.class));
    assertThat(setType.getAssignmentClass()).isEqualTo(Set.class);
    assertThat(setType.getRetrievalClass()).isEqualTo(Set.class);
    assertThat(setType.getTypeArgumentCount()).isEqualTo(1);
    assertThat(setType.getTypeArgument(0).getRetrievalClass()).isEqualTo(Long.class);
    assertThat(setType.getComponentType().getRetrievalClass()).isEqualTo(Long.class);
    GenericType<Map<Set<Long>, List<String>>> mapType = util.createGenericTypeOfMap(setType, listType);
    assertThat(mapType.getAssignmentClass()).isEqualTo(Map.class);
    assertThat(mapType.getRetrievalClass()).isEqualTo(Map.class);
    assertThat(mapType.getTypeArgumentCount()).isEqualTo(2);
    assertThat(mapType.getTypeArgument(0)).isEqualTo(setType);
    assertThat(mapType.getTypeArgument(1)).isEqualTo(listType);
    assertThat(mapType.getKeyType()).isEqualTo(setType);
    assertThat(mapType.getComponentType()).isEqualTo(listType);
  }

  @Test
  public void testToTypeAndToString() throws Exception {

    assertThat(getReflectionUtil().toType("java.lang.String")).isEqualTo(String.class);
    checkTypeParser("?", "?");
    checkTypeParser("? extends java.lang.String", "? extends String");
    checkTypeParser("? super java.lang.String", "? super String");
    checkTypeParser("java.lang.String", "String");
    checkTypeParser("java.util.List<java.lang.String>", "List<String>");
    checkTypeParser("java.util.Map<java.lang.String, java.lang.String>", "Map<String, String>");
    checkTypeParser("java.util.List<?>", "List<?>");
    checkTypeParser("java.util.Map<? super java.lang.String, ? extends java.lang.String>",
        "Map<? super String, ? extends String>");
    checkTypeParser("java.util.Map<java.util.List<java.lang.String>, java.util.Set<java.lang.String>>",
        "Map<List<String>, Set<String>>");
    checkTypeParser("?[]", "?[]");
    checkTypeParser("java.util.List<?>[]", "List<?>[]");
  }

  private void checkTypeParser(String typeString) throws Exception {

    checkTypeParser(typeString, null);
  }

  private void checkTypeParser(String typeString, String typeShort) throws Exception {

    ReflectionUtil util = getReflectionUtil();
    Type type = util.toType(typeString);
    assertThat(util.toString(type)).isEqualTo(typeString);
    if (typeShort != null) {
      assertThat(util.toStringSimple(type)).isEqualTo(typeShort);
    }
  }

  @Test
  public void testFindResourceNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test resources
    Set<String> resourceNameSet;
    Filter<String> filter = new Filter<String>() {

      @Override
      public boolean accept(String value) {

        if (value.endsWith(".xml")) {
          return true;
        }
        return false;
      }
    };
    String hyphenation = "net/sf/mmm/util/text/hyphenation.xml";
    resourceNameSet = util.findResourceNames("net.sf.mmm.util", false, filter);
    assertThat(resourceNameSet).doesNotContain(hyphenation);
    resourceNameSet = util.findResourceNames("net.sf.mmm.util.text", false, filter);
    assertThat(resourceNameSet).contains(hyphenation);
    resourceNameSet = util.findResourceNames("net.sf.mmm.util", true, filter);
    assertThat(resourceNameSet).contains(hyphenation);
  }

  @Test
  public void testFindClassNames() throws Exception {

    ReflectionUtil util = getReflectionUtil();
    // test directories
    Set<String> classNameSet = util.findClassNames(ReflectionUtilImpl.class.getPackage().getName(), false);
    assertThat(classNameSet).contains(ReflectionUtilImpl.class.getName()) //
        .contains(ReflectionUtilTest.class.getName()) //
        .doesNotContain(TypeVariableImpl.class.getName());

    // test sub-package functionality
    classNameSet = util.findClassNames(GenericTypeImpl.class.getPackage().getName(), true);
    assertThat(classNameSet).contains(TypeVariableImpl.class.getName());

    // test JAR files
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), false);
    assertThat(classNameSet).contains(Test.class.getName()) //
        .contains(Assert.class.getName()) //
        .doesNotContain(Result.class.getName());

    // test sub-package functionality
    classNameSet = util.findClassNames(Test.class.getPackage().getName(), true);
    assertThat(classNameSet).contains(Result.class.getName());

  }

  public static class StringList extends ArrayList<String> {

    /** TODO: javadoc. */
    private static final long serialVersionUID = 1L;

  }

  public static class ParameterizedStringList<E> extends StringList {

    /** TODO: javadoc. */
    private static final long serialVersionUID = 1L;

  }

  public static class ExParameterizedStringList extends ParameterizedStringList<Integer> {

    /** TODO: javadoc. */
    private static final long serialVersionUID = 1L;

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
