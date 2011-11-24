/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.entity.resource.DataEntityResource;
import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.repository.DataRepository;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.base.entity.resource.AbstractDataFolder;
import net.sf.mmm.data.base.reflection.AbstractMutableDataReflectionService;
import net.sf.mmm.data.resource.api.ContentResource;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.CastFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the abstract base implementation of the {@link DataRepository}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataRepository extends AbstractLoggableComponent implements
    DataRepository {

  /** @see #getReflectionService() */
  private AbstractMutableDataReflectionService contentModel;

  /** @see #getRootFolder() */
  private AbstractDataFolder rootFolder;

  /**
   * The constructor.
   */
  public AbstractDataRepository() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataFolder getRootFolder() {

    return this.rootFolder;
  }

  /**
   * @param rootFolder the {@link #getRootFolder() rootFolder} to set.
   */
  public void setRootFolder(AbstractDataFolder rootFolder) {

    this.rootFolder = rootFolder;
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
      throw new CastFailedException(e, object, object.getClass(), entityClass);
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
  public <E extends DataEntityResource> E getByPath(String path, Class<E> entityClass)
      throws DataException {

    return cast(getByPath(path), entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractMutableDataReflectionService getReflectionService() {

    return this.contentModel;
  }

  /**
   * @param contentModel the {@link #getReflectionService() content-model} to
   *        set.
   */
  @Resource
  public void setContentModel(AbstractMutableDataReflectionService contentModel) {

    this.contentModel = contentModel;
  }

  /**
   * {@inheritDoc}
   */
  public DataEntityResource getByPath(String path) throws DataException {

    DataFolder folder = getRootFolder();
    // use smart query on persistent store (recursive SQL)?
    // TODO: create Parser for Path
    DataEntityResource child = folder;
    StringTokenizer st = new StringTokenizer(path, DataEntityResource.PATH_SEPARATOR);
    while (st.hasMoreTokens()) {
      String segment = st.nextToken();
      child = folder.getChild(segment);
      if (child == null) {
        // TODO: return null instead?
        throw new ObjectNotFoundException(DataEntityResource.class, path);
      } else if (child instanceof DataFolder) {
        folder = (DataFolder) child;
      } else if (st.hasMoreElements()) {
        throw new ObjectMismatchException(child, DataFolder.class, path);
      }
    }
    return child;
  }

  protected DataObject createInstance(DataClass<? extends DataObject> dataClass)
      throws DataException {

    DataObject object;
    try {
      object = dataClass.getJavaClass().newInstance();
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, dataClass.getJavaClass());
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, dataClass.getJavaClass());
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
