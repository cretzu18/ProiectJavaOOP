public class Admin extends User{
    Admin() {
        super("admin", "admin@delivery.ro", "1111", "admin");
    }

    public Admin(String nume, String email, String telefon, String parola) {
        super(nume, email, telefon, parola);
    }
}
