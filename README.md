# gameStudio

**My DB is running on port 5433 !!!**

**Configuration** is in file /psql.conf (using Properties !!!)

**DB schemas** are in file /db.sql

BRANCHES:
---
main - stable working version

work - not stable, sometimes not even working!

STATUS:
---

Mines
- working
- hiscores displayed after game or in sub-menu
- fixed multiple hiscore records
- ranking after each game (TODO: move it to exit from game :/ )
- hiscores are using JPA now!

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

JPA
- separate DB: gamestudiojpa
- fixed issue with grouped *Services in GameStudioServices
- hiscores



