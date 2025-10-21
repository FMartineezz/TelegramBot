package dtos.solicitud;


public record SolicitudDTO(String id, String descripcion, EstadoSolicitudBorradoEnum estado, String hechoId) {
    public SolicitudDTO(String id, String descripcion, EstadoSolicitudBorradoEnum estado, String hechoId) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.hechoId = hechoId;
    }

    public String id() {
        return this.id;
    }

    public String descripcion() {
        return this.descripcion;
    }

    public EstadoSolicitudBorradoEnum estado() {
        return this.estado;
    }

    public String hechoId() {
        return this.hechoId;
    }
}

