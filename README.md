## CCH App Backend

This is the backend repo for the [Christian Campus House app](https://github.com/jackcase04/cch-app-mobile) chores reminder app. It manages chores and notifications for 64 residents, automating what was previously done manually with Excel.

If you are a house admin, scroll to the bottom of this README for instructions on updating the database.

### Features

- REST API made with Java Spring Boot
- User authentication
- Chore scheduling
- Scheduled push notifications
- Cloud deployment using AWS Elastic Beanstalk
- PostgreSQL database hosted on AWS RDS
- CI/CD pipeline with GitHub Actions + Docker for automated builds and deployments

### Technologies

- Language: Java
- Framework: Spring Boot
- Database: PostgreSQL
- Infrastructure: AWS Elastic Beanstalk, AWS RDS
- CI/CD: GitHub Actions with Docker

### Impact

- 64 active users use the app daily in the Christian Campus House
- Replaced a manual Excel-based system with automated chore tracking and notifications
- Improved accountability and efficiency in house operations

### Design
Database ER Diagram:  

![ER Diagram](assets/CCHAppER.png)

### For House Admin:

Before every semester, the new residents will need to be added to the database, and old chores will need to be purged. To do this, follow these steps EXACTLY:

### Names

- Request from the current house minister a Google Sheet with the updated residents and chores for the current semester. We will refer to this as `Updated Chores`.
- Request the Google Sheet file `Chores Document for App` from the previous admin.
- Navigate to the `Control` tab in the "Chores Document for App" Google Sheet.
    - From the `Updated Chores` Google Sheet, copy and paste the names from the `Control` sheet into the `Chores Document for App`'s `Control` names section.
    - The names should be formatted exactly as such:
        - Firstname Lastname
    - If all is done properly, you should end up with a result looking like this:

![Details 1](assets/details1.png)

### Dates

- Navigate now to the `New_Room` sheet in the `Chores Document for App` Google Sheet.
- The dates going from cell `D3:S3` will almost surely be incorrect. You will need to correct them for the specific semester.
- Go onto your calendar app of choice, and starting with the first week of classes for the semester, fill out the dates for all 16 weeks of the semester.
- This will be in cells `D3:S3`. Start with `D3` and move down the line to `S3` at the end.
- The format MUST be in this format, with NO preceding 0's:
    - `Month/Day-Month/Day`
    - EX: `4/13-4/17`, `9/29-10/3`, etc
- Depending on the semester, there will be a week skipped for either Spring Break or Fall Break. Take this into account when updating the dates, and check on the offical Missouri S&T website to be sure.
- Once done, you will have a result such as this:

![Details 2](assets/details2.png)

- NOTE: Should go all the way to 16 weeks, screenshot only goes to 8 for compactness.
- Next, navigate to the `Old_Room` sheet in the `Chores Document for App` Google Sheet.
- Copy and paste the exact contents of `D3:S3` that you just updated in `New_Room` into cells `D3:S3` in the `Old_Room` tab.

### Names Output

- Once the above steps are done, the Google sheet will have generated a list of all the names and chores for all residents for the semester. Start first on exporting the names.
- Navigate to the `Names` tab in the bottom of the `Chores Document for App` Google Sheet. It should have generated a long list of names such as this:

![Details 3](assets/details3.png)

- NOTE: Cells should go down to row 64.
- Confirm that this looks correct. Then navigate to `File -> Download -> Comma Seperated Values (.csv)` button in the top right of the Google Sheet. Click this button to download the names as a CSV file on your computer.

### Chores Output

- Next step is to export the chores.
- Navigate to the `Output` tab in the bottom of the `Chores Document for App` Google Sheet. It should have generated a long list of names such as this:

![Details 4](assets/details4.png)

- NOTE: Cells should go down to row 1793.
- Confirm that this looks correct. Then navigate to `File -> Download -> Comma Seperated Values (.csv)` button in the top right of the Google Sheet. Click this button to download the chores as a CSV file on your computer.

With these 2 CSV files, you now have everything you need to update the database for the semester.

### Future Work

- [ ] Rework notification scheduling approach away from a polling approach. This will help reduce cloud costs.
    - Potentially consider Client side scheduling
- [ ] Look into self-hosting for even cheaper costs.
- [ ] Admin dashboard for chore/role management.
- [ ] Analytics on task completion.
    - EX: Task completed at `timestamp`, etc
