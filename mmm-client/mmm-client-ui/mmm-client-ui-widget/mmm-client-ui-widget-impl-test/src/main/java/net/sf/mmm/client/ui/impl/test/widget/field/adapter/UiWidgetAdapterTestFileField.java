/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterFileField;
import net.sf.mmm.util.io.api.FileItem;

/**
 * This is the implementation of {@link UiWidgetAdapterFileField} for testing without native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestFileField extends UiWidgetAdapterTestField<FileItem, FileItem> implements
    UiWidgetAdapterFileField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestFileField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FileItem convertValueFromString(String stringValue) {

    return null;
  }

}
