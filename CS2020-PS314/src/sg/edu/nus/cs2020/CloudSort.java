package sg.edu.nus.cs2020;
import java.util.*;

public class CloudSort {
    /**
     * Sorts the file using odd-even sort
     * @param inFile file to be sorted
     * @param outFile file to store sorted output
     * @param numServers number of servers used
     */
    static void cloudSort(String inFile, String outFile, int numServers) {
        System.out.println("--- " + inFile + " sorting started! ---");
        //set up servers
        CloudManager manager = new CloudManager(ICloudManager.CloudProvider.AmazonEC2, 1000);
        manager.initiliazeCloud(inFile, numServers);

        //sorting algorithm here
            //helper class
            class ServerTask {
                int p1, p2;

                ServerTask(int pageOne, int pageTwo) {
                    p1 = pageOne;
                    p2 = pageTwo;
                }

                void schedule(int serverID) {
                    manager.scheduleSort(serverID, p1, p2);
                }
            }

            //helper queue
            Queue<ServerTask> taskQueue = new LinkedList<>();
            int numPages = manager.numPages();

            //schedule tasks
            if (numPages > 1) {
                for (int i = 0; i < numPages / 2; i++) {
                    for (int j = 0; j < numPages - 1; j += 2) {
                        taskQueue.offer(new ServerTask(j, j + 1));
                    }
                    for (int j = 1; j < numPages - 1; j += 2) {
                        taskQueue.offer(new ServerTask(j, j + 1));
                    }
                }
            } else {
                manager.scheduleSort(0,0,0);
                manager.executePhase();
            }


            //perform all tasks in order
            while (taskQueue.peek() != null) {
                //start and end variables to ensure that queue doesnt loop back on itself
                int start = taskQueue.peek().p1;
                int end = taskQueue.peek().p2;
                for (int i = 0; i < numServers; i++) {
                    if (taskQueue.peek() == null || taskQueue.peek().p2 == start || taskQueue.peek().p1 == end) break;
                    ServerTask currTask = taskQueue.remove();
                    currTask.schedule(i);
                }
                manager.executePhase();
            }

        //output file
        boolean sorted = manager.isSorted();
        manager.getStatus();
        manager.shutDown(outFile);
        if (sorted) {
            System.out.println("--- " + inFile + " sorting passed! ---");
        } else {
            System.out.println("--- " + inFile + " sorting FAILED! ---");
        }
    }

    /**
     * binary search the file for key, returning the index
     * @param manager cloudmanager system to use
     * @param key key to find
     * @param left left index of range
     * @param right right index of range
     * @return index that the search algorithm returns
     */
    private static int binarySearch(CloudManager manager, int key, int left, int right) {
        if (left == right) return left;
        else {
            int mid = (left + right + 1) / 2;
            return key < manager.getElement(mid) ? binarySearch(manager, key, left, mid - 1) : binarySearch(manager, key, mid, right);
        }
    }

    /**
     * checks if a file is 99% sorted, returning false with 99% probability if it isn't
     * @param inFile file to check
     * @return whether it is sorted
     */
    static boolean isSorted(String inFile) {
        final int k = 459;

        //set up servers
        CloudManager manager = new CloudManager(ICloudManager.CloudProvider.AmazonEC2, 1000);
        manager.initiliazeCloud(inFile, 1);

        //procedure
        for (int r = 0; r < k; r++) {
            int i = (int) (Math.random() * manager.numElements());
            int j = binarySearch(manager, manager.getElement(i), 0, manager.numElements() - 1);
            if (i!= j) return false;
        }
        return true;
    }
}
