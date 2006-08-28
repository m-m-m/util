/* $Id: DependencyIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This is the interface for a
 * {@link ExtendedComponentDescriptorIF#getDependencies() dependency} of a
 * {@link ComponentProviderIF component}.<br>
 * An instance of the {@link #getSpecification() depdency-specification} is
 * injected automatically into to the {@link ComponentProviderIF component}
 * {@link ExtendedComponentDescriptorIF#getDependencies() declaring} this
 * dependency. According to the {@link #getInjectionType() injection-type} this
 * happens via the {@lnik ComponentDescriptor#getConstructor() constructor}, a
 * {@link #getInjectionMethod() setter} or a {@link #getInjectionField() field}.<br>
 * 
 * @param <S>
 *        is the templated type of the {@link #getSpecification()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DependencyIF<S> {

    /**
     * This method gets the
     * {@link ComponentDescriptorIF#getSpecification() sepcification} of the
     * dependend component to inject.
     * 
     * @return the specification.
     */
    Class<S> getSpecification();

    /**
     * This method gets the
     * {@link ComponentManagerIF#requestComponent(Class, String) instance-ID} of
     * the dependency.
     * 
     * @see ComponentManagerIF#requestComponent(Class, String)
     * 
     * @return the instance-ID of the dependency or <code>null</code> for the
     *         {@link ComponentManagerIF#requestComponent(Class) default instance}.
     */
    String getInstanceId();

    /**
     * This method gets the type defining how the dependency is injected.
     * 
     * @return the injection type.
     */
    Type getInjectionType();

    /**
     * This method gets the {@link Method method} that is used to inject the
     * instance of the dependency {@link #getSpecification() specification}.
     * The method must take a {@link Method#getParameterTypes() single argument}
     * that is {@link Class#isAssignableFrom(Class) assignable} from the
     * {@link #getSpecification() specification}. Further the method must be
     * available in the
     * {@link ExtendedComponentDescriptorIF#getImplementation() component implementation}
     * and should be public.<br>
     * It is recommendet that the method is a setter according to the JAVA beans
     * standard. The method should only store the injected object and especially
     * not rely that another setter dependency is injected before.<br>
     * TODO: Example
     * 
     * @return the method where to inject the dependency if the
     *         {@link #getInjectionType() injection-type} is {@link Type#SETTER},
     *         <code>null</code> otherwise.
     */
    Method getInjectionMethod();

    /**
     * This method gets the {@link Field field} that is used to inject the
     * instance of the dependency {@link #getSpecification() specification}.
     * The field {@link Field#getType() type} must be
     * {@link Class#isAssignableFrom(Class) assignable} from the
     * {@link #getSpecification() specification}.<br>
     * ATTENTION: Field injection is NOT recommended!!! Using non-final public
     * fields is bad design. Anything else requires that the framework injecting
     * the dependency is allowed to do so by the security manager. Additionally
     * this prevents easy testing. This injection type is only supported to
     * allow integrating existing components using field injection.
     * 
     * @return the field where to inject the dependency if the
     *         {@link #getInjectionType() injection-type} is {@link Type#FIELD},
     *         <code>null</code> otherwise.
     */
    Field getInjectionField();

    /**
     * An enumeration of the availabe
     * {@link #getInjectionType() injection-types}.
     */
    public static enum Type {

        /**
         * This is the type of a dependency that is injected into the
         * {@link ExtendedComponentDescriptorIF#getConstructor() constructor}.
         */
        CONSTRUCTOR,

        /**
         * This is the type of a dependency that is injected into a
         * {@link #getInjectionMethod() method}.
         */
        SETTER,

        /**
         * This is the type of a dependency that is injected into a
         * {@link DependencyIF#getInjectionField() field}.<br>
         * ATTENTION: Field injection is NOT recommended!!! Using non-final
         * public fields is bad design. Anything else requires that the
         * framework injecting the dependency is allowed to do so by the
         * security manager. Additionally this prevents easy testing. This
         * injection type is only supported to allow integrating existing
         * components using field injection.
         */
        FIELD

    }

}
