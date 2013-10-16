/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTreeModelDummy implements UiTreeModel<String> {

  /**
   * The constructor.
   * 
   */
  public UiTreeModelDummy() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRootNode() {

    return "Root";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getChildren(String node) {

    List<String> children = new ArrayList<String>();
    for (int i = 0; i < 5; i++) {
      children.add(node + "." + i);
    }
    return children;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getChildrenAsync(String node, Consumer<List<String>> callback) {

    callback.accept(getChildren(node));
  }

}
