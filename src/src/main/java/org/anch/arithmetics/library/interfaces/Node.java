package org.anch.arithmetics.library.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This is a basic node of a binary tree,
 * and hence of a binary expression tree, has zero, one, or two children.
 * Algebraic expression trees represent expressions that contain numbers, variables, and unary and binary operators.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BinaryOperationNode.class, name = "binary"),
        @JsonSubTypes.Type(value = UnaryOperationNode.class, name = "unary"),
        @JsonSubTypes.Type(value = ConstantOperandNode.class, name = "constant"),
        @JsonSubTypes.Type(value = VariableOperandNode.class, name = "variable")})
public interface Node {
    String getType();
}
