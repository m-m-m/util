/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.demo.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;
import net.sf.mmm.client.ui.impl.test.demo.transferobject.Address;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
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

    this.fieldZip = dataBinding.createAndBind(Address.PROPERTY_POSTAL_CODE);
    this.fieldCity = dataBinding.createAndBind(Address.PROPERTY_CITY);
    row.addChild(factory.createLabel("Zip/City"));
    row.addChildren(this.fieldZip, this.fieldCity);
    getDelegate().addChild(row);

    // TODO ...
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

}
