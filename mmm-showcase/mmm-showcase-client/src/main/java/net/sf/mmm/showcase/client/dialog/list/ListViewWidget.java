/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.list;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomVerticalPanel;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ListViewWidget extends UiWidgetCustomVerticalPanel {

  /** @see #getContactList() */
  private ContactListWidget contactList;

  /**
   * The constructor.
   *
   * @param context - see {@link #getContext()}.
   */
  public ListViewWidget(UiContext context) {

    super(context);
    this.contactList = new ContactListWidget(context);
    getDelegate().addChild(this.contactList);
  }

  /**
   * @return the list
   */
  public ContactListWidget getContactList() {

    return this.contactList;
  }

}
