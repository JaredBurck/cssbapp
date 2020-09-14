# Using Docker
## Setup
1. You should have already created a spring-framework folder inside your IdeaProjects directory
1. Download from the docker folder from this [SharePoint site](https://compsourcemutual.sharepoint.com/:f:/r/sites/InformationTechnology/Shared%20Documents/Training/Spring%20Framework?csf=1&web=1&e=b9seAb) 
1. Unzip the download and put the docker folder with all its’ content inside the spring-framework folder

## Build a war
You need to build a war file from your project first.
1. Open the Gradle window if necessary (from the right sidebar, select Gradle)
1. Select exercise<#>>Tasks>build>build
1. Assuming there were no compilation errors, you can verify a war exists
    1. In the Project window, under the exercise<#>/build/libs folder, you should see a .war file

## Running your application
1. Open PowerShell window if necessary
1. Navigate to your IdeaProjects\spring-framework\docker folder if necessary
1. For exercises 1 & 2:
    1. Run the script `sfw-start <exercise #>`
        1. e.g sfw-start 1
    1. You should see Docker build your container, jboss start, your application deployed, started, and running.
    1. Test your application using the url and port specified by the exercise instructions
    1. When done, see the __Stopping your application__ section below
1. For exercise 3:
    1. Run the script `sfw-start <exercise #> <env>` replacing env with nothing, test, or prod
        1. e.g. sfw-start 3 prod
    1. You should see Docker build your container, jboss start, your application deployed, started, and running.
    1. Test your application using the url and port specified by the exercise instructions
    1. When done, see the __Stopping your application__ section below

## Stopping your application
1. Open a second PowerShell window if necessary
1. Navigate to your IdeaProjects\spring-framework\docker folder if necessary
1. Run the script `sfw-stop`
1. In your 1st PowerShell window, you should see jboss shutdown, the container(s) stop, and then Docker clean up files
1. If your application does not stop, at the command prompt, type: `docker-compose down`

## Troubleshooting
1. If your 1st PowerShell window does not stop, click in the window and press Ctrl-C.  Docker should stop.
2. To get help, at a PowerShell window prompt where you have navigated to your docker folder, type `docker-compose config`.
Copy the results and send them to your instructor.
