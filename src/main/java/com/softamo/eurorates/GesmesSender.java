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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Introspected
public class GesmesSender {
    @JacksonXmlProperty(namespace = "gesmes", localName = "name")
    @NonNull
    @NotBlank
    private String name;

    public GesmesSender() {
    }

    public GesmesSender(@NonNull String name) {
        this.name = name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
