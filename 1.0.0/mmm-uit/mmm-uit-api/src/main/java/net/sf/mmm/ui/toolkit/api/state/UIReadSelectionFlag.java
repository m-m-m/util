/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the selection flag of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSelectionFlag {

  /**
   * This method determines if the object is selected.
   * 
   * @return <code>true</code> if this object is selected.
   */
  boolean isSelected();

}
