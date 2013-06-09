/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import net.sf.mmm.client.ui.api.handler.UiHandler;

/**
 * This is the interface for a {@link UiHandler} that allows to {@link #getDetailsForSelection(Object) get the
 * details for a selection}. It is dedicated for {@link AbstractUiWidgetCustomMasterDetail} and allows to
 * display a reduced list of data objects in {@link AbstractUiWidgetCustomMasterDetail#getMasterList() master
 * list} with (lazy) loading of the {@link AbstractUiWidgetCustomMasterDetail#getDetailPanel() details} via
 * this handler. This interface should typically be implemented by the controller (if it calls services on the
 * server).
 * 
 * @param <SELECTION> is the generic type of the individual row from the master panel.
 * @param <DETAIL> is the generic type of the actual value representing the &lt;SELECTION&gt; that gets set in
 *        the details panel.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerDetailsForSelection<SELECTION, DETAIL> extends UiHandler {

  /**
   * This method gets the &lt;DETAIL&gt; for the given &lt;SELECTION&gt;.
   * 
   * @param selection is the object that has just been selected (in
   *        {@link AbstractUiWidgetCustomMasterDetail#getMasterList()}).
   * @return is the detail object to view or edit in
   *         {@link AbstractUiWidgetCustomMasterDetail#getDetailPanel()}.
   */
  DETAIL getDetailsForSelection(SELECTION selection);

}
