/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.sequence;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.persistence.api.sequence.SequenceManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of the {@link SequenceManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract class AbstractSequenceManager extends AbstractLoggableComponent implements SequenceManager {

  /**
   * The constructor.
   */
  public AbstractSequenceManager() {

    super();
  }

  /**
   * A {@link String} identifying the {@link Sequence} with {@link Sequence#getSchema() schema} and
   * {@link Sequence#getName() name}.
   *
   * @param sequence is the {@link Sequence}.
   * @return the {@link String} identifier.
   */
  protected String getSequenceAsString(Sequence sequence) {

    String schema = sequence.getSchema();
    if (schema == null) {
      return sequence.getName();
    } else {
      return schema + "." + sequence.getName();
    }
  }

}
