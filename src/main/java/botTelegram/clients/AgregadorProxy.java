package botTelegram.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.Fuente.HechoDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class AgregadorProxy {

    private final String endpoint;
    private final AgregadorRetrofitClient service;

    public AgregadorProxy(ObjectMapper objectMapper) {
        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_AGREGADOR", "https://two025-tp-entrega-2-igalpr.onrender.com");

        var retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service = retrofit.create(AgregadorRetrofitClient.class);
    }

    @SneakyThrows
    public List<HechoDTO> getHechos(String colectionName) throws IOException {
        Response<List<HechoDTO>> response = service.getHechosPorColleccion(colectionName).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente Agregador");
        }

        List<HechoDTO> hecho = response.body();
        if (hecho == null) {
            throw new IOException("Error al obtener los hechos por coleccion en Agregador");
        }

        return hecho;
    }
}
