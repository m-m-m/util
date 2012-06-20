/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.ui.toolkit.api.view.UiNode;

/**
 * This is the abstract base implementation of the {@link UiNodeAdapter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiNodeAdapter<DELEGATE> implements UiNodeAdapter<DELEGATE> {

  /** @see #getStyles() */
  private String styles;

  /** @see #getStyles() */
  private Set<String> stylesSet;

  /** @see #getNode() */
  private final UiNode node;

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   */
  public AbstractUiNodeAdapter(UiNode node) {

    super();
    this.node = node;
  }

  /**
   * This method gets the {@link AbstractUiNode node} that owns this adapter.
   * 
   * @return the owing {@link AbstractUiNode node}.
   */
  public UiNode getNode() {

    return this.node;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    // do not care here...
    return false;
  }

  /**
   * <b>ATTENTION:</b><br>
   * This method implementation always returns <code>true</code> by default. You have to override it for
   * implementations that can be made invisible by the user, e.g. for a
   * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWindow} that can be closed.
   * 
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return true;
  }

  /**
   * <b>ATTENTION:</b><br>
   * This default implementation throws a {@link UnsupportedOperationException}. You need to override this in
   * subclasses that support {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled} via the
   * end-user API.
   * 
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    return this.styles;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasStyle(String style) {

    if (this.stylesSet != null) {
      return this.stylesSet.contains(style);
    }
    return (style.equals(this.styles));
  }

  /**
   * This method sets the {@link #getStyles() styles} internally.
   * 
   * @param newStyles are the new styles to set.
   */
  protected void doSetStyles(String newStyles) {

    this.styles = newStyles;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addStyle(String style) {

    assert (style != null);
    assert (!style.isEmpty());
    assert (!style.contains(" "));
    String currentStyles = getStyles();
    if (this.stylesSet == null) {
      if (currentStyles.isEmpty()) {
        doSetStyles(style);
      } else if (!currentStyles.equals(style)) {
        this.stylesSet = new HashSet<String>();
        this.stylesSet.add(currentStyles);
        this.stylesSet.add(style);
        doSetStyles(currentStyles + " " + style);
      }
    } else {
      boolean added = this.stylesSet.add(style);
      if (added) {
        doSetStyles(currentStyles + " " + style);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeStyle(String style) {

    assert (style != null);
    assert (!style.isEmpty());
    assert (!style.contains(" "));
    String currentStyles = getStyles();
    if (this.stylesSet == null) {
      if (currentStyles.equals(style)) {
        doSetStyles("");
      }
    } else {
      boolean removed = this.stylesSet.remove(style);
      if (removed) {
        StringBuilder sb = new StringBuilder(currentStyles.length() - style.length() - 1);
        for (String s : this.stylesSet) {
          if (sb.length() > 0) {
            sb.append(' ');
          }
          sb.append(s);
        }
        doSetStyles(sb.toString());
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    if (this.stylesSet != null) {
      this.stylesSet.clear();
    }
    if ((styles == null) || (styles.length() == 0)) {
      return;
    }
    String[] strings = styles.split(" ");
    if (strings.length == 1) {
      doSetStyles(strings[0]);
    } else if (strings.length > 1) {
      if (this.stylesSet == null) {
        this.stylesSet = new HashSet<String>();
      }
      for (String style : strings) {
        if (!style.isEmpty()) {
          this.stylesSet.add(style);
        }
      }
      StringBuilder sb = new StringBuilder(styles.length());
      for (String style : this.stylesSet) {
        if (sb.length() > 0) {
          sb.append(' ');
        }
        sb.append(style);
      }
      doSetStyles(sb.toString());
    }
  }

  /**
   * This method gets the {@link #getStyles() styles} as {@link Set}.
   * 
   * @return the {@link #getStyles() styles} as {@link Set}.
   */
  protected Set<String> getStylesSet() {

    return this.stylesSet;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    // nothing to do by default...
  }

}
