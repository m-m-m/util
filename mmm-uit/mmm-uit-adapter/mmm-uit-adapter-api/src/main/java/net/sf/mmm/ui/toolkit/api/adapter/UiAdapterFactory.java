/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter;

/**
 * This is the interface for a factory used to #create create {@link UiAdapter}s.
 * 
 * @param <WIDGET> is the generic top-level type of the underlying {@link #getWidget(UiAdapter) widgets}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiAdapterFactory<WIDGET> {

  /**
   * This method creates a new {@link UiAdapter} instance of the given type (<code>adapterInterface</code>).<br/>
   * <b>ATTENTION:</b><br/>
   * The API of the {@link UiAdapter}s defines base interfaces marks as <code>abstract</code>. These
   * interfaces can NOT be specified as argument to this method as they will cause an exception.
   * 
   * @param <ADAPTER> is the generic type of the {@link UiAdapter} to create.
   * @param adapterInterface is the interface reflecting the {@link UiAdapter} to create. Must NOT be
   *        <code>abstract</code>.
   * @return the new {@link UiAdapter}.
   */
  <ADAPTER extends UiAdapter<?>> ADAPTER create(Class<ADAPTER> adapterInterface);

  /**
   * This method gets the underlying native widget of the given {@link UiAdapter}.
   * 
   * @param adapter is the {@link UiAdapter} to unwrap. It has to be an instance {@link #create(Class)
   *        created} by this factory.
   * @return the native widget.
   */
  WIDGET getWidget(UiAdapter<?> adapter);

}
