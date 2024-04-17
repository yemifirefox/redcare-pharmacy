package util;

import com.redcare.pharmacy.dto.Repository;

import java.util.List;

public class TestData {
    public static List<Repository.Item> buildResponse(String date) {
        var first = new Repository.Item(1111, "name_1", "fullname_1", false, "htmlUrl",
                "gitUrl", "java", date, "234");
        var second = new Repository.Item(1110, "name_2", "fullname_2", true, "htmlUrl",
                "gitUrl", "java", date, "211");
        return List.of(first, second);
    }
}
