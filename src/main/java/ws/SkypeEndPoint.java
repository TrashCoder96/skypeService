package ws;

import logic.SkypeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import skypeschema.GetMessageRequest;
import skypeschema.GetMessageResponse;

import java.io.IOException;

/**
 * Created by asus-pc on 26.02.2017.
 */

@Endpoint
public class SkypeEndPoint {

    @Autowired
    private SkypeComponent skypeComponent;

    @PayloadRoot(namespace = "http://tempuri.org/SkypeSchema.xsd", localPart = "getMessageRequest")
    @ResponsePayload
    public GetMessageResponse getSkype(@RequestPayload GetMessageRequest request) throws IOException {
        skypeComponent.sendMessage(request.getContent(), request.getId());
        GetMessageResponse response = new GetMessageResponse();
        response.setStatus("OK");
        return response;
    }

}
