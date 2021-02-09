package Main;

import Entities.*;
import static MongoDB.CrudOperation.*;
import static MongoDB.UpdateEntites.*;
import static MongoDB.Analytics.*;
import static Neo4j.Analytics.*;
import static Neo4j.CrudOperation.*;

import org.neo4j.driver.exceptions.NoSuchRecordException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static User user;
    private static ProfessionalUser profUser;

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
                System.out.println("User not find");
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
        gender = input.nextLine().charAt(0);
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

        do {
            System.out.print("\nChoose operation: \n");
            System.out.println("1. Find a user");
            System.out.println("2. Follow a new user");
            System.out.println("3. Unfollow a user");
            System.out.println("4. Find a professional user");
            System.out.println("5. Follow a new professional user");
            System.out.println("6. Rate a user");
            System.out.println("7. Unfollow a professional user");
            System.out.println("8. Read a company data");
            System.out.println("9. Read all company history");
            System.out.println("10. Read a company history by period");
            System.out.println("11. Follow a new company");
            System.out.println("12. Unfollow a company");
            System.out.println("13. Read all report of a company");
            System.out.println("14. Read all report of a professional user");
            System.out.println("15. Personal information");
            System.out.println("16. Get suggests companies");
            System.out.println("0. Logout");

            item = in.nextInt();

            switch (item) {

                case 1:
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
                        System.out.println("User don't found");
                    }
                    break;
                }

                case 2:
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

                case 3:
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

                case 4:
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
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 5:
                {
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        followProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 6:
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
                        System.out.println("Professional user don't found");
                    }
                    input.nextLine();
                    break;
                }

                case 7:
                {
                    System.out.println("Insert username of the professional user to unfollow");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        unfollowProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 8:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine();
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 9:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 10:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();
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
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 11:
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

                case 12:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 14:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 15:
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

                case 16:
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

        do {
            System.out.print("\nChoose operation: \n");
            System.out.println("1. Find a user");
            System.out.println("2. Find a professional user");
            System.out.println("3. Read a company data");
            System.out.println("4. Read all company history");
            System.out.println("5. Read a company history by period");
            System.out.println("6. Follow a new company");
            System.out.println("7. Unfollow a company");
            System.out.println("8. Create a new report");
            System.out.println("9. Update a report");
            System.out.println("10. Read all report of a company");
            System.out.println("11. Read all report of a professional user");
            System.out.println("12. Verify the interest of companies");
            System.out.println("13. Most underrated companies");
            System.out.println("14. Personal information");
            System.out.println("0. Logout");

            item = in.nextInt();

            switch (item) {

                case 1:
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
                        System.out.println("User don't found");
                    }
                    break;
                }

                case 2:
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
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 3:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine();
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 4:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 5:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();
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
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 6:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        followCompany_byProfessionalUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 7:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byProfessionalUser(user.getUsername(), symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 8:
                {
                    System.out.print("Insert title:");
                    title = input.nextLine();

                    do {
                        System.out.print("Insert date of the report yyyy-mm-dd:");
                        dateReport = input.nextLine();

                        if(dateReport.matches("\\d{4}-\\d{2}-\\d{2}"))
                            break;
                        System.out.println("Error date format");

                    } while(true);

                    System.out.print("Insert type of report:");
                    typeReport = input.nextLine();

                    System.out.print("Insert the analized values:");
                    analizedValues = input.next();

                    System.out.print("Insert the company symbol to associate the report with:");
                    symbol = input.nextLine();

                    System.out.print("Insert the text:");
                    details = input.nextLine();

                    Report r = new Report(title, dateReport, typeReport, analizedValues, details, symbol, profUser.getUsername());

                    createReport(r);

                    break;
                }

                case 9:
                {
                    System.out.print("Insert title:");
                    title = input.nextLine();

                    System.out.print("Insert the text:");
                    details = input.nextLine();

                    if(updateReport_Text_byTitle(profUser.getUsername(),title, details) != 1)
                        System.out.print("Error in the update");
                    else
                        System.out.print("Operation complete");

                    break;
                }

                case 10:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 11:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 12:
                {
                    System.out.println("Insert average volume");
                    float v = input.nextFloat();
                    input.nextLine();
                    Analytics2(v);
                    break;
                }

                case 13:
                {
                    System.out.println("Insert capitalization");
                    String s = input.nextLine();
                    long i = Long.valueOf(s).longValue();
                    Analytics3(i);
                    break;
                }

                case 14:
                {
                    List <Integer> l1 = professiaonalUserFollow(profUser.getUsername());
                    System.out.println("NumberFollower: " + l1.get(0) + " NumberFollowerCompany: " + l1.get(1) + "\n");

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

        do {
            System.out.print("\nChoose operation: \n");
            System.out.println("1. Find a user");
            System.out.println("2. Follow a new user");
            System.out.println("3. Unfollow a user");
            System.out.println("4. Delete user");
            System.out.println("5. Find a professional user");
            System.out.println("6. Delete professional user");
            System.out.println("7. Follow a new professional user");
            System.out.println("8. Rate a user");
            System.out.println("9. Unfollow a professional user");
            System.out.println("10. Read a company data");
            System.out.println("11. Add a new company");
            System.out.println("12. Delete a company");
            System.out.println("13. Read all company history");
            System.out.println("14. Read a company history by period");
            System.out.println("15. Follow a new company");
            System.out.println("16. Unfollow a company");
            System.out.println("17. Read all report of a company");
            System.out.println("18. Read all report of a professional user");
            System.out.println("19. Most profitable period");
            System.out.println("20. Verify the interest of companies");
            System.out.println("21. Most underrated companies");
            System.out.println("22. Update history");
            System.out.println("23. Update summary");
            System.out.println("24. Personal information");
            System.out.println("25. Get suggests companies");
            System.out.println("26. Add a new admin");
            System.out.println("0. Logout");

            item = in.nextInt();

            switch (item) {

                case 1:
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
                        System.out.println("User not find");
                    }
                    break;
                }

                case 2:
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

                case 3:
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

                case 4:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        readUser_byUsername(username);
                        deleteUser_byUsername(username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not find");
                    }

                    break;
                }

                case 5:
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
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 6:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    try{
                        readProfessionalUser_byUsername(username);
                        deleteProfessionalUser(username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Professional user not find");
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
                        System.out.println("Professional user don't found");
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
                        System.out.println("Professional user don't found");
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
                        System.out.println("Professional user don't found");
                    }
                    break;
                }

                case 10:
                {
                    System.out.println("Insert the symbol of the company");
                    symbol = input.nextLine();
                    try {
                        Companies c = new Companies(readCompanyInfo_bySymbol(symbol), readCompany_bySymbol(symbol));
                        System.out.println(c.toString());
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't find");
                    }
                    break;
                }

                case 11:
                {
                    String name, exchange, sector, description, city, phone, state, country, address, website;
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
                    fullTimesemployees = input.nextInt();
                    input.nextLine();

                    Companies c1 = new Companies(symbol, name, exchange, sector, fullTimesemployees, description, city, phone, state, country, address, website);

                    try{
                        readCompanyInfo_bySymbol(symbol);
                        System.out.println("Company already exist");
                        break;

                    } catch (NoSuchRecordException e) {
                        addCompany(c1);
                        createCompany(c1);
                        System.out.println("Operation complete");
                    }



                    break;
                }

                case 12:
                {
                    System.out.println("Insert symbol");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompanyInfo_bySymbol(symbol);
                        deleteCompany_bySymbol(symbol);
                        deleteCompany(symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("User not find");
                    }

                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<History> h = readHistory_bySymbol(symbol);

                    if(h.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 14:
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
                        System.out.println("Company don't found");
                    else
                        System.out.println(h.toString());

                    break;
                }

                case 15:
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

                case 16:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    symbol = input.nextLine().toUpperCase();

                    try{
                        readCompany_bySymbol(symbol);
                        unfollowCompany_byUser(user.getUsername(),symbol);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Company don't found");
                    }
                    break;
                }

                case 17:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine().toUpperCase();

                    List<Report> r = readReports_bySymbol(symbol);

                    if(r.isEmpty())
                        System.out.println("Company don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 18:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);

                    if(r.isEmpty())
                        System.out.println("Professional user don't found");
                    else
                        System.out.println(r.toString());

                    break;
                }

                case 19:
                {
                    Analytics1();
                    break;
                }

                case 20:
                {
                    System.out.println("Insert average volume");
                    float v = input.nextFloat();
                    input.nextLine();
                    Analytics2(v);
                    break;
                }

                case 21:
                {
                    System.out.println("Insert capitalization");
                    String s = input.nextLine();
                    long i = Long.valueOf(s).longValue();
                    Analytics3(i);
                    break;
                }

                case 22:
                {
                    updateHistory();
                    break;
                }

                case 23:
                {
                    System.out.println("Insert filename");
                    String s = input.nextLine();
                    try
                    {
                        updateSummary(s);
                    }catch ( NoSuchFileException e)
                    {
                        System.out.println("Filename don't found");
                    }

                    break;
                }

                case 24:
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

                case 25:
                {
                    List <String> s1 = suggestedCompany(user.getUsername());

                    for(String s:s1)
                        System.out.println(s);

                    break;
                }

                case 26:
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