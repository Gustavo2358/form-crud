package com.example.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/")
    public String redirectToClientsPage() {
        return "redirect:/clientes";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("cliente", new Cliente());
        return "form";
    }

//    @PostMapping("/")
//    public String submit(Cliente cliente, Model model){
//        clienteRepository.save(cliente);
//        model.addAttribute("message", "Client saved successfully!");
//        return"form";
//    }

    /*redirectAttributes is an object of the class RedirectAttributes in Spring MVC.
     This object is used to store attributes to be exposed to the next request in case of a redirect.
      When you use 'redirect:/' as the return value of a controller method,
      the request is redirected to the specified URL and any attributes added to the redirectAttributes object
      are exposed to the next request.
     These attributes persist only for a single request and can be used to transfer data from one request to another.*/
    @PostMapping("/form")
    public String submit(Cliente cliente, RedirectAttributes redirectAttributes){
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("message", "Client saved successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/clientes";
    }

    @GetMapping("/clientes")
    public String getAllClients(Model model) {
        List<Cliente> clients = clienteRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        clienteRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Client deleted successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/clientes";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Cliente client = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid client id:" + id));
        model.addAttribute("cliente", client);
        return "update-client";
    }

    @PostMapping("/update")
    public String updateClient(Cliente client, RedirectAttributes redirectAttributes) {
        clienteRepository.save(client);
        redirectAttributes.addFlashAttribute("message", "Client updated successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/clientes";
    }
}
