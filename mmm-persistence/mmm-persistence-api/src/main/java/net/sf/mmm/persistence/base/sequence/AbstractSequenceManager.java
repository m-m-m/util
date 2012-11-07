/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.sequence;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.persistence.api.sequence.SequenceManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link SequenceManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSequenceManager extends AbstractLoggableComponent implements
    SequenceManager {

  /**
   * The constructor.
   */
  public AbstractSequenceManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void reset(Sequence sequence) {

    NlsNullPointerException.checkNotNull(Sequence.class, sequence);
    setValue(sequence, sequence.getMinimumValue());
  }

}
