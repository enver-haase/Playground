package com.example.application.service;

import com.example.application.data.entity.Client;
import com.example.application.views.grid.GridView;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.SerializablePredicate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class ClientService {

    private final int NUM_ENTRIES = 200000;

    public int size(Query<Client, SerializablePredicate<Client>> query){
        if (query.getFilter().isPresent()) {
            GridView.MyFilterValueHolder myFilterValueHolder = (GridView.MyFilterValueHolder) query.getFilter().get();
            if (myFilterValueHolder.isEffective()) {
                return Math.toIntExact(fetch(query).count());
            }
            else {
                return NUM_ENTRIES;
            }
        }
        else{
            return NUM_ENTRIES;
        }
    }

    public Stream<Client> fetch(Query<Client, SerializablePredicate<Client>> query){
        final int offset = query.getOffset();
        final int limit = query.getLimit();

        Stream<Client> stream = Stream.iterate( createClient(offset), client -> client.getId() < NUM_ENTRIES, item -> createClient(item.getId()+1) );
        if (query.getFilter().isPresent()){
            stream = stream.filter(query.getFilter().get());
        }

        return stream.limit(limit);
    }


    // We have have a generational approach here as we do not have a real DB
    private Client createClient(int id) {

        System.out.println("Creating client "+id);

        Client c = new Client();
        c.setId(id);

        boolean woman = (id % 2 == 0);
        String image = "https://randomuser.me/api/portraits/"+(woman?"wo":"")+"men/"+id%100+".jpg";
        c.setImg(image);

        c.setClient(fetchClientName(id));

        c.setAmount(id * 16.2434d);

        String[] statuses = {"Pending", "Success", "Error"};
        c.setStatus(statuses[id % 3]);

        String date = LocalDate.of(1900+id%100, 1+id%12, 1+id%28).toString();
        c.setDate(date);

        return c;
    }


    private String fetchClientName(int id) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random(id); // _pseudo_ random only! Same name for same Id / seed.

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
