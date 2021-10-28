## Andes Coachmark
### Description
AndesCoachmark is a component indent to guide the customers through the new features that are showing up on an app. This is designed for working "alone". It means that you just need to set up a few configurations. Check the [RFC](https://docs.google.com/document/d/1Bgkprwm9wNo4dc5Q2fC09t2Xak0qcWjHpSj62wjaSj4/edit) here for more information about the definition.
## Usage
Showing a Coachmark needs the following three steps:
* Creating the steps
* Creating the model data
* Creating the component Here is an example of how you can build the Coachmark:
### Creating the steps
Every step highlights a single UIView and configures the title and the description of the tour. Also, the next button text has to be set, as well. The style will be describe it later.
```
val firstStep = AndesWalkthroughCoachmarkStep(
            title = "Title", 
            description = "Description", 
            nextText = "Next", 
            view = firstView, 
            style = AndesWalkthroughCoachmarkStyle.RECTANGLE
        )
val secondStep = AndesWalkthroughCoachmarkStep(
            title = "Title", 
            description = "Description", 
            nextText = "Got it!", 
            view = secondView, 
            style = AndesWalkthroughCoachmarkStyle.CIRCLE
        )
```
### Creating the model data
The coachmark knows to handle the NestedScrollView in order to scroll when is needed. It will do this for you if you set the scrollView in the model. Furthermore, you can set the completionHandler for performing actions when the coachmark finishes its tour.
```
val model = AndesWalkthroughCoachmark(
            steps = mutableListOf(firstStep, secondStep), 
            scrollView = scrollView
        ) {
            println("End of Coachmark")
        }
```
### Creating the coachmark
Finally, you create the component with a model:
```
let coachmark = AndesCoachMarkView(model: model)
```
## Highlighting Style
The style duration is specified with an enum containing three different values:
* rectangle - The most common case
* circle - Use it only if the view to point is a circle
## Some considerations
build() method must be used so that the coachmark show up on the screen. If you need perform custom actions (tracking) you could use the callbacks delegate:
* onClose
* onNext
```
coachmark.withTrackingListener(object: CoachmarkTracking {
                override fun onClose(position: Int) {
                    TODO("Not yet implemented")
                }
                override fun onNext(position: Int) {
                    TODO("Not yet implemented")
                }
            })
```
## Let's see it in action
![videoplayback (2) (1)](https://user-images.githubusercontent.com/18038862/93133052-feed6500-f6ac-11ea-8fe8-7d355a1828b1.gif)