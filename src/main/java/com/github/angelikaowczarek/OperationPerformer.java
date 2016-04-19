package com.github.angelikaowczarek;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.List;

abstract class OperationPerformer {
    protected String apiKey;
    protected URL url;
    protected HttpsURLConnection connection;

    public OperationPerformer(String apiKey) {
        this.apiKey = apiKey;
    }

    abstract void perform(List<String> parameters) throws IOException;
}
