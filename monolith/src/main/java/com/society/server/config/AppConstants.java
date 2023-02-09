package com.society.server.config;

import java.util.List;

public final class AppConstants {
    private AppConstants(){}
    public static final String PROJECT_NAME = "Society";
    public static final String API_BASE = "/api/v1";

    public static final int MINIMUM_USERNAME_LENGTH = 5;
    public static final int MAXIMUM_USERNAME_LENGTH = 20;
    public static final List<String> DO_NOT_FILTER_PATHS = List.of(
            AppConstants.API_BASE + "/auth/signin",
            AppConstants.API_BASE + "/auth/signup"
    );
    public static final List<String> CROSS_ORIGIN_DOMAINS = List.of(
            "http://localhost:3000",
            "http://127.0.0.1:80"
    );
}
