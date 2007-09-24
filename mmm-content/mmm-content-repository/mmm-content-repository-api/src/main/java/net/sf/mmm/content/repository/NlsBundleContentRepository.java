/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleContentRepository extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleContentRepository() {

    super();
  }

  /** @see net.sf.mmm.content.repository.api.ContentObjectNotExistsException */
  public static final String ERR_OBJECT_NOT_EXISTS_FOR_ID = "No content-object exists for the requested id \"{0}\"!";

  /** @see net.sf.mmm.content.repository.api.ContentObjectNotExistsException */
  public static final String ERR_OBJECT_NOT_EXISTS_FOR_PATH = "No content-object exists for the requested path \"{0}\"!";

  /** @see net.sf.mmm.content.repository.api.ContentObjectWrongTypeException */
  public static final String ERR_OBJECT_WRONG_TYPE = "The content-object \"{0}\" has the type \"{1}\" that is incompatible with the requested type \"{2}\"!";

}
