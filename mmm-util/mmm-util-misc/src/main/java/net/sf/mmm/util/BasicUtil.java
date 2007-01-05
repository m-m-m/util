/* $Id$ */
package net.sf.mmm.util;

/**
 * This class is a collection of useful helper macros (static methods) for
 * little general purpose operations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtil {

  /**
   * Forbidden constructor.
   */
  private BasicUtil() {

    super();
  }

  /**
   * This method checks if two given objects equal each other according to
   * {@link Object#equals(java.lang.Object)}with the extension that the
   * objects may be <code>null</code>.
   * 
   * @param o1
   *        the first object to comapre.
   * @param o2
   *        the second object to comapre.
   * @return <code>true</code> if both objects are <code>null</code> or
   *         the first is not <code>null</code> and
   *         <code>o1.equals(o2)</code> returns <code>true</code>;
   *         <code>false</code> otherwise.
   */
  public static boolean isEqual(Object o1, Object o2) {

    if (o1 == null) {
      return (o2 == null);
    } else {
      return o1.equals(o2);
    }
  }

}
