package com.everisbootcamp.createaccount.Constant;

public enum Constants {
    ;

    public static class Path {

        private static final String IPR = "localhost";
        private static final String PORT = "9500";

        private static final String SERVER_PATH = "/server";
        private static final String MS_PATH = "/microservices";

        private static final String HTTP_CONSTANT = "http://";
        private static final String GATEWAY = IPR.concat(":").concat(PORT);

        public static final String LOGIC_PATH = HTTP_CONSTANT
            .concat(GATEWAY)
            .concat(SERVER_PATH)
            .concat("/logic");

        public static final String CUSTOMERS_PATH = HTTP_CONSTANT
            .concat(GATEWAY)
            .concat(MS_PATH)
            .concat("/customer");
    }
}
