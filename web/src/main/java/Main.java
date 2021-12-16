import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main { // TODO: 05.12.2021 del

    public static void main(String[] args) {
        /*List<Tag> list = new ArrayList<>();
        list.add(new Tag(1));
        list.add(new Tag(2));
        list.add(new Tag(3));
        String str = new Gson().toJson(list);
        System.out.println(str);
        List<Tag> newList = new Gson().fromJson(str, new TypeToken<ArrayList<Tag>>(){}.getType());
        newList.forEach(System.out::println);*/
       /* GiftCertificate giftCertificate = GiftCertificate.builder().id(1).name("qwe").description("sdf")
                .duration(12).price(123).build();
        String str = new Gson().toJson(giftCertificate);
        System.out.println(str);
         giftCertificate = new Gson().fromJson(str, GiftCertificate.class);*/
        /*List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("q"));
        tagList.add(new Tag("Tesla"));
        Tag.GiftCertificateTag giftCertificateTag = new Tag.GiftCertificateTag(GiftCertificate.builder()
                .name("testN")
                .description("testD")
                .duration(12)
                .build(), tagList);
        String str = new Gson().toJson(giftCertificateTag);
        System.out.println(str);*/
    }
}
