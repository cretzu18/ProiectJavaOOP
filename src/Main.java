import java.util.Scanner;

public class Main {
    private static FoodDeliveryService service = new FoodDeliveryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        populareDate(service);

        boolean running = true;
        while (running) {
            System.out.println("\n--- FOOD DELIVERY APP ---\n");
            System.out.println("1. Login");
            System.out.println("2. Creaza User Nou");
            System.out.println("3. Vezi restaurante");
            System.out.println("4. Vezi produse");
            System.out.println("0. Iesire");
            System.out.print("Optiune: ");

            try {
                int optiune = Integer.parseInt(scanner.nextLine());

                switch (optiune) {
                    case 1:
                        fereastraLogin();
                        break;
                    case 2:
                        fereastraInregistrare();
                        break;
                    case 3:
                        service.afiseazaRestaurante();
                        break;
                    case 4:
                        service.afiseazaRestaurante();
                        System.out.print("Restaurantul pentru care vrei sa vezi produsele: ");
                        String restaurant = scanner.nextLine();
                        service.afiseazaProduse(restaurant);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Optiunea trebuia sa fie un numar intreg.");
            }
        }
    }

    private static void fereastraLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Parola: ");
        String parola = scanner.nextLine();

        if (service.login(email, parola)) {
            User logat = service.getUserLogat();
            if (logat instanceof Admin) meniuAdmin();
            else if (logat instanceof Client) meniuClient();
            else if (logat instanceof Curier) meniuCurier();
        }
    }

    private static void fereastraInregistrare() {
        try {
            System.out.println("Tipul contului este: 1. Client sau 2. Curier ?");
            int optiune = Integer.parseInt(scanner.nextLine());
            if (optiune != 1 && optiune != 2) {
                System.out.println("Optiune invalida");
                return;
            }


            System.out.print("Nume: ");
            String nume = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Telefon: ");
            String telefon = scanner.nextLine();
            String tipVehicul = null;
            Adresa adresa = null;
            if (optiune == 1) {
                System.out.println("Adresa:");
                System.out.print("Oras: ");
                String oras = scanner.nextLine();
                System.out.print("Strada: ");
                String strada = scanner.nextLine();
                System.out.print("Numar: ");
                int numar = Integer.parseInt(scanner.nextLine());
                adresa = new Adresa(oras, strada, numar);
            } else {
                System.out.print("Tip vehicul: ");
                tipVehicul = scanner.nextLine();
            }
            System.out.print("Parola: ");
            String parola = scanner.nextLine();
            if (optiune == 1) {
                service.inregistrareUser(new Client(nume, email, telefon, parola, adresa));
            } else {
                service.inregistrareUser(new Curier(nume, email, telefon, parola, tipVehicul));
            }
        } catch (NumberFormatException e) {
                System.out.println("Optiunea trebuia sa fie un numar intreg.");
        }
    }

    private static void meniuAdmin() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n--- PANOU CONTROL ADMIN ---\n");
                System.out.println("1. Adauga Restaurant");
                System.out.println("2. Adauga Mancare intr-un restaurant");
                System.out.println("3. Adauga Bautura intr-un restaurant");
                System.out.println("4. Vezi toti utlizatorii");
                System.out.println("5. Vezi restaurante");
                System.out.println("6. Vezi produse");
                System.out.println("0. Logout");
                System.out.print("Optiune: ");

                int optiune = Integer.parseInt(scanner.nextLine());
                switch (optiune) {
                    case 1:
                        System.out.print("Nume Restaurant: ");
                        String nume = scanner.nextLine();
                        System.out.print("Specific: ");
                        String specific = scanner.nextLine();
                        service.adminAdaugaRestaurant(new Restaurant(nume, specific));
                        System.out.println("Restaurantul a fost adaugat cu succes!");
                        break;

                    case 2:
                        service.afiseazaRestaurante();
                        System.out.print("Numele restaurantului pentru produs: ");
                        String numeRestaurant = scanner.nextLine();
                        System.out.print("Nume mancare: ");
                        String numeMancare = scanner.nextLine();
                        System.out.print("Descriere: ");
                        String descMancare = scanner.nextLine();
                        System.out.print("Pret: ");
                        double pretMancare = Double.parseDouble(scanner.nextLine());
                        System.out.print("Calorii: ");
                        int calMancare = Integer.parseInt(scanner.nextLine());
                        System.out.print("Gramaj (g): ");
                        double gramajMancare = Double.parseDouble(scanner.nextLine());

                        service.adminAdaugaProdus(numeRestaurant, new Mancare(numeMancare, descMancare, pretMancare, calMancare, gramajMancare));
                        break;

                    case 3:
                        service.afiseazaRestaurante();
                        System.out.print("Numele restaurantului pentru produs: ");
                        String numeRes = scanner.nextLine();
                        System.out.print("Nume Bautura: ");
                        String numeBautura = scanner.nextLine();
                        System.out.print("Descriere: ");
                        String descBautura = scanner.nextLine();
                        System.out.print("Pret: ");
                        double pretBautura = Double.parseDouble(scanner.nextLine());
                        System.out.print("Calorii: ");
                        int calBautura = Integer.parseInt(scanner.nextLine());
                        System.out.print("Volum (ml): ");
                        int volBautura = Integer.parseInt(scanner.nextLine());
                        System.out.print("Este alcoolica? (true/false): ");
                        boolean alcBautura = Boolean.parseBoolean(scanner.nextLine());

                        service.adminAdaugaProdus(numeRes, new Bautura(numeBautura, descBautura, pretBautura, calBautura, volBautura, alcBautura));
                        break;

                    case 4:
                        service.adminVizualizareUtilizatori();
                        break;

                    case 5:
                        service.afiseazaRestaurante();
                        break;

                    case 6:
                        service.afiseazaRestaurante();
                        System.out.print("Restaurantul pentru care vrei sa vezi produsele: ");
                        String restaurant = scanner.nextLine();
                        service.afiseazaProduse(restaurant);
                        break;

                    case 0:
                        service.logout();
                        running = false;
                        break;

                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Optiunea trebuia sa fie un numar intreg.");
            }
        }
    }

    private static void meniuClient() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n--- MENIU CLIENT ---\n");
                System.out.println("1. Vizualizeaza toate restaurantele");
                System.out.println("2. Vezi meniul unui restaurant");
                System.out.println("3. Adauga un produs in cos");
                System.out.println("4. Vezi cosul de cumparaturi");
                System.out.println("5. Plaseaza comanda");
                System.out.println("6. Vezi istoricul comenzilor mele");
                System.out.println("7. Vezi lista curierilor");
                System.out.println("8. Lasa un rating unui restaurant");
                System.out.println("0. Logout");
                System.out.print("Optiune: ");
                int optiune = Integer.parseInt(scanner.nextLine());

                switch (optiune) {
                    case 1:
                        service.afiseazaRestaurante();
                        break;

                    case 2:
                        service.afiseazaRestaurante();
                        System.out.print("Restaurantul pentru care vrei sa vezi produsele: ");
                        String restaurant = scanner.nextLine();
                        service.afiseazaProduse(restaurant);
                        break;

                    case 3:
                        service.afiseazaRestaurante();
                        System.out.print("Din ce restaurant? ");
                        String numeRestaurant = scanner.nextLine();
                        service.afiseazaProduse(numeRestaurant);
                        System.out.print("Ce produs doresti? ");
                        String numeProdus = scanner.nextLine();
                        System.out.print("Cantitate: ");
                        int cantitate = Integer.parseInt(scanner.nextLine());
                        service.clientAdaugaInCos(numeRestaurant, numeProdus, cantitate);
                        break;

                    case 4:
                        service.clientVizualizareCos();
                        break;

                    case 5:
                        Adresa adresaImplicita = ((Client) service.getUserLogat()).getAdresaImplicita();
                        System.out.println(adresaImplicita);
                        System.out.print("Folositi adresa implicita? (Da/Nu): ");
                        String raspuns = scanner.nextLine();
                        if (raspuns.equalsIgnoreCase("da")) {
                            service.clientPlaseazaComanda(adresaImplicita);
                        } else {
                            System.out.print("Oras: ");
                            String oras = scanner.nextLine();
                            System.out.print("Strada: ");
                            String strada = scanner.nextLine();
                            System.out.print("Numar: ");
                            int numar = Integer.parseInt(scanner.nextLine());

                            service.clientPlaseazaComanda(new Adresa(oras, strada, numar));
                        }
                        break;

                    case 6:
                        service.clientVizualizareIstoric();
                        break;

                    case 7:
                        service.clientVizualizareCurieri();
                        break;

                    case 8:
                        service.afiseazaRestaurante();
                        System.out.print("Numele restaurantului pentru rating: ");
                        String numeRes = scanner.nextLine();
                        System.out.print("Nota (1-5): ");
                        double nota = Double.parseDouble(scanner.nextLine());
                        service.clientLasaRating(numeRes, nota);
                        break;

                    case 0:
                        service.logout();
                        running = false;
                        break;

                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Optiunea trebuia sa fie un numar intreg.");
            }
        }
    }

    private static void meniuCurier() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n--- PANOU CONTROL CURIER ---\n");
                System.out.println("1. Vizualizeaza comenzi disponibile");
                System.out.println("2. Preia o comanda pentru livrare");
                System.out.println("3. Finalizeaza livrarea");
                System.out.println("0. Logout");
                System.out.print("Optiune: ");

                int optiune = Integer.parseInt(scanner.nextLine());
                switch (optiune) {
                    case 1:
                        service.curierAfiseazaComenziDisponibile();
                        break;

                    case 2:
                        service.curierAfiseazaComenziDisponibile();
                        System.out.print("Introdu ID-ul comenzii pe care vrei sa o preiei: ");
                        int idPreluare = Integer.parseInt(scanner.nextLine());
                        service.curierPreiaComanda(idPreluare);
                        break;

                    case 3:
                        service.curierFinalizeazaComanda();
                        break;

                    case 0:
                        service.logout();
                        running = false;
                        break;

                    default:
                        System.out.println("Optiune invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Optiunea trebuia sa fie un numar intreg.");
            }
        }
    }

    public static void populareDate(FoodDeliveryService service) {
        // Creare admin
        Admin admin = new Admin();
        service.inregistrareUser(admin);

        // Creare restaurante
        Restaurant r1 = new Restaurant("Pizza Hut", "Italian");
        Restaurant r2 = new Restaurant("Burger King", "Fast Food");
        Restaurant r3 = new Restaurant("Sushi Zen", "Japonez");

        // Adaugare produse ca si admin
        service.login("admin@delivery.ro", "admin");
        service.adminAdaugaRestaurant(r1);
        service.adminAdaugaRestaurant(r2);
        service.adminAdaugaRestaurant(r3);

        // Creare produse ca si admin
        // Pizza Hut
        service.adminAdaugaProdus("Pizza Hut", new Mancare("Margherita", "Sos rosii, mozzarella", 35.0, 800, 450.0));
        service.adminAdaugaProdus("Pizza Hut", new Mancare("Quattro Stagi", "De toate", 45.0, 950, 500.0));
        service.adminAdaugaProdus("Pizza Hut", new Bautura("Pepsi", "Carbogazoasa", 8.0, 150, 500, false));

        // Burger King
        service.adminAdaugaProdus("Burger King", new Mancare("Whopper", "Vita, salata", 32.0, 1100, 350.0));
        service.adminAdaugaProdus("Burger King", new Bautura("Bere Ursus", "Rece", 12.0, 200, 500, true));

        // Sushi Zen
        service.adminAdaugaProdus("Sushi Zen", new Mancare("Sushi California", "Somon, Orez", 25.0, 400, 150));

        // Creare client si curier
        Client maria = new Client("Maria", "maria@test.com", "0744123456", "pass123", new Adresa("Bucuresti", "Victoriei", 10));
        Curier ion = new Curier("Ion Livrator", "ion@delivery.ro", "0788999000", "ion123", "Bicicleta");

        service.inregistrareUser(maria);
        service.inregistrareUser(ion);

        service.logout();

        // Plasare comanda ca client
        service.login("maria@test.com", "pass123");
        service.clientAdaugaInCos("Pizza Hut", "Margherita", 2);
        service.clientAdaugaInCos("Burger King", "Bere Ursus", 1);
        service.clientAdaugaInCos("Sushi Zen", "Sushi California", 2);
        service.clientPlaseazaComanda(maria.getAdresaImplicita());
        service.logout();

        // Preluare comenzi si finalizarea lor ca curier
        service.login("ion@delivery.ro", "ion123");
        service.curierPreiaComanda(1000);
        service.curierFinalizeazaComanda();
        service.curierPreiaComanda(1001);
        service.curierFinalizeazaComanda();
        service.curierPreiaComanda(1002);
        service.curierFinalizeazaComanda();
        service.logout();

        // Rating pentru restaurante ca client
        service.login("maria@test.com", "pass123");
        service.clientLasaRating("Pizza Hut", 3.5);
        service.clientLasaRating("Burger King", 3);
        service.clientLasaRating("Sushi Zen", 4.5);
        service.logout();
    }
}