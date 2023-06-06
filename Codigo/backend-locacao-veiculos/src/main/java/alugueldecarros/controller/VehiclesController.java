package alugueldecarros.controller;

import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.RequestEntity.VehiclesRequest;
import alugueldecarros.models.ResponseEntity.VehiclesResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.Vehicles;
import alugueldecarros.models.dto.UserDto;
import alugueldecarros.service.UserService;
import alugueldecarros.service.VehiclesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/vehicles")
public class VehiclesController {

    @Autowired
    private VehiclesService vehiclesService;


    @PostMapping(path = "/create")
    @ApiOperation(value = "Criar novo veiculo")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public ResponseEntity<VehiclesResponse> createVehicle(
            @ApiParam(value = "Json da requisição que contem os dados do veiculo a ser salvo")
            @Valid @RequestBody VehiclesRequest request) throws NotFoundException {
        VehiclesResponse response = this.vehiclesService.create(request);
        return ResponseEntity.ok().body(
                response
        );
    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    @PostMapping(path = "/edit")
    @ApiOperation(value = "Editar um veiculo existente")
    public ResponseEntity<VehiclesResponse> editVehicle(
            @ApiParam(value = "Json da requisição que contem o dado a ser editado")
            @Valid @RequestBody VehiclesRequest request) throws NotFoundException {

        return ResponseEntity.ok().body(
                this.vehiclesService.editVehicle(request)
        );
    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    @DeleteMapping(path = "/delete/idVehicle/{idVehicle}")
    @ApiOperation(value = "Desativa um registro de carro existente")
    public ResponseEntity<VehiclesResponse> deleteVehicle(@PathVariable(value="idVehicle") final Long idVehicle){
        return ResponseEntity.ok().body(
                this.vehiclesService.deleteVehicle(idVehicle)
        );
    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','CLIENT'})")
    @GetMapping(path = "/page/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista os veiculo disponivel por página quantidade")
    public Page<Vehicles> listVehiclesByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de veiculos a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size){

        Pageable pages = PageRequest.of(page, size);
        return this.vehiclesService.listVehiclesByPage(pages);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','CLIENT'})")
    @GetMapping(path = "page/{page}/size/{size}/name/{name}")
    @ResponseBody
    @ApiOperation(value = "Lista veiculos por página quantidade pelo nome")
    public Page<Vehicles> listVehiclesByNameAndPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
                    int page,
            @ApiParam(value = "Quantidade de veiculos a serem listados por página", example = "10")
            @PathVariable(value="size")
                    int size,
            @PathVariable(value="name")
                    String name
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.vehiclesService.listVehiclesByPageAndName(pages, name);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','CLIENT'})")
    @GetMapping(path = "getVehicleByid/idVehicle/{idVehicle}")
    @ResponseBody
    @ApiOperation(value = "Retorna um veiculo por id")
    public ResponseEntity<VehiclesResponse> getUserById(
              @PathVariable(value="idVehicle")
            Long idVehicle)throws NotFoundException{

        return ResponseEntity.ok().body(
                this.vehiclesService.getVehicleById(idVehicle)
        );

    }
}
