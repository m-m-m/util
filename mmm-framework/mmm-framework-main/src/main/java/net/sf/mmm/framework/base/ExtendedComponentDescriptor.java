/* $Id$ */
package net.sf.mmm.framework.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ContainerException;
import net.sf.mmm.framework.api.DependencyIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.api.DependencyIF.Type;
import net.sf.mmm.framework.base.descriptor.SimpleComponentDescriptor;
import net.sf.mmm.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.collection.AbstractReadOnlyLookaheadIterator;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the implementation of the {@link ExtendedComponentDescriptorIF}
 * interface.
 * 
 * @param <S>
 *        is the component {@link #getSpecification() specification}
 * @param <I>
 *        is the component {@link #getImplementation() implementation}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ExtendedComponentDescriptor<S, I extends S> extends SimpleComponentDescriptor<S>
        implements ExtendedComponentDescriptorIF<S, I> {

    /** @see #getImplementation() */
    private final Class<I> implementation;

    /** @see #getConstructor() */
    private final Constructor<I> constructor;

    /** @see #getInstantiationPolicy() */
    private final String policy;

    /** @see #getDependencies() */
    private final DependencyIF<?>[] constructorDependencies;

    /** @see #getDependencies() */
    private final DependencyIF<?>[] methodDependencies;

    /** @see #getDependencies() */
    private final DependencyIF<?>[] fieldDependencies;

    /** @see #getLifecycleMethod(String) */
    private final Map<String, LifecycleMethod> lifecycleMap;

    /** @see #getLifecycleMethods() */
    private final Collection<LifecycleMethod> lifecycleView;

    /**
     * The constructor.
     * 
     * @param componentSpecification
     *        is the {@link #getSpecification() specification} of the component.
     * @param componentConstructor
     *        is the {@link #getConstructor() constructor} of the component.
     * @param instantiationPolicy
     *        is the {@link #getInstantiationPolicy() policy} for the
     *        instantiation of the component.
     * @param dependencies
     *        are the {@link #getDependencies() dependencies} of the component.
     *        is the {@link #getConstructor() constructor}
     */
    public ExtendedComponentDescriptor(Class<S> componentSpecification,
            Constructor<I> componentConstructor, String instantiationPolicy,
            DependencyIF<?>[] dependencies) {

        this(componentSpecification, componentConstructor.getDeclaringClass(),
                componentConstructor, instantiationPolicy, dependencies, NO_LIFECYCLE);
    }

    /**
     * The constructor.
     * 
     * @param componentSpecification
     *        is the {@link #getSpecification() specification} of the component.
     * @param componentConstructor
     *        is the {@link #getConstructor() constructor} of the component.
     * @param instantiationPolicy
     *        is the {@link #getInstantiationPolicy() policy} for the
     *        instantiation of the component.
     * @param dependencies
     *        are the {@link #getDependencies() dependencies} of the component.
     *        is the {@link #getConstructor() constructor}
     * @param lifecycleMethods
     *        are the {@link #getLifecycleMethods() lifecycle-methods}.
     */
    public ExtendedComponentDescriptor(Class<S> componentSpecification,
            Constructor<I> componentConstructor, String instantiationPolicy,
            DependencyIF[] dependencies, LifecycleMethod[] lifecycleMethods) {

        this(componentSpecification, componentConstructor.getDeclaringClass(),
                componentConstructor, instantiationPolicy, dependencies, lifecycleMethods);
    }

    /**
     * The constructor.
     * 
     * @param componentSpecification
     *        is the {@link #getSpecification() specification} of the component.
     * @param componentImplementation
     *        is the {@link #getImplementation() implementation} of the
     *        component.
     * @param componentConstructor
     *        is the {@link #getConstructor() constructor} of the component.
     * @param instantiationPolicy
     *        is the {@link #getInstantiationPolicy() policy} for the
     *        instantiation of the component.
     * @param dependencies
     *        are the {@link #getDependencies() dependencies} of the component.
     *        is the {@link #getConstructor() constructor}
     * @param lifecycleMethods
     *        are the {@link #getLifecycleMethods() lifecycle-methods}.
     */
    private ExtendedComponentDescriptor(Class<S> componentSpecification,
            Class<I> componentImplementation, Constructor<I> componentConstructor,
            String instantiationPolicy, DependencyIF<?>[] dependencies,
            LifecycleMethod[] lifecycleMethods) {

        super(componentSpecification);
        this.implementation = componentImplementation;
        this.constructor = componentConstructor;
        this.policy = instantiationPolicy;
        // initialize lifecycle methods
        this.lifecycleMap = new HashMap<String, LifecycleMethod>();
        this.lifecycleView = Collections.unmodifiableCollection(this.lifecycleMap.values());
        for (int i = 0; i < lifecycleMethods.length; i++) {
            String phase = lifecycleMethods[i].getLifecyclePhase();
            if (this.lifecycleMap.containsKey(phase)) {
                throw new IllegalArgumentException("Duplicate lifecycle phase '" + phase + "'!");
            }
            Method method = lifecycleMethods[i].getLifecycleMethod();
            if (method.getParameterTypes().length != 0) {
                throw new IllegalArgumentException("Lifecycle method '" + method.getName()
                        + "' is illegal - must NOT take arguments!");
            }
            validateOwnership(method, this.implementation);
            this.lifecycleMap.put(phase, lifecycleMethods[i]);
        }

        Class<?>[] constructorArgTypes;
        if (this.constructor == null) {
            this.constructorDependencies = NO_DEPENDENCY;
            constructorArgTypes = ReflectionUtil.NO_PARAMETERS;
        } else {
            constructorArgTypes = this.constructor.getParameterTypes();
            this.constructorDependencies = new AbstractDependency[constructorArgTypes.length];
        }
        DependencyIF[] methodDeps = NO_DEPENDENCY;
        DependencyIF[] fieldDeps = NO_DEPENDENCY;
        if (dependencies.length != 0) {
            int constructorDepCount = 0;
            int methodDepCount = 0;
            int fieldDepCount = 0;
            for (int i = 0; i < dependencies.length; i++) {
                Class<?> dependencySpec = dependencies[i].getSpecification();
                if (dependencySpec == null) {
                    throw new NlsIllegalArgumentException("Dependency[" + i + "] is illegal in '"
                            + toString() + "': specification must NOT be null!");
                }
                switch (dependencies[i].getInjectionType()) {
                    case CONSTRUCTOR:
                        if (constructorDepCount < this.constructorDependencies.length) {
                            this.constructorDependencies[constructorDepCount] = dependencies[i];
                            if (!constructorArgTypes[constructorDepCount]
                                    .isAssignableFrom(dependencies[i].getSpecification())) {
                                throw new NlsIllegalArgumentException(
                                        NlsResourceBundle.ERR_DESCRIPTOR_CONSTRUCTOR_DEPENDENCY_TYPE,
                                        getSpecification(), Integer
                                                .valueOf(constructorDepCount + 1),
                                        constructorArgTypes[constructorDepCount], dependencies[i]
                                                .getSpecification());
                            }
                            constructorDepCount++;
                        } else {
                            throw new NlsIllegalArgumentException(
                                    NlsResourceBundle.ERR_DESCRIPTOR_CONSTRUCTOR_DEPENDENCY_COUNT,
                                    getSpecification(), Integer
                                            .valueOf(this.constructorDependencies.length), Integer
                                            .valueOf(constructorDepCount + 1));
                        }
                        break;
                    case FIELD:
                        fieldDepCount++;
                        assert (dependencies[i].getInjectionMethod() == null);
                        assert (dependencies[i].getInjectionField() != null);
                        Field field = dependencies[i].getInjectionField();
                        Class<?> fieldType = field.getType();
                        if ((fieldType != dependencySpec)
                                && !fieldType.isAssignableFrom(dependencySpec)) {
                            throw new NlsIllegalArgumentException("Dependency[" + i
                                    + "] is illegal: Field '" + field.getName() + "' has type '"
                                    + fieldType + "' which is NOT assignable to '" + dependencySpec
                                    + "'!");
                        }
                        Class<?> fieldClass = field.getDeclaringClass();
                        if ((fieldClass != this.implementation)
                                && !fieldClass.isAssignableFrom(this.implementation)) {
                            throw new NlsIllegalArgumentException("Dependency[" + i
                                    + "] is illegal: Field '" + field.getName() + "' declared in '"
                                    + fieldClass + "' does NOT belong to '" + this.implementation
                                    + "'!");
                        }
                        break;
                    case SETTER:
                        methodDepCount++;
                        assert (dependencies[i].getInjectionMethod() != null);
                        assert (dependencies[i].getInjectionField() == null);
                        break;
                    default :
                        throw new ContainerException(NlsResourceBundle.ERR_INJECTION_TYPE_ILLEGAL,
                                dependencies[i].getInjectionType(), getSpecification());
                }
            }
            if (constructorDepCount != this.constructorDependencies.length) {
                throw new NlsIllegalArgumentException(
                        NlsResourceBundle.ERR_DESCRIPTOR_CONSTRUCTOR_DEPENDENCY_COUNT,
                        getSpecification(), Integer.valueOf(this.constructorDependencies.length),
                        Integer.valueOf(constructorDepCount));
            }
            if (methodDepCount > 0) {
                methodDeps = new DependencyIF<?>[methodDepCount];
            }
            if (fieldDepCount > 0) {
                fieldDeps = new DependencyIF<?>[fieldDepCount];
            }
            methodDepCount = 0;
            fieldDepCount = 0;
            for (int i = 0; i < dependencies.length; i++) {
                switch (dependencies[i].getInjectionType()) {
                    case FIELD:
                        fieldDeps[fieldDepCount++] = dependencies[i];
                        break;
                    case SETTER:
                        methodDeps[methodDepCount++] = dependencies[i];
                        break;
                }
            }
        }
        this.methodDependencies = methodDeps;
        this.fieldDependencies = fieldDeps;

    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getImplementation()
     * 
     */
    public Class<I> getImplementation() {

        return this.implementation;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getConstructor()
     * 
     */
    public Constructor<I> getConstructor() {

        return this.constructor;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getInstantiationPolicy()
     * 
     */
    public String getInstantiationPolicy() {

        return this.policy;
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies()
     * 
     */
    public Iterator<DependencyIF> getDependencies() {

        return new DependencyIterator(null);
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies(net.sf.mmm.framework.api.DependencyIF.Type)
     * 
     */
    public Iterator<DependencyIF> getDependencies(Type type) {

        return new DependencyIterator(type);
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getLifecycleMethod(java.lang.String)
     * 
     */
    public LifecycleMethod getLifecycleMethod(String phase) {

        return this.lifecycleMap.get(phase);
    }

    /**
     * @see net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getLifecycleMethods()
     * 
     */
    public Iterator<LifecycleMethod> getLifecycleMethods() {

        return this.lifecycleView.iterator();
    }

    /**
     * This method validates that the given <code>method</code> belongs to the
     * given <code>implementation</code>.
     * 
     * @param method
     *        is the method to validate.
     * @param implementation
     *        is the class that should own the method.
     */
    private static void validateOwnership(Method method, Class<?> implementation) {

        Class<?> declaringClass = method.getDeclaringClass();
        if ((declaringClass != implementation) && (declaringClass.isAssignableFrom(implementation))) {
            throw new NlsIllegalArgumentException("Illegal component descriptor - method '"
                    + method.getName() + "' declared in '" + declaringClass
                    + "' does NOT belong to implementation '" + implementation + "'!");
        }
    }

    /**
     * This inner class is a read-only iterator for dependencies.
     */
    private class DependencyIterator extends AbstractReadOnlyLookaheadIterator<DependencyIF> {

        /** @see ExtendedComponentDescriptor#getDependencies(Type) */
        private final Type type;

        /** the current index in {@link #currentArray} */
        private int currentIndex;

        /** the current dependency array */
        private DependencyIF<?>[] currentArray;

        /**
         * The constructor.
         * 
         * @param dependencyType
         *        is the type of the dependecies to iterate or <code>null</code>
         *        to iterate all dependencies.
         */
        public DependencyIterator(Type dependencyType) {

            super();
            this.type = dependencyType;
            this.currentIndex = 0;
            if (this.type == Type.FIELD) {
                this.currentArray = ExtendedComponentDescriptor.this.fieldDependencies;
            } else if (this.type == Type.SETTER) {
                this.currentArray = ExtendedComponentDescriptor.this.methodDependencies;
            } else {
                this.currentArray = ExtendedComponentDescriptor.this.constructorDependencies;
            }
            findFirst();
        }

        /**
         * @see net.sf.mmm.util.collection.AbstractReadOnlyLookaheadIterator#findNext()
         * 
     */
        @Override
        protected DependencyIF<?> findNext() {

            if (this.currentIndex < this.currentArray.length) {
                return this.currentArray[this.currentIndex++];
            }
            if (this.type == null) {
                if (this.currentArray == ExtendedComponentDescriptor.this.constructorDependencies) {
                    this.currentArray = ExtendedComponentDescriptor.this.fieldDependencies;
                    this.currentIndex = 0;
                    return findNext();
                } else if (this.currentArray == ExtendedComponentDescriptor.this.fieldDependencies) {
                    this.currentArray = ExtendedComponentDescriptor.this.methodDependencies;
                    this.currentIndex = 0;
                    return findNext();
                }
            }
            return null;
        }

    }

}
