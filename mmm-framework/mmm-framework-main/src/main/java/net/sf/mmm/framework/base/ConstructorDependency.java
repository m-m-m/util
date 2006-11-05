package net.sf.mmm.framework.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.framework.api.ComponentManager;

/**
 * This inner class is the implementation of {@link AbstractDependency} for the
 * {@link #getInjectionType() type}
 * {@link net.sf.mmm.framework.api.Dependency.Type#CONSTRUCTOR}.
 * 
 * @param <S>
 *        is the templated type of the {@link #getSpecification()}.
 */
public final class ConstructorDependency<S> extends AbstractDependency<S> {

    /**
     * The constructor.
     * 
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     */
    public ConstructorDependency(Class<S> specification) {

        this(specification, ComponentManager.DEFAULT_INSTANCE_ID);
    }

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
    public ConstructorDependency(Class<S> specification, String instanceId) {

        super(specification, instanceId);
    }

    /**
     * @see net.sf.mmm.framework.api.Dependency#getInjectionField()
     */
    public Field getInjectionField() {

        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.Dependency#getInjectionMethod()
     */
    public Method getInjectionMethod() {

        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.Dependency#getInjectionType()
     */
    public Type getInjectionType() {

        return Type.CONSTRUCTOR;
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractDependency#getInjectionTargetDescription()
     */
    public String getInjectionTargetDescription() {

        return "constructor";
    }

}