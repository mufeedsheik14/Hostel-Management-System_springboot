package com.hostel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HostelManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HostelManagementApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  HOSTEL MANAGEMENT SYSTEM STARTED!");
        System.out.println("  Open: http://localhost:8081");
        System.out.println("========================================\n");
    }
}
