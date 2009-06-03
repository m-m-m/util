/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

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
    Object value;
    String valueSource = "test-case";
    // convert to calendar
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    value = converter.convert(date, valueSource, Calendar.class);
    Assert.assertEquals(calendar, value);
    String calendarString = Iso8601UtilImpl.getInstance().formatDateTime(calendar);
    value = converter.convert(calendarString, valueSource, Calendar.class);
    Assert.assertEquals(calendar.getTime(), ((Calendar) value).getTime());
  }

  @Test
  public void testConvert2String() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to string
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = Iso8601UtilImpl.getInstance().formatDateTime(date);
    value = converter.convert(date, valueSource, String.class);
    Assert.assertEquals(dateString, value);
    value = converter.convert(String.class, valueSource, String.class);
    Assert.assertEquals(String.class.getName(), value);
  }

  @Test
  public void testConvert2Enum() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to enum
    value = converter.convert(TestEnum.SOME_ENUM_CONSTANT, valueSource, String.class);
    String someEnumConstant = "SomeEnumConstant";
    Assert.assertEquals(someEnumConstant, value);
    value = converter.convert(someEnumConstant, valueSource, TestEnum.class);
    Assert.assertSame(TestEnum.SOME_ENUM_CONSTANT, value);
    value = converter.convert(TestEnum.SOME_ENUM_CONSTANT.name(), valueSource, TestEnum.class);
    Assert.assertSame(TestEnum.SOME_ENUM_CONSTANT, value);
  }

  @Test
  public void testConvert2Collection() throws Exception {

    ComposedValueConverter converter = getComposedValueConverter();
    Object value;
    String valueSource = "test-case";
    // convert to collection
    String first = "first";
    String second = "2nd";
    String third = "3.";
    String[] array = new String[] { first, second, third };
    value = converter.convert(array, valueSource, List.class);
    Assert.assertNotNull(value);
    List list = (List) value;
    Assert.assertEquals(array.length, list.size());
    for (int index = 0; index < array.length; index++) {
      Assert.assertSame(array[index], list.get(index));
    }
    StringBuilder buffer = new StringBuilder();
    for (int index = 0; index < array.length; index++) {
      buffer.append(array[index]);
      if (index < array.length - 1) {
        buffer.append(',');
      }
    }
    value = converter.convert(buffer.toString(), valueSource, List.class);
    Assert.assertNotNull(value);
    list = (List) value;
    Assert.assertEquals(array.length, list.size());
    for (int index = 0; index < array.length; index++) {
      Assert.assertEquals(array[index], list.get(index));
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
