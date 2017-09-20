package indexer;

import java.io.IOException;
import java.util.Map;

import avro.shaded.com.google.common.collect.ImmutableMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import elastic.ElasticClient;
import model.avro.Bill;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticIndexer {

    private static final ObjectMapper mapper = new ObjectMapper();


    public void indexDocument(Bill bill) throws IOException {
        RestClient client = ElasticClient.createClient();
        ImmutableMap params = params("pretty").build();
        Response response = client.performRequest("POST", "/bills", params, entity(bill));
    }


    protected String toJson(Map<String, Object> object) {
        try {
            return mapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    protected String toJson(Bill object) {
        try {
            return mapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpEntity entity(Map<String, Object> object) {
        return new StringEntity(toJson(object), ContentType.APPLICATION_JSON);
    }
    protected HttpEntity entity(Bill object) {
        return new StringEntity(toJson(object), ContentType.APPLICATION_JSON);
    }
    public static ImmutableMap.Builder params() {
        return ImmutableMap.builder();
    }

    public static ImmutableMap.Builder params(String name) {
        return params(name, "");
    }

    public static ImmutableMap.Builder params(String name, String value) {
        return params().put(name, value);
    }

}
