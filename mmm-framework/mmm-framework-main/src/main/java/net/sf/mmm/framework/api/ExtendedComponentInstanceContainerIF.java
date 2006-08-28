/* $Id$ */
package net.sf.mmm.framework.api;

/**
 * This is the interface for a {@link ComponentInstanceContainerIF container}
 * that also contains internal details of a managed component.
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
public interface ExtendedComponentInstanceContainerIF<S, I extends S> extends
        ComponentInstanceContainerIF<S> {

    /**
     * the {@link #getLifecycleState() lifecycle state} if
     * {@link DependencyIF.Type#FIELD field} injection is completed.
     */
    String LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE = "fieldInjectionComplete";

    /**
     * the {@link #getLifecycleState() lifecycle state} if
     * {@link DependencyIF.Type#SETTER setter} injection is completed.
     */
    String LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE = "setterInjectionComplete";

    /**
     * This method gets the descriptor of the component.
     * 
     * @return the descriptor.
     */
    ExtendedComponentDescriptorIF<S, I> getDescriptor();

    /**
     * This method gets the private instance of the component. The
     * {@link #getInstance() public instance} may be a
     * {@link ComponentProxyBuilderIF proxy} on this instance.
     * 
     * @return the private instance of the component.
     */
    I getPrivateInstance();

    /**
     * This method gets the current lifecycle-state of the component. This is
     * typically the latest
     * {@link ExtendedComponentDescriptorIF#getLifecycleMethod(String) lifecycle-phase}
     * that has been performed. In some situations the component might change
     * its state independently. E.g. if the phase
     * {@link net.sf.mmm.framework.api.LifecycleMethod#LIFECYCLE_EXECUTE "execute"}
     * was be performed and has completed, the
     * {@link #getLifecycleState() state} will become
     * {@link net.sf.mmm.framework.api.LifecycleMethod#LIFECYCLE_EXECUTED "executed"}.
     * 
     * @return the current lifecycle state or <code>null</code> if the
     *         lifecycle has NOT yet been started.
     */
    String getLifecycleState();

    /**
     * This method sets the {@link #getLifecycleState() lifecycle-state} to the
     * given <code>state</code>. This method should only be called by the
     * {@link LifecycleManagerIF lifecycle-manager}.
     * 
     * @param state
     *        is the new lifecycle phase to set.
     */
    void setLifecycleState(String state);

}
