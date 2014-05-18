/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.widgets;

import java.util.Arrays;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class WidgetsViewWidget extends UiWidgetCustomGridPanel<Void> {

  /**
   * The constructor.
   *
   * @param context - see {@link #getContext()}.
   */
  public WidgetsViewWidget(UiContext context) {

    super(context, Void.class);
    UiWidgetFactory factory = context.getWidgetFactory();
    UiWidgetTextField textField = factory.createTextField("TextField");
    getDelegate().addChildren(textField);
    UiWidgetTextAreaField textArea = factory.createTextAreaField("TextArea");
    getDelegate().addChildren(textArea);
    UiWidgetLongField longField = factory.createLongField("LongField");
    getDelegate().addChildren(longField);
    UiWidgetComboboxField<Fruit> fruitsField = factory.createComboBox("Fruits", Arrays.asList(Fruit.values()));
    getDelegate().addChildren(fruitsField);
  }

}
