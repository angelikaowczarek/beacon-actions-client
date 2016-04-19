package com.github.angelikaowczarek;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class AddOperationPerformer extends OperationPerformer {
    private String uniqueId;
    private String actionType;
    private String payload;
    private String proximity;
    private HttpsURLConnection connection;
    private AddOperationParametersParser addOperationParametersParser;

    public AddOperationPerformer(String apiKey) {
        super(apiKey);
    }

    // Syntax: java Main apiKey ADD uniqueId actionType payload proximity
    public void perform(List<String> parameters) throws IOException {
        addOperationParametersParser = new AddOperationParametersParser(parameters);
        addOperationParametersParser.validateParameters();

        uniqueId = addOperationParametersParser.getUniqueId();
        actionType = addOperationParametersParser.getActionType();
        payload = addOperationParametersParser.getPayload();
        proximity = addOperationParametersParser.getProximity();
        url = new URL("https://api.kontakt.io/action/create");

        createConnection();
        connection.connect();

        writeDataToConnectionStream();

        throwExceptionIfFailed();
        printOutputIfSuccess();

        connection.disconnect();
    }

    private void printOutputIfSuccess() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output;
        System.out.println("Output from server... \n");
        while ((output = bufferedReader.readLine()) != null) {
            System.out.println(output);
        }
    }

    private void throwExceptionIfFailed() throws IOException {
        if (connection.getResponseCode() != HttpsURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed: Server responsed HTTP error code: "
                    + connection.getResponseCode());
        }
    }

    private void writeDataToConnectionStream() throws IOException {
        OutputStream os = connection.getOutputStream();
        String input;

        if (actionType.equals("CONTENT")) {
            input = "uniqueId=" + uniqueId + "&proximity=" + proximity + "&actionType=" + actionType + "&file=" + payload;
        } else
            input = "uniqueId=" + uniqueId + "&proximity=" + proximity + "&actionType=" + actionType + "&url=" + payload;

        os.write(input.getBytes());
        os.flush();
    }

    private void createConnection() throws IOException {
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Api-Key", apiKey);
        connection.setRequestProperty("Accept", "application/vnd.com.kontakt+json");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
    }
}
