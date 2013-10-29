/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.text.ParseException;

import net.sf.mmm.client.ui.api.common.Color;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * A {@link ColorBox} is an input widget for {@link Color}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ColorBox extends ValueBoxBase<Color> implements AttributeWriteDataList {

  /**
   * The constructor.
   */
  public ColorBox() {

    super(JavaScriptUtil.getInstance().createInputElement(HtmlConstants.INPUT_TYPE_COLOR), ColorFormat.INSTANCE,
        ColorFormat.INSTANCE);
    setDirectionEstimator(false);
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      setDirection(Direction.LTR);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setDataList(DataList dataList) {

    getElement().setAttribute(HtmlConstants.ATTRIBUTE_LIST, dataList.getId());
    dataList.setOwner(this);
  }

  /**
   * @return the {@link #getElement() element} as {@link InputElement}.
   */
  protected InputElement getInputElement() {

    return getElement().cast();
  }

  /**
   * Sets the maximum allowable length.
   * 
   * @param length the maximum length, in characters
   */
  public void setMaxLength(int length) {

    getInputElement().setMaxLength(length);
  }

  /**
   * {@link com.google.gwt.text.shared.Renderer} and {@link Parser} for {@link Color}.
   */
  private static final class ColorFormat extends AbstractRenderer<Color> implements Parser<Color> {

    /** The singleton instance. */
    private static final ColorFormat INSTANCE = new ColorFormat();

    /**
     * The constructor.
     */
    private ColorFormat() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render(Color color) {

      if (color == null) {
        return "";
      }
      return color.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color parse(CharSequence text) throws ParseException {

      if (text == null) {
        return null;
      }
      String colorString = text.toString();
      if (colorString.length() == 0) {
        return null;
      }
      return new Color(colorString);
    }

  }

}
