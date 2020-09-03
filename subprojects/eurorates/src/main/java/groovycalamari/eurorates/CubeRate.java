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
package groovycalamari.eurorates;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class CubeRate {
    @NonNull
    @NotBlank
    @JacksonXmlProperty(isAttribute = true)
    private String currency;

    @JacksonXmlProperty(isAttribute = true)
    @NonNull
    @NotBlank
    private Float rate;

    public CubeRate() {
    }

    public CubeRate(@NonNull @NotBlank String currency,
                    @NonNull @NotBlank Float rate) {
        this.currency = currency;
        this.rate = rate;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    @NonNull
    public Float getRate() {
        return rate;
    }

    public void setRate(@NonNull Float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "<Cube currency='" + currency + "', rate='" + rate + "'/>";
    }
}
