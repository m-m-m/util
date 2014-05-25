/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.masterdetail;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomMasterDetail;
import net.sf.mmm.showcase.client.dialog.editor.ContactBean;
import net.sf.mmm.showcase.client.dialog.editor.ContactForm;
import net.sf.mmm.showcase.client.dialog.list.ContactListWidget;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MasterDetailViewWidget extends UiWidgetCustomMasterDetail<ContactBean> {

  /**
   * The constructor.
   *
   * @param context - see {@link #getContext()}.
   */
  public MasterDetailViewWidget(UiContext context) {

    super(context, new ContactListWidget(context), new ContactForm(context));
    ((ContactListWidget) getMasterPanel()).initTestdata();
  }

}
