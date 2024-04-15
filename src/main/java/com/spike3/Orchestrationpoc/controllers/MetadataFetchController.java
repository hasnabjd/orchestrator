package com.spike3.Orchestrationpoc.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spike3.Orchestrationpoc.models.Metadata;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/metadata")
public class MetadataFetchController {

    private final ObjectMapper objectMapper;

    public MetadataFetchController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Metadata> getMetadata() throws IOException {
        // Charger le fichier metadata.json depuis les ressources de classe
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("metadata.json");

        // Désérialiser le contenu du fichier JSON en tant que tableau de Metadata
        Metadata[] metadataArray = objectMapper.readValue(inputStream, Metadata[].class);

        // Convertir le tableau en une liste
        List<Metadata> metadataList = Arrays.asList(metadataArray);

        return metadataList;
    }

}
