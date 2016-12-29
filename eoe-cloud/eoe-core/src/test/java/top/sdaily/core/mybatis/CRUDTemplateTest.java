package top.sdaily.core.mybatis;

import org.junit.BeforeClass;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soya on 2016/11/17.
 */
public class CRUDTemplateTest {

    private static CRUDTemplate crudTemplate;

    @BeforeClass
    public static void setUp() throws Exception {
        crudTemplate = new CRUDTemplate();
    }

    @org.junit.Test
    public void testFindList() throws Exception {
        System.out.println(crudTemplate.findList(new Conditions(User.class)));
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setId("1");
        user.setAddress("st.01 a.21");
        user.setLastName("sun");
        user.setFirstName("dongshan");
        System.out.println(crudTemplate.insert(user));
    }

    @org.junit.Test
    public void testInsertBatch() throws Exception {

        User user1 = new User();
        user1.setId("1");
        user1.setAddress("st.01 a.21");
        user1.setLastName("sun");
        user1.setFirstName("dongshan");

        User user2 = new User();
        user2.setId("2");
        user2.setAddress("st.01 a.21");
        user2.setLastName("sun");
        user2.setFirstName("zhixia");

        ArrayList arrayList = new ArrayList();
        arrayList.add(user1);
        arrayList.add(user2);

        Map map = new HashMap();
        map.put("list",arrayList);

        System.out.println(crudTemplate.insertBatch(map));

    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setId("1");
        user.setAddress("st.01 a.21");
        user.setLastName("sun");
        user.setFirstName("dongshan");
        System.out.println(crudTemplate.update(user));
    }
}

@Table(name = "t_user")
class User{
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}