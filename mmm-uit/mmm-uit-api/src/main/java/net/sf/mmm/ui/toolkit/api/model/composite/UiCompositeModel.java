/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.model.composite;

import java.util.List;

import net.sf.mmm.ui.toolkit.api.model.UiElementModel;

/**
 * This is the model of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiCompositeModel extends UiElementModel {

  /**
   * This method gets the children of this composite model.
   * 
   * @return a list with the children.
   */
  List<UiElementModel> getChildren();

}
