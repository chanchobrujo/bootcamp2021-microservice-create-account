package com.everisbootcamp.createaccount.Constant;

import java.util.Arrays;
import java.util.List;

public enum Constants {
    ;

    public static class Messages {

        public static final String CORRECT_DATA = "Datos registrados correctamente.";
        public static final String INCORRECT_DATA = "Datos incorrectos.";
        public static final String INVALID_DATA = "Datos inválidos.";
        public static final String REPET_DATA = "Datos repetidos.";

        public static final String CLIENT_NOT_FOUND = "Cliente no econtrado.";
        public static final String CLIENT_DELETED_SUCCESS = "Datos eliminados correctamente.";

        public static final String CLIENT_ACCOUNT_DENIED = "Usted ya no puede tener mas cuentas de este tipo.";
    }

    public static class Path {

        private static final String IPR = "localhost";
        private static final String PORT = "9500";

        private static final String SERVER_PATH = "/server";
        private static final String MS_PATH = "/microservices";

        private static final String HTTP_CONSTANT = "http://";
        private static final String GATEWAY = IPR.concat(":").concat(PORT);

        public static final String LOGIC_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(SERVER_PATH).concat("/logic");

        public static final String CUSTOMERS_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(MS_PATH).concat("/customer");
    }

    public static List<String> TYPE_ACCOUNT = Arrays.asList(
        "Cuenta plazo fijo",
        "Cuenta de ahorro",
        "Cuenta corriente"
    );
    public static List<String> PROFILE = Arrays.asList("VIP", "PYME");
}
