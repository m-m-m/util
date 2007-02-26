/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.api.ContainerException;
import net.sf.mmm.framework.api.IocSecurityManager;
import net.sf.mmm.framework.api.MutableIocContainer;
import net.sf.mmm.framework.api.IocContainer;
import net.sf.mmm.framework.base.provider.SimpleSingletonProvider;

/**
 * This is the abstract base implementation of the {@link MutableIocContainer}
 * interface.
 * 
 * TODO: shared component management and shutdown?!?
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractIocContainer implements MutableIocContainer {

  /** @see #getParentContainer() */
  private final AbstractIocContainer parent;

  /** @see #getName() */
  private final String name;

  /** @see #getProvider(Class) */
  private final Map<Class, ComponentProvider> providerMap;

  /** @see #requestComponent(Class, String, DependencyNode) */
  private final Map<ComponentInstanceContainer, DependencyNode> instanceMap;

  /** @see #getDependencyNode() */
  private final DependencyNode<IocContainer> containerNode;

  /** @see #getDependencyNode() */
  private final ComponentManager componentManager;

  /** the pool for dependency-nodes */
  // private final PoolIF<DependencyNode> nodePool;
  /** @see #start() */
  private volatile boolean started;

  /** @see #stop() */
  private volatile boolean stopped;

  /** @see #checkPermission(Class, Class, String) */
  private IocSecurityManager securityManager;

  /**
   * The constructor for the root container.
   */
  public AbstractIocContainer() {

    this(null, NAME_ROOT);
  }

  /**
   * The constructor.
   * 
   * @param parentContainer
   * @param containerName
   *        is the {@link #getName() name}.
   */
  public AbstractIocContainer(AbstractIocContainer parentContainer, String containerName) {

    super();
    this.parent = parentContainer;
    this.name = containerName;
    this.providerMap = new HashMap<Class, ComponentProvider>();
    this.instanceMap = new HashMap<ComponentInstanceContainer, DependencyNode>();
    ContainerProvider containerProvider = new ContainerProvider();
    this.providerMap.put(containerProvider.getDescriptor().getSpecification(), containerProvider);
    this.containerNode = new DependencyNode<IocContainer>(this);
    if (this.parent != null) {
      this.containerNode.setSource(getParentContainer().getDependencyNode());
    }
    this.containerNode.setInstanceContainer(containerProvider.getInstanceContainer());
    this.containerNode.setInstanceId(ComponentManager.DEFAULT_INSTANCE_ID);
    this.containerNode.setProvider(containerProvider);
    this.componentManager = new ComponentManagerImpl(this, this.containerNode);
    this.started = false;
    this.stopped = false;
    this.securityManager = null;
    // this.nodePool = new SimplePool<DependencyNode>(DependencyNode.class,
    // 100);
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainer#getName()
   */
  public final String getName() {

    return this.name;
  }

  /**
   * This method gets the parent container that created this container.
   * 
   * @see #createChildContainer(String)
   * 
   * @return the parent-container or <code>null</code> if this is a top-level
   *         container.
   */
  protected AbstractIocContainer getParentContainer() {

    return this.parent;
  }

  /**
   * This method gets the {@link #getDependencyNode() dependency-node} of this
   * IOC-container itself. Do not call this method until the container is
   * {@link #start() started}.
   * 
   * @return the own dependency-node.
   */
  protected DependencyNode<IocContainer> getDependencyNode() {

    return this.containerNode;
  }

  /**
   * This method gets the component manager.
   * 
   * @return the component manager.
   */
  public ComponentManager getComponentManager() {

    return this.componentManager;
  }

  /**
   * @see net.sf.mmm.framework.api.MutableIocContainer#addComponentProvider(net.sf.mmm.framework.api.ComponentProvider)
   */
  public void addComponentProvider(ComponentProvider<?> componentProvider)
      throws ContainerException {

    ensureNotStarted();
    Class<?> spec = componentProvider.getDescriptor().getSpecification();
    if (this.providerMap.containsKey(spec)) {
      throw new DuplicateSpecificationException(spec);
    }
    debug("adding provider for " + spec);
    this.providerMap.put(spec, componentProvider);
  }

  /**
   * @see net.sf.mmm.framework.api.MutableIocContainer#start()
   */
  public final synchronized void start() {

    ensureNotStarted();

    try {
      debug("starting " + toString());
      // setup container as component
      doStart();

      info(toString() + " has been started successfully");
      this.started = true;
    } catch (RuntimeException e) {
      warning("Startup failed: " + e.getMessage());
      throw e;
    }
  }

  /**
   * This method is called from {@link #start()}. Override this method to add
   * your custom startup code.
   */
  protected void doStart() {

  }

  /**
   * @see net.sf.mmm.framework.api.MutableIocContainer#stop()
   */
  public final synchronized void stop() {

    if (!this.stopped) {
      if (!this.started) {
        throw new ContainerException(NlsBundleFrameworkCore.ERR_CONTAINER_NOT_STARTED);
      }
      info("stopping IoC-Container [" + getName() + "]");
      this.stopped = true;
      releaseTargets(this.containerNode);
      Iterator<DependencyNode> nodeIterator = this.instanceMap.values().iterator();
      while (nodeIterator.hasNext()) {
        DependencyNode node = nodeIterator.next();
        debug("disposing component " + node);
        node.getProvider().dispose(node.getInstanceContainer(), node.getComponentManager());
      }
      doStop();
    } else {
      warning("Cannot stop the container because it is already stopped.");
    }
  }

  /**
   * This method is called from {@link #stop()}.
   */
  protected void doStop() {

  }

  /**
   * @see net.sf.mmm.framework.api.IocContainer#isRunning()
   */
  public boolean isRunning() {

    return (this.started && !this.stopped);
  }

  /**
   * This method throws an exception if the container has already been
   * started. Call this method from all methods that perform modifications of
   * the container.
   * 
   * @throws ContainerException
   *         if the container has NOT been {@link #start() started} or already
   *         been {@link #stop() stopped}.
   */
  protected final void ensureRunning() throws ContainerException {

    if (!this.started) {
      throw new ContainerException(NlsBundleFrameworkCore.ERR_CONTAINER_NOT_STARTED);
    }
    if (this.stopped) {
      throw new ContainerException(NlsBundleFrameworkCore.ERR_CONTAINER_STOPPED);
    }
  }

  /**
   * This method throws an exception if the container has already been
   * started. Call this method from all methods that perform modifications of
   * the container.
   * 
   * @throws ContainerException
   *         if the container has already been started.
   */
  protected final void ensureNotStarted() throws ContainerException {

    if (this.started) {
      throw new ContainerException(NlsBundleFrameworkCore.ERR_CONTAINER_STARTED);
    }
  }

  /**
   * This method gets the provider for the given <code>specification</code>.
   * 
   * @param <S>
   *        is the
   *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
   *        of the requested component.
   * @param specification
   *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
   *        of the component.
   * @return the requested provider or <code>null</code> if NOT available.
   */
  protected <S> ComponentProvider<S> getProvider(Class<S> specification) {

    ComponentProvider<S> provider = this.providerMap.get(specification);
    if (provider == null) {
      if (getParentContainer() != null) {
        provider = getParentContainer().getProvider(specification);
      }
    }
    return provider;
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#hasComponent(java.lang.Class)
   * 
   * This method determines if the component for the given
   * {@link ComponentDescriptor#getSpecification() specification} is
   * available. In that case, it can be retrieved via the
   * {@link ComponentManager#requestComponent(Class)} method without causing
   * an "component not registered" exception.
   * 
   * @param <S>
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification} of
   *        the requested component.
   * @param specification
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification} of
   *        the component.
   * @return <code>true</code> if a component for the given
   *         {@link ComponentDescriptor#getSpecification() specification} is
   *         registered, <code>false</code> otherwise.
   */
  public <S> boolean hasComponent(Class<S> specification) {

    return (getProvider(specification) != null);
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#releaseComponent(Object)
   * 
   * @param <S>
   * @param component
   * @param source
   */
  public <S> void releaseComponent(S component, DependencyNode<?> source) {

    DependencyNode<?> targetNode = source.getTarget();
    if (targetNode != null) {
      DependencyNode<?> releaseNode = targetNode.findSibling(component);
      if (releaseNode != null) {
        releaseComponentByNode(releaseNode);
        releaseNode.removeFromSiblingList();
        return;
      }
    }
    /*
     * Iterator<DependencyNode> targetIterator =
     * source.getTargets().iterator(); while (targetIterator.hasNext()) {
     * DependencyNode<?> target = targetIterator.next(); if
     * (target.getInstanceContainer().getInstance() == component) {
     * releaseComponentByNode(target); // targetIterator.remove(); return; } }
     */
    warning("Could not release unknown component instance '" + component + "'!");
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#releaseComponent(java.lang.Object)
   * 
   * @param <S>
   *        is the templated type of the associated
   *        {@link ComponentDescriptor#getSpecification() component specification}.
   * @param node
   *        is the node with the component instance to release.
   */
  protected <S> void releaseComponentByNode(DependencyNode<S> node) {

    // TODO: call on right container
    debug("releasing component " + node);
    ComponentInstanceContainer<S> instanceContainer = node.getInstanceContainer();
    boolean released = node.getProvider().release(instanceContainer, node.getComponentManager());
    // node.removeFromSiblingList();
    if (released) {
      debug("released component " + node);
      // see if the component was shared and we have an original node...
      DependencyNode<?> releasedNode = this.instanceMap.remove(instanceContainer);
      if (releasedNode == null) {
        warning("shared component released again: " + node);
        releasedNode = node;
      }
      releaseTargets(releasedNode);
      /*
       * node.clear(); this.nodePool.add(node); if (releasedNode != node) {
       * releasedNode.clear(); this.nodePool.add(releasedNode); }
       */
      /*
       * } else { // the component is still used (shared) DependencyNode<?>
       * sharedNode = this.instanceMap.get(instanceContainer); if (sharedNode ==
       * null) { // we do not yet have it in the shared component map so lets
       * add // it. debug("tracking shared component " + node);
       * this.instanceMap.put(instanceContainer, node); // then we move the
       * node to the container's node node.removeFromSiblingList();
       * node.setSource(this.sharedDependencyNode);
       * this.sharedDependencyNode.addTarget(node); } else { // do we have
       * dependencies (or can we just drop the duplicate // node? if
       * (node.getTarget() != null) { // error warning("Duplicate shared
       * component " + node); } }
       */
    }
  }

  protected <S> void releaseTargets(DependencyNode<S> node) {

    // okay recursively release all dependencies of the released
    // component
    DependencyNode<?> target = node.getTarget();
    if (target != null) {
      DependencyNode<?> pointer = target.getPreviousSibling();
      while (pointer != target) {
        releaseComponentByNode(pointer);
        pointer = pointer.getPreviousSibling();
      }
      releaseComponentByNode(target);
    }

  }

  protected <S> void disposeComponentByNode(DependencyNode<S> node) {

    debug("diposing component " + node);
    ComponentInstanceContainer<S> instanceContainer = node.getInstanceContainer();
    node.getProvider().dispose(instanceContainer, node.getComponentManager());

    // okay recursively release all dependencies of the released
    // component
    DependencyNode<?> target = node.getTarget();
    if (target != null) {
      DependencyNode<?> pointer = target.getPreviousSibling();
      while ((pointer != target) && (pointer != pointer.getPreviousSibling())) {
        disposeComponentByNode(pointer);
        pointer = pointer.getPreviousSibling();
      }
      disposeComponentByNode(target);
    }

  }

  /**
   * This method detects cyclic dependencies.
   * 
   * @param specification
   *        is the
   *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
   *        of the requested component.
   * @param instanceId
   *        is the
   *        {@link ComponentManager#requestComponent(Class, String) instance-ID}
   *        of the requested component.
   * @param sourceNode
   *        is the node for the source component performing the request.
   * @throws DependencyCycleException
   *         if a cycle was detected.
   */
  protected void detectDependencyCycle(Class<?> specification, String instanceId,
      DependencyNode<?> sourceNode) throws DependencyCycleException {

    DependencyNode<?> node = sourceNode;
    while (node != null) {
      ComponentInstanceContainer<?> instanceContainer = node.getInstanceContainer();
      if (node.getProvider().getDescriptor().getSpecification() == specification) {
        if (isStrictOnDependencyCycle() || instanceContainer.getInstanceId().equals(instanceId)) {
          StringBuffer sb = new StringBuffer();
          sb.append(specification.getName());
          sb.append('[');
          sb.append(instanceId);
          sb.append(']');
          DependencyNode<?> pointer = sourceNode;
          while (pointer != null) {
            sb.append("\n<--");
            sb.append(pointer.getProvider().getDescriptor().getSpecification().getName());
            sb.append('[');
            sb.append(pointer.getInstanceId());
            sb.append(']');
            if (pointer == node) {
              pointer = null;
            } else {
              pointer = pointer.getSource();
            }
          }
          throw new DependencyCycleException(sb.toString());
        }
      }
      node = node.getSource();
    }
  }

  /**
   * This method gets a constant flag determining if the container is strict
   * when
   * {@link #detectDependencyCycle(Class, String, DependencyNode) detecting cyclic dependencies}.
   * In this case a trace containing the same
   * {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
   * twice is immediately treated as {@link DependencyCycleException error}.
   * This is the default behaviour.<br>
   * You may override this method and return <code>false</code> instead to
   * disable strict mode. Then a trace is treated as cycle only if two
   * {@link DependencyNode nodes} point to the same
   * {@link ComponentDescriptor#getSpecification() specification} AND the
   * {@link Object#equals(Object) same}
   * {@link ComponentManager#requestComponent(Class, String) instance-ID}.
   * 
   * @return <code>true</code> if strict, <code>false</code> otherwise.
   */
  protected boolean isStrictOnDependencyCycle() {

    return true;
  }

  /**
   * This method checks if the component <code>source</code> is allowed to
   * request an instance of <code>target</code> and <code>instanceId</code>.
   * 
   * @see IocSecurityManager#checkPermission(Class, Class, String)
   * 
   * @param source
   *        is the source
   * @param target
   * @param instanceId
   * @throws SecurityException
   */
  protected final void checkPermission(Class<?> source, Class<?> target, String instanceId)
      throws SecurityException {

    if (this.securityManager != null) {
      this.securityManager.checkPermission(source, target, instanceId);
    }
    if (getParentContainer() != null) {
      getParentContainer().checkPermission(source, target, instanceId);
    }
  }

  /**
   * @see ComponentManager#requestComponent(Class, String)
   * 
   * @param <S>
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification} of
   *        the requested component.
   * @param specification
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification} of
   *        the requested component.
   * @param instanceId
   *        identifies a specific instance of the component for
   *        <code>specification</code> if there are multiple. The
   *        {@link ComponentManager#requestComponent(Class) default} is
   *        {@link ComponentManager#DEFAULT_INSTANCE_ID}.
   * @param sourceNode
   * @return an instance of the requested component. It must fulfill the given
   *         {@link ComponentDescriptor#getSpecification() specification}.
   *         It is illegal (and typically NOT possible) to cast it to another
   *         type (that is no super-type).
   * @throws ComponentException
   *         if the requested component is NOT
   *         {@link #hasComponent(Class) available} or could not be provided.
   * @throws ContainerException
   *         if a fundamental problem occurred (e.g. the container has NOT been
   *         started).
   */
  protected <S> S requestComponent(Class<S> specification, String instanceId,
      DependencyNode<?> sourceNode) throws ComponentException, ContainerException {

    debug("request on " + specification.getName() + " for id '" + instanceId + "' ...");
    checkPermission(sourceNode.getProvider().getDescriptor().getSpecification(), specification,
        instanceId);
    return requestComponentOnDeclaringContainer(specification, instanceId, sourceNode);
  }

  private <S> S requestComponentOnDeclaringContainer(Class<S> specification, String instanceId,
      DependencyNode<?> sourceNode) throws ComponentException, ContainerException {

    // actually the request should be handled by the container declaring the
    // according provider.
    ComponentProvider<S> provider = this.providerMap.get(specification);
    if (provider == null) {
      if (getParentContainer() != null) {
        return getParentContainer().requestComponentOnDeclaringContainer(specification, instanceId,
            sourceNode);
      } else {
        throw new ComponentNotAvailableException(specification);
      }
    }
    detectDependencyCycle(specification, instanceId, sourceNode);

    // create dependency node for request...
    DependencyNode<S> node = new DependencyNode<S>(this);
    node.setSource(sourceNode);
    node.setProvider(provider);
    node.setInstanceId(instanceId);
    ComponentInstanceContainer<S> ic = provider.request(instanceId, sourceNode.getProvider()
        .getDescriptor(), sourceNode.getInstanceId(), node.getComponentManager());
    DependencyNode<?> sharedNode = this.instanceMap.get(ic);
    node.setInstanceContainer(ic);
    sourceNode.addTarget(node);
    if (sharedNode == null) {
      this.instanceMap.put(ic, node);
    } else {
      DependencyNode<?> targetNode = node.getTarget();
      if (targetNode != null) {
        // TODO:
        // sharedNode.addTargets(targetNode);
        warning("Duplicate dependency on shared component: " + targetNode);
      }
    }
    return ic.getInstance();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return "IoC-Container [" + this.name + "]";
  }

  /**
   * This method prints out a warning message.<br>
   * This implementation simply delegates to {@link System#err} for NOT adding
   * a dependency to a specific logger API. Please override this method and
   * use an appropriate logger.
   * 
   * @param message
   *        is the warning message.
   */
  protected void warning(String message) {

    System.err.println(message);
  }

  /**
   * This method prints out an informational message.<br>
   * This implementation simply delegates to {@link System#out} for NOT adding
   * a dependency to a specific logger API. Please override this method and
   * use an appropriate logger.
   * 
   * @param message
   *        is the informational message.
   */
  protected void info(String message) {

    System.out.println(message);
  }

  /**
   * This method prints out an debugging message.<br>
   * This implementation simply delegates to {@link System#out} for NOT adding
   * a dependency to a specific logger API. Please override this method and
   * use an appropriate logger.
   * 
   * @param message
   *        is the debugging message.
   */
  protected void debug(String message) {

    System.out.println(message);
  }

  /**
   * This is the {@link ComponentProvider provider} of the
   * {@link IocContainer}.
   */
  protected class ContainerProvider extends SimpleSingletonProvider<IocContainer> {

    /**
     * The constructor.
     */
    protected ContainerProvider() {

      super(IocContainer.class, new IocContainerProxy(AbstractIocContainer.this));
    }

    /**
     * @see net.sf.mmm.framework.base.provider.SimpleSingletonProvider#dispose(net.sf.mmm.framework.api.ComponentInstanceContainer,
     *      net.sf.mmm.framework.api.ComponentManager)
     */
    @Override
    public void dispose(ComponentInstanceContainer<IocContainer> singletonContainer,
        ComponentManager componentMgr) {

      stop();
    }

  }

}
