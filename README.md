# gameStudio

**My DB is running on port 5433 !!!**

**JDBC Configuration** is in file /psql.conf

**DB schemas** are in file /db.sql

BRANCHES:
---

mini-task - 1.12: self-explaining (will be removed soon?)

test-221128 - self-explaining (new)

test-221114 - (will be removed soon)

main - stable working version (currently thymeleaf chapter)

work - not stable, sometimes not even working!

STATUS:
---

Profiles:
- WiP
- currently available only from nickname in comments
- added "privileges" (user->admin)
- added counter for points (not implemented yet)
- added states for muted / banned 

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
- !!! switching to dynamic version - working OK
- working
- left click open, right click (un)mark in web version
- removed image-based design, now we are using CSS only
 
Tiles
- working
- TODO: dynamic version?

Light
- working
- TODO: dynamic version?

GameStudio
- !!! highscores are now dynamical !!! 
- loading configuration from config file located in root
- created ALL 3 services
- Spring runner
- rankings displayed next to each game in main menu
- TODO: make other services dynamic too

JPA
- separate DB: gamestudiojpa
- all services working
-  ! removed JPA playground package



