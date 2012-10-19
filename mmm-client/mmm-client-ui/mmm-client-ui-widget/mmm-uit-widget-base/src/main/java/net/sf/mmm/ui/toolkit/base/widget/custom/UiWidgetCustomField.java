/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.custom;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} that is a {@link UiWidgetField
 * field widget}. The major usecase is to create {@link UiWidgetField field widgets} for custom
 * {@link net.sf.mmm.util.lang.api.Datatype} based on existing {@link UiWidgetField field widgets}.<br/>
 * Instead of extending this class directly you should rather extend one of its sub-classes.
 * 
 * @see UiWidgetFactory#createForDatatype(Class)
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically a custom
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomField<VALUE, DELEGATE extends UiWidgetComposite<?>> extends
    UiWidgetCustom<VALUE, DELEGATE> implements UiWidgetField<VALUE> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomField(UiWidgetFactory<?> factory, DELEGATE delegate) {

    super(factory, delegate);
  }

  /**
   * This method gets the value in case the {@link #getDelegate() delegate widget} returns <code>null</code>
   * as value. The default implementation return <code>null</code>. Override to change.
   * 
   * @return the <code>null</code>-value.
   */
  protected VALUE getNullValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSource() {

    String source = getLabel();
    if (source == null) {
      source = super.getSource();
      // source may still be null but no useful fallback available...
    }
    return source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildIndex(UiWidget child) {

    return getDelegate().getChildIndex(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(int index) throws IndexOutOfBoundsException {

    return getDelegate().getChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(String id) {

    return getDelegate().getChild(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    return getDelegate().getChildCount();
  }

}
