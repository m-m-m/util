/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.descriptor;

import net.sf.mmm.framework.api.ComponentDescriptor;

/**
 * This is the abstract baee implementation of the {@link ComponentDescriptor}
 * interface.
 * 
 * @param <S>
 *        is the {@link #getSpecification() specification} of the provided
 *        component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentDescriptor<S> implements ComponentDescriptor<S> {

  /** @see #getName() */
  private String name;

  /** @see #getCategory() */
  private String category;

  /** @see #getDomain() */
  private String domain;

  /**
   * The constructor.
   */
  public AbstractComponentDescriptor() {

    super();
    this.name = null;
    this.category = null;
    this.domain = null;
  }

  /**
   * This method should be called from the constructor of the implementing
   * class to initialize the descriptor.
   */
  protected void initialize() {

    if (this.name == null) {
      this.name = getSpecification().getCanonicalName();
    }
    if (this.category == null) {
      this.category = getSpecification().getName();
    }
    if (this.domain == null) {
      // TODO
      String specification = getSpecification().getName();
      if (specification.startsWith("org") || specification.startsWith("com")) {
        int index = specification.indexOf('.', 5);
        if (index > 0) {
          this.domain = specification.substring(0, index);
        } else {
          this.domain = specification;
        }
      } else if (specification.startsWith("java")) {
        this.domain = "java";
      } else {
        int index = specification.indexOf('.');
        index = specification.indexOf('.', index);
        index = specification.indexOf('.', index);
        if (index > 0) {
          this.domain = specification.substring(0, index);
        } else {
          this.domain = specification;
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getCategory() {

    return this.category;
  }

  /**
   * {@inheritDoc}
   */
  public String getDomain() {

    return this.domain;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method sets the {@link #getCategory() category}.
   * 
   * @param newCategory
   *        is the category to set.
   */
  public void setCategory(String newCategory) {

    this.category = newCategory;
  }

  /**
   * This method sets the {@link #getDomain() domain}.
   * 
   * @param newDomain
   *        is the domain to set.
   */
  public void setDomain(String newDomain) {

    this.domain = newDomain;
  }

  /**
   * This method sets the {@link #getName() name}.
   * 
   * @param newName
   *        is the name to set.
   */
  public void setName(String newName) {

    this.name = newName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    int bufferSize = 3;
    bufferSize += this.name.length();
    String classname = getSpecification().getName();
    bufferSize += classname.length();
    StringBuffer sb = new StringBuffer(bufferSize);
    sb.append(this.name);
    sb.append(" [");
    sb.append(classname);
    sb.append(']');
    assert (sb.length() == bufferSize);
    return sb.toString();
  }

}
