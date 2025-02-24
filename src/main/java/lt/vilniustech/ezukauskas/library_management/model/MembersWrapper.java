package lt.vilniustech.ezukauskas.library_management.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "members")
public class MembersWrapper {

    private List<Member> members;

    @XmlElement(name = "member")
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
