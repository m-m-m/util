/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.Dependency;

/**
 * This is the interface for a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}
 * of a {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor component}.<br>
 * An instance of the {@link #getSpecification() depdency-specification} is
 * injected automatically into to the
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getImplementation() component}
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() declaring}
 * this dependency. According to the {@link #getInjectionType() injection-type}
 * this happens via the
 * {@lnik net.sf.mmm.framework.api.ExtendedComponentDescriptor#getConstructor() constructor},
 * a {@link #getInjectionMethod() setter} or a
 * {@link #getInjectionField() field}.<br>
 * 
 * @param <S>
 *        is the templated type of the {@link #getSpecification()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDependency<S> implements Dependency<S> {

    /** @see #getSpecification() */
    private final Class<S> spec;

    /** @see #getInstanceId() */
    private final String componentId;

    /**
     * The constructor.
     * 
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     * @param instanceId
     *        is the
     *        {@link SimpleComponentInstanceContainer#getInstanceId() instance-ID} of
     *        the dependency.
     */
    public AbstractDependency(Class<S> specification, String instanceId) {

        super();
        this.spec = specification;
        if ((instanceId == null) || (instanceId.length() == 0)) {
            this.componentId = ComponentManager.DEFAULT_INSTANCE_ID;
        } else {
            this.componentId = instanceId;
        }
    }

    /**
     * This method gets the
     * {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() sepcification}
     * of the dependency to inject.
     * 
     * @return the specification.
     */
    public final Class<S> getSpecification() {

        return this.spec;
    }

    /**
     * This method gets the
     * {@link SimpleComponentInstanceContainer#getInstanceId() instance-ID} of the
     * dependency.
     * 
     * @see net.sf.mmm.framework.api.ComponentManager#requestComponent(Class,
     *      String)
     * 
     * @return the instance-ID of the dependency.
     */
    public final String getInstanceId() {

        return this.componentId;
    }

    /**
     * This method gets a {@link Object#toString() string-representation} of the
     * target of the injection.
     * 
     * @return a description of the injection target.
     */
    public abstract String getInjectionTargetDescription();

    /**
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString() {

        return getInjectionType().toString() + "-Dependency on " + this.spec;
    }

}
