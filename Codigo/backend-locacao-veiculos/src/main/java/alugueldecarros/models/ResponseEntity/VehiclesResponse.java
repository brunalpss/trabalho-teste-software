package alugueldecarros.models.ResponseEntity;

import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class VehiclesResponse {


    private Long idVehicle;

    private String name;

    private String chassi;

    private String licensePlate;

    private String manufacturer;

    private String manufacturedYear;

    private String legalDocument;

    private Date createdAt;

    private Date deletedAt;

    public VehiclesResponse() {
    }

    public VehiclesResponse(Long idVehicle, String name, String chassi, String licensePlate,
                            String manufacturer, String manufacturedYear, String legalDocument,
                            Date createdAt, Date deletedAt) {
        this.idVehicle = idVehicle;
        this.name = name;
        this.chassi = chassi;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.manufacturedYear = manufacturedYear;
        this.legalDocument = legalDocument;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static VehiclesResponse fromVehicles (Vehicles vehicles){
        return new VehiclesResponse(
                vehicles.getIdVehicle(),
                vehicles.getName(),
                vehicles.getChassi(),
                vehicles.getLicensePlate(),
                vehicles.getManufacturer(),
                vehicles.getManufacturedYear(),
                vehicles.getLegalDocument(),
                vehicles.getCreatedAt(),
                vehicles.getDeletedAt()


        );
    }
}
