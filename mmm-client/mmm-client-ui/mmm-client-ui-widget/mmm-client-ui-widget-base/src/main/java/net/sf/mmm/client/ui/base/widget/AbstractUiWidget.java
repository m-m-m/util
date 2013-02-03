/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.feature.AbstractUiFeatureValue;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.client.ui.api.widget.UiWidget}. Below this
 * class there are two inheritance hierarchies {@link AbstractUiWidgetReal} and
 * {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom}. To avoid problems with the lack of
 * multi-inheritance in Java, we already implement {@link UiWidgetWithValue} by extending
 * {@link AbstractUiFeatureValue}. For subclasses that have no value {@link Void} is used for
 * {@literal <VALUE>}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidget<VALUE> extends AbstractUiFeatureValue<VALUE> implements UiWidgetWithValue<VALUE> {

  /** @see #getContext() */
  private final AbstractUiContext context;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidget(UiContext context) {

    super();
    this.context = (AbstractUiContext) context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final AbstractUiContext getContext() {

    return this.context;
  }

  /**
   * @return <code>true</code> if the {@link UiWidgetAdapter} has already been {@link #getWidgetAdapter()
   *         created}. Otherwise <code>false</code> (if {@link #getWidgetAdapter()} has never been called
   *         yet).
   */
  public abstract boolean hasWidgetAdapter();

  /**
   * This method gets or creates the {@link UiWidgetAdapter}.<br/>
   * <b>ATTENTION:</b><br/>
   * On the first call of this method, the {@link UiWidgetAdapter} is created. For the purpose of lazy
   * instantiation this should happen as late as possible. Use {@link #hasWidgetAdapter()} to prevent
   * unnecessary creation.
   * 
   * @return the {@link UiWidgetAdapter}.
   */
  protected abstract UiWidgetAdapter getWidgetAdapter();

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final UiWidgetAdapter getWidgetAdapter(UiWidget widget) {

    return ((AbstractUiWidget<?>) widget).getWidgetAdapter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeEventSender<VALUE> createChangeEventSender() {

    ChangeEventSender<VALUE> changeEventSender = new ChangeEventSender<VALUE>(this, getContext());
    return changeEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSource() {

    return getId();
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  protected abstract void removeFromParent();

  /**
   * This method invokes {@link #removeFromParent()} on the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users.
   * 
   * @param widget is the widget that should be removed from its {@link #getParent() parent}.
   */
  public static void removeFromParent(UiWidget widget) {

    ((AbstractUiWidget<?>) widget).removeFromParent();
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected abstract void setParent(UiWidgetComposite<?> parent);

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  public static void setParent(UiWidget widget, UiWidgetComposite<?> newParent) {

    ((AbstractUiWidget<?>) widget).setParent(newParent);
  }

}
