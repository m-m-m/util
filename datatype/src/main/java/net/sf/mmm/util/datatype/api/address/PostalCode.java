/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.address;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents a postal code (also called ZIP code). This
 * is an identifier of a region, city, or even a district within a country. Different countries have different systems
 * for their postal codes. Some are numeric some are alphanumeric. In any case the postal codes aim to optimize the
 * logistics of mail delivery and typically allow sorting mail for different regions. In most cases the (major) city can
 * be determined from country and postal code. <br>
 * <b>ATTENTION:</b><br>
 * Please note that some countries (e.g. Panama) do not have the concept of postal codes. Further, this datatype is just
 * a container for the actual postal code value. It does NOT perform true validation as this is specific per country.
 *
 * @see #getValue()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PostalCode extends AbstractSimpleDatatype<String> {

  private static final long serialVersionUID = -7499247409257142127L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected PostalCode() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the actual postal code. See {@link #getValue()}.
   */
  public PostalCode(String value) {

    super(value);
    NlsNullPointerException.checkNotNull("value", value);
    // TODO: more precise validation using pattern...
  }

}
