package no.ntnu.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import no.ntnu.entity.CarModels;
import no.ntnu.entity.Dealers;
import no.ntnu.entity.ExtraFeatures;
import no.ntnu.entity.Listings;
import no.ntnu.entity.Orders;
import no.ntnu.entity.Accounts;
import no.ntnu.entity.Users;
import no.ntnu.repository.AccountsRepository;
import no.ntnu.repository.CarModelsRepository;
import no.ntnu.repository.DealersRepository;
import no.ntnu.repository.ExtraFeaturesRepository;
import no.ntnu.repository.ListingsRepository;
import no.ntnu.repository.OrdersRepository;
import no.ntnu.repository.UsersRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

  private final AccountsRepository accountsRepository;
  private final UsersRepository usersRepository;
  private final DealersRepository dealersRepository;
  private final CarModelsRepository carModelsRepository;
  private final ExtraFeaturesRepository extraFeaturesRepository;
  private final ListingsRepository listingsRepository;
  private final OrdersRepository ordersRepository;

  public DataInitializer(
      AccountsRepository accountsRepository,
      UsersRepository usersRepository,
      DealersRepository dealersRepository,
      CarModelsRepository carModelsRepository,
      ExtraFeaturesRepository extraFeaturesRepository,
      ListingsRepository listingsRepository,
      OrdersRepository ordersRepository) {
    this.accountsRepository = accountsRepository;
    this.usersRepository = usersRepository;
    this.dealersRepository = dealersRepository;
    this.carModelsRepository = carModelsRepository;
    this.extraFeaturesRepository = extraFeaturesRepository;
    this.listingsRepository = listingsRepository;
    this.ordersRepository = ordersRepository;
  }

  // Seed state — set during run(), used across seed methods
  private ExtraFeatures bluetooth, dabRadio, heatedSeats, longRange, rearWheelDrive, fourWheelDrive, glassRoof,
      autonomousDriving, camera360, adaptiveCruiseControl, appleCarPlay, proPilotAssist, heatedSteeringWheel,
      navigation,
      cruiseControl, reversingCamera, panoramaRoof, hud, harmanKardonSound, parkingSensors, wirelessCharging,
      laneAssistance, ledHeadlights, androidAuto, leatherSeats, airConditioning, cdPlayer;
  private CarModels vwGolf, teslaModel3, teslaModelY, mercedesEqv, nissanLeaf, mazda2, bmwX3, skodaFabia, citroenEC4,
      renaultZoe, peugeot3008, peugeotIon;
  private Dealers smidigBilhandel, teslaCom, teslaAlesund, auto1Premium, finnNo, usedCarDealer, bilikist, premiumAuto,
      localDealer, citroenNorge, usedEvShop, bilcenter, evBudgetCars;
  private Users dave, alice, bob;
  private Listings soldGolf, soldLeaf, soldIon, transitTesla3, transitBmwX3, transitMercedesEqv, availableTesla3,
      availableModelY, availableMazda2, availableSkodaFabia, availableCitroenEC4, availableZoe, availablePeugeot3008;

  @Override
  @Transactional
  public void run(String... args) {
    if (extraFeaturesRepository.count() > 0) { // check to prevent running it twice
      return;
    }
    seedExtraFeatures();
    seedCarModels();
    seedDealers();
    seedUsers();
    seedListings();
    seedOrders();
    seedFavorites();
  }

  private void seedExtraFeatures() {
    bluetooth = createExtraFeature("Bluetooth", "Wireless audio and phone connectivity");
    dabRadio = createExtraFeature("DAB Radio", "Digital audio broadcasting for better sound quality");
    heatedSeats = createExtraFeature("Heated Seats", "Heated seats for comfort");
    longRange = createExtraFeature("Long Range", "Extended battery range");
    rearWheelDrive = createExtraFeature("Rear Wheel Drive", "Power delivered to the rear wheels");
    fourWheelDrive = createExtraFeature("Four-Wheel Drive", "Power delivered to all four wheels for better traction");
    glassRoof = createExtraFeature("Glass Roof", "Panoramic glass roof");
    autonomousDriving = createExtraFeature("Autonomous Driving", "Self-driving capability");
    camera360 = createExtraFeature("360° Camera", "Surround-view camera system");
    adaptiveCruiseControl = createExtraFeature("Adaptive Cruise Control",
        "Automatically adjusts speed to maintain a safe distance to the vehicle ahead");
    appleCarPlay = createExtraFeature("Apple CarPlay", "iPhone integration for infotainment");
    proPilotAssist = createExtraFeature("ProPilot Assist",
        "Semi-autonomous driving with steering, acceleration and braking assistance");
    heatedSteeringWheel = createExtraFeature("Heated Steering Wheel", "Steering wheel with built-in heating");
    navigation = createExtraFeature("Navigation", "Built-in GPS navigation system");
    cruiseControl = createExtraFeature("Cruise Control", "Maintains a set speed without pressing the accelerator");
    reversingCamera = createExtraFeature("Reversing Camera", "Rear-view camera for parking assistance");
    panoramaRoof = createExtraFeature("Panorama Roof", "Large panoramic sunroof spanning most of the roof");
    hud = createExtraFeature("Head-Up Display", "Projects speed and navigation info onto the windshield");
    harmanKardonSound = createExtraFeature("Harman Kardon Sound", "Premium surround sound system from Harman Kardon");
    parkingSensors = createExtraFeature("Parking Sensors", "Front and rear sensors for parking assistance");
    wirelessCharging = createExtraFeature("Wireless Charging", "Qi wireless charging pad for smartphones");
    laneAssistance = createExtraFeature("Lane Assistance", "Alerts and corrects when drifting out of lane");
    ledHeadlights = createExtraFeature("LED Headlights", "Energy-efficient LED headlights for improved visibility");
    androidAuto = createExtraFeature("Android Auto", "Android smartphone integration for infotainment");
    leatherSeats = createExtraFeature("Leather Seats", "Premium leather upholstery");
    airConditioning = createExtraFeature("Air Conditioning", "Climate control system");
    cdPlayer = createExtraFeature("CD Player", "Built-in CD player for audio playback");
  }

  private void seedCarModels() {
    vwGolf = createCarModel(
        "Volkswagen", "Golf",
        CarModels.CarType.HATCHBACK,
        2019, 5, CarModels.Transmission.MANUAL,
        CarModels.EnergySource.DIESEL,
        null, null, null, null, null, null,
        Set.of(bluetooth, dabRadio, heatedSeats));

    teslaModel3 = createCarModel(
        "Tesla", "Model 3",
        CarModels.CarType.SEDAN,
        2025, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, null, null, null, 210, new BigDecimal("6.1"),
        Set.of(longRange, rearWheelDrive));

    teslaModelY = createCarModel(
        "Tesla", "Model Y",
        CarModels.CarType.SUV,
        2022, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, null, null, null, null, null,
        Set.of(fourWheelDrive, glassRoof, autonomousDriving));

    mercedesEqv = createCarModel(
        "Mercedes-Benz", "EQV",
        CarModels.CarType.MINIVAN,
        2021, 7, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, new BigDecimal("90.0"), 330, CarModels.RangeStandard.WLTP, null, null,
        Set.of(camera360, adaptiveCruiseControl, heatedSeats, appleCarPlay));

    nissanLeaf = createCarModel(
        "Nissan", "Leaf",
        CarModels.CarType.HATCHBACK,
        2016, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, new BigDecimal("40.0"), 270, CarModels.RangeStandard.WLTP, null, null,
        Set.of(proPilotAssist, heatedSteeringWheel, navigation));

    mazda2 = createCarModel(
        "Mazda", "2",
        CarModels.CarType.HATCHBACK,
        2018, 5, CarModels.Transmission.MANUAL,
        CarModels.EnergySource.GAS,
        "1.5L", null, null, null, null, null,
        Set.of(cruiseControl, bluetooth, reversingCamera));

    bmwX3 = createCarModel(
        "BMW", "X3",
        CarModels.CarType.SUV,
        2020, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.DIESEL,
        "2.0D xDrive", null, null, null, null, null,
        Set.of(panoramaRoof, hud, harmanKardonSound, parkingSensors));

    skodaFabia = createCarModel(
        "Skoda", "Fabia",
        CarModels.CarType.HATCHBACK,
        2019, 5, CarModels.Transmission.MANUAL,
        CarModels.EnergySource.GAS,
        "1.0 TSI", null, null, null, null, null,
        Set.of(dabRadio, bluetooth, parkingSensors));

    citroenEC4 = createCarModel(
        "Citroen", "E-C4",
        CarModels.CarType.HATCHBACK,
        2025, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, new BigDecimal("50.0"), 354, CarModels.RangeStandard.WLTP, null, null,
        Set.of(heatedSeats, wirelessCharging, laneAssistance, ledHeadlights));

    renaultZoe = createCarModel(
        "Renault", "Zoe",
        CarModels.CarType.HATCHBACK,
        2021, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, new BigDecimal("52.0"), 385, CarModels.RangeStandard.WLTP, null, null,
        Set.of(reversingCamera, heatedSeats, androidAuto));

    peugeot3008 = createCarModel(
        "Peugeot", "3008",
        CarModels.CarType.CROSSOVER,
        2019, 5, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.HYBRID,
        null, null, null, null, null, null,
        Set.of(adaptiveCruiseControl, camera360, leatherSeats));

    peugeotIon = createCarModel(
        "Peugeot", "iOn",
        CarModels.CarType.HATCHBACK,
        2017, 4, CarModels.Transmission.AUTOMATIC,
        CarModels.EnergySource.ELECTRIC,
        null, new BigDecimal("16.0"), 150, CarModels.RangeStandard.NEDC, null, null,
        Set.of(airConditioning, heatedSeats, cdPlayer, dabRadio));
  }

  private void seedDealers() {
    smidigBilhandel = createDealer("Smidig Bilhandel", "smidig@bilhandel.no", null, "smidig123");
    teslaCom = createDealer("Tesla.com", "sales@tesla.com", null, "tesla123");
    teslaAlesund = createDealer("Tesla Ålesund", "alesund@tesla.com", null, "tesla123");
    auto1Premium = createDealer("AUTO 1 PREMIUM", "auto1@premium.no", null, "auto1123");
    finnNo = createDealer("Finn.no", "salg@finn.no", null, "finn123");
    usedCarDealer = createDealer("Used Car Dealer", "info@usedcardealer.no", null, "used123");
    bilikist = createDealer("Bilikist", "kontakt@bilikist.no", null, "bilikist123");
    premiumAuto = createDealer("Premium Auto", "salg@premiumauto.no", null, "premium123");
    localDealer = createDealer("Local Dealer", "info@localdealer.no", null, "local123");
    citroenNorge = createDealer("Citroen Norge", "salg@citroen.no", null, "citroen123");
    usedEvShop = createDealer("Used EV Shop", "salg@usedevshop.no", null, "usedev123");
    bilcenter = createDealer("Bilcenter", "salg@bilcenter.no", null, "bilcenter123");
    evBudgetCars = createDealer("EV Budget Cars", "info@evbudgetcars.no", null, "evbudget123");
  }

  private void seedListings() {
    // SOLD
    soldGolf = createListing(vwGolf, smidigBilhandel, null, new BigDecimal("120097"), "White",
        Listings.Condition.SECONDHAND, 101000, Listings.Availability.SOLD, null);
    soldLeaf = createListing(nissanLeaf, usedCarDealer, null, new BigDecimal("185900"), "Blue",
        Listings.Condition.SECONDHAND, 62800, Listings.Availability.SOLD, null);
    soldIon = createListing(peugeotIon, evBudgetCars, null, new BigDecimal("59000"), "Silver",
        Listings.Condition.SECONDHAND, 54300, Listings.Availability.SOLD, null);

    // IN TRANSIT
    transitTesla3 = createListing(teslaModel3, teslaAlesund, null, new BigDecimal("368000"), "Black",
        Listings.Condition.NEW, 0, Listings.Availability.IN_TRANSIT, LocalDateTime.of(2026, 7, 15, 0, 0));
    transitBmwX3 = createListing(bmwX3, premiumAuto, null, new BigDecimal("459000"), "Dark Blue",
        Listings.Condition.SECONDHAND, 77200, Listings.Availability.IN_TRANSIT, LocalDateTime.of(2026, 6, 20, 0, 0));
    transitMercedesEqv = createListing(mercedesEqv, finnNo, null, new BigDecimal("699000"), "Silver",
        Listings.Condition.SECONDHAND, 45300, Listings.Availability.IN_TRANSIT, LocalDateTime.of(2026, 6, 28, 0, 0));

    // AVAILABLE
    availableTesla3 = createListing(teslaModel3, teslaCom, null, new BigDecimal("369990"), "Black",
        Listings.Condition.NEW, 0, Listings.Availability.AVAILABLE, null);
    availableModelY = createListing(teslaModelY, auto1Premium, null, new BigDecimal("375000"), "Blue",
        Listings.Condition.SECONDHAND, 28950, Listings.Availability.AVAILABLE, null);
    availableMazda2 = createListing(mazda2, bilikist, null, new BigDecimal("139000"), "Red",
        Listings.Condition.SECONDHAND, 89400, Listings.Availability.AVAILABLE, null);
    availableSkodaFabia = createListing(skodaFabia, localDealer, null, new BigDecimal("129500"), "White",
        Listings.Condition.SECONDHAND, 102500, Listings.Availability.AVAILABLE, null);
    availableCitroenEC4 = createListing(citroenEC4, citroenNorge, null, new BigDecimal("349000"), "Teal",
        Listings.Condition.NEW, 0, Listings.Availability.AVAILABLE, null);
    availableZoe = createListing(renaultZoe, usedEvShop, null, new BigDecimal("199990"), "Blue",
        Listings.Condition.SECONDHAND, 41800, Listings.Availability.AVAILABLE, null);
    availablePeugeot3008 = createListing(peugeot3008, bilcenter, null, new BigDecimal("329000"), "Black",
        Listings.Condition.SECONDHAND, 83500, Listings.Availability.AVAILABLE, null);
  }

  private void seedUsers() {
    dave = createUser("Dave", "Dangerous", "dave", null, "Dangerous2026");
    alice = createUser("Alice", "Hansen", "alice@nordrive.no", "98765432", "Alice2026");
    bob = createUser("Bob", "Nilsen", "bob@nordrive.no", "91234567", "Bob2026");
    createAdmin("chuck", "Nunchucks2026");
  }

  private void seedOrders() {
    createOrder(dave, soldGolf, soldGolf.getPrice());
    createOrder(alice, soldLeaf, soldLeaf.getPrice());
    createOrder(bob, soldIon, soldIon.getPrice());
  }

  private void seedFavorites() {
    dave.setFavoriteListings(Set.of(availableTesla3, availableModelY));
    usersRepository.save(dave);

    alice.setFavoriteListings(Set.of(availableZoe, availableMazda2, availableTesla3));
    usersRepository.save(alice);

    bob.setFavoriteListings(Set.of(availableModelY));
    usersRepository.save(bob);
  }

  // -------------------------------------------------------------------------
  // Helper methods
  // -------------------------------------------------------------------------

  private ExtraFeatures createExtraFeature(String name, String description) {
    ExtraFeatures feature = new ExtraFeatures();
    feature.setName(name);
    feature.setDescription(description);
    return extraFeaturesRepository.save(feature);
  }

  private CarModels createCarModel(
      String brand,
      String modelName,
      CarModels.CarType carType,
      int productionYear,
      int passengers,
      CarModels.Transmission transmission,
      CarModels.EnergySource energySource,
      String engine,
      BigDecimal batteryCapacityKwh,
      Integer rangeKm,
      CarModels.RangeStandard rangeStandard,
      Integer topSpeedKmh,
      BigDecimal acceleration,
      Set<ExtraFeatures> extraFeatures) {
    CarModels car = new CarModels();
    car.setBrand(brand);
    car.setModelName(modelName);
    car.setCarType(carType);
    car.setProductionYear(productionYear);
    car.setPassengers(passengers);
    car.setTransmission(transmission);
    car.setEnergySource(energySource);
    car.setEngine(engine);
    car.setBatteryCapacityKwh(batteryCapacityKwh);
    car.setRangeKm(rangeKm);
    car.setRangeStandard(rangeStandard);
    car.setTopSpeedKmh(topSpeedKmh);
    car.setAcceleration(acceleration);
    car.setExtraFeatures(extraFeatures);
    return carModelsRepository.save(car);
  }

  private Users createUser(String firstName, String lastName, String email, String phoneNumber, String password) {
    Users user = new Users();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPhoneNumber(phoneNumber);
    user.setPassword(password);
    return usersRepository.save(user);
  }

  private Accounts createAdmin(String email, String password) {
    Accounts admin = new Accounts(Accounts.Role.ROLE_ADMIN);
    admin.setEmail(email);
    admin.setPassword(password);
    return accountsRepository.save(admin);
  }

  private Dealers createDealer(String companyName, String email, String phoneNumber, String password) {
    Dealers dealer = new Dealers();
    dealer.setCompanyName(companyName);
    dealer.setEmail(email);
    dealer.setPhoneNumber(phoneNumber);
    dealer.setPassword(password);
    return dealersRepository.save(dealer);
  }

  private Listings createListing(
      CarModels carModel,
      Dealers dealer,
      String plateNumber,
      BigDecimal price,
      String color,
      Listings.Condition condition,
      int mileage,
      Listings.Availability availability,
      LocalDateTime availableFrom) {
    Listings listing = new Listings();
    listing.setCarModel(carModel);
    listing.setDealer(dealer);
    listing.setPlateNumber(plateNumber);
    listing.setPrice(price);
    listing.setColor(color);
    listing.setCondition(condition);
    listing.setMileage(mileage);
    listing.setAvailability(availability);
    listing.setAvailableFrom(availableFrom);
    return listingsRepository.save(listing);
  }

  private Orders createOrder(Users buyer, Listings listing, BigDecimal purchasePrice) {
    Orders order = new Orders();
    order.setBuyer(buyer);
    order.setListing(listing);
    order.setPurchasePrice(purchasePrice);
    return ordersRepository.save(order);
  }
}
