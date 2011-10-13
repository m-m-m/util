/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.data.api.ContentIdManager;
import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.reflection.api.ContentClassLoader;
import net.sf.mmm.data.reflection.api.access.ContentClassReadAccessById;
import net.sf.mmm.data.reflection.api.access.ContentClassReadAccessByTitle;
import net.sf.mmm.data.reflection.api.access.ContentFieldReadAccessById;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the abstract base implementation of the {@link ContentClassLoader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentClassLoader implements ContentClassLoader {

  /** @see #getContentModelService() */
  private final AbstractMutableContentModelService contentModelService;

  /**
   * The constructor.
   * 
   * @param contentModelService is the {@link #getContentModelService()
   *        content-model service}.
   */
  public AbstractContentClassLoader(AbstractMutableContentModelService contentModelService) {

    super();
    this.contentModelService = contentModelService;
  }

  /**
   * This method gets the content-model service.
   * 
   * @return the content-model service.
   */
  protected AbstractMutableContentModelService getContentModelService() {

    return this.contentModelService;
  }

  /**
   * This method gets the {@link ClassResolver} to use.
   * 
   * @return the {@link ClassResolver}.
   */
  protected ClassResolver getClassResolver() {

    return this.contentModelService;
  }

  /**
   * This method gets the {@link ContentIdManager}.
   * 
   * @return the {@link ContentIdManager}.
   */
  protected ContentIdManager getIdManager() {

    return this.contentModelService.getIdManager();
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
  public static class Context implements ContentFieldReadAccessById, ContentClassReadAccessById,
      ContentClassReadAccessByTitle {

    /** @see #getContentClass(String) */
    private final Map<String, AbstractContentClass> name2classMap;

    /** @see #getContentClass(long) */
    private final Map<Long, AbstractContentClass> id2classMap;

    /** @see #getContentField(long) */
    private final Map<Long, AbstractContentField> id2fieldMap;

    /**
     * The constructor.
     */
    public Context() {

      super();
      this.name2classMap = new HashMap<String, AbstractContentClass>();
      this.id2classMap = new HashMap<Long, AbstractContentClass>();
      this.id2fieldMap = new HashMap<Long, AbstractContentField>();
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass<? extends ContentObject> getContentClass(String name) {

      return this.name2classMap.get(name);
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass<? extends ContentObject> getContentClass(long id) {

      return this.id2classMap.get(Long.valueOf(id));
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass<? extends ContentObject> getContentClass(long id, String name) {

      AbstractContentClass<? extends ContentObject> idClass = getContentClass(id);
      AbstractContentClass<? extends ContentObject> nameClass = getContentClass(name);
      if (idClass == nameClass) {
        return idClass;
      } else {
        throw new DuplicateObjectException(ContentClass.class.getName(), Long.valueOf(id));
      }
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentField<? extends ContentObject, ?> getContentField(long id) {

      return this.id2fieldMap.get(Long.valueOf(id));
    }

    /**
     * This method registers the given <code>contentClass</code>.
     * 
     * @param contentClass is the content-class to add.
     * @throws DuplicateObjectException if a content-class is already registered
     *         with the same name or id.
     */
    public void put(AbstractContentClass contentClass) throws DuplicateObjectException {

      AbstractContentClass old;
      old = this.name2classMap.put(contentClass.getTitle(), contentClass);
      if (old != null) {
        throw new DuplicateObjectException(ContentClass.class.getName(), contentClass.getTitle());
      }
      old = this.id2classMap.put(contentClass.getId(), contentClass);
      if (old != null) {
        throw new DuplicateObjectException(ContentClass.class.getName(), contentClass.getId());
      }
    }

  }

}
