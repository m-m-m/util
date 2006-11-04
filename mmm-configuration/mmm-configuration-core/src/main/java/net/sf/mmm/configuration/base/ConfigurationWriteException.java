/* $ Id: $ */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.NlsResourceBundle;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;

/**
 * This is the exception thrown if a configuration file was not found.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationWriteException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = 4441678946509285043L;

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   */
  public ConfigurationWriteException(ConfigurationAccess access) {

    super(NlsResourceBundle.ERR_WRITE, access);
  }

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public ConfigurationWriteException(ConfigurationAccess access, Throwable nested) {

    super(NlsResourceBundle.ERR_WRITE, access, nested);
  }

}
