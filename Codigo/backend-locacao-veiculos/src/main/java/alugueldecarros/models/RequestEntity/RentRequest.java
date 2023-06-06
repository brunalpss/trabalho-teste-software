package alugueldecarros.models.RequestEntity;

import alugueldecarros.enums.OrderStatusEnum;
import alugueldecarros.models.Rents;
import alugueldecarros.models.User;
import alugueldecarros.models.Vehicles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RentRequest {

    private Long idRent;

    private User idCreator;

    private User idAttendant;

    private Vehicles idVehicle;


    private String status;


    private Date withdrawDate;


    private Date returnDate;


    private double price;


    private String paymentStatus;


    private Date createdAt;

    private Date deletedAt;


    public RentRequest() {
    }

    public static Rents toRents (RentRequest request){
        return new Rents(

            request.idCreator,
            request.idAttendant,
            request.idVehicle,
            request.status != null ? request.status : OrderStatusEnum.CREATED.getCode(),
            request.withdrawDate,
            request.returnDate,
            request.price,
            request.paymentStatus,
            new Date(),
            null
        );
    }
}
