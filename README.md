github-comment-counter [![Build Status](https://travis-ci.org/tzachz/github-comment-counter.svg?branch=master)](https://travis-ci.org/tzachz/github-comment-counter) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=tzachz_github-comment-counter&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=tzachz_github-comment-counter) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tzachz_github-comment-counter&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tzachz_github-comment-counter)
======================

A [dropwizard](https://github.com/codahale/dropwizard)-based server displaying number of Pull Request Comments performed by an organization's GitHub users, to help organizations identify and celebrate top reviewers. Counts all recent in-code comments on organization's opened and closed Pull Requests, excluding comments on one's own Pull Requests.
Queries [GitHub API](http://developer.github.com/v3/) every X minutes and displays the result.

Live Demo: [GitHub Organization's Top Reviewers](https://github-comment-counter.herokuapp.com/service/leaderboard/month)

![alt tag](https://raw.githubusercontent.com/tzachz/github-comment-counter/master/leaderboard-sample.png)


Deployment
==========
There are three deployment options:
 1. Using Heroku: if you have a [Heroku](https://www.heroku.com/) account, that's the easiest way, skip to [Heroku Deployment](#heroku-deployment)
 2. [Build](#build) from source code, after adjusting [Configuration](#configuration)
 3. Using latest release: 
    - Download latest **zip** and **yml** file from [releases](https://github.com/tzachz/github-comment-counter/releases) page
    - Unzip the zip file, e.g.: `unzip ~/Downloads/leaderboard-server-0.1.57.zip`
    - Copy yml file into unzipped folder, e.g.: `cd leaderboard-server-0.1.57 && cp ~/Downloads/leaderboard-server.yml .`
    - Edit the yml file per insructions in [Configuration](#configuration) section
    - Run the server: `./bin/leaderboard-server server leaderboard-server.yml`


Configuration
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

repositories:           # subset of repositories to consider.  If
- repo1                 # empty will read all repositories in the org.
- repo2

refreshRateMinutes: 10  # interval between API activations
```
Specify either a token or a username and password.

If you're using a locally-hosted GitHub instance, you can override the default API endpoint URL by adding something like this to your yml file:
```yml
gitHubApiUrl: https://github.corp.xyzcompany.com/api/v3/
```

Build
=====

To run the tests, you'll need to supply credentials, since some of the tests require a github user to activate the API. Here and elsewhere, you can either pass your GitHub username and password as credentials
or pass a single [OAuth token](https://github.com/settings/applications) associated with your account.
```
./gradlew clean build -Dusername=<a github user> -Dpassword=<her password> -Dtoken=<an OAuth token>
```

(If your user account has [two-factor authentication](https://help.github.com/articles/about-two-factor-authentication/) enabled, only token-based access will work.)

To run the server locally (without running tests, and after properly setting [Configuration](#configuration)), run:
```
./gradlew run
```

To build an artifact you can deploy elsewhere, run:
```
./gradlew distZip
```
The zipfile will be created by Gradle's application plugin with a `./bin/leaderboard-server` file that can be used to start the server as follows:
```
./bin/leaderboard-server server leaderboard-server.yml
```

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
