/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.text.ParseException;
import java.time.LocalTime;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Parser;

/**
 * A {@link LocalTimeBox} is an input widget for {@link LocalTime}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LocalTimeBox extends InputBox<LocalTime> {

  /**
   * The constructor.
   */
  public LocalTimeBox() {

    super(JavaScriptUtil.getInstance().createInputElement(HtmlConstants.INPUT_TYPE_TIME), LocalTimeFormat.INSTANCE,
        LocalTimeFormat.INSTANCE);
  }

  /**
   * {@link com.google.gwt.text.shared.Renderer} and {@link Parser} for {@link LocalTime}.
   */
  private static final class LocalTimeFormat extends AbstractRenderer<LocalTime> implements Parser<LocalTime> {

    /** The singleton instance. */
    private static final LocalTimeFormat INSTANCE = new LocalTimeFormat();

    /**
     * The constructor.
     */
    private LocalTimeFormat() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render(LocalTime time) {

      if (time == null) {
        return "";
      }
      return time.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime parse(CharSequence text) throws ParseException {

      if (text == null) {
        return null;
      }
      String timeString = text.toString();
      if (timeString.length() == 0) {
        return null;
      }
      return LocalTime.parse(timeString);
    }
  }

}
