/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactForm extends UiWidgetCustomGridPanel<ContactBean> {

  /** The field for {@link ContactBean#getFirstName()}. */
  private UiWidgetTextField widgetFirstName;

  /** The field for {@link ContactBean#getLastName()}. */
  private UiWidgetTextField widgetLastName;

  /** The field for {@link ContactBean#getBirthday()}. */
  private UiWidgetDateField widgetBirthday;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public ContactForm(UiContext context) {

    super(context);
    this.widgetFirstName = context.getWidgetFactoryAdvanced().createTextField("First name");
    this.widgetFirstName.addValidatorMandatory();
    this.widgetLastName = context.getWidgetFactoryAdvanced().createTextField("Last name");
    this.widgetLastName.addValidatorMandatory();
    this.widgetBirthday = context.getWidgetFactory().create(UiWidgetDateField.class);
    this.widgetBirthday.setFieldLabel("Birthday");
    getDelegate().addChildren(this.widgetFirstName);
    getDelegate().addChildren(this.widgetLastName);
    getDelegate().addChildren(this.widgetBirthday);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContactBean doGetValue(ContactBean template, ValidationState state) throws RuntimeException {

    ContactBean result = template;
    if (result == null) {
      result = new ContactBean();
    }
    result.setFirstName(this.widgetFirstName.getValueDirect(null, state));
    result.setLastName(this.widgetLastName.getValueDirect(null, state));
    result.setBirthday(this.widgetBirthday.getValueDirect(null, state));
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(ContactBean value) {

    ContactBean contact = value;
    if (contact == null) {
      contact = new ContactBean();
    }
    this.widgetFirstName.setValue(contact.getFirstName());
    this.widgetLastName.setValue(contact.getLastName());
    this.widgetBirthday.setValue(contact.getBirthday());
  }

}
