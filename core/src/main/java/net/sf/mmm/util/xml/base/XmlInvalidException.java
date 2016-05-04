/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.xml.api.XmlException;

/**
 * A {@link XmlInvalidException} is like a {@link org.xml.sax.SAXException} but as a {@link RuntimeException}. Besides
 * it has native language support build in.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class XmlInvalidException extends XmlException {

  private static final long serialVersionUID = 1309150847866589344L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "XmlInvalid";

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception - e.g. a {@link org.xml.sax.SAXException}.
   */
  public XmlInvalidException(Throwable nested) {

    this(nested, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception - e.g. a {@link org.xml.sax.SAXException}.
   * @param source describes the source of the invalid XML. Typically this will be the filename where the XML was read
   *        from. It is used in in the exception message. This will help to find the problem easier.
   */
  public XmlInvalidException(Throwable nested, Object source) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorXmlInvalid(source));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
