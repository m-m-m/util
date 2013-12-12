/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetDynamicPanel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base class for a {@link UiWidgetCustomComposite custom composite widget} that
 * implements the UI pattern <em>master/detail (panel)</em>. It combines a {@link #getMasterPanel() master
 * panel} that allows {@link UiFeatureSelectedValue selecting} an object that automatically gets displayed in
 * the {@link #getDetailPanel() details panel} and can typically be edited there.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically bound to
 *        {@link java.util.List}&lt;SELECTION&gt;.
 * @param <SELECTION> is the generic type of the individual row from the {@link #getMasterList() master list}.
 * @param <DETAIL> is the generic type of the actual value representing the &lt;SELECTION&gt; that gets set in
 *        the {@link #getDetailPanel() details panel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetCustomMasterDetail<VALUE, SELECTION, DETAIL> extends
    UiWidgetCustomComposite<VALUE, UiWidgetRegular, UiWidgetDynamicPanel<UiWidgetRegular>> {

  /** @see #getDetailsForSelection(Object) */
  private UiHandlerDetailsForSelection<SELECTION, DETAIL> handlerDetailsForSelection;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public AbstractUiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate,
      Class<VALUE> valueClass) {

    this(context, delegate, valueClass, null);
  }

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   * @param handlerDetailsForSelection is the {@link UiHandlerDetailsForSelection} to use by
   *        {@link #getDetailsForSelection(Object)}.
   */
  public AbstractUiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate,
      Class<VALUE> valueClass, UiHandlerDetailsForSelection<SELECTION, DETAIL> handlerDetailsForSelection) {

    super(context, delegate, valueClass);
    this.handlerDetailsForSelection = handlerDetailsForSelection;
  }

  /**
   * @return the master panel containing the {@link #getMasterList() master list}.
   */
  protected abstract UiWidgetCustom<VALUE, ?> getMasterPanel();

  /**
   * This method gets the actual widget containing a list of &lt;SELECTION&gt; to choose from. This widget has
   * to be a child of the {@link #getMasterPanel() master panel}. A selection in the master list will
   * automatically trigger an update of the {@link #getDetailPanel() details panel}.<br/>
   * <b>NOTE:</b><br/>
   * The {@link #getDetailPanel() details panel} can only display a single &lt;DETAIL&gt; at a time. Therefore
   * if {@link net.sf.mmm.client.ui.api.common.SelectionMode#MULTIPLE_SELECTION multi-selection} is enabled,
   * nothing will be displayed in the {@link #getDetailPanel() details panel}. This may still make sense to
   * allow mass-operations such as
   * {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionRemove#onRemove(net.sf.mmm.client.ui.api.event.UiEvent)
   * remove} in the {@link #getMasterPanel() master panel}.
   * 
   * @return the master list.
   */
  protected abstract UiFeatureSelectedValue<SELECTION> getMasterList();

  /**
   * This method gets the details panel showing the {@link #getDetailsForSelection(Object) detail for the
   * current selection} from the {@link #getMasterList() master list}. If the &lt;DETAIL&gt; should be
   * editable for the user, this details panel will typically be an {@link UiWidgetCustomEditor editor}.
   * 
   * @return the details panel.
   */
  protected abstract UiWidgetCustom<DETAIL, ?> getDetailPanel();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    Handler handler = new Handler();
    getMasterList().addSelectionHandler(handler);
    // ensure proper modes on startup.
    getMasterPanel().setMode(UiMode.EDIT);
    getDetailPanel().setMode(UiMode.VIEW);
    getDelegate().addChild(getMasterPanel());
    // // TODO hohwille add caption (heading section) with the currently selected detail... Wrap DetailPanel?
    getDelegate().addChild(getDetailPanel());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetMode(UiMode mode) {

    // super.doSetMode(mode);
    getMasterPanel().setMode(mode);
    // propagate VIEW mode(s) also to details panel so master/detail pattern can be cascaded/nested
    if (!mode.isEditable()) {
      getDetailPanel().setMode(mode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    return getMasterPanel().getValueDirect(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value, boolean forUser) {

    getMasterPanel().setValue(value, forUser);
  }

  /**
   * This method is called if the selection of the {@link #getMasterList() master list} has changed.
   * 
   * @param selectedValues are the currently selected values.
   * @param event is the {@link UiEventSelectionChange selection change event}.
   */
  protected void onMasterListSelection(List<SELECTION> selectedValues, UiEventSelectionChange<SELECTION> event) {

    if (selectedValues.size() == 1) {
      SELECTION selection = selectedValues.get(0);
      DETAIL detail = getDetailsForSelection(selection);
      getDetailPanel().setValue(detail);
    }
  }

  /**
   * This method gets the &gt;DETAIL&ls; for the given &lt;SELECTION&gt;.
   * 
   * @see UiHandlerDetailsForSelection
   * 
   * @param selection is the current selection.
   * @return the according detail.
   */
  @SuppressWarnings("unchecked")
  protected DETAIL getDetailsForSelection(SELECTION selection) {

    if (this.handlerDetailsForSelection != null) {
      return this.handlerDetailsForSelection.getDetailsForSelection(selection);
    } else {
      return (DETAIL) selection;
    }
  }

  /**
   * This inner class implements the handler-interfaces.
   */
  private class Handler extends UiHandlerEventSelection<SELECTION> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSelectionChange(UiEventSelectionChange<SELECTION> event) {

      List<SELECTION> selectedValues = event.getSource().getSelectedValues();
      onMasterListSelection(selectedValues, event);
    }

  }

}
