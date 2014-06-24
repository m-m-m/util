/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.FormatterToString;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for {@link Enum} datatypes.
 *
 * @param <E> is the generic type of the {@link Enum}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeEnum<E extends Enum<E>> extends AbstractUiSingleWidgetFactoryDatatype<E> {

  /** @see #create(UiContext) */
  private final Formatter<E> formatter;

  /**
   * The constructor.
   *
   * @param enumType is the type of the {@link Enum}.
   */
  public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType) {

    this(enumType, FormatterToString.<E> getInstance());
  }

  /**
   * The constructor.
   *
   * @param enumType is the type of the {@link Enum}.
   * @param formatter is the {@link Formatter} used to get the display titles of the {@link Enum} constants.
   */
  public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType, Formatter<E> formatter) {

    super(enumType);
    this.formatter = formatter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<E> create(UiContext context) {

    UiWidgetComboboxField<E> radioButtons = context.getWidgetFactory().create(UiWidgetComboboxField.class);
    radioButtons.setFormatter(this.formatter);
    radioButtons.setOptions(getOptionList());
    return radioButtons;
  }

  /**
   * @return the {@link List} of enum-options. Override to filter options or to add <code>null</code>.
   */
  protected List<E> getOptionList() {

    return Arrays.asList(getDatatype().getEnumConstants());
  }
}
