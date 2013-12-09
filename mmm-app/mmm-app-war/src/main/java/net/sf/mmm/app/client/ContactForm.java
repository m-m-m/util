/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetColorField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTimeField;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;
import net.sf.mmm.util.datatype.api.color.Color;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactForm extends UiWidgetCustomGridPanel<ContactBean> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public ContactForm(UiContext context) {

    super(context, ContactBean.class);
    UiDataBinding<ContactBean> dataBinding = getDataBinding();
    getDelegate().setColumnCount(2);
    UiWidgetWithValue<String> createAndBind = dataBinding.createAndBind(Contact.PROPERTY_FIRST_NAME);
    getDelegate().addChildren(createAndBind);
    getDelegate().addChildren(dataBinding.createAndBind(Contact.PROPERTY_LAST_NAME));
    UiWidgetCollapsableSection section = getFactory().create(UiWidgetCollapsableSection.class);
    section.setLabel("Optionals");
    // section = getFactory().createSection("Optionals");
    getDelegate().addChildSpanned(section);

    UiWidgetDateField widgetBirthday = getFactory().create(UiWidgetDateField.class);// dataBinding.createAndBind(Contact.PROPERTY_BIRTHDAY);
    widgetBirthday.setLabel("Birthday");
    getDelegate().addChildren(widgetBirthday);

    UiWidgetWithValue<Integer> widgetShoeSize = dataBinding.createAndBind(Contact.PROPERTY_SHOE_SIZE);
    getDelegate().addChildren(widgetShoeSize);

    UiWidgetWithValue<Double> widgetIncome = dataBinding.createAndBind(Contact.PROPERTY_INCOME);
    getDelegate().addChildren(widgetIncome);

    UiWidgetRichTextField richTextField = context.getWidgetFactory().create(UiWidgetRichTextField.class);
    richTextField.setLabel("RichText");
    getDelegate().addChildren(richTextField);

    UiWidgetRadioButtonsField<SizeUnit> radios = context.getWidgetFactory().create(UiWidgetRadioButtonsField.class);
    radios.setOptions(Arrays.asList(SizeUnit.values()));
    radios.setLabel("Unit");
    getDelegate().addChildren(radios);

    UiWidgetColorField colors = context.getWidgetFactory().create(UiWidgetColorField.class);
    colors.setLabel("Color");
    List<Color> colorOptions = new ArrayList<Color>();
    colorOptions.add(Color.BLACK);
    colorOptions.add(Color.RED);
    colorOptions.add(Color.GREEN);
    colorOptions.add(Color.BLUE);
    colorOptions.add(Color.WHITE);
    colorOptions.add(Color.CYAN);
    colorOptions.add(Color.YELLOW);
    colorOptions.add(Color.MAGENTA);
    colorOptions.add(Color.GREY);
    colors.setOptions(colorOptions);
    getDelegate().addChildren(colors);

    UiWidgetTimeField time = context.getWidgetFactory().create(UiWidgetTimeField.class);
    time.setLabel("Departuretime");
    getDelegate().addChildren(time);

    section.addCollapseWidget(widgetBirthday);
    section.addCollapseWidget(richTextField);
    section.addCollapseWidget(radios);
  }
}
