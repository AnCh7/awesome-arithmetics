package org.anch.arithmetics.library.nodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.anch.arithmetics.library.interfaces.ConstantOperandNode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public final class ConstantOperandNodeImpl implements ConstantOperandNode {

    @NotNull(message = "{parameter.null}")
    private final BigDecimal operand;

    @JsonCreator
    public ConstantOperandNodeImpl(@JsonProperty("operand") BigDecimal operand) {
        if (operand == null) throw new IllegalArgumentException("Operand can't be null.");
        this.operand = operand;
    }

    public BigDecimal getOperand() {
        return operand;
    }

    public String getType() {
        return "constant";
    }
}
