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
import net.sf.mmm.content.model.base.DuplicateClassException;
import net.sf.mmm.content.value.api.ContentId;
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
  private final Map<String, ContentClass> name2class;

  /** @see #getClass(ContentId) */
  private final Map<ContentId, ContentClass> id2class;

  /** @see #getClasses() */
  private final Collection<ContentClass> classesView;

  /** @see #getRootClass() */
  private ContentClass rootClass;

  /**
   * The constructor.
   */
  public AbstractContentModelService() {

    super();
    this.name2class = new HashMap<String, ContentClass>();
    this.id2class = new HashMap<ContentId, ContentClass>();
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
  public ContentClass getClass(String name) throws ContentModelException {

    return this.name2class.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getClass(ContentId id) throws ContentModelException {

    return this.id2class.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends ContentClass> getClasses() {

    return this.classesView;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getRootClass() {

    return this.rootClass;
  }

  /**
   * This method set the {@link #getRootClass() root-class}.
   * 
   * @param rootClass is the root-class to set.
   */
  protected void setRootClass(ContentClass rootClass) {

    this.rootClass = rootClass;
  }

  /**
   * This method registers the given <code>contentClass</code> to this
   * service.<br>
   * It does NOT {@link #fireEvent(ContentModelEvent) fire} the according event.
   * 
   * @param contentClass is the class to add.
   * @throws ContentModelException if the class is already registered.
   */
  protected void addClass(ContentClass contentClass) throws ContentModelException {

    ContentClass duplicate;
    ContentId id = contentClass.getId();
    duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateClassException(id);
    }
    String name = contentClass.getName();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateClassException(name);
    }
    this.name2class.put(name, contentClass);
    this.id2class.put(id, contentClass);
  }

  /**
   * This method {@link #addClass(ContentClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link ContentClass#getSubClasses() sub-classes} are also
   * {@link #addClassRecursive(ContentClass) registered} recursively.
   * 
   * @param contentClass is the class to add.
   * @throws ContentModelException if the class or one of its sub-classes
   *         could NOT be registered.
   */
  protected void addClassRecursive(ContentClass contentClass) throws ContentModelException {

    addClass(contentClass);
    for (ContentClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

  /**
   * This method removes the given <code>contentClass</code> from the model.<br>
   * <b>WARNING:</b><br>
   * Use this feature with extreme care.
   * 
   * @param contentClass is the content-class to remove.
   * @throws ContentModelException
   */
  protected void removeClass(ContentClass contentClass) throws ContentModelException {

    if (contentClass.getModifiers().isSystem()) {
      // TODO: NLS
      throw new ContentModelException("Can NOT remove system class!");
    }
    ContentClass old = this.id2class.remove(contentClass.getId());
    assert (old == contentClass);
    old = this.name2class.remove(contentClass.getName());
    assert (old == contentClass);
  }

}
