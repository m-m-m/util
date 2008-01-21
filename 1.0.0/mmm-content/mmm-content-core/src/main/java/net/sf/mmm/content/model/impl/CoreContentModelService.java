/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.AbstractMutableContentModelService;
import net.sf.mmm.content.model.base.ContentClassLoaderStAX;
import net.sf.mmm.content.model.base.ContentClassLoader;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.impl.StaticSmartIdManager;
import net.sf.mmm.util.event.ChangeEvent;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService} interface that
 * assumes that {@link SmartId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
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
      throw new ContentModelException("Missing class for ContentClass!");
    }
    // ContentClassImpl.setContentClass(classClass);
    AbstractContentClass fieldClass = getContentClass(getIdManager().getFieldClassId());
    if (fieldClass == null) {
      // TODO:
      throw new ContentModelException("Missing class for ContentField!");
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
    fireEvent(new ContentModelEvent(getRootContentClass(), ChangeEvent.Type.UPDATE));
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createNewClass(SmartId id, String name) {

    AbstractContentClass contentClass = new ContentClassImpl(name, id);
    setContentObjectId(contentClass, id);
    return contentClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField createNewField(SmartId id, String name) {

    AbstractContentField contentField = new ContentFieldImpl(name, id);
    setContentObjectId(contentField, id);
    return contentField;
  }

}
