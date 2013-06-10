/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
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
    getDelegate().setColumnCount(2);
    getDelegate().addChildren(dataBinding.createAndBind(ContactBean.PROPERTY_FIRST_NAME));
    getDelegate().addChildren(dataBinding.createAndBind(ContactBean.PROPERTY_LAST_NAME));
    UiWidgetCollapsableSection section = getFactory().create(UiWidgetCollapsableSection.class);
    section.setLabel("Optionals");
    // section = getFactory().createSection("Optionals");
    getDelegate().addChildSpanned(section);
    UiWidgetWithValue<String> widgetBirthday = dataBinding.createAndBind(ContactBean.PROPERTY_BIRTHDAY);
    section.addCollapseWidget(widgetBirthday);
    getDelegate().addChildren(widgetBirthday);
  }

}
