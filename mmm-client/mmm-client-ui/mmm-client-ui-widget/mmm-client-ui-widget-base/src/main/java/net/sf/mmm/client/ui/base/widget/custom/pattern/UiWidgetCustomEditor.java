/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainStartEdit;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainStopEdit;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.lang.api.Callback;
import net.sf.mmm.util.nls.api.NlsAccess;

/**
 * This is the abstract base class for a {@link UiWidgetCustomComposite custom composite widget} that
 * implements the UI pattern <em>editor</em>. It supports {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW
 * viewing} a composite {@link #getValue() value} (an {@link net.sf.mmm.util.entity.api.GenericEntity entity}
 * or business object). It has a {@link UiWidgetButtonPanel button panel} with an "Edit"-Button that
 * {@link #setMode(net.sf.mmm.client.ui.api.common.UiMode) switches} to the
 * {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit-mode}. In edit-mode the user can modify the
 * {@link #getValue() object} and finally save his changes by pressing a "Save"-Button. This will check for
 * {@link #isModified() modifications}, trigger
 * {@link #validate(net.sf.mmm.util.validation.api.ValidationState) validation} and create a new instance of
 * the {@link #getValue() value object} with the current modifications that is saved by delegation to the
 * {@link UiHandlerObjectSave#onSave(Object)} on widgets for UI patterns or forms to edit business objects
 * (see {@link #doGetValue()} and {@link #doSetValue(Object)}).
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomEditor<VALUE> extends
    UiWidgetCustomComposite<VALUE, UiWidgetRegular, UiWidgetVerticalPanel> {

  /** @see #createButtonPanel() */
  private final UiHandler handler;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomEditor(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetVerticalPanel.class));
    this.handler = new UiHandler();
    createAndAddChildren();
    UiWidgetButtonPanel buttonPanel = createButtonPanel();
    getDelegate().addChild(buttonPanel);
  }

  /**
   *
   */
  protected void createAndAddChildren() {

  }

  protected UiWidgetButtonPanel createButtonPanel() {

    UiWidgetButtonPanel buttonPanel = getContext().getWidgetFactory().create(UiWidgetButtonPanel.class);

    UiWidgetButton startEditButton = getContext().getWidgetFactoryAdvanced().createButton(
        UiHandlerPlainStartEdit.class, this.handler);
    UiWidgetButton saveButton = getContext().getWidgetFactoryAdvanced().createButton(UiHandlerPlainSave.class,
        this.handler);
    UiWidgetButton stopEditButton = getContext().getWidgetFactoryAdvanced().createButton(UiHandlerPlainStopEdit.class,
        this.handler);

    buttonPanel.addChild(startEditButton);
    buttonPanel.addChild(saveButton);
    buttonPanel.addChild(stopEditButton);
    return buttonPanel;
  }

  /**
   * This inner class implements the handler interfaces.
   */
  private class UiHandler implements UiHandlerPlainStartEdit, UiHandlerPlainStopEdit, UiHandlerPlainSave {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSave() {

      // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStopEditMode() {

      if (isModified()) {
        NlsBundleClientUiRoot bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
        Callback<String> callback = new Callback<String>() {

          @Override
          public Void apply(String argument) {

            stopEditMode();
            return null;
          }
        };
        String labelOk = bundle.labelConfirm().getLocalizedMessage();
        String labelCancel = bundle.labelCancel().getLocalizedMessage();
        getContext().getPopupHelper().showPopup(bundle.messageConfirmStopEdit().getLocalizedMessage(),
            MessageSeverity.QUESTION, bundle.titleDiscardPopup().getLocalizedMessage(), callback, labelOk, labelCancel);
      } else {
        stopEditMode();
      }
    }

    /**
     * 
     * TODO: javadoc
     */
    private void stopEditMode() {

      resetValue();
      setMode(UiMode.VIEW);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartEditMode() {

      setMode(UiMode.EDIT);
    }

  }

}
