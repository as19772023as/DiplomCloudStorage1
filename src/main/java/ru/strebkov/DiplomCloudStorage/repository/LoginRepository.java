package ru.strebkov.DiplomCloudStorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.DiplomCloudStorage.model.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LoginRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

}
