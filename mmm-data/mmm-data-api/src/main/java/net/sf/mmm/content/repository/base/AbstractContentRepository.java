/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.base;

import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentReflectionException;
import net.sf.mmm.content.reflection.base.AbstractMutableContentModelService;
import net.sf.mmm.content.repository.api.ContentObjectWrongTypeException;
import net.sf.mmm.content.repository.api.ContentRepository;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.resource.base.AbstractContentResource;
import net.sf.mmm.content.resource.base.AbstractContentResource.AbstractContentResourceModifier;

/**
 * This is the abstract base implementation of the {@link ContentRepository}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentRepository extends AbstractContentResourceModifier implements
    ContentRepository {

  /** @see #getContentModel() */
  private AbstractMutableContentModelService contentModel;

  /** @see #getRootFolder() */
  private AbstractContentObject rootFolder;

  /**
   * The constructor.
   */
  public AbstractContentRepository() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentObject getRootFolder() {

    return this.rootFolder;
  }

  /**
   * @param rootFolder the {@link #getRootFolder() rootFolder} to set.
   */
  public void setRootFolder(AbstractContentObject rootFolder) {

    this.rootFolder = rootFolder;
    setContentObjectClassAccess(this.rootFolder, getContentModel());

  }

  /**
   * This method handles the cast of a <code>object</code> to the given
   * <code>entityClass</code>.
   * 
   * @param <E> is the generic type of the <code>entityClass</code>.
   * @param object is the object to cast.
   * @param entityClass is the expected type of the given <code>object</code>.
   * @return the given <code>object</code> casted to <code>entityClass</code>.
   * @throws ContentObjectWrongTypeException if the cast failed because the
   *         given <code>object</code> is NOT compatible with the given
   *         <code>entityClass</code>.
   */
  protected <E extends ContentObject> E cast(ContentObject object, Class<E> entityClass)
      throws ContentObjectWrongTypeException {

    try {
      return entityClass.cast(object);
    } catch (ClassCastException e) {
      throw new ContentObjectWrongTypeException(e, object, entityClass.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentObject> E get(ContentId id, Class<E> entityClass)
      throws ContentException {

    return cast(get(id), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentObject> E get(String path, Class<E> entityClass) throws ContentException {

    return cast(get(path), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractMutableContentModelService getContentModel() {

    return this.contentModel;
  }

  /**
   * @param contentModel the {@link #getContentModel() content-model} to set.
   */
  @Resource
  public void setContentModel(AbstractMutableContentModelService contentModel) {

    this.contentModel = contentModel;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentResource> E getRawObject(E entity) throws ContentException {

    AbstractContentResource object = (AbstractContentResource) entity;
    object = object.getRawObject();
    return (E) object;
  }

  /**
   * {@inheritDoc}
   */
  public ContentObject get(String path) throws ContentException {

    AbstractContentObject object = getRootFolder();
    // use smart query on persistent store (recursive SQL)?
    // TODO: create Parser for Path
    StringTokenizer st = new StringTokenizer(path, ContentObject.PATH_SEPARATOR);
    while (st.hasMoreTokens()) {
      String segment = st.nextToken();
      object = object.getChild(segment);
      if (object == null) {
        // TODO: lookup in DB?
        // TODO: return null instead?
        throw new ContentObjectNotExistsException(path);
      }
    }
    return object;
  }

  protected ContentObject createInstance(ContentClass contentClass) throws ContentException {

    ContentObject object;
    try {
      object = contentClass.getJavaClass().newInstance();
    } catch (Exception e) {
      throw new ContentReflectionException(e, "Failed to create instance of class \"{0}\"!",
          contentClass);
    }
    return object;
  }

  /**
   * {@inheritDoc}
   */
  public ContentObject create(ContentClass contentClass, String name, ContentResource parent)
      throws ContentException {

    AbstractContentObject object = (AbstractContentObject) createInstance(contentClass);
    // object.setName(name);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentResource> E createVersion(E resource) throws ContentException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void save(ContentObject object) throws ContentException {

    // TODO Auto-generated method stub

  }

}
