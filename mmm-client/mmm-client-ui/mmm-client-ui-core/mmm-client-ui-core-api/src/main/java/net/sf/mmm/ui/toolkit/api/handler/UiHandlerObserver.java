/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler;

/**
 * This is the interface for an observer, that gets notified {@link #beforeHandler(Class) before} and
 * {@link #afterHandler(Class) after} one or multiple {@link UiHandler}(s) get invoked. It is therefore no
 * {@link UiHandler} even though this might be indicated by its name.
 * 
 * @see net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerObserver {

  /**
   * This method is called before the {@link UiHandler}(s) are invoked.
   * 
   * @param handlerType is the {@link Class} reflecting the {@link UiHandler}(s) that are observer. This is
   *        useful for {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver objects} that
   *        support multiple types of {@link UiHandler}s.
   */
  void beforeHandler(Class<? extends UiHandler> handlerType);

  /**
   * This method is called after the {@link UiHandler}(s) are invoked.
   * 
   * @param handlerType is the {@link Class} reflecting the {@link UiHandler}(s) that are observer. This is
   *        useful for {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver objects} that
   *        support multiple types of {@link UiHandler}s.
   */
  void afterHandler(Class<? extends UiHandler> handlerType);

}
