package org.anch.arithmetics.library;

import org.anch.arithmetics.library.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.NotSupportedException;
import java.math.BigDecimal;
import java.util.Map;

public class ExpressionTree {

    private static final Logger logger = LogManager.getLogger(ExpressionTree.class.getName());

    private Node expression;
    private Map<String, BigDecimal> parameters;

    public ExpressionTree(Node root, Map<String, BigDecimal> params) {
        expression = root;
        parameters = params;
    }

    public BigDecimal eval() throws ArithmeticException, NotSupportedException {
        return eval(expression);
    }

    private BigDecimal eval(Node root) throws ArithmeticException, NotSupportedException {
        logger.info("Processing node with {} type", root.getType());

        if (root instanceof ConstantOperandNode) {
            return ProcessConstantNode((ConstantOperandNode) root);
        } else if (root instanceof VariableOperandNode) {
            return ProcessVariableNode((VariableOperandNode) root);
        } else if (root instanceof UnaryOperationNode) {
            return ProcessUnaryNode((UnaryOperationNode) root);
        } else if (root instanceof BinaryOperationNode) {
            return ProcessBinaryNode((BinaryOperationNode) root);
        } else {
            throw new NotSupportedException("Unknown expression type: " + root.getClass());
        }
    }

    private BigDecimal ProcessConstantNode(ConstantOperandNode node) {
        return node.getOperand();
    }

    private BigDecimal ProcessVariableNode(VariableOperandNode node) {
        if (parameters.containsKey(node.getOperand())) {
            return parameters.get(node.getOperand());
        } else {
            throw new ArithmeticException("Expression contains unknown variable: " + node.getOperand());
        }
    }

    private BigDecimal ProcessUnaryNode(UnaryOperationNode node) {
        switch (node.getOperator()) {
            case Negation:
                return eval(node.getOperand()).negate();
            default:
                throw new NotSupportedException("Not supported unary operator in expression: " + node.getOperator());
        }
    }

    private BigDecimal ProcessBinaryNode(BinaryOperationNode node) {
        switch (node.getOperator()) {
            case Addition:
                return eval(node.getLeft()).add(eval(node.getRight()));
            case Subtraction:
                return eval(node.getLeft()).subtract(eval(node.getRight()));
            case Multiplication:
                return eval(node.getLeft()).multiply(eval(node.getRight()));
            case Division:
                return eval(node.getLeft()).divide(eval(node.getRight()));
            default:
                throw new NotSupportedException("Not supported binary operator in expression: " + node.getOperator());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printExpression(expression, sb);
        return sb.toString();
    }

    private void printExpression(Node root, StringBuilder sb) {
        if (root instanceof ConstantOperandNode) {
            sb.append(((ConstantOperandNode) root).getOperand());
        } else if (root instanceof VariableOperandNode) {
            VariableOperandNode r = (VariableOperandNode) root;
            if (parameters.containsKey(r.getOperand())) {
                sb.append(parameters.get(r.getOperand()));
            } else {
                throw new ArithmeticException("Expression contains unknown variable: " + r.getOperand());
            }
        } else if (root instanceof UnaryOperationNode) {
            UnaryOperationNode r = (UnaryOperationNode) root;
            sb.append(r.getOperator().toValue()).append(" ");
            printExpression(r.getOperand(), sb);
        } else if (root instanceof BinaryOperationNode) {
            BinaryOperationNode r = (BinaryOperationNode) root;
            sb.append("(");
            printExpression(r.getLeft(), sb);
            sb.append(" ").append(r.getOperator().toValue()).append(" ");
            printExpression(r.getRight(), sb);
            sb.append(")");
        } else {
            throw new NotSupportedException("Unknown expression type: " + root.getClass());
        }
    }
}
