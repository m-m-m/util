/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This is the interface for a {@link ComponentInstanceContainer container}
 * that also contains internal details of a managed component.
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
public interface ExtendedComponentInstanceContainer<S, I extends S> extends
        ComponentInstanceContainer<S> {

    /**
     * the {@link #getLifecycleState() lifecycle state} if
     * {@link Dependency.Type#FIELD field} injection is completed.
     */
    String LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE = "fieldInjectionComplete";

    /**
     * the {@link #getLifecycleState() lifecycle state} if
     * {@link Dependency.Type#SETTER setter} injection is completed.
     */
    String LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE = "setterInjectionComplete";

    /**
     * This method gets the descriptor of the component.
     * 
     * @return the descriptor.
     */
    ExtendedComponentDescriptor<S, I> getDescriptor();

    /**
     * This method gets the private instance of the component. The
     * {@link #getInstance() public instance} may be a
     * {@link ComponentProxyBuilder proxy} on this instance.
     * 
     * @return the private instance of the component.
     */
    I getPrivateInstance();

    /**
     * This method gets the current lifecycle-state of the component. This is
     * typically the latest
     * {@link ExtendedComponentDescriptor#getLifecycleMethod(String) lifecycle-phase}
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
     * {@link LifecycleManager lifecycle-manager}.
     * 
     * @param state
     *        is the new lifecycle phase to set.
     */
    void setLifecycleState(String state);

}
