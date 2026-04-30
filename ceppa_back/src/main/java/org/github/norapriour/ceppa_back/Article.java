package org.github.norapriour.ceppa_back;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Article(
        Integer id,

        @JsonProperty("author")
        String auteur,

        @JsonProperty("title")
        String titre,

        @JsonProperty("text")
        String texte
) {}