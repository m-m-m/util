/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.base;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.repository.api.ContentObjectNotExistsException;
import net.sf.mmm.content.repository.api.ContentObjectWrongTypeException;
import net.sf.mmm.content.repository.api.ContentRepository;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.resource.base.AbstractContentResource;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the abstract base implementation of the {@link ContentRepository}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentRepository implements ContentRepository {

  /** @see #getContentModel() */
  private ContentModelService contentModel;

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
  }

  protected <E extends ContentObject> E cast(ContentObject object, Class<E> entityClass) {

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
  public ContentModelService getContentModel() {

    return this.contentModel;
  }

  /**
   * @param contentModel the {@link #getContentModel() content-model} to set.
   */
  @Resource
  public void setContentModel(ContentModelService contentModel) {

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
    // TODO: create Parser for Path
    java.util.StringTokenizer st = new java.util.StringTokenizer(path, ContentObject.PATH_SEPARATOR);
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
      throw new ContentModelException(e, "Failed to create instance of class \"{0}\"!",
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
    //object.setName(name);
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
