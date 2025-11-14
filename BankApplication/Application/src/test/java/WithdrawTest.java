import Models.Account;
import Models.Gender;
import Models.HairColor;
import Models.User;
import Producers.AccountEventProducer;
import Producers.UserEventProducer;
import Repositories.AccountRepository;
import Repositories.TransactionRepository;
import Repositories.UserFriendshipRepository;
import Contracts.Exceptions.NotEnoughBalanceException;
import Services.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class WithdrawTest {

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private TransactionRepository mockTransactionRepository;

    @Mock
    private UserFriendshipRepository mockUserFriendshipRepository;

    @Mock
    private AccountEventProducer accountEventProducer;

    @Mock
    private UserEventProducer userEventProducer;

    @InjectMocks
    private AccountServiceImpl accountService;

    private final long id = 1;
    private final String login = "bob";
    private final String name = "mahachev";
    private final int age = 20;
    private final Gender gender = Gender.MALE;
    private final HairColor hairColor = HairColor.GREEN;
    private final BigDecimal startBalance = new BigDecimal("100");
    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(login, name, age, gender, hairColor);
        user.setUserId(id);

        account = new Account(user);
        account.setId(id);
        account.setBalance(startBalance);

        Mockito.when(mockAccountRepository.existsById(id)).thenReturn(true);
        Mockito.when(mockAccountRepository.findById(id)).thenReturn(Optional.of(account));
    }

    @Test
    void withdrawWhenEnoughMoneyTestShouldSucceed() {
        BigDecimal amount = new BigDecimal("10");
        BigDecimal result = startBalance.subtract(amount);

        accountService.selectAccount(id, user);
        accountService.withdraw(amount);

        account.setBalance(result);
        BigDecimal balance = accountService.getBalance();

        assert balance.compareTo(result) == 0;
    }

    @Test
    void withdrawWhenNotEnoughMoneyTestShouldThrowException() {
        BigDecimal amount = new BigDecimal("100000");
        accountService.selectAccount(id, user);
        assertThrows(NotEnoughBalanceException.class, () -> {
            accountService.withdraw(amount);
        });
    }
}
