package org.anch.arithmetics.library.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.anch.arithmetics.library.nodes.ConstantOperandNodeImpl;

import java.math.BigDecimal;

/**
 * A constant operand stores a value in the expression.
 * For example: ( 6 / 3 ) - operands 6 and 3 are constant operands.
 */
@JsonDeserialize(as = ConstantOperandNodeImpl.class)
public interface ConstantOperandNode extends Node {
    BigDecimal getOperand();
}
