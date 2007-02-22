/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.api.ComponentProxyBuilder;
import net.sf.mmm.framework.api.ContainerException;
import net.sf.mmm.framework.api.Dependency;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainer;
import net.sf.mmm.framework.api.IocException;
import net.sf.mmm.framework.api.LifecycleManager;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.api.Dependency.Type;
import net.sf.mmm.framework.base.ComponentInstantiationManager;
import net.sf.mmm.framework.base.ExtendedComponentInstanceContainerImpl;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is a generic implementation of the {@link ComponentProvider}
 * interface.
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
public class GenericComponentProvider<S, I extends S> implements ComponentProvider<S> {

    /** @see #getDescriptor() */
    private final ExtendedComponentDescriptor<S, I> descriptor;

    /** @see #request(String, ComponentDescriptor, String, ComponentManager) */
    private final ComponentInstantiationManager<S, I> instantiationManager;

    /**
     * The constructor.
     * 
     * @param componentDescriptor
     * @param instantiationPolicyManager
     */
    public GenericComponentProvider(ExtendedComponentDescriptor<S, I> componentDescriptor,
            ComponentInstantiationManager<S, I> instantiationPolicyManager) {

        super();
        this.descriptor = componentDescriptor;
        this.instantiationManager = instantiationPolicyManager;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#getDescriptor()
     */
    public ExtendedComponentDescriptor<S, I> getDescriptor() {

        return this.descriptor;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#dispose(net.sf.mmm.framework.api.ComponentInstanceContainer, net.sf.mmm.framework.api.ComponentManager)
     */
    public void dispose(ComponentInstanceContainer<S> instanceContainer, ComponentManager componentManager) {
    
        // TODO !!!
        System.out.println("shutdown " + instanceContainer);
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#release(net.sf.mmm.framework.api.ComponentInstanceContainer,
     *      net.sf.mmm.framework.api.ComponentManager) 
     */
    public boolean release(ComponentInstanceContainer<S> instanceContainer,
            ComponentManager componentManager) {

        ExtendedComponentInstanceContainerImpl<S, I> extendedInstanceContainer = (ExtendedComponentInstanceContainerImpl<S, I>) instanceContainer;
        boolean release = this.instantiationManager.release(extendedInstanceContainer);
        if (release) {
            dispose(extendedInstanceContainer, componentManager);
        }
        return release;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#request(java.lang.String,
     *      net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String,
     *      net.sf.mmm.framework.api.ComponentManager)
     */
    public ExtendedComponentInstanceContainerImpl<S, I> request(String instanceId,
            ComponentDescriptor sourceDescriptor, String sourceInstanceId,
            ComponentManager componentManager) throws ComponentException {

        ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = this.instantiationManager
                .request(instanceId);
        if (instanceContainer.getInstance() == null) {
            I privateInstance = instanceContainer.getPrivateInstance();
            // instantiation
            if (privateInstance == null) {
                privateInstance = instantiateComponent(instanceId, sourceDescriptor,
                        sourceInstanceId, componentManager);
                instanceContainer.setPrivateInstance(privateInstance);
            }
            // field injection
            if (instanceContainer.getLifecycleState() == null) {
                performFieldInjection(privateInstance, instanceId, sourceDescriptor,
                        sourceInstanceId, componentManager);
                instanceContainer
                        .setLifecycleState(ExtendedComponentInstanceContainer.LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE);
            }
            // setter injection
            if (ExtendedComponentInstanceContainer.LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE
                    .equals(instanceContainer.getLifecycleState())) {

                performSetterInjection(privateInstance, instanceId, sourceDescriptor,
                        sourceInstanceId, componentManager);
                instanceContainer
                        .setLifecycleState(ExtendedComponentInstanceContainer.LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE);
            }
            // real lifecycle
            if (ExtendedComponentInstanceContainer.LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE
                    .equals(instanceContainer.getLifecycleState())) {

                Iterator<LifecycleMethod> lifecycleIterator = this.descriptor.getLifecycleMethods();
                if (lifecycleIterator.hasNext()) {
                    LifecycleManager lifecycleManager = componentManager
                            .requestComponent(LifecycleManager.class);
                    Set<String> phases = lifecycleManager.getLifecyclePhases();
                    while (lifecycleIterator.hasNext()) {
                        LifecycleMethod lifecycleMethod = lifecycleIterator.next();
                        if (!phases.contains(lifecycleMethod.getLifecyclePhase())) {
                            throw new ContainerException("Lifecycle phase '"
                                    + lifecycleMethod.getLifecyclePhase()
                                    + "' not supported by lifecycle manager '" + lifecycleManager
                                    + "'");
                        }
                    }
                    lifecycleManager.setupComponent(instanceContainer);
                    componentManager.releaseComponent(lifecycleManager);
                }
            }
            // proxy
            S publicInstance;
            if (componentManager.hasComponent(ComponentProvider.class)) {
                ComponentProxyBuilder proxyBuilder = componentManager
                        .requestComponent(ComponentProxyBuilder.class);
                publicInstance = proxyBuilder.createProxy(this.descriptor, privateInstance);
            } else {
                publicInstance = privateInstance;
            }
            instanceContainer.setInstance(publicInstance);
        }
        return instanceContainer;
    }

    /**
     * 
     * @param instanceId
     * @param sourceDescriptor
     * @param sourceInstanceId
     * @param componentManager
     * @return
     * @throws ComponentInstantiationException
     */
    protected I instantiateComponent(String instanceId, ComponentDescriptor sourceDescriptor,
            String sourceInstanceId, ComponentManager componentManager)
            throws ComponentInstantiationException {

        try {
            Constructor<I> constructor = getDescriptor().getConstructor();
            if (constructor == null) {
                throw new NullPointerException("no constructor!");
            }
            Class<?>[] argTypes = constructor.getParameterTypes();
            if (argTypes.length == 0) {
                return constructor.newInstance(ReflectionUtil.NO_ARGUMENTS);
            }
            Object[] args = new Object[argTypes.length];
            int argIndex = 0;
            Iterator<Dependency> dependencyIterator = this.descriptor
                    .getDependencies(Type.CONSTRUCTOR);
            while (dependencyIterator.hasNext()) {
                Dependency dependency = dependencyIterator.next();
                Class<?> depSpec = dependency.getSpecification();
                // TODO: how to handle configuration dependencies???

                // This check is actually too late here...
                if (!argTypes[argIndex].isAssignableFrom(depSpec)) {

                }
                args[argIndex] = requestDependency(componentManager, instanceId, dependency);
                argIndex++;
            }
            return constructor.newInstance(args);
        } catch (IocException e) {
            throw e;
        } catch (Exception e) {
            throw new ComponentInstantiationException(this.descriptor.getSpecification(),
                    this.descriptor.getImplementation(), ComponentManager.DEFAULT_INSTANCE_ID, e);
        }
    }

    /**
     * 
     * @param instance
     * @param instanceId
     * @param sourceDescriptor
     * @param sourceInstanceId
     * @param componentManager
     * @throws ComponentException
     */
    protected void performFieldInjection(I instance, String instanceId,
            ComponentDescriptor sourceDescriptor, String sourceInstanceId,
            ComponentManager componentManager) throws ComponentException {

        try {
            Iterator<Dependency> dependencyIterator = this.descriptor.getDependencies(Type.FIELD);
            while (dependencyIterator.hasNext()) {
                Dependency<?> dependency = dependencyIterator.next();
                Object component = requestDependency(componentManager, instanceId, dependency);
                dependency.getInjectionField().set(instance, component);
            }
        } catch (IocException e) {
            throw e;
        } catch (Exception e) {
            throw new ComponentInstantiationException(this.descriptor.getSpecification(),
                    this.descriptor.getImplementation(), ComponentManager.DEFAULT_INSTANCE_ID, e);
        }
    }

    /**
     * 
     * @param instance
     * @param instanceId
     * @param sourceDescriptor
     * @param sourceInstanceId
     * @param componentManager
     * @throws ComponentException
     */
    protected void performSetterInjection(I instance, String instanceId,
            ComponentDescriptor sourceDescriptor, String sourceInstanceId,
            ComponentManager componentManager) throws ComponentException {

        Iterator<Dependency> dependencyIterator = this.descriptor.getDependencies(Type.SETTER);
        while (dependencyIterator.hasNext()) {
            Dependency<?> dependency = dependencyIterator.next();
            Object component = requestDependency(componentManager, instanceId, dependency);
            try {
                dependency.getInjectionMethod().invoke(instance, component);
            } catch (Exception e) {
                throw new ComponentInjectionException(e, component, dependency, instance);
            }
        }
    }

    /**
     * 
     * @param componentManager
     * @param instanceId
     * @param dependency
     * @return the requested dependency.
     * @throws DependencyCreationException
     */
    protected Object requestDependency(ComponentManager componentManager, String instanceId,
            Dependency<?> dependency) throws DependencyCreationException {

        try {
            return componentManager.requestComponent(dependency.getSpecification(), dependency
                    .getInstanceId());
        } catch (ComponentException e) {
            throw new DependencyCreationException(this.descriptor, instanceId, dependency, e);
        }
    }

}
