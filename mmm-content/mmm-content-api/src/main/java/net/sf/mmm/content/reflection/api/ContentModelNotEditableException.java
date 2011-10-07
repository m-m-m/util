/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api;

import net.sf.mmm.content.NlsBundleContentApi;

/**
 * This exception is thrown if the {@link MutableContentModelService
 * content-model} was modified by the user without being
 * {@link MutableContentModelService#isEditable() editable}.
 * 
 * @see net.sf.mmm.content.reflection.api.access.ContentModelWriteAccess
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class ContentModelNotEditableException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6915074246211424540L;

  /**
   * The constructor.
   */
  public ContentModelNotEditableException() {

    super(NlsBundleContentApi.ERR_MODEL_NOT_EDITABLE);
  }

}
