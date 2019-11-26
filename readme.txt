git link - https://github.com/kbagg/iitbqa

Project Name - IITB_Q&A

Team Members:

Ashwin Kumar - 194050001
Kaartik Bhushan - 194050006
Ankit Raj - 193050098

Contributions:

Ashwin Kumar - Android
Kaartik Bhushan - Django
Ankit Raj - Designing, UI, Presentation, Report

Motivation:

The main vision of this application is to build a bridge between newbie and experts in order to learn and explore different genres. Newly admitted students often have doubts/queries regarding subjects to choose \& field to explore but there is no forum to answer these queries. A student can contact few of the seniors from their department but not all, their might be an area that student want to explore but that belongs to other department. Not all queries need to be answered individually and many a times it is not known who in the campus might know the solution. Even previously admitted students don't know the ongoing research work in their department let alone any other department.
There is a serious requirement for a Q\&A forum working on the institute (or at least department) level. So we propose to build such a forum where students or professors alike can ask or answer questions with ease.

How to host:

If there is no data in the DB yet, then execute the following steps in order:

     ./manage.py makemigrations
     ./manage.py migrate
     ./manage.py loaddata initial\_data.json
     ./manage.py runserver

If one wants to erase the current DB and start from scratch, then execute these following commands in the main project-

     find . -path "*/migrations/*.py" -not -name "\_\_init\_\_.py" -delete
     find . -path "*/migrations/*.pyc"  -delete
     rm db.sqlite3
    
For running the application, perform the following steps in order:

   1.  Launch AndroidStudio
   2.  Download emulator image for the device one wants to use
   3.  Run the project using this image
