/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.popup;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.base.widget.custom.window.UiWidgetCustomPopup;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ExamplePopup extends UiWidgetCustomPopup {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public ExamplePopup(UiContext context) {

    super(context);

    UiWidgetFactory factory = context.getWidgetFactory();
    setTitle("Popup Title");
    // getSize().setMinimumHeight(Length.valueOfPixel(250));
    // getSize().setSize(600, 400, LengthUnit.PIXEL);
    UiWidgetLabel label1 = factory.create(UiWidgetLabel.class);
    label1.setLabel("Hello World!");
    UiWidgetLabel label2 = factory.create(UiWidgetLabel.class);
    label2.setLabel("This is some extra long text to test scrollbar facitlities and resizing. We spent various extra "
        + "effort for these features that are unfortunately not offered by GWT itself out of the box.");
    final UiWidgetCheckboxField checkboxResizsable = factory.create(UiWidgetCheckboxField.class);
    checkboxResizsable.setTitle("resizable");
    checkboxResizsable.setValue(Boolean.TRUE);
    UiHandlerEventValueChange<Boolean> changeHandler = new UiHandlerEventValueChange<Boolean>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

        setResizable(checkboxResizsable.getValue().booleanValue());
      }
    };
    checkboxResizsable.addChangeHandler(changeHandler);
    final UiWidgetCheckboxField checkboxMovable = factory.create(UiWidgetCheckboxField.class);
    checkboxMovable.setTitle("movable");
    checkboxMovable.setValue(Boolean.TRUE);
    UiHandlerEventValueChange<Boolean> changeHandlerMove = new UiHandlerEventValueChange<Boolean>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

        setMovable(checkboxMovable.getValue().booleanValue());
      }
    };
    checkboxMovable.addChangeHandler(changeHandlerMove);
    final UiWidgetCheckboxField checkboxClosable = factory.create(UiWidgetCheckboxField.class);
    checkboxClosable.setTitle("closable");
    checkboxClosable.setValue(Boolean.TRUE);
    UiHandlerEventValueChange<Boolean> changeHandlerClose = new UiHandlerEventValueChange<Boolean>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

        setClosable(checkboxClosable.getValue().booleanValue());
      }
    };
    checkboxClosable.addChangeHandler(changeHandlerClose);
    UiWidgetVerticalPanel verticalPanel = factory.create(UiWidgetVerticalPanel.class);
    verticalPanel.addChild(label1);
    verticalPanel.addChild(label2);
    verticalPanel.addChild(checkboxResizsable);
    verticalPanel.addChild(checkboxMovable);
    verticalPanel.addChild(checkboxClosable);
    addChild(verticalPanel);
    createAndAddCloseButton();
    // getButtonPanel().addChild(button);
    // centerWindow();
  }

}
