package org.anch.arithmetics.library.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.anch.arithmetics.library.nodes.BinaryOperationNodeImpl;
import org.anch.arithmetics.library.operators.BinaryOperator;

/**
 * A binary operator is an operator that operates on two operands and manipulates them to return a result.
 * For example: ( 6 / 3 ) - the division operator "/" operates on operands 6 and 3.
 */
@JsonDeserialize(as = BinaryOperationNodeImpl.class)
public interface BinaryOperationNode extends Node {
    BinaryOperator getOperator();
    Node getLeft();
    Node getRight();
}