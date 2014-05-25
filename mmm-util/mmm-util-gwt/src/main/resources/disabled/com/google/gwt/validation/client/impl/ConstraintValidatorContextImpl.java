/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.validation.client.impl;

import com.google.gwt.validation.client.impl.metadata.MessageAndPath;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeContextBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * GWT safe immutable implementation of {@link ConstraintValidatorContext}
 * <p>
 * These objects are very short lived.
 *
 * @param <A> the constraint being validated
 * @param <T> the type of object being validated
 */
public final class ConstraintValidatorContextImpl<A extends Annotation, T>
    implements
    ConstraintValidatorContext {

  /**
   * Builder for {@link ConstraintValidatorContextImpl}.
   */
  public final class ConstraintViolationBuilderImpl implements
      ConstraintViolationBuilder {

    private ConstraintValidatorContextImpl<A, T> context;
    private String messageTemplate;

    /**
     * @param constraintValidatorContextImpl
     * @param messageTemplate
     */
    public ConstraintViolationBuilderImpl(
        ConstraintValidatorContextImpl<A, T> constraintValidatorContextImpl,
        String messageTemplate) {
      this.context = constraintValidatorContextImpl;
      this.messageTemplate = messageTemplate;
    }

    @Override
    public ConstraintValidatorContext addConstraintViolation() {
      messages.add(new MessageAndPath(context.basePath, messageTemplate));
      return context;
    }

    @Override
    public NodeBuilderDefinedContext addNode(String name) {
      return new NodeBuilderDefinedContextImpl(this, messageTemplate,
          basePath.append(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeafNodeBuilderCustomizableContext addBeanNode() {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeBuilderDefinedContext addParameterNode(int index) {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeBuilderCustomizableContext addPropertyNode(String name) {

      // TODO Auto-generated method stub
      return null;
    }

  }

  /**
   * Immutable GWT safe {@link NodeBuilderCustomizableContext}.
   */
  public final class NodeBuilderCustomizableContextImpl implements
      NodeBuilderCustomizableContext {
    private final String messageTemplate;
    private final ConstraintViolationBuilderImpl parent;
    private final PathImpl path;

    public NodeBuilderCustomizableContextImpl(
        ConstraintViolationBuilderImpl parent, String messageTemplate,
        PathImpl path) {
      this.parent = parent;
      this.messageTemplate = messageTemplate;
      this.path = path;
    }

    @Override
    public ConstraintValidatorContext addConstraintViolation() {
      return null;
    }

    @Override
    public NodeBuilderCustomizableContext addNode(String name) {
      return this;
    }

    @Override
    public NodeContextBuilder inIterable() {
      return new NodeContextBuilderImpl(path, messageTemplate, parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeafNodeBuilderCustomizableContext addBeanNode() {

      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeBuilderCustomizableContext addPropertyNode(String name) {

      return this;
    }
  }

  /**
   * Immutable GWT safe {@link NodeBuilderDefinedContext}.
   */
  public final class NodeBuilderDefinedContextImpl implements
      NodeBuilderDefinedContext {

    private final String messageTemplate;
    private final ConstraintViolationBuilderImpl parent;
    private final PathImpl path;

    public NodeBuilderDefinedContextImpl(ConstraintViolationBuilderImpl parent,
        String messageTemplate, PathImpl path) {
      this.parent = parent;
      this.messageTemplate = messageTemplate;
      this.path = path;
    }

    @Override
    public ConstraintValidatorContext addConstraintViolation() {
      messages.add(new MessageAndPath(path, messageTemplate));
      return parent.context;
    }

    @Override
    public NodeBuilderCustomizableContext addNode(String name) {
      return new NodeBuilderCustomizableContextImpl(parent, messageTemplate,
          path.append(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeafNodeBuilderCustomizableContext addBeanNode() {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeBuilderCustomizableContext addPropertyNode(String name) {

      // TODO Auto-generated method stub
      return null;
    }

  }

  /**
   * Immutable GWT safe {@link NodeContextBuilder}.
   */
  public final class NodeContextBuilderImpl implements NodeContextBuilder {

    private final String messageTemplate;

    private final ConstraintViolationBuilderImpl parent;
    private final PathImpl path;

    public NodeContextBuilderImpl(PathImpl path, String messageTemplate,
        ConstraintViolationBuilderImpl parent) {
      super();
      this.path = path;
      this.messageTemplate = messageTemplate;
      this.parent = parent;
    }

    @Override
    public ConstraintValidatorContext addConstraintViolation() {
      return null;
    }

    @Override
    public NodeBuilderCustomizableContext addNode(String name) {
      return new NodeBuilderCustomizableContextImpl(parent, messageTemplate,
          path.append(name));
    }

    @Override
    public NodeBuilderDefinedContext atIndex(Integer index) {
      return new NodeBuilderDefinedContextImpl(parent, messageTemplate,
          path.appendIndex(null, index.intValue()));
    }

    @Override
    public NodeBuilderDefinedContext atKey(Object key) {
      return new NodeBuilderDefinedContextImpl(parent, messageTemplate,
          path.appendKey(null, key));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LeafNodeBuilderCustomizableContext addBeanNode() {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeBuilderCustomizableContext addPropertyNode(String name) {

      // TODO Auto-generated method stub
      return null;
    }
  }

  private final PathImpl basePath;
  private final ConstraintDescriptor<A> descriptor;

  private boolean disableDefault;
  private final Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
  private final HashSet<MessageAndPath> messages = new HashSet<MessageAndPath>();

  public ConstraintValidatorContextImpl(PathImpl path,
      ConstraintDescriptor<A> descriptor) {
    super();
    this.basePath = path;
    this.descriptor = descriptor;
  }

  @Override
  public ConstraintViolationBuilder buildConstraintViolationWithTemplate(
      String messageTemplate) {
    ConstraintViolationBuilderImpl builder = new ConstraintViolationBuilderImpl(
        this, messageTemplate);
    return builder;
  }

  @Override
  public void disableDefaultConstraintViolation() {
    disableDefault = true;
  }

  @Override
  public String getDefaultConstraintMessageTemplate() {
    return (String) descriptor.getAttributes().get("message");
  }

  public Set<MessageAndPath> getMessageAndPaths() {
    if (!disableDefault) {
      messages.add(new MessageAndPath(this.basePath, this
          .getDefaultConstraintMessageTemplate()));
    }
    return messages;
  }

  public Set<ConstraintViolation<T>> getViolations() {
    return violations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T unwrap(Class<T> type) {

    return (T) this;
  }

}
