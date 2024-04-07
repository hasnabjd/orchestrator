package com.spike3.Orchestrationpoc.controllers;

import com.spike3.Orchestrationpoc.models.Metadata;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private static final String JSON_FILE_PATH = "metadata.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public Metadata[] getAllMetadata() {
        try {
            return objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Metadata[0];
        }
    }

    @GetMapping("/{name}")
    public Metadata getMetadataByName(@PathVariable String name) {
        try {
            Metadata[] metadataArray = objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class);
            for (Metadata metadata : metadataArray) {
                if (metadata.getName().equals(name)) {
                    return metadata;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{name}/{version}")
    public Metadata getMetadataByNameAndVersion(@PathVariable String name, @PathVariable String version) {
        try {
            Metadata[] metadataArray = objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class);
            for (Metadata metadata : metadataArray) {
                if (metadata.getName().equals(name) && metadata.getVersion().equals(version)) {
                    return metadata;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
