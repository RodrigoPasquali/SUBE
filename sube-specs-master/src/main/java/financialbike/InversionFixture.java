package financialbike;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

public abstract class InversionFixture {

    protected final String targetUrl;

    public InversionFixture(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public float ganancia() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(this.targetUrl);
        HttpEntity entity = new StringEntity(this.prepararRequest(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        if (response == null) {
            return -1;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(response.getEntity().getContent(), Map.class);
        return Float.parseFloat(result.get("ganancia").toString());
    }

    public void setCaso(String c) {

    }

    protected abstract String  prepararRequest() throws JsonProcessingException;
}
