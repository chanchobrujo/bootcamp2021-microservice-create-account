package com.everisbootcamp.createaccount.Data;

import com.everisbootcamp.createaccount.Web.Consumer;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "accounts")
public class Account {

    @Id
    private String idaccount;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datecreated = LocalDateTime.now(ZoneId.of("America/Lima"));

    private String numberaccount = Consumer.webClientLogic.get().uri("/generatednumber/12").retrieve().bodyToMono(String.class).block();

    private String idcustomer;
    private String typeaccount;
    private String profile;
    private Double amount;

    private Rules rules;

    public Account(String idcustomer, String typeaccount, String profile, Double amount) {
        this.idcustomer = idcustomer;
        this.typeaccount = typeaccount;
        this.profile = profile;
        this.amount = amount;
    }
}
