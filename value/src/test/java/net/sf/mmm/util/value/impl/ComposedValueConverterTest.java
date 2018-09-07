/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.Assert;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;
import net.sf.mmm.util.value.impl.pojo1.FooEnum;
import net.sf.mmm.util.value.impl.pojo1.MyPojoImpl;
import net.sf.mmm.util.value.impl.pojo1.SubPojo;
import net.sf.mmm.util.value.impl.pojo1.SubPojoImpl;
import net.sf.mmm.util.value.impl.pojo2.MyPojo;

/**
 * This is the test-case for {@link ComposedValueConverterImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ComposedValueConverterTest extends Assertions {

  protected ComposedValueConverter getComposedValueConverter() {

    return DefaultComposedValueConverter.getInstance();
  }

  @Test
  public void testConverter() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    assertThat(converter.getSourceType()).isEqualTo(Object.class);
    assertThat(converter.getTargetType()).isEqualTo(Object.class);
  }

  @Test
  public void testConvert2Integer() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to integer
    Integer i = Integer.valueOf(1234);
    assertThat(converter.convert(i.toString(), valueSource, Integer.class)).isEqualTo(i);
    assertThat(converter.convert(Long.valueOf(i.intValue()), valueSource, Integer.class)).isEqualTo(i);
  }

  @Test
  public void testConvert2Date() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to date
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    value = converter.convert(calendar, valueSource, Date.class);
    assertThat(value).isEqualTo(date);
    String dateString = Iso8601UtilImpl.getInstance().formatDateTime(date);
    assertThat(converter.convert(dateString, valueSource, Date.class)).isEqualTo(date);
    assertThat(converter.convert(Long.valueOf(date.getTime()), valueSource, Date.class)).isEqualTo(date);
  }

  @Test
  public void testConvert2Calendar() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert to calendar
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    assertThat(converter.convertValue(date, valueSource, Calendar.class)).isEqualTo(calendar);
    String calendarString = Iso8601UtilImpl.getInstance().formatDateTime(calendar);
    assertThat(converter.convertValue(calendarString, valueSource, Calendar.class).getTime()).isEqualTo(calendar.getTime());
  }

  @Test
  public void testConvert2String() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert to string
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = Iso8601UtilImpl.getInstance().formatDateTime(date);
    assertThat(converter.convertValue(date, valueSource, String.class)).isEqualTo(dateString);
    assertThat(converter.convertValue(String.class, valueSource, String.class)).isEqualTo(String.class.getName());
  }

  @Test
  public void testConvert2Boolean() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Boolean value;
    String valueSource = "test-case";
    // convert to boolean
    value = converter.convertValue("true", valueSource, Boolean.class);
    assertThat(value).isEqualTo(Boolean.TRUE);
    boolean value2 = converter.convertValue("false", valueSource, boolean.class);
    Assert.assertFalse(value2);
  }

  @Test
  public void testConvert2Enum() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert to enum
    String valueString = converter.convertValue(TestEnum.SOME_ENUM_CONSTANT, valueSource, String.class);
    String someEnumConstant = "some-enum-constant";
    assertThat(valueString).isEqualTo(someEnumConstant);
    assertThat(converter.convertValue(someEnumConstant, valueSource, TestEnum.class)).isSameAs(TestEnum.SOME_ENUM_CONSTANT);
    assertThat(converter.convertValue(TestEnum.SOME_ENUM_CONSTANT.name(), valueSource, TestEnum.class)).isSameAs(TestEnum.SOME_ENUM_CONSTANT);
    assertThat(converter.convertValue("SomeEnumConstant", valueSource, TestEnum.class)).isSameAs(TestEnum.SOME_ENUM_CONSTANT);
  }

  @Test
  public void testConvert2Class() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert to string
    assertThat(converter.convertValue("java.lang.String", valueSource, Class.class)).isEqualTo(String.class);
    Type classOfNumber = Generic.class.getMethod("getClassOfNumber", ReflectionUtilLimited.NO_PARAMETERS).getGenericReturnType();
    Type classExtendsNumber = Generic.class.getMethod("getClassExtendsNumber", ReflectionUtilLimited.NO_PARAMETERS).getGenericReturnType();
    assertThat(converter.convertValue("java.lang.Number", valueSource, Class.class, classOfNumber)).isEqualTo(Number.class);
    assertThat(converter.convertValue("java.lang.Number", valueSource, Class.class, classExtendsNumber)).isEqualTo(Number.class);
    assertThat(converter.convertValue("java.lang.Integer", valueSource, Class.class, classExtendsNumber)).isEqualTo(Integer.class);
    assertThat(converter.convertValue("java.lang.Double", valueSource, Class.class, classExtendsNumber)).isEqualTo(Double.class);
    // Class java.lang.Date does not match Class<? extends Number>
    try {
      converter.convertValue("java.util.Date", valueSource, Class.class, classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      assertThat(e).isInstanceOf(NlsParseException.class);
    }
    // Class java.lang.Object does not match Class<? extends Number>
    try {
      converter.convertValue("java.lang.Object", valueSource, Class.class, classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      assertThat(e).isInstanceOf(NlsParseException.class);
    }
    // "..." is no Class at all!
    try {
      converter.convertValue("...", valueSource, Class.class, classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      assertThat(e).isInstanceOf(NlsParseException.class);
    }
  }

  @Test
  public void testConvert2Collection() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    List value;
    String valueSource = "test-case";
    // convert to collection
    String first = "first";
    String second = "2nd";
    String third = "3.";
    String[] array = new String[] { first, second, third };
    value = converter.convertValue(array, valueSource, List.class);
    assertThat(value).isNotNull().hasSize(array.length);
    for (int index = 0; index < array.length; index++) {
      assertThat(value.get(index)).isSameAs(array[index]);
    }
    StringBuilder buffer = new StringBuilder();
    for (int index = 0; index < array.length; index++) {
      buffer.append(array[index]);
      if (index < array.length - 1) {
        buffer.append(',');
      }
    }
    value = converter.convertValue(buffer.toString(), valueSource, List.class);
    assertThat(value).isNotNull().hasSize(array.length);
    for (int index = 0; index < array.length; index++) {
      assertThat(value.get(index)).isEqualTo(array[index]);
    }
  }

  @Test
  public void testConvert2Map() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Map<Integer, List<String>> value;
    Type genericType = TypeClass.class.getDeclaredField("mapOfInteger2StringList").getGenericType();
    String valueSource = "test-case";
    value = converter.convertValue("41=foo,<{[42=bar,thing]}>,43=some", valueSource, Map.class, genericType);
    assertThat(value).hasSize(3);
    List<String> list41 = value.get(41);
    assertThat(list41).hasSize(1);
    assertThat(list41.get(0)).isEqualTo("foo");
    List<String> list42 = value.get(42);
    assertThat(list42).hasSize(2);
    assertThat(list42.get(0)).isEqualTo("bar");
    assertThat(list42.get(1)).isEqualTo("thing");
    List<String> list43 = value.get(43);
    assertThat(list43).hasSize(1);
    assertThat(list43.get(0)).isEqualTo("some");
  }

  @Test
  public void testConvert2Array() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert array to array
    String s1 = "foo";
    String s2 = "bar";
    Object[] array = new Object[] { s1, s2 };
    String[] value = converter.convertValue(array, valueSource, String[].class);
    assertThat(value).isNotNull().hasSize(array.length);
    for (int i = 0; i < array.length; i++) {
      assertThat(value[i]).isSameAs(array[i]);
    }
    // convert 2-dimensional array
    Object[][] array2 = new Object[][] { new Object[] { s1, s2 }, new Object[] { s2 }, new Object[0] };
    String[][] value2 = converter.convertValue(array2, valueSource, String[][].class);
    assertThat(value2).isNotNull().hasSize(array2.length);
    for (int i = 0; i < array2.length; i++) {
      assertThat(value2[i]).hasSize(array2[i].length);
      for (int j = 0; j < array2[i].length; j++) {
        assertThat(value2[i][j]).isSameAs(array2[i][j]);
      }
    }
    // convert primitive- to Object-array
    int[] intArray = new int[] { 42, 43, 44 };
    Integer[] integerArray = converter.convertValue(intArray, valueSource, Integer[].class);
    assertThat(integerArray).isNotNull().hasSize(intArray.length);
    for (int i = 0; i < intArray.length; i++) {
      assertThat(integerArray[i]).isEqualTo(intArray[i]);
    }
    // convert Object- to primitive-array
    int[] intArray2 = converter.convertValue(integerArray, valueSource, int[].class);
    assertThat(intArray2).isNotNull().hasSize(intArray.length);
    for (int i = 0; i < intArray.length; i++) {
      assertThat(intArray2[i]).isEqualTo(intArray[i]);
    }

  }

  @Test
  public void testConvert2CompatiblePojo() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    MyPojoImpl pojo = new MyPojoImpl();
    List<SubPojo> subPojos = new ArrayList<SubPojo>();
    SubPojoImpl subPojo;
    subPojo = new SubPojoImpl();
    subPojo.setFoo(FooEnum.SOME_THING);
    subPojo.setInteger(42);
    subPojo.setString("Hello world!");
    subPojos.add(subPojo);
    subPojo = new SubPojoImpl();
    subPojo.setFoo(FooEnum.FOO_BAR);
    subPojo.setInteger(4711);
    subPojo.setString("Magic");
    subPojos.add(subPojo);
    pojo.setSubPojos(subPojos);
    MyPojo result = converter.convertValue(pojo, valueSource, MyPojo.class);
    assertThat(result).isNotNull();
    List<net.sf.mmm.util.value.impl.pojo2.SubPojo> newSubPojos = result.getSubPojos();
    assertThat(newSubPojos).hasSize(subPojos.size());
    for (int i = 0; i < subPojos.size(); i++) {
      SubPojo sp1 = subPojos.get(i);
      net.sf.mmm.util.value.impl.pojo2.SubPojo sp2 = newSubPojos.get(i);
      assertThat(sp2).isNotNull();
      assertThat(sp2.getInteger()).isEqualTo(Integer.valueOf(sp1.getInteger()));
      assertThat(sp2.getString()).isEqualTo(sp1.getString());
      assertThat(sp2.getFoo().name()).isEqualTo(sp1.getFoo().name());
    }
  }

  protected void addConverter(ComposedValueConverterImpl composedConverter, ValueConverter<?, ?> subConverter) {

    if (subConverter instanceof AbstractComponent) {
      ((AbstractComponent) subConverter).initialize();
    }
    composedConverter.addConverter(subConverter);
  }

  @Test
  public void testSelection() throws Exception {

    ComposedValueConverterImpl composedConverter = new ComposedValueConverterImpl();
    addConverter(composedConverter, new ValueConverterToNumber());
    addConverter(composedConverter, new ValueConverterToInteger());
    addConverter(composedConverter, new ValueConverterFooToObject());
    composedConverter.initialize();
    checkSelection(composedConverter);

    composedConverter = new ComposedValueConverterImpl();
    addConverter(composedConverter, new ValueConverterToInteger());
    addConverter(composedConverter, new ValueConverterToNumber());
    addConverter(composedConverter, new ValueConverterFooToObject());
    composedConverter.initialize();
    checkSelection(composedConverter);

    composedConverter = new ComposedValueConverterImpl();
    addConverter(composedConverter, new ValueConverterFooToObject());
    addConverter(composedConverter, new ValueConverterToInteger());
    addConverter(composedConverter, new ValueConverterToNumber());
    composedConverter.initialize();
    checkSelection(composedConverter);
  }

  protected void checkSelection(ComposedValueConverter converter) {

    String valueSource = "test-case";
    assertThat(converter.convert("foo", valueSource, Integer.class)).isSameAs(ValueConverterToInteger.MAGIC);
    Long l = Long.valueOf(1234567890123456789L);
    assertThat(converter.convert(l.toString(), valueSource, Long.class)).isEqualTo(l);
    assertThat(converter.convert(new Foo(), valueSource, Integer.class)).isSameAs(ValueConverterFooToObject.MAGIC);
  }

  private static class ValueConverterToInteger extends AbstractSimpleValueConverter<Object, Integer> {

    public static final Integer MAGIC = Integer.valueOf(4242);

    @Override
    public Class<Object> getSourceType() {

      return Object.class;
    }

    @Override
    public Class<Integer> getTargetType() {

      return Integer.class;
    }

    @Override
    public <T extends Integer> T convert(Object value, Object valueSource, Class<T> targetClass) {

      if ((value != null) && (value instanceof String)) {
        return (T) MAGIC;
      }
      return null;
    }

  }

  private static class Foo {

  }

  private static class Generic {

    public Class<Number> getClassOfNumber() {

      return Number.class;
    }

    public Class<? extends Number> getClassExtendsNumber() {

      return Integer.class;
    }
  }

  private static class ValueConverterFooToObject extends AbstractSimpleValueConverter<Foo, Object> {

    public static final Integer MAGIC = Integer.valueOf(42);

    @Override
    public Class<Foo> getSourceType() {

      return Foo.class;
    }

    @Override
    public Class<Object> getTargetType() {

      return Object.class;
    }

    @Override
    public <T> T convert(Foo value, Object valueSource, Class<T> targetClass) {

      if ((value != null) && (Integer.class.equals(targetClass))) {
        return (T) MAGIC;
      }
      return null;
    }
  }

  private static enum TestEnum {
    SOME_ENUM_CONSTANT,

    OTHER_CONSTANT
  }

  public static class TypeClass {

    private Map<Integer, List<String>> mapOfInteger2StringList;
  }
}
