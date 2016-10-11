github-comment-counter [![Build Status](https://travis-ci.org/tzachz/github-comment-counter.svg?branch=master)](https://travis-ci.org/tzachz/github-comment-counter)
======================

A [dropwizard](https://github.com/codahale/dropwizard)-based server displaying number of Pull Request Comments performed by an organization's GitHub users, to help organizations identify and celebrate top reviewers. Counts all recent in-code comments on organization's opened and closed Pull Requests, excluding comments on one's own Pull Requests.
Queries [GitHub API](http://developer.github.com/v3/) every X minutes and displays the result.

Live Demo: [GitHub Organization's Top Reviewers](https://github-comment-counter.herokuapp.com/service/leaderboard/month)

![alt tag](https://raw.githubusercontent.com/tzachz/github-comment-counter/master/leaderboard-sample.png)


Deployment
==========
There are three deployment options:
 1. Using Heroku: if you have a [Heroku](https://www.heroku.com/) account, that's the easiest way, skip to [Heroku Deployment](#heroku-deployment)
 2. Downlowding latest release: download latest **jar** and **yml** file from [releases](https://github.com/tzachz/github-comment-counter/releases) page, and continue to [Usage](#usage) section
 3. [Build](#build) from source code, then continue to [Usage](#usage) section


Usage
=====
Edit the leaderboard-server.yml file with the following configuration:
```yml
gitHubCredentials:
    # change these to any valid github credentials; requires
    # either a username/password combination or a token
    username: my-user
    password: my-pass
    token: my-oauth-token

organization: my-org    # organization to show stats for
refreshRateMinutes: 10  # interval between API activations
```
Specify either a token or a username and password.

You're now ready to run the server:
```
java -jar <path to jar> server <path to yml file>
```

You should be able to see the results at `http://<host>:8080/`

If you're using a locally-hosted GitHub instance, you can override the default API endpoint URL by adding something like this to your yml file:
```yml
gitHubApiUrl: https://github.corp.xyzcompany.com/api/v3/
```

Build
=====
To build the artifact (without running tests), run:
```
./gradlew fatJar
```
The jar to use is located in `leaderboard-server/build/libs/`.

To run the tests, you'll need to supply credentials, since some of the tests require a github user to activate the API. Here and elsewhere, you can either pass your GitHub username and password as credentials
or pass a single [OAuth token](https://github.com/settings/applications) associated with your account.
```
./gradlew clean build -Dusername=<a github user> -Dpassword=<her password> -Dtoken=<an OAuth token>
```

(If your user account has [two-factor authentication](https://help.github.com/articles/about-two-factor-authentication/) enabled, only token-based access will work.)


Heroku Deployment
=================
**NOTE**: Heroku-deployed web servers are public by default, so use wisely, or choose another deployment method if you wish to keep your commenters/comments private.

Assuming you have a Heroku account, and Heroku toolbelt installed, run this from the project root to build, deploy, and run the application:
```bash
heroku login  # type user and password when prompted...
heroku create 
heroku config:set ORG_NAME=<your org name>
heroku config:set GH_USER=<username>  # skip if using token
heroku config:set GH_PASS=<password>  # skip if using token
heroku config:set GH_TOKEN=<token>    # skip if using user+password
git push heroku master  # this will take a while...
heroku open

```
The last command should open the app page in your default browser.
