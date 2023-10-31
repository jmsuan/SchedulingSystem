package lanej.schedulingsystem.model;

public class Contact {
    private int contactId;
    private String contactName;
    private String email;
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getContactId() {
        return contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString() {
        return ((Integer)contactId).toString();
        //return "Name: " + contactName + ", Email: " + email;
    }
}
