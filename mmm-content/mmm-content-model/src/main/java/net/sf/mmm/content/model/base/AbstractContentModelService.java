/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.model.api.ClassNotExistsException;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.util.event.AbstractSynchronizedEventSource;
import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.util.event.EventSource;

/**
 * This is the abstract base implementation of the {@link ContentModelService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentModelService extends
    AbstractSynchronizedEventSource<ContentModelEvent, EventListener<ContentModelEvent>> implements
    ContentModelService {

  /** @see #getClass(String) */
  private final Map<String, AbstractContentClass> name2class;

  /** @see #getClass(Id) */
  private final Map<Id, AbstractContentClass> id2class;

  /** @see #getClasses() */
  private final Collection<AbstractContentClass> classesView;

  /**
   * The constructor.
   */
  public AbstractContentModelService() {

    super();
    this.name2class = new HashMap<String, AbstractContentClass>();
    this.id2class = new HashMap<Id, AbstractContentClass>();
    this.classesView = Collections.unmodifiableCollection(this.id2class.values());
  }

  /**
   * {@inheritDoc}
   */
  public EventSource<ContentModelEvent, EventListener<ContentModelEvent>> getEventRegistrar() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getClass(String name) throws ContentModelException {

    AbstractContentClass contentClass = this.name2class.get(name);
    if (contentClass == null) {
      throw new ClassNotExistsException(name);
    }
    return contentClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getClass(Id id) throws ContentModelException {

    AbstractContentClass contentClass = this.id2class.get(id);
    if (contentClass == null) {
      throw new ClassNotExistsException(id);
    }
    return contentClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getClass(java.lang.String)
   * 
   * @param name
   *        the name of the requested class.
   * @return the requested class or <code>null</code> if NOT found.
   */
  protected AbstractContentClass getClassOrNull(String name) {

    return this.name2class.get(name);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getClass(net.sf.mmm.content.value.api.Id)
   * 
   * @param id
   *        the ID of the requested class.
   * @return the requested class or <code>null</code> if NOT found.
   */
  protected AbstractContentClass getClassOrNull(Id id) {

    return this.id2class.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractContentClass> getClasses() {

    return this.classesView;
  }

  /**
   * This method registers the given <code>contentClass</code> to this
   * service.<br>
   * It does NOT {@link #fireEvent(ContentModelEvent) fire} the according event.
   * 
   * @param contentClass
   *        is the class to add.
   * @throws ContentModelRuntimeException
   *         if the class is already registered.
   */
  protected void addClass(AbstractContentClass contentClass) throws ContentModelRuntimeException {

    ContentClass duplicate;
    duplicate = this.id2class.get(contentClass.getId());
    if (duplicate != null) {
      throw new DuplicateClassException(contentClass.getId());
    }
    duplicate = this.name2class.get(contentClass.getName());
    if (duplicate != null) {
      throw new DuplicateClassException(contentClass.getName());
    }
    this.name2class.put(contentClass.getName(), contentClass);
    this.id2class.put(contentClass.getId(), contentClass);
  }

  /**
   * This method {@link #addClass(AbstractContentClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link ContentClass#getSubClasses() sub-classes} are also
   * {@link #addClassRecursive(AbstractContentClass) registered} recursively.
   * 
   * @param contentClass
   *        is the class to add.
   * @throws ContentModelRuntimeException
   */
  protected void addClassRecursive(AbstractContentClass contentClass)
      throws ContentModelRuntimeException {

    addClass(contentClass);
    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

}
