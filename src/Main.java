//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class Employee {
    private String educationLevel;
    private int joiningYear;
    private String city;
    private int paymentTier; // Change to int
    private int age;
    private String gender;
    private boolean benchStatus;
    private int experienceInCurrentDomain;
    private boolean hasLeftCompany;

    // Constructor
    public Employee(String educationLevel, int joiningYear, String city, int paymentTier, int age,
                    String gender, boolean benchStatus, int experienceInCurrentDomain, boolean hasLeftCompany) {
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

    // Getters for attributes
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

    public boolean isBenchStatus() {
        return benchStatus;
    }

    public int getExperienceInCurrentDomain() {
        return experienceInCurrentDomain;
    }

    public boolean hasLeftCompany() {
        return hasLeftCompany;
    }
}

public class Main {

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
            // Skip the header line
            reader.readLine();

            return reader.lines()
                    .map(Main::createEmployeeFromCsv)
                    .collect(Collectors.toList());
        }
    }


    private static Employee createEmployeeFromCsv(String line) {
        String[] attributes = line.split(",");
        // Assuming CSV format: educationLevel,joiningYear,city,paymentTier,age,gender,benchStatus,experienceInCurrentDomain,hasLeftCompany
        return new Employee(attributes[0], Integer.parseInt(attributes[1]), attributes[2],
                Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]), attributes[5],
                Boolean.parseBoolean(attributes[6]), Integer.parseInt(attributes[7]),
                Boolean.parseBoolean(attributes[8]));
    }

    private static void preprocessData(List<Employee> employeeList) {
        // Implement data preprocessing steps
        // Example: Handling missing or invalid data, converting data types, standardizing attributes
        // This could include checking and handling null values, converting string-based data to appropriate types, etc.
    }

    private static void analyzeData(List<Employee> employeeList) {
        CompletableFuture<Void> statisticsAnalysis = CompletableFuture.runAsync(() -> {
            // Implement statistics analysis using Java streams and lambda expressions
            // Example: Calculate average age, education level distribution, gender ratio
            double averageAge = employeeList.stream()
                    .mapToInt(Employee::getAge)
                    .average()
                    .orElse(0);

            long educationLevelDistribution = employeeList.stream()
                    .collect(Collectors.groupingBy(Employee::getEducationLevel, Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .count();

            long maleCount = employeeList.stream()
                    .filter(employee -> "Male".equalsIgnoreCase(employee.getGender()))
                    .count();

            long femaleCount = employeeList.size() - maleCount;

            System.out.println("Average Age: " + averageAge);
            System.out.println("Education Level Distribution: " + educationLevelDistribution);
            System.out.println("Gender Ratio (Male/Female): " + maleCount + "/" + femaleCount);
        });

        CompletableFuture<Void> retentionAnalysis = CompletableFuture.runAsync(() -> {
            // Implement retention analysis using Java streams and lambda expressions
            // Example: Analyze factors affecting employee retention
            long retainedCount = employeeList.stream()
                    .filter(Employee::hasLeftCompany)
                    .count();

            long nonRetainedCount = employeeList.size() - retainedCount;

            System.out.println("Retained Employees: " + retainedCount);
            System.out.println("Non-Retained Employees: " + nonRetainedCount);
        });

        CompletableFuture<Void> segmentationAnalysis = CompletableFuture.runAsync(() -> {
            // Implement segmentation analysis using Java streams and lambda expressions
            // Example: Segment employees based on attributes for targeted analysis
            System.out.println("Segmentation Analysis: (Example - City Wise)");

            employeeList.stream()
                    .collect(Collectors.groupingBy(Employee::getCity))
                    .forEach((city, employees) -> {
                        long cityEmployeeCount = employees.size();
                        System.out.println(city + ": " + cityEmployeeCount);
                    });
        });

        // Wait for all analyses to complete
        CompletableFuture.allOf(statisticsAnalysis, retentionAnalysis, segmentationAnalysis).join();

        // Generate and print analysis reports
        generateReports();
    }

    private static void generateReports() {
        // Implement report generation with meaningful insights
        // You can add more specific insights and reports based on your analysis
        System.out.println("Reports Generated");
    }
}
