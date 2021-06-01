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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.reactivex.Single;

import java.net.MalformedURLException;
import java.net.URL;

public class ManualEuroRatesApi implements EuroRatesApi {

    private RxHttpClient rxHttpClient;

    public ManualEuroRatesApi() throws MalformedURLException {
        this(EuroRatesConfiguration.HOST_LIVE);
    }

    public ManualEuroRatesApi(String url) throws MalformedURLException {
        rxHttpClient = RxHttpClient.create(new URL(url));
    }

    @Override
    public Single<GesmesEnvelope> currentReferenceRates() {
        return retrieve(EuroRatesClient.PATH_CURRENT);
    }

    @Override
    public Single<GesmesEnvelope> historicalReferenceRates() {
        return retrieve(EuroRatesClient.PATH_HISTORY);
    }

    @Override
    public Single<GesmesEnvelope> last90DaysReferenceRates() {
        return retrieve(EuroRatesClient.PATH_90_DAYS);
    }

    private Single<GesmesEnvelope> retrieve(String path) {
        return rxHttpClient.retrieve(getRequest(path), String.class).map(xml ->
        new XmlMapper().readValue(xml.toString(), GesmesEnvelope.class)).firstOrError();
    }

    private HttpRequest getRequest(String uri) {
        return HttpRequest.GET(uri).accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_XML);
    }
}
