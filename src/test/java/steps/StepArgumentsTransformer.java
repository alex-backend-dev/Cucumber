package steps;

import consts.Endpoint;
import io.cucumber.core.options.CurlOption;
import io.cucumber.java.ParameterType;

public class StepArgumentsTransformer {

    @ParameterType("(with|without)")
    public boolean with(String string) {
        return "with".equals(string);
    }

    @ParameterType("(GET_A_BOARD|GET_ALL_BOARDS|GET_A_CARD|CREATE_A_BOARD|CREATE_A_CARD|DELETE_A_BOARD|DELETE_A_CARD|UPDATE_A_BOARD|UPDATE_A_CARD)")
    public Endpoint endpoint(String endpoint){
        return Endpoint.valueOf(endpoint);
    }

    @ParameterType("(GET|POST|PUT|DELETE)")
    public CurlOption.HttpMethod httpMethod(String method) {
        return CurlOption.HttpMethod.valueOf(method);
    }
}
