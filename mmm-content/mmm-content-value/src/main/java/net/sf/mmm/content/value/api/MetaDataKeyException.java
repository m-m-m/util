/* $Id$ */
package net.sf.mmm.content.value.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This is the runtime exception thrown if a meta-data namespace, unqualified or
 * qualified key was illegal.
 * 
 * @see MutableMetaDataSetIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MetaDataKeyException extends NlsRuntimeException {

  /** uid for serialization */
  private static final long serialVersionUID = 3258409517245937977L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param qualifiedKey
   */
  public MetaDataKeyException(String qualifiedKey) {

    // TODO: i18n message
    super("Illegal key \"{0}\"!", qualifiedKey);
  }

}
