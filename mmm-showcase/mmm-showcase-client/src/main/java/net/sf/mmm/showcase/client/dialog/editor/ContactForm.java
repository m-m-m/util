/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.editor;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;

/**
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactForm extends UiWidgetCustomGridPanel<ContactBean> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public ContactForm(UiContext context) {

    super(context, ContactBean.class);
    UiDataBinding<ContactBean> dataBinding = getDataBinding();
    getDelegate().setColumnCount(4);
    getDelegate().addChildren(dataBinding.createAndBind(Contact.PROPERTY_FIRST_NAME),
        dataBinding.createAndBind(Contact.PROPERTY_LAST_NAME));

    getDelegate().addChildren(dataBinding.createAndBind(Contact.PROPERTY_EMAIL),
        dataBinding.createAndBind(Contact.PROPERTY_PHONE));
  }
}
