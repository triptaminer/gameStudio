# gameStudio

**My DB is running on port 5433 !!!**

**JDBC Configuration** is in file /psql.conf

**DB schemas** are in file /db.sql

BRANCHES:
---

test-221128 - self-explaining (new)

test-221114 - (will be removed soon)

main - stable working version (currently thymeleaf chapter)

work - not stable, sometimes not even working!

STATUS:
---
Registration
- new users are now able to register
- FIXME: only hardcoded password 'heslo' is working, user passwords are not stored yet
- login/register screens now looks little better

Initialisation
- when gameStudio is starting with blank DB, it will fill itself with some default users and other content
- see JPA properties file for correct setup! 
- FIXME: init triggered by login form only!

Thymeleaf and WEB ui
- ALL games working
- all services working
- login working
- re-designed all game boards
- TODO: 
  - register of new user
  - re-work design (partially done)

All WEB games
- fixed bug when player was able to act after game ends

Mines
- working
- left click open, right click (un)mark in web version
- removed image-based design, now we are using CSS only
 
Tiles
- working

Light
- working

GameStudio
- loading configuration from config file located in root
- extracted service connections to separate class
- preparation for different DB services
- created ALL 3 services
- highscores are working on every game
- Spring runner
- rankings displayed next to each game in main menu

JPA
- separate DB: gamestudiojpa
- all services working
- JPA playground package (user input not implemented yet)



