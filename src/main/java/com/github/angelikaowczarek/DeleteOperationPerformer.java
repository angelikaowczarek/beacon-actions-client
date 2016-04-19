package com.github.angelikaowczarek;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DeleteOperationPerformer extends OperationPerformer {
    private DeleteOperationParametersParser deleteOperationParametersParser;

    public DeleteOperationPerformer(String apiKey) {
        super(apiKey);
    }

    public void perform(List<String> parameters) throws IOException {
        deleteOperationParametersParser = new DeleteOperationParametersParser(parameters);
        deleteOperationParametersParser.validateParameters();
        String actionId = deleteOperationParametersParser.getActionId();

        url = createUrl();
        createConnection();
        connection.connect();
        writeDataToConnectionStream(actionId);

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
        if (connection.getResponseCode() != HttpsURLConnection.HTTP_CREATED && connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed: Server responded HTTP error code: "
                    + connection.getResponseCode());
        }
    }

    private void writeDataToConnectionStream(String actionId) throws IOException {
        String input = "actionId=" + actionId;
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
    }

    private URL createUrl() throws MalformedURLException {
        String urlString = "https://api.kontakt.io/action/delete?";
        return new URL(urlString);
    }

    private void createConnection() throws IOException {
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Api-Key", apiKey);
        connection.setRequestProperty("Accept", "application/vnd.com.kontakt+json");
        connection.setRequestMethod("POST");
    }
}
