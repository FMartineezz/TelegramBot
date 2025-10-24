package botTelegram.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.Fuente.HechoDTO;
import botTelegram.dtos.Fuente.PdiDTO;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
public class FuenteProxy {

    private final String endpoint;
    private final FuenteRetrofitClient service;

    public FuenteProxy(ObjectMapper objectMapper) {
        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_FUENTE", "https://fuentegrupo10-1.onrender.com/");

        ObjectMapper mapper = objectMapper.copy();
        mapper.registerModule(new JavaTimeModule());
        mapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE);

        var retrofit = new Retrofit.Builder()
                .baseUrl(endpoint.endsWith("/") ? endpoint : endpoint + "/")
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        this.service = retrofit.create(FuenteRetrofitClient.class);
    }

    @SneakyThrows
    public HechoDTO agregarHecho(HechoDTO hechoDTO) throws IOException {
        Response<HechoDTO> response = service.crearHecho(hechoDTO).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException(" Error conectándose con el componente Fuente");
        }

        HechoDTO hecho = response.body();
        if (hecho == null) {
            throw new IOException(" Error al crear el Hecho — respuesta vacía");
        }

        return hecho;
    }

    @SneakyThrows
    public PdiDTO agregarPdi(String hechoId, PdiDTO pdiDTO) throws IOException {
        Response<PdiDTO> response = service.crearPdi(pdiDTO).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException(" Error conectándose con el componente Fuente");
        }

        PdiDTO pdi = response.body();
        if (pdi == null) {
            throw new IOException(" Error al crear el PDI — respuesta vacía");
        }

        return pdi;
    }
}
