/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.binding;

import java.util.List;
import java.util.Locale;

import net.sf.mmm.client.ui.impl.test.AbstractUiTest;
import net.sf.mmm.client.ui.impl.test.demo.transferobject.Address;
import net.sf.mmm.client.ui.impl.test.demo.widget.AddressForm;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

import org.junit.Test;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingTest extends AbstractUiTest {

  /**
   * Tests {@link net.sf.mmm.client.ui.base.binding.UiDataBindingPojoComposite}.
   */
  @Test
  public void testDataBindingComplex() {

    AddressForm form = new AddressForm(getContext());
    // test setting empty value
    form.setValue(null);
    // and getting empty bean back
    Address value = form.getValue();
    assertNotNull(value);
    assertEquals(new Address(), value);
    Address value2 = form.getValue();
    assertNotNull(value2);
    assertNotSame(value, value2);
    assertEquals(value, value2);

    // test validation, will fail as city is mandatory
    ValidationStateImpl state = new ValidationStateImpl();
    boolean success = form.validate(state);
    assertFalse(success);
    assertFalse(state.isValid());
    List<ValidationFailure> failureList = state.getFailureList();
    assertEquals(1, failureList.size());
    ValidationFailure failure = failureList.get(0);
    assertNotNull(failure);
    assertEquals(ValidatorMandatory.CODE, failure.getCode());
    assertEquals("The value has to be filled.", failure.getMessage(Locale.ROOT));
    assertEquals("city", failure.getSource());

    // fill mandatory fields and get value including validation
    String street = "Main road";
    assertFalse(form.isModified());
    form.getFieldStreet().setValueForUser(street);
    assertTrue(form.isModified());

    value = form.getValue();
    assertNotNull(value);
    Address address = new Address();
    address.setStreet(street);
    assertEquals(address, value);
  }
}
