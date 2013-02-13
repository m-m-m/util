/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import java.util.List;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the factory of all available {@link DialogController}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DialogControllerFactory {

  /**
   * This method creates a new list with all {@link DialogController}s available in the client.
   * 
   * @return a new list with fresh instances of all available {@link DialogController}s.
   */
  List<DialogController<?>> createDialogControllers();

}
