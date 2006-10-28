/* $Id$ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.base.ComponentInstanceContainer;

/**
 * This is a very simple implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProviderIF} interface that provides
 * a singleton instance.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component.
 * 
 * @see #dispose(ComponentInstanceContainerIF, ComponentManagerIF)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleSingletonProvider<S> extends AbstractStaticSingletonComponentProvider<S> {

  /**
   * The constructor.
   * 
   * @param specification
   *        is the
   *        {@link ComponentDescriptorIF#getSpecification() specification}.
   * @param instance
   *        is the singleton instance.
   */
  public SimpleSingletonProvider(Class<S> specification, S instance) {

    super(specification);
    setInstanceContainer(new ComponentInstanceContainer<S>(instance));
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor
   *        is the {@link ComponentDescriptorIF descriptor} of the provided
   *        component.
   * @param instance
   *        is the singleton instance.
   */
  public SimpleSingletonProvider(ComponentDescriptorIF<S> componentDescriptor, S instance) {

    this(componentDescriptor, new ComponentInstanceContainer<S>(instance));
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor
   *        is the {@link ComponentDescriptorIF descriptor} of the provided
   *        component.
   * @param componentInstanceContainer
   *        is the container with the singleton instance.
   */
  public SimpleSingletonProvider(ComponentDescriptorIF<S> componentDescriptor,
      ComponentInstanceContainerIF<S> componentInstanceContainer) {

    super(componentDescriptor);
    setInstanceContainer(componentInstanceContainer);
  }

}
