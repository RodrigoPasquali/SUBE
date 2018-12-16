package mailmerger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nicopaez on 14/06/2018.
 */
public class MailMergerFixture {


    private String errorMessage = "";
    private HttpResponse response;
    private String serverUrl = "http://requestbin.fullcontact.com/1ivp8a01";

    public MailMergerFixture(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setRequest(String requestBody) throws IOException {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(this.serverUrl);
            HttpEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            this.response = client.execute(request);
        }
        catch(Exception ex) {
            this.errorMessage = ex.getMessage();
        }
    }

    public int response_status() throws IOException {
        if (this.response == null ) {
            return 900;
        }
        return response.getStatusLine().getStatusCode();
    }

    public String response_text()  throws IOException {
        if (this.response == null ) {
            return this.errorMessage;
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
