package Models;

import jakarta.persistence.*;

/**
 * Сущность, описывающая дружбу между двумя пользователями.
 */
@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false, referencedColumnName = "user_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false, referencedColumnName = "user_id")
    private User user2;


    /**
     * Создаёт дружбу между двумя пользователями.
     */
    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friendship() {}

    public Long getId() { return id; }

    public User getUser1() { return user1; }

    public void setUser1(User user1) { this.user1 = user1; }

    public User getUser2() { return user2; }

    public void setUser2(User user2) { this.user2 = user2; }
}
