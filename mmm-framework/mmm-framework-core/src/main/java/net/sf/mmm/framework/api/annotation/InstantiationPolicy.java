/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

/**
 * Annotation instantiation policy. The constants of this enumerated type
 * describe the policies for instantiation of a component
 * {@link net.sf.mmm.framework.api.annotation.Implementation}. They are
 * used in conjunction with the
 * {@link net.sf.mmm.framework.api.annotation.Implementation} meta-annotation type to
 * specify when to create a new and when to reuse an existing instance of a
 * component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum InstantiationPolicy {

    /**
     * Annotates a singleton-style component
     * {@link net.sf.mmm.framework.api.annotation.Implementation}. For such component
     * only a single instance will be created that can be shared. This policy
     * should only be used if the
     * {@link net.sf.mmm.framework.api.annotation.Implementation} is thread-safe.
     */
    SINGLETON,

    /**
     * Annotates a component {@link net.sf.mmm.framework.api.annotation.Implementation}
     * that will be created every time it is requested.
     */
    PER_REQUEST,

    /**
     * Annotates a component {@link net.sf.mmm.framework.api.annotation.Implementation}
     * that is individual for each component that depends on it. Typical
     * examples for such components are loggers or configurations. 
     */
    PER_COMPONENT,

    /**
     * Annotates a component {@link net.sf.mmm.framework.api.annotation.Implementation}
     * that can be pooled. This means that an instance of the component will be
     * put back in a pool after it is
     * {@link net.sf.mmm.framework.api.ContainerIF#releaseComponent(Object) released}.
     * Then it can be reused if a new instance is
     * {@link net.sf.mmm.framework.api.ContainerIF#requestComponent(Class) requested}.
     */
    POOLABLE

}
