package charming_star;

public class Member {
    private int id;
    private String name;
    private int age;
    private String membershipType;
    private String joinDate;

    public Member(int id, String name, int age, String membershipType, String joinDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
        this.joinDate = joinDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getJoinDate() {
        return joinDate;
    }
}
