/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.handler.UiHandler;

/**
 * This is the marker interface for a {@link UiHandler} that represents something that may also be called an
 * <em>event listener</em>. It declares action operations like
 * {@link UiHandlerEventClick#onClick(net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick, boolean)},
 * {@link UiHandlerEventValueChange#onValueChange(net.sf.mmm.util.lang.api.attribute.AttributeReadValue, boolean)}
 * .
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiHandlerEvent extends UiHandler {

  // nothing to add

}
