package com.everisbootcamp.createaccount.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerModel {

    private String idcustomer;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateafiliation;

    private String namecustomer;
    private String lastnamecustomer;

    private String documentType;
    private String numberdocument;
    private String numberphone;
    private String emailaddress;

    private String typecustomer;
}
