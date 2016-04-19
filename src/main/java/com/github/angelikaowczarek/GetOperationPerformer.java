package com.github.angelikaowczarek;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GetOperationPerformer extends OperationPerformer {
    private String uniqueId;
    private GetOperationParametersParser getOperationParametersParser;

    public GetOperationPerformer(String apiKey) {
        super(apiKey);
    }

    public void perform(List<String> parameters) throws IOException {
        getOperationParametersParser = new GetOperationParametersParser(parameters);
        getOperationParametersParser.validateParameters();
        uniqueId = getOperationParametersParser.getUniqueId();

        url = createUrl();
        createConnection();
        connection.connect();

        throwExceptionIfFailed();
        JSONObject actionsObject = createActionsObject();
        findActions(actionsObject);

        connection.disconnect();
    }

    private JSONObject createActionsObject() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String actionsLine = bufferedReader.readLine();
        return new JSONObject(actionsLine);
    }

    private void throwExceptionIfFailed() throws IOException {
        if (connection.getResponseCode() != HttpsURLConnection.HTTP_CREATED && connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed: Server responded HTTP error code: "
                    + connection.getResponseCode());
        }
    }

    private void createConnection() throws IOException {
        connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Api-Key", apiKey);
        connection.setRequestProperty("Accept", "application/vnd.com.kontakt+json");
        connection.setRequestMethod("GET");
    }

    private URL createUrl() throws MalformedURLException {
        String urlParameters = "uniqueId=" + uniqueId;
        String urlString = "https://api.kontakt.io/action?" + urlParameters;
        return new URL(urlString);
    }

    private void findActions(JSONObject obj) {
        JSONArray actions = obj.getJSONArray("actions");
        boolean isAnyActionFound = false;
        JSONObject action;
        JSONArray devicesArray;

        for (int i = 0; i < actions.length(); i++) {

            action = actions.getJSONObject(i);
            devicesArray = action.getJSONArray("deviceUniqueIds");

            for (int j = 0; j < devicesArray.length(); j++) {

                if (devicesArray.getString(j).equals(uniqueId)) {
                    isAnyActionFound = true;
                    printAction(action);
                    break;
                }
            }
        }
        if (!isAnyActionFound) {
            System.out.println("No actions assigned to this device found");
            System.exit(1);
        }
    }

    private void printAction(JSONObject action) {
        String actionDescription = "";

        if (action.has("id"))
            actionDescription += "ID: " + action.getString("id") + "\n";

        if (action.has("actionType"))
            actionDescription += "TYPE: " + action.getString("actionType") + "\n";

        if (action.has("proximity"))
            actionDescription += "PROXIMITY: " + action.getString("proximity") + "\n";

        actionDescription += "DEVICE_UNIQUE_IDS: " + action.getJSONArray("deviceUniqueIds").toString() + "\n";

        if (action.has("content"))
            actionDescription += "CONTENT: " + action.getString("content") + "\n";

        if (action.has("contentCategory") && action.getString("actionType").equals("CONTENT"))
            actionDescription += "CONTENT_CATEGORY: " + action.getString("contentCategory") + "\n";

        if (action.has("url") && action.getString("actionType").equals("BROWSER"))
            actionDescription += "URL: " + action.getString("url") + "\n";

        System.out.println(actionDescription);
    }
}
