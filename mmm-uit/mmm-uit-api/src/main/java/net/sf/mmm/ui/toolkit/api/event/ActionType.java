/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This enum contains the available action event types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ActionType {

  /**
   * The action if something is selected.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadSelected
   */
  SELECT,

  /**
   * The action if something is de-selected.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadSelected
   */
  DESELECT,

  /**
   * The action if something is hidden.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadVisible
   */
  HIDE,

  /**
   * The action if something is shown.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadVisible
   */
  SHOW,

  /**
   * The action if something is activated.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiRead
   */
  ACTIVATE,

  /**
   * The action if something is de-activated.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiRead
   */
  DEACTIVATE,

  /**
   * The action if a window is iconified.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiRead
   */
  ICONIFY,

  /**
   * The action if a window is de-iconified.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiRead
   */
  DEICONIFY,

  /**
   * The action if a window is disposed.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadDisposed
   */
  DISPOSE;

}
