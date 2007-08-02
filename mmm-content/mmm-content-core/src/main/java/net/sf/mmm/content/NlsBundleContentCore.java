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
public class NlsBundleContentCore extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleContentCore() {

    super();
  }

  /** @see net.sf.mmm.content.security.api.PermissionDeniedException */
  public static final String ERR_PERMISSION_DENIED = "Permission denied for \"{0}\" performing \"{1}\" on \"{2}\"!";

  /** @see net.sf.mmm.content.model.api.FieldNotExistsException */
  public static final String ERR_NO_SUCH_FIELD = "The class \"{1}\" does not own a field with the name \"{0}\"!";

  /** @see net.sf.mmm.content.model.api.ClassNotExistsException */
  public static final String ERR_NO_SUCH_CLASS_NAME = "The content-class with name \"{0}\" was NOT found!";

  /** @see net.sf.mmm.content.model.api.ClassNotExistsException */
  public static final String ERR_NO_SUCH_CLASS_ID = "The content-class with ID \"{0}\" was NOT found!";

  /**
   * exception message if a class could NOT be created because the super-class
   * is NOT extendable.
   */
  public static final String ERR_CLASS_NOT_EXTENDABLE = "The class \"{0}\" is NOT extendable!";

  /** exception message if user tried to create a system-class. */
  public static final String ERR_CLASS_SYSTEM = "User can NOT create system-class \"{0}\"!";

  /** @see net.sf.mmm.content.model.base.DuplicateClassException */
  public static final String ERR_DUPLICATE_CLASS_NAME = "The class with the name \"{0}\" already exists!";

  /** @see net.sf.mmm.content.model.base.DuplicateClassException */
  public static final String ERR_DUPLICATE_CLASS_ID = "The class with the ID \"{0}\" already exists!";

  /** @see net.sf.mmm.content.model.base.DuplicateFieldException */
  public static final String ERR_DUPLICATE_FIELD_NAME = "The field with the name \"{0}\" already exists!";

  /** @see net.sf.mmm.content.model.base.DuplicateFieldException */
  public static final String ERR_DUPLICATE_FIELD_ID = "The field with the ID \"{0}\" already exists!";

  /** @see net.sf.mmm.content.model.base.IllegalModifiersException */
  public static final String ERR_MODIFIERS_TRANSIENT_MUTABLE = "A transient field has to be read-only!";

  /** @see net.sf.mmm.content.model.base.IllegalModifiersException */
  public static final String ERR_MODIFIERS_TRANSIENT_STATIC = "A transient field can NOT be static!";

  /** @see net.sf.mmm.content.model.base.IllegalModifiersException */
  public static final String ERR_MODIFIERS_ABSTRACT_FINAL = "An abstract class can NOT be final!";

  /** @see net.sf.mmm.content.model.base.IllegalModifiersException */
  public static final String ERR_MODIFIERS_FINAL_EXTENDABLE = "A final class can NOT be extendable!";

  /** @see net.sf.mmm.content.model.base.IllegalModifiersException */
  public static final String ERR_MODIFIERS_USER_UNEXTENDABLE = "Only system-classes can be un-extendable without being final!";

  /** @see net.sf.mmm.content.model.api.ContentModelNotEditableException */
  public static final String ERR_MODEL_NOT_EDITABLE = "Failed to modify the content-model because it is NOT editable!";

  /** exception message if user tried to delete a class or field that is system. */
  public static final String ERR_DELETE_SYSTEM = "Can NOT delete \"{0}\" because it is required by the system!";

  /** @see net.sf.mmm.content.model.base.ContentModelSystemModifyException */
  public static final String ERR_MODIFY_SYSTEM = "Can NOT modify \"{0}\" because it is required by the system!";

  /** @see net.sf.mmm.content.base.ContentCastException */
  public static final String ERR_CAST = "Can NOT cast from \"{0}\" to \"{1}\"!";

}
