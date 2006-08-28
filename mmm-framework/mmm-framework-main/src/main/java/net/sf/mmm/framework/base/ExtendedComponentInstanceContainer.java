/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF;

/**
 * This is the implementation of the
 * {@link ExtendedComponentInstanceContainerIF} interface.
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
public class ExtendedComponentInstanceContainer<S, I extends S> extends
        ComponentInstanceContainer<S> implements ExtendedComponentInstanceContainerIF<S, I> {

    /** @see #getDescriptor() */
    private final ExtendedComponentDescriptorIF<S, I> descriptor;

    /** @see #getLifecycleState() */
    private String lifecycleState;

    /** @see #getPrivateInstance() */
    private I privateInstance;

    /**
     * The constructor.
     * 
     * @param componentDescriptor
     *        is the {@link #getDescriptor() descriptor} of the component
     *        instance.
     */
    public ExtendedComponentInstanceContainer(
            ExtendedComponentDescriptorIF<S, I> componentDescriptor) {

        super();
        this.descriptor = componentDescriptor;
        this.lifecycleState = null;
        this.privateInstance = null;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF#getDescriptor()
     * {@inheritDoc}
     */
    public ExtendedComponentDescriptorIF<S, I> getDescriptor() {

        return this.descriptor;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF#getLifecycleState()
     * {@inheritDoc}
     */
    public String getLifecycleState() {

        return this.lifecycleState;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF#getPrivateInstance()
     * {@inheritDoc}
     */
    public I getPrivateInstance() {

        return this.privateInstance;
    }

    /**
     * This method sets the {@link #getPrivateInstance() private instance}.
     * 
     * @param newPrivateInstance
     *        is the private instance to set.
     */
    public void setPrivateInstance(I newPrivateInstance) {

        this.privateInstance = newPrivateInstance;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF#setLifecycleState(java.lang.String)
     * {@inheritDoc}
     */
    public void setLifecycleState(String state) {

        this.lifecycleState = state;
    }

}
