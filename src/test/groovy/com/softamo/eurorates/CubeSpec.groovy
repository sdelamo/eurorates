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
package com.softamo.eurorates

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

class CubeSpec extends ApplicationContextSpecification {
    void "cube XML reading"() {
        given:
        int mockPort = SocketUtils.findAvailableTcpPort()
        EmbeddedServer mockServer = ApplicationContext.run(EmbeddedServer, [
                'spec.name': 'CubeSpec',
                'micronaut.server.port': mockPort,
        ])
        ApplicationContext applicationContext = ApplicationContext.run([
                'mock.url': "http://localhost:${mockPort}"
        ])
        XmlClient xmlClient = applicationContext.getBean(XmlClient)

        when:
        Cube cube = Mono.from(xmlClient.xmlContent).block()

        then:
        cube
        cube.times
        cube.times.size() == 1
        cube.times[0].rates
        cube.times[0].rates[0].currency == 'USD'
        cube.times[0].rates[0].rate == 1.1987f
        cube.times[0].rates[1].currency == 'JPY'
        cube.times[0].rates[1].rate == 126.92f

        cleanup:
        applicationContext.close()
        mockServer.close()
    }

    @Client(value = '${mock.url}')
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    static interface XmlClient {
        @Get
        @SingleResult
        Publisher<Cube> getXmlContent();
    }

    @Requires(property = "spec.name", value = "CubeSpec")
    @Controller
    static class MockController {

        @Consumes(MediaType.APPLICATION_XML)
        @Produces(MediaType.APPLICATION_XML)
        @Get
        String xml() {
            '<Cube><Cube time="2020-09-01"><Cube currency="USD" rate="1.1987"/><Cube currency="JPY" rate="126.92"/><Cube currency="BGN" rate="1.9558"/><Cube currency="CZK" rate="26.226"/><Cube currency="DKK" rate="7.4434"/><Cube currency="GBP" rate="0.88975"/><Cube currency="HUF" rate="354.02"/><Cube currency="PLN" rate="4.3925"/><Cube currency="RON" rate="4.8398"/><Cube currency="SEK" rate="10.3605"/><Cube currency="CHF" rate="1.0865"/><Cube currency="ISK" rate="164.50"/><Cube currency="NOK" rate="10.4378"/><Cube currency="HRK" rate="7.5320"/><Cube currency="RUB" rate="88.1474"/><Cube currency="TRY" rate="8.8196"/><Cube currency="AUD" rate="1.6242"/><Cube currency="BRL" rate="6.5126"/><Cube currency="CAD" rate="1.5600"/><Cube currency="CNY" rate="8.1739"/><Cube currency="HKD" rate="9.2900"/><Cube currency="IDR" rate="17465.06"/><Cube currency="ILS" rate="4.0183"/><Cube currency="INR" rate="87.4085"/><Cube currency="KRW" rate="1420.12"/><Cube currency="MXN" rate="26.0278"/><Cube currency="MYR" rate="4.9656"/><Cube currency="NZD" rate="1.7727"/><Cube currency="PHP" rate="58.171"/><Cube currency="SGD" rate="1.6274"/><Cube currency="THB" rate="37.250"/><Cube currency="ZAR" rate="19.9459"/></Cube></Cube>'
        }
    }
}
