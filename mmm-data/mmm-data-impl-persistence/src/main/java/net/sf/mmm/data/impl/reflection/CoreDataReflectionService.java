/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import java.io.IOException;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClassLoader;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.base.reflection.AbstractDataClass;
import net.sf.mmm.data.base.reflection.AbstractDataField;
import net.sf.mmm.data.base.reflection.AbstractMutableDataReflectionService;
import net.sf.mmm.data.base.reflection.DataClassLoaderStAX;
import net.sf.mmm.data.impl.DataIdManagerImpl;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataReflectionService} interface that
 * assumes that {@link DataId}s are used as well as specific implementations for
 * class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CoreDataReflectionService extends AbstractMutableDataReflectionService {

  /** @see #getClassLoader() */
  private DataClassLoader classLoader;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   */
  public CoreDataReflectionService() {

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
  public DataClassLoader getClassLoader() {

    return this.classLoader;
  }

  /**
   * @param classLoader the classLoader to set
   */
  public void setClassLoader(DataClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doInitialize() {

    super.initialize();
    if (getDataIdManager() == null) {
      DataIdManagerImpl idManager = new DataIdManagerImpl();
      idManager.initialize();
      setDataIdManager(idManager);
    }
    if (this.classLoader == null) {
      this.classLoader = new DataClassLoaderStAX(this);
    }
    loadClasses();
  }

  /**
   * This method loads the content-model.
   */
  protected void loadClasses() {

    AbstractDataClass<? extends DataObject> rootClass = (AbstractDataClass<? extends DataObject>) this.classLoader
        .loadClasses();
    setRootClass(rootClass);
    addClassRecursive(rootClass);
    AbstractDataClass<? extends DataObject> classClass = getDataClass(getDataIdManager()
        .getClassClassId());
    if (classClass == null) {
      // TODO:
      throw new DataReflectionException("Missing class for ContentClass!");
    }
    // ContentClassImpl.setContentClass(classClass);
    AbstractDataClass<? extends DataObject> fieldClass = getDataClass(getDataIdManager()
        .getFieldClassId());
    if (fieldClass == null) {
      // TODO:
      throw new DataReflectionException("Missing class for ContentField!");
    }
    // ContentFieldImpl.setContentClass(fieldClass);
  }

  /**
   * This method reloads the content-model. If the external representation of
   * the model has been modified, this method updates the model to import these
   * changes.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws DataException if the content-model is invalid.
   */
  public void reaload() throws IOException, DataException {

    loadClasses();
    fireEvent(new DataReflectionEvent(getRootDataClass(), ChangeType.UPDATE));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <CLASS extends DataObject> AbstractDataClass<CLASS> createDataClass() {

    return new DataClassImpl<CLASS>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <CLASS extends DataObject, FIELD> AbstractDataField<CLASS, FIELD> createDataField() {

    return new DataFieldImpl<CLASS, FIELD>();
  }

}
