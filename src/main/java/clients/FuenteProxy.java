package clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.Fuente.HechoDTO;
import dtos.Fuente.PdiDTO;
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
        this.endpoint = env.getOrDefault("URL_FUENTE", "https://fuentegrupo10-1.onrender.com");

        var retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service = retrofit.create(FuenteRetrofitClient.class);
    }

    @SneakyThrows
    public HechoDTO crearHecho(HechoDTO dto) throws IOException {
        Response<HechoDTO> response = service.crearHecho(dto).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente Fuente");
        }

        HechoDTO hecho = response.body();
        if (hecho == null) {
            throw new IOException("Error al crear el Hecho");
        }

        return hecho;
    }

    @SneakyThrows
    public PdiDTO crearPdi(String hechoId, PdiDTO dto) throws IOException {
        Response<PdiDTO> response = service.crearPdi(hechoId, dto).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente Fuente");
        }

        PdiDTO pdi = response.body();
        if (pdi == null) {
            throw new IOException("Error al crear el PDI");
        }

        return pdi;
    }
}
