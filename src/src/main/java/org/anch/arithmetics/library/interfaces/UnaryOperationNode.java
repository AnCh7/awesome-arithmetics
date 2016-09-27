package org.anch.arithmetics.library.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.anch.arithmetics.library.nodes.UnaryOperationNodeImpl;
import org.anch.arithmetics.library.operators.UnaryOperator;

/**
 * The unary operators operate on a single operand.
 * For example: ( -6 / 3 ) - the negation operator operates on operand 6.
 */
@JsonDeserialize(as = UnaryOperationNodeImpl.class)
public interface UnaryOperationNode extends Node {
    UnaryOperator getOperator();
    Node getOperand();
}
