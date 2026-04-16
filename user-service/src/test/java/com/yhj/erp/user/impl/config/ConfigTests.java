package com.yhj.erp.user.impl.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for config classes.
 */
class ConfigTests {

    @Test
    void userServiceConfigTest() {
        UserServiceConfig config = new UserServiceConfig();
        assertNotNull(config);
    }
}