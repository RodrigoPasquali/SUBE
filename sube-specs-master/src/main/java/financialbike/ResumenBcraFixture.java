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

import java.util.HashMap;
import java.util.Map;

public class ResumenBcraFixture {

    private static final String RUTA_RESUMEN = "/resumen";
    private String destinatario;
    private String informacionRequerida;
    private String targetUrl;

    public void setCaso(String caso) {

    }

    public ResumenBcraFixture() {
        this.targetUrl = new EnvFixture().targetUrl() + RUTA_RESUMEN;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setInformacionRequerida(String informacionRequerida) {
        this.informacionRequerida = informacionRequerida;
    }

    private String prepararRequest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("mail", this.destinatario);
        data.put("info_requerida", this.informacionRequerida);
        return mapper.writeValueAsString(data);
    }

    public String resultado() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(this.targetUrl);
            HttpEntity entity = new StringEntity(this.prepararRequest(), ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = client.execute(request);

            if (response == null ) {
                return "ERROR";
            }
            Integer statusCode = response.getStatusLine().getStatusCode();
            return statusCode.toString();
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
}
