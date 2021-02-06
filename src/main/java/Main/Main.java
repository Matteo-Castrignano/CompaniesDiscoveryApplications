package Main;

import Entities.*;
import org.neo4j.driver.exceptions.NoSuchRecordException;

import java.util.List;
import java.util.Scanner;
import static MongoDB.CrudOperation.*;
import static Neo4j.Analytics.*;
import static Neo4j.CrudOperation.*;


public class Main {
    
    private static User user;
    private static ProfessionalUser profUser;

    private void login()
    {
        Scanner input = new Scanner(System.in);
        String username, password;
        boolean result=true;

        while (result)
        {
            System.out.print("Insert Username:");
            username = input.nextLine();
            System.out.print("Insert Password:");
            password = input.nextLine();

            user = new User();
            profUser = new ProfessionalUser();
            
            try{
                user = readUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            try{
                profUser = readProfessionalUser_byUsername(username);
            } catch (NoSuchRecordException e) {}

            if ( user.getUsername() == null && profUser.getUsername() == null)
                System.out.println("User not find");
            else if ( user.getUsername().equals(password) )
                return;
            else if ( profUser.getUsername().equals(password) )
                return;
            else
                System.out.println("Wrong password");
        }
    }

    private void singIn()
    {
        return;
    }

    public void init() throws Exception {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem;

        do {

            System.out.print("Choose operation: \n");
            System.out.print("1. Login");
            System.out.print("2. Sing In");

            menuItem = in.nextInt();

            switch (menuItem) {

                case 1:
                    login();
                    
                    if(user.getType_user() == 2)
                        userMenu();
                    else if ( user.getType_user() == 0)
                        adminMenu();
                    else
                        profUserMenu();

                case 2:
                    singIn();

                default:
                    System.out.println("Invalid choice");

            }

        } while (!quit);

        return;
    }

    private void userMenu() throws Exception {

        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        String username, symbol, start, end;
        boolean quit = false;
        int menuItem;

        do {
            System.out.print("Choose operation: \n");
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
            System.out.println("0. Exit");

            menuItem = in.nextInt();

            switch (menuItem) {

                case 1:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    User u1 = new User();
                    try{
                        u1 = readUser_byUsername(username);
                    } catch (NoSuchRecordException e) {}

                    if ( u1.getUsername() == null )
                        System.out.println("User not find");
                    else
                        System.out.println(u1.toString());

                    break;
                }

                case 2:
                {
                    System.out.println("Insert username of the user to follow");
                    username = input.nextLine();

                    try{
                        addUser_toFollow(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 3:
                {
                    System.out.println("Insert username of the user to unfollow");
                    username = input.nextLine();

                    try{
                        unfollow_User(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 4:
                {
                    System.out.println("Insert username");
                    username = input.nextLine();

                    User u1 = new User();
                    try {
                        u1 = readProfessionalUser_byUsername(username);
                    } catch (NoSuchRecordException e) {
                    }

                    if (u1.getUsername() == null)
                        System.out.println("Professiona user not find");
                    else
                        System.out.println(u1.toString());

                    break;
                }

                case 5:
                {
                    System.out.println("Insert username of the professional user to follow");
                    username = input.nextLine();

                    try{
                        followProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
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
                        vote = input.nextInt();

                    } while(vote < 6 && vote > 0);

                    try{
                        rate_ProfessionalUser(user.getUsername(), username, vote);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 7:
                {
                    System.out.println("Insert username of the professional user to unfollow");
                    username = input.nextLine();

                    try{
                        unfollowProfessionalUser_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 8:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    username = input.nextLine();

                    break;
                }

                case 9:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();

                    List<History> h = readHistory_bySymbol(symbol);
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
                    System.out.println(h.toString());

                    break;
                }

                case 11:
                {
                    System.out.println("Insert the symbol of the company to follow");
                    username = input.nextLine();

                    try{
                        followCompany_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 12:
                {
                    System.out.println("Insert the symbol of the company to unfollow");
                    username = input.nextLine();

                    try{
                        unfollowCompany_byUser(user.getUsername(), username);
                        System.out.println("Operation complete");
                    } catch (NoSuchRecordException e) {
                        System.out.println("Error");
                    }

                    break;
                }

                case 13:
                {
                    System.out.println("Insert the symbol of a company");
                    symbol = input.nextLine();

                    List<Report> r = readReports_bySymbol(symbol);
                    System.out.println(r.toString());

                    break;
                }

                case 14:
                {
                    System.out.println("Insert username of a professional");
                    username = input.nextLine();

                    List<Report> r = readReports_byUsername(username);
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

                }

                case 16:
                {
                    List <String> s1 = suggestedCompany(user.getUsername());
                    System.out.println( s1.toString());
                    break;

                }

                case 0:
                {
                    quit = true;
                    break;
                }

                default:
                    System.out.println("Invalid choice.");
            }

        } while (!quit);

        return;

    }

    private void profUserMenu() {
    }

    private void adminMenu() {
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
