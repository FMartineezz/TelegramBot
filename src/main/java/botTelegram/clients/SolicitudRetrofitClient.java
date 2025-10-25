package botTelegram.clients;

import botTelegram.dtos.solicitud.SolicitudDTO;
import botTelegram.dtos.solicitud.SolicitudModificacionRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SolicitudRetrofitClient {

    @POST("/solicitudes")
    Call<SolicitudDTO> agregar(@Body SolicitudDTO dto);

    @PATCH("/solicitudes/{id}")
    Call<SolicitudDTO> modificar(@Path("id") String id, @Body SolicitudModificacionRequestDTO body);

    @GET("/solicitudes/{id}")
    Call<SolicitudDTO> buscarPorId(@Path("id") String id);
}
