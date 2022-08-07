# MovieMatchV2

This project is my capstone project for the Software Development AEC at Vanier College in Montreal. I will be creating a dating app style Movie Matching Android application, using swipeable cards and matching to another user. I am hoping to integrate third party login, with Google and Meta, as well as a chat function and a review function. The API that I am using is called Streaming Availability by Movie of the Night and allows for many different services from over 60 countries. The first 3 services that I will aim for will be Netflix, Amazon Prime and Disney+ as those are the three that I have and will make testing easier.

*** Week 1 (June 27 – Jul 3) ***  
As I am doing this retroactively and forgot to save my earlier iterations of the README file, I will attempt to recreate each week from the start. I created my proposal this week and while waiting for approval, I started looking at a wireframe for a DFD and which UML diagrams that I would need to complete for the project. I used draw.io as I am most comfortable with this and will continue to do so as I move forward. After approval, I moved ahead on the Project Planning and Analysis document which helped me to assess the size and complexity of the project. Please refer to the document for a more detailed version of the project. It was in this document that I decided on most of the diagrams and ideas behind the project.

*** Week 2 (Jul 4 – Jul 10) ***
This week, I moved into some of the coding and diagrams. I was able to implement the Firebase Database and set up the Login and Registration. I started with a library and tutorial on how to create the swipeable card interface but once I implemented the API call, I ran into a ton of issues with integration. As such, near the end of the week, I moved onto a different approach and was able to create the interface that I was looking for. I also had to recreate a different repository as I ran into an error after trying to merge my work. 

*** Week 3 (Jul 11 - Jul 17) ***
At the start of this week, I have been able to move into a new GitHub repo and will be using this one. During the troubleshooting from last week, I believe that I have learned enough to not run into anymore issues and if I do, I will reach out before deleting and starting again. I didn’t realize that I would be using it to track hours worked…although it makes sense. This week’s plan is to create more documentation for the next steps, which is saving swipes to database for matching, creating a host lobby and a join button, OTP creation and sharing as well as the third-party logins. I have worked on the UI/UX as well as some of the required menus during the weekend so as to not be overwhelmed when it comes time to put all of this together. At the end of this week, I had a meeting with my coordinator and after the discussion, I knew that the next week was going to be a busy one as there would be a change of direction for the application. In place of matching with a person that you know and are most likely beside, the application will now match you with anyone who is interested in the same movie.

*** Week 4 (Jul 18 - Jul 24) ***
To start off the week, I looked at the original documentation and saw that I would have to make changes to the database structure as well as the logic behind the Swipe and Match activities. I had a really difficult time with the logic and ended up making some cuts to the program. I cut out the OTP for a lobby and put the social media sharing on the back burner. I looked through the documentation for Firebase RealTime database as well as Android documentation to start building the queries and references to ensure that I would be able to have te Matches show the correct person for the correct movies. By the end of the week, I had run into several issues with this logic. I could see the information that I wanted in the Logcat, I could sort it but my loops would onyl go through all the information and I was having issues with the display aspect. I started work on the PowerPoint presentation as well as the Word doc that will acoompany the presentation. Also, there were some front end improvements, as well as added parameters to the API call.

*** Week 5 (Jul 25 - Jul 31) ***
I continued to have a really difficult time with the display of information from Swipe and Matches. I tried nesting in ValueEventListeners and ChildEventListeners with no luck. During the time that I wasn't working on this (which, to be honest, was about 80% pf my time as it was a very integral part of the application), I continued to try and improve the UI and UX by adding in the finalized NavBar menu, the Details on Long Push for movies as well as updating the UML diagrams and DFD diagrams due to the changes that came from the meeting a couple of weeks ago. I even worked through the weekend as the timeline that I had put in place for porgramming was fast approaching. Finally, on Sunday, I was able imlement a logic where each user who chose "Yes" for a movie would immediately be shown their matches for that film! Success on such an integral part of the app led to a pretty enthusiastic pop off as well as a giant sigh of relief!!

*** Week 6 (Aug 1 - Aug 7) ***
This week marked the real start of documentation as well as the opportunity to work on the extra bells and whistles that were envisioned earlier on in the project. Among these are the ability to play a choice directly from the application, a movie and series details page as well as the ability to change platforms directly from the swiping activity. The random page generator had to be updated which took 2 days due to running out of the free version's API calls as well. Also, I was able to get the messaging between users enabled. I was hoping to be able to a dd a chat feature withtin the application itself but ran out of programming time. Both users manuals (in app and separate) were written this week, and the SDD template was found. The first 9 pages were written this week and the diagrams from early planning were added into the document. The coming week will see the end of the documentation section, unit tests will be implemented and final touches added to the UI of the application.



