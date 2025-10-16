import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Customer( String customerId, String name, String email, int age, String city, String registeredAt ) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
        this.registeredAt = LocalDate.parse(registeredAt, formatter).atStartOfDay();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", registeredAt=" + registeredAt +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
