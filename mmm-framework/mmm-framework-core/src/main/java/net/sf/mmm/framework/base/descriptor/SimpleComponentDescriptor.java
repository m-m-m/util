/* $Id$ */
package net.sf.mmm.framework.base.descriptor;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.framework.api.ComponentDescriptorIF} interface.
 * 
 * @param <S>
 *        is the {@link #getSpecification() specification} of the provided
 *        component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleComponentDescriptor<S> extends AbstractComponentDescriptor<S> {

  /** @see #getSpecification() */
  private final Class<S> specification;

  /**
   * The constructor.
   * 
   * @param componentSpecification
   *        is the {@link #getSpecification() specification} of the component.
   */
  public SimpleComponentDescriptor(Class<S> componentSpecification) {

    super();
    this.specification = componentSpecification;
    initialize();
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification()
   * 
   */
  public Class<S> getSpecification() {

    return this.specification;
  }

}
