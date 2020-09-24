package pe.itana.unitestpoc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import pe.itana.unitestpoc.entity.Cliente;
import pe.itana.unitestpoc.entity.Cliente.TipoDocumento;
import pe.itana.unitestpoc.repository.ClienteRepository;
import static org.mockito.ArgumentMatchers.argThat;


//@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class ClienteServiceImplTest {
	
	@Mock
	ClienteRepository clienteRepository;
	
	@InjectMocks
	ClienteService clienteService = new ClienteServiceImpl();
	
	Pageable pageable = PageRequest.of(0, 20);
	static List<Cliente> clientes;
	static Cliente clientePPNN;
	static Cliente clientePPJJ;
	static Cliente clienteNuevo;


	@BeforeAll
	public static void setupBeforeAll() {
		clientes = new ArrayList<>();;
		clientePPNN = new Cliente(1001, "Juan Perez", TipoDocumento.DNI, "15248695");
		clientePPJJ = new Cliente(1002, "Financiera Qapac", TipoDocumento.RUC, "20245168595");
		clientes.add(clientePPNN);
		clientes.add(clientePPJJ);
		
		clienteNuevo = new Cliente(null, "Maria Aguirre", TipoDocumento.DNI, "68957584");
		
	}
	
	@BeforeEach
	public void setupBeforeEach() {

		
		when(clienteRepository.findAll(pageable)).thenReturn(new PageImpl<Cliente>(clientes));
		when(clienteRepository.findAll()).thenReturn(clientes);

		when(clienteRepository.findById(1001)).thenReturn(Optional.of(clientePPNN));
		when(clienteRepository.findById(1002)).thenReturn(Optional.of(clientePPJJ));
		when(clienteRepository.findById(1003)).thenThrow(new NoSuchElementException());
		when(clienteRepository.existsById(1001)).thenReturn(true);
		when(clienteRepository.existsById(1002)).thenReturn(true);
		when(clienteRepository.existsById(1003)).thenThrow(new NoSuchElementException());
		when(clienteRepository.findAll(Example.of(new Cliente("20245168595")))).thenReturn(Arrays.asList(clientePPJJ));
		
		ArgumentMatcher<Example<Cliente>> es20245168595 = example -> example.getProbe()
				.getNroDocumento().equals("20245168595");
		when(clienteRepository.findAll(argThat(es20245168595))).thenReturn(Arrays.asList(clientePPJJ));
		
		when(clienteRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
		when(clienteRepository.save(argThat(cliente -> cliente.getCodCliente() == null)))
			.thenReturn(clienteNuevo.withCodCliente(1003));
		
		ArgumentMatcher<Example<Cliente>> es15248695 = example -> example.getProbe()
				.getNroDocumento().equals("15248695");
		when(clienteRepository.exists(argThat(es15248695))).thenReturn(true);
	}

	
	// ------------ findAll---------------------------------------
	@Test
	// Should_ObtenerClientes_When_SeEncuentraTodosClientes
	public void findAll_Should_ObtenerClientes() {
		List<Cliente> clientes = clienteService.findAll();
		assertNotNull(clientes);
		assertEquals(2, clientes.size());
		verify(clienteRepository, times(1)).findAll();
	}
	
	
	
	// ------------ findById--------------------------------------
	@Test
	// Should_ObtenerCliente_When_SeEncuentraCliente
	public void findById_Should_ObtenerCliente() {
		Cliente cliente = clienteService.findById(1001);
		assertNotNull(cliente);
		assertEquals(1001, cliente.getCodCliente());
		verify(clienteRepository, times(1)).findById(anyInt());
	}
	
	@Test
	// Should_LanzarExcepcion_When_NoExisteClienteById
	public void findById_Should_LanzarExcepcion_When_NoExisteCliente() {
		assertThrows(NoSuchElementException.class, () -> clienteService.findById(1003));
		verify(clienteRepository, times(1)).findById(anyInt());
	}
	
	// ------------ update--------------------------------------
	
	@Test
	// Should_RetornarCliente_When_ClienteEsActualizado
	public void update_Should_RetornarCliente() {
		Cliente cliente = new Cliente(1001, "Juan Perez Suarez", TipoDocumento.DNI, "15248695");
		assertEquals(1001, clienteService.update(cliente).getCodCliente());
		verify(clienteRepository, times(1)).existsById(anyInt());
	}
	
	@Test
	//Should_TirarException_When_NoExisteClienteByIdAlActualizar
	public void update_Should_LanzarException_When_NoExisteCliente() {
		Cliente cliente = new Cliente();
		cliente.setCodCliente(1003);
		assertThrows(NoSuchElementException.class, () -> clienteService.update(cliente));
		verify(clienteRepository, times(1)).existsById(anyInt());
	}
	
    @Captor
    private ArgumentCaptor<Example<Cliente>> argument;
    
	@Test
	// Should_TirarException_When_NroDocumentoDuplicadoAlActualizar
	public void update_Should_LanzarException_When_NroDocumentoEsDuplicado() {
		Cliente cliente = new Cliente(1001, "Juan Perez", TipoDocumento.DNI, "20245168595");
		
		assertThrows(DuplicateKeyException.class, () -> clienteService.update(cliente));
		
		verify(clienteRepository, times(1)).findAll(argument.capture());
		assertEquals("20245168595", argument.getValue().getProbe().getNroDocumento());
		
	}

	// ------------ create--------------------------------------
	
	@Test
	// Should_RetornarCliente_When_ClienteEsCreado
	public void create_Should_RetornarCliente() {
		Cliente cliente = new Cliente(null, "Maria Aguirre", TipoDocumento.DNI, "68957584");
		assertNotNull(clienteService.create(cliente).getCodCliente());;
		verify(clienteRepository, times(1)).exists(argument.capture());
	}

	@Test
	// Should_TirarException_When_ExisteCodClienteAlCrear
	public void create_Should_LanzarException_When_ExisteCodCliente() {
		Cliente cliente = new Cliente(1003, "Maria Aguirre", TipoDocumento.DNI, "68957584");
		assertThrows(IllegalArgumentException.class, () -> clienteService.create(cliente));
		verify(clienteRepository, never()).existsById(anyInt());
	}
	
    
	@Test
	// Should_TirarException_When_NroDocumentoDuplicadoAlCrear
	public void create_Should_LanzarException_When_NroDocumentoEsDuplicado() {
		Cliente cliente = new Cliente(null, "Maria Aguirre", TipoDocumento.DNI, "15248695");
		
		assertThrows(DuplicateKeyException.class, () -> clienteService.create(cliente));
		
		verify(clienteRepository, times(1)).exists(argument.capture());
		
	}
	
}
