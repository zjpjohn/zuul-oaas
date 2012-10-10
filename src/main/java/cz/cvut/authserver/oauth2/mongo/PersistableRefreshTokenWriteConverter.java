package cz.cvut.authserver.oauth2.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import cz.cvut.authserver.oauth2.models.PersistableRefreshToken;
import org.springframework.stereotype.Component;

import static cz.cvut.authserver.oauth2.mongo.MongoDbConstants.refresh_tokens.*;

/**
 * Converter from {@link PersistableRefreshToken} to MongoDB object.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
@Component
public class PersistableRefreshTokenWriteConverter extends AutoRegisteredConverter<PersistableRefreshToken, DBObject> {

    public DBObject convert(PersistableRefreshToken source) {
        DBObject target = new BasicDBObject();

        target.put(TOKEN_ID, source.getValue());
        if (source.getExpiration() != null) {
            target.put(EXPIRATION, source.getExpiration());
        }

        target.put(AUTHENTICATION, new OAuth2AuthenticationWriteConverter().convert(source.getAuthentication()));

        return target;
    }
}
