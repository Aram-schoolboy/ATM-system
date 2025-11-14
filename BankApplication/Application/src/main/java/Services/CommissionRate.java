package Services;

import java.math.BigDecimal;

/**
 * Процент комиссии в зависимости от типа получателя.
 */
public enum CommissionRate {
    /** Владелец счёта — комиссия отсутствует */
    OWNER(BigDecimal.ZERO),
    /** Друг — комиссия 3% */
    FRIEND(new BigDecimal("0.03")),
    /** Другие — комиссия 5% */
    OTHER(new BigDecimal("0.05"));

    /** Значение комиссии */
    private final BigDecimal rate;

    CommissionRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * Применяет комиссию к указанной сумме.
     *
     * @param amount сумма, к которой применяется комиссия
     * @return сумма комиссии
     */
    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(rate);
    }
}
