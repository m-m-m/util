/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.text.ParseException;
import java.time.LocalDate;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Parser;

/**
 * A {@link LocalDateBox} is an input widget for {@link LocalDate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LocalDateBox extends InputBox<LocalDate> {

  /**
   * The constructor.
   */
  public LocalDateBox() {

    super(JavaScriptUtil.getInstance().createInputElement(HtmlConstants.INPUT_TYPE_DATE), LocalDateFormat.INSTANCE,
        LocalDateFormat.INSTANCE);
  }

  /**
   * {@link com.google.gwt.text.shared.Renderer} and {@link Parser} for {@link LocalDate}.
   */
  private static final class LocalDateFormat extends AbstractRenderer<LocalDate> implements Parser<LocalDate> {

    /** The singleton instance. */
    private static final LocalDateFormat INSTANCE = new LocalDateFormat();

    /**
     * The constructor.
     */
    private LocalDateFormat() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render(LocalDate date) {

      if (date == null) {
        return "";
      }
      return date.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate parse(CharSequence text) throws ParseException {

      if (text == null) {
        return null;
      }
      String dateString = text.toString();
      if (dateString.length() == 0) {
        return null;
      }
      return LocalDate.parse(dateString);
    }

  }

}
