/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

  /**
   * exception message if a class could NOT be created because the super-class
   * is NOT extendable.
   */
  public static final String ERR_CLASS_NOT_EXTENDABLE = "The class \"{type}\" is NOT extendable!";

  /** exception message if user tried to create a system-class. */
  public static final String ERR_CLASS_SYSTEM = "User can NOT create system-class \"{type}\"!";

  /** @see net.sf.mmm.data.api.reflection.DataModifiersIllegalTransientMutableException */
  public static final String ERR_MODIFIERS_TRANSIENT_MUTABLE = "A transient field has to be read-only!";

  /** @see net.sf.mmm.data.api.reflection.DataModifiersIllegalTransientStaticException */
  public static final String ERR_MODIFIERS_TRANSIENT_STATIC = "A transient field can NOT be static!";

  /** @see net.sf.mmm.data.api.reflection.DataModifiersIllegalAbstractFinalException */
  public static final String ERR_MODIFIERS_ABSTRACT_FINAL = "An abstract class can NOT be final!";

  /** @see net.sf.mmm.data.api.reflection.DataModifiersIllegalFinalExtendableException */
  public static final String ERR_MODIFIERS_FINAL_EXTENDABLE = "A final class can NOT be extendable!";

  /** @see net.sf.mmm.data.api.reflection.DataModifiersIllegalException */
  public static final String ERR_MODIFIERS_USER_UNEXTENDABLE = "Only system-classes can be un-extendable without being final!";

  /** @see net.sf.mmm.data.api.reflection.DataReflectionNotEditableException */
  public static final String ERR_MODEL_NOT_EDITABLE = "Failed to modify the data model because it is NOT editable!";

  /** exception message if user tried to delete a class or field that is system. */
  public static final String ERR_DELETE_SYSTEM = "Can NOT delete \"{type}\" because it is required by the system!";

  /** @see net.sf.mmm.data.api.reflection.DataSystemModifyException */
  public static final String ERR_MODIFY_SYSTEM = "Can NOT modify \"{type}\" because it is required by the system!";

}
