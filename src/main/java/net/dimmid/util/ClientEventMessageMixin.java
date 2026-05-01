package net.dimmid.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ClientEventMessageMixin {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String userId;
}
