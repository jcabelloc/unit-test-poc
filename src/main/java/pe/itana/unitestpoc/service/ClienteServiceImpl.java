package pe.itana.unitestpoc.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import pe.itana.unitestpoc.dto.ClienteDto;
import pe.itana.unitestpoc.entity.Cliente;
import pe.itana.unitestpoc.repository.ClienteRepository;
import pe.itana.unitestpoc.utils.ZytrustException;


@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public Cliente findById(Integer id) {
		logger.info("Obteniendo cliente con id {}", id);
		Optional<Cliente> opt = clienteRepository.findById(id);
		Cliente cliente = null;
		if (opt.isPresent()) {
			cliente = opt.get();
		}
		return cliente;
	}
	
	@Override
	public Cliente create(Cliente cliente) {
		logger.info("Creando al cliente {}", cliente);
		if (cliente.getCodCliente() != null) {
			throw new IllegalArgumentException();
		}
		
		Cliente cli = new Cliente(cliente.getNroDocumento());
		if (clienteRepository.exists(Example.of(cli))) {
			throw new DuplicateKeyException("");
		}

		cliente = clienteRepository.save(cliente);

		return cliente;
	}
	
	@Override
	public Cliente update(Cliente cliente) {
		logger.info("Actualizando al cliente {}", cliente);

		if (!clienteRepository.existsById(cliente.getCodCliente())) {
			throw new ZytrustException("999");
		}
		
		Cliente cli = new Cliente(cliente.getNroDocumento());
		Example<Cliente> exampleCli = Example.of(cli);
		clienteRepository.findAll(exampleCli).
			forEach( e -> {
				if (!e.getCodCliente().equals(cliente.getCodCliente())) {
					throw new DuplicateKeyException("");
				}
			});
		return clienteRepository.save(cliente);
	}
	
	@Override
	public List<Cliente> findAll() {
		logger.info("Obteniendo todos los clientes");

		return clienteRepository.findAll();
	}

  @Override
  public List<ClienteDto> findByNombreStartingWith(String primerNombre) {
    return clienteRepository.findByNombreStartingWith(primerNombre);
  }
	
	
	
}
