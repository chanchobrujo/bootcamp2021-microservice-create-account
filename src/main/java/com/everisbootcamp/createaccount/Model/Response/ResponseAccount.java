package com.everisbootcamp.createaccount.Model.Response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAccount {

    private String NumberAccount;
    private String TypeAccount;
    private String customer;
    private Double Amount;

    private Map<String, Object> Rules;

    private String DateCreated;
}
