package clients;

import dtos.solicitud.SolicitudDTO;
import dtos.solicitud.SolicitudModificacionRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SolicitudRetrofitClient {

    @POST("/solicitudes")
    Call<SolicitudDTO> agregar(@RequestBody SolicitudDTO dto);

    @POST("/solicitudes/{id}")
    Call<SolicitudDTO> modificar(@Path("id") String id, @RequestBody SolicitudModificacionRequestDTO body);

}
