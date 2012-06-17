/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassLoader;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.access.DataClassReadAccessById;
import net.sf.mmm.data.api.reflection.access.DataClassReadAccessByTitle;
import net.sf.mmm.data.api.reflection.access.DataFieldReadAccessById;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the abstract base implementation of the {@link DataClassLoader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataClassLoader implements DataClassLoader {

  /** @see #getDataReflectionService() */
  private final AbstractMutableDataReflectionService dataReflectionService;

  /**
   * The constructor.
   * 
   * @param contentModelService is the {@link #getDataReflectionService()
   *        content-model service}.
   */
  public AbstractDataClassLoader(AbstractMutableDataReflectionService contentModelService) {

    super();
    this.dataReflectionService = contentModelService;
  }

  /**
   * This method gets the content-model service.
   * 
   * @return the content-model service.
   */
  protected AbstractMutableDataReflectionService getDataReflectionService() {

    return this.dataReflectionService;
  }

  /**
   * This method gets the {@link ClassResolver} to use.
   * 
   * @return the {@link ClassResolver}.
   */
  protected ClassResolver getClassResolver() {

    return this.dataReflectionService;
  }

  /**
   * This method gets the {@link ReflectionUtil} instance.
   * 
   * @return the {@link ReflectionUtil}.
   */
  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtilImpl.getInstance();
  }

  /**
   * This inner class is the context used to cache results during class-loading.
   */
  @SuppressWarnings("rawtypes")
  public static class Context implements DataFieldReadAccessById, DataClassReadAccessById,
      DataClassReadAccessByTitle {

    /** @see #getDataClass(String) */
    private final Map<String, AbstractDataClass> name2classMap;

    /** @see #getDataClass(long) */
    private final Map<Long, AbstractDataClass> id2classMap;

    /** @see #getDataField(long) */
    private final Map<Long, AbstractDataField> id2fieldMap;

    /**
     * The constructor.
     */
    public Context() {

      super();
      this.name2classMap = new HashMap<String, AbstractDataClass>();
      this.id2classMap = new HashMap<Long, AbstractDataClass>();
      this.id2fieldMap = new HashMap<Long, AbstractDataField>();
    }

    /**
     * {@inheritDoc}
     */
    public AbstractDataClass<? extends DataObjectView> getDataClass(String name) {

      return this.name2classMap.get(name);
    }

    /**
     * {@inheritDoc}
     */
    public AbstractDataClass<? extends DataObjectView> getDataClass(long id) {

      return this.id2classMap.get(Long.valueOf(id));
    }

    /**
     * {@inheritDoc}
     */
    public DataField<? extends DataObjectView, ?> getDataField(DataId id)
        throws ObjectNotFoundException {

      return getDataField(id.getObjectId());
    }

    /**
     * {@inheritDoc}
     */
    public DataClass<? extends DataObjectView> getDataClass(DataId id) {

      NlsNullPointerException.checkNotNull(DataId.class, id);
      if (id.getClassId() == DataClass.CLASS_ID) {
        return getDataClass(id.getObjectId());
      } else {
        return getDataClass(id.getClassId());
      }
    }

    /**
     * {@inheritDoc}
     */
    public AbstractDataField<? extends DataObjectView, ?> getDataField(long id) {

      return this.id2fieldMap.get(Long.valueOf(id));
    }

    /**
     * This method registers the given <code>contentClass</code>.
     * 
     * @param contentClass is the content-class to add.
     * @throws DuplicateObjectException if a content-class is already registered
     *         with the same name or id.
     */
    public void put(AbstractDataClass contentClass) throws DuplicateObjectException {

      AbstractDataClass old;
      old = this.name2classMap.put(contentClass.getTitle(), contentClass);
      if (old != null) {
        throw new DuplicateObjectException(DataClass.class.getName(), contentClass.getTitle());
      }
      old = this.id2classMap.put(contentClass.getId(), contentClass);
      if (old != null) {
        throw new DuplicateObjectException(DataClass.class.getName(), contentClass.getId());
      }
    }

  }

}
