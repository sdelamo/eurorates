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
package com.softamo.eurorates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import java.net.MalformedURLException;
import java.net.URL;

public class ManualEuroRatesApi implements EuroRatesApi {

    private final HttpClient httpClient;

    public ManualEuroRatesApi() throws MalformedURLException {
        this(EuroRatesConfiguration.HOST_LIVE);
    }

    public ManualEuroRatesApi(String url) throws MalformedURLException {
        httpClient = HttpClient.create(new URL(url));
    }

    @Override
    @SingleResult
    public Publisher<GesmesEnvelope> currentReferenceRates() {
        return retrieve(EuroRatesClient.PATH_CURRENT);
    }

    @Override
    @SingleResult
    public Publisher<GesmesEnvelope> historicalReferenceRates() {
        return retrieve(EuroRatesClient.PATH_HISTORY);
    }

    @Override
    @SingleResult
    public Publisher<GesmesEnvelope> last90DaysReferenceRates() {
        return retrieve(EuroRatesClient.PATH_90_DAYS);
    }

    @SingleResult
    public Publisher<GesmesEnvelope> retrieve(String path) {
        return Flux.from(httpClient.retrieve(getRequest(path), String.class))
                .flatMap(xml -> {
                    try {
                        GesmesEnvelope envelope = new XmlMapper().readValue(xml, GesmesEnvelope.class);
                        return Flux.just(envelope);
                    } catch (JsonProcessingException e) {
                        return Flux.error(e);
                    }
                });
    }

    private HttpRequest<?> getRequest(String uri) {
        return HttpRequest.GET(uri)
                .accept(MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_XML);
    }
}
