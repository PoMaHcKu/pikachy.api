package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.AdditionalCredentials;
import by.itra.pikachy.api.repository.AdditionalCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditionalCredentialsService {

    private final AdditionalCredentialsRepository credentialsRepository;

    public void remove(AdditionalCredentials credentials) {
        credentialsRepository.delete(credentials);
    }

    public AdditionalCredentials save(AdditionalCredentials credentials) {
        AdditionalCredentials fromDb = credentialsRepository
                .findByUserAndUserAgent(credentials.getUser(), credentials.getUserAgent());
        if (fromDb == null) {
            return credentialsRepository.save(credentials);
        }
        fromDb.setExpiration(credentials.getExpiration());
        return credentialsRepository.save(fromDb);
    }

    public List<AdditionalCredentials> getExpiredCredentials(Date expiration) {
        return credentialsRepository.findAllByExpirationBefore(expiration);
    }
}