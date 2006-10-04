/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.base.provider.InstanceIdNotAvailableException;

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
public abstract class AbstractDefaultComponentInstantiationManager<S, I extends S> extends
        AbstractComponentInstantiationManager<S, I> {

    /**
     * The constructor.
     */
    public AbstractDefaultComponentInstantiationManager() {

        super();
    }

    /**
     * @see AbstractComponentInstantiationManager#AbstractComponentInstantiationManager(ExtendedComponentDescriptorIF)
     * 
     */
    public AbstractDefaultComponentInstantiationManager(
            ExtendedComponentDescriptorIF<S, I> componentDescriptor) {

        super(componentDescriptor);
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#request(String)
     * 
     */
    public final ExtendedComponentInstanceContainer<S, I> request(String instanceId)
            throws InstanceIdNotAvailableException {

        if (ComponentManagerIF.DEFAULT_INSTANCE_ID.equals(instanceId)) {
            return requestDefault();
        } else {
            return requestByInstanceId(instanceId);
        }
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#request(String)
     * 
     * @return the container for the
     *         {@link ComponentManagerIF#DEFAULT_INSTANCE_ID default} instance.
     */
    public abstract ExtendedComponentInstanceContainer<S, I> requestDefault();

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#request(String)
     * 
     * @param instanceId
     * @return the requested instance-ID
     * @throws InstanceIdNotAvailableException
     *         if the component is NOT available for the requested
     *         <code>instanceId</code>.
     */
    public ExtendedComponentInstanceContainer<S, I> requestByInstanceId(String instanceId)
            throws InstanceIdNotAvailableException {

        throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
    }

}
