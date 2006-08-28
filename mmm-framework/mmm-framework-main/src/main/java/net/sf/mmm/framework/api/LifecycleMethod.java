/* $Id: LifecycleMethod.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.api;

import java.lang.reflect.Method;

/**
 * This class represents a {@link #getLifecycleMethod() method} of a
 * {@link ExtendedComponentDescriptorIF#getImplementation() component}
 * associated with a {@link #getLifecyclePhase() phase} of the
 * {@link LifecycleManagerIF#getLifecyclePhases() lifecycle}.
 * 
 * @see LifecycleManagerIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class LifecycleMethod {

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for the
     * initialization of a componenet.
     * 
     * @see javax.annotation.PostConstruct
     */
    public static final String LIFECYCLE_INITIALIZE = "initialize";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for starting a
     * component.
     * 
     * The suggested signature is: <br>
     * <code>public void start()</code>
     */
    public static final String LIFECYCLE_START = "start";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for the
     * execution of the component in a separate {@link Thread thread}. The
     * component should also support {@link #LIFECYCLE_STOP stop} or
     * {@link #LIFECYCLE_DISPOSE dispose} to be able to stop the component in a
     * defined way (e.g. when shutting down).
     * 
     * @see Runnable#run()
     */
    public static final String LIFECYCLE_EXECUTE = "execute";

    /**
     * This is the
     * {@link ExtendedComponentInstanceContainerIF#getLifecycleState() lifecycle status}
     * if the {@link #getLifecyclePhase() lifecycle-phase}
     * {@link #LIFECYCLE_EXECUTE "execute"} has completed.
     * 
     * @see Runnable#run()
     */
    public static final String LIFECYCLE_EXECUTED = "executed";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for stopping a
     * component.
     * 
     * The suggested signature is: <br>
     * <code>public void stop()</code>
     */
    public static final String LIFECYCLE_STOP = "stop";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for disposing a
     * component.
     * 
     * @see javax.annotation.PreDestroy
     */
    public static final String LIFECYCLE_DISPOSE = "dispose";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for pausing a
     * component. Components supporting this lifecycle phase should also support
     * {@link #LIFECYCLE_RESUME resume}.
     * 
     * @see #LIFECYCLE_RESUME
     * 
     * The suggested signature is: <br>
     * <code>public void pause()</code>
     */
    public static final String LIFECYCLE_PAUSE = "pause";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for resuming a
     * {@link #LIFECYCLE_PAUSE paused} component.
     * 
     * @see #LIFECYCLE_PAUSE
     * 
     * The suggested signature is: <br>
     * <code>public void resume()</code>
     */
    public static final String LIFECYCLE_RESUME = "resume";

    /**
     * This is the {@link #getLifecyclePhase() lifecycle-phase} for
     * reconfiguring a {@link #LIFECYCLE_PAUSE paused} component.
     * 
     * @see #LIFECYCLE_PAUSE
     * 
     * The suggested signature is: <br>
     * <code>public void reconfigure()</code>
     */
    public static final String LIFECYCLE_RECONFIGURE = "reconfigure";

    /** @see #getLifecyclePhase() */
    private final String phase;

    /** @see #getLifecycleMethod() */
    private Method method;

    /**
     * The constructor.
     * 
     * @param lifecyclePhase
     *        is the {@link #getLifecyclePhase() phase}.
     * @param lifecycleMethod
     *        is the {@link #getLifecycleMethod() method}.
     */
    public LifecycleMethod(String lifecyclePhase, Method lifecycleMethod) {

        super();
        this.phase = lifecyclePhase;
        this.method = lifecycleMethod;
    }

    /**
     * This method gets the name of the lifecylce phase associated with the
     * {@link #getLifecycleMethod() method}.
     * 
     * @return the lifecycle phase
     */
    public String getLifecyclePhase() {

        return this.phase;
    }

    /**
     * This method gets the {@link Method method} of the
     * {@link ExtendedComponentDescriptorIF#getLifecycleMethods() declaring}
     * {@link ExtendedComponentDescriptorIF#getImplementation() component-implementation}
     * for the associated {@link #getLifecyclePhase() lifecycle-phase}. It must
     * be a public non-arg (no {@link Method#getParameterTypes() arguments})
     * method.<br>
     * 
     * @return the according lifecycle method.
     */
    public Method getLifecycleMethod() {

        return this.method;
    }

}
