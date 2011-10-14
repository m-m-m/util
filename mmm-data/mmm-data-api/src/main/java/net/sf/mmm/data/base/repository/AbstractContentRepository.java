/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.mmm.data.api.DataCastException;
import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.repository.DataObjectWrongTypeException;
import net.sf.mmm.data.api.repository.DataRepository;
import net.sf.mmm.data.base.AbstractContentObject;
import net.sf.mmm.data.base.reflection.AbstractMutableContentModelService;
import net.sf.mmm.data.resource.api.ContentResource;
import net.sf.mmm.data.resource.base.AbstractContentResource;
import net.sf.mmm.data.resource.base.AbstractContentResource.AbstractContentResourceModifier;

/**
 * This is the abstract base implementation of the {@link DataRepository}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentRepository extends AbstractContentResourceModifier implements
    DataRepository {

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
   * @throws DataCastException if the cast failed because the given
   *         <code>object</code> is NOT compatible with the given
   *         <code>entityClass</code>.
   */
  protected <E extends DataObject> E cast(DataObject object, Class<E> entityClass)
      throws DataCastException {

    try {
      return entityClass.cast(object);
    } catch (ClassCastException e) {
      throw new DataObjectWrongTypeException(e, object, entityClass.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E extends DataObject> E get(DataId id, Class<E> entityClass) throws DataException {

    return cast(get(id), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <E extends DataObject> E get(String path, Class<E> entityClass) throws DataException {

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
  public <E extends ContentResource> E getRawObject(E entity) throws DataException {

    AbstractContentResource object = (AbstractContentResource) entity;
    object = object.getRawObject();
    return (E) object;
  }

  /**
   * {@inheritDoc}
   */
  public DataObject get(String path) throws DataException {

    AbstractContentObject object = getRootFolder();
    // use smart query on persistent store (recursive SQL)?
    // TODO: create Parser for Path
    StringTokenizer st = new StringTokenizer(path, DataObject.PATH_SEPARATOR);
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

  protected DataObject createInstance(DataClass contentClass) throws DataException {

    DataObject object;
    try {
      object = contentClass.getJavaClass().newInstance();
    } catch (Exception e) {
      throw new DataReflectionException(e, "Failed to create instance of class \"{0}\"!",
          contentClass);
    }
    return object;
  }

  /**
   * {@inheritDoc}
   */
  public DataObject create(DataClass contentClass, String name, ContentResource parent)
      throws DataException {

    AbstractContentObject object = (AbstractContentObject) createInstance(contentClass);
    // object.setName(name);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentResource> E createVersion(E resource) throws DataException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void save(DataObject object) throws DataException {

    // TODO Auto-generated method stub

  }

}
