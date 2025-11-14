package Services;

import Contracts.Exceptions.UserNotFoundByLoginException;
import Contracts.UserFriendshipService;
import Models.User;
import Producers.AccountEventProducer;
import Repositories.AccountRepository;
import Repositories.TransactionRepository;
import Repositories.UserFriendshipRepository;
import Contracts.AccountService;
import Contracts.Exceptions.AccountLoginFailureException;
import Contracts.Exceptions.AccountNotFoundException;
import Contracts.Exceptions.NotEnoughBalanceException;
import Models.Account;
import Models.Transaction;
import Models.TransactionType;
import Repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountEventProducer accountEventProducer;
    private Account currentAccount;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserFriendshipService userFriendshipService;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, UserFriendshipService userFriendshipService, UserRepository userRepository, AccountEventProducer accountEventProducer) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userFriendshipService = userFriendshipService;
        this.userRepository = userRepository;
        this.accountEventProducer = accountEventProducer;
    }

    @Override
    public Account addAccount(String ownerLogin) {
        if (!userRepository.existsByLogin(ownerLogin)) {
            throw new UserNotFoundByLoginException(ownerLogin);
        }
        Account account = new Account(userRepository.findByLogin(ownerLogin));
        accountRepository.save(account);
        accountEventProducer.ProduceAccountCreationMessage(account);
        return account;
    }

    @Override
    public void selectAccount(long id, User user) {
        Account accountToLogIn = accountRepository.findById(id)
                .orElseThrow(() -> new AccountLoginFailureException(id));;
        if (!accountToLogIn.getOwner().getLogin().equals(user.getLogin())) {
            throw new AccountLoginFailureException(id);
        }
        currentAccount = accountToLogIn;
    }

    @Override
    public void selectAccount(long id) {
        Account accountToLogIn = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        currentAccount = accountToLogIn;
    }

    @Override
    public BigDecimal getBalance() {
        return currentAccount.getBalance();
    }

    @Override
    public User getOwner() {
        return currentAccount.getOwner();
    }

    @Override
    public void deposit(BigDecimal amount) {
        transactionRepository.save(new Transaction(LocalDateTime.now(), currentAccount, currentAccount, amount, TransactionType.DEPOSIT));
        currentAccount.setBalance(currentAccount.getBalance().add(amount));
        accountRepository.save(currentAccount);
        accountEventProducer.ProduceAccountBalanceChangedMessage(currentAccount.getId(), currentAccount.getBalance());
    }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal newBalance = currentAccount.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughBalanceException(Long.toString(currentAccount.getId()));
        }
        transactionRepository.save(new Transaction(LocalDateTime.now(), currentAccount, currentAccount, amount, TransactionType.WITHDRAW));
        currentAccount.setBalance(newBalance);
        accountRepository.save(currentAccount);
        accountEventProducer.ProduceAccountBalanceChangedMessage(currentAccount.getId(), currentAccount.getBalance());
    }

    @Override
    public void transfer(long id, BigDecimal amount) {
        Account receiverAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        BigDecimal commission = calculateCommission(currentAccount, receiverAccount, amount);
        BigDecimal amountWithCommission = amount.add(commission);
        BigDecimal newBalance = currentAccount.getBalance().subtract(amountWithCommission);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughBalanceException(Long.toString(currentAccount.getId()));
        }
        transactionRepository.save(new Transaction(LocalDateTime.now(), currentAccount, receiverAccount, amount, TransactionType.TRANSFER));
        currentAccount.setBalance(newBalance);
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
        accountRepository.save(currentAccount);
        accountRepository.save(receiverAccount);

        accountEventProducer.ProduceAccountBalanceChangedMessage(currentAccount.getId(), currentAccount.getBalance());
        accountEventProducer.ProduceAccountBalanceChangedMessage(receiverAccount.getId(), receiverAccount.getBalance());
    }

    @Override
    public List<Account> getUserAccountsByLogin(String login) {
        if (!userRepository.existsByLogin(login)) {
            throw new UserNotFoundByLoginException(login);
        }
        return accountRepository.findByOwnerLogin(login);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findAllByTransactionType(type);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw  new AccountNotFoundException(accountId);
        }
        Account account = accountRepository.findById(accountId).orElseThrow();
        return transactionRepository.findAllBySenderAccountOrReceiverAccount(account, account);
    }

    private BigDecimal calculateCommission(Account account1, Account account2, BigDecimal amount) {
        User owner1 = account1.getOwner();
        User owner2 = account2.getOwner();
        if (owner1.equals(owner2)) {
            return CommissionRate.OWNER.apply(amount);
        }
        if (userFriendshipService.friendshipExists(owner1, owner2)) {
            return CommissionRate.FRIEND.apply(amount);
        }
        return CommissionRate.OTHER.apply(amount);
    }
}