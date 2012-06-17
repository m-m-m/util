/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate.usertype;

import java.sql.Types;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the abstract base implementation of {@link org.hibernate.usertype.UserType} to map a {@link String}
 * based {@link Datatype}.
 * 
 * @param <T> the generic for the adapted {@link Datatype}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class StringDatatypeUserType<T extends Datatype<String>> extends DatatypeUserType<String, T> {

  /**
   * The constructor.
   * 
   * @param datatype is the {@link #returnedClass() java class} representing the adapted {@link Datatype}.
   */
  public StringDatatypeUserType(Class<T> datatype) {

    super(Types.VARCHAR, datatype, String.class);
  }

}
