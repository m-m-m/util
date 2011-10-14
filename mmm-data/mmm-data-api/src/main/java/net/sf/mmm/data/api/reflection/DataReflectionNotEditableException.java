/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This exception is thrown if the {@link MutableDataReflectionService
 * content-model} was modified by the user without being
 * {@link MutableDataReflectionService#isEditable() editable}.
 * 
 * @see net.sf.mmm.data.api.reflection.access.DataReflectionWriteAccess
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataReflectionNotEditableException extends DataReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6915074246211424540L;

  /**
   * The constructor.
   */
  public DataReflectionNotEditableException() {

    super(NlsBundleDataApi.ERR_MODEL_NOT_EDITABLE);
  }

}
