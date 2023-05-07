package com.example.springbootwebfluxdemo.handler;

import com.example.springbootwebfluxdemo.dao.CustomerDao;
import com.example.springbootwebfluxdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Month;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao customerDao;


    public Mono<ServerResponse> loadCustomers(ServerRequest request){
        Flux<Customer> customerList = customerDao.getCustomerList();
        return ServerResponse.ok().body(customerList,Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int customerId = Integer.valueOf(request.pathVariable("input"));
        Mono<Customer> customer = customerDao.getCustomerList().filter(c -> c.getId()==customerId).take(1).single();
        return ServerResponse.ok().body(customer,Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId()+":"+dto.getName());
        return ServerResponse.ok().body(saveResponse,String.class);
    }


}
