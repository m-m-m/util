/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isEditable() editable-flag}
 * of an {@link net.sf.mmm.ui.toolkit.api.UiObject object}. Such object allows
 * editing but this behavior can be enabled or disabled. Editable means that the
 * end-user can edit the data of the object (e.g. the text of a text-input
 * field). This is closely related to {@link UiReadEnabled}. However disabling
 * the {@link #isEditable() editable-flag} may still allow user-interaction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadEditable {

  /**
   * This method gets the editable status.
   * 
   * @return <code>true</code> if this object is editable.
   */
  boolean isEditable();

}
