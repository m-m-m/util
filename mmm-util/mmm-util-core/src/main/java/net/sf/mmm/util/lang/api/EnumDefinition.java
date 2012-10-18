/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for the definition of an {@link EnumTypeWithCategory}. <br/>
 * As an example we can think of an instance of this interface called <em>country</em> defined as
 * {@link EnumDefinition}{@literal <Iso2CountryCode, Void>}. This defines an enumeration based on the type
 * <code>Iso2CountryCode</code> that has no {@link #getCategory() category}. Next, we define another instance
 * of this interface called <code>state</code> defined as {@link EnumDefinition}
 * {@literal <StateDatatype, Iso2CountryCode>}. The method {@link #getCategory()} will return the
 * <em>country</em> instance we defined earlier. This means that a state is classified by a county.<br/>
 * <b>ATTENTION:</b><br/>
 * The {@link #getValue() value} is used as identifier key and has to be unique.
 * 
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category} or {@link Void} for none.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EnumDefinition<TYPE, CATEGORY> extends Datatype<String> {

  /**
   * @return the {@link EnumDefinition} this {@link EnumDefinition} is categorized by or <code>null</code> for
   *         no category (then {@literal <CATEGORY>} shall be bound to {@link Void}).
   */
  EnumDefinition<CATEGORY, ?> getCategory();

  /**
   * @return the {@link EnumType} of this {@link EnumDefinition}.
   */
  Class<TYPE> getEnumType();

  /**
   * @return <code>true</code> if {@link EnumType}-instances of {@link #getEnumType() this type} can be added
   *         or removed, <code>false</code> otherwise (e.g. if implemented by a {@link Enum}).
   */
  boolean isMutable();

  /**
   * @return <code>true</code> if all {@link EnumType}-instances of {@link #getEnumType() this type} can
   *         cached (default) or <code>false</code> if there are too many of them (e.g. for entities like
   *         cities of the world).
   */
  boolean isCachable();

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * The value is used as identifier and has to be unique.
   */
  @Override
  String getValue();

}
