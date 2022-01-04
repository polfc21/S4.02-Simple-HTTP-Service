package com.itacademy.model;

public enum JobType {
    JUNIOR(1200),
    MIDDLE(2000),
    SENIOR(3000),
    MANAGER(4000),
    OWNER(5000);

    private final double salary;

    JobType(double salary){
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public static boolean existsJob(String job){
        for (JobType jobType: JobType.values()) {
            if (jobType.name().equalsIgnoreCase(job)) {
                return true;
            }
        }
        return false;
    }
}
