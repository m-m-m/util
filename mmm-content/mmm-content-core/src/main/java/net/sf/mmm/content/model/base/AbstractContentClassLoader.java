/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.base.AbstractContentObject.AbstractContentObjectModifier;
import net.sf.mmm.content.model.api.access.ContentClassReadAccessById;
import net.sf.mmm.content.model.api.access.ContentClassReadAccessByName;
import net.sf.mmm.content.model.api.access.ContentFieldReadAccessById;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.api.ClassResolver;

/**
 * This is the abstract base implementation of the {@link ContentClassLoader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClassLoader extends AbstractContentObjectModifier implements
    ContentClassLoader {

  /** @see #getContentModelService() */
  private final AbstractMutableContentModelService contentModelService;

  /**
   * The constructor.
   * 
   * @param contentModelService is the
   *        {@link #getContentModelService() content-model service}.
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

  protected ContentReflectionFactory getContentReflectionFactory() {

    return this.contentModelService;
  }

  protected SmartIdManager getIdManager() {

    return this.contentModelService.getIdManager();
  }

  /**
   * This method gets the {@link ReflectionUtil} instance.
   * 
   * @return the {@link ReflectionUtil}.
   */
  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtil.getInstance();
  }

  /**
   * This inner class is the context used to cache results during class-loading.
   */
  public static class Context implements ContentFieldReadAccessById, ContentClassReadAccessById,
      ContentClassReadAccessByName {

    /** @see #getContentClass(String) */
    private final Map<String, AbstractContentClass> name2classMap;

    /** @see #getContentClass(ContentId) */
    private final Map<SmartId, AbstractContentClass> id2classMap;

    /** @see #getContentField(ContentId) */
    private final Map<SmartId, AbstractContentField> id2fieldMap;

    /**
     * The constructor.
     */
    public Context() {

      super();
      this.name2classMap = new HashMap<String, AbstractContentClass>();
      this.id2classMap = new HashMap<SmartId, AbstractContentClass>();
      this.id2fieldMap = new HashMap<SmartId, AbstractContentField>();
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
        // TODO
        throw new DuplicateClassException(id);
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
     * @throws DuplicateClassException if a content-class is already registered
     *         with the same name or id.
     */
    public void put(AbstractContentClass contentClass) throws DuplicateClassException {

      AbstractContentClass old;
      old = this.name2classMap.put(contentClass.getName(), contentClass);
      if (old != null) {
        // TODO
        throw new DuplicateClassException(contentClass.getName());
      }
      old = this.id2classMap.put(contentClass.getId(), contentClass);
      if (old != null) {
        // TODO
        throw new DuplicateClassException(old, contentClass);
      }
    }

  }

}
