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
package groovycalamari.euroforeignexchangereference;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

/**
 * {@link ConfigurationProperties} for {@link EuroForeignExchangeReferenceRatesClient}.
 */
@ConfigurationProperties(EuroForeignExchangeReferenceRatesConfiguration.PREFIX)
public class EuroForeignExchangeReferenceRatesConfiguration extends HttpClientConfiguration {
    public static final String PREFIX = "euro";
    public static final String HOST_LIVE = "https://www.ecb.europa.eu";

    private final EuroForeignExchangeReferenceRatesConnectionPoolConfiguration connectionPoolConfiguration;

    @NonNull
    @NotBlank
    private String url = HOST_LIVE;

    @NotBlank
    @NonNull
    public String getUrl() {
        return this.url;
    }

    public void setUrl(@NonNull @NotBlank String url) {
        this.url = url;
    }


    public EuroForeignExchangeReferenceRatesConfiguration(final ApplicationConfiguration applicationConfiguration,
                                          final EuroForeignExchangeReferenceRatesConnectionPoolConfiguration connectionPoolConfiguration) {
        super(applicationConfiguration);
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }


    @Override
    @NonNull
    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return this.connectionPoolConfiguration;
    }

    /**
     * {@link ConnectionPoolConfiguration} for {@link EuroForeignExchangeReferenceRatesClient}.
     */
    @ConfigurationProperties(ConnectionPoolConfiguration.PREFIX)
    public static class EuroForeignExchangeReferenceRatesConnectionPoolConfiguration extends ConnectionPoolConfiguration {
    }

    /**
     * Extra {@link ConfigurationProperties} to set the values for the {@link io.micronaut.retry.annotation.Retryable} annotation on {@link EuroForeignExchangeReferenceRatesClient}.
     */
    @ConfigurationProperties(EuroForeignExchangeReferenceRatesConnectionPoolRetryConfiguration.PREFIX)
    public static class EuroForeignExchangeReferenceRatesConnectionPoolRetryConfiguration {

        public static final String PREFIX = "retry";

        private static final Duration DEFAULT_DELAY = Duration.ofSeconds(5);
        private static final int DEFAULT_ATTEMPTS = 0;

        /**
         * @return The delay between retry attempts
         */
        private Duration delay = DEFAULT_DELAY;

        /**
         * @return The maximum number of retry attempts
         */
        private int attempts = DEFAULT_ATTEMPTS;

        public Duration getDelay() {
            return delay;
        }

        public void setDelay(Duration delay) {
            this.delay = delay;
        }

        public int getAttempts() {
            return attempts;
        }

        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }
    }
}

