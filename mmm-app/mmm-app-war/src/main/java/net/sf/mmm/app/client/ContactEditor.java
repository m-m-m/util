/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomFormEditor;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactEditor extends UiWidgetCustomFormEditor<ContactBean> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param handlerObjectSave is the {@link UiHandlerObjectSave} to save the {@link ContactBean}.
   */
  public ContactEditor(UiContext context, UiHandlerObjectSave<ContactBean> handlerObjectSave) {

    super(context, handlerObjectSave, new ContactForm(context));
  }
}
