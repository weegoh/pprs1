# Gang of Fours - PPRS

This project is designed to be a functionl prototype for a Personalised Perscription Recommedation System (PPRS). The project has been designed and written to meet the requirements of CSC3600 ICT Professional Project, as our capstone course.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The below list provides a functional set of tools to develop this project. Development has been tested on a Windows 10 environment only, the instructions below represent this. Whilst other editors and configurations may work, it is best for us to have a uniform toolkit to work with. Please download the following:

[MySQL Communinity Edition - Installer 8.0.15](https://dev.mysql.com/downloads/windows/installer/8.0.html "Download MySQL Installer")

[Java SE Development Kit 8u201](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html "Download JDK 8u201")

[Maven 3.6.0 Binary Zip](https://maven.apache.org/download.cgi "Download Maven 3.6.0")

[Git for Windows](https://git-scm.com/download/win "Download Git for Windows")

[Visual Studio Code](https://code.visualstudio.com/Download "Download Visual Studio Code")

### Installing

Following these step-by-step instructions should get your development environment up and running.
1. Install MySQL Community Edition
    * Run the installer with custom installer options
    * Choose the following components to install:
    > MySQL Server
    
    > MySQL Workbench

    > Connector / J

    > MySQL Shell
    * Install any required dependancies
    * Click through to the accounts and roles section
    * Enter root password of "GangOf4s"
    * Click through and apply the configuration
    * Once installed, open MySQL workbench and create a DB called pprs and a user named pprs with full privelages on the pprs DB

2. Install Java 8 SDK
    * Run the installer, using the default options
    * Go to Control Panel > System > Avanced System Settings > Environment Variables
    * Create a new User Variable:
    > Variable Name: JAVA_HOME

    > Variable Value: C:\Program Files\Java\jdk1.8.0_201
    * Edit the user PATH variable and add new entry:
    > Value: C:\Program Files\Java\jdk1.8.0_201\bin

3. Install Maven 3.6.0
    * Unpack contents of zip folder into C:\Program Files
    * Go to Control Panel > System > Avanced System Settings > Environment Variables
    * Edit the user PATH variable and add new entry:
    > Value: C:\Program Files\apache-maven-3.6.0\bin

4. Install Git for Windows
    * Run installer with all default settings (you can chose your default editor as VS Code if you want)

5. Install Visual Studio Code
    * Run the installer with default options
    * Once installed, install the following extensions:
    > Java Extension Pack

    > Spring Boot Extension Pack

    > Lombok Annotations Support for VS Code

6. Clone the repository to your local machine
    * Navigate to the folder where you wish to store the repository on your computer.
    * Right click in the file explorer and select "Git Bash Here"
    * Type the following commands:
    ```
    git clone https://github.com/mnelsonwhite/GangOfFours.git
    ```
    ```
    git config --global user.name "Your user name"
    ```
    ```
    git config --global user.email "Your email here"
    ```
7. Open the repository in Visual Sudio Code
    * Open Visual Studio Code
    * Select File > Open Folder
    * Select the path to the repository folder you just created
8. Check it is all working
    * Expand the Spring-Boot Dashboard at the bottom of the explorer pane
    * Right click on the application and select run
    * In your web browser, navigate to http://localhost:8080 to see the home page

## Deployment

Add additional notes about how to deploy this on a live system will be provided later.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Contributing

This project is currently not open to external contributors.

## Authors

* **Matthew Nelson-White** - *Initial work* - [Mnelsonwhite](https://github.com/mnelsonwhite)
* **Ashley Rowe** - *Initial work* - [Ashmosis](https://github.com/ashmosis)
* **Ben Anderson** - *Initial work* - [BAnderson76](https://github.com/BAnderson76)
* **Kyle Taylor** - *Initial work* - [KyleTaylor95](https://github.com/KyleTaylor95)


## License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details.

