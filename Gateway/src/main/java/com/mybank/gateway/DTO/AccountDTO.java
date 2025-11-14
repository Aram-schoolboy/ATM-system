package com.mybank.gateway.DTO;

import java.math.BigDecimal;

public record AccountDTO(Long id, BigDecimal balance, UserDTO owner) {
}
