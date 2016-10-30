/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.query.api.statement.StatementFactory;

/**
 * This is the abstract base implementation of {@link StatementFactory}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractStatementFactory extends AbstractLoggableComponent implements StatementFactory {

  /**
   * The constructor.
   */
  public AbstractStatementFactory() {
    super();
  }

}
