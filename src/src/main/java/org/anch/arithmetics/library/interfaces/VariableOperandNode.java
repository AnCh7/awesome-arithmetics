package org.anch.arithmetics.library.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.anch.arithmetics.library.nodes.VariableOperandNodeImpl;

/**
 * A variable operand stores an identifier character in the expression.
 * For example: ( 6 / x ) - operands x is variable operand.
 */
@JsonDeserialize(as = VariableOperandNodeImpl.class)
public interface VariableOperandNode extends Node {
    String getOperand();
}
