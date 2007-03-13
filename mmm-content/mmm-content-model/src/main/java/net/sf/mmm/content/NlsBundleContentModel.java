/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the content subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleContentModel extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleContentModel() {

    super();
  }

  /**
   * exception message if permission was denied.
   * 
   * @see net.sf.mmm.content.security.api.PermissionDeniedException
   */
  public static final String ERR_PERMISSION_DENIED = "Permission denied for \"{0}\" performing \"{1}\" on \"{2}\"!";

  /**
   * @see net.sf.mmm.content.model.api.NoSuchFieldException
   */
  public static final String ERR_NO_SUCH_FIELD = "The class \"{1}\" does not own a field with the name \"{0}\"!";

  /**
   * @see net.sf.mmm.content.model.api.NoSuchClassException
   */
  public static final String ERR_NO_SUCH_CLASS_NAME = "The content-class with name \"{0}\" was NOT found!";

  /**
   * @see net.sf.mmm.content.model.api.NoSuchClassException
   */
  public static final String ERR_NO_SUCH_CLASS_ID = "The content-class with ID \"{0}\" was NOT found!";
  
  /**
   * exception message if a class could NOT be created because the super-class
   * is NOT extendable.
   */
  public static final String ERR_CLASS_NOT_EXTENDABLE = "The class \"{0}\" is NOT extendable!";

  /**
   * @see net.sf.mmm.content.model.base.DuplicateClassException
   */
  public static final String ERR_DUPLICATE_CLASS_NAME = "The class with the name \"{0}\" already exists!";

  /**
   * @see net.sf.mmm.content.model.base.DuplicateClassException
   */
  public static final String ERR_DUPLICATE_CLASS_ID = "The class with the ID \"{0}\" already exists!";

  /**
   * @see net.sf.mmm.content.model.base.DuplicateFieldException
   */
  public static final String ERR_DUPLICATE_FIELD_NAME = "The field with the name \"{0}\" already exists!";

  /**
   * @see net.sf.mmm.content.model.base.DuplicateFieldException
   */
  public static final String ERR_DUPLICATE_FIELD_ID = "The field with the ID \"{0}\" already exists!";

}
