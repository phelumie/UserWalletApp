# UserwalletAPP

Welcome to the UserwalletAPP repository! This project is a user wallet application that allows users to sign up and manage their digital transactions efficiently. It utilizes various technologies such as Firebase for authentication, PostgreSQL as the SQL database, RabbitMQ for interservice communication, and MongoDB for notifications (although notification implementation is pending).

## Features

- **User Registration and Wallet Creation:** Users can sign up for an account, and upon registration, a wallet account is automatically created for them. The user management service sends a message using RabbitMQ to the wallet service to initiate the wallet creation process.

- **Scheduled Deposits:** Users can create scheduled deposits where multiple beneficiaries can receive funds. A scheduled job runs every day at 12 PM GMT+1. If a request for creating a schedule deposit is received before 11:58 AM and the starting date is the current date, the system adjusts the next schedule date to the following day. The job obtains a pessimistic lock on all wallets associated with the schedule deposit to prevent inconsistencies during concurrent transactions.

- **Balance Check and Notifications:** During the scheduled job execution, if the current wallet balance is insufficient to cover all beneficiaries' deposits, the next schedule date is adjusted based on the configured parameters. Additionally, the wallet service sends a message to the notification service, indicating whether to use email or mobile notification, to inform the user about the status of their deposit.

**Note:** Please be aware that some API endpoints are protected and require proper authorization. If a user is not authorized, they will receive a 403 (Forbidden) response. If a user is not authenticated, they will receive a 401 (Unauthorized) response.

## Prerequisites

To run this project, ensure that you have the following dependencies installed:

- Java 17
- Docker

## Getting Started

To get started with the UserwalletAPP project, follow these steps:

1. Clone the repository to your local machine.

```shell
git clone https://github.com/phelumie/UserWalletApp.git
```

2. Obtain the Firebase configuration file from the Firebase Console. This file is required for authentication.

3. Upload the JSON file obtained from Firebase to the project's classpath.

4. Set the `firebase.apikey` environment variable in your development environment, and assign it the API key given by Firebase.

5. Start PostgreSQL and RabbitMQ using Docker.

```shell
docker run --name postgres -p 5455:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -d postgres

docker run -d --name my_rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9.21-management-alpine
```

6. Configure the necessary connection details for PostgreSQL and RabbitMQ in the project.

7. Build and run the Spring Boot application.

8. Access the Swagger API documentation for each microservice:

- User Management Service: [http://localhost:9090/swagger-ui/index.html#/](http://localhost:9090/swagger-ui/index.html#/)

- User Wallet Service: [http://localhost:9090/swagger-ui/index.html#/](http://localhost:9090/swagger-ui/index.html#/)

9. Access the application by navigating to the appropriate URL in your web browser.

**Note:** Some API endpoints require proper authorization. If a user is not authorized, they will receive a 403 (Forbidden) response. If a user is not authenticated, they will receive a 401 (Unauthorized) response. The application includes proper logging and error handling to ensure a smooth user experience.

## Contribution

Contributions to the UserwalletAPP project are welcome! If you encounter any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
