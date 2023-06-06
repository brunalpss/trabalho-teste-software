package alugueldecarros.models.ResponseEntity;

import alugueldecarros.models.Contracts;
import alugueldecarros.models.Rents;
import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ContractsResponse {


    private Long idContracts;

    private Vehicles idVehicle;

    private Rents idRent;

    private String contractType;

    private String filePath;

    private String contractNumber;

    private String contractDescription;

    private Date createdAt;

    private Date deletedAt;

    public ContractsResponse(Long idContracts, Vehicles idVehicle,
                             Rents idRent, String contractType, String filePath, String contractNumber,
                             String contractDescription, Date createdAt, Date deletedAt) {
        this.idContracts = idContracts;
        this.idVehicle = idVehicle;
        this.idRent = idRent;
        this.contractType = contractType;
        this.filePath = filePath;
        this.contractNumber = contractNumber;
        this.contractDescription = contractDescription;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static ContractsResponse fromContracts (Contracts contracts){
        return new ContractsResponse(
                contracts.getIdContracts(),
                contracts.getIdVehicle(),
                contracts.getIdRent(),
                contracts.getContractType(),
                contracts.getFilePath(),
                contracts.getContractNumber(),
                contracts.getContractDescription(),
                contracts.getCreatedAt(),
                contracts.getDeletedAt()
        );
    }
}
