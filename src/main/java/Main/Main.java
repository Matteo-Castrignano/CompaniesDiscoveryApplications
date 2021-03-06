package Main;

import Entities.*;
import static MongoDB.CrudOperation.*;
import static MongoDB.UpdateEntites.*;
import static MongoDB.Analytics.*;
import static Neo4j.Analytics.*;
import static Neo4j.CrudOperation.*;

import com.mongodb.MongoTimeoutException;
import org.neo4j.driver.exceptions.NoSuchRecordException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static User user;
    private static ProfessionalUser profUser;

    private void addCompanyConsistency(){
        String name, exchange, sector, description, city, phone, state, country, address, website, symbol;
        Scanner input = new Scanner(System.in);
        int fullTimesemployees;

        System.out.println("Insert name:");
        name = input.nextLine();
        System.out.println("Insert symbol:");
        symbol = input.nextLine().toUpperCase();
        System.out.println("Insert exchange:");
        exchange = input.nextLine();
        System.out.println("Insert sector:");
        sector = input.nextLine();
        System.out.println("Insert description:");
        description = input.nextLine();
        System.out.println("Insert city:");
        city = input.nextLine();
        System.out.println("Insert phone:");
        phone = input.nextLine();
        System.out.println("Insert state:");
        state = input.nextLine();
        System.out.println("Insert country:");
        country = input.nextLine();
        System.out.println("Insert address:");
        address = input.nextLine();
        System.out.println("Insert website:");
        website = input.nextLine();
        System.out.println("Insert number of full time employees:");
        String numberEmpl = input.nextLine();
        if(numberEmpl.length() > 9){
            System.out.println("Number troppo grande! try again");
            numberEmpl = input.nextLine();
        }
        fullTimesemployees = Integer.valueOf(numberEmpl);



        Companies c1 = new Companies(symbol, name, exchange, sector, fullTimesemployees, description, city, phone, state, country, address, website);

        try{
            readCompanyInfo_bySymbol(symbol);
            System.out.println("Company already exist");


        } catch (NoSuchRecordException e) {
            boolean addNeo4j = addCompany(c1);
            if(addNeo4j){
                try {
                    boolean addMongo = createCompany(c1);
                    if(!addMongo){

                        boolean removeNeo4j = deleteCompany_bySymbol(symbol);
                        if (!removeNeo4j) {
                            System.out.println("Operation was not performed! Database not consistency! \n");
                        } else {
                            System.out.println("Operation was not performed! Try Again");
                        }

                    }else{
                        System.out.println("Operation complete");
                    }
                }catch(MongoTimeoutException emdb){
                    System.out.println("Mongo is not available");
                    boolean removeNeo4j = deleteCompany_bySymbol(symbol);
                    if (!removeNeo4j) {
                        System.out.println("Operation was not performed! Database not consistency! \n");
                    } else {
                        System.out.println("Operation was not performed! Try Again");
                    }
                }
            }else{
                System.out.println("Operation was not performed! Try Again");
            }

        }

    }

    private void deleteCompanyConsistency(){
        String symbol;
        Scanner input = new Scanner(System.in);
        System.out.println("Insert symbol");
        symbol = input.nextLine().toUpperCase();
        Companies companyToDelete = null;
        try{
            companyToDelete = readCompanyInfo_bySymbol(symbol);

            boolean deleteFromNeo4j = deleteCompany_bySymbol(symbol);
            if(deleteFromNeo4j){
                long deleteFromMongo = deleteCompany(symbol);
                if(deleteFromMongo <= 0){
                    boolean addNeo4j = addCompany(companyToDelete);
                    if(!addNeo4j){
                        System.out.println("Operation was not performed! Database not consistency! \n");
                    }else{
                        System.out.println("Operation was not performed! Try Again");
                    }
                }else{
                    //
                    long deleteHistory = deleteHistory_bySymbol(symbol);
                    //Company history is in the db
                    if(deleteHistory < 0)
                        System.out.println("The company's history data is still in the MongoDB! Try to delete this data");

                    long deleteReport = deleteReport_bySymbol(symbol);

                    if(deleteReport < 0)
                        System.out.println("The company's report data is still in the MongoDB! Try to delete this data");


                    if(deleteReport >= 0 && deleteHistory >= 0)
                        System.out.println("Operation complete");
                }
            }else{
                System.out.println("Operation was not performed! Try Again");
            }

        } catch (NoSuchRecordException e) {
            System.out.println("Company not found");
        } catch(MongoTimeoutException emdb){
            System.out.println("Mongo is not available");
            boolean addNeo4j = addCompany(companyToDelete);
            if(!addNeo4j){
                System.out.println("Operation was not performed! Database not consistency! \n");
            }else{
                System.out.println("Operation was not performed! Try Again");
            }
        }

    }

    private void login()
    {
        Scanner input = new Scanner(System.in);
        String username, password;

        while (true)
        {
            System.out.print("Insert Username: ");
            username = input.nextLine();
            System.out.print("Insert Password: ");
            password = input.nextLine();

            try{
                user = readUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            if (user != null)
                if(user.getPassword().equals(password))
                    return;

            try{
                profUser = readProfessionalUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            if (profUser != null)
                if(profUser.getPassword().equals(password))
                    return;

            if (user  == null && profUser == null)
                System.out.println("User not found");
            else
                System.out.println("Wrong password");
        }
    }

    private void singIn()
    {
        String username, password, name, surname, date, email, country, profession, specializationSector;
        char gender;
        int type_user;
        User u;
        ProfessionalUser pu;
        Scanner input = new Scanner(System.in);

        System.out.println("Insert Name: ");
        name = input.nextLine();
        System.out.println("Insert Surname: ");
        surname = input.nextLine();

        do {
            System.out.println("Insert date of birth yyyy-mm-dd: ");
            date = input.nextLine();

            if(date.matches("\\d{4}-\\d{2}-\\d{2}") && date.matches("\\d{4}-\\d{2}-\\d{2}"))
                break;
            System.out.println("Error date format ");

        } while(true);

        System.out.println("Insert gender M or F: ");
        gender = input.nextLine().toUpperCase().charAt(0);
        System.out.println("Insert Email: ");
        email = input.nextLine();
        System.out.println("Insert Country: ");
        country = input.nextLine();

        do {
            System.out.print("Insert Username: ");
            username = input.nextLine();

            try{
                user = readUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            try{
                profUser = readProfessionalUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            if ( user == null && profUser == null)
                break;

            System.out.println("Username already exist ");

        } while(true);


        System.out.println("Insert Password: ");
        password = input.nextLine();

        do {
            System.out.println("Insert type of user: ");
            System.out.println("1. Professional User ");
            System.out.println("2. User ");
            type_user = input.nextInt();

            if(type_user == 2 )
            {
                u = new User(username, password, name, surname, date, gender, email, country);
                addUser(u);
                break;
            }
            else if (type_user == 1)
            {
                input.nextLine();
                System.out.println("Insert Profession: ");
                profession = input.nextLine();
                System.out.println("Insert specialization sector:");
                specializationSector = input.nextLine();

                pu = new ProfessionalUser(username, password, name, surname, date, gender, email, country, profession, specializationSector, 0);
                addProfessionlUser(pu);
                break;
            }
            else
                System.out.print("Error insert");

        } while(true);

    }

    public void init() throws IOException {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem;

        do {

            System.out.print("\nChoose operation: \n");
            System.out.print("1. Login\n");
            System.out.print("2. Sing In\n");
            System.out.print("3. Shutdown\n");

            menuItem = in.nextInt();

            switch (menuItem) {

                case 1:
                    login();

                    if(user != null && user.getType_user() == 2)
                        userMenu();
                    else if ( user != null && user.getType_user() == 0)
                        adminMenu();
                    else
                        profUserMenu();

                    break;

                case 2:
                    singIn();
                    break;

                case 3:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid choice");

            }

        } while (!quit);

    }

    private void userMenu() {

        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        String username, symbol, start, end;
        boolean quit = false;
        int item;
        boolean firstTime = false;
        do {
            if(!firstTime) {
                System.out.print("\nChoose operation: \n");
                System.out.println("-1. Show commands");
                System.out.println("0. Logout");
                firstTime = true;
            }
            item = in.nextInt();

            switch (item) {
                case -1:{
                    System.out.print("\nChoose operation: \n");
                    System.out.println("------------- OPERATION ON USER -------------");
                    System.out.println("1. User list");
                    System.out.println("2. Find a user");
                    System.out.println("3. Follow a new user");
                    System.out.println("4. Unfollow a user");
                    System.out.println("------------- OPERATION ON PROFESSIONAL USER -------------");
                    System.out.println("5. Professional user list");
                    System.out.println("6. Find a professional user");
                    System.out.println("7. Follow a new professional user");
                    System.out.println("8. Rate a user");
                    System.out.println("9. Unfollow a professional user");
                    System.out.println("------------- OPERATION ON COMPANY -------------");
                    System.out.println("10. Company list");
                    System.out.println("11. Read a company data");
                    System.out.println("12. Read all company history");
                    System.out.println("13. Read a company history by period");
                    System.out.println("14. Follow a new company");
                    System.out.println("15. Unfollow a company");
                    System.out.println("------------- OPERATION ON REPORT -------------");
                    System.out.println("16. Read all report of a company");
                    System.out.println("17. Read all report of a professional user");
                    System.out.println("------------- PERSONAL INFO & SUGGESTED COMPANIES -------------");
                    System.out.println("18. Personal information");
                    System.out.println("19. Get suggests companies");
                    System.out.println("--------------------------");
                    System.out.println("-1. Show commands");
                    System.out.println("0. Logout");
                    break;
                }

                case 1:
                {
                    List<String> ls = listAllUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 2:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        User u1 = readUser_byUsername(username);
                        System.out.println(u1.toString1()+"\n");

                        List <Companies> l2 = listFollowedCompany(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                        List <String> l3 = listFollowedUser(username);
                        System.out.println("\nUsername list of followed user:\n");
                        for(String l: l3)
                            System.out.println(l);

                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 3:
                {
                    System.out.println("Insert username of the user to follow");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        addUser_toFollow(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 4:
                {
                    System.out.println("Insert username of the user to unfollow");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        unfollow_User(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 5:
                {
                    List<String> ls = listAllProfessionalUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 6:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try {
                        ProfessionalUser pu = readProfessionalUser_byUsername(username);
                        System.out.println(pu.toString()+"\n");

                        List <Companies> l2 = listFollowedCompany(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 7:
                {
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        followProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 8:
                {
                    int vote;
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    do {
                        System.out.println("Insert the vote from 1 to 5");
                        try
                        {
                            vote = input.nextInt();
                        }catch (InputMismatchException e)
                        {
                            vote = 0;
                        }
                    } while(vote > 5 && vote < 1);

                    try{
                        readProfessionalUser_byUsername(username);
                        rate_ProfessionalUser(user.getUsername(), username, vote);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    input.nextLine();
                    break;
                }

                case 9:
                {
                    System.out.println("Insert username of the professional user to unfollow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        unfollowProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 10:
                {
                    System.out.println("Insert a letter: ");
                    String car = input.nextLine().toUpperCase();
                    List<String> ls = listAllCompanies(car);

                    for( String s: ls)
                        System.out.println(s);

                    break;
                }

                case 11:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine().toUpperCase();;
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 12:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();;
                    do {
                        System.out.println("Insert the start date yyyy-mm-dd");
                        start = input.nextLine();
                        System.out.println("Insert the end date yyyy-mm-dd");
                        end = input.nextLine();

                        if(start.matches("\\d{4}-\\d{2}-\\d{2}") && end.matches("\\d{4}-\\d{2}-\\d{2}"))
                            break;
                        System.out.println("Error string format");

                    } while(true);

                    List<History> h = readHistory_byPeriod(start, end, symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 14:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        followCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }
                    break;
                }

                case 15:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 16:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 17:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 18:
                {
                    List <Integer> l1 = userFollowing(user.getUsername());
                    System.out.println("NumberFollowerUser: " + l1.get(0) + " NumberFollowerCompany: " + l1.get(1) + "\n");

                    List <Companies> l2 = listFollowedCompany(user.getUsername());
                    System.out.println("\nList followed companies:\n");
                    for(Companies l: l2)
                        System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    List <String> l3 = listFollowedUser(user.getUsername());
                    System.out.println("\nUsername list of followed user:\n");
                    for(String l: l3)
                        System.out.println(l);

                    List <String> l4 = listFollowedProfessionalUser(user.getUsername());
                    System.out.println("\nUsername list of followed professional user:\n");
                    for(String l: l4)
                        System.out.println(l);

                    break;
                }

                case 19:
                {
                    List <String> s1 = suggestedCompany(user.getUsername());

                    for(String s:s1)
                        System.out.println(s);

                    break;
                }

                case 0:
                {
                    user = null;
                    profUser = null;
                    quit = true;
                    break;
                }

                default:
                    System.out.println("Invalid choice");
            }

        } while (!quit);

    }

    private void profUserMenu() {

        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        String username, symbol, start, end;
        String title, dateReport, typeReport, analizedValues, details;
        boolean quit = false;
        int item;
        boolean firstTime = false;
        do {
            if(!firstTime) {
                System.out.print("\nChoose operation: \n");
                System.out.println("-1. Show commands");
                System.out.println("0. Logout");
                firstTime = true;
            }
            item = in.nextInt();

            switch (item) {
                case -1:{
                    System.out.print("\nChoose operation: \n");
                    System.out.println("------------- OPERATION ON USER -------------");
                    System.out.println("1. User list");
                    System.out.println("2. Find a user");
                    System.out.println("------------- OPERATION ON PROFESSIONAL USER -------------");
                    System.out.println("3. Professional user list");
                    System.out.println("4. Find a professional user");
                    System.out.println("------------- OPERATION ON COMPANY -------------");
                    System.out.println("5. Company list");
                    System.out.println("6. Read a company data");
                    System.out.println("7. Read all company history");
                    System.out.println("8. Read a company history by period");
                    System.out.println("9. Follow a new company");
                    System.out.println("10. Unfollow a company");
                    System.out.println("------------- OPERATION ON REPORT -------------");
                    System.out.println("11. Create a new report");
                    System.out.println("12. Update a report");
                    System.out.println("13. Read all report of a company");
                    System.out.println("14. Read all report of a professional user");
                    System.out.println("------------- ANALYTICS & PERSONAL INFO -------------");
                    System.out.println("15. Verify the interest of companies");
                    System.out.println("16. Most underrated companies");
                    System.out.println("17. Personal information");
                    System.out.println("--------------------------");
                    System.out.println("-1. Show commands");
                    System.out.println("0. Logout");
                    break;
                }
                case 1:
                {
                    List<String> ls = listAllUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 2:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        User u1 = readUser_byUsername(username);
                        System.out.println(u1.toString1()+"\n");

                        List <Companies> l2 = listFollowedCompany(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                        List <String> l3 = listFollowedUser(username);
                        System.out.println("\nUsername list of followed user:\n");
                        for(String l: l3)
                            System.out.println(l);

                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 3:
                {
                    List<String> ls = listAllProfessionalUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 4:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try {
                        ProfessionalUser pu = readProfessionalUser_byUsername(username);
                        System.out.println(pu.toString()+"\n");

                        List <Companies> l2 = listFollowedCompany_byProfessionalUser(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 5:
                {
                    System.out.println("Insert a letter: ");
                    String car = input.nextLine().toUpperCase();
                    List<String> ls = listAllCompanies(car);

                    for( String s: ls)
                        System.out.println(s);

                    break;
                }

                case 6:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine().toUpperCase();
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 7:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 8:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();
                    do {
                        System.out.println("Insert the start date yyyy-mm-dd");
                        start = input.nextLine();
                        System.out.println("Insert the end date yyyy-mm-dd");
                        end = input.nextLine();

                        if(start.matches("\\d{4}-\\d{2}-\\d{2}") && end.matches("\\d{4}-\\d{2}-\\d{2}"))
                            break;
                        System.out.println("Error date format");

                    } while(true);

                    List<History> h = readHistory_byPeriod(start, end, symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 9:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        followCompany_byProfessionalUser(profUser.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 10:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byProfessionalUser(profUser.getUsername(), symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 11:
                {
                    System.out.println("Insert title: ");
                    title = input.nextLine();

                    do {
                        System.out.println("Insert date of the report yyyy-mm-dd:");
                        dateReport = input.nextLine();

                        if(dateReport.matches("\\d{4}-\\d{2}-\\d{2}"))
                            break;
                        System.out.println("Error date format");

                    } while(true);

                    System.out.println("Insert type of report: ");
                    typeReport = input.nextLine();

                    System.out.println("Insert the analized values:");
                    analizedValues = input.next();

                    System.out.println("Insert the company symbol to associate the report with:");
                    symbol = input.nextLine();

                    System.out.println("Insert the text:");
                    details = input.nextLine();

                    Report r = new Report(title, dateReport, typeReport, analizedValues, details, symbol, profUser.getUsername());

                    createReport(r);

                    break;
                }

                case 12:
                {
                    System.out.println("Insert title:");
                    title = input.nextLine();

                    System.out.println("Insert the text:");
                    details = input.nextLine();

                    if(updateReport_Text_byTitle(profUser.getUsername(),title, details) != 1)
                        System.out.println("Error in the update");
                    else
                        System.out.println("Operation complete");

                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 14:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine().toUpperCase();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 15:
                {
                    System.out.println("Insert average volume");
                    float v = input.nextFloat();
                    input.nextLine();
                    Analytics2(v);
                    break;
                }

                case 16:
                {
                    System.out.println("Insert capitalization");
                    String s = input.nextLine();
                    long i = Long.valueOf(s).longValue();
                    Analytics3(i);
                    break;
                }

                case 17:
                {
                    List <Integer> l1 = professiaonalUserFollow(profUser.getUsername());
                    System.out.println("NumberFollower: " + l1.get(0) + " | NumberFollowerCompany: " + l1.get(1) +  " | Average Rating: "+profUser.getAverageRating() + "\n");

                    List <Companies> l2 = listFollowedCompany_byProfessionalUser(profUser.getUsername());
                    System.out.println("\nList followed companies:\n");
                    for(Companies l: l2)
                        System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    break;
                }

                case 0:
                {
                    user = null;
                    profUser = null;
                    quit = true;
                    break;
                }

                default:
                    System.out.println("Invalid choice");
            }

        } while (!quit);

    }

    private void adminMenu() throws IOException {

        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        String username, symbol, start, end;
        boolean quit = false;
        int item;
        boolean oneChoise = false;

        do {
            if(!oneChoise) {
                System.out.print("\nChoose operation: \n");
                System.out.println("-1. Show Comands");
                System.out.println("0. Logout");
                oneChoise = true;
            }
            item = in.nextInt();

            switch (item) {
                case -1:{
                    System.out.print("\nChoose operation: \n");
                    System.out.println("------------- OPERATION ON USER -------------");
                    System.out.println("1. User list");
                    System.out.println("2. Find a user");
                    System.out.println("3. Follow a new user");
                    System.out.println("4. Unfollow a user");
                    System.out.println("5. Delete user");
                    System.out.println("------------- OPERATION ON PROFESSIONAL USER -------------");
                    System.out.println("6. Professional user list");
                    System.out.println("7. Find a professional user");
                    System.out.println("8. Delete professional user");
                    System.out.println("9. Follow a new professional user");
                    System.out.println("10. Rate a  professional user");
                    System.out.println("11. Unfollow a professional user");
                    System.out.println("------------- OPERATION ON COMPANY -------------");
                    System.out.println("12. Company list");
                    System.out.println("13. Read a company data");
                    System.out.println("14. Add a new company");
                    System.out.println("15. Delete a company");
                    System.out.println("16. Read all company history");
                    System.out.println("17. Read a company history by period");
                    System.out.println("18. Follow a new company");
                    System.out.println("19. Unfollow a company");
                    System.out.println("------------- OPERATION ON REPORT -------------");
                    System.out.println("20. Read all report of a company");
                    System.out.println("21. Read all report of a professional user");
                    System.out.println("------------- ANALYTICS & UPDATE DATA -------------");
                    System.out.println("22. Most profitable period");
                    System.out.println("23. Verify the interest of companies");
                    System.out.println("24. Most underrated companies");
                    System.out.println("25. Update history");
                    System.out.println("26. Update summary");
                    System.out.println("27. Personal information");
                    System.out.println("28. Get suggests companies");
                    System.out.println("29. Add a new admin");
                    System.out.println("-----------------------------");
                    System.out.println("-1. Show Comands");
                    System.out.println("0. Logout");
                    break;
                }

                case 1:
                {
                    List<String> ls = listAllUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 2:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        User u1 = readUser_byUsername(username);
                        System.out.println(u1.toString1()+"\n");

                        List <Companies> l2 = listFollowedCompany(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                        List <String> l3 = listFollowedUser(username);
                        System.out.println("\nUsername list of followed user:\n");
                        for(String l: l3)
                            System.out.println(l);

                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 3:
                {
                    System.out.println("Insert username of the user to follow");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        addUser_toFollow(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 4:
                {
                    System.out.println("Insert username of the user to unfollow");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        unfollow_User(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }
                    break;
                }

                case 5:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        deleteUser_byUsername(username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }

                    break;
                }

                case 6:
                {
                    List<String> ls = listAllProfessionalUser();

                    for( String s: ls)
                        System.out.println(s);
                    break;
                }

                case 7:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try {
                        ProfessionalUser pu = readProfessionalUser_byUsername(username);
                        System.out.println(pu.toString()+"\n");

                        List <Companies> l2 = listFollowedCompany(username);
                        System.out.println("\nList followed companies:\n");
                        for(Companies l: l2)
                            System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 8:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        deleteProfessionalUser(username);
                        deleteReport_byUsername(username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }

                    break;
                }

                case 9:
                {
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        followProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 10:
                {
                    int vote;
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    do {
                        System.out.println("Insert the vote from 1 to 5");
                        try
                        {
                            vote = input.nextInt();
                        }catch (InputMismatchException e)
                        {
                            vote = 0;
                        }
                    } while(vote > 5 && vote < 1);

                    try{
                        readProfessionalUser_byUsername(username);
                        rate_ProfessionalUser(user.getUsername(), username, vote);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    input.nextLine();
                    break;
                }

                case 11:
                {
                    System.out.println("Insert username of the professional user to unfollow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        unfollowProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not found");
                    }
                    break;
                }

                case 12:
                {
                    System.out.println("Insert a letter: ");
                    String car = input.nextLine().toUpperCase();
                    List<String> ls = listAllCompanies(car);

                    for( String s: ls)
                        System.out.println(s);

                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine().toUpperCase();
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 14: //insert in a function
                {
                    addCompanyConsistency();
                    break;
                }

                case 15: //insert in a function
                {
                    deleteCompanyConsistency();
                    break;
                }

                case 16:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 17:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();
                    do {
                        System.out.println("Insert the start date yyyy-mm-dd");
                        start = input.nextLine();
                        System.out.println("Insert the end date yyyy-mm-dd");
                        end = input.nextLine();

                        if(start.matches("\\d{4}-\\d{2}-\\d{2}") && end.matches("\\d{4}-\\d{2}-\\d{2}"))
                            break;
                        System.out.println("Error date format");

                    } while(true);

                    List<History> h = readHistory_byPeriod(start, end, symbol);

                    if(h.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 18:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        followCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }
                    break;
                }

                case 19:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company not found");
                    }
                    break;
                }

                case 20:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 21:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user not found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 22:
                {
                    Analytics1();
                    break;
                }

                case 23:
                {
                    System.out.println("Insert average volume");
                    float v = input.nextFloat();
                    input.nextLine();
                    Analytics2(v);
                    break;
                }

                case 24:
                {
                    System.out.println("Insert capitalization");
                    String s = input.nextLine();
                    long i = Long.valueOf(s).longValue();
                    Analytics3(i);
                    break;
                }

                case 25:
                {
                    updateHistory();
                    break;
                }

                case 26:
                {
                    System.out.println("Insert filename");
                    String s = input.nextLine();
                    try
                    {
                        updateSummary(s);
                    }catch ( NoSuchFileException e)
                    {
                        System.out.println("Filename not found");
                    }

                    break;
                }

                case 27:
                {
                    List <Integer> l1 = userFollowing(user.getUsername());
                    System.out.println("NumberFollowerUser: " + l1.get(0) + " NumberFollowerCompany: " + l1.get(1) + "\n");

                    List <Companies> l2 = listFollowedCompany(user.getUsername());
                    System.out.println("\nList followed companies:\n");
                    for(Companies l: l2)
                        System.out.println("Name: " + l.getName() +" Symbol: "+l.getSymbol());

                    List <String> l3 = listFollowedUser(user.getUsername());
                    System.out.println("\nUsername list of followed user:\n");
                    for(String l: l3)
                        System.out.println(l);

                    List <String> l4 = listFollowedProfessionalUser(user.getUsername());
                    System.out.println("\nUsername list of followed professional user:\n");
                    for(String l: l4)
                        System.out.println(l);

                    break;
                }

                case 28:
                {
                    List <String> s1 = suggestedCompany(user.getUsername());

                    for(String s:s1)
                        System.out.println(s);

                    break;
                }

                case 29:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        addAdmin(username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not found");
                    }

                    break;
                }

                case 0:
                {
                    user = null;
                    profUser = null;
                    quit = true;
                    break;
                }

                default:
                    System.out.println("Invalid choice");
            }

        } while (!quit);

    }

    public static void main(String[] args) throws Exception {
        openConnection();
        initDriver();
        Main m = new Main();
        m.init();
        close();
        closeConnection();
    }
}