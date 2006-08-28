/* $Id: ComponentInstantiationManagerIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.base;

import java.util.Iterator;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.base.provider.InstanceIdNotAvailableException;

/**
 * This is the interface for a manager that manages the instantiation of
 * specific components. An implementation of this interface represents a
 * specific instantiation policy (e.g. singleton, per-request, pooled, etc.).
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
public interface ComponentInstantiationManagerIF<S, I extends S> {

    /**
     * This method gets the instance container for the requested component
     * instance. The implementation decides if this will be a new and empty
     * container or the component will be reused and an existing
     * instance-container will be recycled.
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the requested component.
     * @return the instance container for the requested component.
     * @throws InstanceIdNotAvailableException
     *         if the component is NOT available for the requested
     *         <code>instanceId</code>.
     */
    ExtendedComponentInstanceContainer<S, I> request(String instanceId)
            throws InstanceIdNotAvailableException;

    /**
     * This method is called when a component instance is released. It decides
     * if the component in the given <code>instanceContainer</code> will be
     * shutdown or kept untouched to be reused.
     * 
     * @param instanceContainer
     *        is the container with the released component instance. This
     *        container should have been retrieved from the method
     *        {@link #request(String)}.
     * @return <code>true</code> if the component should be shut down,
     *         <code>false</code> otherwise.
     */
    boolean release(ExtendedComponentInstanceContainer<S, I> instanceContainer);

    /**
     * This method is called to dispose the component associated with this
     * instantiation manager. The instantiation manager must return all
     * containers with instances that are shared or pooled (where
     * {@link #release(ExtendedComponentInstanceContainer)} returned
     * <code>false</code>).
     * 
     * @return an array with all containers of shared instances.
     */
    ExtendedComponentInstanceContainer<S, I>[] dispose();

}
