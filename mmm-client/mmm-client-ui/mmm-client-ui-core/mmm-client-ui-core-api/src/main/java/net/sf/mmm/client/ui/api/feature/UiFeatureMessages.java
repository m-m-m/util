/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;


/**
 * This is the interface for an object that may contain or display {@link net.sf.mmm.util.lang.api.Message
 * messages}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureMessages extends UiFeature {

  /**
   * This method is clearing all messages. This includes
   * {@link UiFeatureValidation#validate(net.sf.mmm.util.validation.api.ValidationState) validation failures}
   * as well as other {@link net.sf.mmm.util.lang.api.Message messages} such as infos, errors, or warnings
   * (e.g. in a message-panel). This is a recursive operation that is propagated to all potential children.
   */
  void clearMessages();

}
