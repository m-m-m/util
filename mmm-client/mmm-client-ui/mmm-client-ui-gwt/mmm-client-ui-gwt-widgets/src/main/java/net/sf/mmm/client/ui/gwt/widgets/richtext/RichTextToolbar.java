/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.client.ui.gwt.widgets.BorderPanel;
import net.sf.mmm.client.ui.gwt.widgets.ButtonGroup;
import net.sf.mmm.client.ui.gwt.widgets.ButtonWidget;
import net.sf.mmm.client.ui.gwt.widgets.PopupWindow;
import net.sf.mmm.client.ui.gwt.widgets.Toolbar;
import net.sf.mmm.client.ui.gwt.widgets.VerticalFlowPanel;
import net.sf.mmm.util.gwt.api.JsSelection;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.nls.api.NlsAccess;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.RichTextArea.Justification;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is a {@link com.google.gwt.user.client.ui.Widget} that represents the toolbar for a
 * {@link RichTextArea}. The toolbar allows to format the selected text or insert images, hyperlinks, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RichTextToolbar extends Toolbar {

  /** The available font sizes. */
  private static final FontSize[] FONT_SIZES = new FontSize[] { FontSize.XX_SMALL, FontSize.X_SMALL, FontSize.SMALL,
      FontSize.MEDIUM, FontSize.LARGE, FontSize.X_LARGE, FontSize.XX_LARGE };

  /** The associated {@link RichTextArea} to modify via this toolbar. */
  private final RichTextArea richTextArea;

  /** The {@link Formatter} for the {@link RichTextArea}. */
  private final Formatter formatter;

  /** @see #getBehavior(RichTextFeature) */
  private final Map<RichTextFeature, FeatureBehavior> behaviorMap;

  // private final Map<RichTextFeature, ButtonBase> buttonMap;

  /** The {@link NlsBundleClientUiRoot} for NLS. */
  private final NlsBundleClientUiRoot bundle;

  /** @see #getFontSettingsPopup() */
  private FontSettingsPopup fontSettingsPopup;

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

    this.behaviorMap = new HashMap<RichTextFeature, FeatureBehavior>();
    addFeatureBehaviors();
    createButtonGroup(RichTextFeature.BOLD, RichTextFeature.ITALIC, RichTextFeature.UNDERLINE,
        RichTextFeature.STRIKETHROUGH, RichTextFeature.SUBSCRIPT, RichTextFeature.SUPERSCRIPT,
        RichTextFeature.REMOVE_FORMAT);
    createButtonGroup(RichTextFeature.ALIGN_LEFT, RichTextFeature.ALIGN_CENTER, RichTextFeature.ALIGN_RIGHT);
    createButtonGroup(RichTextFeature.UNORDERED_LIST, RichTextFeature.ORDERED_LIST, RichTextFeature.INDENT,
        RichTextFeature.OUTDENT, RichTextFeature.HORIZONTAL_LINE);
    createButtonGroup(RichTextFeature.FONT_FAMILY, RichTextFeature.FONT_SIZE, RichTextFeature.FONT_COLOR,
        RichTextFeature.BACKGROUND_COLOR);
    createButtonGroup(RichTextFeature.INSERT_IMAGE, RichTextFeature.INSERT_LINK);
    createButtonGroup(RichTextFeature.UNDO, RichTextFeature.REDO);
    UpdateHandler handler = new UpdateHandler();
    richTextArea.addKeyUpHandler(handler);
    richTextArea.addClickHandler(handler);
  }

  /**
   * {@link #addBehavior(FeatureBehavior) Adds} all available {@link FeatureBehavior}s at construction time.
   */
  protected void addFeatureBehaviors() {

    addBehavior(new FeatureBehaviorBold(this));
    addBehavior(new FeatureBehaviorItalic(this));
    addBehavior(new FeatureBehaviorUnderline(this));
    addBehavior(new FeatureBehaviorStrikethrough(this));
    addBehavior(new FeatureBehaviorSubscript(this));
    addBehavior(new FeatureBehaviorSuperscript(this));
    addBehavior(new FeatureBehaviorRemoveFormat(this));
    addBehavior(new FeatureBehaviorAlignLeft(this));
    addBehavior(new FeatureBehaviorAlignCenter(this));
    addBehavior(new FeatureBehaviorAlignRight(this));
    addBehavior(new FeatureBehaviorIndent(this));
    addBehavior(new FeatureBehaviorOutdent(this));
    addBehavior(new FeatureBehaviorHorizontalLine(this));
    addBehavior(new FeatureBehaviorOrderedList(this));
    addBehavior(new FeatureBehaviorUnorderedList(this));
    addBehavior(new FeatureBehaviorFontFamily(this));
    addBehavior(new FeatureBehaviorFontSize(this));
    addBehavior(new FeatureBehaviorFontColor(this));
    addBehavior(new FeatureBehaviorBackgroundColor(this));
    addBehavior(new FeatureBehaviorInsertLink(this));
    addBehavior(new FeatureBehaviorInsertImage(this));
    addBehavior(new FeatureBehaviorUndo(this));
    addBehavior(new FeatureBehaviorRedo(this));
  }

  /**
   * @param behavior the {@link FeatureBehavior} to register in the internal {@link Map}.
   */
  private void addBehavior(FeatureBehavior behavior) {

    FeatureBehavior old = this.behaviorMap.put(behavior.getFeature(), behavior);
    assert (old == null);
  }

  /**
   * @param feature is the {@link RichTextFeature}.
   * @return the {@link FeatureBehavior} for the given {@link RichTextFeature}. Invoking
   *         {@link FeatureBehavior#getFeature() getFeature()} on the result has to return the given
   *         {@link RichTextFeature}.
   */
  private FeatureBehavior getBehavior(RichTextFeature feature) {

    FeatureBehavior featureBehavior = this.behaviorMap.get(feature);
    assert (featureBehavior != null);
    return featureBehavior;
  }

  /**
   * @return the {@link Formatter} instance of the {@link RichTextArea}.
   */
  Formatter getFormatter() {

    return this.formatter;
  }

  /**
   * @return the {@link FontSettingsPopup}. Lazily created on the first call.
   */
  private FontSettingsPopup getFontSettingsPopup() {

    if (this.fontSettingsPopup == null) {
      this.fontSettingsPopup = new FontSettingsPopup();
    }
    return this.fontSettingsPopup;
  }

  /**
   * Opens a popup with the font related {@link RichTextFeature feature settings}.
   */
  void openFontSettingsPopup() {

    getFontSettingsPopup().center();
    updateFontSettings();
    getFontSettingsPopup().setVisible(true);
  }

  /**
   * Creates a new {@link ButtonGroup} with buttons for the given {@link RichTextFeature}s.
   * 
   * @param features are the {@link RichTextFeature}s to create buttons for.
   */
  private void createButtonGroup(RichTextFeature... features) {

    startGroup();
    for (RichTextFeature feature : features) {
      FeatureBehavior behavior = getBehavior(feature);
      add(behavior.getToolbarWidget());
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
          // this.linkMode = true;
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
        JsSelection selection = JavaScriptUtil.getInstance().getSelection(
            RichTextToolbar.this.richTextArea.getElement());
        Window.alert(selection.getText() + "\n" + selection.getHtml());
        // RichTextToolbar.this.formatter.setFontSize(fontSize);
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
      default :
        break;
    }
  }

  //
  // /**
  // * Creates a button for the given {@link RichTextFeature}.
  // *
  // * @param feature is the {@link RichTextFeature}.
  // * @return the according button.
  // */
  // private ButtonBase createButton(final RichTextFeature feature) {
  //
  // ButtonBase button;
  // String tooltip = feature.toNlsMessage().getLocalizedMessage();
  // SafeHtml html = HtmlTemplates.INSTANCE.iconMarkup(feature.getIcon());
  // if (isToggleFeature(feature)) {
  // button = createToggleButton(html, tooltip);
  // } else {
  // button = createPushButton(html, tooltip);
  // }
  // ClickHandler clickHandler = new ClickHandler() {
  //
  // @Override
  // public void onClick(ClickEvent event) {
  //
  // invokeFeature(feature);
  // }
  //
  // };
  // button.addClickHandler(clickHandler);
  // return button;
  // }

  /**
   * Sets the given {@link RichTextFeature} to the given availability.
   * 
   * @param feature is the {@link RichTextFeature}.
   * @param available - <code>true</code> if the given <code>feature</code> should be available (button
   *        visible), <code>false</code> otherwise.
   */
  public void setFeatureAvailable(RichTextFeature feature, boolean available) {

    getBehavior(feature).setVisible(available);
  }

  /**
   * Updates the status of the toolbar (with all the toggle buttons).
   */
  private void updateToolbar() {

    for (FeatureBehavior behavior : this.behaviorMap.values()) {
      behavior.updateToolbar();
    }
  }

  /**
   * Updates the status of the {@link FontSettingsPopup}.
   */
  private void updateFontSettings() {

    for (FeatureBehavior behavior : this.behaviorMap.values()) {
      behavior.updateFontSettings();
    }
  }

  /**
   * @param enabled - <code>true</code> to enable, <code>false</code> to disable.
   */
  public void setEnabled(boolean enabled) {

    for (FeatureBehavior behavior : this.behaviorMap.values()) {
      behavior.setEnabled(enabled);
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

      updateToolbar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyUp(KeyUpEvent event) {

      updateToolbar();
    }
  }

  /**
   * The {@link PopupWindow} for the font settings.
   */
  class FontSettingsPopup extends PopupWindow {

    private FontSize fontSize;

    private final Grid gridLayout;

    private final VerticalFlowPanel contentPanel;

    private final BorderPanel previewPanel;

    private final FlowPanel previewArea;

    private final InlineLabel previewText;

    /**
     * The constructor.
     */
    public FontSettingsPopup() {

      super(false, true);
      addStyleName(CssStyles.POPUP);
      String title = RichTextToolbar.this.bundle.titleFontSettings().getLocalizedMessage();
      setTitleText(title);

      this.contentPanel = new VerticalFlowPanel();
      this.gridLayout = new Grid(4, 2);
      String labelPreviewText = RichTextToolbar.this.bundle.labelFontSettingsPreviewText().getLocalizedMessage();
      this.previewText = new InlineLabel(labelPreviewText);
      this.previewArea = new FlowPanel();
      this.previewArea.add(this.previewText);

      int rowIndex = 0;
      addSelectionFeature(RichTextFeature.FONT_FAMILY, rowIndex++);
      addSelectionFeature(RichTextFeature.FONT_SIZE, rowIndex++);
      addSelectionFeature(RichTextFeature.FONT_COLOR, rowIndex++);
      addSelectionFeature(RichTextFeature.BACKGROUND_COLOR, rowIndex++);
      BorderPanel fontPanel = new BorderPanel();
      fontPanel.setLabel("Font");
      fontPanel.add(this.gridLayout);
      this.contentPanel.add(fontPanel);

      BorderPanel effectsPanel = new BorderPanel();
      effectsPanel.setLabel("Effects");
      FlexTable effectsGrid = new FlexTable();
      effectsPanel.add(effectsGrid);
      int row = 0;
      int column = 0;
      int columnCount = 2;
      for (FeatureBehavior behavior : RichTextToolbar.this.behaviorMap.values()) {
        if (behavior instanceof AbstractToggleFeatureBehavior) {
          Widget fontSettingsWidget = behavior.getFontSettingsWidget();
          effectsGrid.setWidget(row, column, fontSettingsWidget);
          column++;
          if (column >= columnCount) {
            column = 0;
            row++;
          }
          Element element;
          RichTextFeature feature = behavior.getFeature();
          if ((feature == RichTextFeature.STRIKETHROUGH) || (feature == RichTextFeature.SUPERSCRIPT)) {
            element = this.previewArea.getElement();
          } else {
            element = this.previewText.getElement();
          }
          behavior.setFontSettingsPreviewElement(element);
        }
      }
      this.contentPanel.add(effectsPanel);
      String labelPreview = RichTextToolbar.this.bundle.labelPreview().getLocalizedMessage();
      this.previewPanel = new BorderPanel();
      this.previewPanel.setLabel(labelPreview);
      this.previewPanel.add(this.previewArea);
      this.contentPanel.add(this.previewPanel);

      ButtonWidget applyButton = new ButtonWidget(RichTextToolbar.this.bundle.labelApply().getLocalizedMessage());
      applyButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          for (FeatureBehavior behavior : RichTextToolbar.this.behaviorMap.values()) {
            behavior.applyFontSettings();
          }
          hide();
        }
      });
      getButtonPanel().add(applyButton);
      ButtonWidget cancelButton = new ButtonWidget(RichTextToolbar.this.bundle.labelCancel().getLocalizedMessage());
      cancelButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          hide();
        }
      });
      getButtonPanel().add(cancelButton);
      add(this.contentPanel);
    }

    private void addSelectionFeature(RichTextFeature feature, int rowIndex) {

      FeatureBehavior behavior = getBehavior(feature);
      this.gridLayout.setWidget(rowIndex, 0, behavior.getFontSettingsLabel());
      this.gridLayout.setWidget(rowIndex, 1, behavior.getFontSettingsWidget());
      Element element = this.previewText.getElement();
      behavior.setFontSettingsPreviewElement(element);
    }
  }
}
