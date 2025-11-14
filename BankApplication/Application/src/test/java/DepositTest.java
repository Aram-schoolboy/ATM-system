import Models.Account;
import Models.Gender;
import Models.HairColor;
import Models.User;
import Producers.AccountEventProducer;
import Producers.UserEventProducer;
import Repositories.AccountRepository;
import Repositories.TransactionRepository;
import Repositories.UserFriendshipRepository;
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

class DepositTest {

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
    void depositTest() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal result = startBalance.add(amount);

        accountService.selectAccount(id, user);
        accountService.deposit(amount);

        account.setBalance(result);
        BigDecimal balance = accountService.getBalance();

        assert balance.compareTo(result) == 0;
    }
}
