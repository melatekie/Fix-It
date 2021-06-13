Android App Design Project
===

# FIX IT!
## [Video Demo](https://www.youtube.com/watch?v=noWat9MWYlU)
## Group Members
- [Mei Tak Lee](https://github.com/melatekie)
- [Sheng Dong](https://github.com/sDong517)
- [Liulan Zheng](https://github.com/liulanz)
## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)
3. [Sprint Progress](#Sprint-Progress)

## Overview
### Description
An app that gathers homeowners, renters, and professionals to discuss home projects with ways to repair or fixes. Users are able to find professionals in their local area.

### App Evaluation
- **Category:** Social Networking/Services
- **Mobile:** Mobile is a fast and easy way for customers and professionals to connect with their needs.
- **Story:** Creates a platform for demands in the form of home repairs/services which is not readily available in today’s world. By having a streamlined application that can both advertise and fulfill needs, people are more inclined to spread and utilize the application.
- **Market:** The clients can be anyone who owns or rents a home (mostly adults). The professionals are people who have licenses or certifications related to home/building.
- **Habit:** This app can be used at least once per year when cleaning up their home, when maintenance is needed or for remodeling projects. It can be used as often to ask questions, give suggestions, or to showcase their home projects. 
- **Scope:** Allow users to browse Q/A on home projects and allow professionals to advertise their services.


## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Login
    - Users log in with their username and password
    - Log in button disabled when username or password are empty
    - Has a button to Sign Up Activity
* Register
    - Switches between sign up with User or Professional
    - Users can enter to Main Activity immediately after sign up
    - Professionals are brought back to Log In Activity after sign up
    - Goes back to Log In Activity with back button
* Home
    - Users can create posts
    - Users can delete their own posts
    - Able to pull down refresh
    - Able to infinite scroll 
    - Displays user profile, username, post timestamp, post description, post image, Likes count, Comments count, post solved/unsolved
    - Button to go into Detail Post Activity
    - Profile image gets into user profile
    - Compose button shrinks when scrolled
    - Bottom navigation has options to Home and Edit Profile
    - Professional has verified check icon next to their username
    - Users can input search text with realtime result of posts. Search areas under username, post description, and category/tag.
* Edit Profile
    - Users able to edit their information except username, first and last name
    - Displays profile image and data from Database
    - Log out button in action bar 
* Profile (user)
    * Basic information of user 
    * Back button gets back to previous screen
* Profile (professional)
    * Verification of identity
    * Displays rating
    * Basic information and profession
    * Back button gets back to previous screen
* Compose
    - Popup overlay with a message icon as background
    - Category and descriptions are required information
    - Camera button to take image. Shows image before being saved.
    - Post button disabled if description is empty 
* Detail Post
    - Back button takes user back to Home Activity
    - Action bar displays username of post creator and beginning of description
    - Displays similar info as Home Activity 
    - Full image button shows a popup with the full image
    - Tag for its category
    - Current user can reply to post, increments comment count
    - Current user profile image displays next to reply space
    - All related comments are displays for post
    - Replied comments displays user profile image, username, description, time difference
    - Users can delete their own comments, also decrements comment count
    

**Optional Nice-to-have Stories**
* Tips to pop up when open app
* Administration to monitor users and professionals
* Setting
    * Choose to show private information or not 
    * change password
* rating
* upload profile image
* reply to posts
* other users can DM other users
* Search posts
* Professionals can advertise
* Users can search for repair/fix based on their location

### 2. Screen Archetypes

* Login
    * user can log in
* Register
    - user can register for an account for User/Professional (bool), (professional) location
* Home (User)
    - Create posts
    - menu to categories
    - Respond to posts as 'user' tag
* Home (Professional)
    * menu to categories
    * Answer posts with 'professional' tag
    * Unable to create new posts 
* Profile (user)
    * Basic information of user 
* Profile (professional)
    * Verification of identity
    * Basic information and profession
* Detail
    * other user or professional can reply
    * image (not required)
    * question
    * report functionality 


### 3. Navigation
**Tab Navigation (top)** (Tab to Screen)
* Categories
* logout


**Tab Navigation (bottom)** (Tab to Screen)

* Home
* Account

**Flow Navigation** (Screen to Screen)
* Login
    - Home
    - Register
* Register
    - back to Login
    - Home
* Home
    - Detail Post
    - User Profile
    - Compose
* Edit Profile
    - Log out button to Log in 
* Detail Post
    - back to previous 
* User Profile
    - back to previous 
* Compose
    - Home
* LogOut
    * Login


## Wireframes

<img src="images/wireframe1.png" width=600> 
<img src="images/wireframe2.png" width=600> 
<img src="images/wireframe3.png" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="./images/Login.jpg" width=140><img src="./images/RegisterUser.jpg" width=140><img src="./images/Home.jpg" width=140><img src="./images/Compose.jpg" width=140> 
<img src="./images/DetailPost.jpg" width=140><img src="./images/ProfileEdit.jpg" width=140><img src="./images/ProfileUser.jpg" width=140>


### [BONUS] Interactive Prototype
<img src="./images/figma_walkthrough1.gif" width=300>

## Schema
### Models
#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user account(default field) |
   | firstName     | String   | name of user |
   | lastName      | String   | name of user |
   | email         | String   | email of user |
   | username      | String   | alias of user |
   | password      | String   | password of user |
   | profileImage  | File     | user profile image |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |

#### Professional

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | user       | Pointer to User    | user model |
   | ratings | Number | rating of a professional |
   | title     | String   | job title of user |
   | company     | String   | company name of user |
   | street     | String   | work street address of user |
   | unit     | String   | unit for address of user |
   | city     | String   | city of company |
   | state     | String   | state of company |
   | zipcode | Number| zipcode of company |
   | phone   | Number| work phone number of user |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |


#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts |
   | Question     | String   | user question |
   | commentsCount | Number   | number of comments that has been posted to an image |
   | likesCount    | Number   | number of likes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   | solved     | Boolean | user can mark the post if it the question gets resolved |
   | category     | String | category of user's post |

#### Comment

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user account(default field) |
   | postId       | String   | unique id of post |
   | userId      | String   | unique id of user |
   | comment        | String   | user's reply to post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   

### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts order by each Post CreatedAt
         ```java
         ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "issue with getting posts",e);
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post"+post.getDescription()+" username: "+ post.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(posts);
                fragmentPostsBinding.swiperContainer.setRefreshing(false);

                }
            });
        }
        ```
      - (Create/POST) Create a new like on a post
      - (Delete) Delete existing like
      - (Create/POST) Create a new comment on a post
      - (Delete) Delete existing comment
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image

## Open-source libraries used

- [Parse](https://www.back4app.com/) - Low code database
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## Sprint Progress
### Sprint 1
#### Items Completed
* Created framework
* Added default styles/color and icons
* Added Login and Data models
* Connected to Parse

#### Walkthrough 
<img src="https://user-images.githubusercontent.com/77254935/115634617-f8c10600-a2d7-11eb-826b-1ea302026e52.gif" width=250>

### Sprint 2
#### Items Completed
* User profile
* Sign up with user and professional
* Added Log out

#### Walkthrough 

<img src="https://user-images.githubusercontent.com/37808313/115913706-f0cfa600-a43e-11eb-9119-9fdbd056fb36.gif" width=250><img src="https://user-images.githubusercontent.com/77254935/115914259-b6b2d400-a43f-11eb-8c31-e09a5baf2af3.gif" width=250><img src="https://user-images.githubusercontent.com/43690277/116009257-7cd00200-a5e6-11eb-8a31-3de66f2412d3.gif" width=250><img src="https://user-images.githubusercontent.com/43690277/116009589-83f80f80-a5e8-11eb-8b74-7e5ad5d38235.gif" width=250>

### Sprint 3

#### Items Completed
* User Detailed profile
* Cleaned up Compose and added disable/enable conditional 
* Updated and began comment section 

<img src="https://user-images.githubusercontent.com/77254935/117904584-9d16e680-b29f-11eb-9cbe-31e19e3947ba.gif" width=250> <img src="https://user-images.githubusercontent.com/77254935/117904601-a2743100-b29f-11eb-896b-4f06a4cddad6.gif" width=250>


### Sprint 4
#### Items Completed
* Detail post with comments
* Detail post with clickable full image 

#### Walkthrough 
<img src="https://user-images.githubusercontent.com/37808313/118311155-864ddb00-b4bd-11eb-90c8-4be1e5a208aa.gif" width=250> <img src="https://user-images.githubusercontent.com/77254935/118342839-499cd680-b4f3-11eb-8b7c-0e7775dcacb2.gif" width=250>
