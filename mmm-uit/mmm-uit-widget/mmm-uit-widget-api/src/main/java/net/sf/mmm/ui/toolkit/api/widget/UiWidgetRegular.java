/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

/**
 * This is the interface for a <em>regular</em> {@link UiWidget}. Here regular means that it represents a
 * generic widget that can be placed
 * {@link net.sf.mmm.ui.toolkit.api.widget.composite.UiWidgetCompositeRegular almost anywhere}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetRegular<VALUE> extends UiWidget<VALUE> {

  // just a marker interface...

}
