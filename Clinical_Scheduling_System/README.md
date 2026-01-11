# Clinical Scheduling & Imaging System

A Java-based clinic operations system that simulates how a medical practice manages patients, providers, imaging technicians, appointments, and medical records. The system enforces real scheduling rules, assigns imaging staff using rotation logic, and generates operational and financial reports.

---

## Features
- Provider and technician directory loaded from file  
- Patient billing and provider credit reports  
- Office and imaging appointment scheduling  
- Technician rotation for imaging services  
- Conflict and date validation (no past dates, no weekends, valid DOBs)  
- Appointment rescheduling and cancellation  
- Imaging vs office workflow separation  
- Sorted operational reports  

---

## Technology
- Java  
- Object-Oriented Design  
- Inheritance & Polymorphism  
- Custom data structures (circular linked list, visit chains)  
- JUnit test cases  

---

## How to Run
From the project root (copy and paste into terminal):

```bash
mkdir -p out
find src -name "*.java" ! -name "*Test.java" -print | xargs javac -d out
java -cp out:src clinic.RunProject2
```

Commands:
| Command | Description                                                 |
| ------- | ----------------------------------------------------------- |
| `D`     | Schedule a doctor (office) visit                            |
| `T`     | Schedule an imaging appointment (XRAY, CATSCAN, ULTRASOUND) |
| `R`     | Reschedule an office visit                                  |
| `C`     | Cancel an appointment                                       |
| `PA`    | Print all appointments                                      |
| `PI`    | Print imaging-only schedule                                 |
| `PO`    | Print office-only schedule                                  |
| `PP`    | Print appointments by patient                               |
| `PL`    | Print appointments by county                                |
| `PS`    | Patient billing statement                                   |
| `PC`    | Provider credit statement                                   |
| `Q`     | Quit                                                        |

## Example Test Scripts:
Schedule an office visit (D,MM/DD/YYYY,TIMESLOT,FirstName,LastName,DOB,DoctorID):
```bash
D,02/10/2026,3,Jane,Doe,08/09/2000,01
PA
```
Schedule two imaging visits (T,MM/DD/YYYY,TIMESLOT,FirstName,LastName,DOB,IMAGINGTYPE):
```bash
T,02/10/2026,4,Jane,Doe,08/09/2000,XRAY
T,02/10/2026,5,Jane,Doe,08/09/2000,XRAY
PI
```
Reschedule an office visit (R,MM/DD/YYYY,OLD_SLOT,FirstName,LastName,DOB,NEW_SLOT):
```bash
R,02/10/2026,3,Jane,Doe,08/09/2000,6
PA
```
Cancel a visit (C,MM/DD/YYYY,SLOT,FirstName,LastName,DOB):
```bash
C,02/10/2026,6,Jane,Doe,08/09/2000
PA
```
```bash
Generate Reports: 
PP
PL
PO
PI
PS
PC
```

This project demonstrates the design of a healthcare workflow engine that:
Validates appointment data and enforces scheduling rules, Manages provider and imaging technician resources, Supports clinical operations and reporting, and Tracks patient visit history and financial information.

## Author
Jasmine Saffold,
B.A. Computer Science, Rutgers University
