package com.springboot.ECommerce.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.CustomerRepository;


@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    public Customer registerCustomer(Customer customer) {
        User user = customer.getUser();

        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            user = userFromDb;
        } else {
            user.setRole(USER_ROLE.ROLE_CUSTOMER);
            user = userService.signUp(user);
        }

        customer.setUser(user);

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    //update customer without profile pic
    
//    public Customer updateCustomer(int id, Customer updatedCustomer) {
//        Customer dbCustomer = customerRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Invalid Customer ID"));
//
//        if (updatedCustomer.getName() != null)
//            dbCustomer.setName(updatedCustomer.getName());
//
//        if (updatedCustomer.getEmail() != null)
//            dbCustomer.setEmail(updatedCustomer.getEmail());
//
//        if (updatedCustomer.getPhone() != null)
//            dbCustomer.setPhone(updatedCustomer.getPhone());
//
//        return customerRepository.save(dbCustomer);
//    }
    

	public Customer getCustomerByUsername(String username) {
		
		
		return customerRepository.getCustomerByUsername(username);
	}
	
	// update customer with profile
	
	public Customer updateCustomer(int id, String name, String email, String phone, MultipartFile file) throws IOException {
	    Customer dbCustomer = customerRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Invalid Customer ID Provided"));

	    if (name != null) dbCustomer.setName(name);
	    if (email != null) dbCustomer.setEmail(email);
	    if (phone != null) dbCustomer.setPhone(phone);

	    if (file != null && !file.isEmpty()) {
	        String originalFileName = file.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

	        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "svg");
	        if (!allowedExtensions.contains(extension)) {
	            throw new RuntimeException("Invalid file extension: " + extension);
	        }

	        long kbs = file.getSize() / 1024;
	        if (kbs > 3000) {
	            throw new RuntimeException("Image too large. Max size allowed is 3MB");
	        }

	        String uploadFolder = "D:\\VsCode\\REACT\\Hexaware-FSD\\EcomFrontend\\public\\images";
	        Files.createDirectories(Path.of(uploadFolder));
	        Path path = Paths.get(uploadFolder, originalFileName);
	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	        dbCustomer.setProfilePic(originalFileName);
	    }

	    return customerRepository.save(dbCustomer);
	}

	
	//upload profile pic
	
	public Customer uploadCustomerProfilePic(MultipartFile file, String username) throws IOException {
	    Customer customer = customerRepository.getCustomerByUsername(username);

	    if (customer == null) {
	        throw new RuntimeException("Customer not found");
	    }

	    String originalFileName = file.getOriginalFilename();
	    String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

	    if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension)) {
	        throw new RuntimeException("Invalid f	ile type");
	    }

	    long kbs = file.getSize() / 1024;
	    if (kbs > 3000) {
	        throw new RuntimeException("File size exceeds limit of 3000KB");
	    }

	    String uploadFolder = "D:\\VsCode\\REACT\\Hexaware-FSD\\EcomFrontend\\public\\images";
	    Files.createDirectories(Path.of(uploadFolder));
	    Path path = Paths.get(uploadFolder, originalFileName);
	    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	    customer.setProfilePic(originalFileName);
	    return customerRepository.save(customer);
	}

	
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
