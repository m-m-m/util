/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

/**
 * A {@link XmlGenericException} is used to wrap exceptions of underlying XML-frameworks such as StAX or SAX
 * that throw checked exceptions.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlGenericException extends XmlException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5597494957022339458L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public XmlGenericException(Throwable nested) {

    super(nested, nested.getMessage());
  }

}
