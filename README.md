## CCH App Backend
This is the backend repo for the [Christian Campus House app](https://github.com/jackcase04/cch-app-mobile) chores reminder app. It manages chores and notifications for 64 residents, automating what was previously done manually with Excel.

## Features

- RESTful API made with Java Spring Boot
- User authentication
- Chore scheduling — schedules migrated from Excel into PostgreSQL
- Scheduled push notifications
- Cloud deployment using AWS Elastic Beanstalk
- PostgreSQL database hosted on AWS RDS
- CI/CD pipeline with GitHub Actions + Docker for automated builds and deployments

## Technologies

- Language: Java
- Framework: Spring Boot
- Database: PostgreSQL
- Infrastructure: AWS Elastic Beanstalk, AWS RDS
- CI/CD: GitHub Actions with Docker

## Impact

- 64 active users use the app daily in the Christian Campus House
- Replaced a manual Excel-based system with automated chore tracking and notifications
- Improved accountability and efficiency in house operations

## Design
Database ER Diagram:  

![ER Diagram](assets/CCHAppER.png)

## Future Work

- [ ] Admin dashboard for chore/role management
- [ ] Analytics on task completion
