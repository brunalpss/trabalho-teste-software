package alugueldecarros.models.RequestEntity;

import alugueldecarros.models.Contracts;
import alugueldecarros.models.Rents;
import alugueldecarros.models.ResponseEntity.ContractsResponse;
import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ContractsRequest {


    private Long idContracts;

    private Vehicles idVehicle;

    @NotNull(message = "Campo idRent não pode ser nulo")
    @NotEmpty(message = "Campo idRent não pode ser vazio")
    private Rents idRent;

    @NotNull(message = "Campo contractType não pode ser nulo")
    @NotEmpty(message = "Campo contractType não pode ser vazio")
    private String contractType;

    private String filePath;

    @NotNull(message = "Campo contractNumber não pode ser nulo")
    @NotEmpty(message = "Campo contractNumber não pode ser vazio")
    private String contractNumber;

    @NotNull(message = "Campo contractDescription não pode ser nulo")
    @NotEmpty(message = "Campo contractDescription não pode ser vazio")
    private String contractDescription;

    private Date createdAt;

    private Date deletedAt;


    public static Contracts toContracts (ContractsRequest request){

        return new Contracts(

                request.getIdContracts(),
                request.getIdVehicle(),
                request.getIdRent(),
                request.getContractType(),
                request.getFilePath(),
                request.getContractNumber(),
                request.getContractDescription(),
                request.getCreatedAt(),
                request.getDeletedAt()
        );
    }
}
