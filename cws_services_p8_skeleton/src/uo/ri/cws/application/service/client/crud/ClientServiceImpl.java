package uo.ri.cws.application.service.client.crud;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientCrudService {
    @Override
    public ClientDto addClient(ClientDto client) throws BusinessException {
        return null;
    }

    @Override
    public void deleteClient(String idClient) throws BusinessException {

    }

    @Override
    public void updateClient(ClientDto client) throws BusinessException {

    }

    @Override
    public List<ClientDto> findAllClients() throws BusinessException {
        return null;
    }

    @Override
    public Optional<ClientDto> findClientById(String idClient)
            throws BusinessException {
        return Optional.empty();
    }
}
