import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.List;

public class FoodDeliveryService {
    private Map<String, User> utilizatori = new HashMap<>();
    private TreeSet<Restaurant> restaurante = new TreeSet<>();
    private List<Comanda> comenzi = new ArrayList<>();
    private User userLogat = null;

    public User getUserLogat() {
        return userLogat;
    }

    // Creaza un user nou si il adauga in lista
    public void inregistrareUser(User u) {
        // Verifica daca exista deja un cont asociat email-ului introdus
        if (utilizatori.containsKey(u.getEmail())) {
            System.out.println("Email-ul " + u.getEmail() + " este deja utilizat pentru alt cont!");
            return;
        }

        utilizatori.put(u.getEmail(), u);
        System.out.println("Utilizatorul " + u.getNume() + " (" + u.getClass().getSimpleName() + ") inregistrat cu succes.");;
    }

    // Realizeaza conectarea la cont pe baza email-ului
    public boolean login(String email, String parola) {
        User u = utilizatori.get(email);
        if (u != null && u.verificaParola(parola)) {
            userLogat = u;
            System.out.println("Logarea a avut succes! Bine ai revenit, " + userLogat.getNume() + "!");
            return true;
        }
        System.out.println("Logarea a esuat! Email sau parola incorecta.");
        return false;
    }

    // Deconecteaza user-ul curent
    public void logout() {
        if (userLogat != null) {
            userLogat = null;
            System.out.println("Logout reusit!");
            return;
        }

        System.out.println("Nu este niciun cont logat.");
    }

    // Afiseaza restaurantele pe baza rating-ului
    public void afiseazaRestaurante() {
        if (restaurante.isEmpty()) {
            System.out.println("Nu exista restaurante.");
            return;
        }

        System.out.println("\n--- Lista Restaurante Sortate Dupa Rating ---\n");
        for (Restaurant r: restaurante) {
            System.out.println(r);
        }
    }

    // Afiseaza toate produsele specifice numelui restaurantului
    public void afiseazaProduse(String numeRestaurant) {
        Restaurant restaurant = null;
        for (Restaurant r: restaurante) {
            if (r.getNume().equalsIgnoreCase(numeRestaurant)) {
                restaurant = r;
                break;
            }
        }

        if (restaurant == null) {
            System.out.println("Eroare: restaurantul nu exista!");
            return;
        }

        List<Produs> meniu = restaurant.getMeniu();
        if (meniu.isEmpty()) {
            System.out.println("Restaurantul" + restaurant.getNume() + " nu are prdouse!");
        } else {
            System.out.println("\n--- Meniu " + restaurant.getNume() + " ---\n");
            for (Produs p: meniu) {
                System.out.println("- " + p);
            }
        }
    }

    // OPERATII ADMIN
    public void adminVizualizareUtilizatori() {
        if (userLogat == null || !(userLogat instanceof Admin)) {
            System.out.println("Acces Interzis! Doar Adminul poate vedea utilizatorii!");
            return;
        }

        if (utilizatori.isEmpty()) {
            System.out.println("Nu exista utilizatori creati.");
            return;
        }

        System.out.println("\n======= LISTA UTILIZATORI SISTEM =======");
        System.out.printf("%-15s | %-20s | %-15s | %-10s\n", "NUME", "EMAIL", "TELEFON", "ROL");
        System.out.println("------------------------------------------------------------");

        for (User u : utilizatori.values()) {
            String rol = u.getClass().getSimpleName();

            System.out.printf("%-15s | %-20s | %-15s | %-10s\n",
                    u.getNume(),
                    u.getEmail(),
                    u.getTelefon(),
                    rol);
        }
        System.out.println("------------------------------------------------------------\n");
    }

    public void adminAdaugaRestaurant(Restaurant r) {
        if (userLogat == null || !(userLogat instanceof Admin)) {
            System.out.println("Acces Interzis! Doar Adminul poate introduce restaurante!");
            return;
        }

        restaurante.add(r);
    }

    public void adminAdaugaProdus(String numeRestaurant, Produs produs) {
        if (userLogat == null || !(userLogat instanceof Admin)) {
            System.out.println("Acces Interzis! Doar Adminul poate introduce produse!");
            return;
        }

        Restaurant restaurant = null;
        for (Restaurant r: restaurante) {
            if (r.getNume().equalsIgnoreCase(numeRestaurant)) {
                restaurant = r;
                break;
            }
        }

        if (restaurant != null) {
            produs.setRestaurant(restaurant);
            restaurant.adaugaProdus(produs);

            System.out.println("Produs introduc cu succes!");
        } else {
            System.out.println("Eroare: restaurantul nu exista!");
        }
    }
    // END OPERATII ADMIN

    // OPERATII CLIENT
    public void clientVizualizareCos() {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a acessa cosul de cumparaturi!");
            return;
        }

        Client client = (Client) userLogat;
        Map<Produs, Integer> cos = client.getCosCumparaturi();

        if (cos.isEmpty()) {
            System.out.println("\nCosul este gol.\n");
            return;
        }

        System.out.println("\n--- Cosul De Cumparaturi ---\n");
        double totalCos = 0;
        for (Map.Entry<Produs, Integer> entry: cos.entrySet()) {
            Produs p = entry.getKey();
            int cantitate = entry.getValue();
            double subtotal = p.getPret() * cantitate;
            totalCos += subtotal;
            System.out.printf("- %s x %d buc. | Subtotal: %.2f RON\n", p.getNume(), cantitate, subtotal);
        }

        System.out.printf("TOTAL: %.2f RON\n", totalCos);
        System.out.println("------------------------------\n\n");
    }

    public void clientVizualizareIstoric() {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a acessa cosul de cumparaturi!");
            return;
        }

        Client client = (Client) userLogat;
        List<Comanda> istoric = client.getIstoricComenzi();

        if (istoric.isEmpty()) {
            System.out.println("\nNu ai nicio comanda plasata anterior.\n");
            return;
        }

        System.out.println("\n--- Istoric Comenzi ---\n");
        for(Comanda c: istoric) {
            System.out.println(c);
        }
    }

    public void clientVizualizareCurieri() {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a acessa cosul de cumparaturi!");
            return;
        }

        List<Curier> listaCurieri = new ArrayList<>();
        for (User u: utilizatori.values()) {
            if (u instanceof Curier) {
                listaCurieri.add((Curier) u);
            }
        }

        if (listaCurieri.isEmpty()) {
            System.out.println("Nu exista curieri in aplicatie!");
            return;
        }

        System.out.println("\n--- Lista Curieri ---\n");
        for (Curier c: listaCurieri) {
            System.out.println(c);
        }
    }

    public void clientAdaugaInCos(String numeRestaurant, String numeProdus, int cantitate) {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a acessa cosul de cumparaturi!");
            return;
        }

        Restaurant restaurant = null;
        for (Restaurant r: restaurante) {
            if (r.getNume().equalsIgnoreCase(numeRestaurant)) {
                restaurant = r;
                break;
            }
        }

        if (restaurant == null) {
            System.out.println("Eroare: restaurantul nu exista!");
            return;
        }

        Produs produs = null;
        for (Produs p: restaurant.getMeniu()) {
            if (p.getNume().equalsIgnoreCase(numeProdus)) {
                produs = p;
                break;
            }
        }

        if (produs == null) {
            System.out.println("Eroare: produsul nu exista!");
            return;
        }

        Client client = (Client) userLogat;
        client.adaugaInCos(produs, cantitate);
        System.out.println("Produsul a fost adaugat cu succes in cos!");;
    }

    void clientPlaseazaComanda(Adresa adresa) {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a acessa cosul de cumparaturi!");
            return;
        }

        Client client = (Client) userLogat;
        Map<Produs, Integer> cos = client.getCosCumparaturi();

        if (cos.isEmpty()) {
            System.out.println("Eroare: Cosul tau este gol!");
            return;
        }

        // Separam produsele din cos pe restaurante
        // Folosind un Map: Restaurant -> Map(Produs -> Cantitate)
        Map<Restaurant, Map<Produs, Integer>> comenziPeRestaurante = new HashMap<>();

        for (Map.Entry<Produs, Integer> entry: cos.entrySet()) {
            Produs p = entry.getKey();
            int cantitate = entry.getValue();
            Restaurant r = p.getRestaurant();

            comenziPeRestaurante.putIfAbsent(r, new HashMap<>());
            comenziPeRestaurante.get(r).put(p, cantitate);
        }

        for (Restaurant r: comenziPeRestaurante.keySet()) {
            Map<Produs, Integer> produseSubcomanda = comenziPeRestaurante.get(r);
            Comanda comandaNoua = new Comanda(client, r, adresa, produseSubcomanda);
            this.comenzi.add(comandaNoua);
            client.adaugaComandaInIstoric(comandaNoua);
            System.out.println("Comanda " + comandaNoua.getIdComanda() + " a fost plasata cu succes!");
        }

        client.golesteCos();
    }

    public void clientLasaRating(String numeRestaurant, double rating) {
        if (userLogat == null || !(userLogat instanceof Client)) {
            System.out.println("Eroare: Trebuie sa fii logat ca si Client pentru a lasa o recenzie!");
            return;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("Eroare: Nota trebuie sa fie intre 1 si 5");
            return;
        }

        Client client = (Client) userLogat;

        boolean eligibil = false;
        for (Comanda c: client.getIstoricComenzi()) {
            if (c.getRestaurant().getNume().equalsIgnoreCase(numeRestaurant)) {
                if (c.getStatus().equals("FINALIZAT")) {
                    eligibil = true;
                    break;
                }
            }
        }

        if (!eligibil) {
            System.out.println("Eroare: Poti lasa un rating doar restaurantelor de la care ai facut cel putin o comanda!");
            return;
        }

        for (Restaurant r: restaurante) {
            if (r.getNume().equalsIgnoreCase(numeRestaurant)) {
                restaurante.remove(r);
                r.adaugaRecenzie(rating);
                restaurante.add(r);

                System.out.println("Rating-ul a fost inregistrat!");
                return;
            }
        }
    }
    // END ACTIUNI CLIENT

    // ACTIUNI CURIER
    public void curierPreiaComanda(int idComanda) {
        if (userLogat == null || !(userLogat instanceof Curier)) {
            System.out.println("Eroare: Doar curierul poate prelua comenzi!");
            return;
        }

        Curier curier = (Curier) userLogat;

        if (!curier.esteDisponibil()) {
            System.out.println("Eroare: Esti deja intr-o cursa! Finalizeaza comanda curenta inainte de a prelua alta.");
            return;
        }

        Comanda comanda = null;
        for (Comanda c: comenzi) {
            if (c.getIdComanda() == idComanda) {
                comanda = c;
                break;
            }
        }

        if (comanda == null) {
            System.out.println("Eroare: Comanda " + idComanda + " nu exista!");
            return;
        }

        if (!comanda.getStatus().equals("IN_ASTEPTARE")) {
            System.out.println("Eroare: Comanda " + idComanda + " nu este disponibila!");
        }

        comanda.setCurier(curier);
        comanda.setStatus("IN_LIVRARE");
        curier.setEsteDisponibil(false);
        curier.setComandaCurenta(comanda);

        System.out.println("Succes: Ai preluat Comanda " + idComanda);
    }

    public void curierFinalizeazaComanda() {
        if (userLogat == null || !(userLogat instanceof Curier)) {
            System.out.println("Eroare: Doar curierul poate finaliza comenzi!");
            return;
        }

        Curier curier = (Curier) userLogat;
        Comanda comanda = curier.getComandaCurenta();

        if (comanda == null) {
            System.out.println("Eroare: Nu ai nicio comanda in livrare!");
            return;
        }

        comanda.setStatus("FINALIZAT");
        curier.setComandaCurenta(null);
        curier.setEsteDisponibil(true);

        System.out.println("Succes: Comanda " + comanda.getIdComanda() + " a fost finalizata.");
    }

    public void curierAfiseazaComenziDisponibile() {
        boolean exista = false;
        for (Comanda c: comenzi) {
            if (c.getStatus().equals("IN_ASTEPTARE")) {
                System.out.println(c);
                exista = true;
            }
        }

        if (!exista) {
            System.out.println("Nu exista comenzi disponibile");
        }
    }
    // END ACTIUNI CURIER
}
