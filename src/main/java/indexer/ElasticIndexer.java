package indexer;

import java.io.IOException;
import java.util.Map;

import avro.shaded.com.google.common.collect.ImmutableMap;
import bills.billsprocesser.utils.Converter;
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
        ImmutableMap params = params().build();
        Response response = client.performRequest("PUT", "/billsindex/bills/" + bill.getId(), params, entity(Converter.fromAvroBillToModelBill(bill)));
    }

    public void indexDocument(bills.billsprocesser.model.Bill bill) throws IOException {
        RestClient client = ElasticClient.createClient();
        ImmutableMap params = params().build();
        Response response = client.performRequest("PUT", "/billsindex/bills/" + bill.getId(), params, entity(bill));
    }

    protected String toJson(Map<String, Object> object) {
        try {
            return mapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: this is not generic...so one day has to be made more generic
    protected String toJson(bills.billsprocesser.model.Bill object) {
        try {
            return mapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpEntity entity(Map<String, Object> object) {
        return new StringEntity(toJson(object), ContentType.APPLICATION_JSON);
    }

    protected HttpEntity entity(bills.billsprocesser.model.Bill object) {
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
