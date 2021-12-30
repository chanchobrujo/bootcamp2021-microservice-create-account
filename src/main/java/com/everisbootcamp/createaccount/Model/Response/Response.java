package com.everisbootcamp.createaccount.Model.Response;

import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesError;
import com.everisbootcamp.createaccount.Constant.Enums.Messages.MessagesSuccess;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("America/Lima"));

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public Response(MessagesSuccess message) {
        this.message = message.getMessages();
        this.status = HttpStatus.valueOf(message.getCod());
    }

    public Response(MessagesError message) {
        this.message = message.getMessages();
        this.status = HttpStatus.valueOf(message.getCod());
    }

    public Map<String, Object> getResponse() {
        Map<String, Object> response = new HashMap<>();

        response.put("message", this.getMessage());
        response.put("timestamp", this.getTimestamp());

        return response;
    }
}
