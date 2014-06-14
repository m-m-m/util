/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.value.api.SimpleGenericValueConverter;
import net.sf.mmm.util.value.base.SimpleGenericValueConverterImpl;

/**
 * This is the interface for an object that identifies a {@link #getDialogId() dialog} in combination with its
 * {@link #getParameters()}. It allows to represent a specific state of the client that can be bookmarked and
 * reopened at a later point in time.<br/>
 * <b>HINT:</b><br/>
 * For each of your {@link Dialog} you need to define a {@link #getDialogId() dialog-id}. To decouple your
 * navigation from the actual dialogs and their implementation, it is recommended to define them in a central
 * interface (you can also have multiple such interfaces or classes for large modular systems). See
 * {@link DialogConstants} to get started. For {@link Dialog}s may have mandatory or optional
 * {@link #getParameter(String, Class) parameters} it is further recommended to create sub-classes for
 * {@link DialogPlace} or to define a <em>factory</em> for your {@link DialogPlace}s that takes the parameter
 * values as properly typed arguments. This will make refactoring easier if parameters need to be changed. If
 * you are sub-classing {@link DialogPlace} please be <b>aware</b> that your
 * {@link net.sf.mmm.client.ui.base.dialog.DialogController}s are NOT permitted to cast {@link DialogPlace} to
 * your sub-class as the {@link DialogManager#navigateTo(DialogPlace) navigation} can also take place via URL
 * typing or from a bookmark and the framework will NOT know your subclasses. For generic access to your
 * parameters please also define <code>PARAMETER_*</code> constants in your {@link DialogConstants}.
 * 
 * <pre>
 * public class MyScreenDialogPlace extends {@link DialogPlace} {
 *
 *   public MyScreenDialogPlace(Type mandatoryParameterFoo) {
 *     this(mandatoryParameterFoo, null);
 *   }
 *
 *   public MyScreenDialogPlace(Type mandatoryParameterFoo, OtherType optionalParameterBar) {
 *     super(MyDialogConstants.DIALOG_ID_MY_SCREEN, {@link #addParameter(Map, String, Object, boolean) addParameter}(
 *      {@link #newParameter(String, Object) newParameter}(MyDialogConstants.PARAMETER_MY_SCREEN_FOO, mandatoryParameterFoo),
 *      MyDialogConstants.PARAMETER_MY_SCREEN_BAR, optionalParameterBar, false));
 *   }
 * }
 * </pre>
 * 
 * If you do not need to {@link DialogManager#openInNewWindow(DialogPlace) open} {@link DialogPlace places} in
 * a new window, you can also combine your places with the {@link DialogManager} and instead of providing
 * create methods you define methods like:
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
  public static final char SEPARATOR_VALUE = '=';

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

  /** @see #getValueConverter() */
  private static SimpleGenericValueConverter valueConverter;

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
  protected DialogPlace(String dialogId, Map<String, String> parameters) {

    super();
    if (!dialogId.matches(PATTERN_DIALOG_ID)) {
      throw new NlsIllegalArgumentException(dialogId, "dialogId");
    }
    this.dialogId = dialogId;
    if (parameters == null) {
      this.parameters = null;
    } else {
      this.parameters = Collections.unmodifiableMap(parameters);
    }
  }

  /**
   * Creates a {@link Map} with the given parameter.
   * 
   * @param key is the {@link #getParameter(String, Class) name of the parameter}.
   * @param value is the {@link #getParameter(String, Class) value of the parameter}.
   * @return a new {@link Map} with the given parameter.
   */
  protected static Map<String, String> newParameter(String key, Object value) {

    return newParameter(key, value, true);
  }

  /**
   * Creates a {@link Map} with the given parameter.
   * 
   * @param key is the {@link #getParameter(String, Class) name of the parameter}.
   * @param value is the {@link #getParameter(String, Class) value of the parameter}. May be <code>null</code>
   *        if <code>required</code> is <code>false</code>. Then an empty {@link Map} is returned. Use the
   *        empty {@link String} instead of <code>null</code> to prevent this.
   * @param required - if <code>false</code> the given <code>value</code> may be <code>null</code>,
   *        <code>true</code> if the parameter is required and an exception shall be thrown if
   *        <code>value</code> is <code>null</code>.
   * @return a new {@link Map} with the given parameter.
   */
  protected static Map<String, String> newParameter(String key, Object value, boolean required) {

    Map<String, String> parameters = new HashMap<String, String>();
    return addParameter(parameters, key, value, required);
  }

  /**
   * Adds the given parameter to the given {@link Map}.
   * 
   * @param parameters is the map with the current parameters.
   * @param key is the {@link #getParameter(String, Class) name of the parameter}.
   * @param value is the {@link #getParameter(String, Class) value of the parameter}.
   * @return the given <code>parameters</code> {@link Map}.
   */
  protected static Map<String, String> addParameter(Map<String, String> parameters, String key, Object value) {

    return addParameter(parameters, key, value, true);
  }

  /**
   * Adds the given parameter to the given {@link Map}.
   * 
   * @param parameters is the map with the current parameters.
   * @param key is the {@link #getParameter(String, Class) name of the parameter}.
   * @param value is the {@link #getParameter(String, Class) value of the parameter}. May be <code>null</code>
   *        for generic usage with optional parameter. Then the given {@link Map} is returned unchanged. Use
   *        the empty {@link String} instead of <code>null</code> to prevent this.
   * @param required - if <code>false</code> the given <code>value</code> may be <code>null</code>,
   *        <code>true</code> if the parameter is required and an exception shall be thrown if
   *        <code>value</code> is <code>null</code>.
   * @return the given <code>parameters</code> {@link Map}.
   */
  protected static Map<String, String> addParameter(Map<String, String> parameters, String key, Object value,
      boolean required) {

    NlsNullPointerException.checkNotNull("key", key);
    if (parameters.containsKey(key)) {
      throw new DuplicateObjectException(value, key, parameters.get(key));
    }
    if (value != null) {
      parameters.put(key, value.toString());
    } else if (required) {
      throw new NlsNullPointerException("value[key=" + key + "]");
    }
    return parameters;
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

    String value = getParameter(key);
    if (value == null) {
      return null;
    }
    if ((type != String.class) && (value.isEmpty())) {
      return null;
    }
    return getValueConverter().convert(value, this.dialogId + "@" + key, type);
  }

  /**
   * @return the {@link SimpleGenericValueConverter} used for conversion in
   *         {@link #getParameter(String, Class)}.
   */
  protected SimpleGenericValueConverter getValueConverter() {

    if (valueConverter == null) {
      SimpleGenericValueConverterImpl impl = new SimpleGenericValueConverterImpl();
      impl.initialize();
      valueConverter = impl;
    }
    return valueConverter;
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
      Map<String, String> parameters = new LinkedHashMap<String, String>();
      int start = stateIndex + 1;
      int length = place.length();
      while (start < length) {
        int end = place.indexOf(SEPARATOR_PARAMETER, start);
        if (end < 0) {
          end = length;
        }

        // key=value
        String key;
        String value;
        int equalsIndex = place.indexOf(SEPARATOR_VALUE, start);
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
      char separator = SEPARATOR_STATE;
      for (Entry<String, String> entry : this.parameters.entrySet()) {
        String key = entry.getKey();
        assert (key.matches(PATTERN_KEY));
        String value = entry.getValue();
        if (value != null) {
          assert (value.matches(PATTERN_VALUE));
          buffer.append(separator);
          separator = SEPARATOR_PARAMETER;
          buffer.append(key);
          buffer.append(SEPARATOR_VALUE);
          buffer.append(value);
        }
      }
      return buffer.toString();
    }
  }

}
