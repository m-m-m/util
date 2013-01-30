/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetSplitPanel;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * While {@link UiWidgetFactory} allows to create any {@link UiWidget} via a generic and stable API this
 * interface defines a higher-level factory that offers more comfort. So {@link UiWidgetFactory} is the
 * low-level factory to abstract from the underlying implementation this factory is technology independent and
 * offers more comfort to the end-user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetFactoryAdvanced {

  /**
   * This method creates a new {@link UiWidgetButton}.
   * 
   * @param label is the {@link UiWidgetButton#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetButton createButton(String label);

  /**
   * This method creates a new {@link UiWidgetLabel}.
   * 
   * @param label is the {@link UiWidgetLabel#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLabel createLabel(String label);

  /**
   * This method creates a new {@link UiWidgetTextField}.
   * 
   * @param label is the {@link UiWidgetTextField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetTextField createTextField(String label);

  /**
   * This method creates a new {@link UiWidgetIntegerField}.
   * 
   * @param label is the {@link UiWidgetIntegerField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetIntegerField createIntegerField(String label);

  /**
   * This method creates a new {@link UiWidgetLongField}.
   * 
   * @param label is the {@link UiWidgetLongField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLongField createLongField(String label);

  /**
   * This method creates a new {@link UiWidgetComboBox}.
   * 
   * @param <VALUE> is the generic type of the available selection options.
   * 
   * @param label is the {@link UiWidgetLongField#getFieldLabel() label}.
   * @param enumDefinition is the {@link EnumDefinition} identifying the available
   *        {@link UiWidgetComboBox#getOptions() options}. These may be loaded asynchronous via
   *        {@link net.sf.mmm.util.lang.api.EnumProvider}.
   * @return the new widget instance.
   */
  <VALUE> UiWidgetComboBox<VALUE> createComboBox(String label, EnumDefinition<VALUE, ?> enumDefinition);

  /**
   * This method creates a new {@link UiWidgetSplitPanel}.
   * 
   * @param orientation - {@link Orientation#HORIZONTAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel} or
   *        {@link Orientation#VERTICAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel}.
   * @param children are the {@link UiWidgetSplitPanel#getChild(String) children}. You need to provide at
   *        least two children.
   * @return the new {@link UiWidgetSplitPanel}.
   */
  UiWidgetSplitPanel createSplitPanel(Orientation orientation, UiWidgetRegular... children);

}
