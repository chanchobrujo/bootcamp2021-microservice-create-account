package com.everisbootcamp.createaccount.Constant.Paths;

import com.everisbootcamp.createaccount.Constant.Constan;

public enum Path {
    ;

    public static class URLS {

        private static final String IPR = "localhost";
        private static final String PORT = "9500";

        private static final String SERVER_PATH = Constan.SLASH.concat("server");
        private static final String MS_PATH = Constan.SLASH.concat("microservices");

        private static final String HTTP_CONSTANT =
            "http".concat(Constan.DOUBLE_POINT).concat(Constan.DOUBLE_SLASH);
        private static final String GATEWAY = IPR.concat(Constan.DOUBLE_POINT).concat(PORT);

        private static final String SERVICE_CALL(String value) {
            return HTTP_CONSTANT.concat(GATEWAY).concat(value).concat(Constan.SLASH);
        }

        public static final String LOGIC_PATH = SERVICE_CALL(SERVER_PATH)
            .concat("logic")
            .concat(Constan.SLASH);

        public static final String CUSTOMERS_PATH = SERVICE_CALL(MS_PATH)
            .concat("customer")
            .concat(Constan.SLASH);
    }
}
