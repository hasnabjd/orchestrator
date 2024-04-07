package com.spike3.Orchestrationpoc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private String name;
    private String remoteEntryUrl;
    private String version;
    private Map<String, String> dependencies;
    private List<String> permissions;
    private Map<String, String> env;
    public String getName() {
        return name;
    }

    public String getRemoteEntryUrl() {
        return remoteEntryUrl;
    }

    public String getVersion() {
        return version;
    }
}
