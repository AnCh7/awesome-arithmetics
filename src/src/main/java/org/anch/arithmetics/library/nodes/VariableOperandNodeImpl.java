package org.anch.arithmetics.library.nodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.anch.arithmetics.library.interfaces.VariableOperandNode;

import javax.validation.constraints.NotNull;

public final class VariableOperandNodeImpl implements VariableOperandNode {

    @NotNull(message = "{parameter.null}")
    private final String operand;

    @JsonCreator
    public VariableOperandNodeImpl(@JsonProperty("operand") String operand) {
        if (operand == null) throw new IllegalArgumentException("Operand can't be null.");
        this.operand = operand;
    }

    public String getOperand() {
        return operand;
    }

    public String getType() {
        return "variable";
    }
}
