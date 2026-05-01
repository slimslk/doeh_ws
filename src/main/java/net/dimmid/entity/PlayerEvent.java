package net.dimmid.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlayerEvent(
        @JsonProperty(value = "user_id", required = true) String userId,
        @JsonProperty(value = "action", required = true) String action,
        @JsonProperty(value = "params") List<Object> params
) {
    @JsonCreator
    public PlayerEvent {
        params = (params == null) ? List.of() : params;
    }
}
