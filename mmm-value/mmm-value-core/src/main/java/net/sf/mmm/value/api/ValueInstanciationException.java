/* $ Id: $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.CoreNlsResourceBundle;

/**
 * This is the exception thrown if an reflective
 * {@link java.lang.Class#newInstance() instantiation} failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueInstanciationException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = -1522159316208300410L;

  /**
   * The constructor.
   * 
   * @param javaClass
   *        is the class that could not be instantiated.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public ValueInstanciationException(Class javaClass, Throwable nested) {

    super(nested, CoreNlsResourceBundle.ERR_INSTANTIATION_FAILED, javaClass);
  }

}
