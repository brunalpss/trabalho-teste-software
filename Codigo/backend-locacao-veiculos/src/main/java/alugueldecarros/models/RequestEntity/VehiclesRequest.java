package alugueldecarros.models.RequestEntity;

import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class VehiclesRequest {

    private Long idVehicle;

    @NotNull(message = "Campo name não pode ser nulo")
    @NotEmpty(message = "Campo name não pode ser vazio")
    private String name;

    @NotNull(message = "Campo chassi não pode ser nulo")
    @NotEmpty(message = "Campo chassi não pode ser vazio")
    private String chassi;

    @NotNull(message = "Campo licensePlate não pode ser nulo")
    @NotEmpty(message = "Campo licensePlate não pode ser vazio")
    private String licensePlate;

    @NotNull(message = "Campo manufacturer não pode ser nulo")
    @NotEmpty(message = "Campo manufacturer não pode ser vazio")
    private String manufacturer;

    @NotNull(message = "Campo manufacturedYear não pode ser nulo")
    @NotEmpty(message = "Campo manufacturedYear não pode ser vazio")
    private String manufacturedYear;

    @NotNull(message = "Campo legalDocument não pode ser nulo")
    @NotEmpty(message = "Campo legalDocument não pode ser vazio")
    private String legalDocument;

    private Date createdAt;

    private Date deletedAt;

    public VehiclesRequest() {
    }


    public static Vehicles toVehicles (VehiclesRequest request){
        return new Vehicles(
                request.getIdVehicle(),
                request.getName(),
                request.getChassi(),
                request.getLicensePlate(),
                request.getManufacturer(),
                request.getManufacturedYear(),
                request.getLegalDocument(),
                request.getCreatedAt(),
                request.getDeletedAt()
        );
    }
}
