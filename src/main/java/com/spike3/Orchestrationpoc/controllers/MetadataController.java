package com.spike3.Orchestrationpoc.controllers;

import com.spike3.Orchestrationpoc.models.Metadata;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private static final String JSON_FILE_PATH = "src/main/resources/metadata.json";
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

    @PostMapping
    public String addMetadata(@RequestBody Metadata metadata) {
        try {
            // Lire les métadonnées existantes
            Metadata[] metadataArray = objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class);

            // Vérifier si la métadonnée existe déjà
            for (Metadata existingMetadata : metadataArray) {
                if (existingMetadata.getName().equals(metadata.getName())) {
                    return "Metadata with the same name already exists. Please use PUT to update it.";
                }
            }

            // Ajouter la nouvelle métadonnée à la liste
            List<Metadata> metadataList = new ArrayList<>(Arrays.asList(metadataArray));
            metadataList.add(metadata);

            // Écrire la liste mise à jour dans le fichier JSON
            objectMapper.writeValue(new File(JSON_FILE_PATH), metadataList);

            return "Metadata added successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to add metadata.";
        }
    }

    @PutMapping("/{name}")
    public String updateMetadata(@PathVariable String name, @RequestBody Metadata updatedMetadata) {
        try {
            // Lire les métadonnées existantes
            Metadata[] metadataArray = objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class);

            // Mettre à jour la métadonnée avec le même nom
            boolean found = false;
            for (int i = 0; i < metadataArray.length; i++) {
                Metadata existingMetadata = metadataArray[i];
                if (existingMetadata.getName().equals(name)) {
                    metadataArray[i] = updatedMetadata;
                    found = true;
                    break;
                }
            }

            // Si aucune métadonnée avec le même nom n'a été trouvée, retourner une erreur
            if (!found) {
                return "Metadata not found.";
            }

            // Écrire la liste mise à jour dans le fichier JSON
            objectMapper.writeValue(new File(JSON_FILE_PATH), Arrays.asList(metadataArray));

            return "Metadata updated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update metadata.";
        }
    }


}
