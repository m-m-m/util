/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetAbstractButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetToggleButton;
import net.sf.mmm.client.ui.api.widget.field.RichTextFeature;
import net.sf.mmm.util.gwt.api.JavaScriptSelection;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.nls.api.NlsAccess;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.RichTextArea.Justification;

/**
 * This class is a {@link com.google.gwt.user.client.ui.Widget} that represents the toolbar for a {@link RichTextArea}.
 * The toolbar allows to format the selected text or insert images, hyperlinks, etc.
 */
public class RichTextToolbar extends Toolbar {

  /** The available font sizes. */
  private static final FontSize[] FONT_SIZES = new FontSize[] { FontSize.XX_SMALL, FontSize.X_SMALL,
  FontSize.SMALL, FontSize.MEDIUM, FontSize.LARGE, FontSize.X_LARGE, FontSize.XX_LARGE };

  /** The associated {@link RichTextArea} to modify via this toolbar. */
  private final RichTextArea richTextArea;

  /** The {@link Formatter} for the {@link RichTextArea}. */
  private final Formatter formatter;

  /** @see #createButton(RichTextFeature) */
  private final Map<RichTextFeature, ButtonBase> buttonMap;

  /** The {@link NlsBundleClientUiRoot} for NLS. */
  private final NlsBundleClientUiRoot bundle;

  /** <code>true</code> if {@link RichTextFeature#INSERT_LINK} is currently active. */
  private boolean linkMode;

  /**
   * Creates a new toolbar that drives the given rich text area.
   * 
   * @param richTextArea the rich text area to be controlled
   */
  public RichTextToolbar(RichTextArea richTextArea) {

    super();
    this.richTextArea = richTextArea;
    this.formatter = this.richTextArea.getFormatter();
    this.bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
    setWidth("100%");
    addStyleName("RichTextToolbar");

    this.buttonMap = new HashMap<RichTextFeature, ButtonBase>();

    createButtonGroup(RichTextFeature.BOLD, RichTextFeature.ITALIC, RichTextFeature.UNDERLINE,
        RichTextFeature.STRIKETHROUGH, RichTextFeature.SUBSCRIPT, RichTextFeature.SUPERSCRIPT,
        RichTextFeature.REMOVE_FORMAT);
    createButtonGroup(RichTextFeature.ALIGN_LEFT, RichTextFeature.ALIGN_CENTER, RichTextFeature.ALIGN_RIGHT);
    createButtonGroup(RichTextFeature.UNORDERED_LIST, RichTextFeature.ORDERED_LIST, RichTextFeature.INDENT,
        RichTextFeature.OUTDENT, RichTextFeature.HORIZONTAL_LINE);
    createButtonGroup(RichTextFeature.FONT_FAMILY, RichTextFeature.FONT_SIZE, RichTextFeature.TEXT_COLOR,
        RichTextFeature.BACKGROUND_COLOR);
    createButtonGroup(RichTextFeature.INSERT_IMAGE, RichTextFeature.INSERT_LINK);
    createButtonGroup(RichTextFeature.UNDO, RichTextFeature.REDO);
    UpdateHandler handler = new UpdateHandler();
    richTextArea.addKeyUpHandler(handler);
    richTextArea.addClickHandler(handler);
  }

  /**
   * Creates a new {@link ButtonGroup} with buttons for the given {@link RichTextFeature}s.
   * 
   * @param features are the {@link RichTextFeature}s to create buttons for.
   */
  private void createButtonGroup(RichTextFeature... features) {

    startGroup();
    for (RichTextFeature feature : features) {
      ButtonBase button = createButton(feature);
      add(button);
      this.buttonMap.put(feature, button);
    }
    endGroup();
  }

  /**
   * @param feature is the {@link RichTextFeature} to invoke (e.g. if according button has been clicked).
   */
  protected void invokeFeature(RichTextFeature feature) {

    switch (feature) {
      case BOLD:
        RichTextToolbar.this.formatter.toggleBold();
        break;
      case ITALIC:
        RichTextToolbar.this.formatter.toggleItalic();
        break;
      case UNDERLINE:
        RichTextToolbar.this.formatter.toggleUnderline();
        break;
      case SUBSCRIPT:
        RichTextToolbar.this.formatter.toggleSubscript();
        break;
      case SUPERSCRIPT:
        RichTextToolbar.this.formatter.toggleSuperscript();
        break;
      case STRIKETHROUGH:
        RichTextToolbar.this.formatter.toggleStrikethrough();
        break;
      case ALIGN_LEFT:
        RichTextToolbar.this.formatter.setJustification(Justification.LEFT);
        break;
      case ALIGN_CENTER:
        RichTextToolbar.this.formatter.setJustification(Justification.CENTER);
        break;
      case ALIGN_RIGHT:
        RichTextToolbar.this.formatter.setJustification(Justification.RIGHT);
        break;
      case UNORDERED_LIST:
        RichTextToolbar.this.formatter.insertUnorderedList();
        break;
      case ORDERED_LIST:
        RichTextToolbar.this.formatter.insertOrderedList();
        break;
      case HORIZONTAL_LINE:
        RichTextToolbar.this.formatter.insertHorizontalRule();
        break;
      case INSERT_IMAGE:
        String url = Window.prompt(this.bundle.labelEnterImageUrl().getLocalizedMessage(), "http://");
        if (url != null) {
          RichTextToolbar.this.formatter.insertImage(url);
        }
        break;
      case INSERT_LINK:
        url = Window.prompt(this.bundle.labelEnterLinkUrl().getLocalizedMessage(), "http://");
        if (url != null) {
          RichTextToolbar.this.formatter.createLink(url);
          this.linkMode = true;
        }
        break;
      case REMOVE_FORMAT:
        this.formatter.removeFormat();
        this.formatter.removeLink();
        break;
      case FONT_FAMILY:
        final DialogBox popup = new DialogBox();
        popup.setStylePrimaryName("Popup");
        popup.setText("Please choose font");
        Grid content = new Grid(3, 2);
        content.setWidget(0, 0, new Label("Font-Family"));
        final ListBox dropdownFontFamily = new ListBox(false);
        for (String font : JavaScriptUtil.getInstance().getAvailableFonts()) {
          dropdownFontFamily.addItem(font);
        }
        content.setWidget(0, 1, dropdownFontFamily);
        content.setWidget(1, 0, new Label("Font-Size"));
        final ListBox dropdownFontSize = new ListBox(false);
        for (FontSize size : FONT_SIZES) {
          dropdownFontSize.addItem(Integer.toString(size.getNumber()));
        }
        content.setWidget(1, 1, dropdownFontSize);
        Button widget = new Button("OK");
        ClickHandler handler = new ClickHandler() {

          @Override
          public void onClick(ClickEvent event) {

            popup.hide();
            String fontFamily = dropdownFontFamily.getValue(dropdownFontFamily.getSelectedIndex());
            RichTextToolbar.this.formatter.setFontName(fontFamily);
            String fontSize = dropdownFontSize.getValue(dropdownFontSize.getSelectedIndex());
            for (FontSize size : FONT_SIZES) {
              if (Integer.toString(size.getNumber()).equals(fontSize)) {
                RichTextToolbar.this.formatter.setFontSize(size);
              }
            }
          }
        };
        widget.addClickHandler(handler);
        content.setWidget(2, 0, widget);
        popup.setGlassEnabled(true);
        popup.setWidget(content);
        popup.center();
        popup.show();
        break;
      case FONT_SIZE:
        JavaScriptSelection selection = JavaScriptUtil.getInstance().getSelection(
            RichTextToolbar.this.richTextArea.getElement());
        Window.alert(selection.getText() + "\n" + selection.getHtml());
        break;
      case INDENT:
        RichTextToolbar.this.formatter.rightIndent();
        break;
      case OUTDENT:
        RichTextToolbar.this.formatter.leftIndent();
        break;
      case UNDO:
        RichTextToolbar.this.formatter.undo();
        break;
      case REDO:
        RichTextToolbar.this.formatter.redo();
        break;
      default:
        break;
    }
  }

  /**
   * Creates a button for the given {@link RichTextFeature}.
   * 
   * @param feature is the {@link RichTextFeature}.
   * @return the according button.
   */
  private ButtonBase createButton(final RichTextFeature feature) {

    ButtonBase button;
    String tooltip = feature.toNlsMessage().getLocalizedMessage();
    SafeHtml html = HtmlTemplates.INSTANCE.iconMarkup(feature.getIcon());
    if (isToggleFeature(feature)) {
      button = createToggleButton(html, tooltip);
    } else {
      button = createPushButton(html, tooltip);
    }
    ClickHandler clickHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        invokeFeature(feature);
      }

    };
    button.addClickHandler(clickHandler);
    return button;
  }

  /**
   * @param feature is the {@link RichTextFeature}.
   * @return <code>true</code> if the {@link RichTextFeature feature} is toggled, <code>false</code> otherwise.
   */
  private boolean isToggleFeature(RichTextFeature feature) {

    switch (feature) {
      case BOLD:
      case ITALIC:
      case UNDERLINE:
      case SUBSCRIPT:
      case SUPERSCRIPT:
      case STRIKETHROUGH:
        return true;
      default:
        return false;
    }
  }

  /**
   * Sets the given {@link RichTextFeature} to the given availability.
   * 
   * @param feature is the {@link RichTextFeature}.
   * @param available - <code>true</code> if the given <code>feature</code> should be available (button visible),
   *        <code>false</code> otherwise.
   */
  public void setFeatureAvailable(RichTextFeature feature, boolean available) {

    ButtonBase button = this.buttonMap.get(feature);
    if (button != null) {
      button.setVisible(available);
    }
  }

  /**
   * Creates a new {@link Button}.
   * 
   * @param html is the markup for the button content.
   * @param tooltip is the tooltip for the button.
   * @return the new {@link SimpleToggleButton}.
   */
  private Button createPushButton(SafeHtml html, String tooltip) {

    Button button = new Button(html);
    button.setStylePrimaryName(UiWidgetAbstractButton.STYLE_PRIMARY);
    button.setTitle(tooltip);
    return button;
  }

  /**
   * Creates a new {@link SimpleToggleButton}.
   * 
   * @param html is the markup for the button content.
   * @param tooltip is the tooltip for the button.
   * @return the new {@link SimpleToggleButton}.
   */
  private SimpleToggleButton createToggleButton(SafeHtml html, String tooltip) {

    SimpleToggleButton tb = new SimpleToggleButton();
    tb.setHTML(html);
    tb.setStylePrimaryName(UiWidgetAbstractButton.STYLE_PRIMARY);
    tb.addStyleName(UiWidgetToggleButton.STYLE_TOGGLE_BUTTON);
    tb.setTitle(tooltip);
    return tb;
  }

  /**
   * Updates the status of a toggle button for the given <code>feature</code>.
   * 
   * @param feature is the {@link RichTextFeature} to update.
   * @param active is the new toggle button status.
   */
  private void setToggleFeatureStatus(RichTextFeature feature, boolean active) {

    assert (isToggleFeature(feature));
    ButtonBase button = this.buttonMap.get(feature);
    if (button instanceof SimpleToggleButton) {
      SimpleToggleButton toggleButton = (SimpleToggleButton) button;
      toggleButton.setValue(Boolean.valueOf(active));
    }
  }

  /**
   * Updates the status of all the toggle buttons.
   */
  private void updateStatus() {

    if (this.formatter != null) {
      setToggleFeatureStatus(RichTextFeature.BOLD, this.formatter.isBold());
      setToggleFeatureStatus(RichTextFeature.ITALIC, this.formatter.isItalic());
      setToggleFeatureStatus(RichTextFeature.UNDERLINE, this.formatter.isUnderlined());
      setToggleFeatureStatus(RichTextFeature.SUBSCRIPT, this.formatter.isSubscript());
      setToggleFeatureStatus(RichTextFeature.SUPERSCRIPT, this.formatter.isSuperscript());
      setToggleFeatureStatus(RichTextFeature.STRIKETHROUGH, this.formatter.isStrikethrough());
      // this.formatter.removeLink();
      if (this.linkMode) {
        // this.formatter.selectNone();;
        this.formatter.removeLink();
        this.linkMode = false;
      }
    }
  }

  /**
   * @param enabled - <code>true</code> to enable, <code>false</code> to disable.
   */
  public void setEnabled(boolean enabled) {

    for (ButtonBase button : this.buttonMap.values()) {
      button.setEnabled(enabled);
    }
  }

  /**
   * This class is the handler to update the toolbar buttons, etc.
   */
  private class UpdateHandler implements ClickHandler, KeyUpHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(ClickEvent event) {

      updateStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyUp(KeyUpEvent event) {

      updateStatus();
    }
  }
}
