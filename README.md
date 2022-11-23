# gameStudio

**My DB is running on port 5433 !!!**

**JDBC Configuration** is in file /psql.conf

**DB schemas** are in file /db.sql

BRANCHES:
---

test-221114 - self-explaining

main - stable working version (currently thymeleaf chapter)

work - not stable, sometimes not even working!

STATUS:
---

Thymeleaf and WEB ui
- ALL games working
- all services working
- login working
- TODO: 
  - register of new user
  - re-work design

Mines
- working
- hiscores displayed after game or in sub-menu
- fixed multiple hiscore records
- ranking after each game (TODO: move it to exit from game :/ )
- hiscores are using JPA now!
- left click open, right click (un)mark
 
Tiles
- working
- hiscores displayed after game or in sub-menu
- removed obsolete HiScore class

Light
- working
- hiscores displayed after game or in sub-menu
- removed obsolete HiScore class
- cleaned code from unused features of Tiles

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



