/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "segment")
public abstract class Segment extends JaxbObject {

  /**
   * The constructor.
   */
  public Segment() {

    super();
  }

  /**
   * This method gets the (minimum) length of this segment.
   * 
   * @return the (minimum) length.
   */
  public abstract long getLength();

}
