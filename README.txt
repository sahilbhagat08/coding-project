# Harbor Take Home Project

Welcome to the Harbor take home project. We hope this is a good opportunity for you to showcase your skills.

## The Challenge

Build us a REST API for calendly. Remember to support

- Setting own availability
- Showing own availability
- Finding overlap in schedule between 2 users

It is up to you what else to support.

## Expectations

We care about

- Have you thought through what a good MVP looks like? Does your API support that?
- What trade-offs are you making in your design?
- Working code - we should be able to pull and hit the code locally. Bonus points if deployed somewhere.
- Any good engineer will make hacks when necessary - what are your hacks and why?

We don't care about

- Authentication
- UI
- Perfection - good and working quickly is better

It is up to you how much time you want to spend on this project. There are likely diminishing returns as the time spent goes up.

## Submission

Please fork this repository and reach out to Prakash when finished.

## Next Steps

After submission, we will conduct a 30 to 60 minute code review in person. We will ask you about your thinking and design choices.



Functional Requirements -
User should be able to set the availability
User should be able to find overlap of availability between two users

Non Functional Requirements -
Code should be maintainable i.e simple to understand and easily extendable.
Complete test coverage

Assumptions -
No strict requirements arround availability
Durable so we need to store state in the database layer
Privacy and Security is out of scope
No strict requirements arround latency so system should act as fast as possible
Scalability is out of scope
No strict requirements arround load

Users can have multiple Availabilities
Availabilities will have attributes like - id, userId, fromTime, toTime
Daily Cronjob to remove the older daily availabilities to free up space


Data Model Design

Relationships - Users has 1:1 relationship with Availabilities
We will use denormalization as all the data is being fetched together and is hieraical
Access Pattern -
Write availabilities by userID
Get Availabilities by userID
Get Availabilities by 2 userID and find intersection

Algorithm - How to find intersection?
Search for weekly availability first
calculate intersection for the week
calculate intersection for all days
Return the intersection intervals

Code structure should be modular

Instructions:
Save the script: Copy the above code and save it to a file named setup_docker_and_run.sh.
Make the script executable: Run the following command to make the script executable:

chmod +x setup_docker_and_run.sh

Run the script: Execute the script with the following command:

./setup_docker_and_run.sh

This will:
Install Docker and Docker Compose on your system.
Build and run your Docker Compose project.
This will run on linux based systems.
If you are on mac you need to install docker compose desktop edition and run -

docker-compose up --build

If you already have a working docker and docker compose then run -

docker-compose up --build


Go to http://localhost:8080/swagger-ui/index.htm to check API

