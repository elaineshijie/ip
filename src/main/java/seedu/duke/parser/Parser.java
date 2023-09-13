package seedu.duke.parser;

import java.util.ArrayList;

import seedu.duke.datafile.Storage;
import seedu.duke.exceptions.InvalidDeadlineException;
import seedu.duke.exceptions.InvalidEventException;
import seedu.duke.exceptions.InvalidTaskException;
import seedu.duke.exceptions.InvalidTaskIndexException;
import seedu.duke.exceptions.InvalidTodoException;
import seedu.duke.exceptions.KeywordNotFoundException;
import seedu.duke.exceptions.LemonException;
import seedu.duke.exceptions.NoTasksException;
import seedu.duke.tasklist.TaskList;
import seedu.duke.tasks.Deadline;
import seedu.duke.tasks.Event;
import seedu.duke.tasks.Task;
import seedu.duke.tasks.Todo;
import seedu.duke.ui.Ui;



/**
 * The parser class handles all the processing of commands from the user input.
 */
public class Parser {


    enum Commands {
        BYE,
        MARK,
        UNMARK,
        DELETE,
        LIST,
        TODO,
        DEADLINE,
        EVENT,
        FIND
    }

    /**
     * Calls methods from ui, tasks, storage to handle the actions corresponding to the commands.
     * @param input the command input by user.
     * @param tasks a TaskList with the tasks by user.
     * @param tasksData the Storage that loads, add and delete tasks.
     * @param ui the Ui object that deals with user input & output messages to users.
     * @throws LemonException thrown when commands are invalid.
     */
    public static String parseTasks(String input, TaskList tasks, Storage tasksData, Ui ui) throws LemonException {
        System.out.println("input is: " + input);
        if (!input.equals("bye")) {
            String commandType = input.split(" ")[0].toUpperCase();
            try {
                Commands command = Commands.valueOf(commandType);
                switch (command) {
                case LIST:
                    if (tasks.getTasksSize() < 1) {
                        System.out.println("No tasks??");
                        throw new NoTasksException("");
                    }
                    return ui.listAll(tasks);

                case MARK:
                    try {
                        int indexToMark = Integer.valueOf(input.split(" ")[1]);
                        String taskDescription = tasks.markTask(indexToMark - 1);
                        tasksData.saveTasks(tasks.getTaskList());
                        return ui.markPrint(taskDescription, tasks);

                    } catch (IndexOutOfBoundsException e) {
                        throw new InvalidTaskIndexException("");
                    }
                case UNMARK:
                    try {
                        int indexToUnmark = Integer.valueOf(input.split(" ")[1]);
                        String unmarkTaskDescription = tasks.unmarkTask(indexToUnmark - 1);
                        tasksData.saveTasks(tasks.getTaskList());
                        return ui.unmarkPrint(unmarkTaskDescription, tasks);

                    } catch (IndexOutOfBoundsException e) {
                        throw new InvalidTaskIndexException("");
                    }
                case TODO:
                    String[] taskSplit = input.split(" ", 2);
                    if (taskSplit.length < 2) {
                        throw new InvalidTodoException("");
                    }
                    String taskDescription = taskSplit[1];
                    String taskDescriptionTodo = tasks.addTasks(new Todo(taskDescription));
                    tasksData.saveTasks(tasks.getTaskList());
                    return ui.addTasks(taskDescriptionTodo, tasks);

                case DEADLINE:
                    String task = input.split(" ", 2)[1];
                    String[] getDeadlineArray = task.split("/by ", 2);
                    if (getDeadlineArray.length < 2) {
                        throw new InvalidDeadlineException("");
                    }
                    String taskDesc = getDeadlineArray[0];
                    String by = getDeadlineArray[1];
                    Task newDeadlineTask = new Deadline(taskDesc, by);
                    String taskDescriptionDeadline = tasks.addTasks(newDeadlineTask);
                    tasksData.saveTasks(tasks.getTaskList());
                    return ui.addTasks(taskDescriptionDeadline, tasks);

                case EVENT:
                    String inputTask = input.split(" ", 2)[1];
                    String[] getEventFromArray = inputTask.split("/from ", 2);
                    if (getEventFromArray.length < 2) {
                        throw new InvalidEventException("");
                    }
                    String taskDetails = getEventFromArray[0];
                    String[] getEventToArray = getEventFromArray[1].split(" /to ", 2);
                    if (getEventToArray.length < 2) {
                        throw new InvalidEventException("");
                    }
                    String from = getEventToArray[0];
                    String to = getEventToArray[1];
                    Task newEventTask = new Event(taskDetails, from, to);
                    String taskDescriptionEvent = tasks.addTasks(newEventTask);
                    tasksData.saveTasks(tasks.getTaskList());
                    return ui.addTasks(taskDescriptionEvent, tasks);

                case DELETE:
                    int inputDelete = Integer.valueOf(input.split(" ", 2)[1]) - 1;
                    try {
                        Task taskToDelete = tasks.getTask(inputDelete);
                        String taskDescriptionDelete = tasks.deleteTask(taskToDelete);
                        tasksData.saveTasks(tasks.getTaskList());
                        return ui.deletePrint(taskDescriptionDelete, tasks);
                    } catch (IndexOutOfBoundsException e) {
                        throw new InvalidTaskIndexException("");
                    }

                case FIND:
                    String[] commandSplit = input.split(" ", 2);
                    if (commandSplit.length < 2) {
                        throw new KeywordNotFoundException("No Keywords to find!");
                    }
                    String keyword = input.split(" ")[1];
                    ArrayList<Task> matchingTasks = tasks.findKeyword(keyword);
                    if (matchingTasks.size() == 0) {
                        throw new KeywordNotFoundException("No Tasks found!");
                    }
                    return ui.listMatching(matchingTasks);

                default:
                    throw new InvalidTaskException(" " + input + " ");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidTaskException(" " + input + " ");
            }
        }
        return ui.bye();
    }
}
