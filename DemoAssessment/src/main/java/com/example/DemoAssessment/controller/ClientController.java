package com.example.DemoAssessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DemoAssessment.model.Client;
import com.example.DemoAssessment.repository.ClientRepository;
import com.example.DemoAssessment.exception.ResourceNotFoundException;


@RestController
@RequestMapping("/api/v1/client")
public class ClientController {	
			
	@Autowired
    private ClientRepository clientRepository;
	
	@GetMapping(path="/getallclients")
	public List<Client> getAllClients(){
		return clientRepository.findAll();
	}
	
	@GetMapping(path="/getallclientsbypagination")
	public Page<Client> getAllClientsByPagination(@RequestParam Optional<Integer> page){
		return clientRepository.findAll(PageRequest.of(page.orElse(0), 10));		
	}	
	
	@GetMapping(path = "/getclientbyid/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId)
        throws ResourceNotFoundException {
		
    	Client client = clientRepository.findById(clientId)
          .orElseThrow(() -> new ResourceNotFoundException("Unable to find client on this id : " + clientId));
        return ResponseEntity.ok().body(client);
    }
    
    @PostMapping
    public Client addClient(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Client> updateClientById(@PathVariable(value = "id") Long clientId,
         @Valid @RequestBody Client selectedClient) throws ResourceNotFoundException {
    	
        Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new ResourceNotFoundException("Unable to find client on this id : " + clientId));
        
        client.setName(selectedClient.getName());	                       
        
        if(selectedClient.getNewIC() != null) {
        	client.setNewIC(selectedClient.getNewIC());
        }
        
        if(selectedClient.getEmail() != null) {
        	client.setEmail(selectedClient.getEmail());
        }
               
        final Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping(path = "{id}")
    public Map<String, Boolean> deleteClientById(@PathVariable(value = "id") Long clientId)
         throws ResourceNotFoundException {
    	
        Client client = clientRepository.findById(clientId)
       .orElseThrow(() -> new ResourceNotFoundException("Unable to find client on this id : " + clientId));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Client with ID " + clientId + " has successfully been deleted ? ", Boolean.TRUE);
        return response;
    }
	
	
}
