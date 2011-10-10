/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClassLoader;
import net.sf.mmm.content.reflection.api.ContentReflectionEvent;
import net.sf.mmm.content.reflection.api.ContentReflectionException;
import net.sf.mmm.content.reflection.base.AbstractContentClass;
import net.sf.mmm.content.reflection.base.AbstractContentField;
import net.sf.mmm.content.reflection.base.AbstractMutableContentModelService;
import net.sf.mmm.content.reflection.base.ContentClassLoaderStAX;
import net.sf.mmm.content.reflection.impl.statically.ContentClassImpl;
import net.sf.mmm.content.reflection.impl.statically.ContentFieldImpl;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.content.reflection.api.ContentReflectionService} interface that
 * assumes that {@link ContentId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CoreContentModelService extends AbstractMutableContentModelService {

  /** @see #getClassLoader() */
  private ContentClassLoader classLoader;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   */
  public CoreContentModelService() {

    super();
    this.editable = false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * This method sets the {@link #isEditable() editable} flag.
   * 
   * @param editable the editable to set
   */
  public void setEditable(boolean editable) {

    this.editable = editable;
  }

  /**
   * @return the classLoader
   */
  public ContentClassLoader getClassLoader() {

    return this.classLoader;
  }

  /**
   * @param classLoader the classLoader to set
   */
  public void setClassLoader(ContentClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @throws Exception if an I/O error was caused by the class-loader.
   */
  @Override
  @PostConstruct
  public void initialize() throws Exception {

    super.initialize();
    if (getIdManager() == null) {
      setIdManager(new StaticSmartIdManager());
    }
    if (this.classLoader == null) {
      this.classLoader = new ContentClassLoaderStAX(this);
    }
    loadClasses();
  }

  /**
   * This method loads the content-model.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws ContentException if the content-model is invalid.
   */
  protected void loadClasses() throws IOException, ContentException {

    AbstractContentClass rootClass = this.classLoader.loadClasses();
    setRootClass(rootClass);
    addClassRecursive(rootClass);
    AbstractContentClass classClass = getContentClass(getIdManager().getClassClassId());
    if (classClass == null) {
      // TODO:
      throw new ContentReflectionException("Missing class for ContentClass!");
    }
    // ContentClassImpl.setContentClass(classClass);
    AbstractContentClass fieldClass = getContentClass(getIdManager().getFieldClassId());
    if (fieldClass == null) {
      // TODO:
      throw new ContentReflectionException("Missing class for ContentField!");
    }
    // ContentFieldImpl.setContentClass(fieldClass);
  }

  /**
   * This method reloads the content-model. If the external representation of
   * the model has been modified, this method updates the model to import these
   * changes.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws ContentException if the content-model is invalid.
   */
  public void reaload() throws IOException, ContentException {

    loadClasses();
    fireEvent(new ContentReflectionEvent(getRootContentClass(), ChangeEventType.UPDATE));
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createNewClass(ContentId id, String name) {

    AbstractContentClass contentClass = new ContentClassImpl(name, id);
    setContentObjectId(contentClass, id);
    return contentClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField createNewField(ContentId id, String name) {

    AbstractContentField contentField = new ContentFieldImpl(name, id);
    setContentObjectId(contentField, id);
    return contentField;
  }

}
