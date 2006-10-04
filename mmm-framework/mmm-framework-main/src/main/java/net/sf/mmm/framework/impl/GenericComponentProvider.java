/* $Id$ */
package net.sf.mmm.framework.impl;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.api.ComponentProxyBuilderIF;
import net.sf.mmm.framework.api.ContainerException;
import net.sf.mmm.framework.api.DependencyIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF;
import net.sf.mmm.framework.api.IocException;
import net.sf.mmm.framework.api.LifecycleManagerIF;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.api.DependencyIF.Type;
import net.sf.mmm.framework.base.ComponentInstantiationManagerIF;
import net.sf.mmm.framework.base.ExtendedComponentInstanceContainer;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is a generic implementation of the {@link ComponentProviderIF}
 * interface.
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
public class GenericComponentProvider<S, I extends S> implements ComponentProviderIF<S> {

    /** @see #getDescriptor() */
    private final ExtendedComponentDescriptorIF<S, I> descriptor;

    /** @see #request(String, ComponentDescriptorIF, String, ComponentManagerIF) */
    private final ComponentInstantiationManagerIF<S, I> instantiationManager;

    /**
     * The constructor.
     * 
     * @param componentDescriptor
     * @param instantiationPolicyManager
     */
    public GenericComponentProvider(ExtendedComponentDescriptorIF<S, I> componentDescriptor,
            ComponentInstantiationManagerIF<S, I> instantiationPolicyManager) {

        super();
        this.descriptor = componentDescriptor;
        this.instantiationManager = instantiationPolicyManager;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#getDescriptor()
     *      
     */
    public ExtendedComponentDescriptorIF<S, I> getDescriptor() {

        return this.descriptor;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#dispose(net.sf.mmm.framework.api.ComponentInstanceContainerIF, net.sf.mmm.framework.api.ComponentManagerIF)
     * 
     */
    public void dispose(ComponentInstanceContainerIF<S> instanceContainer, ComponentManagerIF componentManager) {
    
        // TODO !!!
        System.out.println("shutdown " + instanceContainer);
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#release(net.sf.mmm.framework.api.ComponentInstanceContainerIF,
     *      net.sf.mmm.framework.api.ComponentManagerIF) 
     */
    public boolean release(ComponentInstanceContainerIF<S> instanceContainer,
            ComponentManagerIF componentManager) {

        ExtendedComponentInstanceContainer<S, I> extendedInstanceContainer = (ExtendedComponentInstanceContainer<S, I>) instanceContainer;
        boolean release = this.instantiationManager.release(extendedInstanceContainer);
        if (release) {
            dispose(extendedInstanceContainer, componentManager);
        }
        return release;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#request(java.lang.String,
     *      net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String,
     *      net.sf.mmm.framework.api.ComponentManagerIF)
     */
    public ExtendedComponentInstanceContainer<S, I> request(String instanceId,
            ComponentDescriptorIF sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        ExtendedComponentInstanceContainer<S, I> instanceContainer = this.instantiationManager
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
                        .setLifecycleState(ExtendedComponentInstanceContainerIF.LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE);
            }
            // setter injection
            if (ExtendedComponentInstanceContainerIF.LIFECYCLE_STATE_FIELD_INJECTION_COMPLETE
                    .equals(instanceContainer.getLifecycleState())) {

                performSetterInjection(privateInstance, instanceId, sourceDescriptor,
                        sourceInstanceId, componentManager);
                instanceContainer
                        .setLifecycleState(ExtendedComponentInstanceContainerIF.LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE);
            }
            // real lifecycle
            if (ExtendedComponentInstanceContainerIF.LIFECYCLE_STATE_SETTER_INJECTION_COMPLETE
                    .equals(instanceContainer.getLifecycleState())) {

                Iterator<LifecycleMethod> lifecycleIterator = this.descriptor.getLifecycleMethods();
                if (lifecycleIterator.hasNext()) {
                    LifecycleManagerIF lifecycleManager = componentManager
                            .requestComponent(LifecycleManagerIF.class);
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
            if (componentManager.hasComponent(ComponentProviderIF.class)) {
                ComponentProxyBuilderIF proxyBuilder = componentManager
                        .requestComponent(ComponentProxyBuilderIF.class);
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
    protected I instantiateComponent(String instanceId, ComponentDescriptorIF sourceDescriptor,
            String sourceInstanceId, ComponentManagerIF componentManager)
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
            Iterator<DependencyIF> dependencyIterator = this.descriptor
                    .getDependencies(Type.CONSTRUCTOR);
            while (dependencyIterator.hasNext()) {
                DependencyIF dependency = dependencyIterator.next();
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
                    this.descriptor.getImplementation(), ComponentManagerIF.DEFAULT_INSTANCE_ID, e);
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
            ComponentDescriptorIF sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        try {
            Iterator<DependencyIF> dependencyIterator = this.descriptor.getDependencies(Type.FIELD);
            while (dependencyIterator.hasNext()) {
                DependencyIF<?> dependency = dependencyIterator.next();
                Object component = requestDependency(componentManager, instanceId, dependency);
                dependency.getInjectionField().set(instance, component);
            }
        } catch (IocException e) {
            throw e;
        } catch (Exception e) {
            throw new ComponentInstantiationException(this.descriptor.getSpecification(),
                    this.descriptor.getImplementation(), ComponentManagerIF.DEFAULT_INSTANCE_ID, e);
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
            ComponentDescriptorIF sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        Iterator<DependencyIF> dependencyIterator = this.descriptor.getDependencies(Type.SETTER);
        while (dependencyIterator.hasNext()) {
            DependencyIF<?> dependency = dependencyIterator.next();
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
    protected Object requestDependency(ComponentManagerIF componentManager, String instanceId,
            DependencyIF<?> dependency) throws DependencyCreationException {

        try {
            return componentManager.requestComponent(dependency.getSpecification(), dependency
                    .getInstanceId());
        } catch (ComponentException e) {
            throw new DependencyCreationException(this.descriptor, instanceId, dependency, e);
        }
    }

}
