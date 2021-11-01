# MasteryProject

## High-level Requirements

User is a AirBnB Reservation administrator.

- The administrator may view existing reservations for a host.
- The administrator may create a reservation for a guest with a host.
- The administrator may edit existing reservations.
- The administrator may cancel a future reservation.

## Requirements

### View Reservations for Host

- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- If the host is not found, display a message.
- If the host has no reservations, display a message.
- Show all reservations for that host.
- Show useful information for each reservation: the guest, dates, totals, etc.
- Sort reservations in a meaningful way


### Make a Reservation
- The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- Show all future reservations for that host so the administrator can choose available dates.
- Enter a start and end date for the reservation.
- Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate    and weekend rate. For each day in the reservation, determine if it is a weekday or a weekend. If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
- On confirmation, save the reservation.


## Validation

- Guest, host, and start and end dates are required.
- The guest and host must already exist in the "database". Guests and hosts cannot be created.
- The start date must come before the end date.
- The reservation may never overlap existing reservation dates.
- The start date must be in the future.


### Edit a Reservation
Edits an existing reservation.
- Find a reservation.
- Start and end date can be edited. No other data can be edited.
- Recalculate the total, display a summary, and ask the user to confirm.

## Validation
- Guest, host, and start and end dates are required.
- The guest and host must already exist in the "database". Guests and hosts cannot be created.
- The start date must come before the end date.
- The reservation may never overlap existing reservation dates.

### Cancel a Reservation
Cancel a future reservation.

- Find a reservation.
- Only future reservations are shown.
- On success, display a message.
## Validation

-You cannot cancel a reservation that's in the past.

## Technical Requirements

- Must be a Maven project.
- Spring dependency injection configured with either XML or annotations.
- All financial math must use BigDecimal.
- Dates must be LocalDate, never strings.
- All file data must be represented in models in the application.
- Reservation identifiers are unique per host, not unique across the entire application. Effectively, the combination of a      reservation identifier and a host identifier is required to uniquely identify a reservation.

## Package/Class Overview

```
src
├───main
│   └───java
│       └───learn
│           └───DontWreckMyHouse
│               │   App.java                            -- app entry point
│               │
│               ├───data
│               │       DataException.java              -- data layer custom exception
│               │       ReservationFileRepository.java  -- concrete repository
│               │       ReservationRepository.java      -- repository interface
│               │       GuestFileRepository.java        -- guest methods
|               |       GuestRepository.java            -- guest interface
|               |       HostFileRepository.java         -- find host methods
|               |       HostRepository.java             -- host interface
|               |
│               ├───domain
│               │       ReservationService.java         -- reervation validation/rules
│               │       GuestService.java               -- guest validation/rules
│               │       Host Service.java               -- host validation/rules
│               │       Result                          -- domain result for handling success/failure
│               │       Response                        -- messages
│               │
│               ├───models
│               │       Reservation.java                -- date-range / guest / host
│               │       Guest.java                      -- id/gname/email
│               │       Host.java                       -- email /location/rent
│               │
│               │
│               └───ui
│                       Controller.java           -- UI controller
│                       View.java                 -- all console input/output
│                       ConsoleIO.java            -- read/write methods / error phrase
│                       MainMenuOption.java       -- enum Menu selections
│                       GenerateRequest.java      -- Date handling
│
│
└───test
    └───java
        └───learn
            └───DontWreckMyHouse
                ├───data
                │       ReservationFileRepositoryTest.java    -- ReservationFileRepository tests
                │       ReservationRepositoryTestDouble.java  -- helps tests the service, implements ReservationRepository
                |       GuestFileRepositoryTest.java          -- GuestFileRepository tests
                │       GuestRepositoryTestDouble.java        -- helps tests the service, implements ReservationRepository
                |       HostFileRepositoryTest.java           -- HostFileRepository tests
                │       HostRepositoryTestDouble.java         -- helps tests the service, implements ReservationRepository
                |       
                |
                └───domain
                        ReservationServiceTest.java           -- ReservationService tests
                        ReservationServiceTestDouble.java     -- helps ^ tests, implements Reservation Repo
                        GuestServiceTest.java                 --ReservationService tests
                        HostServiceTest.java                  -- ReservationService tests
```

## Class Details

### App
- `public static void main(String[])` -- instantiate all required classes with valid arguments, dependency injection(Spring Annotations). run controller

### data.DataException

Custom data layer exception.

- `public DataException(String, Throwable)` -- constructor, Throwable arg is the root cause exception

### data.ReservationFileRepository
- `private String filePath`
- `private Header` --  id,date-range,guest,guest email
- `public List<Reservation> findbyHost(String)` -- finds all Reservations by host email id, uses the private `
` method
- `public Reservation add(Reservation)` -- create a Reservation
- `public boolean update(Reservation)` -- update a Reservation
- `public boolean deleteByGuestEmail(int)` -- delete a Reservation by guest email String
- `private List<Reservation> findAll()` -- finds all Reservations in the data source (file), does not need to be public
- `private String serialize(Reservation guest)` -- convert a Reservation into a String (a line) in the file
- `private Reservation deserialize(String[] fields, Host email)` -- convert a String into a Reservation

### data.ReservationlRepository (interface)

Contract for ReservationFileRepository and ReservationRepositoryTestDouble.

- `List<Reservation> findByHost(String)`
- `Reservation add(Reservation)`
- `boolean update(Reservation)`
- `boolean deleteByGuestEmail(Reservation)`

### data.GuestFileRepository
-`private String filePath`
-`private String Header` - id/firstname/lastname/email
-`public List<Guest> findAll()` - find all guests
-`public List<Guest> findbyGuestEmail(String email)` - locate guest through email
-`private String serialize(Guest guest)` - get id/name/name/email
-`private Guest deserialize(String[] fields)` - set ^ in proper indexes

### data.GuestRepository (interface)
-`List<Guest> findAll()`
-`List<Guest> findByGuestEmail`
  
### data.HostFileRepository
-`private String filePath`
-`private String Header`- email, location, rentperday
-`public List<Host> findByEmailHost(String host)` - find info through email id
-`private String serialize(Host host)` - email/location/rent
-`private Host deserialize(String[] fields)` - set ^ in proper indexes

### data.HostRepository (interface)
-`List<Host> findByEmailHost(String host)`

### domain.Result T> extend Reponse
-`private T payload`
-`public T getPayload()`- return payload
`public void setPayload(T payload)` - set

### domain.Response
-`private Array<String> messages`
-`public boolean isSuccess()`
-`public List<String> getErrorMessages()`
-`public void addErrorMessage(String message)`

### domain.ReservationService
-`private final ReservationRepository`
-`private final GuestRepository`
-`private final HostRepository`
-`public ReservationService(ReservationR repo, GuestRepo repo, Host repo)` --constructor
-`public List<Reservation> findByGuestEmail()` --pass through repository
-`public Result<Reservation> add (Reservation res)` - validate then add
-`public Result<Reservation> update (Reservation res)` - validate then update
-`public Result<Reservation> deleteByGuestEmail(String email)` - pass through to repo
-`private Result<Reservation> validate(Reservation res)` - validation process

### domain.GuestService
- `private final GuestRepository`
- `public GuestService(GuestR repo)
- `public Result<Guest> findByGuestEmail(Guest guest)` 

### domain.HostService
- `private final HostRepository`
- `public HostService(HostR repo)
- `public Result<Host> findEmailByHost(Host host)`
- `private rentPerDay(BigDecimal rent, LocalDate day)`

### model.Reservation
- `private String id`
- `private Locale Date range`
- `private String Guest name`
- `private String guest email`

### model.Guest
- `private String id` - get/set
- `private String first name` - get/set
- `private String last name` - get/set
- `private String guest email` - get/set

### model.Host
- `private String hostemail` - get/set
- `private String location` - get/set
- `private Big Deciminal rent` - get/set

### ui.ConsoleIO
- `private static final String Invalid Number` -- error invalid number
- `private static final String Invalid Date` -- error invalid dates
- `private static final String Required` --  error missing input
- `private static final String Number out of Range` -- number within the given range
- `privare final Scanner`
- `private DateTimeFormatter formatter`
- `public void print(String msg)`
- `public void println(String msg)`
- `public void printf(Stinf msg)`
- `public String readString(String prompt)` -- scanner
- `public String readString(String prompt)` -- required msg
- `public double readDouble(String prompt)` -- invalid number msg
- `public double readDouble(String prompt, double min, double max)` -- number out of range msg
- `public int readInt(String prompt)`  -- invlaid number msg
- `public int readInt(String prompt, int min, int max)` -- number out of range msg
- ` public boolean readBoolean(String prompt)` -- yes or no validation msg
- `public LocalDate readLocalDate(String prompt)` -- invalid date msg
- `public BigDecimal readBigDecimal(String prompt)` -- invalid number msg
- `public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max)` -- invalid range msg

### ui.Controller
- `private View view` -- required View dependency
- `private ReservationService service` -- required service dependency
- `private GuestService service` -- required service dependency
- `private HostService service` -- required service dependency
- `public Controller(View, ReservationService, GuestService, HostService)` -- constructor with dependencies
- `public void run()` -- kicks off the whole app, menu loop
- `private void viewReservationsByHost()` -- coordinates between service and view to display Reservations by host email
- `private void makeReservation()` -- coordinates between service and view to add a new reservation
- `private void editReservation()` -- coordinates between service and view to update a reservation
- `private void cancelReservation()` -- coordinates between service and view to delete a reservation

### ui.View
- `private Scanner console` -- a Scanner to be used across all methods
- `public int chooseOptionFromMenu()` -- display the main menu and select an option, used to Controller.run()
- `public void displayHeader(String)` -- display text to the console with emphasis
- `public LocalDate getReservationDateStart()`
- `public LocalDate getReservationDateEnd()`
- `public LocaleDate getReservationDateRange()`
- `public Reservation chooseReservation(List<Reservation> res)`
- `public Host chooseHost(List<Host> host)`
- `public Guest chooseGuest(List<Guest> guest)`
- `public Reservation makeReservation(Host host, Guest guest)` -- set both entities / set datestart&end /
- `public void enterToContinue()`
- `public void displayException(Exception ex)
- `public void displayStatus(Bool success, Str msg)`
- `public void displayStatus(Bool success, List<Str> msg)`
- `public void displayReservations(List<Reservation> res)`
- `public void displayByHost(List<Host> host>
- `public Reservation updateReservation(Reservation res)`

### ui.GenerateRequest
- `private LocalDate start` --get/set
- `private LocalDate end` -- get/set
- `private int count` -- get/set

### ui.MainMenuOption
- `Enum Exit
- `Enum ViewResByHost
- `Enum MakeReservation
- `Enum EditReservation
- `Enum CancelReservation
- `private int value` -get
- `private String msg` -get
- `private boolean hidden` -get
- `private MainMenuOption(int,Str,bool)
- `public static MainMenuOption fromValue(int)` 

## Steps

1. Create a Maven project.
2. Add jUnit 5, Jupiter, as a Maven dependency and refresh Maven
3. Create packages.
4. Create the `Reservation, Guest, Host` model.
5. Create the data layer's custom `DataException`
6. Create the `FileRepository` classes.

    All methods should catch IOExceptions and throw `DataException`.

    - add the filePath field and create a constructor to initialize the field
    - generate tests for `FileRepository`s
    - create a `data` directory in the project root. add test, seed, and production data files
    - implement `findAll`, `serialize`, and `deserialize`. these are all private method. may be useful to make `findAll` public temporarily and test it quick.
    - implement `findByGUestEmail`, it uses `findAll`.
    - implement `add`
    - improve tests by establishing known-good-state with `@BeforeAll`
    - test `add`
    - implement `update`
    - test `update`
    - implement `deleteByGuestEmail`
    - test `deleteByGuestEmail`

7. Extract the `Repository`s interfaces (IntelliJ: Refactor -> Extract Interface) from `FileRepository`s.
8. Create `Result`.
9. Create `Reponse`
10. Create `Service`s.

    - add  `Repository`s (interface) field with a corresponding constructor
    - generate tests for `Service`s
    - create `RepositoryTestDouble`s to support service testing, this test class implements `Repository`s
    - implement `findByGuestEmail` and test, implement just enough code in `RepositoryTestDouble` to enable service testing
    - implement `add` and test, requires validation
    - implement `update` and test, requires validation
    - implement `deleteByGuestEmail` and test

11. Create `MainMenuOptions`
    - add menu choices enums w/ strings
    - add constructor to get value and return option
   
12. Create `ConsoleIo`
    - add read/print statements
    - add invalid catches
    
13. Create `Generate Request`
    - add Date start / end / count with getter and setters
    
14. Create `View`
    - add view methods
    
15. Create `Controller`

    - add fields for services and view with corresponding constructor
    - add switch for menu options
    - add a `run` method

16. Create `App` and the `main` method.

    - instantiate all required objects: repository, service, view, and controller
    - run the controller

17. Work back and forth between controller and view.

    Run early and often. Add `System.out.println`s as breadcrumbs in controller, but don't forget to remove them when development is complete.

    Determine the correct sequence for service calls and view calls. What is the order?
    
18.Once operating correctly implement Spring Annotations.

## Controller Perspectives

### View Reservations from Host
1. collect reservation list from host email
2. use the email to fetch reservations from the service
3. use the view to display reservations and location
4. Enter to continue

### Add a Reservation
1. collect a complete and new reservation from the view
2. use the service to add the reservation and grab its result
3. Show selected beginning and end dates showing rent cost. Prompt user agreement.
4. display success with reservation ID or failure msg
5. Enter to continue

### Update a Panel
1. collect Reservation list from the host email
2. use the host email to fetch reservations from the service
3. display the reservations in the view and allow the user to choose a reservation by guest email (if no reservation selected, abort)
4. update reservation dates (setters) in the view
5. Prompt confirmation of dates and payment amount
6. use the service to update/save the reservation and grab its result
7. display the result in the view
8. Enter to continue

### Delete a Panel
1. collect Resercations from the view by host email
2. use the email to fetch reservations from the service
3. display the reservations in the view and allow the user use guest email to choose reservation (if no reservations selected, abort)
4. use the service to delete the reservation by its identifier
5. display success or failure in the view
