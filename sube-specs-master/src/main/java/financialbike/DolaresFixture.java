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
import java.util.HashMap;
import java.util.Map;

public class DolaresFixture extends InversionFixture {

    private float monto;
    private float compra;
    private float venta;
    private float capital;
    private static final String RUTA_DOLARES = "/dolares";

    public DolaresFixture() {
        super(new EnvFixture().targetUrl() + RUTA_DOLARES);
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setCompra(float valorCompra) {
        this.compra = valorCompra;
    }

    public void setVenta(float valorVenta) {
        this.venta = valorVenta;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    protected String prepararRequest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("compra", this.compra);
        data.put("venta", this.venta);
        data.put("capital", this.capital);
        return mapper.writeValueAsString(data);
    }
}
