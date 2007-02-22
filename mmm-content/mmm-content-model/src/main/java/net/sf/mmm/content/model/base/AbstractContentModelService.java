/* $Id$ */
package net.sf.mmm.content.model.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelService;
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
  private final Map<String, ContentClass> name2class;

  /** @see #getClass(Id) */
  private final Map<Id, ContentClass> id2class;

  /** @see #getRootClass() */
  private final ContentClass root;

  /**
   * The constructor.
   * 
   * @param rootClass
   * @throws ContentModelException
   *         if the <code>rootClass</code> or its sub-classes are inconsistent.
   */
  public AbstractContentModelService(ContentClass rootClass) throws ContentModelException {

    super();
    this.name2class = new HashMap<String, ContentClass>();
    this.id2class = new HashMap<Id, ContentClass>();
    this.root = rootClass;
    addClassRecursive(this.root);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelService#getEventRegistrar()
   */
  public EventSource<ContentModelEvent, EventListener<ContentModelEvent>> getEventRegistrar() {

    return this;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getClass(java.lang.String)
   */
  public ContentClass getClass(String name) throws ContentModelException {

    return this.name2class.get(name);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getClass(net.sf.mmm.content.value.api.Id)
   */
  public ContentClass getClass(Id id) throws ContentModelException {

    return this.id2class.get(id);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getClasses()
   */
  public Collection<ContentClass> getClasses() {

    // TODO
    return this.id2class.values();
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getRootClass()
   */
  public ContentClass getRootClass() {

    return this.root;
  }

  /**
   * This method registers the given <code>contentClass</code> to this
   * service.<br>
   * It does NOT {@link #fireEvent(ContentModelEvent) fire} the according event.
   * 
   * @param contentClass
   *        is the class to add.
   * @throws ContentModelException
   */
  protected void addClass(ContentClass contentClass) throws ContentModelException {

    ContentClass duplicate;
    duplicate = this.id2class.get(contentClass.getId());
    if (duplicate != null) {
      if (duplicate == contentClass) {
        // already registered...
        return;
      } else {
        throw new ContentModelException("");
      }
    }
    duplicate = this.name2class.get(contentClass.getName());
    if (duplicate != null) {
      // TODO
    }
    this.name2class.put(contentClass.getName(), contentClass);
    this.id2class.put(contentClass.getId(), contentClass);
  }

  /**
   * This method {@link #addClass(ContentClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link ContentClass#getSubClasses() sub-classes} are
   * {@link #addClassRecursive(ContentClass) registered} recursively.
   * 
   * @param contentClass
   *        is the class to add.
   * @throws ContentModelException
   */
  protected void addClassRecursive(ContentClass contentClass) throws ContentModelException {

    addClass(contentClass);
    for (ContentClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

}
