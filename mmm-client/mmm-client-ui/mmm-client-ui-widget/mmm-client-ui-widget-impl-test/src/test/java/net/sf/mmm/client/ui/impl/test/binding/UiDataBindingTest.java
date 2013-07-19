/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.binding;

import java.util.List;
import java.util.Locale;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.impl.test.AbstractUiTest;
import net.sf.mmm.client.ui.impl.test.demo.transferobject.Address;
import net.sf.mmm.client.ui.impl.test.demo.widget.AddressForm;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

import org.junit.Test;

/**
 * This is the test-case for {@link UiDataBinding} and {@link UiWidgetField#getValue() value} and
 * {@link UiWidgetField#validate(net.sf.mmm.util.validation.api.ValidationState) validation} in realistic
 * scenarios using the {@link UiWidget} framework.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingTest extends AbstractUiTest {

  /**
   * The constructor.
   * 
   * @param springConfig
   */
  public UiDataBindingTest(String springConfig) {

    super(springConfig);
  }

  /**
   * Tests {@link net.sf.mmm.client.ui.base.binding.UiDataBindingPojo}.
   */
  @Test
  public void testDataBindingSimple() {

    UiContext context = getContext();
    String label = "Name";
    UiWidgetTextField textField = context.getWidgetFactory().createTextField(label);
    ValueChangeHandler<String> handler = new ValueChangeHandler<String>(textField);
    assertNotNull(textField);
    assertEquals(label, textField.getFieldLabel());

    assertNull(textField.getOriginalValue());
    assertNull(textField.getValue());
    assertTrue(textField.validate(null));
    assertFalse(textField.isModified());
    assertNull(textField.getValidationFailure());

    String validationFailure = "test failure!";
    textField.setValidationFailure(validationFailure);
    assertEquals(validationFailure, textField.getValidationFailure());

    String value = "The special value";
    assertEquals(0, handler.getEventCount());
    textField.setValue(value);
    assertFalse(textField.isModified());
    assertEquals(value, textField.getValue());
    assertNull(textField.getValidationFailure());
    handler.assertOneEvent();
  }

  /**
   * Tests {@link net.sf.mmm.client.ui.base.binding.UiDataBindingPojoComposite}.
   */
  @Test
  public void testDataBindingComplex() {

    UiContext context = getContext();
    AddressForm form = new AddressForm(context);
    verifyDataBindingComplex(form);
    assertFalse(hasWidgetAdapter(form));
    form = new AddressForm(context);
    UiWidgetMainWindow mainWindow = context.getWidgetFactory().getMainWindow();
    mainWindow.addChild(form);
    verifyDataBindingComplex(form);
    assertTrue(hasWidgetAdapter(form));
  }

  /**
   * @param form is the {@link AddressForm} to perform various tests on.
   */
  private void verifyDataBindingComplex(AddressForm form) {

    UiWidgetField<String> fieldCity = ((UiWidgetField<String>) form.getFieldCity());
    assertTrue(fieldCity.isMandatory());

    // add test handlers...
    ValueChangeHandler<Address> formChangeHandler = new ValueChangeHandler<Address>(form);
    ValueChangeHandler<String> cityChangeHandler = new ValueChangeHandler<String>(fieldCity);

    // test setting empty value
    form.setValue(null);
    formChangeHandler.assertOneEvent();
    cityChangeHandler.assertOneEvent();
    formChangeHandler.clear();
    // and getting empty bean back
    Address value = form.getValue();
    assertNotNull(value);
    assertEquals(new Address(), value);
    Address value2 = form.getValue();
    assertNotNull(value2);
    assertNotSame(value, value2);
    assertEquals(value, value2);

    // test validation, will fail as city is mandatory
    ValidationStateImpl validationState = new ValidationStateImpl();
    boolean success = form.validate(validationState);
    assertFalse(success);
    assertFalse(validationState.isValid());
    List<ValidationFailure> failureList = validationState.getFailureList();
    assertEquals(1, failureList.size());
    ValidationFailure failure = failureList.get(0);
    assertNotNull(failure);
    assertEquals(ValidatorMandatory.CODE, failure.getCode());
    String failureMessage = "The value has to be filled.";
    assertEquals(failureMessage, failure.getMessage(Locale.ROOT));
    assertEquals(failureMessage, fieldCity.getValidationFailure());
    assertEquals("city", failure.getSource());

    // fill mandatory fields and get value including validation
    String city = "Hometown";
    assertFalse(form.isModified());
    fieldCity.setValueForUser(city);
    assertTrue(form.isModified());

    if (form.hasWidgetAdapter()) {
      clearValidationFailureSetCount(form);
    }
    value = form.getValueAndValidate(null);
    assertNotNull(value);
    Address address = new Address();
    address.setCity(city);
    assertEquals(address, value);
    if (form.hasWidgetAdapter()) {
      assertValidationFailureSetCount(form, 1);
    }

    // fill various properties and verify data binding
    value = new Address();
    city = "City";
    String country = "Country";
    String houseNumber = "12a";
    PostalCode postalCode = new PostalCode("54321");
    String state = "State";
    String street = "Street";

    value.setCity(city);
    value.setCountry(country);
    value.setHouseNumber(houseNumber);
    value.setPostalCode(postalCode);
    value.setState(state);
    value.setStreet(street);

    form.setValue(value);
    assertFalse(form.isModified());
    value2 = form.getValue();
    assertNotSame(value, value2);
    assertEquals(value, value2);
    assertEquals(city, form.getFieldCity().getValue());
    assertEquals(houseNumber, form.getFieldHouseNumber().getValue());
    assertEquals(street, form.getFieldStreet().getValue());
    assertEquals(postalCode, form.getFieldZip().getValue());
    assertEquals(country, form.getFieldCountry().getValue());
  }
}
