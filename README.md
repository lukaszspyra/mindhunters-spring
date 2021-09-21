## mindhunters-spring

My own conversion of team application created during iSA bootcamp. 
Java EE project re-written to use Spring framework, run on Tomcat server with Google OAuth2 login. 

Virtual Bartender with extensive database of cocktail recipes (both alcoholic and non-alcoholic). 

### Features include: 
- searching recipes by names/ingredients,
- filtering list of recipes by kind of beverage,
- recipe management - users can add cocktails to favourites, send their own propositions or edit/remove existing ones. Both actions are reviewed by admins before acceptance, 
- admins can display statistics, nominate another admin from existing users, accept/reject recipe proposals - both decisions are confirmed by emailing system to authors.

### Technologies used:
- Spring Boot 2.3.1,
- Freemarker,
- Hibernate,
- database management: 
  - Mysql for app hosted on Amazon AWS,
  - Postgresql for app hosted on Heroku.
- SLF4j loggers,
- JQuery/CSS/Bootstrap.

Application available @:
https://mindhunters.herokuapp.com/
