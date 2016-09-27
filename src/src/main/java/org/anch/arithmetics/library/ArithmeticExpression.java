package org.anch.arithmetics.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.anch.arithmetics.library.interfaces.Node;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public class ArithmeticExpression {

    @Valid
    @NotNull(message = "{parameter.null}")
    private Node expressionTree;

    private Map<String, BigDecimal> variables;

    @JsonCreator
    public ArithmeticExpression(@JsonProperty("expressionTree") Node expressionTree,
                                @JsonProperty("variables") Map<String, BigDecimal> variables) {

        if (expressionTree == null) throw new IllegalArgumentException("Expression tree can't be null.");
        this.expressionTree = expressionTree;
        this.variables = variables;
    }

    public Node getExpressionTree() {
        return expressionTree;
    }

    public Map<String, BigDecimal> getVariables() {
        return variables;
    }
}


