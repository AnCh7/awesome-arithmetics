package org.anch.arithmetics.service;

import org.anch.arithmetics.library.ArithmeticExpression;
import org.anch.arithmetics.library.ExpressionTree;
import org.anch.arithmetics.service.errorhandling.ArithmeticsServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("arithmetics/v1")
public class ArithmeticsResource {

    private static final Logger logger = LogManager.getLogger(ArithmeticsResource.class.getName());

    @POST
    @Path("calculate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response calculate(@Valid @NotNull ArithmeticExpression expression) throws ArithmeticsServiceException {
        try {
            ExpressionTree tree = new ExpressionTree(expression.getExpressionTree(), expression.getVariables());
            logger.info("Processing expression {}", tree.toString());
            BigDecimal result = tree.eval();
            return Response.status(200).entity(result).build();
        } catch (Exception ex) {
            Response.Status status = Response.Status.BAD_REQUEST;
            throw new ArithmeticsServiceException(status, status.getStatusCode(), ex.getMessage());
        }
    }
}
