package vn.hoidanit.laptopshop.domain;

public class User {
    public long id;

    public String email;

    public String password;

    public String fullName;

    public String address;

    public String phone;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User [getId()=" + getId() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
                + ", getFullName()=" + getFullName() + ", getAddress()=" + getAddress() + ", getPhone()=" + getPhone()
                + "]";
    }

}
