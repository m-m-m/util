/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api;

import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.busy.BusyManager;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;

/**
 * This interface gives access to the central components of the client infrastructure.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ClientComponents {

  /**
   * @return the instance of {@link BusyManager}.
   */
  BusyManager getBusyManager();

  /**
   * @return the instance of {@link DialogManager}.
   */
  DialogManager getDialogManager();

  /**
   * @return the instance of {@link UiPopupHelper}.
   */
  UiPopupHelper getPopupManager();

  /**
   * @return the instance of {@link RemoteInvocationServiceCaller}.
   */
  RemoteInvocationServiceCaller getServiceCaller();

}
