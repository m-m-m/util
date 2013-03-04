/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This interface only exists for documentation of the concept of a <em>variant</em>. An operation may have a
 * variant that influences its behavior. This can be anything from a strategy (e.g. "bubble-sort" for a sort
 * operation) to something that causes additional steps to take place (e.g. "keep-backup" for a save
 * operation). In general it is recommended by making this explicit by different signatures with reasonable
 * names. However, sometimes there is need for generic code to call a single operation with different
 * variants. In such case an additional argument can represent which variant of the operation shall be used.
 * This can be done in one of the following ways:
 * <ul>
 * <li>take the variant as {@link Object} for ultimate flexibility and only link to {@link Variant} for
 * documentation.</li>
 * <li>take the variant as {@link Variant} to make it more expressive but also more invasive.</li>
 * <li>take a specific type (e.g. {@link Enum} or {@link net.sf.mmm.util.pojo.api.Pojo}) as input that may
 * implement this interface for documentation purpose (but is not required to do so).</li>
 * </ul>
 * In any case you should avoid casing a given {@link Variant}. Instead, whenever possible use a primitive
 * type for Variant such as an {@link Enum} or {@link String} (from a constant). Then you can do something
 * like <code>if (MyVariantEnum.SPECIAL == variant) { doSomethingSpecial(); } ...</code>.<br/>
 * <b>ATTENTION:</b><br/>
 * If not documented otherwise a {@link Variant} may always be <code>null</code> what is typically the
 * default.
 * 
 * @author hohwille
 * @since 3.0.1
 */
public interface Variant {

  // just a marker interface or for documentation purpose...

}
