package models;

import play.db.jpa.Model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Model
{

    public String name;
    public String email;
    public String password;
    public String address;
    public String gender;
    public Double height;
    public Double startingWeight;





    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessmentList = new ArrayList<Assessment>();




    public Member(String name, String email, String password, String address, String gender, Double  height, Double startingWeight)
    {

        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.height = height;
        this.startingWeight = startingWeight;

    }


    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }


    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
}
