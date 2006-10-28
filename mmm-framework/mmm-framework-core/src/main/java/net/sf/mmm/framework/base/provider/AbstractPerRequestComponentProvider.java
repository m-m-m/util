/* $Id$ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.base.ComponentInstanceContainer;

/**
 * This is an abstract implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProviderIF} interface for custom
 * providers.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPerRequestComponentProvider<S> extends AbstractComponentProvider<S> {

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(Class)
   * 
   */
  public AbstractPerRequestComponentProvider(Class<S> specification) {

    super(specification);
  }

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptorIF)
   * 
   */
  public AbstractPerRequestComponentProvider(ComponentDescriptorIF<S> componentDescriptor) {

    super(componentDescriptor);
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentProviderIF#request(java.lang.String,
   *      net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String,
   *      net.sf.mmm.framework.api.ComponentManagerIF)
   */
  public ComponentInstanceContainerIF<S> request(String instanceId,
      ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
      ComponentManagerIF componentManager) throws ComponentException {

    S instance;
    if (ComponentManagerIF.DEFAULT_INSTANCE_ID.equals(instanceId)) {
      instance = requestDefault(sourceDescriptor, sourceInstanceId, componentManager);
    } else {
      instance = requestById(instanceId, sourceDescriptor, sourceInstanceId, componentManager);
    }
    return new ComponentInstanceContainer<S>(instance);
  }

  /**
   * This method requests the
   * {@link ComponentManagerIF#DEFAULT_INSTANCE_ID default} instance of the
   * component.
   * 
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return a new instance of the component.
   * @throws ComponentException
   */
  protected abstract S requestDefault(ComponentDescriptorIF<?> sourceDescriptor,
      String sourceInstanceId, ComponentManagerIF componentManager) throws ComponentException;

  /**
   * @see net.sf.mmm.framework.api.ComponentProviderIF#request(java.lang.String,
   *      net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String,
   *      net.sf.mmm.framework.api.ComponentManagerIF)
   * 
   * @param instanceId
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return the requested descriptor.
   * @throws ComponentException
   */
  protected S requestById(String instanceId, ComponentDescriptorIF<?> sourceDescriptor,
      String sourceInstanceId, ComponentManagerIF componentManager) throws ComponentException {

    throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentProviderIF#release(net.sf.mmm.framework.api.ComponentInstanceContainerIF,
   *      net.sf.mmm.framework.api.ComponentManagerIF)
   */
  public boolean release(ComponentInstanceContainerIF<S> instanceContainer,
      ComponentManagerIF componentManager) {

    dispose(instanceContainer, componentManager);
    return true;
  }

  /**
   * ATTENTION: this implementation does nothing. Please override if your
   * component has to be shut-down.
   * 
   * @see net.sf.mmm.framework.api.ComponentProviderIF#dispose(net.sf.mmm.framework.api.ComponentInstanceContainerIF,
   *      net.sf.mmm.framework.api.ComponentManagerIF)
   */
  public void dispose(ComponentInstanceContainerIF<S> instanceContainer,
      ComponentManagerIF componentManager) {

  }

}
