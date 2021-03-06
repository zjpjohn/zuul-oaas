/*
 * The MIT License
 *
 * Copyright 2013-2016 Czech Technical University in Prague.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cz.cvut.zuul.oaas.oauth2

import cz.cvut.zuul.oaas.models.PersistableAuthorizationCode
import cz.cvut.zuul.oaas.repos.AuthorizationCodesRepo
import groovy.util.logging.Slf4j
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices
import org.springframework.transaction.annotation.Transactional

/**
 * This class adapts {@link AuthorizationCodesRepo} to the
 * {@link org.springframework.security.oauth2.provider.code.AuthorizationCodeServices}
 * interface from the Spring Security OAuth framework.
 */
@Slf4j
class AuthorizationCodeServicesAdapter extends RandomValueAuthorizationCodeServices {

    final AuthorizationCodesRepo oauthCodesRepo


    AuthorizationCodeServicesAdapter(AuthorizationCodesRepo oauthCodesRepo) {
        this.oauthCodesRepo = oauthCodesRepo
    }


    void store(String code, OAuth2Authentication authentication) {
        log.debug 'Storing authorization code: {}', code
        oauthCodesRepo.save(new PersistableAuthorizationCode(code, authentication))
    }

    OAuth2Authentication remove(String code) {
        log.debug 'Consuming authorization code: {}', code

        def result = oauthCodesRepo.findOne(code)

        if (result) {
            oauthCodesRepo.deleteById(code)
        }
        result?.authentication
    }

    @Transactional
    String createAuthorizationCode(OAuth2Authentication authentication) {
        super.createAuthorizationCode(authentication)
    }

    @Transactional
    OAuth2Authentication consumeAuthorizationCode(String code) {
        super.consumeAuthorizationCode(code)
    }
}
