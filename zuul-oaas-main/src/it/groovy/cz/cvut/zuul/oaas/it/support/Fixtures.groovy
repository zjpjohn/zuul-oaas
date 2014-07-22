/*
 * The MIT License
 *
 * Copyright 2013-2014 Czech Technical University in Prague.
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
package cz.cvut.zuul.oaas.it.support

import cz.cvut.zuul.oaas.models.Client
import org.springframework.security.core.authority.SimpleGrantedAuthority

import static cz.cvut.zuul.oaas.models.AuthorizationGrant.*

abstract class Fixtures {

    static allGrantsClient() {
        new Client (
            clientId: 'test-client',
            clientSecret: 'top-secret',
            scope: [ 'urn:zuul:oauth:test', 'urn:zuul:oauth:test2', 'urn:zuul:oauth:oaas:tokeninfo' ],
            authorizedGrantTypes: [ AUTHORIZATION_CODE, CLIENT_CREDENTIALS, IMPLICIT, REFRESH_TOKEN ],
            registeredRedirectUri: [ 'http://example.org' ],
            authorities: [ new SimpleGrantedAuthority('ROLE_CLIENT') ],
        )
    }
}
