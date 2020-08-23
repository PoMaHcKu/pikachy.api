package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.AdditionalCredentials;
import by.itra.pikachy.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AdditionalCredentialsRepository extends JpaRepository<AdditionalCredentials, Integer> {
    AdditionalCredentials findByUserAndUserAgent(User user, String userAgent);

    List<AdditionalCredentials> findAllByExpirationBefore(Date expiration);
}