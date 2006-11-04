/* $Id$ */
package net.sf.mmm.framework.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.Dependency;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.base.ConstructorDependency;
import net.sf.mmm.framework.base.AbstractDependency;
import net.sf.mmm.framework.base.ExtendedComponentDescriptorImpl;
import net.sf.mmm.framework.base.SetterDependency;
import net.sf.mmm.util.reflect.AnnotationUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This class is used to {@link #createDescriptor(String, String) create}
 * {@link ExtendedComponentDescriptor}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DescriptorBuilder {

    /** the classloader to use */
    private final ClassLoader classloader;

    /** the lifecycle method for {@link Runnable#run()} */
    private final LifecycleMethod lifecycleExceute;

    /**
     * The constructor.
     */
    public DescriptorBuilder() {

        this(Thread.currentThread().getClass().getClassLoader());
    }

    /**
     * The constructor.
     * 
     * @param loader
     *        is the classloader used to load classes.
     */
    public DescriptorBuilder(ClassLoader loader) {

        super();
        this.classloader = loader;
        // Runnable#run()
        Method runMethod;
        try {
            runMethod = Runnable.class.getMethod("run", ReflectionUtil.NO_PARAMETERS);
        } catch (Exception e) {
            throw new IllegalStateException("Internal Error!", e);
        }
        this.lifecycleExceute = new LifecycleMethod(LifecycleMethod.LIFECYCLE_EXECUTE, runMethod);
    }

    /**
     * This method finds the
     * {@link ExtendedComponentDescriptor#getConstructor() constructor} used
     * to instantiate the
     * {@link ExtendedComponentDescriptor#getImplementation() component implementation}.
     * If there is more than one constructor, the one with the fewest number of
     * {@link Constructor#getParameterTypes() arguments} is choosen. If this is
     * NOT unique, the result is unspecified.
     * 
     * @param <I>
     *        is the templated type of the
     *        {@link ExtendedComponentDescriptor#getImplementation() component implementation}.
     * @param implementation
     *        is the
     *        {@link ExtendedComponentDescriptor#getImplementation() component implementation}.
     * @return the requested constructor.
     * @throws ComponentException
     *         if the given <code>implementation</code> is illegal (e.g. has
     *         no public constructor).
     */
    public <I> Constructor<I> findConstructor(Class<I> implementation) throws ComponentException {

        Constructor<?>[] constructors = implementation.getConstructors();
        if (constructors.length == 0) {
            throw new ComponentException(NlsResourceBundle.ERR_NO_CONSTRUCTOR, implementation);
        }
        Constructor<I> result = null;
        if (constructors.length == 1) {
            result = (Constructor<I>) constructors[0];
        } else {
            int currentArgLen = Integer.MAX_VALUE;
            for (int i = 0; i < constructors.length; i++) {
                int argLen = constructors[i].getParameterTypes().length;
                if (argLen < currentArgLen) {
                    result = (Constructor<I>) constructors[i];
                    currentArgLen = argLen;
                }
                // if (argLen == 0) { break; }
            }
        }
        return result;
    }

    /**
     * This method determines the
     * {@link ExtendedComponentDescriptor#getInstantiationPolicy() instantiation-policy}
     * of the component.
     * 
     * @param implementation
     *        is the
     *        {@link ExtendedComponentDescriptor#getImplementation() component implementation}.
     * @return the
     *         {@link ExtendedComponentDescriptor#getInstantiationPolicy() instantiation-policy}
     *         to be used for the given <code>implementation</code>.
     */
    public String getInstantiationPolicy(Class<?> implementation) {

        String policy = ExtendedComponentDescriptor.INSTANTIATION_POLICY_SINGLETON;
        Resource classAnnotation = AnnotationUtil
                .getClassAnnotation(implementation, Resource.class);
        if (classAnnotation != null) {
            if (!classAnnotation.shareable()) {
                policy = ExtendedComponentDescriptor.INSTANTIATION_POLICY_PER_REQUEST;
            }
        }
        return policy;
    }

    /**
     * This method creates the {@link ConstructorDependency} values for the
     * given <code>constructor</code> and adds them to the given
     * <code>dependencies</code>.
     * 
     * @param constructor
     *        is the constructor to build the dependencies for.
     * @param dependencies
     *        is the list where to add the dependencies.
     */
    public void addConstructorDependencies(Constructor<?> constructor,
            List<Dependency> dependencies) {

        Class<?>[] constructorArgs = constructor.getParameterTypes();
        if (constructorArgs.length > 0) {
            // Annotation[][] constrcutorAnnoations =
            // constructor.getParameterAnnotations();
            for (int i = 0; i < constructorArgs.length; i++) {
                /*
                 * Resource constructorArgResource = null; for (int j = 0; j <
                 * constrcutorAnnoations[i].length; j++) { if (Resource.class ==
                 * constrcutorAnnoations[i][j].getClass()) {
                 * constructorArgResource = (Resource)
                 * constrcutorAnnoations[i][j]; break; } }
                 */
                String instanceId = null;
                /*
                 * if (constructorArgResource != null) { instanceId =
                 * constructorArgResource.name(); }
                 */
                AbstractDependency dep = new ConstructorDependency(constructorArgs[i], instanceId);
                dependencies.add(dep);
            }
        }
    }

    /**
     * This method creates the descriptor for the given
     * {@link ExtendedComponentDescriptor#getSpecification() specification}
     * and
     * {@link ExtendedComponentDescriptor#getImplementation() implementation}.
     * 
     * @param <S>
     *        is the component
     *        {@link ExtendedComponentDescriptor#getSpecification() specification}
     * @param <I>
     *        is the component
     *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
     * @param specification
     *        is the component
     *        {@link ExtendedComponentDescriptor#getSpecification() specification}
     * @param implementation
     *        is the component
     *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
     * @return the according descriptor.
     */
    public <S, I extends S> ExtendedComponentDescriptor<S, I> createDescriptor(
            Class<S> specification, Class<I> implementation) {

        String policy = getInstantiationPolicy(implementation);

        List<Dependency> dependencies = new ArrayList<Dependency>();
        Constructor<I> constructor = findConstructor(implementation);
        addConstructorDependencies(constructor, dependencies);

        List<LifecycleMethod> lifecycleMethods = new ArrayList<LifecycleMethod>();
        if (implementation.isAssignableFrom(Runnable.class)) {
            lifecycleMethods.add(this.lifecycleExceute);
        }
        Method[] methods = implementation.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Class<?>[] parameterTypes = methods[i].getParameterTypes();
            if (parameterTypes.length == 1) {
                Resource resourceAnnotation = AnnotationUtil.getMethodAnnotation(methods[i],
                        Resource.class);
                if (resourceAnnotation != null) {
                    String instanceId = resourceAnnotation.name();
                    AbstractDependency<?> dependency = new SetterDependency(methods[i],
                            parameterTypes[0], instanceId);
                    dependencies.add(dependency);
                }
            }
            if (parameterTypes.length == 0) {
                // lifecycle methods???
                PostConstruct init = AnnotationUtil.getMethodAnnotation(methods[i],
                        PostConstruct.class);
                if (init != null) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_INITIALIZE,
                            methods[i]));
                }
                PreDestroy dispose = AnnotationUtil.getMethodAnnotation(methods[i],
                        PreDestroy.class);
                if (dispose != null) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_DISPOSE,
                            methods[i]));
                }
                String methodName = methods[i].getName();
                if (LifecycleMethod.LIFECYCLE_START.equals(methodName)) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_START,
                            methods[i]));
                } else if (LifecycleMethod.LIFECYCLE_STOP.equals(methodName)) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_STOP,
                            methods[i]));
                } else if (LifecycleMethod.LIFECYCLE_PAUSE.equals(methodName)) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_PAUSE,
                            methods[i]));
                } else if (LifecycleMethod.LIFECYCLE_RESUME.equals(methodName)) {
                    lifecycleMethods.add(new LifecycleMethod(LifecycleMethod.LIFECYCLE_RESUME,
                            methods[i]));
                }

            }
            // allMethods[i].getAnnotation(annotationClass);
        }
        LifecycleMethod[] lifecycleMethodsArray = new LifecycleMethod[lifecycleMethods.size()];
        lifecycleMethodsArray = lifecycleMethods.toArray(lifecycleMethodsArray);
        Dependency[] dependenciesArray = new Dependency[dependencies.size()];
        dependenciesArray = dependencies.toArray(dependenciesArray);
        ExtendedComponentDescriptor<S, I> descriptor = new ExtendedComponentDescriptorImpl<S, I>(
                specification, constructor, policy, dependenciesArray, lifecycleMethodsArray);
        return descriptor;
    }

    /**
     * 
     * @param specificationName
     *        is the {@link Class#getName() name} of the component
     *        {@link ExtendedComponentDescriptor#getSpecification() specification}
     * @param implementationName
     *        is the {@link Class#getName() name} of the component
     *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
     * @return the according descriptor.
     */
    public ExtendedComponentDescriptor createDescriptor(String specificationName,
            String implementationName) {

        try {
            Class specification = this.classloader.loadClass(specificationName);
            Class implementation = this.classloader.loadClass(implementationName);
            return createDescriptor(specification, implementation);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ConfigurationException(e, "");
        }
    }

    /**
     * The main method to run this class.
     * 
     * @param args
     *        are the command-line arguments.
     */
    public static void main(String[] args) {

        System.out.println(AnnotationUtil.getClassAnnotation(DescriptorBuilder.class,
                PreDestroy.class));
    }

}
