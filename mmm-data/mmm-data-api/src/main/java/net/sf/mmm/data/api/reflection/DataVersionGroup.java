/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

/**
 * A {@link DataVersionGroup} groups a set of {@link DataClass}es like a package
 * in java. This is used for version controlling the schema (DDL) of classes
 * within the same namespace. If a newer version is detected an automatic
 * migration can be performed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataVersionGroup {

  /**
   * This method gets the na
   * 
   * @return
   */
  String getNamespace();

  String getVersion();

}
