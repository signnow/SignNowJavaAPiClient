package com.signnow.library.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("java:S1104")  // field name equal to JsonProperty
public class GenericId {
    public String id;

    @JsonIgnore
    private final Map<String, String> additionalMembers = new HashMap<>();

    @JsonAnySetter
    public void ignored(String name, Object value) {
        if (value != null) {
            additionalMembers.put(name, value.toString());
        } else {
            additionalMembers.put(name, null);
        }
    }

    /**
     * For debug purposes, contains API response object additional information that is not supported via DTO.
     *
     * @return
     */
    public Map<String, String> getAdditionalMembers() {
        return Collections.unmodifiableMap(additionalMembers);
    }
}
