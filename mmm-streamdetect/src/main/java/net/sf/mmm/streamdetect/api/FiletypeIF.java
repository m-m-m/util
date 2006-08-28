/* $Id$ */
package net.sf.mmm.streamdetect.api;

import java.util.Iterator;

/**
 * This is the interface for a filetype. Since mimetypes are no precise
 * specification of a filetype this filetype is introduced. In addition to a
 * mimetype it has a hierarchie tree and can be more fine-grained (there can be
 * multiple {@link FiletypeIF}s for a single mimetype).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FiletypeIF {

    /**
     * This method gets the default extension associated with this filetype
     * (e.g. ".jpg").
     * 
     * @return the default file-extension or <code>null</code> if not defined.
     */
    String getDefaultExtension();

    /**
     * This method gets the list of additional extensions associated with this
     * filetype (e.g. contains ".jpeg").
     * 
     * @return an iterator of all additional file-extensions.
     */
    Iterator<String> getAdditionalExtensions();

    /**
     * This method gets the unique common name of this type (e.g. jpeg for
     * image/jpeg, ms-exel for application/vnd.ms-excel, etc.)
     * 
     * @return the common type-name.
     */
    String getName();

    /**
     * This method gets the mimetype of this type (e.g. image/jpeg).
     * 
     * @return the mimetype string.
     */
    String getMimetype();

    /**
     * This method gets the parent type this type is derived from.
     * 
     * @return the parent type or <code>null</code> if this is the root type.
     */
    FiletypeIF getParentType();

    /**
     * This method gets the number of direct sub-types of this file-type.
     * 
     * @return the sub-type count.
     */
    int getSubTypeCount();

    /**
     * This method gets the sub-type at the given index position.
     * 
     * @param index
     *        is the position of the requested sub-type.
     * @return the sub-type at the given index.
     */
    FiletypeIF getSubType(int index);

    /**
     * This method determines if the given filetype is a parent type of this
     * filetype or the same object. <br>
     * For filetypes <code>type1</code> and <code>type2</code> the expression
     * <code>type1.{@link #isParentType(FiletypeIF) isParentType}(type1)</code>
     * will always be <code>true</code> and
     * <code>type1.{@link #isParentType(FiletypeIF) isParentType}(type2)</code>
     * will be <code>true</code> only if ...
     * 
     * @param type
     *        is the filetype to compare.
     * @return <code>true</code> if the given filetype is a parent type of
     *         this filetype or the same object.
     */
    boolean isParentType(FiletypeIF type);

    /**
     * This method determines if the given filetype is a sub type of this
     * filetype or the same object. <br>
     * The expression
     * <code>type1.{@link #isSubType(FiletypeIF) isSubType}(type2)</code> will
     * produce the same result as
     * <code>type2.{@link #isParentType(FiletypeIF) isParentType}(type1)</code>.
     * 
     * @param type
     *        is the filetype to compare.
     * @return <code>true</code> if the given filetype is located in the
     *         subtree represented by the current filetype as node.
     */
    boolean isSubType(FiletypeIF type);

    /**
     * This method determines if the current filetype is abstract. If concrete
     * data has an exact filetype that filetype can not be abstract. <br>
     * An abstract filetype should have sub-types, a non abstract filetype can
     * habe sub-types.
     * 
     * TODO: "abstract=true|false" vs. "type=abstract|fallback/dummy|normal"
     * 
     * @return <code>true</code> if the filetype is abstract,
     *         <code>false</code> otherwise.
     */
    boolean isAbstract();

    /**
     * This method gets alle types that are derived from this type.
     * 
     * @return an iterator of all sub-types.
     */
    Iterator<? extends FiletypeIF> getSubTypes();

}