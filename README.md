CLASE:
- User (Abstract): String nume, String telefon, String email, String parola;
- Admin implements User;
- Client implements User + Adresa adresaImplicita, List<Comanda> istoricComenzi, Map<Produs, Integer> cosCumparaturi;
- Curier implements User + String tipVehicul, boolean esteDisponibil, Comanda comandaCurenta;
- Produs (Abstract) - String nume, String descriere, double pret, int calorii;
- Mancare implements Produs + double gramaj;
- Bautura implements Produs + int volum, boolean contineAlcool;
- Restaurant - String nume, String specific, double rating, int numarRecenzii, List<Produs> meniu;
- Adresa - String oras, String strada, int numar;
- Comanda - static int contorId, int idComanda, Client client, Adresa adresa, Curier curier, Restaurant restaurant, Map<Produs, Integer> produseComandate, String status, LocalDateTime dataPlasarii, double pretTotal;
- FoodDeliveryService - Map<String, User> utilizatori, TreeSet<Restaurant> restaurante, List<Comanda> comenzi, User userLogat; 
- Main.

ACTIUNI:
- Guest:
	1. Login
	2. Creaza user nou
	3. Vezi restaurante
	4. Vezi produse
	0. Exit
- Admin:
	1. Adauga Restaurant
	2. Adauga Mancare intr-un Restaurant
	3. Adauga Bautura intr-un Restaurant
	4. Vezi toti userii
	5. Vezi restaurante
	6. Vezi produse 
	0. Logout
- Client:
	1. Vezi restaurante
	2. Vezi produse
	3. Adauga un produs in cos
	4. Vezi cosul de cumparaturi
	5. Plaseaza comanda
	6. Vezi istoric comenzi
	7. Vezi lista curieri
	8. Lasa un rating
	0. Logout
- Curier:
	1. Vezi comenzi disponibile
	2. Preia o comanda
	3. Finalizeaza comanda
	0. Logout
