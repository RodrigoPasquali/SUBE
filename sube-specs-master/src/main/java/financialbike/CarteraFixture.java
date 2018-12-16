package financialbike;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.HashMap;
import java.util.Map;

public class CarteraFixture {

    private final String baseUrl;
    private String usuario;
    private String tipo;
    private Map<String, Object> resultado;

    private final String RUTA_CARTERA = "/cartera";
    private final String RUTA_INVERSIONES = "/inversiones";

    public CarteraFixture() {
        this.baseUrl = new EnvFixture().targetUrl();
    }

    public void setCaso(String c) {

    }

    // {"usuario":"juan@gomez.com", "tipo":"individuo" }
    public boolean usuarioTipo(String usuario, String tipo) throws JsonProcessingException {
        this.usuario = usuario;
        this.tipo = tipo;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("usuario", this.usuario);
        data.put("tipo", this.tipo);
        return 0 != this.ejecutarPost(RUTA_CARTERA, mapper.writeValueAsString(data));
    }

    protected int ejecutarPost(String ruta, String body) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(this.baseUrl + ruta);
            HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = client.execute(request);

            if (response == null ) {
                return 0;
            }
            Integer statusCode = response.getStatusLine().getStatusCode();
            return statusCode;
        }
        catch(Exception ex) {
            return 0;
        }
    }

    protected void ejecutarGet(String ruta) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(this.baseUrl + ruta);
            HttpResponse response = client.execute(request);

            if (response == null || (response.getStatusLine().getStatusCode() != 200 )) {
                resultado = null;
            }
            ObjectMapper mapper = new ObjectMapper();
            resultado = mapper.readValue(response.getEntity().getContent(), Map.class);
        }
        catch(Exception ex) {
            this.resultado = null;
        }
    }

    // {"cartera":"juan@gomez.com", "tipo":"dolar", "data":"monto_capital,compra,venta" }
    public boolean inversionCapitalCompraVenta(String tipoInversion, float capital, float compra, float venta) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("cartera", this.usuario);
        data.put("tipo", tipoInversion);
        data.put("data", capital + "," + compra + "," + venta);
        return 0 != this.ejecutarPost(RUTA_INVERSIONES, mapper.writeValueAsString(data));
    }

    // {"cartera":"juan@gomez.com", "tipo":"plazofijo_tradicional", "data":"monto_capital,interes,plazo" }
    public boolean inversionCapitalInteresPlazo(String tipoInversion, float capital, int interes, int plazo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("cartera", this.usuario);
        data.put("tipo", tipoInversion);
        data.put("data", capital + "," + interes + "," + plazo);
        return 0 != this.ejecutarPost(RUTA_INVERSIONES, mapper.writeValueAsString(data));
    }

    // {"cartera":"juan@gomez.com", "tipo":"plazofijo_precancelable", "data":"monto_capital,interes,plazo,precancela" }
    public boolean inversionCapitalInteresPlazoPrecancela(String tipoInversion, float capital, int interes, int plazo, int precancela) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("cartera", this.usuario);
        data.put("tipo", tipoInversion);
        data.put("data", capital + "," + interes + "," + plazo + "," + precancela);
        return 0 != this.ejecutarPost(RUTA_INVERSIONES, mapper.writeValueAsString(data));
    }

    //  {"cartera":"juan@gomez.com", "ganancia_bruta":34290.4, "ganancia_neta":1632.9, "impuestos":9600, "cantidad_inversiones": 1}
    public float gananciaBruta() {
        this.ejecutarGet(RUTA_INVERSIONES + "/" + this.usuario);
        if (resultado != null){
            return Float.parseFloat(resultado.get("ganancia_bruta").toString());
        }
        return -1;
    }

    public float impuestos() {
        if (resultado != null){
            return Float.parseFloat(resultado.get("impuestos").toString());
        }
        return -1;
    }

    public float gananciaNeta() {
        if (resultado != null){
            return Float.parseFloat(resultado.get("ganancia_neta").toString());
        }
        return -1;
    }

    public int cantidadInversiones() {
        if (resultado != null){
            return Integer.parseInt(resultado.get("cantidad_inversiones").toString());
        }
        return -1;
    }
}
