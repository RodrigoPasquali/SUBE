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

public class PlazoFijoTradicionalFixture extends InversionFixture {

    private int plazo;
    private float interes;
    private float capital;
    private static final String RUTA_PLAZOFIJOT = "/plazofijot";

    public PlazoFijoTradicionalFixture() {
        super(new EnvFixture().targetUrl() + RUTA_PLAZOFIJOT);
    }

    public void setPlazoAcordado(int plazoAcordado) {
        this.plazo = plazoAcordado;
    }

    public void setInteresAnual(float interesAnual) {
        this.interes = interesAnual;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }


    // {"plazo":360,"interes":10.0,"capital":10000.0}
    protected String prepararRequest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("plazo", this.plazo);
        data.put("interes", this.interes);
        data.put("capital", this.capital);
        return mapper.writeValueAsString(data);
    }
}
