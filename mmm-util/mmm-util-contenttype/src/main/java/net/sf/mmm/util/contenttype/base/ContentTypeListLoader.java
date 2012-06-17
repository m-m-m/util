/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the {@link XmlBeanMapper} used to load the {@link ContentTypeList}
 * via JAXB.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentTypeListLoader extends XmlBeanMapper<ContentTypeList> {

  /**
   * The constructor.
   */
  public ContentTypeListLoader() {

    super(ContentTypeList.class);
    setXIncludeAware(true);
  }

}
