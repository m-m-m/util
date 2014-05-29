/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.sequence;

/**
 * This is the interface used to define a sequence. It may be handy to implement this as an {@link Enum} for
 * your particular sequences.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Sequence {

  /**
   * This method gets the database schema where the sequence is located.
   *
   * @return the schema or <code>null</code> to use the default schema.
   */
  String getSchema();

  /**
   * This method gets the name of the sequence. It is recommended to have a naming convention for database
   * objects like sequences.
   *
   * @return the name of the sequence.
   */
  String getName();

}
