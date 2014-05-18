/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.gwt;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactBean implements Contact {

  /** UID for serialization. */
  private static final long serialVersionUID = 985011128511803342L;

  /** @see #getFirstName() */
  private String firstName;

  /** @see #getLastName() */
  private String lastName;

  // /** @see #getBirthday() */
  // private LocalDate birthday;

  /** @see #getShoeSize() */
  private int shoeSize;

  /** @see #getIncome() */
  private double income;

  /**
   * The constructor.
   */
  public ContactBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastName() {

    return this.lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public LocalDate getBirthday() {
  //
  // return this.birthday;
  // }
  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public void setBirthday(LocalDate birthday) {
  //
  // this.birthday = birthday;
  // }

  /**
   * {@inheritDoc}
   */
  public int getShoeSize() {

    return this.shoeSize;
  }

  /**
   * {@inheritDoc}
   */
  public void setShoeSize(int shoeSize) {

    this.shoeSize = shoeSize;
  }

  /**
   * {@inheritDoc}
   */
  public double getIncome() {

    return this.income;
  }

  /**
   * {@inheritDoc}
   */
  public void setIncome(double income) {

    this.income = income;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();
    result.append(this.lastName);
    result.append(", ");
    result.append(this.firstName);
    // if (this.birthday != null) {
    // result.append(" (*");
    // result.append(this.birthday);
    // result.append(")");
    // }
    return result.toString();
  }

}
