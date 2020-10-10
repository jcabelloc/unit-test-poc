package pe.itana.unitestpoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.itana.unitestpoc.dto.ClienteDto;
import pe.itana.unitestpoc.entity.Cliente;
import pe.itana.unitestpoc.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;
  
  @GetMapping
  public ResponseEntity<List<Cliente>> getClientes() {
    return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
  }

  @GetMapping(params = "primerNombre")
  public ResponseEntity<List<ClienteDto>> findByNombreStartingWith(
      @RequestParam("primerNombre") String primerNombre) {
    return new ResponseEntity<>(clienteService.findByNombreStartingWith(primerNombre), 
        HttpStatus.OK);
  }
}
