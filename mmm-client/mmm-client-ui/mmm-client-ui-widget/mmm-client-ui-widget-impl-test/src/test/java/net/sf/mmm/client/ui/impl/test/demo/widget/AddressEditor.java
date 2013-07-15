/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.demo.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomFormEditor;
import net.sf.mmm.client.ui.impl.test.demo.transferobject.Address;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AddressEditor extends UiWidgetCustomFormEditor<Address> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param handlerObjectSave is the {@link UiHandlerObjectSave} to save the {@link Address}.
   */
  public AddressEditor(UiContext context, UiHandlerObjectSave<Address> handlerObjectSave) {

    super(context, handlerObjectSave, new AddressForm(context));
  }
}
