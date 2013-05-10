/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.base.dialog.root.RootDialogController;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link DialogControllerFactory}.
 * 
 * @see #createAndAddDialogControllers(List)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDialogControllerFactory extends AbstractLoggableComponent implements
    DialogControllerFactory {

  /**
   * The constructor.
   */
  public AbstractDialogControllerFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final List<DialogController<?>> createDialogControllers() {

    List<DialogController<?>> result = new ArrayList<DialogController<?>>();
    result.add(new RootDialogController());
    createAndAddDialogControllers(result);
    return result;
  }

  /**
   * This method creates all {@link DialogController}s and {@link List#add(Object) adds} them to the given
   * {@link List}.<br/>
   * <b>ATTENTION:</b><br/>
   * The {@link RootDialogController} has already been added before this method is called.
   * 
   * @param controllerList is the {@link List} where to add the {@link DialogController}s.
   */
  protected abstract void createAndAddDialogControllers(List<DialogController<?>> controllerList);

}
