# Car-Charging-Station
Simulation of the Car charging station with several alternative sources of energy. 
Task Completion Description:

Matriculation Number: 7216156
Responsibilities:
1. Develop the Station configuration for the Capstone project and also the corresponding part in the Main.java file to align the Locatio.java and Station.java file
2. Create chaining exceptions which means to relate one exception with another exception, i.e one exception describes the cause of another exception
   Handled the wrong weather status input to implement chaining exception in Hometask 1
3. Write the theory answers to Hometask 1 (question no 2.1), Hometask 2 (question no 2.1)


Matriculation Number: 7219089
Responsibilities: 
1. Develop the Weather conditions and Energy sources configuration for the Capstone project and also the corresponding part in the Main.java file to align the scenario of the capstone project
2. Create Resource Management exceptions in the capstone project in Hometask 1 where the scanner is closed to reduce the usage of resources.
3. Write the theory answers to Hometask 1 (question no 2.4), and Hometask 2 (question no 2.3)


Matriculation Number: 7216782
Responsibilities: 
1. Develop the Location configuration for the Capstone project and also the corresponding part in the other files to align the scenario of the capstone project
2. Create Multiple exceptions in the capstone project in Hometask 1 
3. Write the theory answers to Hometask 1 (question no 2.3), and Hometask 2 (question no 2.4)






Answers to the questions of Home Task 1:

1. The try-with-resources statement automatically closes all the resources at the end of the statement.
A resource is an object to be closed at the end of the program.
Example: 
import java.io.*;
class Main {
  public static void main(String[] args) {
    String line;
    try(BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
      while ((line = br.readLine()) != null) {
        System.out.println("Line =>"+line);
      }
    } catch (IOException e) {
      System.out.println("IOException in try block =>" + e.getMessage());
    }
  }
}


2. The throw statement can be used to throw an object when a program detects an error. The use of the throw statement allows a program to react to errors that it detects just the way the Java runtime system and all the predefined classes react. This allows a certain uniformity that would not be possible otherwise. This throw is used for custom errors.

3. The Throwable class is the superclass of all errors and exceptions in the Java language. Only objects that are instances of this class (or one of its subclasses) are thrown by the Java Virtual Machine or can be thrown by the Java throw statement. Similarly, only this class or one of its subclasses can be the argument type in a catch clause. For the purposes of compile-time checking of exceptions, Throwable and any subclass of Throwable that is not also a subclass of either RuntimeException or Error are regarded as checked exceptions.
Instances of two subclasses, Error, and Exception, are conventionally used to indicate that exceptional situations have occurred. Typically, these instances are freshly created in the context of the exceptional situation so as to include relevant information.

4.
  Examples of catching and handling exceptions

  a.
  
      try {
          
          FileInput file = new FileInput("file.txt")
          file.close();
      } catch (FileNotFoundException e) {
          System.out.println("File not found: " + e.getMessage());
      } catch (IOException e) {
          System.out.println("IO error occurred: " + e.getMessage());
      }

  b.

      try {
       
         int[] a = new int[50];
         System.out.println(arr[100]);
      } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
         System.out.println("Exception occurred: " + e.getMessage());
      }

   c. 

      try {
          
          String s = null;
          System.out.println(s.length()); 
      } catch (Exception e) {
          System.out.println("Exception occurred: " + e.getMessage());
      }


   d.

      try {   
          int result = 30 / 0; 
      } catch (ArithmeticException e) {
          System.out.println("Cannot divide by zero.");
          throw e; 
      }


   e.

      try {   
          try {        
              String str = "prj";
              int num = Integer.parseInt(str); 
          } catch (NumberFormatException e) {
              System.out.println("NumberFormatException: " + e.getMessage());
          }
          int[] arr = new int[50];
          System.out.println(arr[10]); 
      } catch (ArrayIndexOutOfBoundsException e) {
          System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
      }




Answers to the questions of Home Task 2:

1. Files.newByteChannel returns an instance of SeekableByteChannel which allows one to read from (or write to) any position in the file.
2. Have to Use the %n conversion — the \n escape is not platform-independent.
3. The Files.probeContentType method uses the platform's underlying file type detector to evaluate and return the MIME type.
4. I would use the Files.isSymbolicLink method.
