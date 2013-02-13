/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.value.api.SimpleGenericValueConverter;
import net.sf.mmm.util.value.base.SimpleGenericValueConverterImpl;

/**
 * This is the interface for an object that identifies a {@link #getDialogId() dialog} in combination with its
 * {@link #getParameters()}. It allows to represent a specific state of the client that can be bookmarked and
 * reopened at a later point in time.<br/>
 * 
 * For each of your {@link Dialog} you need to define a {@link #getDialogId() dialog-id}. To decouple your
 * navigation from the actual dialogs and their implementation, it is recommended to define them in a central
 * interface of class (you can also have multiple such classes for large modular systems). As {@link Dialog}s
 * may have mandatory or optional parameters it is further recommended to define a "factory" for your
 * {@link DialogPlace}s that takes the parameter values as properly typed parameters. This will make
 * refactoring easier if parameters need to be changed. Here is a simple example to illustrate the idea:
 * 
 * <pre>
 * public class Places {
 *   public static final String PLACE_HOME = "home";
 *   public static final String PLACE_DOCUMENT = "document";
 *   public static final String PLACE_PROFILE = "profile";
 *   ...
 *
 *   public static final String PARAM_DOCUMENT_ID = "documentId";
 *   ...
 *
 *   public static DialogPlace createHome() {
 *     return new DialogPlace(PLACE_HOME);
 *   }
 *
 *   public static DialogPlace createDocument(long documentId) {
 *     return new DialogPlace(PLACE_DOCUMENT, createParameters(PARAM_DOCUMENT_ID, Long.toString(documentId));
 *   }
 *   ...
 * }
 * </pre>
 * 
 * If you do not need to {@link DialogManager#openWindow(DialogPlace) open} {@link DialogPlace places} in a
 * new window, you can also combine your places with the {@link DialogManager} and instead of providing create
 * methods you define methods like:
 * 
 * <pre>
 *   public void navigateDocument(long documentId) {
 *     DialogPlace place = new DialogPlace(PLACE_DOCUMENT, createParameters(PARAM_DOCUMENT_ID, Long.toString(documentId));
 *     getDialogManager().{@link DialogManager#navigateTo(DialogPlace) navigateTo}(place);
 *   }
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DialogPlace {

  /** The separator for {@link #getDialogId() dialog-id} and its {@link #getParameters() parameters}. */
  public static final char SEPARATOR_STATE = ':';

  /** The separator used between {@link #getParameters() parameters}. */
  public static final char SEPARATOR_PARAMETER = ';';

  /** The separator for key and value of a {@link #getParameter(String) parameter}. */
  public static final char SEPARATOR_VALUE = ';';

  /** The regex pattern used to assert {@link #getParameter(String) parameter keys}. */
  public static final String PATTERN_KEY = "[^;:=&/]+";

  /** The regex pattern used to assert {@link #getParameter(String) parameter values}. */
  public static final String PATTERN_VALUE = "[^;]*";

  /** @see Dialog#PATTERN_DIALOG_ID */
  public static final String PATTERN_DIALOG_ID = AbstractDialog.PATTERN_DIALOG_ID;

  /** @see #getDialogId() */
  private final String dialogId;

  /** @see #getParameters() */
  private Map<String, String> parameters;

  /**
   * The constructor.
   * 
   * @param dialogId - see {@link #getDialogId()}.
   */
  public DialogPlace(String dialogId) {

    this(dialogId, null);
  }

  /**
   * The constructor.
   * 
   * @param dialogId - see {@link #getDialogId()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public DialogPlace(String dialogId, Map<String, String> parameters) {

    super();
    if (!dialogId.matches(PATTERN_DIALOG_ID)) {
      throw new NlsIllegalArgumentException(dialogId, "dialogId");
    }
    this.dialogId = dialogId;
    this.parameters = Collections.unmodifiableMap(parameters);
  }

  /**
   * This method gets the {@link Dialog#getId() ID} of the {@link Dialog} identified by this
   * {@link DialogPlace}.
   * 
   * @return the {@link Dialog#getId() dialog ID}.
   */
  public final String getDialogId() {

    return this.dialogId;
  }

  /**
   * @return the parameterMap
   */
  protected Map<String, String> getParameters() {

    return this.parameters;
  }

  /**
   * This method determines if a {@link #getParameter(String)} is present (!= null).
   * 
   * @param key is the name of the parameter to test.
   * @return <code>true</code> if that parameter is present (NOT <code>null</code>), <code>false</code>
   *         otherwise.
   */
  public boolean hasParameter(String key) {

    // we do not accept null values as present parameters...
    return (getParameter(key) != null);
  }

  /**
   * This method gets a parameter of this {@link DialogPlace}.
   * 
   * @param key is the name of the requested parameter.
   * @return the value of the requested parameter. Will be <code>null</code> if NOT set.
   */
  public String getParameter(String key) {

    String value = null;
    if (this.parameters != null) {
      value = this.parameters.get(key);
    }
    return value;
  }

  /**
   * This method gets a typed parameter of this {@link DialogPlace}.
   * 
   * @param <T> is the generic type of the desired parameter value.
   * 
   * @param key is the name of the requested parameter.
   * @param type is the desired type of the parameter
   * @return the value of the requested parameter converted to the given <code>type</code>. Will be
   *         <code>null</code> if NOT set.
   */
  public <T> T getParameter(String key, Class<T> type) {

    Object value = getParameter(key);
    return getValueConverter().convert(value, this.dialogId + "@" + key, type);
  }

  /**
   * @return the {@link SimpleGenericValueConverter} used for conversion in
   *         {@link #getParameter(String, Class)}.
   */
  protected SimpleGenericValueConverter getValueConverter() {

    // TODO: Use ClientContext to retrieve singleton instance
    return new SimpleGenericValueConverterImpl();
  }

  /**
   * This is the inverse operation of {@link #toString()}.
   * 
   * @param place is the string-representation of the {@link DialogPlace} to create.
   * @return the according {@link DialogPlace}.
   */
  public static DialogPlace fromString(String place) {

    int stateIndex = place.indexOf(SEPARATOR_STATE);
    if (stateIndex < 0) {
      // myDialogId
      return new DialogPlace(place);
    } else {
      // myDialogId:key1=value1;key2=;key3;key4=value4
      // value2 and value3 are both the empty string.
      String dialogId = place.substring(0, stateIndex);
      Map<String, String> parameters = new HashMap<String, String>();
      int start = stateIndex + 1;
      boolean todo = false;
      while (todo) {
        int end = place.indexOf(SEPARATOR_PARAMETER);
        if (end < 0) {
          end = place.length();
        }

        // key=value
        String key;
        String value;
        int equalsIndex = place.indexOf(SEPARATOR_VALUE);
        if ((equalsIndex < 0) || (equalsIndex > end)) {
          key = place.substring(start, end);
          value = "";
        } else {
          key = place.substring(start, equalsIndex);
          value = place.substring(equalsIndex + 1, end);
        }
        parameters.put(key, value);
        start = end + 1;
      }
      return new DialogPlace(dialogId, parameters);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.dialogId == null) ? 0 : this.dialogId.hashCode());
    result = prime * result + ((this.parameters == null) ? 0 : this.parameters.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DialogPlace other = (DialogPlace) obj;
    if (this.dialogId == null) {
      if (other.dialogId != null) {
        return false;
      }
    } else if (!this.dialogId.equals(other.dialogId)) {
      return false;
    }
    if (this.parameters == null) {
      if (other.parameters != null) {
        return false;
      }
    } else if (!this.parameters.equals(other.parameters)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.parameters == null) {
      return this.dialogId;
    } else {
      StringBuilder buffer = new StringBuilder(this.dialogId);
      for (Entry<String, String> entry : this.parameters.entrySet()) {
        String key = entry.getKey();
        assert (key.matches(PATTERN_KEY));
        String value = entry.getValue();
        assert (value.matches(PATTERN_VALUE));
        if (value != null) {
          if (buffer.length() == 0) {
            buffer.append(SEPARATOR_STATE);
          } else {
            buffer.append(SEPARATOR_PARAMETER);
          }
          buffer.append(key);
          buffer.append(SEPARATOR_VALUE);
          buffer.append(value);
        }
      }
      return buffer.toString();
    }
  }

}
