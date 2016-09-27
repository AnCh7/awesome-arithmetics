package org.anch.arithmetics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.anch.arithmetics.library.ArithmeticExpression;
import org.anch.arithmetics.library.interfaces.Node;
import org.anch.arithmetics.library.nodes.BinaryOperationNodeImpl;
import org.anch.arithmetics.library.nodes.ConstantOperandNodeImpl;
import org.anch.arithmetics.library.nodes.UnaryOperationNodeImpl;
import org.anch.arithmetics.library.nodes.VariableOperandNodeImpl;
import org.anch.arithmetics.library.operators.BinaryOperator;
import org.anch.arithmetics.library.operators.UnaryOperator;
import org.anch.arithmetics.service.errorhandling.ErrorMessage;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ArithmeticsResourceTest extends JerseyTest {


    /*
         (5 + (6 / 3)) * ((6 / 3) - 7) = -35

                  (*)
               /      \
           (+)         (-)
           / \         / \
         (5) (%)     (%) (7)
             / \     / \
           (6) (3) (6) (3)

    */
    private Node expressionOnlyConstants = new BinaryOperationNodeImpl(BinaryOperator.Multiplication,
            new BinaryOperationNodeImpl(BinaryOperator.Addition,
                    new ConstantOperandNodeImpl(new BigDecimal(5)),
                    new BinaryOperationNodeImpl(BinaryOperator.Division,
                            new ConstantOperandNodeImpl(new BigDecimal(6)),
                            new ConstantOperandNodeImpl(new BigDecimal(3)))),
            new BinaryOperationNodeImpl(BinaryOperator.Subtraction,
                    new BinaryOperationNodeImpl(BinaryOperator.Division,
                            new ConstantOperandNodeImpl(new BigDecimal(6)),
                            new ConstantOperandNodeImpl(new BigDecimal(3))),
                    new ConstantOperandNodeImpl(new BigDecimal(7))));

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
    private Node expressionWithVariables = new BinaryOperationNodeImpl(BinaryOperator.Multiplication,
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

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ArithmeticsApplication();
    }

    @Test
    public void calculate_ArithmeticExpression_CannotBeNull_Returns400BadRequest() {
        ArithmeticExpression expression = null;
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_ArithmeticExpression_ExpressionTreeParameter_CannotBeNull() {
        String request = "{ \"expressionTree\": null }";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_ArithmeticExpression_VariablesParameter_AreNotValidated() {
        ArithmeticExpression expression = new ArithmeticExpression(new ConstantOperandNodeImpl(new BigDecimal(6)), null);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(6), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_BinaryOperationNodeParameters_CannotBeNull() {
        String request = "{" +
                "  \"expressionTree\":" +
                "    \"operator\": null," +
                "    \"left\": null," +
                "    \"right\": null" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_BinaryOperationNodeWithValidParameters_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(
                new BinaryOperationNodeImpl(BinaryOperator.Division,
                        new ConstantOperandNodeImpl(new BigDecimal(6)),
                        new ConstantOperandNodeImpl(new BigDecimal(3))), null);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(2), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_ConstantOperationNodeParameters_CannotBeNull() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operand\": null" +
                "  }" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_ConstantOperationNodeWithValidParameters_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(new ConstantOperandNodeImpl(new BigDecimal(6)), null);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(6), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_VariableOperandNodeParameters_CannotBeNull() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operand\": null" +
                "  }" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_VariableOperandNodeWithValidParameters_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(
                new BinaryOperationNodeImpl(BinaryOperator.Division,
                        new VariableOperandNodeImpl("a"),
                        new VariableOperandNodeImpl("b")), variables);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(2), output.readEntity(BigDecimal.class));
    }


    @Test
    public void calculate_UnaryOperationNodeParameters_CannotBeNull() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operator\": null," +
                "    \"node\": null" +
                "  }" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_UnaryOperationNodeWithValidParameters_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(
                new UnaryOperationNodeImpl(UnaryOperator.Negation, new ConstantOperandNodeImpl(new BigDecimal(10))), null);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(-10), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_NodeWithoutTypeParameter_Returns400BadRequest() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operator\": \"+\"" +
                "    \"left\": {" +
                "      \"operand\": 5" +
                "    }," +
                "    \"right\": {" +
                "      \"operand\": 5" +
                "    }" +
                "  }" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
    }

    @Test
    public void calculate_NodeWithTypeParameter_ReturnsValidResult() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operator\": \"+\"," +
                "     \"type\": \"binary\"," +
                "    \"left\": {" +
                "      \"operand\": 5," +
                "      \"type\": \"constant\"" +
                "    }," +
                "    \"right\": {" +
                "      \"operand\": 5," +
                "      \"type\": \"constant\"" +
                "    }" +
                "  }" +
                "}";
        Response output = sendJsonRequest(request);
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(10), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_ExpressionWithOnlyConstants_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(expressionOnlyConstants, null);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(-35), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_ExpressionWithConstantsAndVariables_ReturnsValidResult() {
        ArithmeticExpression expression = new ArithmeticExpression(expressionWithVariables, variables);
        Response output = sendJsonRequest(toJson(expression));
        assertEquals("Should return status 200", 200, output.getStatus());
        assertEquals(new BigDecimal(-35), output.readEntity(BigDecimal.class));
    }

    @Test
    public void calculate_DivisionByZero_ArithmeticsServiceException() {
        String request = "{" +
                "  \"expressionTree\": {" +
                "    \"operator\": \"/\"," +
                "     \"type\": \"binary\"," +
                "    \"left\": {" +
                "      \"operand\": 5," +
                "      \"type\": \"constant\"" +
                "    }," +
                "    \"right\": {" +
                "      \"operand\": 0," +
                "      \"type\": \"constant\"" +
                "    }" +
                "  }" +
                "}";

        Response output = sendJsonRequest(request);
        assertEquals("Should return status 400", 400, output.getStatus());
        ErrorMessage error = output.readEntity(ErrorMessage.class);
        assertEquals("Division by zero", error.getMessage());
    }

    private Response sendJsonRequest(String request) {
        return target("arithmetics/v1/calculate")
                .request()
                .post(Entity.json(request));
    }

    private String toJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            json = e.getMessage();
        }
        return json;
    }
}
