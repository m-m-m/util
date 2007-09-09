/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.io.IOException;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;

/**
 * This is the interface of a "classloader" for {@link ContentClass}es. It is
 * capable of reading a {@link ContentClass} from a configuration (e.g. XML)
 * provided from any data-source (e.g. filesystem, classpath, database).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassLoader {

  /**
   * This method loads the classes of the content-model. Depending on the
   * implementation this can happen by parsing the model from an XML
   * configuration file, reading from a database or analyzing the java-classes
   * of the entities.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @return the root-class of the model.
   * @throws IOException if an I/O error occurred when reading the model.
   * @throws ContentModelException if the configured model is illegal in any
   *         way.
   */
  AbstractContentClass loadClasses() throws IOException, ContentModelException;

}
