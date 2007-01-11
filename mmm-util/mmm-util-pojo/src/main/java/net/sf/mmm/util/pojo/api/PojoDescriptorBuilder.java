/* $Id$ */
package net.sf.mmm.util.pojo.api;

/**
 * This is the interface used to {@link #getDescriptor(Class) get} the
 * {@link PojoDescriptor descriptor} for a given POJO. A POJO (plain old java
 * object) in this manner is more or less any java object.<br>
 * This functionality is an alternative to {@link java.beans.Introspector}.
 * 
 * @see PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptorBuilder {

  /**
   * This method gets the {@link PojoDescriptor descriptor} for the given
   * <code>pojoType</code>.
   * 
   * @param <P>
   *        is the templated type of the <code>pojoType</code>.
   * 
   * @param pojoType
   *        is the type reflecting the POJO.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoType</code>.
   */
  <P> PojoDescriptor<P> getDescriptor(Class<P> pojoType);

}
