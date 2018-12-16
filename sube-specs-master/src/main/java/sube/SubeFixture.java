package sube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import financialbike.EnvFixture;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.HashMap;
import java.util.Map;

public class SubeFixture {

    private static final String RUTA_TARJETAS = "/tarjetas";
    private static final String RUTA_PAGO = "/pagos";
    private String mail;
    private int saldoInicial;
    private int saldoActual;
    private Map<String, Object> resultado;

    private final String baseUrl;

    public SubeFixture() {
        this.baseUrl = new EnvFixture().targetUrl();
    }

    public boolean tarjetaConSaldo(String mail, int montoInicial) throws JsonProcessingException {
        this.mail = mail;
        this.saldoInicial = montoInicial;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("id", this.mail);
        data.put("saldo", this.saldoInicial);
        resultado = this.ejecutarPost(RUTA_TARJETAS, mapper.writeValueAsString(data));
        return (this.resultado.get("statusCode").equals(201));
    }

    protected Map<String, Object> ejecutarPost(String ruta, String body) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(this.baseUrl + ruta);
            HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = client.execute(request);

            if (response == null ) {
                return null;
            }

            Integer statusCode = response.getStatusLine().getStatusCode();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> resultado = mapper.readValue(response.getEntity().getContent(), Map.class);
            resultado.put("statusCode",statusCode);
            return resultado;
        }
        catch(Exception ex) {
            return null;
        }
    }

    protected Map<String, Object> ejecutarPut(String ruta, String body) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut request = new HttpPut(this.baseUrl + ruta);
            HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = client.execute(request);

            if (response == null ) {
                return null;
            }

            Integer statusCode = response.getStatusLine().getStatusCode();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> resultado = mapper.readValue(response.getEntity().getContent(), Map.class);
            resultado.put("statusCode",statusCode);
            return resultado;
        }
        catch(Exception ex) {
            return null;
        }
    }

    protected Map<String, Object> ejecutarGet(String ruta) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(this.baseUrl + ruta);
            HttpResponse response = client.execute(request);

            if (response == null ) {
                return null;
            }

            Integer statusCode = response.getStatusLine().getStatusCode();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> resultado = mapper.readValue(response.getEntity().getContent(), Map.class);
            resultado.put("statusCode",statusCode);
            return resultado;
        }
        catch(Exception ex) {
            return null;
        }
    }

    public int saldo() {
        return Integer.parseInt(this.resultado.get("saldo").toString());
    }

    public int cobrado() {
        return Integer.parseInt(this.resultado.get("cobrado").toString());
    }

    public boolean pagar(int monto) throws JsonProcessingException {
        this.mail = mail;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("id", this.mail);
        data.put("monto", monto);
        this.resultado = this.ejecutarPost(RUTA_PAGO, mapper.writeValueAsString(data));
        return (this.resultado.get("statusCode").equals(200));
    }

    public boolean registrar(String modificador) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("id", this.mail);
        data.put("modificador", modificador);
        this.resultado = this.ejecutarPut(RUTA_TARJETAS, mapper.writeValueAsString(data));
        return (this.resultado.get("statusCode").equals(200));
    }

    public boolean consultar(String id) {
        this.resultado = this.ejecutarGet(RUTA_TARJETAS + "/" + id);
        return true;
    }

    public String modificadores() {
        return this.resultado.get("modificadores").toString();
    }
}
