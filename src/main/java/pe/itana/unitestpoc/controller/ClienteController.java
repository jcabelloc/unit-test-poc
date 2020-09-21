package pe.itana.unitestpoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.itana.unitestpoc.entity.Cliente;
import pe.itana.unitestpoc.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@GetMapping
	ResponseEntity<List<Cliente>> getClientes() {
		
		return new ResponseEntity<List<Cliente>>(clienteService.findAll(), HttpStatus.OK);
		
	}
}
