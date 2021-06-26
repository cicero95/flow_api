package br.com.flow_api.client;

import br.com.flow_api.exceptions.ResourceNotFoundException;
import br.com.flow_api.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ClientService {


    private static final String ERROR_INSERT_CLIENT = "Não foi possivel cadastrar este cliente";
    private static final String ERROR_CLIENT_CPF_EXSITS = "Já existe cliente cadastrado com o CPF - %s ";
    private static final String ERROR_CLIENT_CNPJ_EXSITS = "Já existe cliente cadastrado com o CNPJ - %s ";
    private static final String ERROR_SEARCH_CLIENT = "Ocorreu um erro ao tentar buscar os dados do cliente";

    @Autowired
    private ClientRepository clientsRepository;

    public List<Client> findAll() {
        return clientsRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Client findById(Long id) {
        Optional<Client> client = clientsRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Client insert(Client client) throws ServiceException {
        verifyCpfCnpj(client);

        try {
            clientsRepository.save(client);
        } catch (Exception e) {
            log.error(ERROR_INSERT_CLIENT);
            throw new ServiceException(ERROR_INSERT_CLIENT);
        }
        return client;
    }

    public Client update(Long id, Client obj) {
        try {

            Client client = clientsRepository.getOne(id);
            updateData(client, obj);
            return clientsRepository.save(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Client client, Client obj) {

        client.setName(obj.getName());
        client.setBirth(obj.getBirth());
        client.setEmail(obj.getEmail());
        client.setCell(obj.getCell());
        client.setGender(obj.getGender());
        client.setAddress(obj.getAddress());
        client.setLegalperson(obj.getLegalperson());
        client.setPhysicalperson(obj.getPhysicalperson());
    }

    private Client verifyCpfCnpj(Client client) throws ServiceException {
        Client cpfClient = new Client();
        Client cnpjClient = new Client();

        try {
            cpfClient = clientsRepository.existingCpf(client.getPhysicalperson().getCpf());
            cnpjClient = clientsRepository.existingCnpj(client.getLegalperson().getCnpj());

        } catch (Exception e) {
            throw new ServiceException(ERROR_SEARCH_CLIENT);
        }

        if (cpfClient != null) {
            log.warn(ERROR_CLIENT_CPF_EXSITS.replace("%s", cpfClient.getPhysicalperson().getCpf()));
            throw new EntityNotFoundException(ERROR_CLIENT_CPF_EXSITS.replace("%s", cpfClient.getPhysicalperson().getCpf()));
        }

        if (cnpjClient != null) {
            log.warn(ERROR_CLIENT_CNPJ_EXSITS.replace("%s", cnpjClient.getLegalperson().getCnpj()));
            throw new EntityNotFoundException(ERROR_CLIENT_CNPJ_EXSITS.replace("%s", cnpjClient.getLegalperson().getCnpj()));
        }

        if (client.getPhysicalperson().getCpf() == null) {
            client.getPhysicalperson().setCpf("NÃO INFORMADO");

        } else if (client.getLegalperson().getCnpj() == null) {
            client.getLegalperson().setCnpj("NÃO INFORMADO");
        }
        return client;
    }
}
