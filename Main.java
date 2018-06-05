import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * AEM:2515
 * Ονομα: Ιωαννης
 * Επωνυμο: Μπραντ-Ιωαννιδης
 *
 */

public class Main {

    private static int linePos = 0;  //a variable which is used to read the given file in the appropriate format.
    private static int processesNumber = 0, vmNumber = 0; //the number of the processes and the virtual machines respectively.
    private static int[][] processesCost = null;  //an array where the cost of every process on every virtual machine is saved.
    private static int[][] transferToVmCost = null;  //an array which stores the cost to transfer a process from one VM to another.
    private static int ip = 0, it =0;


    /**
     * This method starts the execution of the project. It calls the methods saveData(String filename)
     * ,which reads the given file and stores the data it contains, and compute(int[][] processes,
     *  int[][] vms) which computes the total cost for every VM and process,for each step, as stated
     *  in the project description.
     *
     *
     * @param args args[0] represents the file name of the file that contains the data that are going
     *             to be processed.
     */
    public static void main(String[] args) {
      if(args.length>0) {
          String filename = args[0];
          saveData(filename);
          int[][] Costs = compute(processesCost, transferToVmCost);
          printArray(Costs, processesNumber, vmNumber);

      }else{
          System.out.println("You have not given a file as an argument.To run this program " +
                  "you should open your cmd and run the below command:\n" +
                  "java -jar AlgorithmsProject2.jar /...path/to/file/filename.txt");
      }

    }


    /**
     *      This method uses basic principles of dynamic programming to efficiently compute the desired
     * solution.
     *      The 2D array Costs[i][j] is computed as follows:
     *  1) Copy the first row of the array processes to the array Costs[i][j].
     *
     *  2) Starting from the 2nd row of Costs[i][j] add the same (i,j) cell of the array processes
     *  to every cell from the previous row of the array Costs[i][j] as well as the cost of the transfer
     *  through the Virtual Machines (0 if running on the same Virtual Machine).
     *
     *  3) From the resulted sums, assign the minimum sum to the Costs[i][j] cell.
     *
     *      Dynamic programming:Instead of re-computing the new result by beginning from the first row
     *   row of the array, the results from the previous row of the array Costs[i][j] are used.
     *
     * @param processes an array that contains the cost of a process to run on a virtual machine.
     * @param vms the cost to transfer a process from a VM to another.
     * @return the result Costs[i][j] array,as described on the project description.
     */
    public static int[][] compute(int[][] processes,int[][] vms){
        int[][] Costs = new int[processesNumber][vmNumber];
        int[][] temp = new int[1][vmNumber];

        for(int j=0;j<vms.length;j++){
            Costs[0][j]=processes[0][j];
        }

        for (int i=1;i<processes.length;i++){
            for (int j=0;j<vms.length;j++){
                int min = Integer.MAX_VALUE;
                for (int k=0;k<vms.length;k++){
                        temp[0][j] = Costs[i-1][k] + processes[i][j] + vms[j][k];
                        if(temp[0][j]<min){
                            min=temp[0][j];
                        }
                }
                Costs[i][j]=min;
            }
        }
        return Costs;
    }


    /**
     * A trivial function that reads the given file, on the given format, and stores the data to the
     * specific arrays that the project states.
     * The storage as well as the reading of the file is entirely based on the file structured
     * as stated in the project's description. It won't work with different file structures.
     *
     * @param filename the name of the file that contains the data
     */

    public static void saveData(String filename){
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine()){
                if (linePos==0){
                    processesNumber = scanner.nextInt();
                    linePos++;
                }
                if (linePos==1){
                    vmNumber = scanner.nextInt();
                    processesCost=new int[processesNumber][vmNumber];
                    transferToVmCost=new int[vmNumber][vmNumber];
                    linePos++;
                }

                if(linePos>=2 && linePos<=2+processesNumber){

                    if(ip<processesNumber) {
                        for (int j = 0; j < vmNumber; j++) {
                            processesCost[ip][j]=scanner.nextInt();
                        }
                    }
                    ip++;
                    linePos++;
                }
                else if(linePos>=2+processesNumber && linePos<2+processesNumber+vmNumber+1){
                    if (it<vmNumber){
                        for (int j=0;j<vmNumber;j++){
                            transferToVmCost[it][j]=scanner.nextInt();
                        }
                    }
                    it++;
                    linePos++;
                }
                else break;


            }
        } catch (FileNotFoundException e) {
            System.out.println("Are you sure this file exists on your computer? Try again.");
            e.printStackTrace();
        }

    }



    /**
     * A simple function which accepts a 2D array,its rows and columns,as arguments and prints the
     * elements of the given array.
     *
     * @param arr a 2D array
     * @param rows the rows of the 2D array
     * @param columns the columns of the 2D array
     *
     */
    public static void printArray(int[][] arr,int rows,int columns){
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                System.out.print(arr[i][j] +",");
            }
            System.out.println();
        }
    }
}
