/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

/**
 * This enum contains all available actions of the lifecycle for a component
 * {@link net.sf.mmm.framework.api.annotation.Implementation} managed by this
 * framework. {@link java.lang.reflect.Modifier#isPublic(int) Public} non-arg
 * {@link java.lang.reflect.Method Methods methods} of the
 * {@link net.sf.mmm.framework.api.annotation.Implementation} can be associated
 * with a lifecycle action. The framework will call this method in the
 * appropriate stage of the components lifecycle following the IoC (inversion of
 * control) pattern. If a lifecycle action is not declared for a specific
 * component it will simply be omitted.<br>
 * 
 * For setting up a component
 * {@link net.sf.mmm.framework.api.annotation.Implementation} the following
 * steps will be executed in the given order:
 * <ol>
 * <li>{@link net.sf.mmm.framework.api.annotation.InstantiationPolicy instantiation}</li>
 * <li>{@link javax.annotation.Resource injection} (can be multiple)</li>
 * <li>{@link javax.annotation.PostConstruct initialization}</li>
 * <li>{@link #START}</li>
 * <li>{@link #EXECUTE}</li>
 * </ol>
 * After setting up, the component is ready to use. As far as supported, the
 * component may be {@link #PAUSE paused}. A paused component can be
 * {@link #RECONFIGURE reconfigured} and/or {@link #RESUME resumed}. This
 * intermediate lifecycle can repeat as often as desired.<br>
 * If the component is not needed anymore it's lifecycle is completed by the
 * following actions in the given order:
 * <ol>
 * <li>{@link #STOP}</li>
 * <li>{@link javax.annotation.PreDestroy dispose}</li>
 * </ol>
 * 
 * @see net.sf.mmm.framework.api.ComponentDescriptorIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum LifecycleAction {

  /**
   * Action marker for initialization method. The recomendet signature of the
   * associated method is:
   * 
   * <pre>
   * public void initialize()
   * </pre>
   * 
   * @see net.sf.mmm.framework.api.marker.InitializableIF
   */
  INITIALIZE,

  /**
   * Action marker for start method. The framework will NOT call this method
   * in a separate {@link Thread}. The recomendet signature of the associated
   * method is:
   * 
   * <pre>
   * public void start()
   * </pre>
   * 
   * @see net.sf.mmm.framework.api.marker.StartableIF
   */
  START,

  /**
   * Action marker for the execute method. This method will be called in a
   * seperate {@link Thread}. It may be done after some time and return or
   * loop forever. The recomendet signature of the associated method is
   * {@link Runnable#run()} or
   * 
   * <pre>
   * public void execute()
   * </pre>
   */
  EXECUTE,

  /**
   * Action marker for pausing a component. The recomendet signature of the
   * associated method is:
   * 
   * <pre>
   * public void pause()
   * </pre>
   */
  PAUSE,

  /**
   * Action marker for reconfiguration. This method is called if configuration
   * that was {@link Injection injected} has changed. The {@link #PAUSE}
   * action will be performed on the component before this action is invoked.
   * It is not specified if the configuration instance itself changes or
   * another instance of the configuration is injected immediatly before this
   * action is called. The recomendet signature of the associated method is:
   * 
   * <pre>
   * public void reconfigure()
   * </pre>
   */
  RECONFIGURE,

  /**
   * Action marker for resuming a component. This is the inverse action of
   * {@link #PAUSE}. The recomendet signature of the associated method is:
   * 
   * <pre>
   * public void resume()
   * </pre>
   */
  RESUME,

  /**
   * Action marker for stopping a component. This is the inverse action of
   * {@link #START}. The recomendet signature of the associated method is:
   * 
   * <pre>
   * public void stop()
   * </pre>
   */
  STOP,

  /**
   * Action marker for dispsing a component. The recomendet signature of the
   * associated method is:
   * 
   * <pre>
   * public void dispose()
   * </pre>
   */
  DISPOSE

}
