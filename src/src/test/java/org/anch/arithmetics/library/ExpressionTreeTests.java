package org.anch.arithmetics.library;

import static org.junit.Assert.assertEquals;


import org.anch.arithmetics.library.interfaces.Node;
import org.anch.arithmetics.library.nodes.BinaryOperationNodeImpl;
import org.anch.arithmetics.library.nodes.ConstantOperandNodeImpl;
import org.anch.arithmetics.library.nodes.VariableOperandNodeImpl;
import org.anch.arithmetics.library.operators.BinaryOperator;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

public class ExpressionTreeTests {

    /*
         (5 + (6 / 3)) * ((6 / 3) - 7) = -35

                  (*)
               /      \
           (+)         (-)
           / \         / \
         (5) (%)     (%) (7)
             / \     / \
           (a) (b) (a) (b)

           a = 6
           b = 3
    */
    private Node expression = new BinaryOperationNodeImpl(BinaryOperator.Multiplication,
            new BinaryOperationNodeImpl(BinaryOperator.Addition,
                    new ConstantOperandNodeImpl(new BigDecimal(5)),
                    new BinaryOperationNodeImpl(BinaryOperator.Division,
                            new VariableOperandNodeImpl("a"),
                            new VariableOperandNodeImpl("b"))),
            new BinaryOperationNodeImpl(BinaryOperator.Subtraction,
                    new BinaryOperationNodeImpl(BinaryOperator.Division,
                            new VariableOperandNodeImpl("a"),
                            new VariableOperandNodeImpl("b")),
                    new ConstantOperandNodeImpl(new BigDecimal(7))));

    private static final HashMap<String, BigDecimal> variables;

    static {
        variables = new HashMap<String, BigDecimal>();
        variables.put("a", new BigDecimal(6));
        variables.put("b", new BigDecimal(3));
    }

    @Test
    public void eval_CalculatesExpressionTree() {

        // Arrange
        ExpressionTree tree = new ExpressionTree(expression, variables);

        // Act
        BigDecimal result = tree.eval();

        // Assert
        assertEquals(new BigDecimal(-35), result);
    }

    @Test
    public void eval_PrintsExpressionTree() {

        // Arrange
        ExpressionTree tree = new ExpressionTree(expression, variables);

        // Act
        String result = tree.toString();

        // Assert
        assertEquals("((5 + (6 / 3)) * ((6 / 3) - 7))", result);
    }

    @Test(expected = ArithmeticException.class)
    public void eval_DivisionByZero_ThrowsArithmeticException() {

        // Arrange
        ExpressionTree tree = new ExpressionTree(
                new BinaryOperationNodeImpl(BinaryOperator.Division,
                        new ConstantOperandNodeImpl(new BigDecimal(5)),
                        new ConstantOperandNodeImpl(new BigDecimal(0))), null);

        // Act
        tree.eval();
    }
}
