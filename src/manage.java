import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;

public class manage {
    ArrayList<String> users = new ArrayList<>();
    ArrayList<String> pending = new ArrayList<>();
    boolean isCreationDone = false;

    // empty constructor so object can be created in main to execute code
    public manage() {
    }

    // setter and getter for array.
    public ArrayList<String> getUsers() {
        return users;
    }

    public ArrayList<String> getPending() {
        return pending;
    }

    public void setPending(ArrayList<String> pending) {
        this.pending = pending;
    }
    //method creation

    /*
     Updates:
        - userInput() method deleted.
        - Constructor with parameters deleted.
    */


    /* TODO:
     *  - Create menu with options of actions the admin can make. [✔]
     *  - Create the list of users the admin can administrate (add, update or remove) [✔]
     *  - Create deleteUser() method. [✔]
     *  - Finish showUsers method (so admin can re-enter users) [✔]
     *
     * */
    // main menu
    public void menuDisplay() {
        String[] actions = {"Create user", "Delete user", "Update user", "Exit"};
        int option = JOptionPane.showOptionDialog(null, "Welcome to the user manage system. " +
                "\nYour current users to manage are: " + Arrays.toString(users.toArray()) + "\n What action do you like to perform?", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, actions, null);
        switch (option) {
            case 0 -> { // create users
                boolean userCreated = userCreation();
                verification(userCreated);
                if (userCreated) {
                    showUsers();
                }
            }
            case 1 -> { // delete users
                boolean isRemoved = deleteUser(isCreationDone);
                verification(isRemoved);
                System.out.println("Case 1 performed!");
            }
            case 2 -> { // update users
                boolean isUpdated = updateUser();
                verification(isUpdated);
                System.out.println("Case 2 Performed!");
            }
            case 3 -> { // exit app
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + option);
        }
    }

    // method userCreation() creates a user depending on the total of users we want to (in Integers).
    public boolean userCreation() {
        int counter = 0, entries;
        boolean isUsersCreated = false;
        try {
            entries = Integer.parseInt(JOptionPane.showInputDialog("How many entries do you like to make?"));
            for (int i = 0; i < entries; i++) {
                String user = JOptionPane.showInputDialog("Enter user N°" + (i + 1));
                pending.add(user);
                setPending(pending);
                counter++;
            }
            if (counter == 0) {
                JOptionPane.showMessageDialog(null, "No users in list.");
            } else if (counter == entries) {
                isUsersCreated = true;
                isCreationDone = true;
            } else {
                JOptionPane.showMessageDialog(null, "An error has occurred while processing users list. ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error has occurred while running instance. ");
            System.out.println("Error: " + e);
        }
        return isUsersCreated;
    }

    // method deleteUser() delete one user from the original array.
    public boolean deleteUser(boolean isCreated) {
        boolean isUserRemoved = false;
        try {
            String userToDelete = (String) JOptionPane.showInputDialog(null, "Which user do you want to delete from list.",
                    "Delete menu", JOptionPane.PLAIN_MESSAGE, null, users.toArray(), "Select user");
            System.out.println(userToDelete);
            if (userToDelete == null && !isCreated) {
                return false;
            }
            int doRemove = JOptionPane.showConfirmDialog(null, "You are going to delete user '" + userToDelete + "'\nAre you sure?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (doRemove == 0) {
                isUserRemoved = users.remove(userToDelete);
                JOptionPane.showMessageDialog(null, "User '" + userToDelete + "' has been deleted successfully.", "Delete menu", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "User '" + userToDelete + "' has not been deleted. ", "Delete menu", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Program failed while trying to execute. ", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + e);
        }
        return isUserRemoved;
    }

    // method updateUser() updates the String value of previous created one.
    public boolean updateUser() {
        boolean isUserUpdated = false;

        try {
            String userToUpdate = (String) JOptionPane.showInputDialog(null, "Select one user to update.", "Update menu",
                    JOptionPane.PLAIN_MESSAGE, null, users.toArray(), "Select user.");
            if (userToUpdate == null) {
                return false;
            }

            String updatedUser = JOptionPane.showInputDialog(null, "Update user: ", JOptionPane.PLAIN_MESSAGE);
            System.out.println("User to update: " + userToUpdate + "\nUpdated user: " + updatedUser);

            int updateMenu = JOptionPane.showConfirmDialog(null, "The user '" + userToUpdate + "' has been updated to '" + updatedUser + "'\nIs that correct?", "Update menu", JOptionPane.YES_NO_OPTION);
            if (updateMenu == 0) {
                int index = users.indexOf(userToUpdate);
                users.set(index, updatedUser);
                isUserUpdated = true;
            } else if (updateMenu == 1) {
                JOptionPane.showMessageDialog(null, "User '" + userToUpdate + "' has not been changed.", "Update menu", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something went wrong. " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return isUserUpdated;
    }

    // method verification() verifies if the creation of users was made correctly, or if it doesn't.
    public void verification(boolean ver) {
        if (!ver) JOptionPane.showMessageDialog(null, "An error has occurred. ");
        else System.out.println("Action executed successfully.");
    }

    // method to show the array of users. Not necessary in final project.
    public void showUsers() {
        //TODO: showUsers() method only show the users. It needs to re-enter in case admin want to.
        int isCorrect = JOptionPane.showConfirmDialog(null, "Your users list is: " + getPending() + ". \n Is that correct?"
                , "Users list", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (isCorrect) {
            case 0 ->{
                ArrayList<String> pendingCopy = new ArrayList<>(pending);
                users.addAll(pendingCopy);
                JOptionPane.showMessageDialog(null, "Your new list of users is: " + getUsers());
                pending.clear();
            }
            case 1 -> {
                pending.clear();
                JOptionPane.showMessageDialog(null, "The user list was not created.");
            }
            case -1 -> {
                pending.clear();
                System.out.println("Pressed 'x'. ");
            }
            default -> throw new IllegalStateException("Unexpected value: " + isCorrect);
        }
    }
}
