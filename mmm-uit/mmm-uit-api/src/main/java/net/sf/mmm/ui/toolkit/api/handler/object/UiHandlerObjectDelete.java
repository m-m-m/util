/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.object;

/**
 * This is the {@link UiHandlerObject} for the action {@link #onDelete(Object) delete}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface UiHandlerObjectDelete<VALUE> extends UiHandlerObject<VALUE> {

  void onDelete(VALUE value);

}
