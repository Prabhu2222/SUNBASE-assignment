package com.prabhu.customermanagementportal.service;

import com.prabhu.customermanagementportal.dto.request.CustomerRequestDto;
import com.prabhu.customermanagementportal.dto.response.CustomerResponseDto;
import com.prabhu.customermanagementportal.entity.Customer;
import com.prabhu.customermanagementportal.exception.EmailAlreadyExistException;
import com.prabhu.customermanagementportal.exception.EmailUpdationException;
import com.prabhu.customermanagementportal.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Value("${custom.integer.dataPerPage:4}")
    private int defaultPageSize;

    @Autowired
    RestTemplate restTemplate;
    public String createCustomer(CustomerRequestDto customerRequestDto) {
        Optional<Customer> optionalCustomer=customerRepository.findByEmail(customerRequestDto.getEmail());
        if(optionalCustomer.isPresent()) throw new EmailAlreadyExistException("email id already exists!!! try using different email id.");
        //dto to entity conversion
        Customer customer=Customer.builder()
                .uuid(String.valueOf(UUID.randomUUID()))
                .first_name(customerRequestDto.getFirst_name().toLowerCase())
                .last_name(customerRequestDto.getLast_name().toLowerCase())
                .street(customerRequestDto.getStreet().toLowerCase())
                .address(customerRequestDto.getAddress().toLowerCase())
                .email(customerRequestDto.getEmail().toLowerCase())
                .phone(customerRequestDto.getPhone().toLowerCase())
                .state(customerRequestDto.getState().toLowerCase())
                .city(customerRequestDto.getCity().toLowerCase())
                .build();
       customerRepository.save(customer);

       return "successfully created";
    }

    public String updateCustomer(CustomerRequestDto customerRequestDto) {
        //find customer by mail from database since email is unique
        Optional<Customer> optionalCustomer=customerRepository.findByEmail(customerRequestDto.getEmail());
        //if not exist create new customer else
        if(optionalCustomer.isEmpty()) {
            throw new EmailUpdationException("Error: May be email does not exist/Existing email can not be updated!!!");
        }
        //if exist update the existing data
        Customer customer=optionalCustomer.get();
        customer.setFirst_name(customerRequestDto.getFirst_name().toLowerCase());
        customer.setLast_name(customerRequestDto.getLast_name().toLowerCase());
        customer.setStreet(customerRequestDto.getStreet().toLowerCase());
        customer.setAddress(customerRequestDto.getAddress().toLowerCase());
        customer.setEmail(customerRequestDto.getEmail().toLowerCase());
        customer.setPhone(customerRequestDto.getPhone().toLowerCase());
        customer.setState(customerRequestDto.getState().toLowerCase());
        customer.setCity(customerRequestDto.getCity().toLowerCase());

        customerRepository.save(customer);

        return "successfully updated";

    }

    public String deleteCustomer(String email) {
        Optional<Customer> optionalCustomer=customerRepository.findByEmail(email);
        if(optionalCustomer.isEmpty()) return "no customer found !!!";
        Customer customer=optionalCustomer.get();
        customerRepository.delete(customer);
        return "removed customer successfully";
    }

    public CustomerResponseDto getCustomerById(String id) {
        Optional<Customer> optionalCustomer=customerRepository.findByUuid(id);
        if(optionalCustomer.isEmpty()) return null;
        Customer customer=optionalCustomer.get();
        CustomerResponseDto responseDto=CustomerResponseDto.builder()
                        .first_name(customer.getFirst_name())
                .last_name(customer.getLast_name())
                .street(customer.getStreet())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();

        return  responseDto;
    }

    public Page<Customer> getAllCustomerPageByPage(int pageNo) {
//        int defaultPageSize=4;
        Pageable pageable= PageRequest.of(pageNo,defaultPageSize);

        Page<Customer> customerPage=customerRepository.findAll(pageable);


       return customerPage;
    }

    public Page<Customer> getAllCustomerPageByName(int pageNo,String pattern) {
//        int defaultPageSize=4;
          Pageable pageable=PageRequest.of(pageNo,defaultPageSize);

           Page<Customer> ans= customerRepository.findByFirstNameContains(pattern,pageable);
        System.out.println(ans);
        return ans;
    }

    public Page<Customer> searchByPhone(int pageNo, String pattern) {
//        int defaultPageSize=4;
        Pageable pageable=PageRequest.of(pageNo,defaultPageSize);
        Page<Customer> ans=customerRepository.findByPhoneContains(pattern,pageable);
        return ans;
    }

    public Page<Customer> searchByEmail(int pageNo, String pattern) {
//        int defaultPageSize=4;
        Pageable pageable=PageRequest.of(pageNo,defaultPageSize);
        Page<Customer> ans=customerRepository.findByEmailContains(pattern,pageable);
        return ans;
    }

    public Page<Customer> searchByCity(int pageNo, String pattern) {
//        int defaultPageSize=4;
        Pageable pageable=PageRequest.of(pageNo,defaultPageSize);
        Page<Customer> ans=customerRepository.findByCityContains(pattern,pageable);
        return ans;
    }

    private boolean syncSearchCustomerByEmail(String email){
        Optional<Customer> optionalCustomer=customerRepository.findByEmail(email);
        if(optionalCustomer.isEmpty())
        {
            System.out.println("customer not found");
            return false;
        }
        return true;
    }

    private boolean syncUpdateCustomer(Customer newCustomer){
        Customer existingCustomer=customerRepository.findByEmail(newCustomer.getEmail().toLowerCase()).get();
        existingCustomer.setFirst_name(newCustomer.getFirst_name().toLowerCase());
        existingCustomer.setLast_name(newCustomer.getLast_name().toLowerCase());
        existingCustomer.setEmail(newCustomer.getEmail().toLowerCase());
        existingCustomer.setPhone(newCustomer.getPhone().toLowerCase());
        existingCustomer.setCity(newCustomer.getCity().toLowerCase());
        existingCustomer.setState(newCustomer.getState().toLowerCase());
        existingCustomer.setAddress(newCustomer.getAddress().toLowerCase());
        existingCustomer.setStreet(newCustomer.getStreet().toLowerCase());
        customerRepository.save(existingCustomer);
        return true;
    }

    private boolean syncAddCustomer(Customer customer){
        Customer customerToBeSaved=new Customer();
        customerToBeSaved.setUuid(customer.getUuid());
        customerToBeSaved.setFirst_name(customer.getFirst_name().toLowerCase());
        customerToBeSaved.setLast_name(customer.getLast_name().toLowerCase());
        customerToBeSaved.setEmail(customer.getEmail().toLowerCase());
        customerToBeSaved.setAddress(customer.getAddress().toLowerCase());
        customerToBeSaved.setStreet(customer.getStreet().toLowerCase());
        customerToBeSaved.setCity(customer.getCity().toLowerCase());
        customerToBeSaved.setState(customer.getState().toLowerCase());
        customerToBeSaved.setPhone(customer.getPhone().toLowerCase());
        customerRepository.save(customerToBeSaved);
        return true;
    }

    public String syncCustomerDatabase(List<Customer> list) {
        for(Customer ele:list){
            if(syncSearchCustomerByEmail(ele.getEmail().toLowerCase())){
               syncUpdateCustomer(ele);
            }else{
               syncAddCustomer(ele);
            }
        }

        return "successfully synced with database";
    }




}
