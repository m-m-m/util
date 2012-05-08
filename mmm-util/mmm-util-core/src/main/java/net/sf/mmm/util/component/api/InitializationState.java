/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This enum contains the available states of an initialization. The lifecycle starts in state
 * {@link #UNINITIALIZED}. At the beginning of the initialization it switches to {@link #INITIALIZING} and if
 * initialization has completed it goes to {@link #INITIALIZED}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum InitializationState {

  /** State if initialization has not begun. */
  UNINITIALIZED,

  /** State if initialization has started. */
  INITIALIZING,

  /** State if initialization is complete. */
  INITIALIZED,

}
