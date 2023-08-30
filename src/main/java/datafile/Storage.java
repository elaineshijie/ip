package datafile;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
        private static final String FILE_PATH = "./datafile/tasks.txt";

        public Storage() {
        }

        public void saveTasks(ArrayList<Task> tasks) {
            try {
                File file = new File(FILE_PATH);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    System.out.println("Creating new file");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            try {
                writeToFile(FILE_PATH, "");
                if (tasks.size() > 0) {
                    for (int i = 0; i < tasks.size(); i++) {
                        addToFile(FILE_PATH, tasks.get(i).addDataFormat() + "\n");
                    }
                }
            } catch (IOException e) {
                System.out.println("Issues saving tasks to storage.");
            }

        }
        private static void writeToFile(String filePath, String textToAdd) throws IOException {
            FileWriter fw = new FileWriter(filePath);
            fw.write(textToAdd);
            fw.close();
        }
        private static void addToFile(String filePath, String textToAdd) throws IOException {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(textToAdd + System.lineSeparator());
            fw.close();
        }

        public ArrayList<Task> loadFile() {
            ArrayList<Task> tasks = new ArrayList<>();
            File dataFile = new File(FILE_PATH);
            Scanner scanner;
            // if there is no storage - no tasks
            if (!dataFile.exists()) {
                System.out.println("File doesn't exist?");
                return tasks;
            } else {
                try {
                    scanner = new Scanner(dataFile);
                    while (scanner.hasNextLine()) {
                        String[] input = scanner.nextLine().split(" \\| ");
                        String taskType = input[0];
                        switch(taskType) {
                            case "T":
                                boolean isDoneT = checkDone(input[1]);
                                String descriptionT = input[2];
                                Task toAddTaskT = new Todo(descriptionT, isDoneT);
                                tasks.add(toAddTaskT);
                                break;
                            case "D":
                                boolean isDoneD = checkDone(input[1]);
                                String descriptionD = input[2];
                                String by = input [3];
                                Task toAddTaskD = new Deadline(descriptionD, by, isDoneD);
                                tasks.add(toAddTaskD);
                                break;
                            case "E":
                                boolean isDoneE = checkDone(input[1]);
                                String descriptionE = input[2];
                                String fromE = input[3];
                                String toE = input[4];
                                Task toAddTaskE = new Event(descriptionE, fromE, toE, isDoneE);
                                tasks.add(toAddTaskE);
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                }

                }
                return tasks;
        }

        private static boolean checkDone(String isDone) {
            return isDone.equals("1")
                    ? true
                    : false;
        }



}
