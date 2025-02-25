# EzLookAndBook

## About the Project

EzLookAndBook is an innovative service booking platform that
connects clients with professionals from various industries, such
as hairdressing, cosmetics, and many others. The service provides
convenience for both users searching for top-quality services and
business owners managing service.

## Features

### Client Panel:

1. **Set User City**  
   Clients can set their city, which helps tailor service recommendations based on location.

2. **View Profile**  
   Clients can view their personal information and manage their profile.

3. **Add and Delete Opinions**  
   Clients can leave feedback about services and delete their opinions if necessary.

4. **Book Appointments**  
   Clients can book appointments with various service providers.

5. **View Bookings**  
   Clients can view their upcoming bookings.
6. **Support Chats**

   Clients can initiate a support chat with the admin if they need help, and respond to open chats.


 ### Admin panel:

1. **Manage Service Categories**  
       Admins can add and manage service categories.

2. **Business Profile Verification**  
   Admins can review and approve/reject business profiles for verification.

3. **Service Opinions**  
   Admins can view, delete, and manage service opinions from users.

4. **Reported Opinions**  
   Admins can review reported opinions and take necessary actions.

5. **Manage Business Owners**  
   Admins can view and manage business owners associated with the platform.
6. **Manage Support Chats**

   Admins can manage and respond to support chat requests, ensuring client issues are handled efficiently.
### Owner panel:

1. **Business Profile Management**  
   Service providers can view and manage their business profile and send verification requests for business profile approval.

2. **Service Options Management**  
   Owners can add, update, or delete service options associated with their business.

3. **Availability Management**  
   Owners can set and update availability for their services, ensuring customers can book appointments.

4. **Booking Management**  
   Owners can view and manage pending and confirmed bookings for their services, including accepting or rejecting reservations.

5. **Report Opinions**  
   Service providers can report inappropriate or fraudulent opinions to the admin for review.
6. **Support Chats**

    Owners can initiate a support chat with the admin if they need help, and respond to open chats.

## Technologies used:

- **Java 17**: The programming language used for development.
- **Spring Boot**: Framework for building the application.
    - **Spring Data JPA**: For database interactions.
    - **Spring Security**: For securing the application.
    - **Spring Boot Web**: For building web applications.
    - **Spring Boot Starter Test**: For testing the application.
- **Lombok**: To reduce boilerplate code by generating getters, setters, etc.
- **Hibernate**: ORM framework used with Spring Data JPA.
- **JSON Web Token (JWT)**: For authentication and authorization.
- **PostgreSQL**: The relational database management system used.
- **JUnit**: For unit testing.
- **Spring Data REST WebMVC**: For creating RESTful APIs.
- **Maven**
- **IntelliJ IDEA**
- **Postman**

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Postman**: https://www.postman.com/downloads/
- **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/)

## How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/LukaszCh233/EzLookAndBook.git
cd EzLookAndBook
```

### 2. Build and Start the Containers

```bash
docker-compose up --build
```

### 3. Stopping the Containers

```bash
docker-compose down
```