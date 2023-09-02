package seedu.duke.ui;

import seedu.duke.Exceptions.InvalidTaskIndexException;
import seedu.duke.Exceptions.KeywordNotFoundException;
import seedu.duke.Tasks.Task;
import seedu.duke.tasklist.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    public Ui() {

    }

    public void welcomeMessage() {
        System.out.println("Hello! I'm Lemon!" + "\nWhat can I do for you?");
    }

    public void showLoadingError() {
        System.out.println("Error loading storage file!");
    }

    public void markPrint(String toMarkTask, TaskList tasks) {
        System.out.println("Nice! I've marked this task as done:\n " + toMarkTask + "\n");
        System.out.println("Now you have " + tasks.getTasksSize() + " tasks in the list.\n");
    }

    public void unmarkPrint(String toUnmarkTask, TaskList tasks) {
        System.out.println("OK, I've marked this task as not done yet:\n " + toUnmarkTask + "\n");
        System.out.println("Now you have " + tasks.getTasksSize() + " tasks in the list.\n");
    }

    public void deletePrint(String toDeleteTask, TaskList tasks) {
        System.out.println("Noted. I've removed this task:\n" + toDeleteTask);
        System.out.println("Now you have " + tasks.getTasksSize() + " tasks in the list.\n");
    }

    public void addTasks(String toAddTask, TaskList tasks) {
        System.out.println("Got it. I've added this task: " + toAddTask);
        System.out.println("Now you have " + tasks.getTasksSize() + " tasks in the list.\n");
    }

    public void listAll(TaskList tasks) throws InvalidTaskIndexException {
        try {
            for (int i = 0; i < tasks.getTasksSize(); i++) {
                System.out.println(i + 1 + ". " + tasks.getTask(i).toString());
            }
            System.out.println("\n");
        } catch (InvalidTaskIndexException e) {
            throw new InvalidTaskIndexException("Invalid task index");
        }
    }

    public void listMatching(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + ". " + tasks.get(i).toString());
        }
        System.out.println("\n");
    }


    public void bye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

}
