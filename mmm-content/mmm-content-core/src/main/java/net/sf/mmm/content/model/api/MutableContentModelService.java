/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.model.api.access.ContentModelWriteAccess;

/**
 * This interface extends the {@link ContentModelService} interface with methods
 * to edit the content model.<br>
 * <b>ATTENTION:</b><br>
 * Please note that an {@link #isEditable() editable} content-model requires
 * that the persistent-stores of the content also support this feature. So take
 * care when you configure the components of your system.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableContentModelService extends ContentModelService, ContentModelWriteAccess {

  /**
   * This method determines if this service provides a content-model that is
   * editable at runtime.
   * 
   * @return <code>true</code> if the model is editable.
   */
  boolean isEditable();

}
