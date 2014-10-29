/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCodeAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetInstantField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetOptionsField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsVerticalField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRangeField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalTimeField;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.test.AbstractUiTest;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.value.api.Range;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

import org.junit.Test;

/**
 * This is the test-case for all kinds of {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField field
 * widgets}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFieldTest extends AbstractUiTest {

  /**
   * The constructor.
   * 
   * @param springConfig is the Spring XML config location or <code>null</code> to test without spring.
   */
  public UiWidgetFieldTest(String springConfig) {

    super(springConfig);
  }

  /**
   * Performs a number of checks to test the widget identified by <code>widgetInterface</code>.
   * 
   * @param <VALUE> is the type of the value managed by the widget.
   * @param <WIDGET> is the type of the {@link UiWidgetWithValue widget}.
   * @param widgetInterface is the Class reflecting the interface of the widget.
   * @param value1 is a test value.
   * @param value2 is another test value that differs from <code>value1</code>.
   * @return the widget that has been created and tested. May be used for additional invidiual tests.
   */
  protected <VALUE, WIDGET extends UiWidgetWithValue<VALUE> & UiWidgetNative> WIDGET checkWidget(
      Class<WIDGET> widgetInterface, VALUE value1, VALUE value2) {

    // create widget and check...
    WIDGET widget = getContext().getWidgetFactory().create(widgetInterface);
    checkWidget(widget, value1, value2);
    return widget;
  }

  /**
   * Performs a number of checks to test the widget identified by <code>widgetInterface</code>.
   * 
   * @param <VALUE> is the type of the value managed by the widget.
   * @param <WIDGET> is the type of the {@link UiWidgetWithValue widget}.
   * @param widget is the {@link UiWidgetWithValue widget} to test.
   * @param value1 is a test value.
   * @param value2 is another test value that differs from <code>value1</code>.
   * @return the change handler that has been registered in the widget.
   */
  protected <VALUE, WIDGET extends UiWidgetWithValue<VALUE>> ValueChangeHandler<VALUE> checkWidget(WIDGET widget,
      VALUE value1, VALUE value2) {

    return checkWidget(widget, value1, value2, value1, value2);
  }

  /**
   * Performs a number of checks to test the widget identified by <code>widgetInterface</code>.
   * 
   * @param <VALUE> is the type of the value managed by the widget.
   * @param <WIDGET> is the type of the {@link UiWidgetWithValue widget}.
   * @param widget is the {@link UiWidgetWithValue widget} to test.
   * @param value1 is a test value.
   * @param value2 is another test value that differs from <code>value1</code>.
   * @return the change handler that has been registered in the widget.
   */
  protected <VALUE, WIDGET extends UiWidgetWithValue<VALUE>> ValueChangeHandler<VALUE> checkWidgetWithStringAdapterValue(
      WIDGET widget, VALUE value1, VALUE value2) {

    return checkWidget(widget, value1, value2, value1.toString(), value2.toString());
  }

  /**
   * Performs a number of checks to test the widget identified by <code>widgetInterface</code>.
   * 
   * @param <VALUE> is the type of the value managed by the widget.
   * @param <WIDGET> is the type of the {@link UiWidgetWithValue widget}.
   * @param widget is the {@link UiWidgetWithValue widget} to test.
   * @param value1 is a test value.
   * @param value2 is another test value that differs from <code>value1</code>.
   * @param adapterValue1 is the {@link #getAdapterValue(UiWidgetWithValue) adapter value} corresponding to
   *        <code>value1</code>.
   * @param adapterValue2 is the {@link #getAdapterValue(UiWidgetWithValue) adapter value} corresponding to
   *        <code>value2</code>.
   * @return the change handler that has been registered in the widget.
   */
  protected <VALUE, WIDGET extends UiWidgetWithValue<VALUE>> ValueChangeHandler<VALUE> checkWidget(WIDGET widget,
      VALUE value1, VALUE value2, Object adapterValue1, Object adapterValue2) {

    if (value1.equals(value2)) {
      throw new IllegalArgumentException("value1 (" + value1 + ") == value2 (" + value2 + ")");
    }

    // and change handler...
    ValueChangeHandler<VALUE> handler = new ValueChangeHandler<VALUE>(widget);
    handler.assertNoEvent();

    // set value and verify...
    widget.setValue(value1);
    handler.assertOneEvent();
    handler.clear();
    VALUE widgetValue = widget.getValue();
    assertNotNull(widgetValue);
    assertEquals(value1, widgetValue);
    verifyHasWidgetAdapterRecursive((AbstractUiWidget<?>) widget, false);
    Object adapterValue = getAdapterValue(widget);
    assertEquals(adapterValue1, adapterValue);
    verifyHasWidgetAdapterRecursive((AbstractUiWidget<?>) widget, true);

    // set value2 via adapter and verify...
    setAdapterValue(widget, adapterValue2);
    adapterValue = getAdapterValue(widget);
    assertEquals(adapterValue2, adapterValue);
    widgetValue = widget.getValue();
    assertEquals(value2, widgetValue);
    // TODO: in real live, if the user changed the value, a change event was triggered

    // set value back via widget (now with created adapter) and verify
    widget.setValue(value1);
    adapterValue = getAdapterValue(widget);
    assertNotNull(widgetValue);
    assertEquals(adapterValue1, adapterValue);
    widgetValue = widget.getValue();
    assertNotNull(widgetValue);
    assertEquals(value1, widgetValue);
    handler.assertOneEvent();
    return handler;
  }

  /**
   * Sets the <code>min</code> and <code>max</code> values as range of the given <code>widget</code>. Then it
   * verifies that the given <code>value</code> that must be out of the range is properly validated by the
   * <code>widget</code>.
   * 
   * @param <VALUE> is the generic type of the <code>value</code>.
   * @param widget is the {@link UiWidgetRangeField} to test.
   * @param value is the value to set that has to be out of range.
   * @param min is the minimum range value.
   * @param max is the maximum range value.
   */
  protected <VALUE extends Number & Comparable<VALUE>> void checkRangeWidget(UiWidgetRangeField<VALUE> widget,
      VALUE value, VALUE min, VALUE max) {

    Range<VALUE> range = new Range<VALUE>(min, max);
    if (range.isContained(value)) {
      fail("Value " + value + " has has to be out of range " + range);
    }
    widget.setMinimumValue(min);
    widget.setMaximumValue(max);
    setAdapterValue(widget, value);
    ValidationStateImpl state = new ValidationStateImpl();
    boolean success = widget.validate(state);
    assertFalse("Validation should fail", success);
    assertEquals("1 ValidationFailure expected", 1, state.getFailureCount());
    List<ValidationFailure> failureList = state.getFailureList();
    ValidationFailure validationFailure = failureList.get(0);
    assertNotNull(validationFailure);
    assertEquals(ValueOutOfRangeException.MESSAGE_CODE, validationFailure.getCode());
  }

  /**
   * Test of {@link UiWidgetIntegerField}.
   */
  @Test
  public void testIntegerField() {

    UiWidgetIntegerField widget = checkWidget(UiWidgetIntegerField.class, Integer.valueOf(42), Integer.valueOf(24));
    checkRangeWidget(widget, Integer.valueOf(2), Integer.valueOf(-1), Integer.valueOf(1));
  }

  /**
   * Test of {@link UiWidgetIntegerSliderField}.
   */
  @Test
  public void testIntegerSliderField() {

    UiWidgetIntegerSliderField widget = checkWidget(UiWidgetIntegerSliderField.class, Integer.valueOf(42),
        Integer.valueOf(24));
    checkRangeWidget(widget, Integer.valueOf(2), Integer.valueOf(-1), Integer.valueOf(1));
  }

  /**
   * Test of {@link UiWidgetIntegerField}.
   */
  @Test
  public void testLongField() {

    UiWidgetLongField widget = checkWidget(UiWidgetLongField.class, Long.valueOf(1234567890L), Long.valueOf(1));
    checkRangeWidget(widget, Long.valueOf(2), Long.valueOf(-1), Long.valueOf(1));
  }

  /**
   * Test of {@link UiWidgetCheckboxField}.
   */
  @Test
  public void testCheckboxField() {

    // UiWidgetCheckboxField widget =
    checkWidget(UiWidgetCheckboxField.class, Boolean.TRUE, Boolean.FALSE);
  }

  /**
   * Test of {@link UiWidgetComboboxField}.
   */
  @Test
  public void testComboboxField() {

    UiWidgetComboboxField<LengthUnit> widget = getContext().getWidgetFactory().create(UiWidgetComboboxField.class);
    checkOptionsField(widget);
  }

  /**
   * Tests the given <code>widget</code> using {@link LengthUnit#values()} as options and testing some values.
   * 
   * @param widget is the widget to test.
   */
  private void checkOptionsField(UiWidgetOptionsField<LengthUnit> widget) {

    widget.setOptions(Arrays.asList(LengthUnit.values()));
    checkWidgetWithStringAdapterValue(widget, LengthUnit.PIXEL, LengthUnit.PERCENT);
  }

  /**
   * Test of {@link UiWidgetComboboxField#setAllowCustomInput(boolean)}.
   */
  @Test
  public void testComboboxFieldWithCustomInput() {

    UiWidgetComboboxField<String> widget = getContext().getWidgetFactory().create(UiWidgetComboboxField.class);
    String option1 = "foo";
    String option2 = "bar";
    widget.setOptions(Arrays.asList(option1, option2));
    checkWidget(widget, option1, option2);
    String customOption = "customOption";
    setAdapterValue(widget, customOption);
    ValidationStateImpl state = new ValidationStateImpl();
    String value = widget.getValueAndValidate(state);
    assertNull(value);
    assertFalse(state.isValid());
    assertEquals(1, state.getFailureCount());
    ValidationFailure validationFailure = state.getFailureList().get(0);
    assertNotNull(validationFailure);
    assertEquals(AbstractUiWidgetOptionsField.CODE_UNDEFINED_OPTION, validationFailure.getCode());
    widget.setAllowCustomInput(true);
    state = new ValidationStateImpl();
    value = widget.getValueAndValidate(state);
    assertEquals(customOption, value);
    assertTrue(state.isValid());
  }

  /**
   * Test of {@link UiWidgetDoubleField}.
   */
  @Test
  public void testDoubleField() {

    UiWidgetDoubleField widget = checkWidget(UiWidgetDoubleField.class, Double.valueOf(12.34567890D), Double.valueOf(1));
    checkRangeWidget(widget, Double.valueOf(2), Double.valueOf(-1), Double.valueOf(1));
  }

  /**
   * Test of {@link UiWidgetTextField}.
   */
  @Test
  public void testTextField() {

    checkWidget(UiWidgetTextField.class, "foo", "bar");
  }

  /**
   * Test of {@link UiWidgetPasswordField}.
   */
  @Test
  public void testPasswordField() {

    checkWidget(UiWidgetPasswordField.class, "secret", "password");
  }

  /**
   * Test of {@link UiWidgetLocalDateField}.
   */
  @Test
  public void testDateField() {

    LocalDate now = LocalDate.now();
    LocalDate old = LocalDate.of(2000, 12, 31);
    checkWidget(UiWidgetLocalDateField.class, now, old);
  }

  /**
   * Test of {@link UiWidgetInstantField}.
   */
  @Test
  public void testDateTimeField() {

    checkWidget(UiWidgetLocalTimeField.class, LocalTime.now(), LocalTime.of(23, 59, 59));
  }

  /**
   * Test of {@link UiWidgetTextAreaField}.
   */
  @Test
  public void testTextAreaField() {

    checkWidget(UiWidgetTextAreaField.class, "line1\nline2", "bar");
  }

  /**
   * Test of {@link UiWidgetRichTextField}.
   */
  @Test
  public void testRichTextField() {

    checkWidget(UiWidgetRichTextField.class, "<span>text<br>next</span>", "hi there");
  }

  /**
   * Test of {@link UiWidgetCodeAreaField}.
   */
  @Test
  public void testCodeAreaField() {

    checkWidget(UiWidgetCodeAreaField.class, "public class Foo {\n  private String bar;\n}", "bar");
  }

  /**
   * Test of {@link UiWidgetRadioButtonsField}.
   */
  @Test
  public void testRadiobuttonsField() {

    UiWidgetRadioButtonsField<LengthUnit> widget = getContext().getWidgetFactory()
        .create(UiWidgetRadioButtonsField.class);
    checkOptionsField(widget);
  }

  /**
   * Test of {@link UiWidgetRadioButtonsVerticalField}.
   */
  @Test
  public void testRadiobuttonsVerticalField() {

    UiWidgetRadioButtonsVerticalField<LengthUnit> widget = getContext().getWidgetFactory().create(
        UiWidgetRadioButtonsVerticalField.class);
    checkOptionsField(widget);
  }
}
