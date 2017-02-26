package ws;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import skypeschema.GetMessageRequest;
import skypeschema.GetMessageResponse;

/**
 * Created by asus-pc on 26.02.2017.
 */

@Endpoint
public class SkypeEndPoint {

    @PayloadRoot(namespace = "http://tempuri.org/SkypeSchema.xsd", localPart = "getMessageRequest")
    @ResponsePayload
    public GetMessageResponse getSkype(@RequestPayload GetMessageRequest request) {
        GetMessageResponse response = new GetMessageResponse();
        response.setStatus("OK");
        return response;
    }

}
