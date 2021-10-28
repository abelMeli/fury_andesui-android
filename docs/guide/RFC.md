> “Truth can only be found in one place: the RFC.” 

## Motivation 

Using RFCs (Request For Comments) in AndesUI arises mainly for two reasons: 
- RFC can guide the component's analysis stage, where the definition must be synchronized between both platforms (Anroid & iOS) and the UX team. A complete analysis of the component must be realized using the UXs specs already defined. The RFC is just a medium to formalize this stage. 

- Being Andes a collaborative library, it's important that any developer could access to the definition info for any Andes Component, even when the component is not going to be developed by his team. This approach makes it easy for all the developers to challenge the definition before the component is already implemented, and so we are closer to the best component that we can build. 

Some advantages of using RFC for components definitions are: 

* A cleaner documentation, more complete and understandable.
* Source of truth for both platforms & UX. 
* Visibility for all the stakeholders, including the opposite platform devs or from another team. 
* Probably less fixes should be made after the implementation stage.
* It's easier to contribute to the component's definition. 

## How to 

1. Create a shared Google Doc allowing to comment to all devs in MELI organization. 
2. Create an issue in Github for the corresponding platforms ([iOS](https://github.com/mercadolibre/fury_andesui-ios/issues) | [Android](https://github.com/mercadolibre/fury_andesui-android/issues)). 
3. Define the RFC Tag with this format: 
* If the component is for both platforms: `#{#ANDROID-ISSUE}-{#ISSUE-IOS}` (For example: RFC #14-91) 
* If the component is just for Android: `#{#ISSUE_Android}-Android`
* If the component is just for iOS: `#{#ISSUE_IOS}-IOS`
4. [Here is a template](https://docs.google.com/document/d/1AdLtle0Iz211Ad2ouPMRQ_i_9Sp2M3lJsJGOyOlwgKI/edit#) to guide the RFC's development. 

## Types 

Use a Github Label to represent de type of the RFC created. 
* **New component**: To develop an entire new component or a new variant. 
* **Feature**: To develop a new feature for an existing Andes component. 
* **Other**

## States 

To define the different stages that the RFC could pass through, we use a Github Label to represent the State.  

* **Definition**: At this stage, the RFC is created, the component is analyzed and all doubts are clarified with the UX team.
* **Waiting for comments**: After the definition stage is finalized, we jump to this stage for 3 to 5 days (defined by the creator and depending of the component complexity). During this stages, all the stakeholder can review the RFC and suggest changes. 
* **Approved**: After the last state is completed and if there is no pending review, the RFC is approved by the owners and component's Pull Requests can be merged. 
* **Rejected**: The RFC was rejected at the definition stage (apply mostly for feature requests). 