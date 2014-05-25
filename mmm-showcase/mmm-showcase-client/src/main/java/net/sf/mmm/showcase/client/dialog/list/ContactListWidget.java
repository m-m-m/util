/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.custom.complex.UiWidgetCustomListTable;
import net.sf.mmm.showcase.client.dialog.editor.Contact;
import net.sf.mmm.showcase.client.dialog.editor.ContactBean;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactListWidget extends UiWidgetCustomListTable<ContactBean> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public ContactListWidget(UiContext context) {

    super(context);
    UiWidgetTableColumn<ContactBean, String> columnFirstName = getDelegate().createColumn(Contact.PROPERTY_FIRST_NAME,
        null, null);
    UiWidgetTableColumn<ContactBean, String> columnLastName = getDelegate().createColumn(Contact.PROPERTY_LAST_NAME,
        null, null);
    UiWidgetTableColumn<ContactBean, String> columnEmail = getDelegate().createColumn(Contact.PROPERTY_EMAIL, null,
        null);
    UiWidgetTableColumn<ContactBean, String> columnPhone = getDelegate().createColumn(Contact.PROPERTY_PHONE, null,
        null);
    columnPhone.getSize().setWidthInPixel(160);
    getDelegate().setColumns(Arrays.asList(columnFirstName, columnLastName, columnPhone, columnEmail));
    getDelegate().setTitle("Contacts");
    getDelegate().setSelectionMode(SelectionMode.MULTIPLE_SELECTION);
  }

  /**
   * Initializes some test data.
   */
  public void initTestdata() {

    List<ContactBean> contacts = new ArrayList<>();
    contacts.add(new ContactBean("John", "Doe", "john.doe@googlemail.com", "+40 68275 4618"));
    contacts.add(new ContactBean("Richard", "Miles", "richard.miles@yahoo.com", "+44 123 45678"));
    contacts.add(new ContactBean("Jane", "Stiles", "jane@stiles.com", "+22 99 46452"));
    contacts.add(new ContactBean("Peter", "Pecker", "ppecker@gmx.de", "+44 173 1234567"));
    setValue(contacts);
  }

}
