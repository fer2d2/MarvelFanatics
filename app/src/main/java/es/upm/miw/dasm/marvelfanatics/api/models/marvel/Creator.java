package es.upm.miw.dasm.marvelfanatics.api.models.marvel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Creator {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Creator{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static Creator parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Creator creator = gson.fromJson(response, Creator.class);
        return creator;
    }
}
