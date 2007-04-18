/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.value.api.Id;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.content.model.api.ContentClass class} could NOT be
 * registered because a class with the same
 * {@link net.sf.mmm.content.api.ContentObject#getName() name} or
 * {@link net.sf.mmm.content.api.ContentObject#getId() ID} already exists.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateClassException extends ContentModelRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5254496867101413693L;

  /**
   * The constructor. 
   * 
   * @param name
   *        is the name already in use.
   */
  public DuplicateClassException(String name) {

    super(NlsBundleContentModel.ERR_DUPLICATE_CLASS_NAME, name);
  }

  /**
   * The constructor. 
   * 
   * @param id
   *        is the ID already in use.
   */
  public DuplicateClassException(Id id) {

    super(NlsBundleContentModel.ERR_DUPLICATE_CLASS_ID, id);
  }

}
