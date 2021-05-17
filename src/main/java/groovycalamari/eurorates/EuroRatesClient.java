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
package groovycalamari.eurorates;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import io.micronaut.validation.Validated;
import io.reactivex.Single;

@Validated
@Client(
        value = "${" + EuroRatesConfiguration.PREFIX + ".url:`" + EuroRatesConfiguration.HOST_LIVE + "`}",
        configuration = EuroRatesConfiguration.class
)
@Retryable(
        attempts = "${" + EuroRatesConfiguration.PREFIX + ".retry.attempts:0}",
        delay = "${" + EuroRatesConfiguration.PREFIX + ".retry.delay:5s}")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public interface EuroRatesClient extends EuroRatesApi {

    String PATH_CURRENT = "/stats/eurofxref/eurofxref-daily.xml";
    String PATH_HISTORY = "/stats/eurofxref/eurofxref-hist.xml";
    String PATH_90_DAYS = "/stats/eurofxref/eurofxref-hist-90d.xml";

    @Override
    @Get(PATH_CURRENT)
    Single<GesmesEnvelope> currentReferenceRates();

    @Override
    @Get(PATH_HISTORY)
    Single<GesmesEnvelope> historicalReferenceRates();

    @Override
    @Get(PATH_90_DAYS)
    Single<GesmesEnvelope> last90DaysReferenceRates();
}
