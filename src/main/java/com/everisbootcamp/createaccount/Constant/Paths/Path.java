package com.everisbootcamp.createaccount.Constant.Paths;

public enum Path {
    ;

    public static class URLS {

        private static final String DOUBLE_POINT = ":";
        private static final String DOUBLE_SLASH = "//";
        private static final String SLASH = "/";

        private static final String IPR = "localhost";
        private static final String PORT = "9500";

        private static final String SERVER_PATH = SLASH.concat("server");
        private static final String MS_PATH = SLASH.concat("microservices");

        private static final String HTTP_CONSTANT =
            "http".concat(DOUBLE_POINT).concat(DOUBLE_SLASH);
        private static final String GATEWAY = IPR.concat(DOUBLE_POINT).concat(PORT);

        private static final String SERVICE_CALL(String value) {
            return HTTP_CONSTANT.concat(GATEWAY).concat(value).concat(SLASH);
        }

        public static final String LOGIC_PATH = SERVICE_CALL(SERVER_PATH).concat("logic");
        public static final String CUSTOMERS_PATH = SERVICE_CALL(MS_PATH).concat("customer");
    }
}
