/* $Id$ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;

/**
 * This is an abstract implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProviderIF} interface that has a
 * {@link #requestDefault(ComponentDescriptorIF, String, ComponentManagerIF) different treatment}
 * for the {@link ComponentManagerIF#DEFAULT_INSTANCE_ID default instance-id}
 * than the
 * {@link #requestById(String, ComponentDescriptorIF, String, ComponentManagerIF) handling}
 * for other instance-ids.<br>
 * By default other instance-ids are NOT accepted and cause an
 * {@link InstanceIdNotAvailableException}.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDefaultInstanceComponentProvider<S> extends
        AbstractComponentProvider<S> {

    /**
     * @see AbstractComponentProvider#AbstractComponentProvider(Class)
     * {@inheritDoc}
     */
    public AbstractDefaultInstanceComponentProvider(Class<S> specification) {

        super(specification);
    }

    /**
     * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptorIF)
     * {@inheritDoc}
     */
    public AbstractDefaultInstanceComponentProvider(ComponentDescriptorIF<S> componentDescriptor) {

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

        if (ComponentManagerIF.DEFAULT_INSTANCE_ID.equals(instanceId)) {
            return requestDefault(sourceDescriptor, sourceInstanceId, componentManager);
        } else {
            return requestById(instanceId, sourceDescriptor, sourceInstanceId, componentManager);
        }
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#request(java.lang.String,
     *      net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String,
     *      net.sf.mmm.framework.api.ComponentManagerIF)
     * 
     * @param sourceDescriptor
     * @param sourceInstanceId
     * @param componentManager
     * @return the requested descriptor.
     * @throws ComponentException
     */
    protected abstract ComponentInstanceContainerIF<S> requestDefault(
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException;

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
    protected ComponentInstanceContainerIF<S> requestById(String instanceId,
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
    }

}
