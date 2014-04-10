/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionStartEdit;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionStopEdit;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.lang.api.function.Consumer;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationStateImpl;

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
 * {@link UiHandlerObjectSave#onSave(Object, UiEvent)} on widgets for UI patterns or forms to edit business
 * objects (see {@link #doGetValue(Object, ValidationState)} and {@link #doSetValue(Object, boolean)}).
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomEditor<VALUE> extends
    UiWidgetCustomComposite<VALUE, UiWidgetRegular, UiWidgetVerticalPanel> {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  public static final String PRIMARY_STYLE = "Editor";

  /** @see #createButtonPanel() */
  private final UiHandler handler;

  /** @see UiHandler#onSave(UiEvent) */
  private final UiHandlerObjectSave<VALUE> handlerSaveObject;

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param handlerSaveObject is the {@link UiHandlerObjectSave}
   *        {@link UiHandlerObjectSave#onSave(Object, UiEvent) invoked} if the end-user clicked "save" and the
   *        {@link #getValue() value} has been validated successfully.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustomEditor(UiContext context, UiHandlerObjectSave<VALUE> handlerSaveObject, Class<VALUE> valueClass) {

    super(context, context.getWidgetFactory().create(UiWidgetVerticalPanel.class), valueClass);
    setPrimaryStyle(PRIMARY_STYLE);
    this.handler = new UiHandler();
    this.handlerSaveObject = handlerSaveObject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    UiWidgetButtonPanel buttonPanel = createButtonPanel();
    getDelegate().addChild(buttonPanel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setParent(UiWidgetComposite<?> parent) {

    initialize();
    super.setParent(parent);
  }

  /**
   * This method adds a new child. It should be called from the constructor or from {@link #doInitialize()}
   * before the <code>super</code> call. Otherwise the child may appear after the {@link #createButtonPanel()
   * button panel}.
   *
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite#addChild(net.sf.mmm.client.ui.api.widget.UiWidget)
   *
   * @param child is the {@link UiWidgetRegular} to add as child.
   */
  protected void addChild(UiWidgetRegular child) {

    getDelegate().addChild(child);
  }

  /**
   * @return the {@link UiWidgetButtonPanel} with the edit, save and cancel buttons.
   */
  protected UiWidgetButtonPanel createButtonPanel() {

    UiWidgetFactory factory = getContext().getWidgetFactory();
    UiWidgetButtonPanel buttonPanel = factory.create(UiWidgetButtonPanel.class);

    UiWidgetButton startEditButton = factory.createButton(UiHandlerActionStartEdit.class, this.handler);
    UiWidgetButton saveButton = factory.createButton(UiHandlerActionSave.class, this.handler);
    UiWidgetButton stopEditButton = factory.createButton(UiHandlerActionStopEdit.class, this.handler);

    buttonPanel.addChild(startEditButton);
    buttonPanel.addChild(saveButton);
    buttonPanel.addChild(stopEditButton);
    return buttonPanel;
  }

  /**
   * This inner class implements the handler interfaces.
   */
  private class UiHandler implements UiHandlerActionStartEdit, UiHandlerActionStopEdit, UiHandlerActionSave {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSave(UiEvent event) {

      ValidationState state = new ValidationStateImpl();
      VALUE value = getValueAndValidate(state);
      if (state.isValid()) {
        UiWidgetCustomEditor.this.handlerSaveObject.onSave(value, event);
        setMode(UiMode.VIEW);
      } else {
        boolean showPopup = true;
        // showPopup = getContext().getConfiguration().isShowValidationFailurePopup();
        if (showPopup) {
          showValidationFailurePopup();
        }
      }
    }

    /**
     * This method shows a popup that informs about validation failures.
     */
    protected void showValidationFailurePopup() {

      Consumer<String> callback = null;
      getContext().getPopupHelper().showPopup("There are validation failures. Please correct input data.",
          MessageSeverity.WARNING, "Validation failed", callback, "OK", "Details");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStopEditMode(UiEvent event) {

      if (isModified()) {
        NlsBundleClientUiRoot bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
        Consumer<String> callback = new Consumer<String>() {

          @Override
          public void accept(String argument) {

            stopEditMode();
            return;
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
     * Called from {@link #onStopEditMode(UiEvent)} to actually discard the changes and leave the edit mode.
     */
    private void stopEditMode() {

      resetValue();
      setMode(UiMode.VIEW);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartEditMode(UiEvent event) {

      setMode(UiMode.EDIT);
    }

  }

}
