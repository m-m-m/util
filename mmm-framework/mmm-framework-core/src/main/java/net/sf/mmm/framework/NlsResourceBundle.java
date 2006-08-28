/* $Id: NlsResourceBundle.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework;

import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the framework subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsResourceBundle extends AbstractResourceBundle {

    /**
     * The constructor.
     */
    public NlsResourceBundle() {

        super();
    }

    /**
     * @see net.sf.mmm.framework.impl.LifecycleException
     */
    public static final String ERR_LIFECYCLE = "The lifecycle phase \"{0}\" failed for \"{1}\"!";

    /**
     * @see net.sf.mmm.framework.impl.ComponentInstantiationException
     */
    public static final String ERR_INSTANTIATION = "Failed to instantiate \"{2}\" of component \"{0}\" using \"{1}\"!";

    /**
     * @see net.sf.mmm.framework.impl.ComponentInstantiationException
     */
    public static final String ERR_INJECTION_TYPE_ILLEGAL = "Illegal injection type \"{0}\" in \"{1}\"!";

    /**
     * @see net.sf.mmm.framework.muell.AbstractIocContainer
     */
    public static final String ERR_CONTAINER_STARTED = "The container has already been started!";

    /**
     * @see net.sf.mmm.framework.muell.AbstractIocContainer
     */
    public static final String ERR_CONTAINER_STOPPED = "The container has already been stopped!";

    /**
     * @see net.sf.mmm.framework.muell.AbstractIocContainer
     */
    public static final String ERR_CONTAINER_NOT_STARTED = "The container has NOT been started!";

    /**
     * @see net.sf.mmm.framework.base.DuplicateSpecificationException
     */
    public static final String ERR_COMPONENT_DUPLICATE_SPECIFICATION = "A component for the specification \"{0}\" is already registered!";

    /**
     * @see net.sf.mmm.framework.base.provider.DuplicateInstanceIdException
     */
    public static final String ERR_COMPONENT_DUPLICATE_INSTANCE_ID = "Duplicate instance-ID \"{0}\" for component \"{1}\"!";

    /**
     * @see net.sf.mmm.framework.base.provider.InstanceIdNotAvailableException
     */
    public static final String ERR_COMPONENT_ID_NOT_AVAILABLE = "The component \"{0}\" is NOT available for the instance-ID \"{1}\"!";

    /**
     * @see net.sf.mmm.framework.base.ComponentPermissionDeniedException
     */
    public static final String ERR_COMPONENT_PERMISSION_DENIED = "The component \"{0}\" has no permission request the component \"{1}\" with the instance-ID \"{2}\"!";

    /**
     * @see net.sf.mmm.framework.base.ComponentNotAvailableException
     */
    public static final String ERR_COMPONENT_NOT_AVAILABLE = "The component for the specification \"{0}\" is NOT available!";

    /**
     * @see net.sf.mmm.framework.base.DependencyCycleException
     */
    public static final String ERR_DEPENDENCY_CYCLE = "A cyclic dependency was detected:\n\"{0}\"";

    /**
     * @see net.sf.mmm.framework.base.InstantiationPolicyNotAvailableException
     */
    public static final String ERR_INSTANTIATION_POLICY_NOT_AVAILABLE = "The instantiation policy \"{0}\" requested for component \"{1}\" is NOT available !";

    /**
     * @see net.sf.mmm.framework.impl.DependencyNotAvailableException
     */
    public static final String ERR_DEPENDENCY_NOT_AVAILABLE = "The component \"{0}\" with instance-id \"{1}\" has a required dependency on \"{2}\" that is NOT available!";

    /**
     * @see net.sf.mmm.framework.impl.DependencyCreationException
     */
    public static final String ERR_DEPENDENCY_CREATION = "The component \"{0}\" with instance-id \"{1}\" has a required dependency on \"{2}\" that could NOT be created!";

    /**
     * @see net.sf.mmm.framework.impl.ComponentInjectionException
     */
    public static final String ERR_INJECTION = "Failed to inject \"{0}\" into component \"{2}\" using \"{1}\"!";

    /**
     * @see ComponentDescriptor
     */
    public static final String ERR_DESCRIPTOR_CONSTRUCTOR_DEPENDENCY_COUNT = "The constructor of \"{0}\" takes \"{1}\" argument(s) but \"{2}\" constructor dependencie(s) are supplied!";

    /**
     * @see ComponentDescriptor
     */
    public static final String ERR_DESCRIPTOR_CONSTRUCTOR_DEPENDENCY_TYPE = "Constructor dependencies must be supplied in order of constructor arguments - "
            + "constructor of \"{0}\" requires \"{1}\" at posiiton \"{2}\" that is NOT compatible with \"{3}\"!";

    /**
     * @see ComponentException
     */
    public static final String ERR_NO_CONSTRUCTOR = "The component implementation \"{0}\" has no public constructor!";

}
