package com.mybank.storage.Repositories;

import com.mybank.storage.Messages.UserFriendsModificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendsModificationRepository extends JpaRepository<UserFriendsModificationMessage, Long> {
}
