package org.example.project;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class RestApi {
    Model model = JenaEngine.readModel("data/ontology.owl");
    @PostMapping("/addAvion")
     
    public ResponseEntity<String> addAvion(@RequestBody AvionDTO avionDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?avion rdf:type ont:avion . " +
                                "  ?avion ont:prix \"" + avionDTO.getPrix() + "\" . " +
                                "  ?avion ont:description \"" + avionDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Avion_\", STRUUID())) AS ?avion) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Avion added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding avion: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Méthode pour ajouter un bus
    @PostMapping("/addBus")

    public ResponseEntity<String> addBus(@RequestBody BusDTO busDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT pour ajouter un bus
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?bus rdf:type ont:bus . " +
                                "  ?bus ont:prix \"" + busDTO.getPrix() + "\" . " +
                                "  ?bus ont:description \"" + busDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Bus_\", STRUUID())) AS ?bus) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Bus added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding bus: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addBateau")
     
    public ResponseEntity<String> addBateau(@RequestBody BateauDTO bateauDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT pour le bateau
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?bateau rdf:type ont:bateau . " +
                                "  ?bateau ont:prix \"" + bateauDTO.getPrix() + "\" . " +
                                "  ?bateau ont:description \"" + bateauDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Bateau_\", STRUUID())) AS ?bateau) " +
                                "}";

                // Créer et exécuter la requête de mise à jour
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Bateau added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding bateau: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addBooking")
    public ResponseEntity<String> addBooking(@RequestBody BookingDTO bookingDTO) {
        if (model != null) {
            try {
                // Define the SPARQL endpoint URL for adding bookings
                String fusekiUrl = "http://localhost:3030/tourisme5/update"; // Endpoint for update queries

                // Define the SPARQL INSERT query to add a booking
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?booking rdf:type ont:Booking . " +
                                "  ?booking ont:email \"" + bookingDTO.getEmail() + "\" . " +
                                "  ?booking ont:adresse \"" + bookingDTO.getAdresse() + "\" . " +
                                "  ?booking ont:description \"" + bookingDTO.getDescription() + "\" . " +
                                "  ?booking ont:nom \"" + bookingDTO.getNom() + "\" . " +
                                "  ?booking ont:numtelephone \"" + bookingDTO.getNumtelephone() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Booking_\", STRUUID())) AS ?booking) " +
                                "}";

                // Prepare the connection for SPARQL update
                URL url = new URL(fusekiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/sparql-update");
                conn.setDoOutput(true);

                // Send the SPARQL update query
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(insertQuery.getBytes("UTF-8")); // Specify UTF-8 encoding
                    os.flush();
                }

                // Read the response from the server
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return new ResponseEntity<>("Booking added successfully", HttpStatus.CREATED);
                } else {
                    // Read the error stream to get more details about the error
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    return new ResponseEntity<>("Error adding booking: " + responseCode + " - " + response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addFedEx")
    public ResponseEntity<String> addFedEx(@RequestBody FedExDTO fedExDTO) {
        // Set the SPARQL endpoint URL for updates
        String fusekiUrl = "http://localhost:3030/tourisme5/update"; // Ensure you're hitting the correct endpoint for updates

        if (model != null) {
            try {
                // Define the SPARQL INSERT query to add FedEx
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?fedex rdf:type ont:FedEx . " + // Ensure the correct type is used
                                "  ?fedex ont:email \"" + fedExDTO.getEmail() + "\" . " +
                                "  ?fedex ont:adresse \"" + fedExDTO.getAdresse() + "\" . " +
                                "  ?fedex ont:description \"" + fedExDTO.getDescription() + "\" . " +
                                "  ?fedex ont:nom \"" + fedExDTO.getNom() + "\" . " +
                                "  ?fedex ont:numtelephone \"" + fedExDTO.getNumtelephone() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#FedEx_\", STRUUID())) AS ?fedex) " +
                                "}";

                // Prepare the connection for SPARQL update
                URL url = new URL(fusekiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/sparql-update");
                conn.setDoOutput(true);

                // Send the SPARQL query
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(insertQuery.getBytes("UTF-8")); // Specify UTF-8 encoding
                    os.flush();
                }

                // Check the response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return new ResponseEntity<>("FedEx added successfully", HttpStatus.CREATED);
                } else {
                    // Read the error stream to get more details about the error
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    return new ResponseEntity<>("Error adding FedEx: " + responseCode + " - " + response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding FedEx: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addRandoLoup")
    public ResponseEntity<String> addRandoLoup(@RequestBody RandoLoupDTO randoLoupDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT pour RandoLoup
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?randoLoup rdf:type ont:Randoloup . " +
                                "  ?randoLoup ont:impact_environnemental \"" + randoLoupDTO.getImpactEnvironnemental() + "\" . " +
                                "  ?randoLoup ont:lieu \"" + randoLoupDTO.getLieu() + "\" . " +
                                "  ?randoLoup ont:description \"" + randoLoupDTO.getDescription() + "\" . " +
                                "  ?randoLoup ont:nom \"" + randoLoupDTO.getNom() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#RandoLoup_\", STRUUID())) AS ?randoLoup) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("RandoLoup ajouté avec succès", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de l'ajout de RandoLoup : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/addWeLoveGreen")
    public ResponseEntity<String> addWeLoveGreen(@RequestBody WeLoveGreenDTO weLoveGreenDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?weLoveGreen rdf:type ont:WeLoveGreen . " +
                                "  ?weLoveGreen ont:nom \"" + weLoveGreenDTO.getNom() + "\" . " +
                                "  ?weLoveGreen ont:description \"" + weLoveGreenDTO.getDescription() + "\" . " +
                                "  ?weLoveGreen ont:lieu \"" + weLoveGreenDTO.getLieu() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#WeLoveGreen_\", STRUUID())) AS ?weLoveGreen) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("WeLoveGreen added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding WeLoveGreen: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Méthode pour ajouter un événement ESTC
    @PostMapping("/addEstc")
    public ResponseEntity<String> addEstc(@RequestBody EstcDTO estcDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?estc rdf:type ont:Estc . " +
                                "  ?estc ont:impact_environnemental \"" + estcDTO.getImpactEnvironnemental() + "\" . " +
                                "  ?estc ont:lieu \"" + estcDTO.getLieu() + "\" . " +
                                "  ?estc ont:nom \"" + estcDTO.getNom() + "\" . " +
                                "  ?estc ont:description \"" + estcDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Estc_\", STRUUID())) AS ?estc) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Estc added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding Estc: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addYuga")
    public ResponseEntity<String> addYuga(@RequestBody YugaDTO yugaDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT pour Yuga
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?yuga rdf:type ont:yuga . " +
                                "  ?yuga ont:titre \"" + yugaDTO.getTitre() + "\" . " +
                                "  ?yuga ont:description \"" + yugaDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Yuga_\", STRUUID())) AS ?yuga) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Yuga added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding yuga: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMuseeLouvre")
    public ResponseEntity<String> addMuseeLouvre(@RequestBody MuseeLouvreDTO museeLouvreDTO) {
        if (model != null) {
            try {
                // Définir la requête SPARQL INSERT
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?musee rdf:type ont:MuseeLouvre . " +
                                "  ?musee ont:titre \"" + museeLouvreDTO.getTitre() + "\" . " +
                                "  ?musee ont:description \"" + museeLouvreDTO.getDescription() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#MuseeLouvre_\", STRUUID())) AS ?musee) " +
                                "}";

                // Créer la requête de mise à jour et l'exécuter
                UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier ontologique
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("MuseeLouvre added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding MuseeLouvre: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addPollution")
    public ResponseEntity<String> addPollution(@RequestBody PollutionDTO pollutionDTO) {
        // Set the SPARQL endpoint URL for updates
        String fusekiUrl = "http://localhost:3030/tourisme5/update"; // Ensure you're hitting the correct endpoint for updates

        if (model != null) {
            try {
                // Define the SPARQL INSERT query to add pollution
                String insertQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "INSERT { " +
                                "  ?pollution rdf:type ont:Pollution . " + // Ensure the correct type is used
                                "  ?pollution ont:sujet \"" + pollutionDTO.getSujet() + "\" . " +
                                "  ?pollution ont:description \"" + pollutionDTO.getDescription() + "\" . " +
                                "  ?pollution ont:status \"" + pollutionDTO.getStatus() + "\" . " +
                                "  ?pollution ont:date_reclamation \"" + pollutionDTO.getDateReclamation() + "\" . " +
                                "  ?pollution ont:date_traitement \"" + pollutionDTO.getDateTraitement() + "\" . " +
                                "} WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Pollution_\", STRUUID())) AS ?pollution) " +
                                "}";

                // Prepare the connection for SPARQL update
                URL url = new URL(fusekiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/sparql-update");
                conn.setDoOutput(true);

                // Send the SPARQL query
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(insertQuery.getBytes("UTF-8")); // Specify UTF-8 encoding
                    os.flush();
                }

                // Check the response code
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return new ResponseEntity<>("Pollution report added successfully", HttpStatus.CREATED);
                } else {
                    // Read the error stream to get more details about the error
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    return new ResponseEntity<>("Error adding pollution report: " + responseCode + " - " + response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding pollution report: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addSecurites")
    public ResponseEntity<String> addSecurite1(@RequestBody SecuriteDTO securiteDTO) {
        // Set the SPARQL endpoint URL for updates
        String fusekiUrl = "http://localhost:3030/tourisme5/update";

        try {
            // Define the SPARQL INSERT query to add Securite data
            String insertQuery =
                    "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "INSERT { " +
                            "  ?securite rdf:type ont:Securite . " +
                            "  ?securite ont:sujet \"" + securiteDTO.getSujet() + "\" . " +
                            "  ?securite ont:status \"" + securiteDTO.getStatus() + "\" . " +
                            "  ?securite ont:description \"" + securiteDTO.getDescription() + "\" . " +
                            "  ?securite ont:dateReclamation \"" + securiteDTO.getDateReclamation() + "\"^^xsd:date . " +
                            "  ?securite ont:dateTraitement \"" + securiteDTO.getDateTraitement() + "\"^^xsd:date . " +
                            "} WHERE { " +
                            "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Securite_\", STRUUID())) AS ?securite) " +
                            "}";

            // Prepare the connection for SPARQL update
            URL url = new URL(fusekiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/sparql-update");
            conn.setDoOutput(true);

            // Send the SPARQL query
            try (OutputStream os = conn.getOutputStream()) {
                os.write(insertQuery.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return new ResponseEntity<>("Securite added successfully", HttpStatus.CREATED);
            } else {
                // Read the error stream to get more details about the error
                StringBuilder response = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                }
                return new ResponseEntity<>("Error adding Securite: " + responseCode + " - " + response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding Securite: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avions")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherAvions() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Charger le modèle inféré avec les règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les avions à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-avion.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return "Error when reading model from ontology";
        }
    }
    @GetMapping("/buses")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherBus() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-bus.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }
    @GetMapping("/bateaux")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherBateaux() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Lire le modèle inféré à partir du fichier de règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les bateaux à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-bateau.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }
    @GetMapping("/booking")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherBooking() {
        // Set the SPARQL endpoint URL
        String fusekiUrl = "http://localhost:3030/tourisme5/query"; // Ensure you're hitting the correct endpoint for queries

        // Define the SPARQL query to retrieve booking data
        String sparqlQuery =
                "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "SELECT ?email ?adresse ?description ?nom ?numtelephone " +
                        "WHERE { " +
                        "    ?booking rdf:type ont:Booking . " + // Specify the entity directly
                        "    ?booking ont:email ?email . " +
                        "    ?booking ont:adresse ?adresse . " +
                        "    ?booking ont:description ?description . " +
                        "    ?booking ont:nom ?nom . " +
                        "    ?booking ont:numtelephone ?numtelephone . " +
                        "}";

        try {
            // Prepare the connection
            URL url = new URL(fusekiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/sparql-query");
            conn.setDoOutput(true);

            // Send the SPARQL query
            try (OutputStream os = conn.getOutputStream()) {
                os.write(sparqlQuery.getBytes("UTF-8")); // Specify UTF-8 encoding
                os.flush();
            }

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Return the response as a string
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when retrieving data from Fuseki: " + e.getMessage();
        }
    }
    @GetMapping("/fedex")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherFedEx() {
        // Set the SPARQL endpoint URL
        String fusekiUrl = "http://localhost:3030/tourisme5/query"; // Ensure you're hitting the correct endpoint for queries

        // Define the SPARQL query to retrieve FedEx data
        String sparqlQuery = "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "SELECT ?fedex ?email ?adresse ?description ?nom ?numtelephone " +
                "WHERE { " +
                "  ?fedex rdf:type ont:FedEx . " +
                "  ?fedex ont:email ?email . " +
                "  ?fedex ont:adresse ?adresse . " +
                "  ?fedex ont:description ?description . " +
                "  ?fedex ont:nom ?nom . " +
                "  ?fedex ont:numtelephone ?numtelephone . " +
                "}";

        try {
            // Prepare the connection
            URL url = new URL(fusekiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/sparql-query");
            conn.setDoOutput(true);

            // Send the SPARQL query
            try (OutputStream os = conn.getOutputStream()) {
                os.write(sparqlQuery.getBytes("UTF-8")); // Specify UTF-8 encoding
                os.flush();
            }

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Return the response as a string
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when retrieving data from Fuseki: " + e.getMessage();
        }
    }
    @GetMapping("/estc")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherEstcs() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Charger le modèle inféré avec les règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les estcs à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-estc.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return "Error when reading model from ontology";
        }
    }


    @GetMapping("/weLoveGreens")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherWeLoveGreens() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Charger le modèle inféré avec les règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les WeLoveGreen à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-weLoveGreen.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return "Error when reading model from ontology";
        }
    }

    @GetMapping("/randoloups")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherRandoLoups() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Charger le modèle inféré avec les règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les randoloups à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-randoloup.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return "Erreur lors de la lecture du modèle depuis l'ontologie";
        }
    }




    @GetMapping("/museelouvre") // Change the endpoint as needed
    public String afficherMuseeLouvre() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Lire le modèle inféré à partir du fichier de règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les informations de MuseeMeseeOeuvre à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-museelouvre.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/yuga")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherYugas() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Charger le modèle inféré avec les règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les yugas
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-yuga.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return "Error when reading model from ontology";
        }
    }
    @GetMapping("/pollution")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherPollution() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Lire le modèle inféré à partir du fichier de règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les informations de WeLoveGreen à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-pollution.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }
    @GetMapping("/securite")
    @CrossOrigin(origins = "http://localhost:3000")
    public String afficherSecurite() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            // Lire le modèle inféré à partir du fichier de règles
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            // Exécuter la requête SPARQL pour récupérer les informations de WeLoveGreen à partir du fichier de requête
            OutputStream res = JenaEngine.executeQueryFromFile(inferedModel, "data/query-securite.txt");

            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }
    @PutMapping("/modifyAvion/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyAvion(@PathVariable("id") String avionId, @RequestBody AvionDTO avionDto) {
        if (model != null) {
            try {
                // Construct the URI for the Avion based on the ID
                String avionUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + avionId;

                // Ensure the avion resource exists
                Resource avionResource = model.getResource(avionUri);
                if (avionResource == null) {
                    return new ResponseEntity<>("Avion not found", HttpStatus.NOT_FOUND);
                }

                // Define the SPARQL DELETE/INSERT query
                String modifyQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  <" + avionUri + "> ont:prix ?oldPrix . " +
                                "  <" + avionUri + "> ont:description ?oldDescription . " +
                                "} " +
                                "INSERT { " +
                                "  <" + avionUri + "> ont:prix " + avionDto.getPrix() + " . " +
                                "  <" + avionUri + "> ont:description \"" + avionDto.getDescription() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(<" + avionUri + "> AS ?avion) ." +
                                "  OPTIONAL { ?avion ont:prix ?oldPrix } ." +
                                "  OPTIONAL { ?avion ont:description ?oldDescription } ." +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(modifyQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Avion modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying avion: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/bateaux/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerBateau(@PathVariable String id, @RequestBody BateauDTO bateauDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?bateau ont:prix ?prix . " +
                                "  ?bateau ont:description ?description . " +
                                "} " +
                                "INSERT { " +
                                "  ?bateau ont:prix \"" + bateauDTO.getPrix() + "\" . " +
                                "  ?bateau ont:description \"" + bateauDTO.getDescription() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Bateau_\", \"" + id + "\")) AS ?bateau) . " +
                                "  OPTIONAL { ?bateau ont:prix ?prix . } " +
                                "  OPTIONAL { ?bateau ont:description ?description . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Bateau updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating bateau: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/fedex/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerFedEx(@PathVariable String id, @RequestBody FedExDTO fedExDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?fedex ont:email ?email . " +
                                "  ?fedex ont:adresse ?adresse . " +
                                "  ?fedex ont:description ?description . " +
                                "  ?fedex ont:nom ?nom . " +
                                "  ?fedex ont:numtelephone ?numtelephone . " +
                                "} " +
                                "INSERT { " +
                                "  ?fedex ont:email \"" + fedExDTO.getEmail() + "\" . " +
                                "  ?fedex ont:adresse \"" + fedExDTO.getAdresse() + "\" . " +
                                "  ?fedex ont:description \"" + fedExDTO.getDescription() + "\" . " +
                                "  ?fedex ont:nom \"" + fedExDTO.getNom() + "\" . " +
                                "  ?fedex ont:numtelephone \"" + fedExDTO.getNumtelephone() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#FedEx_\", \"" + id + "\")) AS ?fedex) . " +
                                "  OPTIONAL { ?fedex ont:email ?email . } " +
                                "  OPTIONAL { ?fedex ont:adresse ?adresse . } " +
                                "  OPTIONAL { ?fedex ont:description ?description . } " +
                                "  OPTIONAL { ?fedex ont:nom ?nom . } " +
                                "  OPTIONAL { ?fedex ont:numtelephone ?numtelephone . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("FedEx updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating FedEx: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/booking/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerBooking(@PathVariable String id, @RequestBody BookingDTO bookingDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?booking ont:nom ?nom . " +
                                "  ?booking ont:numtelephone ?numtelephone . " +
                                "  ?booking ont:adresse ?adresse . " +
                                "  ?booking ont:description ?description . " +
                                "  ?booking ont:email ?email . " +
                                "} " +
                                "INSERT { " +
                                "  ?booking ont:nom \"" + bookingDTO.getNom() + "\" . " +
                                "  ?booking ont:numtelephone \"" + bookingDTO.getNumtelephone() + "\" . " +
                                "  ?booking ont:adresse \"" + bookingDTO.getAdresse() + "\" . " +
                                "  ?booking ont:description \"" + bookingDTO.getDescription() + "\" . " +
                                "  ?booking ont:email \"" + bookingDTO.getEmail() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Booking_\", \"" + id + "\")) AS ?booking) . " +
                                "  OPTIONAL { ?booking ont:nom ?nom . } " +
                                "  OPTIONAL { ?booking ont:numtelephone ?numtelephone . } " +
                                "  OPTIONAL { ?booking ont:adresse ?adresse . } " +
                                "  OPTIONAL { ?booking ont:description ?description . } " +
                                "  OPTIONAL { ?booking ont:email ?email . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/randoloup/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerRandoLoup(@PathVariable String id, @RequestBody RandoLoupDTO randoLoupDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?randoLoup ont:nom ?nom . " +
                                "  ?randoLoup ont:description ?description . " +
                                "  ?randoLoup ont:lieu ?lieu . " +
                                "  ?randoLoup ont:impact_environnemental ?impactEnvironnemental . " +
                                "} " +
                                "INSERT { " +
                                "  ?randoLoup ont:nom \"" + randoLoupDTO.getNom() + "\" . " +
                                "  ?randoLoup ont:description \"" + randoLoupDTO.getDescription() + "\" . " +
                                "  ?randoLoup ont:lieu \"" + randoLoupDTO.getLieu() + "\" . " +
                                "  ?randoLoup ont:impact_environnemental \"" + randoLoupDTO.getImpactEnvironnemental() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#RandoLoup_\", \"" + id + "\")) AS ?randoLoup) . " +
                                "  OPTIONAL { ?randoLoup ont:nom ?nom . } " +
                                "  OPTIONAL { ?randoLoup ont:description ?description . } " +
                                "  OPTIONAL { ?randoLoup ont:lieu ?lieu . } " +
                                "  OPTIONAL { ?randoLoup ont:impact_environnemental ?impactEnvironnemental . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Rando Loup updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Rando Loup: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modifyWeLoveGreen/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyWeLoveGreen(@PathVariable("id") String weLoveGreenId, @RequestBody WeLoveGreenDTO weLoveGreenDto) {
        if (model != null) {
            try {
                // Construct the URI for the WeLoveGreen entity based on the ID
                String weLoveGreenUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + weLoveGreenId;

                // Ensure the WeLoveGreen resource exists
                Resource weLoveGreenResource = model.getResource(weLoveGreenUri);
                if (weLoveGreenResource == null) {
                    return new ResponseEntity<>("WeLoveGreen entity not found", HttpStatus.NOT_FOUND);
                }

                // Define the SPARQL DELETE/INSERT query
                String modifyQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  <" + weLoveGreenUri + "> ont:nom ?oldNom . " +
                                "  <" + weLoveGreenUri + "> ont:description ?oldDescription . " +
                                "  <" + weLoveGreenUri + "> ont:lieu ?oldLieu . " +
                                "} " +
                                "INSERT { " +
                                "  <" + weLoveGreenUri + "> ont:nom \"" + weLoveGreenDto.getNom() + "\" . " +
                                "  <" + weLoveGreenUri + "> ont:description \"" + weLoveGreenDto.getDescription() + "\" . " +
                                "  <" + weLoveGreenUri + "> ont:lieu \"" + weLoveGreenDto.getLieu() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(<" + weLoveGreenUri + "> AS ?weLoveGreen) ." +
                                "  OPTIONAL { ?weLoveGreen ont:nom ?oldNom } ." +
                                "  OPTIONAL { ?weLoveGreen ont:description ?oldDescription } ." +
                                "  OPTIONAL { ?weLoveGreen ont:lieu ?oldLieu } ." +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(modifyQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("WeLoveGreen entity modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying WeLoveGreen entity: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/estc/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerEstc(@PathVariable String id, @RequestBody EstcDTO estcDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?estc ont:nom ?nom . " +
                                "  ?estc ont:description ?description . " +
                                "  ?estc ont:lieu ?lieu . " +
                                "  ?estc ont:impact_environnemental ?impactEnvironnemental . " +
                                "} " +
                                "INSERT { " +
                                "  ?estc ont:nom \"" + estcDTO.getNom() + "\" . " +
                                "  ?estc ont:description \"" + estcDTO.getDescription() + "\" . " +
                                "  ?estc ont:lieu \"" + estcDTO.getLieu() + "\" . " +
                                "  ?estc ont:impact_environnemental \"" + estcDTO.getImpactEnvironnemental() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#ESTC_\", \"" + id + "\")) AS ?estc) . " +
                                "  OPTIONAL { ?estc ont:nom ?nom . } " +
                                "  OPTIONAL { ?estc ont:description ?description . } " +
                                "  OPTIONAL { ?estc ont:lieu ?lieu . } " +
                                "  OPTIONAL { ?estc ont:impact_environnemental ?impactEnvironnemental . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("ESTC updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating ESTC: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/yuga/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerYuga(@PathVariable String id, @RequestBody YugaDTO yugaDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?yuga ont:titre ?titre . " +
                                "  ?yuga ont:description ?description . " +
                                "} " +
                                "INSERT { " +
                                "  ?yuga ont:titre \"" + yugaDTO.getTitre() + "\" . " +
                                "  ?yuga ont:description \"" + yugaDTO.getDescription() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Yuga_\", \"" + id + "\")) AS ?yuga) . " +
                                "  OPTIONAL { ?yuga ont:titre ?titre . } " +
                                "  OPTIONAL { ?yuga ont:description ?description . } " +

                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Yuga updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Yuga: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modifyMuseeLouvre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyMuseeLouvre(@PathVariable("id") String museeId, @RequestBody MuseeLouvreDTO museeLouvreDto) {
        if (model != null) {
            try {
                // Construct the URI for the MuseeLouvre based on the ID
                String museeUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + museeId;

                // Ensure the MuseeLouvre resource exists
                Resource museeResource = model.getResource(museeUri);
                if (museeResource == null) {
                    return new ResponseEntity<>("Musee Louvre not found", HttpStatus.NOT_FOUND);
                }

                // Define the SPARQL DELETE/INSERT query
                String modifyQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  <" + museeUri + "> ont:titre ?oldTitre . " +
                                "  <" + museeUri + "> ont:description ?oldDescription . " +
                                "} " +
                                "INSERT { " +
                                "  <" + museeUri + "> ont:titre \"" + museeLouvreDto.getTitre() + "\" . " +
                                "  <" + museeUri + "> ont:description \"" + museeLouvreDto.getDescription() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(<" + museeUri + "> AS ?musee) ." +
                                "  OPTIONAL { ?musee ont:titre ?oldTitre } ." +
                                "  OPTIONAL { ?musee ont:description ?oldDescription } ." +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(modifyQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Musee Louvre modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying Musee Louvre: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/pollution/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerPollution(@PathVariable String id, @RequestBody PollutionDTO pollutionDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?pollution ont:sujet ?sujet . " +
                                "  ?pollution ont:description ?description . " +
                                "  ?pollution ont:status ?status . " +
                                "  ?pollution ont:date_reclamation ?dateReclamation . " +
                                "  ?pollution ont:date_traitement ?dateTraitement . " +
                                "} " +
                                "INSERT { " +
                                "  ?pollution ont:sujet \"" + pollutionDTO.getSujet() + "\" . " +
                                "  ?pollution ont:description \"" + pollutionDTO.getDescription() + "\" . " +
                                "  ?pollution ont:status \"" + pollutionDTO.getStatus() + "\" . " +
                                "  ?pollution ont:date_reclamation \"" + pollutionDTO.getDateReclamation() + "\" . " +
                                "  ?pollution ont:date_traitement \"" + pollutionDTO.getDateTraitement() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Polution_\", \"" + id + "\")) AS ?pollution) . " +
                                "  OPTIONAL { ?pollution ont:sujet ?sujet . } " +
                                "  OPTIONAL { ?pollution ont:description ?description . } " +
                                "  OPTIONAL { ?pollution ont:status ?status . } " +
                                "  OPTIONAL { ?pollution ont:date_reclamation ?dateReclamation . } " +
                                "  OPTIONAL { ?pollution ont:date_traitement ?dateTraitement . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Pollution updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Pollution: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/securite/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> editerSecurite(@PathVariable String id, @RequestBody SecuriteDTO securiteDTO) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String updateQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?securite ont:sujet ?sujet . " +
                                "  ?securite ont:description ?description . " +
                                "  ?securite ont:status ?status . " +
                                "  ?securite ont:date_reclamation ?dateReclamation . " +
                                "  ?securite ont:date_traitement ?dateTraitement . " +
                                "} " +
                                "INSERT { " +
                                "  ?securite ont:sujet \"" + securiteDTO.getSujet() + "\" . " +
                                "  ?securite ont:description \"" + securiteDTO.getDescription() + "\" . " +
                                "  ?securite ont:status \"" + securiteDTO.getStatus() + "\" . " +
                                "  ?securite ont:date_reclamation \"" + securiteDTO.getDateReclamation() + "\" . " +
                                "  ?securite ont:date_traitement \"" + securiteDTO.getDateTraitement() + "\" . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#securite_\", \"" + id + "\")) AS ?securite) . " +
                                "  OPTIONAL { ?securite ont:sujet ?sujet . } " +
                                "  OPTIONAL { ?securite ont:description ?description . } " +
                                "  OPTIONAL { ?securite ont:status ?status . } " +
                                "  OPTIONAL { ?securite ont:date_reclamation ?dateReclamation . } " +
                                "  OPTIONAL { ?securite ont:date_traitement ?dateTraitement . } " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Securite updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Securite: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteAvion/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteAvion(@PathVariable("id") String avionId) {
        // Construire l'URI de l'avion à partir de l'ID
        String avionUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + avionId;

        System.out.println("Demande reçue de suppression de l'avion : " + avionUri);

        if (model != null) {
            try {
                // Définir la requête SPARQL DELETE pour supprimer l'avion
                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE WHERE { " +
                                " <" + avionUri + "> ?p ?o ." +
                                "}";

                // Créer et exécuter la demande de mise à jour
                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, model);

                // Enregistrer le modèle mis à jour dans le fichier d'ontologie
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Avion supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de l'avion : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle à partir de l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bateaux/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerBateau(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?bateau ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#bateau_\", \"" + id + "\")) AS ?bateau) . " +
                                "  ?bateau ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Bateau supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression du bateau: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/bus/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerBus(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?bus ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#bus_\", \"" + id + "\")) AS ?bus) . " +
                                "  ?bus ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Bus supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression du bus: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/fedex/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerFedEx(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?fedex ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#FedEx\", \"" + id + "\")) AS ?fedex) . " +
                                "  ?fedex ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("FedEx supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de FedEx: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteBooking/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") String bookingId) {
        // Construct the URI for the Booking entity
        String bookingUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Booking" + bookingId;

        System.out.println("Demande reçue pour supprimer le booking : " + bookingUri);

        if (model != null) {
            try {
                // SPARQL DELETE query to remove the Booking entity
                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE WHERE { " +
                                "  <" + bookingUri + "> ?p ?o . " +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Booking supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression du Booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle à partir de l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/randoloup/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerRandoloup(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?randoloup ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Randoloup_\", \"" + id + "\")) AS ?randoloup) . " +
                                "  ?randoloup ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Randoloup supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de Randoloup: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteWeLoveGreen/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteWeLoveGreen(@PathVariable("id") String weLoveGreenId) {
        // Construct the URI for the WeLoveGreen entity based on the provided ID
        String weLoveGreenUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + weLoveGreenId;

        System.out.println("Request received to delete WeLoveGreen entity: " + weLoveGreenUri);

        if (model != null) {
            try {
                // Define the SPARQL DELETE query to delete the WeLoveGreen entity
                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE WHERE { " +
                                " <" + weLoveGreenUri + "> ?p ?o ." +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("WeLoveGreen entity deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error deleting WeLoveGreen entity: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/estc/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerESTC(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?estc ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#ESTC_\", \"" + id + "\")) AS ?estc) . " +
                                "  ?estc ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("ESTC supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de ESTC: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/yuga/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerYuga(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?yuga ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Yuga_\", \"" + id + "\")) AS ?yuga) . " +
                                "  ?yuga ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Yuga supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de Yuga: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteMuseeLouvre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteMuseeLouvre(@PathVariable("id") String museeId) {
        // Construct the URI of the MuseeLouvre from the provided ID
        String museeUri = "http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#" + museeId;

        System.out.println("Demande reçue de suppression du musée : " + museeUri);

        if (model != null) {
            try {
                // Define the SPARQL DELETE query to delete the MuseeLouvre
                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE WHERE { " +
                                " <" + museeUri + "> ?p ?o ." +
                                "}";

                // Create and execute the update request
                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, model);

                // Save the updated model to the ontology file
                JenaEngine.saveModel(model, "data/ontology.owl");

                return new ResponseEntity<>("Musée supprimé avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression du musée : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle à partir de l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/pollution/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerPollution(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?pollution ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Pollution_\", \"" + id + "\")) AS ?pollution) . " +
                                "  ?pollution ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Pollution supprimée avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de Pollution: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/securite/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> supprimerSecurite(@PathVariable String id) {
        if (model != null) {
            try {
                Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

                String deleteQuery =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "DELETE { " +
                                "  ?securite ?p ?o . " +
                                "} " +
                                "WHERE { " +
                                "  BIND(IRI(CONCAT(\"http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#Securite_\", \"" + id + "\")) AS ?securite) . " +
                                "  ?securite ?p ?o . " +
                                "}";

                UpdateRequest updateRequest = UpdateFactory.create(deleteQuery);
                UpdateAction.execute(updateRequest, inferedModel);

                JenaEngine.saveModel(inferedModel, "data/ontology.owl");

                return new ResponseEntity<>("Sécurité supprimée avec succès", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de la suppression de Sécurité: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle depuis l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/searchByPrix/{prix}")

    public ResponseEntity<List<AvionDTO>> searchAvionByPrix(@PathVariable("prix") Integer prix) {
        List<AvionDTO> avions = new ArrayList<>();

        if (model != null) {
            try {
                String queryStr =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "SELECT ?avion ?description WHERE { " +
                                "  ?avion rdf:type ont:avion . " +
                                "  ?avion ont:prix " + prix + " . " +
                                "  ?avion ont:description ?description . " +
                                "}";

                // Directly create the QueryExecution with queryStr and model
                try (QueryExecution qe = QueryExecutionFactory.create(queryStr, model)) {
                    ResultSet results = qe.execSelect();

                    while (results.hasNext()) {
                        QuerySolution solution = results.nextSolution();
                        AvionDTO avionDTO = new AvionDTO();
                        avionDTO.setAvion(solution.getResource("avion").getURI());
                        avionDTO.setPrix(prix);
                        avionDTO.setDescription(solution.getLiteral("description").getString());
                        avions.add(avionDTO);
                    }
                }

                return new ResponseEntity<>(avions, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByNom/{nom}")
    public ResponseEntity<List<WeLoveGreenDTO>> searchWeLoveGreenByNom(@PathVariable("nom") String nom) {
        List<WeLoveGreenDTO> weLoveGreens = new ArrayList<>();

        if (model != null) {
            try {
                String queryStr =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "SELECT ?WeLoveGreen ?description ?lieu WHERE { " +
                                "  ?WeLoveGreen rdf:type ont:WeLoveGreen . " +
                                "  ?WeLoveGreen ont:nom \"" + nom + "\" . " +
                                "  ?WeLoveGreen ont:description ?description . " +
                                "  ?WeLoveGreen ont:lieu ?lieu . " +
                                "}";

                // Directly create the QueryExecution with queryStr and model
                try (QueryExecution qe = QueryExecutionFactory.create(queryStr, model)) {
                    ResultSet results = qe.execSelect();

                    while (results.hasNext()) {
                        QuerySolution solution = results.nextSolution();
                        WeLoveGreenDTO weLoveGreenDTO = new WeLoveGreenDTO();
                        weLoveGreenDTO.setNom(nom);
                        weLoveGreenDTO.setDescription(solution.getLiteral("description").getString());
                        weLoveGreenDTO.setLieu(solution.getLiteral("lieu").getString());
                        weLoveGreens.add(weLoveGreenDTO);
                    }
                }

                return new ResponseEntity<>(weLoveGreens, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByTitre/{titre}")
    public ResponseEntity<List<MuseeLouvreDTO>> searchMuseeLouvreByTitre(@PathVariable("titre") String titre) {
        List<MuseeLouvreDTO> museeLouvreList = new ArrayList<>();

        if (model != null) {
            try {
                String queryStr =
                        "PREFIX ont: <http://www.semanticweb.org/msi/ontologies/2024/8/untitled-ontology-4#> " +
                                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                "SELECT ?musee ?description WHERE { " +
                                "  ?musee rdf:type ont:MuseeLouvre . " +
                                "  ?musee ont:titre \"" + titre + "\" . " +
                                "  ?musee ont:description ?description . " +
                                "}";

                // Directly create the QueryExecution with queryStr and model
                try (QueryExecution qe = QueryExecutionFactory.create(queryStr, model)) {
                    ResultSet results = qe.execSelect();

                    while (results.hasNext()) {
                        QuerySolution solution = results.nextSolution();
                        MuseeLouvreDTO museeLouvreDTO = new MuseeLouvreDTO();
                        museeLouvreDTO.setTitre(titre);
                        museeLouvreDTO.setDescription(solution.getLiteral("description").getString());
                        museeLouvreList.add(museeLouvreDTO);
                    }
                }

                return new ResponseEntity<>(museeLouvreList, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



