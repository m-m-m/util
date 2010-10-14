/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.xml.api.XmlException;

/**
 * A {@link XmlInvalidException} is like a {@link org.xml.sax.SAXException} but
 * as a {@link RuntimeException}. Besides it has native language support build
 * in.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class XmlInvalidException extends XmlException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1309150847866589344L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception - e.g. a
   *        {@link org.xml.sax.SAXException}.
   */
  public XmlInvalidException(Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_XML_INVALID);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception - e.g. a
   *        {@link org.xml.sax.SAXException}.
   * @param source describes the source of the invalid XML. Typically this will
   *        be the filename where the XML was read from. It is used in in the
   *        exception message. This will help to find the problem easier.
   */
  public XmlInvalidException(Throwable nested, Object source) {

    super(nested, NlsBundleUtilCore.ERR_XML_INVALID_WITH_SOURCE, toMap(KEY_SOURCE, source));
  }

}
