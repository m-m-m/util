/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.api.IocContainer;

/**
 * This class represents a node in the dependency tree of the
 * {@link #getInstanceContainer() component instances}.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the {@link #getInstanceContainer() component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyNode<S> {

  /** required for the {@link #getComponentManager() dependency-manager} */
  private final AbstractIocContainer container;

  /** @see #getProvider() */
  private ComponentProvider<S> provider;

  /** @see #getInstanceId() */
  private String instanceId;

  /** @see #getComponentManager() */
  private ComponentManagerImpl dependencyManager;

  /** @see #getInstanceContainer() */
  private ComponentInstanceContainer<S> instanceContainer;

  /** @see #isDisposed() */
  private boolean disposed;

  /** @see #getSource() */
  private DependencyNode<?> source;

  /** @see #getTarget() */
  private DependencyNode<?> target;

  /** @see #getNextSibling() */
  private DependencyNode<?> next;

  /** @see #getPreviousSibling() */
  private DependencyNode<?> previous;

  /**
   * The constructor.
   * 
   * @param iocContainer
   *        is the {@link IocContainer IoC-container} owning this node.
   */
  public DependencyNode(AbstractIocContainer iocContainer) {

    super();
    this.container = iocContainer;
    clear();
  }

  /**
   * This method clears this node. It is used to pool the instance for later
   * reuse.
   */
  public void clear() {

    this.next = this;
    this.previous = this;
    this.target = null;
    this.source = null;
    this.provider = null;
    this.instanceId = null;
    this.instanceContainer = null;
    this.dependencyManager = null;
    this.disposed = false;
  }

  /**
   * This method gets the provider of the component.
   * 
   * @return the provider.
   */
  public ComponentProvider<S> getProvider() {

    return this.provider;
  }

  /**
   * This method sets the {@link #getProvider() provider}.
   * 
   * @param newProvider
   *        is the provider to set.
   */
  public void setProvider(ComponentProvider<S> newProvider) {

    this.provider = newProvider;
  }

  /**
   * This method gets the
   * {@link ComponentDescriptor#getSpecification() specification} of the
   * component. This is a shortcut for
   * 
   * <pre>
   * {@link #getProvider()}.{@link ComponentProvider#getDescriptor() getDescriptor()}.{@link ComponentDescriptor#getSpecification() getSpecification()}
   * </pre>
   * 
   * @return the specifciation of the component.
   */
  public Class<S> getSpecification() {

    return this.provider.getDescriptor().getSpecification();
  }

  /**
   * This method gets the instanceId.
   * 
   * @return the instanceId.
   */
  public String getInstanceId() {

    return this.instanceId;
  }

  /**
   * This method sets the {@link #getInstanceId() instance-Id}.
   * 
   * @param newInstanceId
   *        is the instanceId to set.
   */
  public void setInstanceId(String newInstanceId) {

    this.instanceId = newInstanceId;
  }

  /**
   * This method gets the
   * {@link ComponentInstanceContainer#getInstance() component instance}.
   * 
   * @return the component instance.
   */
  public S getInstance() {

    return this.instanceContainer.getInstance();
  }

  /**
   * This method sets the {@link #getInstanceContainer() instanceContainer}.
   * 
   * @param componentInstanceContainer
   *        is the container with the component
   *        {@link net.sf.mmm.framework.api.ComponentInstanceContainer#getInstance() instance}.
   */
  public void setInstanceContainer(ComponentInstanceContainer<S> componentInstanceContainer) {

    this.instanceContainer = componentInstanceContainer;
  }

  /**
   * This method gets the {@link ComponentManager component-manager}.
   * 
   * @return the compoonent-manager.
   */
  public ComponentManager getComponentManager() {

    return this.dependencyManager;
  }

  /**
   * This method gets the dependency-node of the component this one was created
   * for.<br>
   * 
   * @return the source or <code>null</code> if this is the dependency node
   *         for the top-level {@link IocContainer IOC-container} itself.
   */
  public DependencyNode<?> getSource() {

    return this.source;
  }

  /**
   * This method sets the {@link #getSource() source}.
   * 
   * @param sourceNode
   *        is the {@link #getSource() source} or <code>null</code> if this is
   *        the top-level {@link IocContainer container}.
   */
  public void setSource(DependencyNode<?> sourceNode) {

    this.source = sourceNode;
    this.dependencyManager = new ComponentManagerImpl(this.container, this);
  }

  /**
   * This method gets the dependency-node of the first component created as
   * direct dependency for this component. This should apply
   * 
   * <pre>
   * this.{@link #getTarget()}.{@link #getSource()} == this
   * </pre>
   * 
   * @return the target dependency-node or <code>null</code> if this component
   *         has no dependencies.
   */
  public DependencyNode<?> getTarget() {

    return this.target;
  }

  /**
   * This method sets the target.
   * 
   * @param targetNode
   *        is the target to set.
   */
  public void setTarget(DependencyNode<?> targetNode) {

    this.target = targetNode;
  }

  /**
   * This method gets the next dependecy-node in the cyclic list.
   * 
   * @return the next dependecy-node.
   */
  public DependencyNode<?> getNextSibling() {

    return this.next;
  }

  /**
   * This method gets the previous dependecy-node in the cyclic list.
   * 
   * @return the previous dependecy-node.
   */
  public DependencyNode<?> getPreviousSibling() {

    return this.previous;
  }

  /**
   * This method removes this node from the sibling list.
   */
  public void removeFromSiblingList() {

    this.previous.next = this.next;
    this.next.previous = this.previous;
    if ((this.source != null) && (this.source.target == this)) {
      if (this.previous == this) {
        this.source.target = null;
      } else {
        this.source.target = this.next;
      }
    }
  }

  /**
   * This method finds the dependency-node in the sibling list where the
   * {@link #getInstanceContainer() instance-container} has the same
   * {@link ComponentInstanceContainer#getInstance() instance} as the given
   * <code>componentInstance</code>.
   * 
   * @param componentInstance
   *        is the component instance of the requested dependency-node.
   * @return the dependency-node in the sibling list containing the
   *         <code>componentInstance</code> or <code>null</code> if no such
   *         node could be found.
   */
  public DependencyNode<?> findSibling(Object componentInstance) {

    if (this.instanceContainer.getInstance() == componentInstance) {
      return this;
    }
    DependencyNode<?> pointer = this.next;
    while (pointer != this) {
      if (pointer.instanceContainer.getInstance() == componentInstance) {
        return pointer;
      }
      pointer = pointer.next;
    }
    return null;
  }

  /**
   * This method inserts the give <code>sibling</code> to the end of the
   * sibling list.
   * 
   * @param sibling
   *        is the sibling to add.
   */
  public void insertSibling(DependencyNode<?> sibling) {

    sibling.next = this;
    sibling.previous = this.previous;
    this.previous.next = sibling;
    this.previous = sibling;
  }

  public void addTarget(DependencyNode<?> targetNode) {

    assert (targetNode.source == this);
    if (this.target == null) {
      this.target = targetNode;
    } else {
      this.target.insertSibling(targetNode);
    }
  }

  /**
   * This method gets the container with the component instance.
   * 
   * @return the instance-container.
   */
  public ComponentInstanceContainer<S> getInstanceContainer() {

    return this.instanceContainer;
  }

  /**
   * This method determines if this node has already been disposed. After
   * construction or {@link #clear()} this flag is <code>false</code>. It is
   * set to <code>true</code> by {@link #setDisposed()}.
   * 
   * @return the disposed.
   */
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * This method sets the {@link #isDisposed() disposed} flag to
   * <code>true</code>.
   */
  public void setDisposed() {

    this.disposed = true;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    if (this.instanceContainer == null) {
      return this.provider.getDescriptor().toString() + " [id=" + this.instanceId + "]";
    } else {
      return this.instanceContainer.toString();
    }
  }

}
