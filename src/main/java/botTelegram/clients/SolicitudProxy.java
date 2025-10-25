package botTelegram.clients;
import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.solicitud.EstadoSolicitudBorradoEnum;
import botTelegram.dtos.solicitud.SolicitudDTO;
import botTelegram.dtos.solicitud.SolicitudModificacionRequestDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class SolicitudProxy {
    private final String endpoint;
    private final SolicitudRetrofitClient service;

    public SolicitudProxy(ObjectMapper objectMapper){
        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_SOLICITUD", "https://tpdds2025-solicitudes.onrender.com");

        var retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service =retrofit.create(SolicitudRetrofitClient.class);
    }

    @SneakyThrows
    public SolicitudDTO agregar(SolicitudDTO solicitudDTO) throws IOException {
        Response<SolicitudDTO> response = service.agregar(solicitudDTO).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente solicitud");
        }

        SolicitudDTO solicitud = response.body();
        if (solicitud == null) {
            throw new IOException("Error al agregar la solcitud");
        }
        return solicitud;
    }

    @SneakyThrows
    public SolicitudDTO modificar(String id, EstadoSolicitudBorradoEnum nuevoEstado, String nuevaDescripcion) throws IOException {
        SolicitudModificacionRequestDTO modificacion = new SolicitudModificacionRequestDTO();
        modificacion.setEstado(nuevoEstado);
        modificacion.setDescripcion(nuevaDescripcion);
        Response<SolicitudDTO> response = service.modificar(id, modificacion).execute();

        SolicitudDTO solicitud = response.body();
        if (solicitud == null){
            throw new IOException("Error al modificar la solicitud");
        }
        return solicitud;
    }

    @SneakyThrows
    public SolicitudDTO buscarPorId(String id) throws NoSuchElementException{
        Response<SolicitudDTO> response = service.buscarPorId(id).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente solicitud");
        }

        SolicitudDTO solicitud = response.body();
        if(solicitud == null){
            throw new NoSuchElementException("Solicitud no encontrada para este ID: " + id);
        }
        return solicitud;
    }

}
