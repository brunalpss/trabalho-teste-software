package alugueldecarros.service;

import alugueldecarros.models.Cities;

public interface CityService {

    Cities findByCity(String city);
}
