/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomEditor;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactEditor extends UiWidgetCustomEditor<ContactBean> {

  /** The form to edit {@link ContactBean}. */
  private ContactForm contactForm;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param handlerObjectSave is the {@link UiHandlerObjectSave} to save the {@link ContactBean}.
   */
  public ContactEditor(UiContext context, UiHandlerObjectSave<ContactBean> handlerObjectSave) {

    super(context, handlerObjectSave);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createAndAddChildren() {

    this.contactForm = new ContactForm(getContext());
    addChild(this.contactForm);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContactBean doGetValue(ContactBean template, ValidationState state) throws RuntimeException {

    return this.contactForm.getValueDirect(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(ContactBean value) {

    this.contactForm.setValue(value);
  }
}
