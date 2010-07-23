/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;
import net.sf.mmm.util.value.impl.pojo1.FooEnum;
import net.sf.mmm.util.value.impl.pojo1.MyPojoImpl;
import net.sf.mmm.util.value.impl.pojo1.SubPojo;
import net.sf.mmm.util.value.impl.pojo1.SubPojoImpl;
import net.sf.mmm.util.value.impl.pojo2.MyPojo;

import org.junit.Test;

/**
 * This is the test-case for {@link ComposedValueConverterImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ComposedValueConverterTest {

  protected ComposedValueConverter getComposedValueConverter() {

    ComposedValueConverterImpl converter = new DefaultComposedValueConverter();
    converter.initialize();
    return converter;
  }

  @Test
  public void testConverter() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Assert.assertEquals(Object.class, converter.getSourceType());
    Assert.assertEquals(Object.class, converter.getTargetType());
  }

  @Test
  public void testConvert2Integer() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to integer
    Integer i = Integer.valueOf(1234);
    value = converter.convert(i.toString(), valueSource, Integer.class);
    Assert.assertEquals(i, value);
    value = converter.convert(Long.valueOf(i.intValue()), valueSource, Integer.class);
    Assert.assertEquals(i, value);
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
    Assert.assertEquals(date, value);
    String dateString = Iso8601UtilImpl.getInstance().formatDateTime(date);
    value = converter.convert(dateString, valueSource, Date.class);
    Assert.assertEquals(date, value);
    value = converter.convert(Long.valueOf(date.getTime()), valueSource, Date.class);
    Assert.assertEquals(date, value);
  }

  @Test
  public void testConvert2Calendar() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Calendar value;
    String valueSource = "test-case";
    // convert to calendar
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    value = converter.convertValue(date, valueSource, Calendar.class);
    Assert.assertEquals(calendar, value);
    String calendarString = Iso8601UtilImpl.getInstance().formatDateTime(calendar);
    value = converter.convertValue(calendarString, valueSource, Calendar.class);
    Assert.assertEquals(calendar.getTime(), value.getTime());
  }

  @Test
  public void testConvert2String() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String value;
    String valueSource = "test-case";
    // convert to string
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = Iso8601UtilImpl.getInstance().formatDateTime(date);
    value = converter.convertValue(date, valueSource, String.class);
    Assert.assertEquals(dateString, value);
    value = converter.convertValue(String.class, valueSource, String.class);
    Assert.assertEquals(String.class.getName(), value);
  }

  @Test
  public void testConvert2Boolean() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Boolean value;
    String valueSource = "test-case";
    // convert to boolean
    value = converter.convertValue("true", valueSource, Boolean.class);
    Assert.assertEquals(Boolean.TRUE, value);
    boolean value2 = converter.convertValue("false", valueSource, boolean.class);
    Assert.assertFalse(value2);
  }

  @Test
  public void testConvert2Enum() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    String valueSource = "test-case";
    // convert to enum
    String valueString = converter.convertValue(TestEnum.SOME_ENUM_CONSTANT, valueSource,
        String.class);
    String someEnumConstant = "SomeEnumConstant";
    Assert.assertEquals(someEnumConstant, valueString);
    TestEnum value;
    value = converter.convertValue(someEnumConstant, valueSource, TestEnum.class);
    Assert.assertSame(TestEnum.SOME_ENUM_CONSTANT, value);
    value = converter.convertValue(TestEnum.SOME_ENUM_CONSTANT.name(), valueSource, TestEnum.class);
    Assert.assertSame(TestEnum.SOME_ENUM_CONSTANT, value);
  }

  @Test
  public void testConvert2Class() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Class value;
    String valueSource = "test-case";
    // convert to string
    value = converter.convertValue("java.lang.String", valueSource, Class.class);
    Assert.assertEquals(String.class, value);
    Type classOfNumber = Generic.class.getMethod("getClassOfNumber", ReflectionUtil.NO_PARAMETERS)
        .getGenericReturnType();
    Type classExtendsNumber = Generic.class.getMethod("getClassExtendsNumber",
        ReflectionUtil.NO_PARAMETERS).getGenericReturnType();
    value = converter.convertValue("java.lang.Number", valueSource, Class.class, classOfNumber);
    Assert.assertEquals(Number.class, value);
    value = converter
        .convertValue("java.lang.Number", valueSource, Class.class, classExtendsNumber);
    Assert.assertEquals(Number.class, value);
    value = converter.convertValue("java.lang.Integer", valueSource, Class.class,
        classExtendsNumber);
    Assert.assertEquals(Integer.class, value);
    value = converter
        .convertValue("java.lang.Double", valueSource, Class.class, classExtendsNumber);
    Assert.assertEquals(Double.class, value);
    // Class java.lang.Date does not match Class<? extends Number>
    try {
      value = converter
          .convertValue("java.util.Date", valueSource, Class.class, classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      ExceptionHelper.assertCause(e, NlsParseException.class);
    }
    // Class java.lang.Object does not match Class<? extends Number>
    try {
      value = converter.convertValue("java.lang.Object", valueSource, Class.class,
          classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      ExceptionHelper.assertCause(e, NlsParseException.class);
    }
    // "..." is no Class at all!
    try {
      value = converter.convertValue("...", valueSource, Class.class, classExtendsNumber);
      ExceptionHelper.failExceptionExpected();
    } catch (RuntimeException e) {
      // OK
      ExceptionHelper.assertCause(e, NlsParseException.class);
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
    Assert.assertNotNull(value);
    Assert.assertEquals(array.length, value.size());
    for (int index = 0; index < array.length; index++) {
      Assert.assertSame(array[index], value.get(index));
    }
    StringBuilder buffer = new StringBuilder();
    for (int index = 0; index < array.length; index++) {
      buffer.append(array[index]);
      if (index < array.length - 1) {
        buffer.append(',');
      }
    }
    value = converter.convertValue(buffer.toString(), valueSource, List.class);
    Assert.assertNotNull(value);
    Assert.assertEquals(array.length, value.size());
    for (int index = 0; index < array.length; index++) {
      Assert.assertEquals(array[index], value.get(index));
    }
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
    Assert.assertNotNull(value);
    Assert.assertEquals(array.length, value.length);
    for (int i = 0; i < array.length; i++) {
      Assert.assertSame(array[i], value[i]);
    }
    // convert 2-dimensional array
    Object[][] array2 = new Object[][] { new Object[] { s1, s2 }, new Object[] { s2 },
        new Object[0] };
    String[][] value2 = converter.convertValue(array2, valueSource, String[][].class);
    Assert.assertNotNull(value2);
    Assert.assertEquals(array2.length, value2.length);
    for (int i = 0; i < array2.length; i++) {
      Assert.assertEquals(array2[i].length, value2[i].length);
      for (int j = 0; j < array2[i].length; j++) {
        Assert.assertSame(array2[i][j], value2[i][j]);
      }
    }
    // convert primitive- to Object-array
    int[] intArray = new int[] { 42, 43, 44 };
    Integer[] integerArray = converter.convertValue(intArray, valueSource, Integer[].class);
    Assert.assertNotNull(integerArray);
    Assert.assertEquals(intArray.length, integerArray.length);
    for (int i = 0; i < intArray.length; i++) {
      Assert.assertEquals(intArray[i], integerArray[i].intValue());
    }
    // convert Object- to primitive-array
    int[] intArray2 = converter.convertValue(integerArray, valueSource, int[].class);
    Assert.assertNotNull(intArray2);
    Assert.assertEquals(intArray.length, intArray2.length);
    for (int i = 0; i < intArray.length; i++) {
      Assert.assertEquals(intArray[i], intArray2[i]);
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
    Assert.assertNotNull(result);
    List<net.sf.mmm.util.value.impl.pojo2.SubPojo> newSubPojos = result.getSubPojos();
    Assert.assertEquals(subPojos.size(), newSubPojos.size());
    for (int i = 0; i < subPojos.size(); i++) {
      SubPojo sp1 = subPojos.get(i);
      net.sf.mmm.util.value.impl.pojo2.SubPojo sp2 = newSubPojos.get(i);
      Assert.assertNotNull(sp2);
      Assert.assertEquals(Integer.valueOf(sp1.getInteger()), sp2.getInteger());
      Assert.assertEquals(sp1.getString(), sp2.getString());
      Assert.assertEquals(sp1.getFoo().name(), sp2.getFoo().name());
    }
  }

  protected void addConverter(ComposedValueConverterImpl composedConverter,
      ValueConverter<?, ?> subConverter) {

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

    Object value;
    String valueSource = "test-case";
    value = converter.convert("foo", valueSource, Integer.class);
    Assert.assertSame(ValueConverterToInteger.MAGIC, value);
    Long l = Long.valueOf(1234567890123456789L);
    value = converter.convert(l.toString(), valueSource, Long.class);
    Assert.assertEquals(l, value);
    value = converter.convert(new Foo(), valueSource, Integer.class);
    Assert.assertSame(ValueConverterFooToObject.MAGIC, value);
  }

  private static class ValueConverterToInteger extends
      AbstractSimpleValueConverter<Object, Integer> {

    public static final Integer MAGIC = Integer.valueOf(4242);

    public Class<Object> getSourceType() {

      return Object.class;
    }

    public Class<Integer> getTargetType() {

      return Integer.class;
    }

    public Integer convert(Object value, Object valueSource, Class<? extends Integer> targetClass) {

      if ((value != null) && (value instanceof String)) {
        return MAGIC;
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

    /**
     * {@inheritDoc}
     */
    public Class<Foo> getSourceType() {

      return Foo.class;
    }

    /**
     * {@inheritDoc}
     */
    public Class<Object> getTargetType() {

      return Object.class;
    }

    /**
     * {@inheritDoc}
     */
    public Object convert(Foo value, Object valueSource, Class<? extends Object> targetClass) {

      if ((value != null) && (Integer.class.equals(targetClass))) {
        return MAGIC;
      }
      return null;
    }
  }

  private static enum TestEnum {
    SOME_ENUM_CONSTANT,

    OTHER_CONSTANT
  }
}
