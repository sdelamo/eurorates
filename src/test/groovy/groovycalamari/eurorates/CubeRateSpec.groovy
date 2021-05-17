/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovycalamari.eurorates

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

class CubeRateSpec extends ApplicationContextSpecification {

    void "cube rate XML reading"() {
        given:
        int mockPort = SocketUtils.findAvailableTcpPort()
        EmbeddedServer mockServer = ApplicationContext.run(EmbeddedServer, [
                'spec.name': 'CubeRateSpec',
                'micronaut.server.port': mockPort,
        ])
        ApplicationContext applicationContext = ApplicationContext.run([
                'mock.url': "http://localhost:${mockPort}"
        ])
        XmlClient xmlClient = applicationContext.getBean(XmlClient)

        when:
        CubeRate cubeRate = xmlClient.xmlContent.blockingGet()

        then:
        cubeRate
        cubeRate.currency == 'JPY'
        cubeRate.rate == 126.92f

        cleanup:
        applicationContext.close()
        mockServer.close()
    }

    @Client(value = '${mock.url}')
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    static interface XmlClient {
        @Get
        Single<CubeRate> getXmlContent();
    }

    @Requires(property = "spec.name", value = "CubeRateSpec")
    @Controller
    static class MockController {

        @Consumes(MediaType.APPLICATION_XML)
        @Produces(MediaType.APPLICATION_XML)
        @Get
        String xml() {
            '<Cube currency="JPY" rate="126.92"/>'
        }
    }
}
