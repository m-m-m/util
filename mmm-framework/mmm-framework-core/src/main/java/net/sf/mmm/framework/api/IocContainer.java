package net.sf.mmm.framework.api;

/**
 * This is the specification of a container that manages
 * {@link ComponentProvider components} following the IoC (inversion of
 * control) principle. The managed components are registered in the container
 * via a {@link net.sf.mmm.framework.api.ComponentProvider provider}.<br>
 * 
 * @see ComponentProvider
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface IocContainer {

  /**
   * The default {@link #getName() name} of the root container.
   */
  String NAME_ROOT = "root";

  /**
   * The name of this container. This is a general purpose string without
   * technical meaning.
   * 
   * @see #createChildContainer(String)
   * 
   * @return the name of the container.
   */
  String getName();

  /**
   * This method creates a new child-container that will inherit from this
   * container. Only call this method when the container is
   * {@link #isRunning() running}. The child-container is initially NOT
   * {@link #isRunning() running}. You can
   * {@link MutableIocContainer#addComponentProvider(ComponentProvider) add}
   * additional components to the child-container before you
   * {@link MutableIocContainer#start() start} it. The child-container can
   * be {@link MutableIocContainer#stop() stopped} while the
   * parent-container is still in use. If the parent-container is
   * {@link MutableIocContainer#stop() stopped} before, the child-container
   * is {@link MutableIocContainer#stop() stopped}, too.
   * 
   * @param name
   *        is the {@link #getName() name} of the child-container to create.
   * @return the new child-container.
   */
  MutableIocContainer createChildContainer(String name);

  /**
   * This method determines if this container is running. The container is
   * running after it has been {@link MutableIocContainer#start() started}
   * until it is {@link MutableIocContainer#stop() stopped}.
   * 
   * @return <code>true</code> if running.
   */
  boolean isRunning();

}
