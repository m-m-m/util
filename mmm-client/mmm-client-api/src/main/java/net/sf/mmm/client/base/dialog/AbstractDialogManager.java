/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.dialog;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.api.dialog.Dialog;
import net.sf.mmm.client.api.dialog.DialogManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link DialogManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDialogManager extends AbstractLoggableComponent implements DialogManager {

  /** @see #getCurrentDialog(String) */
  private final Map<String, Dialog> type2currentDialogMap;

  /**
   * The constructor.
   */
  public AbstractDialogManager() {

    super();
    this.type2currentDialogMap = new HashMap<String, Dialog>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog getCurrentDialog(String type) {

    return this.type2currentDialogMap.get(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog getCurrentMainDialog() {

    return getCurrentDialog(Dialog.TYPE_MAIN);
  }

  /**
   * This method should be called whenever a {@link Dialog} is opened or brought to the front.
   * 
   * @param dialog the current {@link Dialog}.
   */
  protected void setCurrentDialog(Dialog dialog) {

    this.type2currentDialogMap.put(dialog.getType(), dialog);
  }

}
