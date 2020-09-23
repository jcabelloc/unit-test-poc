package pe.itana.unitestpoc.service;

import java.util.List;
import java.util.NoSuchElementException;

import pe.itana.unitestpoc.entity.Cliente;

public interface ClienteService {
	
    /**
     * Retorna al {@code Cliente} si se encuentra, sino tira la excepcion
     * {@code NoSuchElementException}.
     *
     * @return El valor no-{@code null} de tipo {@code Cliente}
     * @throws NoSuchElementException si no encuentra al {@code Cliente}
     * @since 1.0
     */
	public Cliente findById(Integer id);
	
	
    /**
     * Crea al {@code Cliente} siempre que sus propiedades sean correctas, sino tira la excepcion
     * {@code IllegalArgumentException} o {@code DuplicateKeyException}
     *
     * @return El {@code Cliente} creado
     * @throws IllegalArgumentException si el {@code id} del {@code Cliente} es no-{@code null} 
     * @throws DuplicateKeyException si el {@code nroDocumento} del {@code Cliente} ya existe 
     * @since 1.0
     */
	public Cliente create(Cliente cliente);
	
    /**
     * Actualiza al {@code Cliente} siempre que exista y el {@code nroDocumento} no genere
     * un duplicado, sino tira la excepcion
     * {@code NoSuchElementException} o {@code DuplicateKeyException}
     *
     * @return El {@code Cliente} actualizado
     * @throws NoSuchElementException si el {@code Cliente} no existe 
     * @throws DuplicateKeyException si el {@code nroDocumento} del {@code Cliente} genera un  
     * duplicado
     * @since 1.0
     */
	public Cliente update(Cliente cliente);



	List<Cliente> findAll();
}
