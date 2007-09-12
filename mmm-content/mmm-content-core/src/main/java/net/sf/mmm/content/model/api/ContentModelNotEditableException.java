/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.NlsBundleContentCore;
import net.sf.mmm.content.model.api.access.ContentModelWriteAccess;

/**
 * This exception is thrown if the
 * {@link MutableContentModelService content-model} was modified by the user
 * without being {@link MutableContentModelService#isEditable() editable}.
 * 
 * @see ContentModelWriteAccess
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ContentModelNotEditableException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6915074246211424540L;

  /**
   * The constructor.
   */
  public ContentModelNotEditableException() {

    super(NlsBundleContentCore.ERR_MODEL_NOT_EDITABLE);
  }

}
