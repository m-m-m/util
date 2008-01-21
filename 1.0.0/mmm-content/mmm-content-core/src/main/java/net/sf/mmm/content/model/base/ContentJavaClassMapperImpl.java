/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.api.ContentObject;

/**
 * This is the implementation of the {@link ContentJavaClassMapper} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentJavaClassMapperImpl implements ContentJavaClassMapper {

  /** @see #getJavaClass(String) */
  private Map<String, Class<? extends ContentObject>> classMap;

  /**
   * The constructor.
   * 
   */
  public ContentJavaClassMapperImpl() {

    super();
    this.classMap = new HashMap<String, Class<? extends ContentObject>>();
  }

  /**
   * @param classMap the classMap to set
   */
  public void setClassMap(Map<String, Class<? extends ContentObject>> classMap) {

    this.classMap = classMap;
  }

  /**
   * This method registers the given <code>javaClass</code> for the given
   * <code>contentClassName</code>.
   * 
   * @see #getJavaClass(String)
   * 
   * @param contentClassName is the name of the associated
   *        {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   * @param javaClass is the java-class to register.
   */
  public void registerJavaClass(String contentClassName, Class<? extends ContentObject> javaClass) {

    this.classMap.put(contentClassName, javaClass);
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends ContentObject> getJavaClass(String contentClassName) {

    return this.classMap.get(contentClassName);
  }

}
