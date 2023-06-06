package alugueldecarros.service.impl;

import alugueldecarros.repository.CitiesRepository;
import alugueldecarros.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private CitiesRepository citiesRepository;

}
