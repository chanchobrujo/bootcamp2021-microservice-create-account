package com.everisbootcamp.createaccount.Constant.Enums;

import lombok.Getter;

@Getter
public enum MessagesSuccess {
    SUCCESS_REGISTER(201, "Datos registrados correctamente."),
    UPDATE_DATA(201, "Datos actualizados correctamente."),
    DELETED_DATA(200, "Datos eliminados correctamente.");

    private Integer cod;
    private String messages;

    private MessagesSuccess(Integer cod, String messages) {
        this.cod = cod;
        this.messages = messages;
    }
}
