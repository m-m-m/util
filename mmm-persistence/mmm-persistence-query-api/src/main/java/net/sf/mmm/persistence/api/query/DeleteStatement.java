/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query;

/**
 * This is the interface for a delete statement.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DeleteStatement extends JpqlStatement {

  /**
   * This method executes a JQPL DELETE statement.
   * 
   * @see javax.persistence.Query#executeUpdate()
   * 
   * @return the number of entities that have been deleted.
   */
  int executeDelete();

}
