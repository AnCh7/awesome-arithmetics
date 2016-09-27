package org.anch.arithmetics.library.nodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.anch.arithmetics.library.interfaces.BinaryOperationNode;
import org.anch.arithmetics.library.interfaces.Node;
import org.anch.arithmetics.library.operators.BinaryOperator;

import javax.validation.constraints.NotNull;

public final class BinaryOperationNodeImpl implements BinaryOperationNode {

    @NotNull(message = "{parameter.null}")
    private final BinaryOperator operator;

    @NotNull(message = "{parameter.null}")
    private final Node left;

    @NotNull(message = "{parameter.null}")
    private final Node right;

    @JsonCreator
    public BinaryOperationNodeImpl(@JsonProperty("operator") BinaryOperator operator,
                                   @JsonProperty("left") Node left,
                                   @JsonProperty("right") Node right) {

        if (operator == null) throw new IllegalArgumentException("Operator can't be null.");
        if (left == null) throw new IllegalArgumentException("Left node can't be null.");
        if (right == null) throw new IllegalArgumentException("Right node can't be null.");

        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getType() {
        return "binary";
    }
}
