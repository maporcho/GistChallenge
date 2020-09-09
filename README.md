# Netshoes Gist Challenge

An Android app where users can list all available public gists, and add gists to a favorite list.

![GistChallenge](/docs/gist_challenge.png)

*Leia em [português](README.pt_BR.md)*


## Main features

* **Gist list**: list all public gists. Users can see the Gist Detail by tapping a list item. Users are also able to add/remove a gists from the favorite list, by tapping the star in each list item.

* **Gist detail**: shows the gist owner name, along with the avatar picture. The gist's description and URL is also shown.

* **Favorite gist list**: list all favorited gists. Users can detail a favorite gist by tapping an item in the list. 
Users can remove a favorite by tapping the star in each favorite list item.
Both the favorite gist list and the favorite gist detail are available offline.


## The Solution
The app was built following a MVP architecture, focusing in modularity and testability.

The source code is organized as follows:

* **/common:** non-business related classes used in multiple use cases.

* **/use-cases/<use case>:** business logic and classes related to a specific use case
  * *\<use case\>Contract:* the contract for the use case, tying together the View and Presenter.
  * **/model:** the models used in the specific use case
  * **/presenter:** the Presenter used in the specific use case (descendants of *BasePresenter*)
  * **/repository**: the Repositories used in the specific use case
  * **/ui:** Activities and Fragment used in the view layer for the use case (descendants of *BaseView*)

Interfaces were defined for most classes, and the implementations are in the **implementation** subpackage of the package they're contained into. This was intended to make it easier to test classes (by mocking or stubbing their dependencies, for example).

### Error handling

The Presenters and Repositories methods handle errors by means of callbacks (onSuccess and onFailure). This ensure that client classes are aware of both success and error conditions, and can handle both appropriately.

### Flavors

There are three flavors available: indigo, blue and dark. Each flavor define a different version name and color for the app.

### Tests

A sample unit test is in a **/test** subfolder. This folder structure follows the one found in the /main/java folder.


## Frameworks and libraries used

* **[Kodein](https://kodein.org/Kodein-DI/)**: dependency injection
* **[Retrofit](https://square.github.io/retrofit/)**: REST client
* **[Picasso](https://square.github.io/picasso/)**: image loading
* **[Room](https://developer.android.com/topic/libraries/architecture/room)**: persistence library
* **[Stetho](http://facebook.github.io/stetho/)**: debug utility





