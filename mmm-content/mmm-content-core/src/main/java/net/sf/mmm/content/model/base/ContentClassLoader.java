/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.io.IOException;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.util.resource.DataResource;

/**
 * This is the interface of a "classloader" for {@link ContentClass}es. It is
 * capable of reading a {@link ContentClass} from a configuration (e.g. XML)
 * provided from any data-source (e.g. filesystem, classpath, database).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassLoader {

  /**
   * This method loads the classes by parsing the configuration from the given
   * <code>source</code>. This source may typically point to an XML file on
   * the disc or in the classpath.
   * 
   * @param source is pointing to the origin of the configuration data for the
   *        content-model.
   * @throws IOException if an I/O error occurred when reading the configuration
   *         data.
   * @throws ContentModelException if the configuration data is illegal in any
   *         way.
   */
  void loadClasses(DataResource source) throws IOException, ContentModelException;

}
