/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the factory of all available {@link DialogController}s.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DialogControllerFactory {

  /**
   * Creates the {@link DialogController} with the given <code>dialogId</code>.
   *
   * @param dialogId is the {@link DialogController#getId() ID} of the requested {@link DialogController}.
   * @return a new instance of the specified {@link DialogController}.
   */
  DialogController<?> createDialogController(String dialogId);

}
