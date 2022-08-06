import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException {

        Instagram4j instagram4j = Instagram4j.builder().username("username").password("password").build();

        instagram4j.setup();
        instagram4j.login();

        //getting information about user
        InstagramSearchUsernameResult usernameResult = instagram4j.sendRequest(new InstagramSearchUsernameRequest("username"));
        System.out.println(usernameResult.getUser().username);
        System.out.println(usernameResult.getUser().biography);
        System.out.println(usernameResult.getUser().follower_count);

        //commenting

        InstagramFeedResult feedResult = instagram4j.sendRequest(new InstagramUserFeedRequest(usernameResult.getUser().getPk()));
        for (InstagramFeedItem item : feedResult.getItems()){
            System.out.println(item.caption.getText());
            //comment
            instagram4j.sendRequest(new InstagramPostCommentRequest(item.getPk(),"Thank you everyone!"));
            break;
        }


        //get list of my followers
        InstagramGetUserFollowersResult followersResult = instagram4j.sendRequest(
                new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk()));
        for(InstagramUserSummary summary: followersResult.getUsers()){
            System.out.println(summary.full_name+" "+ summary.username);
        }
        //posting

        instagram4j.sendRequest(new InstagramUploadPhotoRequest(new File("path of photo"),"I uploaded this photo with Java code."));


//
        //follow and unfollow
        InstagramSearchUsernameResult github = instagram4j.sendRequest(new InstagramSearchUsernameRequest("github"));
        instagram4j.sendRequest(new InstagramFollowRequest(github.getUser().getPk()));
        instagram4j.sendRequest(new InstagramUnfollowRequest(github.getUser().getPk()));

        System.out.println(github.getUser().username);
        System.out.println(github.getUser().biography);
        System.out.println(github.getUser().follower_count);


        //read direct
        InstagramInboxResult inboxResult = instagram4j.sendRequest(new InstagramGetInboxRequest());
        for(InstagramInboxThread thread: inboxResult.getInbox().getThreads()){
            for(InstagramInboxThreadItem inboxThreadItem: thread.getItems()){
                System.out.println(inboxThreadItem.text);
            }
        }


    }
}