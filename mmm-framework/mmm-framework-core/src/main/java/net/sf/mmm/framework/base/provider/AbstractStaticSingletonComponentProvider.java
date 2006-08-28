/* $Id$ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;

/**
 * This is the abstract implementation of a {@link ComponentProviderIF} for
 * singleton components.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractStaticSingletonComponentProvider<S> extends
        AbstractDefaultInstanceComponentProvider<S> {

    /** the singleton instance container */
    private ComponentInstanceContainerIF<S> singletonInstanceContainer;

    /**
     * @see AbstractComponentProvider#AbstractComponentProvider(Class)
     *      {@inheritDoc}
     */
    public AbstractStaticSingletonComponentProvider(Class<S> specification) {

        super(specification);
    }

    /**
     * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptorIF)
     *      {@inheritDoc}
     */
    public AbstractStaticSingletonComponentProvider(ComponentDescriptorIF<S> componentDescriptor) {

        super(componentDescriptor);
    }

    /**
     * This method gets the container with the singleton
     * {@link ComponentInstanceContainerIF#getInstance() instance}.
     * 
     * @see #request(String, ComponentDescriptorIF, String, ComponentManagerIF)
     * 
     * @return the instance-container or <code>null</code> if it has been
     *         {@link #dispose(ComponentManagerIF) disposed}.
     */
    public final ComponentInstanceContainerIF<S> getInstanceContainer() {

        return this.singletonInstanceContainer;
    }

    /**
     * 
     * @param instanceContainer
     */
    public final void setInstanceContainer(ComponentInstanceContainerIF<S> instanceContainer) {

        this.singletonInstanceContainer = instanceContainer;
    }

    /**
     * @see net.sf.mmm.framework.base.provider.AbstractDefaultInstanceComponentProvider#requestDefault(net.sf.mmm.framework.api.ComponentDescriptorIF,
     *      java.lang.String, net.sf.mmm.framework.api.ComponentManagerIF)
     *      {@inheritDoc}
     */
    @Override
    protected ComponentInstanceContainerIF<S> requestDefault(
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        if (this.singletonInstanceContainer == null) {
            synchronized (this) {
                if (this.singletonInstanceContainer == null) {
                    this.singletonInstanceContainer = requestSingleton(sourceDescriptor,
                            sourceInstanceId, componentManager);
                }
            }
        }
        return this.singletonInstanceContainer;
    }

    /**
     * This method is called if the instance is requested but is NOT available.
     * 
     * @param sourceDescriptor
     * @param sourceInstanceId
     * @param componentManager
     * @return the requested instance-container.
     */
    protected ComponentInstanceContainerIF<S> requestSingleton(
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) {

        throw new InstanceIdNotAvailableException(ComponentManagerIF.DEFAULT_INSTANCE_ID,
                getDescriptor());
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#release(ComponentInstanceContainerIF,
     *      net.sf.mmm.framework.api.ComponentManagerIF) {@inheritDoc}
     */
    public final boolean release(ComponentInstanceContainerIF<S> instanceContainer,
            ComponentManagerIF componentManager) {

        // nothing to do...
        return false;
    }

    /**
     * ATTENTION: this implementation does nothing but setting the singleton
     * {@link #getInstanceContainer() instance-container} to <code>null</code>.
     * Please override if your component has to be shut-down.
     * 
     * @see net.sf.mmm.framework.api.ComponentProviderIF#dispose(net.sf.mmm.framework.api.ComponentInstanceContainerIF,
     *      net.sf.mmm.framework.api.ComponentManagerIF) {@inheritDoc}
     */
    public void dispose(ComponentInstanceContainerIF<S> instanceContainer,
            ComponentManagerIF componentManager) {

        if (this.singletonInstanceContainer == instanceContainer) {
            this.singletonInstanceContainer = null;
        }
    }

}
