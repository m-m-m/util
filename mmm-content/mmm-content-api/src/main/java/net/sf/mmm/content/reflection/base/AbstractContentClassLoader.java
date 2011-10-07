/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.api.ContentIdManager;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentClassLoader;
import net.sf.mmm.content.reflection.api.MutableContentModelService;
import net.sf.mmm.content.reflection.api.access.ContentClassReadAccessById;
import net.sf.mmm.content.reflection.api.access.ContentClassReadAccessByName;
import net.sf.mmm.content.reflection.api.access.ContentFieldReadAccessById;
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
public abstract class AbstractContentClassLoader<CLASS> implements ContentClassLoader<CLASS> {

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

  protected ClassResolver getClassResolver() {

    return this.contentModelService;
  }

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
  public static class Context implements ContentFieldReadAccessById, ContentClassReadAccessById,
      ContentClassReadAccessByName {

    /** @see #getContentClass(String) */
    private final Map<String, AbstractContentClass> name2classMap;

    /** @see #getContentClass(ContentId) */
    private final Map<ContentId, AbstractContentClass> id2classMap;

    /** @see #getContentField(ContentId) */
    private final Map<ContentId, AbstractContentField> id2fieldMap;

    /**
     * The constructor.
     */
    public Context() {

      super();
      this.name2classMap = new HashMap<String, AbstractContentClass>();
      this.id2classMap = new HashMap<ContentId, AbstractContentClass>();
      this.id2fieldMap = new HashMap<ContentId, AbstractContentField>();
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass getContentClass(String name) {

      return this.name2classMap.get(name);
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass getContentClass(ContentId id) {

      return this.id2classMap.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentClass getContentClass(ContentId id, String name) {

      AbstractContentClass idClass = getContentClass(id);
      AbstractContentClass nameClass = getContentClass(name);
      if (idClass == nameClass) {
        return idClass;
      } else {
        throw new DuplicateObjectException(ContentClass.class.getName(), id);
      }
    }

    /**
     * {@inheritDoc}
     */
    public AbstractContentField getContentField(ContentId id) {

      return this.id2fieldMap.get(id);
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
      old = this.id2classMap.put(contentClass.getContentId(), contentClass);
      if (old != null) {
        throw new DuplicateObjectException(ContentClass.class.getName(),
            contentClass.getContentId());
      }
    }

  }

}
