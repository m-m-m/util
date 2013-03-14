/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomRegularComposite;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base class for a {@link UiWidgetCustomEditor custom editor} that (re-)uses a
 * <em>custom form panel</em> to view and edit the {@link #getValue() value}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomFormEditor<VALUE> extends UiWidgetCustomEditor<VALUE> {

  /** The form panel to view and edit the {@link #getValue() value}. */
  private UiWidgetCustomRegularComposite<VALUE, ?, ?> formPanel;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param handlerSaveObject is the {@link UiHandlerObjectSave}
   *        {@link UiHandlerObjectSave#onSave(Object, Object) invoked} if the end-user clicked "save" and the
   *        {@link #getValue() value} has been validated successfully.
   * @param formPanel is the
   */
  public UiWidgetCustomFormEditor(UiContext context, UiHandlerObjectSave<VALUE> handlerSaveObject,
      UiWidgetCustomRegularComposite<VALUE, ?, ?> formPanel) {

    super(context, handlerSaveObject);
    this.formPanel = formPanel;
    addChild(formPanel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    return this.formPanel.getValueDirect(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createNewValue() {

    // will be created by formPanel...
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value, boolean forUser) {

    this.formPanel.setValue(value, forUser);
  }

}
