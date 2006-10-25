/* $Id$ */
package net.sf.mmm.util.reflect.pojo.api;

/**
 * A {@link PojoPropertyNotFoundException} is thrown if a property shoud be
 * accessed that does NOT exist (was NOT found).
 * 
 * @see PojoDescriptorIF#getProperty(Object, String)
 * @see PojoDescriptorIF#setProperty(Object, String, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyNotFoundException extends RuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = -7713121978429674081L;

  /**
   * The constructor.
   * 
   * @param pojoType 
   * @param propertyName 
   */
  public PojoPropertyNotFoundException(Class<?> pojoType, String propertyName) {

    super("Property " + propertyName + " not found in " + pojoType);
  }

}
