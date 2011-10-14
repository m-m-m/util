/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a component that can
 * {@link #save(DataClass, OutputStream) serialize} and
 * {@link #load(InputStream) de-serialize} the content-model (reflection). A
 * typical implementation will use XML as representation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DataReflectionSerializer {

  /**
   * This method loads the content-model from the given {@link InputStream}.
   * 
   * @param inputStream is the {@link InputStream} pointing to the
   *        {@link #save(DataClass, OutputStream) serialized} content-model.
   * @return the {@link DataClass#getSuperClass() root} {@link DataClass}.
   */
  DataClass<?> load(InputStream inputStream);

  /**
   * This method save the content-model identified by the given
   * <code>toplevelClass</code> to the given {@link OutputStream}.
   * 
   * @param toplevelClass is the top-level {@link DataClass} to save. It
   *        should be the {@link DataClass#getSuperClass() root}
   *        {@link DataClass}.
   * @param outputStream is the {@link OutputStream} to write to.
   */
  void save(DataClass<?> toplevelClass, OutputStream outputStream);

}
