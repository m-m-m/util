/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import com.sun.javafx.binding.MapExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.property.api.AbstractContainerProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.collection.AbstractMapValidatorBuilder;
import net.sf.mmm.util.validation.base.collection.ValidatorBuilderMap;

/**
 * This is the implementation of {@link WritableListProperty}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@SuppressWarnings("restriction")
public class MapProperty<K, V> extends AbstractContainerProperty<ObservableMap<K, V>> implements WritableMapProperty<K, V> {

  @SuppressWarnings("rawtypes")
  private static final GenericType TYPE = new SimpleGenericTypeImpl<>(ObservableMap.class);

  private final MapChangeListener<K, V> MapChangeListener = change -> {
    invalidateProperties();
    invalidated();
    fireValueChangedEvent(change);
  };

  private MapExpressionHelper<K, V> helper;

  private ObservableMap<K, V> value;

  /**
   * The constructor.
   */
  public MapProperty() {
    super(null, TYPE, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public MapProperty(String name, GenericType<? extends ObservableMap<K, V>> type, Object bean) {
    super(name, requireType(type), bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public MapProperty(String name, GenericType<? extends ObservableMap<K, V>> type, Object bean, AbstractValidator<? super ObservableMap<K, V>> validator) {
    super(name, requireType(type), bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public MapProperty(String name, GenericType<? extends ObservableMap<K, V>> type, Object bean, Supplier<? extends ObservableMap<K, V>> expression) {
    super(name, type, bean, expression);
  }

  private static <K, V> GenericType<? extends ObservableMap<K, V>> requireType(GenericType<? extends ObservableMap<K, V>> type) {

    if (type != null) {
      return type;
    }
    return TYPE;
  }

  @Override
  protected ObservableMap<K, V> doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(ObservableMap<K, V> newValue) {

    if (this.value == newValue) {
      return false;
    }
    if (this.value != null) {
      this.value.removeListener(this.MapChangeListener);
    }
    this.value = newValue;
    if (this.value != null) {
      this.value.addListener(this.MapChangeListener);
    }
    return true;
  }

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener InvalidationListeners},
   * {@link javafx.beans.value.ChangeListener ChangeListeners}, and {@link javafx.collections.MapChangeListener}.
   *
   * This method is called when the content of the list changes.
   *
   * @param change the change that needs to be propagated
   */
  protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {

    MapExpressionHelper.fireValueChangedEvent(this.helper, change);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public AbstractMapValidatorBuilder<K, V, ObservableMap<K, V>, PropertyBuilder<MapProperty<K, V>>, ?> withValdidator() {

    Function factory = new Function<PropertyBuilder<MapProperty<K, V>>, ValidatorBuilderMap<K, V, PropertyBuilder<MapProperty<K, V>>>>() {

      @Override
      public ValidatorBuilderMap<K, V, PropertyBuilder<MapProperty<K, V>>> apply(PropertyBuilder<MapProperty<K, V>> t) {

        return new ValidatorBuilderMap<>(t);
      }
    };
    return (ValidatorBuilderMap) withValdidator(factory);
  }

  @Override
  public void addListener(ChangeListener<? super ObservableMap<K, V>> listener) {

    this.helper = MapExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {

    this.helper = MapExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(InvalidationListener listener) {

    this.helper = MapExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = MapExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(MapChangeListener<? super K, ? super V> listener) {

    this.helper = MapExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(MapChangeListener<? super K, ? super V> listener) {

    this.helper = MapExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  protected void fireValueChangedEvent() {

    MapExpressionHelper.fireValueChangedEvent(this.helper);
  }

  @Override
  protected void toJson(JsonGenerator json, ObservableMap<K, V> mapValue) {

    JsonUtil jsonUtil = getJsonUtil();
    json.writeStartObject(getName());
    for (Entry<K, V> entry : mapValue.entrySet()) {
      Object key = entry.getKey();
      String keyString = "null";
      if (key != null) {
        keyString = key.toString();
      }
      jsonUtil.write(json, keyString, entry.getValue());
    }
    json.writeEnd();
  }

  @Override
  public void fromJson(JsonParser json) {

    JsonUtil jsonUtil = getJsonUtil();
    jsonUtil.expectEvent(json, Event.START_OBJECT);
    ObservableMap<K, V> map = jsonUtil.readMap(json, getType());
    set(map);
  }

}
