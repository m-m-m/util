/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.base.widget.custom.field.UiWidgetCustomFieldAtomic;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * This is an {@link UiWidgetCustomFieldAtomic atomic custom field} for {@link PostalCode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomFieldPostalCode extends UiWidgetCustomFieldAtomic<PostalCode, String, UiWidgetTextField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomFieldPostalCode(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetTextField.class), PostalCode.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PostalCode convertValueFromDelegate(String value) {

    if ((value == null) || (value.isEmpty())) {
      return null;
    }
    return new PostalCode(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String convertValueForDelegate(PostalCode value) {

    if (value == null) {
      return null;
    }
    return value.getTitle();
  }

}
