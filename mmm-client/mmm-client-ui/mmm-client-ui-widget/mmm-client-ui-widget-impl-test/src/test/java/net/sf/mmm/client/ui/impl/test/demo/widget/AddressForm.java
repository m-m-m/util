/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.demo.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;
import net.sf.mmm.client.ui.impl.test.demo.transferobject.Address;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is a UI form for testing the {@link UiWidget}-framework. <br>
 * <b>ATTENTION:</b><br>
 * This class combines various approaches how to create fields and to do the data-binding. It is therefore NOT
 * a best-practice that should be followed rather than a showcase of the different ways to use the
 * infrastructure.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AddressForm extends UiWidgetCustomGridPanel<Address> {

  /** @see #getFieldCity() */
  private final UiWidgetWithValue<String> fieldCity;

  /** @see #getFieldZip() */
  private final UiWidgetWithValue<PostalCode> fieldZip;

  /** @see #getFieldStreet() */
  private final UiWidgetWithValue<String> fieldStreet;

  /** @see #getFieldHouseNumber() */
  private final UiWidgetWithValue<String> fieldHouseNumber;

  /** @see #getFieldDummy() */
  // a custom field that manually mapped without databinding...
  private final UiWidgetTextField fieldCountry;

  /** @see #getFieldDummy() */
  // a dummy field that is NOT involved in doSetValue/doGetValue
  private final UiWidgetTextField fieldDummy;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AddressForm(UiContext context) {

    super(context, Address.class);
    UiDataBinding<Address> dataBinding = getDataBinding();
    getDelegate().setColumnCount(2);
    UiWidgetFactory factory = getContext().getWidgetFactory();
    UiWidgetGridRow row = factory.create(UiWidgetGridRow.class);
    this.fieldStreet = dataBinding.createAndBind(Address.PROPERTY_STREET);
    this.fieldHouseNumber = dataBinding.createAndBind(Address.PROPERTY_HOUSE_NUMBER);
    // TODO externalize labels...
    row.addChild(factory.createLabel("Street/No"));
    row.addChildren(this.fieldStreet, this.fieldHouseNumber);
    getDelegate().addChild(row);

    row = factory.create(UiWidgetGridRow.class);
    this.fieldZip = dataBinding.createAndBind(Address.PROPERTY_POSTAL_CODE);
    this.fieldCity = dataBinding.createAndBind(Address.PROPERTY_CITY);
    row.addChild(factory.createLabel("Zip/City"));
    row.addChildren(this.fieldZip, this.fieldCity);
    getDelegate().addChild(row);
    // TODO ...

    this.fieldCountry = factory.createTextField("Country");
    getDelegate().addChildren(this.fieldCountry);

    this.fieldDummy = factory.createTextField("Dummy");
    getDelegate().addChildren(this.fieldDummy);
  }

  /**
   * @return the fieldCity
   */
  public UiWidgetWithValue<String> getFieldCity() {

    return this.fieldCity;
  }

  /**
   * @return the fieldZip
   */
  public UiWidgetWithValue<PostalCode> getFieldZip() {

    return this.fieldZip;
  }

  /**
   * @return the fieldStreet
   */
  public UiWidgetWithValue<String> getFieldStreet() {

    return this.fieldStreet;
  }

  /**
   * @return the fieldHouseNumber
   */
  public UiWidgetWithValue<String> getFieldHouseNumber() {

    return this.fieldHouseNumber;
  }

  /**
   * @return the fieldCountry
   */
  public UiWidgetTextField getFieldCountry() {

    return this.fieldCountry;
  }

  /**
   * @return the dummyField
   */
  public UiWidgetTextField getFieldDummy() {

    return this.fieldDummy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Address doGetValue(Address template, ValidationState state) throws RuntimeException {

    template.setCountry(this.fieldCountry.getValueDirect(null, state));
    return super.doGetValue(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(Address value, boolean forUser) {

    this.fieldCountry.setValue(value.getCountry(), forUser);
    super.doSetValue(value, forUser);
  }

}
