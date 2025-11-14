package com.mybank.storage.Repositories;

import com.mybank.storage.Messages.UserCreationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreationRepository extends JpaRepository<UserCreationMessage, Long> {
}
