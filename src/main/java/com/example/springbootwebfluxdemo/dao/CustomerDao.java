package com.example.springbootwebfluxdemo.dao;

import com.example.springbootwebfluxdemo.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    private  static  void sleepexecution(int i) {
        try{
        Thread.sleep(1000);}catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers(){
      return   IntStream.
                rangeClosed(1,5)
              .peek(CustomerDao::sleepexecution)
                .peek(i -> System.out.println("processing Count :"+i))
                .mapToObj(i -> new Customer(i,"customer"+i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream(){
        return   Flux.range(1,5)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("proccessing count : "+i))
                .map(i -> new Customer(i,"customer"+i));
    }

    public Flux<Customer> getCustomerList(){
        return   Flux.range(1,5)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("proccessing count : "+i))
                .map(i -> new Customer(i,"customer"+i));
    }

}
