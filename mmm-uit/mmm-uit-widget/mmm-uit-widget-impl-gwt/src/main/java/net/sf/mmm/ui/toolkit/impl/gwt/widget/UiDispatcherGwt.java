/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import java.util.concurrent.Callable;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiDispatcher;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiDispatcher} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDispatcherGwt extends AbstractUiDispatcher {

  /**
   * The constructor.
   */
  public UiDispatcherGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean sleep() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeAsynchron(final Runnable task) {

    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {

        task.run();
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeTimer(final Callable<Boolean> task, int delayMilliseconds) {

    Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {

      @Override
      public boolean execute() {

        try {
          Boolean repeat = task.call();
          return (Boolean.TRUE.equals(repeat));
        } catch (Exception e) {
          Log.error("Timer task failed: " + task.getClass().getSimpleName(), e);
          return false;
        }
      }
    }, delayMilliseconds);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeTimer(final Runnable task, int delayMilliseconds) {

    Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {

      @Override
      public boolean execute() {

        task.run();
        return false;
      }
    }, delayMilliseconds);
  }

}
