/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020 Sergio del Amo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovycalamari.euroforeignexchangereference

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Single

class CubeTimeSpec extends ApplicationContextSpecification {

    void "cube time XML reading"() {
        given:
        int curatedPort = SocketUtils.findAvailableTcpPort()
        EmbeddedServer mockServer = ApplicationContext.run(EmbeddedServer, [
                'spec.name': 'CubeTimeSpec',
                'micronaut.server.port': curatedPort,
        ])
        ApplicationContext applicationContext = ApplicationContext.run([
                'mock.url': "http://localhost:${curatedPort}"
        ])
        XmlClient xmlClient = applicationContext.getBean(XmlClient)

        when:
        CubeTime cubeTime = xmlClient.xmlContent.blockingGet()

        then:
        cubeTime
        cubeTime.rates
        cubeTime.rates[0].currency == 'USD'
        cubeTime.rates[0].rate == 1.1987f
        cubeTime.rates[1].currency == 'JPY'
        cubeTime.rates[1].rate == 126.92f

        cleanup:
        applicationContext.close()
        mockServer.close()
    }

    @Client(value = '${mock.url}')
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    static interface XmlClient {
        @Get
        Single<CubeTime> getXmlContent();
    }

    @Requires(property = "spec.name", value = "CubeTimeSpec")
    @Controller
    static class MockController {

        @Consumes(MediaType.APPLICATION_XML)
        @Produces(MediaType.APPLICATION_XML)
        @Get
        String xml() {
            '<Cube time="2020-09-01"><Cube currency="USD" rate="1.1987"/><Cube currency="JPY" rate="126.92"/></Cube>'
        }
    }
}
