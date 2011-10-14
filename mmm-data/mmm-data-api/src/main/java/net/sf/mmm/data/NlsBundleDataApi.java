/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the content subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleDataApi extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleDataApi() {

    super();
  }

  /** @see net.sf.mmm.data.security.api.PermissionDeniedException */
  public static final String ERR_PERMISSION_DENIED = "Permission denied for \"{user}\" performing \"{operation}\" on \"{object}\"!";

  /** @see net.sf.mmm.content.model.api.ContentFieldNotExistsException */
  public static final String ERR_NO_SUCH_FIELD = "The class \"{1}\" does not own a field with the name \"{0}\"!";

  /** @see net.sf.mmm.content.model.api.ContentClassNotExistsException */
  public static final String ERR_NO_SUCH_CLASS_NAME = "The content-class with name \"{0}\" was NOT found!";

  /** @see net.sf.mmm.content.model.api.ContentClassNotExistsException */
  public static final String ERR_NO_SUCH_CLASS_ID = "The content-class with ID \"{0}\" was NOT found!";

  /** @see net.sf.mmm.data.base.reflection.ContentClassLoaderException */
  public static final String ERR_LOAD_CLASS = "Failed to load class(es) (\"{0}\")!";

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

  /** @see net.sf.mmm.content.model.base.DuplicateClassException */
  public static final String ERR_DUPLICATE_CLASS = "The class \"{1}\" is a duplicate of the class \"{0}\" - both ID and name has to be unique!";

  /** @see net.sf.mmm.content.model.base.DuplicateFieldException */
  public static final String ERR_DUPLICATE_FIELD_NAME = "The field with the name \"{0}\" already exists!";

  /** @see net.sf.mmm.content.model.base.DuplicateFieldException */
  public static final String ERR_DUPLICATE_FIELD_ID = "The field with the ID \"{0}\" already exists!";

  /** @see net.sf.mmm.data.base.reflection.ContentModifiersIllegalException */
  public static final String ERR_MODIFIERS_TRANSIENT_MUTABLE = "A transient field has to be read-only!";

  /** @see net.sf.mmm.data.base.reflection.ContentModifiersIllegalException */
  public static final String ERR_MODIFIERS_TRANSIENT_STATIC = "A transient field can NOT be static!";

  /** @see net.sf.mmm.data.base.reflection.ContentModifiersIllegalException */
  public static final String ERR_MODIFIERS_ABSTRACT_FINAL = "An abstract class can NOT be final!";

  /** @see net.sf.mmm.data.base.reflection.ContentModifiersIllegalException */
  public static final String ERR_MODIFIERS_FINAL_EXTENDABLE = "A final class can NOT be extendable!";

  /** @see net.sf.mmm.data.base.reflection.ContentModifiersIllegalException */
  public static final String ERR_MODIFIERS_USER_UNEXTENDABLE = "Only system-classes can be un-extendable without being final!";

  /** @see net.sf.mmm.data.api.reflection.DataReflectionNotEditableException */
  public static final String ERR_MODEL_NOT_EDITABLE = "Failed to modify the content-model because it is NOT editable!";

  /** exception message if user tried to delete a class or field that is system. */
  public static final String ERR_DELETE_SYSTEM = "Can NOT delete \"{0}\" because it is required by the system!";

  /** @see net.sf.mmm.data.api.reflection.DataSystemModifyException */
  public static final String ERR_MODIFY_SYSTEM = "Can NOT modify \"{0}\" because it is required by the system!";

  /** @see net.sf.mmm.data.api.DataCastException */
  public static final String ERR_CAST = "Can NOT cast from \"{source}\" to \"{target}\"!";

  /** @see net.sf.mmm.content.api.LockNotOwnerExecption */
  public static final String ERR_LOCK_NOT_OWNER = "User \"{1}\" can NOT modify lock of user \"{0}\" on object \"{2}\"!";

  /** @see net.sf.mmm.content.repository.api.ContentObjectNotExistsException */
  public static final String ERR_OBJECT_NOT_EXISTS_FOR_ID = "No content-object exists for the requested id \"{0}\"!";

  /** @see net.sf.mmm.content.repository.api.ContentObjectNotExistsException */
  public static final String ERR_OBJECT_NOT_EXISTS_FOR_PATH = "No content-object exists for the requested path \"{0}\"!";

  /** @see net.sf.mmm.data.api.repository.DataObjectWrongTypeException */
  public static final String ERR_OBJECT_WRONG_TYPE = "The content-object \"{0}\" has the type \"{1}\" that is incompatible with the requested type \"{2}\"!";

}
