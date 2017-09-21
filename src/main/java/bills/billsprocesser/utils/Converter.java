package bills.billsprocesser.utils;

import java.util.ArrayList;
import java.util.List;

import model.avro.Bill;
import model.avro.Item;
import model.avro.User;

public class Converter {


    public static bills.billsprocesser.model.Bill fromAvroBillToModelBill(Bill avroBill) {

        bills.billsprocesser.model.Bill modelBill  = new bills.billsprocesser.model.Bill();
        modelBill.setTitle(avroBill.getTitle().toString());
        modelBill.setCreationTime(avroBill.getCreationTime());
        modelBill.setItems(fromAvroItemListToModelItemList(avroBill.getItems()));
        modelBill.setUser(fromAvroUserToModelUser(avroBill.getUser()));
        modelBill.setId(avroBill.getId());

        return modelBill;

    }

    public static bills.billsprocesser.model.Item fromAvroItemToModelItem(Item avroItem) {
        bills.billsprocesser.model.Item modelItem = new bills.billsprocesser.model.Item();
        modelItem.setName(avroItem.getName().toString());
        modelItem.setPrice(avroItem.getPrice());
        modelItem.setId(avroItem.getId());
        return  modelItem;
    }

    public static List<bills.billsprocesser.model.Item> fromAvroItemListToModelItemList(List<Item> avroItems) {
        List<bills.billsprocesser.model.Item> modelItems = new ArrayList<>();
        avroItems.forEach(i -> {
            modelItems.add(fromAvroItemToModelItem(i));
        });
        return modelItems;
    }

    public static bills.billsprocesser.model.User fromAvroUserToModelUser (User avroUser) {
        bills.billsprocesser.model.User modelUser = new bills.billsprocesser.model.User();
        modelUser.setFirstname(avroUser.getFirstname().toString());
        modelUser.setLastname(avroUser.getLastname().toString());
        modelUser.setAddress(avroUser.getAddress().toString());
        modelUser.setId(avroUser.getId());
        return modelUser;
    }



}
