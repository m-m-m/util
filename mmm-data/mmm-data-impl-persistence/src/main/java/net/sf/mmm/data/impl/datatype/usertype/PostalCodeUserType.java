/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype.usertype;

import net.sf.mmm.persistence.impl.hibernate.usertype.StringDatatypeUserType;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * This is the implementation of the {@link org.hibernate.usertype.UserType} to map the datatype
 * {@link PostalCode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PostalCodeUserType extends StringDatatypeUserType<PostalCode> {

  /**
   * The constructor.
   */
  public PostalCodeUserType() {

    super(PostalCode.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PostalCode toDatatype(String value) {

    return new PostalCode(value);
  }

}
