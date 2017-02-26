package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import vo.Mes;
import vo.ResRoot;
import vo.Root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by asus-pc on 26.02.2017.
 */

@Component
public class SkypeComponent {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void sendMessage(String message, String skype_id) throws IOException {
        Root r = new Root();
        r.setMessage(new Mes());
        r.getMessage().setContent(message);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(r);
        json = json.replace("\\\\n", "\\n");
        json = json.replace("\\\\|", "|");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + updateToken())
                .url("https://apis.skype.com/v2/conversations/" + skype_id + "/activities")
                .post(body)
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
    }

    public String updateToken() throws IOException {
        //FormBody.Builder formBuilder = new FormBody.Builder().
        //       addEncoded("grant_type", "client_credentials").
        //       addEncoded("client_id", "3426779b-c697-494c-acf3-e4d6c51d40f1").
        //       addEncoded("client_secret", "sCbzkH0wMO05F1iC0D5UW27").
        //       addEncoded("scope", "https://graph.microsoft.com/.default");
        //RequestBody body = formBuilder.build();

        String url = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String content = "grant_type=client_credentials&client_id=3f964e45-49d2-4f12-91f9-2930b62c4268&client_secret=bgvYjrrqauwyjbohAHcNHHE&scope=https%3A%2F%2Fgraph.microsoft.com%2F.default";

        post.setEntity(new ByteArrayEntity(content.getBytes("UTF-8")));
        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        ResRoot resRoot = mapper.readValue(result.toString(), ResRoot.class);
        return resRoot.getAccess_token();
    }

}
