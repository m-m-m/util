/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.value.api.Id;

/**
 * This exception is thrown if a {@link ContentClass} was requested that does
 * not exist.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClassNotExistsException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8750295439186981933L;

  /**
   * The constructor
   * 
   * @param name
   *        is the {@link ContentClass#getName() name} of the class that does
   *        NOT exist.
   */
  public ClassNotExistsException(String name) {

    super(NlsBundleContentModel.ERR_NO_SUCH_CLASS_NAME, name);
  }

  /**
   * The constructor
   * 
   * @param id
   *        is the {@link ContentClass#getId() ID} of the class that does NOT
   *        exist.
   */
  public ClassNotExistsException(Id id) {

    super(NlsBundleContentModel.ERR_NO_SUCH_CLASS_ID, id);
  }

}
