/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassCachingStrategy;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.base.reflection.AbstractDataClass;
import net.sf.mmm.data.base.reflection.AbstractDataField;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataClass} interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@Table(name = "DATA_CLASS")
@DataClassAnnotation(id = DataClass.CLASS_ID, title = DataClass.CLASS_TITLE)
@DiscriminatorValue("" + DataClass.CLASS_ID)
public final class DataClassImpl<CLASS extends DataObjectView> extends AbstractDataClass<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -6926223109885122995L;

  /** @see #getDeclaredFieldCollection() */
  private Collection<DataFieldImpl> declaredFieldCollection;

  /** @see #getSubClassesList() */
  private List<DataClassImpl<? extends CLASS>> subClasses;

  /** the list of direct sub-classes */
  private List<DataClassImpl> subClassesList;

  /**
   * The constructor.
   */
  public DataClassImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Class getJavaClass() {

    return super.getJavaClass();
  }

  /**
   * @return the declaredFieldCollection
   */
  @OneToMany(mappedBy = "declaringClass", cascade = CascadeType.ALL)
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Collection<DataFieldImpl> getDeclaredFieldCollection() {

    if (this.declaredFieldCollection == null) {
      this.declaredFieldCollection = (Collection) getDeclaredFields();
    }
    return this.declaredFieldCollection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public List<DataClassImpl<? extends CLASS>> getSubClasses() {

    if (this.subClasses == null) {
      return Collections.emptyList();
    }
    return this.subClasses;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addSubClass(AbstractDataClass<? extends CLASS> subClass)
      throws DataReflectionException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new DataReflectionException("Sub-Class must have this class as super-class!");
    }
    if (subClass.getModifiers().isSystem() && !getModifiers().isSystem()) {
      // TODO: NLS
      throw new DataReflectionException("System-class can NOT extend user-class!");
    }
    if (getModifiers().isFinal()) {
      // TODO: NLS
      throw new DataReflectionException("Can NOT extend final class!");
    }
    // idem-potent operation
    if (!this.subClassesList.contains(subClass)) {
      this.subClassesList.add((DataClassImpl<? extends CLASS>) subClass);
    }
  }

  /**
   * @return the subClassesList
   */
  @OneToMany(mappedBy = "superClass")
  public List<DataClassImpl> getSubClassesList() {

    if (this.subClassesList == null) {
      setSubClassesList(new ArrayList<DataClassImpl>());
    }
    return this.subClassesList;
  }

  /**
   * @param subClassesList is the subClassesList to set
   */
  protected void setSubClassesList(List<DataClassImpl> subClassesList) {

    this.subClassesList = subClassesList;
    this.subClasses = (List) Collections.unmodifiableList(this.subClassesList);
  }

  /**
   * @param declaredFieldCollection is the declaredFieldCollection to set
   */
  protected void setDeclaredFieldCollection(Collection<DataFieldImpl> declaredFieldCollection) {

    this.declaredFieldCollection = declaredFieldCollection;
    for (DataFieldImpl<CLASS, ?> field : declaredFieldCollection) {
      addDeclaredField(field);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addDeclaredField(AbstractDataField<CLASS, ?> field) throws DataReflectionException {

    super.addDeclaredField(field);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ManyToOne()
  public DataClassImpl<? extends DataObjectView> getSuperClass() {

    return (DataClassImpl<? extends DataObjectView>) super.getSuperClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
  public DataClassGroupVersionImpl getGroupVersion() {

    return (DataClassGroupVersionImpl) super.getGroupVersion();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Type(type = "net.sf.mmm.data.impl.datatype.usertype.DataClassCachingStrategyUserType")
  public DataClassCachingStrategy getCachingStrategy() {

    return super.getCachingStrategy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Type(type = "net.sf.mmm.data.impl.datatype.usertype.DataClassModifiersUserType")
  public DataClassModifiers getModifiers() {

    return super.getModifiers();
  }

}
