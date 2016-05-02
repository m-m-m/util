/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.uuid.api;

import java.util.UUID;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a factory used to create {@link UUID}s. There can be different implementations
 * for the various {@link UUID#variant() variants} and {@link UUID#version() versions} of {@link UUID}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@ComponentSpecification
public interface UuidFactory {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.uuid.api.UuidFactory";

  /**
   * This method creates a new {@link UUID}.
   * 
   * @return the new {@link UUID}.
   */
  UUID createUuid();

}
