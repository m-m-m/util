/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api.dialog;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the component used to manage {@link Dialog}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DialogManager {

  /**
   * This method gets the current {@link Dialog} of the given <code>type</code>. If multiple {@link Dialog}s
   * of the same type are open at a time, this method will return the one in the front.
   * 
   * @param type is the {@link Dialog#getType() type} of the requested {@link Dialog}.
   * @return the current {@link Dialog}.
   */
  Dialog getCurrentDialog(String type);

  /**
   * This method gets the current {@link Dialog} of the {@link Dialog#getType() type} {@link Dialog#TYPE_MAIN
   * main}.
   * 
   * @return the current {@link Dialog}.
   */
  Dialog getCurrentMainDialog();

  // /**
  // * This method gets the {@link Dialog} with the given <code>id</code>.
  // *
  // * @param id is the {@link Dialog#getId() ID} of the requested {@link Dialog}.
  // * @return the requested {@link Dialog} or <code>null</code> if NOT available.
  // */
  // Dialog getDialog(String id);

}
