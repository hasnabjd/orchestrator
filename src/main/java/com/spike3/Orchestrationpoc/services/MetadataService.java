package com.spike3.Orchestrationpoc.services;

import com.spike3.Orchestrationpoc.models.Metadata;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MetadataService {

    private static final String JSON_FILE_PATH = "src/main/resources/metadata.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "mf-metadata-topic", groupId = "orchestrationGroup")
    public void consumeMetadata(String message) {
        try {
            // Convertir le message JSON en objet Metadata
            Metadata metadata = objectMapper.readValue(message, Metadata.class);

            // Lire le fichier JSON existant ou créer une nouvelle liste si le fichier n'existe pas
            List<Metadata> metadataList;
            try {
                metadataList = new ArrayList<>(Arrays.asList(objectMapper.readValue(new File(JSON_FILE_PATH), Metadata[].class)));
            } catch (IOException e) {
                metadataList = new ArrayList<>();
            }

            // Vérifier si une métadonnée avec le même nom existe déjà
            boolean found = false;
            for (int i = 0; i < metadataList.size(); i++) {
                Metadata existingMetadata = metadataList.get(i);
                if (existingMetadata.getName().equals(metadata.getName())) {
                    // Mettre à jour la métadonnée existante
                    metadataList.set(i, metadata);
                    found = true;
                    break;
                }
            }

            // Si aucune métadonnée avec le même nom n'a été trouvée, ajouter la nouvelle métadonnée
            if (!found) {
                metadataList.add(metadata);
            }

            // Écrire la liste mise à jour dans le fichier JSON
            objectMapper.writeValue(new File(JSON_FILE_PATH), metadataList);

            System.out.println("Metadata has been stored in metadata.json file.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing metadata in metadata.json file.");
            e.printStackTrace();
        }
    }
}
