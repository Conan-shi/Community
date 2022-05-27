package Actors;

public class OldMan {
    private String stewardAccount;

    public String getStewardAccount() {
        return stewardAccount;
    }

    public void setStewardAccount(String stewardAccount) {
        this.stewardAccount = stewardAccount;
    }

    private String id;

    private String account;

    private String name;

    private String gender;

    private String birthDate;

    private String telephoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public OldMan() {
    }

    public OldMan(String stewardAccount,  String account, String name, String gender, String birthDate, String telephoneNumber) {
        this.stewardAccount = stewardAccount;
        this.account = account;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "OldMan{" +
                "stewardAccount='" + stewardAccount + '\'' +
                ", id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }
}
