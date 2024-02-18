package com.prabhu.customermanagementportal.controller;

import com.prabhu.customermanagementportal.dto.request.CustomerRequestDto;
import com.prabhu.customermanagementportal.dto.response.CustomerResponseDto;
import com.prabhu.customermanagementportal.entity.Customer;
import com.prabhu.customermanagementportal.responseModel.ResponseMessage;
import com.prabhu.customermanagementportal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/add")
    public ResponseEntity createCustomer(@RequestBody CustomerRequestDto customerRequestDto){
      String message=  customerService.createCustomer(customerRequestDto);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message(message)
                .build();
      return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        String message= customerService.updateCustomer(customerRequestDto);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message(message)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(value = "email") String email){
        String message=customerService.deleteCustomer(email);
        return new ResponseEntity(message,HttpStatus.ACCEPTED);
    }
    @GetMapping("/get_by_id")
    public ResponseEntity getCustomerById(@RequestParam (value = "id") String id){
        CustomerResponseDto responseDto=customerService.getCustomerById(id);
        if(responseDto==null) return new ResponseEntity<>("no such id found!!!",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseDto,HttpStatus.FOUND);
    }



    @GetMapping("/get_all")
    public ResponseEntity getAllCustomerPageByPage(@RequestParam(value="pageNo",defaultValue="0",required = false) int pageNo){
        Page<Customer> page=customerService.getAllCustomerPageByPage(pageNo);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message("success")
                .dataObject(page)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.FOUND);
    }
@GetMapping("/search_by_name")
    public ResponseEntity searchAllCustomerPageByFirstName(@RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,String pattern){
        Page<Customer>page=customerService.getAllCustomerPageByName(pageNo,pattern);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message("success")
                .dataObject(page)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.FOUND);
    }

    @GetMapping("/search_by_phone")
    public ResponseEntity searchByPhone(@RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,String pattern){
        Page<Customer>page=customerService.searchByPhone(pageNo,pattern);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message("success")
                .dataObject(page)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.FOUND);
    }
    @GetMapping("/search_by_email")
    public ResponseEntity searchByEmail(@RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,String pattern){
        Page<Customer>page=customerService.searchByEmail(pageNo,pattern);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message("success")
                .dataObject(page)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.FOUND);
    }


    @GetMapping("/search_by_city")
    public ResponseEntity searchByCity(@RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,String pattern){
        Page<Customer>page=customerService.searchByCity(pageNo,pattern);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message("success")
                .dataObject(page)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.FOUND);
    }

    @PostMapping("/sync")
    public ResponseEntity syncCustomerDatabase(@RequestBody List<Customer> list){
        String message= customerService.syncCustomerDatabase(list);
        ResponseMessage responseMessage=ResponseMessage.builder()
                .error(false)
                .message(message+"please refresh the page to see the changes..")
                .build();
        return new ResponseEntity(responseMessage,HttpStatus.OK);
    }
}
