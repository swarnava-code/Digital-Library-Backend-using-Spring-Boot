package com.sclab.library.config.apikey;

import java.io.Serializable;

public record ApiEntityDTO(String keyValue, String userType, String roleId) implements Serializable { }
