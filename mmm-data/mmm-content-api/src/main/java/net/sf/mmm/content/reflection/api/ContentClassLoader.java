/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface of a "{@link ClassLoader}" for
 * {@link net.sf.mmm.content.reflection.api.ContentClass}es. It is capable of
 * reading a {@link net.sf.mmm.content.reflection.api.ContentClass} from a
 * configuration (e.g. XML) provided from any data-source (e.g. filesystem,
 * classpath, database).<br/>
 * The {@link ContentClassLoader} is intended for internal usage and shall not
 * be used by end-users.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface ContentClassLoader {

  /**
   * This method loads the classes of the content-model. Depending on the
   * implementation this can happen by parsing the model from an XML
   * configuration file, reading from a database or analyzing the java-classes
   * of the entities.
   * 
   * @return the root-class of the model.
   * @throws ContentReflectionException if the configured model is illegal in
   *         any way.
   */
  ContentClass<? extends ContentObject> loadClasses() throws ContentReflectionException;

}
