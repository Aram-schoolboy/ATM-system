package com.mybank.app.DTO;

import java.math.BigDecimal;

public record AccountDTO(Long id, BigDecimal balance, UserDTO owner) {
}
