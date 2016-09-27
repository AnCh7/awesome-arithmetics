package org.anch.arithmetics.library.nodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.anch.arithmetics.library.interfaces.Node;
import org.anch.arithmetics.library.interfaces.UnaryOperationNode;
import org.anch.arithmetics.library.operators.UnaryOperator;

import javax.validation.constraints.NotNull;

public final class UnaryOperationNodeImpl implements UnaryOperationNode {

    @NotNull(message = "{parameter.null}")
    private final UnaryOperator operator;

    @NotNull(message = "{parameter.null}")
    private final Node operand;

    @JsonCreator
    public UnaryOperationNodeImpl(@JsonProperty("operator") UnaryOperator operator,
                                  @JsonProperty("operand") Node operand) {

        if (operator == null) throw new IllegalArgumentException("Operator can't be null.");
        if (operand == null) throw new IllegalArgumentException("Operand can't be null.");

        this.operator = operator;
        this.operand = operand;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public Node getOperand() {
        return operand;
    }

    public String getType() {
        return "unary";
    }
}
