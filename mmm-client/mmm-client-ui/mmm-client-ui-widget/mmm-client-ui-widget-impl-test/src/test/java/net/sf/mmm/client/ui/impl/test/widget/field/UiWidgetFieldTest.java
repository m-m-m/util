/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCodeAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.impl.test.AbstractUiTest;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationStateImpl;

import org.junit.Assert;
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

    if (value1.equals(value2)) {
      throw new IllegalArgumentException("value1 (" + value1 + ") == value2 (" + value2 + ")");
    }

    // create widget and change handler...
    WIDGET widget = getContext().getWidgetFactory().create(widgetInterface);
    ValueChangeHandler<VALUE> handler = new ValueChangeHandler<VALUE>(widget);
    handler.assertNoEvent();

    // set value and verify...
    widget.setValue(value1);
    handler.assertOneEvent();
    handler.clear();
    VALUE widgetValue = widget.getValue();
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value1, widgetValue);
    verifyHasWidgetAdapterRecursive((AbstractUiWidget<?>) widget, false);
    VALUE adapterValue = getAdapterValue(widget);
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value1, adapterValue);
    verifyHasWidgetAdapterRecursive((AbstractUiWidget<?>) widget, true);

    // set value2 via adapter and verify...
    setAdapterValue(widget, value2);
    adapterValue = getAdapterValue(widget);
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value2, adapterValue);
    widgetValue = widget.getValue();
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value2, widgetValue);
    // TODO: in real live, if the user changed the value, a change event was triggered

    // set value back via widget (now with created adapter) and verify
    widget.setValue(value1);
    adapterValue = getAdapterValue(widget);
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value1, adapterValue);
    widgetValue = widget.getValue();
    Assert.assertNotNull(widgetValue);
    Assert.assertEquals(value1, widgetValue);
    handler.assertOneEvent();
    return widget;
  }

  /**
   * Test of {@link UiWidgetIntegerField}.
   */
  @Test
  public void testIntegerField() {

    UiWidgetIntegerField widget = checkWidget(UiWidgetIntegerField.class, Integer.valueOf(42), Integer.valueOf(24));
    widget.setMinimumValue(Integer.valueOf(-1));
    widget.setMaximumValue(Integer.valueOf(1));
    setAdapterValue(widget, Integer.valueOf(2));
    ValidationState state = new ValidationStateImpl();
    boolean success = widget.validate(state);
    Assert.assertFalse("Validation should fail", success);
  }

  /**
   * Test of {@link UiWidgetIntegerField}.
   */
  @Test
  public void testLongField() {

    UiWidgetLongField widget = checkWidget(UiWidgetLongField.class, Long.valueOf(1234567890L), Long.valueOf(1));
    widget.setMinimumValue(Long.valueOf(-1));
    widget.setMaximumValue(Long.valueOf(1));
    setAdapterValue(widget, Long.valueOf(2));
    ValidationState state = new ValidationStateImpl();
    boolean success = widget.validate(state);
    Assert.assertFalse("Validation should fail", success);
  }

  /**
   * Test of {@link UiWidgetTextField}.
   */
  @Test
  public void testTextField() {

    checkWidget(UiWidgetTextField.class, "foo", "bar");
  }

  /**
   * Test of {@link UiWidgetTextAreaField}.
   */
  @Test
  public void testTextAreaField() {

    checkWidget(UiWidgetTextAreaField.class, "line1\nline2", "bar");
  }

  /**
   * Test of {@link UiWidgetCodeAreaField}.
   */
  @Test
  public void testCodeAreaField() {

    checkWidget(UiWidgetCodeAreaField.class, "public class Foo {\n  private String bar;\n}", "bar");
  }
}
