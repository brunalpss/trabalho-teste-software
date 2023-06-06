package alugueldecarros.service;

import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.RequestEntity.VehiclesRequest;
import alugueldecarros.models.ResponseEntity.VehiclesResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.Vehicles;
import alugueldecarros.models.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;

public interface VehiclesService {


    VehiclesResponse create(VehiclesRequest request);

    VehiclesResponse editVehicle(VehiclesRequest request);

    VehiclesResponse deleteVehicle(Long idVehicle);
    Page<Vehicles> listVehiclesByPage(Pageable pages);
    Page<Vehicles> listVehiclesByPageAndName(Pageable pages, String name);
    VehiclesResponse getVehicleById(Long idVehicle);

    LinkedList<Vehicles> getAllVehiclesToList();
}
