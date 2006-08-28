/* $Id: AbstractComponentInstantiationManager.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.nls.base.NlsIllegalArgumentException;

/**
 * This is the abstract base implementation of the
 * {@link ComponentInstantiationManagerIF} interface.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptorIF#getSpecification() specification}
 *        of the component.
 * @param <I>
 *        is the
 *        {@link ExtendedComponentDescriptorIF#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentInstantiationManager<S, I extends S> implements
        ComponentInstantiationManagerIF<S, I> {

    /** @see #getDescriptor() */
    private ExtendedComponentDescriptorIF<S, I> descriptor;

    /**
     * The constructor.
     */
    public AbstractComponentInstantiationManager() {

        super();
    }

    /**
     * The constructor.
     * 
     * @param componentDescriptor
     *        is the {@link #getDescriptor() descriptor}.
     */
    public AbstractComponentInstantiationManager(
            ExtendedComponentDescriptorIF<S, I> componentDescriptor) {

        super();
        this.descriptor = componentDescriptor;
    }

    /**
     * This method gets the descriptor.
     * 
     * @return the descriptor.
     */
    public ExtendedComponentDescriptorIF<S, I> getDescriptor() {

        return this.descriptor;
    }

    /**
     * This method sets the descriptor.
     * 
     * @param newDescriptor
     *        is the descriptor to set.
     */
    public void setDescriptor(ExtendedComponentDescriptorIF<S, I> newDescriptor) {

        if (this.descriptor != null) {
            throw new NlsIllegalArgumentException("Descriptor already set!");
        }
        this.descriptor = newDescriptor;
    }

    /**
     * This method creates a new instance container. It should be called from
     * {@link ComponentInstantiationManagerIF#request(String)} if a new instance
     * should be created.
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the requested component.
     * @return the new instance container for the requested component.
     */
    protected ExtendedComponentInstanceContainer<S, I> createInstanceContainer(String instanceId) {

        ExtendedComponentInstanceContainer<S, I> instanceContainer = new ExtendedComponentInstanceContainer<S, I>(
                this.descriptor);
        instanceContainer.setInstanceId(instanceId);
        return instanceContainer;
    }
    
}
