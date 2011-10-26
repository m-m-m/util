/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.repository.DataObjectWrongTypeException;
import net.sf.mmm.data.api.repository.DataRepository;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.base.reflection.AbstractMutableDataModelService;
import net.sf.mmm.data.resource.api.ContentResource;
import net.sf.mmm.data.resource.base.AbstractDataResource;
import net.sf.mmm.data.resource.base.AbstractDataResource.AbstractDataResourceModifier;
import net.sf.mmm.util.reflect.api.CastFailedException;

/**
 * This is the abstract base implementation of the {@link DataRepository}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataRepository extends AbstractDataResourceModifier implements
    DataRepository {

  /** @see #getReflectionService() */
  private AbstractMutableDataModelService contentModel;

  /** @see #getRootFolder() */
  private AbstractDataObject rootFolder;

  /**
   * The constructor.
   */
  public AbstractDataRepository() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataObject getRootFolder() {

    return this.rootFolder;
  }

  /**
   * @param rootFolder the {@link #getRootFolder() rootFolder} to set.
   */
  public void setRootFolder(AbstractDataObject rootFolder) {

    this.rootFolder = rootFolder;
    setContentObjectClassAccess(this.rootFolder, getReflectionService());

  }

  /**
   * This method handles the cast of a <code>object</code> to the given
   * <code>entityClass</code>.
   * 
   * @param <E> is the generic type of the <code>entityClass</code>.
   * @param object is the object to cast.
   * @param entityClass is the expected type of the given <code>object</code>.
   * @return the given <code>object</code> casted to <code>entityClass</code>.
   * @throws CastFailedException if the cast failed because the given
   *         <code>object</code> is NOT compatible with the given
   *         <code>entityClass</code>.
   */
  protected <E extends DataObject> E cast(DataObject object, Class<E> entityClass)
      throws CastFailedException {

    try {
      return entityClass.cast(object);
    } catch (ClassCastException e) {
      throw new DataObjectWrongTypeException(e, object, entityClass.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E extends DataObject> E getById(DataId id, Class<E> entityClass) throws DataException {

    return cast(getById(id), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <E extends DataObject> E getByPath(String path, Class<E> entityClass) throws DataException {

    return cast(getByPath(path), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractMutableDataModelService getReflectionService() {

    return this.contentModel;
  }

  /**
   * @param contentModel the {@link #getReflectionService() content-model} to set.
   */
  @Resource
  public void setContentModel(AbstractMutableDataModelService contentModel) {

    this.contentModel = contentModel;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends ContentResource> E getRawObject(E entity) throws DataException {

    AbstractDataResource object = (AbstractDataResource) entity;
    object = object.getRawObject();
    return (E) object;
  }

  /**
   * {@inheritDoc}
   */
  public DataObject getByPath(String path) throws DataException {

    AbstractDataObject object = getRootFolder();
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

    AbstractDataObject object = (AbstractDataObject) createInstance(contentClass);
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
