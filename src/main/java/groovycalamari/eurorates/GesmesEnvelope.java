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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class GesmesEnvelope {

    @NonNull
    @JacksonXmlProperty(namespace = "gesmes", localName = "subject")
    @NotBlank
    private String subject;

    @NonNull
    @NotNull
    @JacksonXmlProperty(namespace = "gesmes", localName = "Sender")
    private GesmesSender sender;

    @NonNull
    @NotNull
    @JacksonXmlProperty(localName = "Cube")
    private Cube cube;

    public GesmesEnvelope() {
    }

    @NonNull
    public GesmesSender getSender() {
        return sender;
    }

    public void setSender(@NonNull GesmesSender sender) {
        this.sender = sender;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }

    @NonNull
    public Cube getCube() {
        return cube;
    }

    public void setCube(@NonNull Cube cube) {
        this.cube = cube;
    }
}
