package br.com.flow_api.flow_api;

import br.com.flow_api.client.*;
import br.com.flow_api.exceptions.ResourceNotFoundException;
import br.com.flow_api.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class ClientTests {

    @Mock
    private ClientRepository clientsRepository;
    @InjectMocks
    private ClientService clientsService;
    private static final Long CLIENT_ID = 218L;
    private static final String ERROR_INSERT_CLIENTS = "Não foi possivel cadastrar este cliente";
    private static final String ERROR_CLIENT_CPF_EXSITS = "Já existe cliente cadastrado com o CPF - %s ";
    private static final String ERROR_CLIENT_CNPJ_EXSITS = "Já existe cliente cadastrado com o CNPJ - %s ";
    private static final String ERROR_SEARCH_CLIENT = "Ocorreu um erro ao tentar buscar os dados do cliente";


    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldNotReturnAllClients() {
        Mockito.when(clientsRepository.findAll(Mockito.any(Sort.class))).thenReturn(null);
        Assert.assertNull(clientsService.findAll());
    }

    @Test
    public void shouldReturnAllClients() {
        List<Client> clientsList = Arrays.asList(new Client());
        Mockito.when(clientsRepository.findAll(Mockito.any(Sort.class))).thenReturn(clientsList);
        clientsService.findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notFoundIdExceptionClientFindById() {
        Client clients = new Client();
        Mockito.when(clientsRepository.findById(clients.getId()))
                .thenThrow(new ResourceNotFoundException("Recurso não encontrado com o Id" + CLIENT_ID));
        Assert.assertNotNull(clientsService.findById(CLIENT_ID));
    }

    @Test(expected = ServiceException.class)
    public void clientIdShoudBeNullOrZeroWhenInserting() throws ServiceException {
        Client clients = new Client();
        clients.setId(null);
        Mockito.when(clientsRepository.save(Mockito.any())).thenReturn(ERROR_INSERT_CLIENTS);
        Assert.assertNull(clientsService.insert(clients));
    }

    @Test(expected = ServiceException.class)
    public void shouldVerifyCpfExist() throws ServiceException {
        Client clients = new Client();
        ClientPhysicalperson physicalperson = new ClientPhysicalperson();
        physicalperson.setCpf("793.623.296-62");
        clients.setPhysicalperson(clients.getPhysicalperson());

        Mockito.when(clientsRepository.existingCpf(Mockito.anyString())).thenThrow(new MockitoException(
                ERROR_CLIENT_CPF_EXSITS.replace("%s", physicalperson.getCpf())));

        Assert.assertNotNull(clientsService.insert(clients));
    }

    @Test(expected = ServiceException.class)
    public void shouldVerifyCnpjExist() throws ServiceException {
        Client clients = new Client();
        ClientLegalperson legalperson = new ClientLegalperson();
        legalperson.setCnpj("78.264.046/0001-05");
        clients.setLegalperson(clients.getLegalperson());

        Mockito.when(clientsRepository.existingCnpj(Mockito.anyString())).thenThrow(new MockitoException(
                ERROR_CLIENT_CNPJ_EXSITS.replace("%s", legalperson.getCnpj())));

        Assert.assertNotNull(clientsService.insert(clients));
    }

    @Test(expected = ServiceException.class)
    public void shouldSaveClientsSearchException() throws ServiceException {
        Client clients = new Client();
        Mockito.when(clientsRepository.save(Mockito.any())).thenThrow(new MockitoException(ERROR_SEARCH_CLIENT));
        clientsService.insert(clients);
    }

    @Test(expected = ServiceException.class)
    public void shouldSaveClientsErrorInsertException() throws ServiceException {
        Client clients = new Client();
        Mockito.when(clientsRepository.save(Mockito.any())).thenThrow(new MockitoException(ERROR_INSERT_CLIENTS));
        clientsService.insert(clients);
    }

    @Test(expected = ServiceException.class)
    public void shouldSaveClientsSuccess() throws ServiceException {
        Client clients = new Client();
        ClientLegalperson legalperson = new ClientLegalperson();

        clients.setId(CLIENT_ID);
        legalperson.setCnpj("78.264.046/0001-05");
        clients.setLegalperson(clients.getLegalperson());

        Mockito.when(clientsRepository.save(Mockito.any())).thenReturn(clients);

        clientsService.insert(clients);
    }
}
