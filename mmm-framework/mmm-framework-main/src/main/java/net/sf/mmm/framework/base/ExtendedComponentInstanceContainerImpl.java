/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainer;

/**
 * This is the implementation of the
 * {@link ExtendedComponentInstanceContainer} interface.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptor#getSpecification() specification}
 *        of the component.
 * @param <I>
 *        is the
 *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ExtendedComponentInstanceContainerImpl<S, I extends S> extends
        SimpleComponentInstanceContainer<S> implements ExtendedComponentInstanceContainer<S, I> {

    /** @see #getDescriptor() */
    private final ExtendedComponentDescriptor<S, I> descriptor;

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
    public ExtendedComponentInstanceContainerImpl(
            ExtendedComponentDescriptor<S, I> componentDescriptor) {

        super();
        this.descriptor = componentDescriptor;
        this.lifecycleState = null;
        this.privateInstance = null;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainer#getDescriptor()
     */
    public ExtendedComponentDescriptor<S, I> getDescriptor() {

        return this.descriptor;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainer#getLifecycleState()
     */
    public String getLifecycleState() {

        return this.lifecycleState;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainer#getPrivateInstance()
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
     * @see net.sf.mmm.framework.api.ExtendedComponentInstanceContainer#setLifecycleState(java.lang.String)
     */
    public void setLifecycleState(String state) {

        this.lifecycleState = state;
    }

}
