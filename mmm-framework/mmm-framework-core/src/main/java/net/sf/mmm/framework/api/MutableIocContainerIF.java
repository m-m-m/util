package net.sf.mmm.framework.api;

/**
 * This is the interface for a mutable {@link IocContainerIF container}.<br>
 * Components can be
 * {@link #addComponentProvider(ComponentProviderIF) registered} until the
 * container is {@link #start() started}.<br>
 * Finally the container can be {@link #stop() stopped} to shut-down.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableIocContainerIF extends IocContainerIF {

  /**
   * This method adds the given {@link ComponentProviderIF provider}. This
   * method must be called before the container is {@link #start() started}.
   * 
   * @param provider
   *        is the provider to add.
   * @throws ContainerException
   *         if the container has already been {@link #start() started}.
   */
  void addComponentProvider(ComponentProviderIF<?> provider) throws ContainerException;

  /**
   * This method starts the container. The method should be called only once.
   * 
   * @see #isRunning()
   * 
   * @throws ContainerException
   *         if the container has already been {@link #start() started} or the
   *         startup failed.
   */
  void start() throws ContainerException;

  /**
   * This method stops the container. This method should NOT be called before
   * the container is {@link #start() started}.
   * 
   * @see #isRunning()
   * 
   * @throws IocException
   *         if the container has NOT been {@link #start() started} or has
   *         already been {@link #stop() stopped}.
   */
  void stop();

}
