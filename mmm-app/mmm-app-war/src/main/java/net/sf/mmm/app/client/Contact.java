/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import net.sf.mmm.util.pojo.api.Pojo;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * This is the interface for a contact person.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Contact extends TransferObject, Pojo {

  /** {@link TypedProperty} for {@link #getFirstName()}. */
  TypedProperty<String> PROPERTY_FIRST_NAME = new TypedProperty<String>("First Name", String.class, "firstName");

  /** {@link TypedProperty} for {@link #getLastName()}. */
  TypedProperty<String> PROPERTY_LAST_NAME = new TypedProperty<String>("lastName");

  // /** {@link TypedProperty} for {@link #getBirthday()}. */
  // TypedProperty<LocalDate> PROPERTY_BIRTHDAY = new TypedProperty<LocalDate>("birthday");

  /** {@link TypedProperty} for {@link #getShoeSize()}. */
  TypedProperty<Integer> PROPERTY_SHOE_SIZE = new TypedProperty<Integer>("shoeSize");

  /** {@link TypedProperty} for {@link #getIncome()}. */
  TypedProperty<Double> PROPERTY_INCOME = new TypedProperty<Double>("income");

  /**
   * @return the firstName
   */
  @Mandatory
  String getFirstName();

  /**
   * @param firstName is the firstName to set
   */
  void setFirstName(String firstName);

  /**
   * @return the lastName
   */
  @Mandatory
  String getLastName();

  /**
   * @param lastName is the lastName to set
   */
  void setLastName(String lastName);

  //
  // /**
  // * @return the birthday
  // */
  // @Past
  // LocalDate getBirthday();
  //
  // /**
  // * @param birthday is the birthday to set
  // */
  // void setBirthday(LocalDate birthday);

  /**
   * @return the shoe size.
   */
  @Min(19)
  @Max(52)
  int getShoeSize();

  /**
   * @param size is the new shoe size.
   */
  void setShoeSize(int size);

  /**
   * @return the income in EUR.
   */
  @DecimalMin(value = "0")
  double getIncome();

  /**
   * @param income is the new income.
   */
  void setIncome(double income);

}
