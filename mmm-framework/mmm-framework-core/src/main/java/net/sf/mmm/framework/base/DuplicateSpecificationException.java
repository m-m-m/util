/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ContainerException;

/**
 * A {@link DuplicateSpecificationException} is thrown if a
 * {@link net.sf.mmm.framework.api.ComponentProvider component} was
 * {@link net.sf.mmm.framework.api.MutableIocContainer#addComponentProvider(net.sf.mmm.framework.api.ComponentProvider) added}
 * but another component with the same
 * {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 * is already registered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateSpecificationException extends ContainerException {

  /** UID for serialization */
  private static final long serialVersionUID = 536566649568067962L;

  /**
   * The constructor.
   * 
   * @param specification
   *        is the specification
   */
  public DuplicateSpecificationException(Class specification) {

    super(NlsResourceBundle.ERR_COMPONENT_DUPLICATE_SPECIFICATION, specification);
  }

}
