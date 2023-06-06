package alugueldecarros.service;

import alugueldecarros.models.States;
import javassist.NotFoundException;

public interface StateService {

    States findByUf(String uf) throws NotFoundException;
}
