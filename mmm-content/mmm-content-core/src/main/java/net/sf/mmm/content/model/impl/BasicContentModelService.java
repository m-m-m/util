/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.AbstractMutableContentModelService;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.ContentClassLoaderStAX;
import net.sf.mmm.content.model.base.ContentClassLoader;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.impl.StaticSmartIdManager;
import net.sf.mmm.util.event.ChangeEvent;
import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.util.resource.DataResource;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService} interface that
 * assumes that {@link SmartId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicContentModelService extends AbstractMutableContentModelService {

  /** @see #getClassLoader() */
  private ContentClassLoader classLoader;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   */
  public BasicContentModelService() {

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
   * @throws IOException if an I/O error was caused by the class-loader.
   */
  @PostConstruct
  public void initialize() throws IOException {

    if (getIdManager() == null) {
      setIdManager(new StaticSmartIdManager());
    }
    if (this.classLoader == null) {
      this.classLoader = new ContentClassLoaderStAX(this);
    }
    initializeSystemClasses();
    loadClasses();
  }

  /**
   * This method initializes the system classes.
   */
  protected void initializeSystemClasses() {

    // object
    AbstractContentClass rootClass = createOrUpdateClass(getIdManager().getRootClassId(),
        ContentObject.CLASS_NAME, null, ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE, false,
        ContentObject.class);
    setRootClass(rootClass);
    // reflection object
    AbstractContentClass reflectionClass = createOrUpdateClass(getIdManager().getClassId(
        ContentReflectionObject.CLASS_ID), ContentReflectionObject.CLASS_NAME, rootClass,
        ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE, false, ContentReflectionObject.class);
    // class
    AbstractContentClass classClass = createOrUpdateClass(getIdManager().getClassClassId(),
        ContentClass.CLASS_NAME, reflectionClass, ClassModifiersImpl.SYSTEM_FINAL, false,
        ContentClass.class);
    ContentClassImpl.setContentClass(classClass);
    // field
    AbstractContentClass fieldClass = createOrUpdateClass(getIdManager().getFieldClassId(),
        ContentField.CLASS_NAME, reflectionClass, ClassModifiersImpl.SYSTEM_FINAL, false,
        ContentField.class);
    ContentFieldImpl.setContentClass(fieldClass);
  }

  /**
   * This method loads the content-model.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws ContentException if the content-model is invalid.
   */
  protected void loadClasses() throws IOException, ContentException {

    DataResource resource = getModelResource();
    this.classLoader.loadClasses(resource);
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
    fireEvent(new ContentModelEvent(getRootClass(), ChangeEvent.Type.UPDATE));
  }

  /**
   * This method gets the resource that points to the external representation of
   * the content-model.
   * 
   * @return the resource pointing to the content-model.
   */
  protected DataResource getModelResource() {

    return new ClasspathResource(XML_MODEL_LOCATION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractContentClass newClass() {

    return new ContentClassImpl();
  }

  /**
   * {@inheritDoc}
   */
  protected AbstractContentField newField() {

    return new ContentFieldImpl();
  }

}
