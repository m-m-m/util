/* $ Id: $ */
package net.sf.mmm.content;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the content subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsResourceBundle extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsResourceBundle() {

    super();
  }

  /**
   * exception message if permission was denied.
   * 
   * @see net.sf.mmm.content.api.security.PermissionDeniedException
   */
  public static final String ERR_PERMISSION_DENIED = "Permission denied for \"{0}\" performing \"{1}\" on \"{2}\"!";

  /**
   * exception message if a requested field does not exist.
   * 
   * @see NoSuchFieldException
   */
  public static final String ERR_NO_SUCH_FIELD = "The class \"{1}\" does not own a field with the name \"{0}\"!";

}
