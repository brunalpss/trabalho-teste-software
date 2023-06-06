package alugueldecarros.service.impl;

import alugueldecarros.models.Rents;
import alugueldecarros.models.RequestEntity.RentRequest;
import alugueldecarros.models.ResponseEntity.RentsResponse;
import alugueldecarros.models.User;
import alugueldecarros.repository.RentsRepository;
import alugueldecarros.service.RentsService;
import alugueldecarros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.Date;
import java.util.Optional;

@Service
public class RentsServiceImpl implements RentsService {
    @Autowired
    RentsRepository rentsRepository;

    @Autowired
    UserService userService;
    @Override
    public RentsResponse createRent(RentRequest request) {
        Optional<Rents> rent = Optional.ofNullable(this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(request.getIdRent()));

        if(!rent.isPresent()){
            return RentsResponse.fromRents(
                    this.rentsRepository.save(
                            RentRequest.toRents(request)
                    )
            );
        }else{
            throw new NonUniqueResultException("Veiculo ja cadastrado!");
        }
    }

    @Override
    public RentsResponse editRent(RentRequest request) {

        Optional<Rents> requestedRent = Optional.ofNullable(this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(request.getIdRent()));
        if(requestedRent.isPresent()){

            requestedRent.get().setPrice(request.getPrice());
            requestedRent.get().setStatus(request.getStatus());
            requestedRent.get().setIdVehicle(request.getIdVehicle());
            requestedRent.get().setReturnDate(request.getReturnDate());
            requestedRent.get().setWithdrawDate(request.getWithdrawDate());
            requestedRent.get().setPaymentStatus(request.getPaymentStatus());


            return RentsResponse.fromRents(
                    this.rentsRepository.save(requestedRent.get())
            );
        }else{
            throw new NonUniqueResultException("Reserva Inexistente!");
        }
    }

    @Override
    public RentsResponse deleteRent(Long idRent) {

        Rents rent = this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(idRent);

        rent.setDeletedAt(new Date());
        return RentsResponse.fromRents(this.rentsRepository.save(rent));
    }

    @Override
    public RentsResponse cancelRentLoggedUser(Long idRent) {

        User loggedUser = this.userService.getUserByPrincipal();
        Rents rent = this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(idRent);

        if(rent.getIdCreator().getIdUser() == loggedUser.getIdUser()) {

            rent.setDeletedAt(new Date());
            return RentsResponse.fromRents(this.rentsRepository.save(rent));
        }else{
            throw new NonUniqueResultException("Reserva Inexistente, ou ID do criador diferente do Id da reserva!");
        }


    }

    @Override
    public Page<Rents> listRentsByPage(Pageable pages) {
        return this.rentsRepository.findAllByDeletedAtIsNullOrderByIdRent(pages);
    }

    @Override
    public Page<Rents> listRentsByPageAndStatus(Pageable pages, String status) {

        return this.rentsRepository.findAllByStatusIgnoreCaseAndDeletedAtIsNull(pages,status);
    }

    @Override
    public Page<Rents> listRentsByUserAndPage(Pageable pages, Long idUser) {

        return this.rentsRepository.findAllByIdCreatorAndDeletedAtIsNullOrderByIdRent(pages,idUser);
    }

    @Override
    public Page<Rents> listRentsByPageAndStatusAndUser(Pageable pages, String status, Long idUser) {
        return this.rentsRepository.findAllByIdCreatorAndStatusIgnoreCaseAndDeletedAtIsNull(pages,idUser,status);
    }

    @Override
    public RentsResponse getRentById(Long idRent) {
        return RentsResponse.fromRents(this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(idRent));
    }

    @Override
    public RentsResponse changeStatus(RentRequest request) {


        Optional<Rents> requestedRent = Optional.ofNullable(this.rentsRepository.findOneByIdRentAndDeletedAtIsNull(request.getIdRent()));
        if(requestedRent.isPresent()){
            requestedRent.get().setStatus(request.getStatus());
            return RentsResponse.fromRents(
                    this.rentsRepository.save(requestedRent.get())
            );
        }else{
            throw new NonUniqueResultException("Reserva Inexistente!");
        }
    }
}
