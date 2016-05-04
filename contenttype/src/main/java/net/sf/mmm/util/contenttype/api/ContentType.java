/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.api;

import java.util.Collection;

import net.sf.mmm.util.collection.api.TreeNode;

/**
 * A {@link ContentType} represents the type of some content (typically a file or BLOB). A {@link ContentType} is
 * similar to a mimetype but more fine-grained (there can be multiple {@link ContentType}s for a single mimetype) and
 * well structured in a {@link TreeNode hierarchy-tree}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentType extends TreeNode<ContentType> {

  /**
   * This method gets the technical name of this {@link ContentType}. For displaying the {@link ContentType} (to the
   * end-user) you should use the {@link #getTitle() title} instead. However the {@link #getTitle() title} of a
   * {@link ContentType} may change over the time while the {@link #getId() ID} will NOT. Therefore you may use this
   * method for internal decisions.
   *
   * @return the ID of this {@link ContentType} (e.g. "PNG").
   */
  String getId();

  /**
   * This method gets the display name of this {@link ContentType} (e.g. "Portable-Network-Graphics")
   *
   * @return the common type-name.
   */
  String getTitle();

  /**
   * This method gets the default extension associated with this {@link ContentType} (e.g. ".jpg").
   *
   * @return the default file-extension or {@code null} if not defined.
   */
  String getDefaultExtension();

  /**
   * This method gets the list of all extensions associated with this {@link ContentType} including the
   * {@link #getDefaultExtension() default extension} (e.g. contains ".jpg" and ".jpeg").
   *
   * @return a {@link Collection} of all additional file-extensions.
   */
  Collection<String> getExtensions();

  /**
   * This method gets the mimetype of this {@link ContentType} (e.g. "image/png").
   *
   * @return the mimetype string.
   */
  String getMimetype();

  /**
   * This method determines if the current {@link ContentType} is abstract. If concrete data has an exact
   * {@link ContentType} that {@link ContentType} should NOT be abstract. <br>
   * An abstract {@link ContentType} should have {@link #getChildren() sub-types}, a non abstract {@link ContentType}
   * can have sub-types.
   *
   * @return {@code true} if the {@link ContentType} is abstract, {@code false} otherwise.
   */
  boolean isAbstract();

  /**
   * This method gets the technical parent of this {@link ContentType} as {@link TreeNode node}. <br>
   * While the {@link ContentType} as {@link TreeNode node} represents the logical hierarchy of {@link ContentType}s,
   * the {@link #getTechnicalParent() technical parent} gives the technical view on the hierarchy. <br>
   * There is no {@code getTechnicalChildren()} method. The children are always retrieved via {@link #getChildren()}.
   * Therefore a {@link ContentType} can have {@link #getChildren() children} that have it as
   * {@link #getTechnicalParent() technical parent} while {@link #getParent()} returns another {@link ContentType}. <br>
   * For instance the {@link ContentType#getParent() logical parent} of the type {@code ODT} (Open Document Text) is the
   * type office document. From a technical point of view an ODT-file is a ZIP-file with a specific structure. Therefore
   * the {@link #getTechnicalParent() technical parent} is the type {@code ZIP} . <br>
   * Typically end-users will NOT be interested in the technical view. However it is exposed for those that may find it
   * useful.
   *
   * @return the technical {@link #getParent() parent} of this {@link ContentType} or {@code null} if this is the
   *         {@link ContentTypeManager#getTechnicalRootType() technical root node}.
   */
  ContentType getTechnicalParent();

  /**
   * This method determines if this {@link ContentType} is an ancestor of the given {@code technicalType}. In other
   * words this method checks if this {@link ContentType} is the direct or indirect {@link #getTechnicalParent()
   * technical parent} of the given {@code technicalType}.
   *
   * @see TreeNode#isAncestor(net.sf.mmm.util.collection.api.GenericTreeNode)
   *
   * @param technicalType is the {@link ContentType} to check.
   * @return {@code true} if this {@link ContentType} is a technical ancestor of the given {@code technicalType}.
   */
  boolean isTechnicalAncestor(ContentType technicalType);

}
