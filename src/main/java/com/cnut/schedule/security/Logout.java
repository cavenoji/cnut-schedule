package com.cnut.schedule.security;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class Logout {

    @NonNull
    @NotBlank
    private String logoutUrl;

    @Nullable
    private String idToken;

    public Logout() {
    }

    @NonNull
    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(@NonNull String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Nullable
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(@Nullable String idToken) {
        this.idToken = idToken;
    }
}