
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class Employee {
    private final String educationLevel;
    private final int joiningYear;
    private final String city;
    private int paymentTier;
    private final int age;
    private final String gender;
    private int benchStatus;
    private final int experienceInCurrentDomain;
    private int hasLeftCompany;

    // Constructor
    public Employee(String educationLevel, int joiningYear, String city, int paymentTier, int age,
                    String gender, int benchStatus, int experienceInCurrentDomain, int hasLeftCompany) {
        this.educationLevel = educationLevel;
        this.joiningYear = joiningYear;
        this.city = city;
        this.paymentTier = paymentTier;
        this.age = age;
        this.gender = gender;
        this.benchStatus = benchStatus;
        this.experienceInCurrentDomain = experienceInCurrentDomain;
        this.hasLeftCompany = hasLeftCompany;
    }

    // Getter and setter methods for attributes
    public String getEducationLevel() {
        return educationLevel;
    }

    public int getJoiningYear() {
        return joiningYear;
    }

    public String getCity() {
        return city;
    }

    public int getPaymentTier() {
        return paymentTier;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getBenchStatus() {
        return benchStatus;
    }

    public void setBenchStatus(int benchStatus) {
        this.benchStatus = benchStatus;
    }
    public void setPaymentTier(int paymentTierval) {
        this.paymentTier = paymentTierval;
    }

    public int getExperienceInCurrentDomain() {
        return experienceInCurrentDomain;
    }

    public int getHasLeftCompany() {
        return hasLeftCompany;
    }


    public void setHasLeftCompany(int hasLeftCompany) {
        this.hasLeftCompany=hasLeftCompany;
    }
}

public class EmployeeAnalyzer {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\c22832b\\Downloads\\Employee.csv";

        try {
            List<Employee> employeeList = loadData(filePath);
            preprocessData(employeeList);
            analyzeData(employeeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> loadData(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            return reader.lines()
                    .map(EmployeeAnalyzer::createEmployeeFromCsv)
                    .collect(Collectors.toList());
        }
    }

    private static Employee createEmployeeFromCsv(String line) {
        String[] attributes = line.split(",");
        // Assuming CSV format: educationLevel,joiningYear,city,paymentTier,age,gender,benchStatus,experienceInCurrentDomain,hasLeftCompany
        return new Employee(attributes[0], Integer.parseInt(attributes[1]), attributes[2],
                Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]), attributes[5],
                convertBenchStatus(attributes[6]), Integer.parseInt(attributes[7]),
                Integer.parseInt(attributes[8]));
    }

    private static int convertBenchStatus(String benchStatus) {
        // Convert "yes" to 1, "no" to 0, and handle other cases
        return "yes".equalsIgnoreCase(benchStatus) ? 1 : 0;
    }

    private static void preprocessData(List<Employee> employeeList) {
        // Handling missing or invalid data, if needed
        for (Employee employee : employeeList) {
            // Handle missing or invalid data for paymentTier (assuming paymentTier cannot be negative)
                // Set a default paymentTier value
            if (employee.getPaymentTier() < 0) {
                employee.setPaymentTier(0);
            }
            // "hasLeftCompany" values are already in 0 and 1
            // Convert "yes" to 1, "no" to 0 for "benchStatus"
            employee.setBenchStatus(convertBenchStatus(employee.getBenchStatus() == 1 ? "YES" : "NO"));
        }
    }

    private static void analyzeData(List<Employee> employeeList) {
        CompletableFuture<Void> statisticsAnalysis = CompletableFuture.runAsync(() -> {
            // statistical analysis (using Java streams and lambda expressions)
            // Calculated: average age, education level distribution, gender ratio
            double averageAge = employeeList.stream()
                    .mapToInt(Employee::getAge)
                    .average()
                    .orElse(0);

            long educationLevelDistribution = employeeList.stream()
                    .collect(Collectors.groupingBy(Employee::getEducationLevel, Collectors.counting()))
                    .entrySet().size();



            long maleCount = employeeList.stream()
                    .filter(employee -> "Male".equalsIgnoreCase(employee.getGender()))
                    .count();

            long femaleCount = employeeList.size() - maleCount;

            System.out.println("Average Age: " + averageAge);
            System.out.println("Education Level Distribution: " + educationLevelDistribution);
            System.out.println("Gender Ratio (Male/Female): " + maleCount + "/" + femaleCount);
        });

        CompletableFuture<Void> retentionAnalysis = CompletableFuture.runAsync(() -> {
            // retention analysis
            // Analyze factors affecting employee retention
            long nonRetainedCount = employeeList.stream()
                    .filter(employee -> employee.getHasLeftCompany() == 1)
                    .count();


            long retainedCount = employeeList.size() - nonRetainedCount;

            System.out.println("Retained Employees: " + retainedCount);
            System.out.println("Non-Retained Employees: " + nonRetainedCount);
            System.out.println("Total Employees: " + employeeList.size());
        });

        CompletableFuture<Void> segmentationAnalysis = CompletableFuture.runAsync(() -> {
            //segmentation analysis
            // Segment employees based on attributes for targeted analysis
            System.out.println("Segmentation Analysis: (City Wise)");

            employeeList.stream()
                    .collect(Collectors.groupingBy(Employee::getCity))
                    .forEach((city, employees) -> {
                        long cityEmployeeCount = employees.size();
                        System.out.println(city + ": " + cityEmployeeCount);
                    });
        });


        CompletableFuture.allOf(statisticsAnalysis, retentionAnalysis, segmentationAnalysis).join();

        // Generate and print analysis reports
        generateReports();
    }

    private static void generateReports() {

        System.out.println("Reports Generated");
    }
}
