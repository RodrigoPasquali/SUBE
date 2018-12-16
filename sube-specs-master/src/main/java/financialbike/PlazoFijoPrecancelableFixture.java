package financialbike;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class PlazoFijoPrecancelableFixture extends InversionFixture {

    private int plazoAcordado;
    private int plazoReal;
    private float interes;
    private float capital;
    private static final String RUTA_PLAZOFIJOP = "/plazofijop";

    public PlazoFijoPrecancelableFixture() {
        super(new EnvFixture().targetUrl() + RUTA_PLAZOFIJOP);
    }

    public void setPlazoAcordado(int plazoAcordado) {
        this.plazoAcordado = plazoAcordado;
    }

    public void setPlazoReal(int plazoReal) {
        this.plazoReal = plazoReal;
    }

    public void setInteresAnual(float interesAnual) {
        this.interes = interesAnual;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    // {"plazo_acordado":360, "plazo_real": 300, "interes":10.0,"capital":10000.0}
    protected String prepararRequest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> data =  new HashMap<String, Object>();
        data.put("plazo_acordado", this.plazoAcordado);
        data.put("plazo_real", this.plazoReal);
        data.put("interes", this.interes);
        data.put("capital", this.capital);
        return mapper.writeValueAsString(data);
    }

}
