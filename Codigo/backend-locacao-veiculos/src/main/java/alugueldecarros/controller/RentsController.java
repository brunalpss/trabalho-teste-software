package alugueldecarros.controller;

import alugueldecarros.models.Rents;
import alugueldecarros.models.RequestEntity.RentRequest;
import alugueldecarros.models.RequestEntity.UserRequest;
import alugueldecarros.models.ResponseEntity.RentsResponse;
import alugueldecarros.models.User;
import alugueldecarros.models.dto.UserDto;
import alugueldecarros.service.RentsService;
import alugueldecarros.service.UserService;
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
@RequestMapping("/rents")
public class RentsController {

    @Autowired
    private RentsService rentsService;

    @PostMapping(path = "/create")
    @ApiOperation(value = "Criar nova Reserva de veículo")
    //@PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY'})")
    public ResponseEntity<RentsResponse> createRent(
            @ApiParam(value = "Json da requisição que contem os dados da reserva a ser criada")
            @Valid @RequestBody RentRequest request) throws NotFoundException {
        RentsResponse response = this.rentsService.createRent(request);
        return ResponseEntity.ok().body(
                response
        );
    }


    @PostMapping(path = "/editRent")
    @ApiOperation(value = "Editar reserva existente")
    public ResponseEntity<RentsResponse> editUser(
            @ApiParam(value = "Json da requisição que contem o dado a ser editado")
            @Valid @RequestBody RentRequest request) throws NotFoundException {

        return ResponseEntity.ok().body(
                this.rentsService.editRent(request)
        );
    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    @DeleteMapping(path = "/delete/idRent{idRent}")
    @ApiOperation(value = "Desativa uma reserva existente")
    public ResponseEntity<RentsResponse> deleteRent(@PathVariable(value="idRent") final Long idRent){
        return ResponseEntity.ok().body(
                this.rentsService.deleteRent(idRent)
        );
    }


    @DeleteMapping(path = "/cancelRent/idRent{idRent}")
    @ApiOperation(value = "Cancela um pedido do proprio usuario logado (muda o status para cancelada) " +
            "id solicitante = id vinculado na rent, ou idADMIN")
    public ResponseEntity<RentsResponse> cancelRent(@PathVariable(value="idRent") final Long idRent){
        return ResponseEntity.ok().body(
                this.rentsService.cancelRentLoggedUser(idRent)
        );
    }


    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY'})")
    @GetMapping(path = "/page/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista todas as reservas página quantidade")
    public Page<Rents> listRentsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de reservas a serem listadas por página", example = "10")
            @PathVariable(value="size")
            int size){

        Pageable pages = PageRequest.of(page, size);
        return this.rentsService.listRentsByPage(pages);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY'})")
    @GetMapping(path = "page/{page}/size/{size}/status/{status}")
    @ResponseBody
    @ApiOperation(value = "Lista todas as reservas com status solicitado por página quantidade")
    public Page<Rents> listRentsByStatusAndPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
                    int page,
            @ApiParam(value = "Quantidade de reservas a serem listadas por página", example = "10")
            @PathVariable(value="size")
                    int size,
            @PathVariable(value="status")
                    String status
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.rentsService.listRentsByPageAndStatus(pages, status);

    }

    @GetMapping(path = "/pageAllByIdUser/{page}/size/{size}/idUser{idUser}")
    @ResponseBody
    @ApiOperation(value = "Lista todas as reservas de um usuario página quantidade")
    public Page<Rents> listRentsByUserAndPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de reservas a serem listadas por página", example = "10")
            @PathVariable(value="size")
            int size,
            @ApiParam(value = "Id Usuario", example = "1")
            @PathVariable(value="idUser")
            Long idUser
    ){

        Pageable pages = PageRequest.of(page, size);
        return this.rentsService.listRentsByUserAndPage(pages,idUser);

    }

    @GetMapping(path = "/pageAllByIdUserAndStatus/{page}/size/{size}/idUser{idUser}/status/{status}")
    @ResponseBody
    @ApiOperation(value = "Lista todas as reservas de um usuario com status solicitado por página quantidade")
    public Page<Rents> listRentsByStatusAndUserByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de reservas a serem listadas por página", example = "10")
            @PathVariable(value="size")
            int size,
            @ApiParam(value = "idUser", example = "3")
            @PathVariable(value="idUser")
            Long idUser,
            @ApiParam(value = "Status", example = "CREATED")
            @PathVariable(value="status")
            String status
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.rentsService.listRentsByPageAndStatusAndUser(pages, status, idUser);

    }
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS','THIRDPARTY','CLIENT'})")
    @GetMapping(path = "getRentByid/idRent/{idRent}")
    @ResponseBody
    @ApiOperation(value = "retorna uma Rent por idRent, se for usuario Client, verifica se o id dele está vinculado na reserva")
    public ResponseEntity<RentsResponse> getRentById(
              @PathVariable(value="idRent")
            Long idRent)throws NotFoundException{

        return ResponseEntity.ok().body(
                this.rentsService.getRentById(idRent)
        );

    }
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','FINANCIAL','SELLERS'})")
    @PostMapping(path = "/changeStatus")
    @ApiOperation(value = "Muda o status de uma reserva para aprovar, reprovar, agendar")
    public ResponseEntity<RentsResponse> changeStatus(
            @ApiParam(value = "Json da requisição que contem o status a ser alterado")
            @Valid @RequestBody RentRequest request) throws NotFoundException {

        return ResponseEntity.ok().body(
                this.rentsService.changeStatus(request)
        );
    }
}
